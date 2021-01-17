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


async def measure_multiple_requests_time(async_http_client: aiohttp.ClientSession, data, n_requests):
    results = await asyncio.gather(*[send_request_with_time_measurement(async_http_client, data)
                                     for i in range(n_requests)])
    elapsed_list = [elapsed for _, elapsed in results]
    for resp, _ in results:
        await resp.text()
    return mean(elapsed_list)


@pytest.fixture()
def analysis_test_data(mongo_client):
    return {"userId": str(mongo_client.chat_id),
            "serviceName": "testing"}


@pytest.mark.asyncio
async def test_response_time_single_request(mongo_client, async_http_client, analysis_test_data):
    elapsed = await measure_multiple_requests_time(async_http_client, analysis_test_data, 1)
    print(f"Single response time: {elapsed}")
    assert elapsed < 1


@pytest.mark.asyncio
async def test_high_load_response_time_average(mongo_client, async_http_client, analysis_test_data):
    average_elapsed = await measure_multiple_requests_time(async_http_client, analysis_test_data, 100)
    print(f"Average response time: {average_elapsed}")
    assert average_elapsed < 10


@pytest.mark.asyncio
async def test_high_load_response_hundreds_time_distributed(mongo_client, async_http_client, analysis_test_data):
    average_elapsed_list = []
    for i in range(5):
        average_elapsed_list.append(await measure_multiple_requests_time(async_http_client, analysis_test_data, 100))

    average_elapsed = mean(average_elapsed_list)
    print(f"Average response time: {average_elapsed}")
    assert average_elapsed < 1


@pytest.mark.asyncio
async def test_high_load_response_time_overload(mongo_client, async_http_client, analysis_test_data):
    average_elapsed = await measure_multiple_requests_time(async_http_client, analysis_test_data, 1000)
    print(f"Average response time: {average_elapsed}")
    assert average_elapsed < 1