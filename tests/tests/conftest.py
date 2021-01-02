import asyncio
import json
from functools import lru_cache

import pytest
from pyrogram import Client as BotClient
from pyrogram.types import Message

from pymongo import MongoClient

BOT_MAX_RETRIES = 30
BOT_RETRY_SECONDS = 0.3

SETTINGS_FILE = "settings.json"
MONGO_DATABASE = "CivillaDatabase"
SESSIONS_COLLECTION = "BotSessions"
USERS_COLLECTION = "Users"
DOMINATORS_COLLECTION = "Dominators"
CAMERAS_COLLECTION = "Cameras"


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


class MongoTestClient:
    def __init__(self, mongo_client: MongoClient, chat_id):
        self.client = mongo_client
        self.db = mongo_client.get_database(MONGO_DATABASE)
        self.users_collection = self.db.get_collection(USERS_COLLECTION)
        self.sessions_collection = self.db.get_collection(SESSIONS_COLLECTION)
        self.dominators_collection = self.db.get_collection(DOMINATORS_COLLECTION)
        self.cameras_collection = self.db.get_collection(CAMERAS_COLLECTION)
        self.chat_id = chat_id

    def _get_object_id(self, object_id):
        return self.chat_id if object_id is None else object_id

    def get_entity(self, collection, object_id=None):
        return collection.find_one(
            filter={"objectId": self._get_object_id(object_id)}
        )

    def update_entity(self, collection, data, object_id=None):
        data_ = {"objectId": self._get_object_id(object_id)}
        data_.update(data)
        return collection.find_one_and_update(
            filter={"objectId": self._get_object_id(object_id)},
            update={
                "$set": data_
            },
            upsert=True
        )

    def delete_entity(self, collection, object_id=None):
        return collection.find_one_and_delete(
            filter={"objectId": self._get_object_id(object_id)}
        )

    def get_user(self):
        return self.get_entity(self.users_collection)

    def update_user(self, data, object_id=None):
        return self.update_entity(self.users_collection, data, object_id=object_id)

    def delete_user(self):
        return self.delete_entity(self.users_collection)

    def get_session(self):
        return self.get_entity(self.sessions_collection)

    def update_session(self, data):
        return self.update_entity(self.sessions_collection, data)

    def delete_session(self):
        return self.delete_entity(self.sessions_collection)

    def get_camera(self, object_id):
        return self.get_entity(self.cameras_collection, object_id)

    def update_camera(self, data, object_id):
        return self.update_entity(self.cameras_collection, data, object_id)

    def delete_camera(self, object_id):
        return self.delete_entity(self.cameras_collection, object_id)

    def get_dominator(self, object_id):
        return self.get_entity(self.dominators_collection, object_id)

    def update_dominator(self, data, object_id):
        return self.update_entity(self.dominators_collection, data, object_id)

    def delete_dominator(self, object_id):
        return self.delete_entity(self.dominators_collection, object_id)


@pytest.fixture()
@pytest.mark.asyncio
async def mongo_client(telegram_client) -> MongoTestClient:
    return MongoTestClient(MongoClient(get_mongo_conn_string()), await telegram_client.get_chat_id())


@pytest.fixture()
@pytest.mark.asyncio
async def clean_user_data(mongo_client):
    mongo_client.delete_user()
    mongo_client.delete_session()


@pytest.fixture()
@pytest.mark.asyncio
async def fill_user_data(mongo_client, telegram_client):
    mongo_client.update_session(
        {
            "isPersonalDataFilled": True,
            "firstNameBuf": "test_first_name",
            "lastNameBuf": "test_last_name",
            "likesBeerBuf": True
        }
    )

    mongo_client.update_user(
        {
            "firstName": "test_first_name",
            "isPoliceman": False,
            "isPolicemanStr": "no",
            "lastName": "test_last_name",
            "likesBeer": True,
            "psychopassValue": 0.8712589961827121
        }
    )


@pytest.fixture()
@pytest.mark.asyncio
async def fill_policeman_user_data(mongo_client, telegram_client):
    mongo_client.update_session(
        {
            "isPersonalDataFilled": True,
            "firstNameBuf": "test_first_name",
            "lastNameBuf": "test_last_name",
            "likesBeerBuf": True
        }
    )
    mongo_client.update_user(
        {
            "firstName": "test_first_name",
            "isPoliceman": True,
            "isPolicemanStr": "yes",
            "lastName": "test_last_name",
            "likesBeer": True,
            "psychopassValue": 0.8712589961827121
        }
    )


@pytest.fixture()
@pytest.mark.asyncio
async def set_session_to_start(mongo_client):
    mongo_client.update_session(
        {
            "msgId": "start",
        }
    )


class RetriesLimitExceeded(Exception):
    pass


class BotTestClient:
    def __init__(self, app: BotClient, bot_name):
        self.app = app
        self.name = bot_name
        self.last_msg_id = None

    async def get_last_new_bot_message(self) -> Message:
        retries = 0
        while True:
            message = (await self.app.get_history(self.name, limit=1))[0]
            if message.from_user.username == get_bot_name() and message.message_id != self.last_msg_id:
                self.last_msg_id = message.message_id
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
async def telegram_client() -> BotTestClient:
    app = BotClient("testing", get_app_id(), get_app_hash())
    await app.start()
    yield BotTestClient(app, get_bot_name())
    await app.stop()