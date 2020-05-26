#!/bin/bash
docker system prune 
docker volume prune
docker network inspect backend-network >/dev/null 2>&1|| docker network create --driver bridge backend-network
docker-compose -f "docker-compose/kafka-compose.yml" up -d
for param in $@ 
do
    cd "$param"
    mvn clean install -Ppackage-docker-image
    cd ..
    docker-compose -f "docker-compose/microservice/$param.yml" up 
    echo "docker-compose-$param"
done
