# Docker solution for Gatling using Influx and Grafana [Graphite enabled]

If you're a serious dev or performance engineer that leverages Gatling to run performance tests, this kit is an essential.

Its a light-weight docker-compose solution to spin up InfluxDB with Graphite enabled and Grafana for metrics collection and analysis during your Gatling test runs.

Versions:
* GRAFANA : 7.5.17
* INFLUXDB : 1.8
* GATLING : 3.8.3

# Prerequisites
The solution needs docker and docker-compose

> Note: If you don't have docker or docker-compose, use the bash script below

> download the bash script from my repo

`sudo curl https://raw.githubusercontent.com/pbushan/GatlingInfluxGrafanaGraphite/master/grafana-influx.sh`

> run the bash script

`sh grafana-influx.sh`

> reboot your server

`sudo reboot`

# How to deploy if docker and docker-compose are already installed

Just clone this repo using 

`git clone https://github.com/basty149/GatlingInfluxGrafanaGraphite.git`

Navigate in the folder 

`cd GatlingInfluxGrafanaGraphite`

Edit the configuration.env file and modify the HOST_IP to your docker host IP.

Run docker-compose using

`sudo docker-compose -f docker-compose-application.yaml --env-file configuration.env up -d`

`sudo docker-compose -f docker-compose.yaml --env-file configuration.env up -d`

The first command starts :
* spring boot example application

The second starts :
* grafana
* influxdb
* gatling
* jmxtrans

That's all. No more action is needed. The second command starts the gatling container and so begin the load test on the example application.

You can now visualize the result in GRAFANA.

To run the load test again, simply restarts the docker container :

`docker start gatling`

The followings URL are now available :
1) grafana : `http://<host>:3000/`
2) chronograf : `http://<host>:8888/`
3) application to test : `http://<host>:8090/example/v1/hotels`
