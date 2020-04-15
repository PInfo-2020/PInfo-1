#!/bin/bash
bash docker-compose/clean.sh
docker network create --driver bridge backend-network
docker-compose -f "docker-compose/kafka-compose.yml" up -d
for param in $@ 
do
    cd $param
    mvn clean install -Ppackage-docker-image
    cd ..
    docker-compose -f "docker-compose/microservice/$param.yml" up -d
    echo "docker-compose-$param"
done