.DEFAULT_GOAL := help

install: ## Install project
	mvn clean install

build: ## build image, usage: make build, make build image=nginx
	sed -i -e "s/host=localhost/host=mongodb/g"  ms_reports/src/main/resources/log4j.properties
	sed -i -e "s/\:\/\/localhost/\:\/\/postgres/g" ms_reports/src/main/resources/reports.properties
	mvn clean install
	sed -i -e "s/host=mongodb/host=localhost/g"  ms_reports/src/main/resources/log4j.properties
	sed -i -e "s/\:\/\/postgres/\:\/\/localhost/g" ms_reports/src/main/resources/reports.properties
	rm -f ms_reports/src/main/resources/*.properties-e
	docker-compose build

up: ## Up docker containers, usage: make up
	docker-compose up

down: ## Stops and removes the docker containers, usage: make down
	docker-compose down

restart: ## Restart all containers, usage: make restart
	docker-compose restart

status: ## Show containers status, usage: make status
	docker-compose ps

help:
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-16s\033[0m %s\n", $$1, $$2}'