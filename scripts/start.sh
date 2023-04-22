#!/bin/bash
./wait-for-it.sh postgres-db:5432 -t 60
java -jar app.jar