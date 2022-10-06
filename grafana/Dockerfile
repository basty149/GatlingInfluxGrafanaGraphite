FROM grafana/grafana:7.5.17-ubuntu

USER root
RUN apt-get update && apt-get install -y curl 

WORKDIR /etc/grafana
COPY datasources ./datasources

WORKDIR /app
COPY entrypoint.sh ./
RUN chmod u+x entrypoint.sh

ENTRYPOINT ["/app/entrypoint.sh"]
