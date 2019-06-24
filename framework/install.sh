#!/bin/bash

cd base 
mvn clean && mvn install
cd ../redis
mvn clean && mvn install
cd ../basic
mvn clean && mvn install
cd ../mongo
mvn clean && mvn install
cd ../mysql
mvn clean && mvn install
cd ..