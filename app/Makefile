run-dist:
	./build/install/app/bin/app -h
run-dist-generate:
	./build/install/app/bin/app ./src/files/first.json ./src/files/second.json
build:
	./gradlew clean
	./gradlew installDist
lint:
	./gradlew checkstyleMain
lint-test:
	./gradlew checkstyleTest

.PHONY: build
