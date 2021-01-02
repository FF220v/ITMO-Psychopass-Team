import pytest
from .conftest import trigger_dominator_endpoint, get_random_device_id


@pytest.fixture(scope="function", autouse=True)
def setup(set_session_to_start):
    pass


@pytest.mark.asyncio
async def test_notify_when_using_dominator(telegram_client, mongo_client, fill_policeman_user_data):
    await telegram_client.send_message("asdf")
    await telegram_client.get_last_new_bot_message()  # saving last state of bot

    device_id = get_random_device_id()
    my_id = await telegram_client.get_chat_id()

    dominator_data = {'objectId': device_id, 'name': 'test_d',
                      'ownerId': my_id, 'type': 'dominator'}
    mongo_client.update_dominator(dominator_data, object_id=device_id)

    # sending requests before we get high psychopass number, so we will get a notification to policeman chat
    while True:
        response = trigger_dominator_endpoint({
            "deviceId": device_id,
            "userId": my_id,
            "targetId": my_id  # Trying to shot ourselves =)
        })

        assert response.status_code == 200
        resp = response.json()
        if resp["usageAllowed"] is True:
            break

    msg = await telegram_client.get_last_new_bot_message()
    assert f"[Notification] [Analysis] [Dominator] Policeman {my_id} " \
           f"test_first_name test_last_name. User psychopass value is higher then average" in msg.text