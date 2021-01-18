import pytest


@pytest.fixture(scope="function", autouse=True)
def setup(set_session_to_start):
    pass


@pytest.mark.asyncio
async def test_unregistred_interface(telegram_client, mongo_client, clean_user_data):
    #  Test flow
    await telegram_client.send_message("start")
    msg = await telegram_client.get_last_new_bot_message()
    assert msg.reply_markup.keyboard == [['Fill my data'], ['About us']]


@pytest.mark.asyncio
async def test_citizen_interface(telegram_client, mongo_client, fill_user_data):
    #  Test flow
    await telegram_client.send_message("start")
    msg = await telegram_client.get_last_new_bot_message()
    assert msg.reply_markup.keyboard == [['Edit my data'], ['View data'],
                                         ['Analyse my psychopass'], ['About us']]


@pytest.mark.asyncio
async def test_policeman_interface(telegram_client, mongo_client, fill_policeman_user_data):
    #  Test flow
    await telegram_client.send_message("start")
    msg = await telegram_client.get_last_new_bot_message()
    assert msg.reply_markup.keyboard == [['Edit my data'], ['View data'],
                                         ['Devices'], ['Analyse my psychopass'], ['About us']]
