run-dist:
	./build/install/app/bin/app -h
run-dist-generate:
	./build/install/app/bin/app ./src/test/resources/fixtures/differTestFirstPath1.json ./src/test/resources/fixtures/differTestFirstPath2.json
build:
	./gradlew clean
	./gradlew installDist
lint:
	./gradlew checkstyleMain
lint-test:
	./gradlew checkstyleTest

.PHONY: build
