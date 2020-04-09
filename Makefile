VERSION=0.1.0

default: build-all

build-all: clean stage

clean:
	./gradlew clean

compile: build

build:
	./gradlew build -xtest

uberjar:
	./gradlew uberjar

dist:
	./gradlew installDist

run: dist
	build/install/battlesnake-examples/bin/snake

stage:
	./gradlew stage

build-docker:
	docker build -t pambrose/battlesnake-examples:${VERSION} .

run-docker:
	docker run -p 8080:8080 pambrose/battlesnake-examples:${VERSION}

push-docker:
	docker push pambrose/battlesnake-examples:${VERSION}

versioncheck:
	./gradlew dependencyUpdates

