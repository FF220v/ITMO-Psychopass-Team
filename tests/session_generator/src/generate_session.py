import asyncio
import json
from functools import lru_cache

from pyrogram import Client


SETTINGS_FILE = "settings.json"


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


async def initialize_client():
    app = Client("testing", get_app_id(), get_app_hash())
    await app.start()
    await app.stop()
    print("Session files were created")

if __name__ == "__main__":
    asyncio.get_event_loop().run_until_complete(initialize_client())