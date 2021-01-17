import pytest
from ..conftest import trigger_camera_endpoint, trigger_dominator_endpoint


@pytest.mark.asyncio
async def test_use_camera_right_device_id(fill_random_camera_data):
    device_id = fill_random_camera_data.get("objectId")

    response = trigger_camera_endpoint({
        "deviceId": device_id,
    })

    assert response.status_code == 200
    assert response.json() == {'status': 'ok', 'reason': ''}


@pytest.mark.asyncio
async def test_use_camera_wrong_device_id():
    response = trigger_camera_endpoint({
        "deviceId": "11111111",
        "userId": "11111111"
    })

    assert response.status_code == 200
    assert response.json() == {'status': 'error', 'reason': 'unauthorized camera'}


@pytest.mark.asyncio
async def test_use_dominator_right_owner(fill_random_dominator_data):
    device_id = fill_random_dominator_data.get("objectId")
    owner_id = fill_random_dominator_data.get("ownerId")

    response = trigger_dominator_endpoint({
        "deviceId": device_id,
        "userId": owner_id,
        "targetId": "asdf"
    })

    assert response.status_code == 200
    resp = response.json()
    assert "usageAllowed" in resp
    resp.pop("usageAllowed")
    assert resp == {'reason': '', 'status': 'ok'}


@pytest.mark.asyncio
async def test_use_dominator_wrong_owner(fill_random_dominator_data):
    device_id = fill_random_dominator_data.get("objectId")

    response = trigger_dominator_endpoint({
        "deviceId": device_id,
        "userId": "wrong_owner_id",
        "targetId": "asdf"
    })

    assert response.status_code == 200
    assert response.json() == {'reason': 'insufficient permissions', 'status': 'error'}