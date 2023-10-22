#! /bin/bash

BASE_IMAGE=openjdk:17

PROJECT_DIRS=("api-gateway-service" "ecom-service-registry" "order-service" "product-service" "user-service")

for dir in "${PROJECT_DIRS[@]}"; do
	cd "$dir"

	mvn clean package

	docker build -t "$dir" --build-arg JAR_FILE=target/*.jar --file Dockerfile .

	cd ..
done
