import re
import pytest
import uuid


device_id_regex = re.compile("objectId ([\da-z]+) added")


@pytest.fixture(scope="function", autouse=True)
def setup(set_session_to_start):
    pass


@pytest.mark.asyncio
async def test_register_dominator(telegram_client, mongo_client, fill_policeman_user_data):
    #  Test flow
    await telegram_client.send_message("Devices")
    msg = await telegram_client.get_last_new_bot_message()
    assert "What to do with devices?" in msg.text

    await telegram_client.send_message("Register device")
    msg = await telegram_client.get_last_new_bot_message()
    assert "What kind of device to register?" in msg.text

    await telegram_client.send_message("Dominator")
    msg = await telegram_client.get_last_new_bot_message()
    assert "Please, input dominator name." in msg.text

    await telegram_client.send_message("test_d")
    msg = await telegram_client.get_last_new_bot_message()
    assert "Is this data correct? Answer Yes or No." in msg.text

    await telegram_client.send_message("Yes")
    msg = await telegram_client.get_last_new_bot_message()
    assert "What to do with devices?" in msg.text
    device_id = device_id_regex.findall(msg.text).pop()

    #  Check final state of database
    device = mongo_client.get_dominator(device_id)
    device.pop("_id")
    assert device == {'objectId': device_id, 'name': 'test_d',
                      'ownerId': await telegram_client.get_chat_id(), 'type': 'dominator'}


@pytest.mark.asyncio
async def test_register_camera(telegram_client, mongo_client, fill_policeman_user_data):
    #  Test flow
    await telegram_client.send_message("Devices")
    msg = await telegram_client.get_last_new_bot_message()
    assert "What to do with devices?" in msg.text

    await telegram_client.send_message("Register device")
    msg = await telegram_client.get_last_new_bot_message()
    assert "What kind of device to register?" in msg.text

    await telegram_client.send_message("Camera")
    msg = await telegram_client.get_last_new_bot_message()
    assert "Please, input camera x coordinate." in msg.text

    await telegram_client.send_message("1")
    msg = await telegram_client.get_last_new_bot_message()
    assert "Please, input camera y coordinate." in msg.text

    await telegram_client.send_message("2")
    msg = await telegram_client.get_last_new_bot_message()
    assert "Please, input camera name." in msg.text

    await telegram_client.send_message("test_c")
    msg = await telegram_client.get_last_new_bot_message()
    assert "Is this data correct? Answer Yes or No." in msg.text

    await telegram_client.send_message("Yes")
    msg = await telegram_client.get_last_new_bot_message()
    assert "What to do with devices?" in msg.text
    device_id = device_id_regex.findall(msg.text).pop()

    #  Check final state of database
    device = mongo_client.get_camera(device_id)
    device.pop("_id")
    assert device == {'objectId': device_id, 'name': 'test_c', 'ownerId': await telegram_client.get_chat_id(),
                      'type': 'camera', 'xCoordinate': 1.0, 'yCoordinate': 2.0}


@pytest.mark.asyncio
async def test_view_dominator(telegram_client, mongo_client, fill_policeman_user_data, fill_random_dominator_data):
    device_id = fill_random_dominator_data.get("objectId")

    #  Test flow
    await telegram_client.send_message("Devices")
    msg = await telegram_client.get_last_new_bot_message()
    assert "What to do with devices?" in msg.text

    await telegram_client.send_message("Show devices")
    msg = await telegram_client.get_last_new_bot_message()
    assert "What kind of devices to show?" in msg.text

    await telegram_client.send_message("Dominators")
    msg = await telegram_client.get_last_new_bot_message()
    assert f"|    {device_id}    |     12345      |     test_d     |" in msg.text


@pytest.mark.asyncio
async def test_view_camera(telegram_client, mongo_client, fill_policeman_user_data, fill_random_camera_data):
    device_id = fill_random_camera_data.get("objectId")

    #  Test flow
    await telegram_client.send_message("Devices")
    msg = await telegram_client.get_last_new_bot_message()
    assert "What to do with devices?" in msg.text

    await telegram_client.send_message("Show devices")
    msg = await telegram_client.get_last_new_bot_message()
    assert "What kind of devices to show?" in msg.text

    await telegram_client.send_message("Cameras")
    msg = await telegram_client.get_last_new_bot_message()
    assert f"|    {device_id}    |     12345      |     test_c     |      1.0       |      2.0       |" in msg.text
