@echo off
echo [Pre-Requirement] Makesure install JDK 6.0+ and set the JAVA_HOME.
echo [Pre-Requirement] Makesure install Maven 3.0.3+ and set the PATH.

set MVN=mvn
set MAVEN_OPTS=%MAVEN_OPTS% -XX:MaxPermSize=128m

echo [Step 1] Install all hermes modules to local maven repository.
call %MVN% clean install
if errorlevel 1 goto error


echo [Step 2] Start all projects.
cd hermes-main
start "hermes-main" %MVN% clean jetty:run -Djetty.port=8105
if errorlevel 1 goto error
cd ..\hermes-console
start "hermes-console" %MVN% clean jetty:run -Djetty.port=8106
if errorlevel 1 goto error
cd ..\

echo [INFO] Please wait a moment. When you see "[INFO] Started Jetty Server" in both 2 popup consoles, you can access below demo sites:
echo [INFO] http://localhost:8105/hermes-main
echo [INFO] http://localhost:8106/hermes-console

goto end
:error
echo Error Happen!!
:end
pause