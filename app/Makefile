run-dist:
	./build/install/app/bin/app -h
run-dist-generate:
	./build/install/app/bin/app ./src/test/resources/fixtures/differTestNormalFirstPath1.json ./src/test/resources/fixtures/differTestNormalFirstPath2.json
build:
	./gradlew clean
	./gradlew installDist
lint:
	./gradlew checkstyleMain
lint-test:
	./gradlew checkstyleTest

.PHONY: build
