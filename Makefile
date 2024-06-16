help:
	@cat $(firstword $(MAKEFILE_LIST))

buildkvs:
	@echo "Building kvs"
	cd kvsapi && ./mvnw clean install -DskipTests

devkvsjar:
	@echo "Building kvs"
	cd kvsapi && ./mvnw clean install -DskipTests
	@echo "copying jar to docker folder"
	docker cp kvsapi/target/dsp_kvs_api.war paapi-container:/kvsapi/
	@echo "done"
