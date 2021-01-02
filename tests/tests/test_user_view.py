import pytest


@pytest.fixture(scope="function", autouse=True)
def setup(set_session_to_start):
    pass


@pytest.mark.asyncio
async def test_user_view_user_role(telegram_client, mongo_client, fill_user_data):
    # Setup: adding another one user
    another_user_id_1 = "rand_id_1"
    another_user_id_2 = "rand_id_2"

    mongo_client.update_user(
        {
            "firstName": "test_first_name",
            "isPoliceman": False,
            "isPolicemanStr": "no",
            "lastName": "test_last_name",
            "likesBeer": True,
            "psychopassValue": 0.8712589961827121
        },
        object_id=another_user_id_1
    )

    mongo_client.update_user(
        {
            "firstName": "test_first_name",
            "isPoliceman": True,
            "isPolicemanStr": "yes",
            "lastName": "test_last_name",
            "likesBeer": True,
            "psychopassValue": 0.8712589961827121
        },
        object_id=another_user_id_2
    )

    #  Test flow
    await telegram_client.send_message("View data")
    msg = await telegram_client.get_last_new_bot_message()
    assert "Which data do you want to see?" in msg.text

    await telegram_client.send_message("My data")
    msg = await telegram_client.get_last_new_bot_message()
    assert "My data:" in msg.text

    # We get only appropriate (our) data
    assert f"|   {await telegram_client.get_chat_id()}    |test_first_name | test_last_name |" \
           f"   Likes beer   |0.87125899618271|" in msg.text
    assert another_user_id_1 not in msg.text
    assert another_user_id_2 not in msg.text


@pytest.mark.asyncio
async def test_user_view_policeman_role(telegram_client, mongo_client, fill_policeman_user_data):
    # Setup: adding another one user
    another_user_id = "rand_id_1"
    another_policeman_id = "rand_id_2"

    mongo_client.update_user(
        {
            "firstName": "another",
            "isPoliceman": True,
            "isPolicemanStr": "yes",
            "lastName": "policeman",
            "likesBeer": True,
            "psychopassValue": 0.8712589961827121
        },
        object_id=another_policeman_id
    )

    mongo_client.update_user(
        {
            "firstName": "another",
            "isPoliceman": False,
            "isPolicemanStr": "no",
            "lastName": "user",
            "likesBeer": True,
            "psychopassValue": 0.8712589961827121
        },
        object_id=another_user_id
    )

    #  Test flow
    await telegram_client.send_message("View data")
    msg = await telegram_client.get_last_new_bot_message()
    assert "Which data do you want to see?" in msg.text

    my_id = await telegram_client.get_chat_id()

    await telegram_client.send_message("Citizens")
    msg = await telegram_client.get_last_new_bot_message()
    # This one is cop
    assert another_policeman_id not in msg.text
    # This one is citizen
    assert another_user_id in msg.text
    # Me is not a citizen, so Im not in the list
    assert my_id not in msg.text

    await telegram_client.send_message("Cops")
    msg = await telegram_client.get_last_new_bot_message()
    # Cop is shown
    assert another_policeman_id in msg.text
    # Citizen is shown
    assert another_user_id not in msg.text
    # I am a cop, so I am shown here
    assert my_id in msg.text