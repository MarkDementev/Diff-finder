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

asc-default:
	./build/install/app/bin/app ./src/test/resources/fixtures/JSON-files/differTestSecondInnerJSONPath1.json ./src/test/resources/fixtures/JSON-files/differTestSecondInnerJSONPath2.json

asc-plain:
	./build/install/app/bin/app -f plain ./src/test/resources/fixtures.YAML-files/differTestFirstInnerYAMLPath1.yml ./src/test/resources/fixtures.YAML-files/differTestFirstInnerYAMLPath2.yml

asc-json:
	./build/install/app/bin/app -f json ./src/test/resources/fixtures/JSON-files/differTestSecondInnerJSONPath1.json ./src/test/resources/fixtures/JSON-files/differTestSecondInnerJSONPath2.json


build-run: build run

.PHONY: build
