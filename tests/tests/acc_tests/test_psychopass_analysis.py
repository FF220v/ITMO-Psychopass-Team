import pytest
import re

psychopass_value_regexp = re.compile("Your psychopass value is (0\.\d+)")


@pytest.fixture(scope="function", autouse=True)
def setup(set_session_to_start):
    pass


@pytest.mark.asyncio
async def test_psychopass_analysis(telegram_client, mongo_client, fill_user_data):
    #  We need to detect two cases here. When psychopass is < 0.5 (normal) and > 0.5 (high)
    bad_case = False
    good_case = False
    while not (bad_case and good_case):
        await telegram_client.send_message("Analyse my psychopass")
        msg = await telegram_client.get_last_new_bot_message()
        psychopass_value = psychopass_value_regexp.findall(msg.text).pop()
        if float(psychopass_value) > 0.5:
            bad_case = True
            assert "Consider killing yourself" in msg.text
        if float(psychopass_value) < 0.5:
            good_case = True
            assert "Your psychopass value is normal" in msg.text

        #  Check final state of database
        user = mongo_client.get_user()
        user.pop("_id")
        user.pop("objectId")
        assert str(user.get("psychopassValue")) == str(psychopass_value)
