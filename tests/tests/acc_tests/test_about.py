import pytest


@pytest.fixture(scope="function", autouse=True)
def setup(set_session_to_start):
    pass


@pytest.mark.asyncio
async def test_about(telegram_client):
    await telegram_client.send_message("About us")
    msg = await telegram_client.get_last_new_bot_message()
    assert "Civilla system" in msg.text

