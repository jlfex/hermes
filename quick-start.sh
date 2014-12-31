#!/bin/bash

echo "[Pre-Requirement] Makesure install JDK 6.0+ and set the JAVA_HOME."
echo "[Pre-Requirement] Makesure install Maven 3.0.3+ and set the PATH."
	
set MAVEN_OPTS=$MAVEN_OPTS -XX:MaxPermSize=128m

echo "[Step 1] Install all hermes modules to local maven repository"
mvn clean install


#echo "[Step 2] Initialize schema and data for all example projects."
#cd examples
#mvn antrun:run -Prefresh-db
#cd ..

echo "[Step 3] Start all projects."
cd hermes-main
mvn clean jetty:run -Djetty.port=8005 &
cd ..\hermes-console
mvn clean jetty:run -Djetty.port=8006 &

echo "[INFO] Please wait a moment then access below demo sites:"
echo "[INFO] http://localhost:8005/hermes-main"
echo "[INFO] http://localhost:8006/hermes-console"

