import pytest


@pytest.fixture(scope="function", autouse=True)
def setup(set_session_to_start):
    pass


@pytest.mark.asyncio
async def test_initial_registration(telegram_client, mongo_client, clean_user_data):
    #  Test flow
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

    await telegram_client.send_message("No")
    msg = await telegram_client.get_last_new_bot_message()
    assert "Is this data correct?" in msg.text
    assert "test_first_name" in msg.text
    assert "test_last_name" in msg.text
    assert msg.text.endswith("You like beer: Well, no, but, actually yes")

    await telegram_client.send_message("Yes")
    msg = await telegram_client.get_last_new_bot_message()
    assert "Personal data saved." in msg.text
    assert msg.text.endswith("Hello there, citizen test_first_name test_last_name!"
                             " It is civilla system. What do you want to do?")

    #  Check final state of database
    user = mongo_client.get_user()
    user.pop("_id")
    user.pop("objectId")
    assert user.get("psychopassValue")
    user.pop("psychopassValue")
    assert user == {'firstName': 'test_first_name', 'isPoliceman': False, 'isPolicemanStr': 'no',
                    'lastName': 'test_last_name', 'likesBeer': False}

    session = mongo_client.get_session()
    session.pop("_id")
    session.pop("objectId")
    assert session == {'isPersonalDataFilled': True, 'msgId': 'start', 'firstNameBuf': 'test_first_name',
                       'lastNameBuf': 'test_last_name', 'likesBeerBuf': False}


@pytest.mark.asyncio
async def test_edit_data(telegram_client, mongo_client, fill_user_data):

    #  Test flow
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

    #  Check final state of database
    user = mongo_client.get_user()
    user.pop("_id")
    user.pop("objectId")
    assert user.get("psychopassValue")
    user.pop("psychopassValue")
    assert user == {'firstName': 'test_first_name', 'isPoliceman': False, 'isPolicemanStr': 'no',
                    'lastName': 'test_last_name', 'likesBeer': True}

    session = mongo_client.get_session()
    session.pop("_id")
    session.pop("objectId")
    assert session == {'isPersonalDataFilled': True, 'msgId': 'start', 'firstNameBuf': 'test_first_name',
                       'lastNameBuf': 'test_last_name', 'likesBeerBuf': True}
