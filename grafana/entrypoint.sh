#!/usr/bin/env sh

url="http://$GF_SECURITY_ADMIN_USER:$GF_SECURITY_ADMIN_PASSWORD@localhost:3000"

post() {
    curl -s -X POST -d "$1" \
        -H 'Content-Type: application/json;charset=UTF-8' \
        "$url$2" 2> /dev/null
}

if [ -z "${GF_INSTALL_PLUGINS}" ] 
then
  unzip -q /app/plugins/grafana-clock-panel-2.1.2.any.zip -d /var/lib/grafana/plugins/grafana-clock-panel
  unzip -q /app/plugins/grafana-worldmap-panel-0.3.3.zip -d /var/lib/grafana/plugins/grafana-worldmap-panel
  unzip -q /app/plugins/yesoreyeram-boomtable-panel-1.4.1.zip -d /var/lib/grafana/plugins/yesoreyeram-boomtable-panel
  unzip -q /app/plugins/grafana-piechart-panel-1.6.4.any.zip -d /var/lib/grafana/plugins/grafana-piechart-panel
fi

if [ ! -f "/var/lib/grafana/.init" ]; then


    exec /run.sh $@ &

    until curl -s "$url/api/datasources" 2> /dev/null; do
        sleep 1
    done

    for datasource in /etc/grafana/datasources/*; do
        post "$(envsubst < $datasource)" "/api/datasources"
    done

    touch "/var/lib/grafana/.init"

    kill $(pgrep grafana)
fi

exec /run.sh $@
