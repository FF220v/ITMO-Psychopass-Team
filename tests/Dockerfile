FROM python:3.8.6-slim-buster
ADD requirements.txt /requirements.txt
RUN pip3 install -r /requirements.txt
ENV PYTHONPATH "/src"
CMD cd /src && ./run_worker.sh
