#!/bin/bash

PORT=8081
java -jar ../target/dsp_kvs_api.war --server.port={$PORT}
