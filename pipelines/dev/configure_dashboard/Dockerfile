FROM python:3.8.6-slim-buster
ADD ./src /src
RUN cd /src && pip3 install -r requirements.txt
ENV PYTHONPATH "/src"
CMD cd /src && /src/run_worker.sh
