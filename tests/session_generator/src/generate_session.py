import asyncio
import json
from functools import lru_cache

from pyrogram import Client


SETTINGS_FILE = "/src/settings.json"
CONN_FILE = "/src/conn.json"

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
    async with app:
        with open(CONN_FILE, "w+") as f:
            f.write(json.dumps({"connection_string": await app.export_session_string()}))
    print("Connection string was saved to conn.json")

if __name__ == "__main__":
    asyncio.get_event_loop().run_until_complete(initialize_client())
