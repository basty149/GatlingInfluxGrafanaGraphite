FROM grafana/grafana:7.5.17-ubuntu

USER root
RUN apt-get update && apt-get install -y curl wget unzip

WORKDIR /etc/grafana
COPY datasources ./datasources

WORKDIR /app
COPY entrypoint.sh ./
RUN chmod u+x entrypoint.sh

WORKDIR /app/plugins
RUN [ -z "${GF_INSTALL_PLUGINS}" ] && wget -q https://storage.googleapis.com/integration-artifacts/grafana-clock-panel/release/2.1.2/any/grafana-clock-panel-2.1.2.any.zip \
https://storage.googleapis.com/plugins-community/grafana-worldmap-panel/release/0.3.3/grafana-worldmap-panel-0.3.3.zip \
https://storage.googleapis.com/integration-artifacts/grafana-piechart-panel/release/1.6.4/any/grafana-piechart-panel-1.6.4.any.zip \
https://storage.googleapis.com/plugins-community/yesoreyeram-boomtable-panel/release/1.4.1/yesoreyeram-boomtable-panel-1.4.1.zip

ENTRYPOINT ["/app/entrypoint.sh"]
