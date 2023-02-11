.DEFAULT_GOAL := build-run

clean:
	./gradlew clean

build:
	./gradlew clean build

install:
	./gradlew clean install

run-dist:
	./build/install/app/bin/app -h

run:
	./gradlew run

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

lint:
	./gradlew checkstyleMain checkstyleTest

update-deps:
	./gradlew useLatestVersions

asc-json:
	./build/install/app/bin/app ./src/test/resources/fixtures/JSON-files/differTestFirstPath1.json ./src/test/resources/fixtures/JSON-files/differTestFirstPath2.json

asc-yaml:
	./build/install/app/bin/app ./src/test/resources/fixtures.YAML-files/differTestFirstPath1.yml ./src/test/resources/fixtures.YAML-files/differTestFirstPath2.yml


build-run: build run

.PHONY: build
