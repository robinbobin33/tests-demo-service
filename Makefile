SHELL := $(shell which bash)
.DEFAULT_GOAL := help

help: ## This help.
	@awk 'BEGIN {FS = ":.*?## "} /^[a-zA-Z_-]+:.*?## / {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}' $(MAKEFILE_LIST)

GRDL_EXEC=./gradlew
DOCKER_EXEC=$(shell which docker)

DOCKER_REGISTRY?=dcr.company.com
INFRA_DIRECTORY?=./infra/
SOURCES_DIRECTORY?=./

APP_NAME=tests-demo-service
APP_SPACE=qa
VERSION_SUFFIX?=-$(shell echo $$USER-$$(date +'%Y%m%d%H%M%S'))

env:
	$(eval VERSION=$(shell $(GRDL_EXEC) properties -q | grep "^version:" | awk '{print $$2}')$(VERSION_SUFFIX))
	$(eval IMAGE_TAG=$(DOCKER_REGISTRY)/$(APP_NAME):$(VERSION))
	$(eval IMAGE_TAG_LATEST=$(DOCKER_REGISTRY)/$(APP_NAME):latest)

grdl: ## get application name
	@echo $(GRDL_EXEC)

app_name: ## get application name
	@echo $(APP_NAME)

app_version: env ## get application version
	@echo $(VERSION)

image_tag: env ## get image tag
	@echo $(IMAGE_TAG)

build: env ## build application
	$(GRDL_EXEC) clean bootJar
	sudo $(DOCKER_EXEC) build $(DOCKER_OPTIONS) . -f $(INFRA_DIRECTORY)Dockerfile -t $(IMAGE_TAG) -t $(IMAGE_TAG_LATEST)

run:
	docker run --rm -p 8081:8080 dcr.company.com/tests-demo-service:latest
