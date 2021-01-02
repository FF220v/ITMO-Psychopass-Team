import asyncio
import json
from functools import lru_cache

import pytest
from pyrogram import Client as BotClient
from pyrogram.types import Message

from pymongo import MongoClient

BOT_MAX_RETRIES = 20
BOT_RETRY_SECONDS = 1
SETTINGS_FILE = "settings.json"
MONGO_DATABASE = "CivillaDatabase"
SESSIONS_COLLECTION = "BotSessions"
USERS_COLLECTION = "Users"


def load_setting(setting: str):
    with open(SETTINGS_FILE) as f:
        return json.load(f)[setting]


@lru_cache()
def get_bot_name():
    return load_setting("bot_name")


@lru_cache()
def get_app_id():
    return load_setting("app_id")


@lru_cache()
def get_app_hash():
    return load_setting("app_hash")


@lru_cache()
def get_mongo_conn_string():
    return f"mongodb://{load_setting('mongo_host')}"


@pytest.fixture()
def mongo_client() -> MongoClient:
    return MongoClient(get_mongo_conn_string())


@pytest.fixture()
@pytest.mark.asyncio
async def clean_user_data(mongo_client, telegram_client):
    chat_id = await telegram_client.get_chat_id()
    db = mongo_client.get_database(MONGO_DATABASE)
    db.get_collection(SESSIONS_COLLECTION).find_one_and_delete(
        filter={"objectId": chat_id}
    )
    db.get_collection(USERS_COLLECTION).find_one_and_delete(
        filter={"objectId": chat_id}
    )


@pytest.fixture()
@pytest.mark.asyncio
async def fill_user_data(mongo_client, telegram_client):
    chat_id = await telegram_client.get_chat_id()
    db = mongo_client.get_database(MONGO_DATABASE)
    db.get_collection(SESSIONS_COLLECTION).find_one_and_update(
        filter={"objectId": chat_id},
        update={
            "$set":
                {
                    "objectId": chat_id,
                    "isPersonalDataFilled": True,
                    "firstNameBuf": "test_first_name",
                    "lastNameBuf": "test_last_name",
                    "likesBeerBuf": True
                }
        },
        upsert=True
    )
    db.get_collection(USERS_COLLECTION).find_one_and_update(
        filter={"objectId": chat_id},
        update={
            "$set":
                {
                    "objectId": chat_id,
                    "firstName": "test_first_name",
                    "isPoliceman": False,
                    "isPolicemanStr": "no",
                    "lastName": "test_last_name",
                    "likesBeer": True,
                    "psychopassValue": 0.8712589961827121
                }
        },
        upsert=True
    )


@pytest.fixture()
@pytest.mark.asyncio
async def set_session_to_start(mongo_client, telegram_client):
    chat_id = await telegram_client.get_chat_id()
    db = mongo_client.get_database(MONGO_DATABASE)
    db.get_collection(SESSIONS_COLLECTION).find_one_and_update(
        filter={"objectId": chat_id},
        update=
        {
            "$set":
                {
                    "objectId": chat_id,
                    "msg": "start",
                }
        },
        upsert=True
    )


class RetriesLimitExceeded(Exception):
    pass


class BotClientTest:
    def __init__(self, app: BotClient, bot_name):
        self.app = app
        self.name = bot_name
        self.last_msg_date = None

    async def get_last_new_bot_message(self) -> Message:
        retries = 0
        while True:
            message = (await self.app.get_history(self.name, limit=1))[0]
            if message.from_user.username == get_bot_name() and message.date != self.last_msg_date:
                self.last_msg_date = message.date
                break
            if retries > BOT_MAX_RETRIES:
                raise RetriesLimitExceeded
            retries += 1
            await asyncio.sleep(BOT_RETRY_SECONDS)
        return message

    async def send_message(self, message):
        return await self.app.send_message(self.name, message)

    async def get_chat_id(self):
        me = await self.app.get_me()
        return str(me.id)


@pytest.fixture()
@pytest.mark.asyncio
async def telegram_client() -> BotClientTest:
    app = BotClient("testing", get_app_id(), get_app_hash())
    await app.start()
    yield BotClientTest(app, get_bot_name())
    await app.stop()