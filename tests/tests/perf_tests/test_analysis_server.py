import asyncio
import json
import time
from statistics import mean

import aiohttp
import pytest
from ..conftest import get_analysis_server_url


@pytest.fixture(scope="function", autouse=True)
@pytest.mark.asyncio
async def setup_test(fill_user_data):  # to avoid insufficient notifications set user to civilian
    pass


async def send_request_with_time_measurement(async_http_client: aiohttp.ClientSession, data):
    start = time.time()
    resp = await async_http_client.post(f"{get_analysis_server_url()}/analyse", data=json.dumps(data))
    return resp, time.time() - start


@pytest.fixture()
def analysis_test_data(mongo_client):
    return {"userId": str(mongo_client.chat_id),
            "serviceName": "testing"}


@pytest.mark.asyncio
async def test_response_time_single_request(mongo_client, async_http_client, analysis_test_data):
    _, elapsed = await send_request_with_time_measurement(async_http_client, analysis_test_data)
    print(f"Elapsed: {elapsed}")
    assert elapsed < 1


@pytest.mark.asyncio
async def test_high_load_response_time_average(mongo_client, async_http_client, analysis_test_data):
    results = await asyncio.gather(*[send_request_with_time_measurement(async_http_client, analysis_test_data)
                                     for i in range(100)])
    elapsed_list = [elapsed for _, elapsed in results]
    average_elapsed = mean(elapsed_list)
    print(f"Elapsed: {average_elapsed}")
    assert average_elapsed < 10


@pytest.mark.asyncio
async def test_high_load_response_time_overload(mongo_client, async_http_client, analysis_test_data):
    results = await asyncio.gather(*[send_request_with_time_measurement(async_http_client, analysis_test_data)
                                     for i in range(1000)])
    elapsed_list = [elapsed for _, elapsed in results]
    average_elapsed = mean(elapsed_list)
    print(f"Elapsed: {average_elapsed}")
    assert average_elapsed < 15