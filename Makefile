JAR_DIR=./build/libs
JAR_NAME=42ggong-api
JAR_VERSION=0.0.1
JAR_FILE=${JAR_DIR}/${JAR_NAME}-${JAR_VERSION}-SNAPSHOT.jar



# Server
.PHONY: start
start:
	@make clean
	@make start_db
	@make build
	bash -c "source .env && java -jar ${JAR_FILE}"

.PHONY: clean
clean:
	@make prebuild
	@make stop_db



# Build
.PHONY: build
build:
	./gradlew build

.PHONY: prebuild
prebuild:
	./gradlew clean



# DB
.PHONY: start_db
start_db:
	brew services start mariadb

.PHONY: stop_db
stop_db:
	brew services stop mariadb

.PHONY: restart_db
restart_db:
	brew services restart mariadb
