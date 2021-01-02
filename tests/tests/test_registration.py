import pytest
import pymongo


@pytest.fixture(scope="function")
def setup(set_session_to_start):
    pass


@pytest.mark.asyncio
async def test_about(telegram_client):
    await telegram_client.send_message("About")
    msg = await telegram_client.get_last_new_bot_message()
    assert "Civilla system" in msg.text


@pytest.mark.asyncio
async def test_initial_registration(telegram_client, clean_user_data):

    await telegram_client.send_message("Fill my data")
    msg = await telegram_client.get_last_new_bot_message()
    assert "Please, input your first name." in msg.text

    await telegram_client.send_message("test_first_name")
    msg = await telegram_client.get_last_new_bot_message()
    assert "test_first_name" in msg.text
    assert msg.text.endswith("Please, input your second name.")

    await telegram_client.send_message("test_last_name")
    msg = await telegram_client.get_last_new_bot_message()
    assert "test_last_name" in msg.text
    assert msg.text.endswith("Do you like beer? Answer Yes or No.")

    await telegram_client.send_message("Yes")
    msg = await telegram_client.get_last_new_bot_message()
    assert "Is this data correct?" in msg.text
    assert "test_first_name" in msg.text
    assert "test_last_name" in msg.text
    assert msg.text.endswith("You like beer: Yes")

    await telegram_client.send_message("Yes")
    msg = await telegram_client.get_last_new_bot_message()
    assert "Personal data saved." in msg.text
    assert msg.text.endswith("Hello there, citizen test_first_name test_last_name!"
                             " It is civilla system. What do you want to do?")


@pytest.mark.asyncio
async def test_edit_data(telegram_client, fill_user_data):

    await telegram_client.send_message("Edit my data")
    msg = await telegram_client.get_last_new_bot_message()
    assert "Please, input your first name." in msg.text

    await telegram_client.send_message("test_first_name")
    msg = await telegram_client.get_last_new_bot_message()
    assert "test_first_name" in msg.text
    assert msg.text.endswith("Please, input your second name.")

    await telegram_client.send_message("test_last_name")
    msg = await telegram_client.get_last_new_bot_message()
    assert "test_last_name" in msg.text
    assert msg.text.endswith("Do you like beer? Answer Yes or No.")

    await telegram_client.send_message("Yes")
    msg = await telegram_client.get_last_new_bot_message()
    assert "Is this data correct?" in msg.text
    assert "test_first_name" in msg.text
    assert "test_last_name" in msg.text
    assert msg.text.endswith("You like beer: Yes")

    await telegram_client.send_message("Yes")
    msg = await telegram_client.get_last_new_bot_message()
    assert "Personal data saved." in msg.text
    assert msg.text.endswith("Hello there, citizen test_first_name test_last_name!"
                             " It is civilla system. What do you want to do?")