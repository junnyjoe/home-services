@echo off
setlocal

set "MAVEN_VERSION=3.9.6"
set "MAVEN_USER_HOME=%USERPROFILE%\.m2"
set "MAVEN_HOME=%MAVEN_USER_HOME%\wrapper\dists\apache-maven-%MAVEN_VERSION%"
set "MAVEN_CMD=%MAVEN_HOME%\bin\mvn.cmd"

if exist "%MAVEN_CMD%" goto runMaven

echo Downloading Maven %MAVEN_VERSION%...
set "MAVEN_URL=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/%MAVEN_VERSION%/apache-maven-%MAVEN_VERSION%-bin.zip"
set "MAVEN_ZIP=%MAVEN_USER_HOME%\wrapper\dists\apache-maven-%MAVEN_VERSION%.zip"

if not exist "%MAVEN_USER_HOME%\wrapper\dists" mkdir "%MAVEN_USER_HOME%\wrapper\dists"

powershell -Command "Invoke-WebRequest -Uri '%MAVEN_URL%' -OutFile '%MAVEN_ZIP%'"
if errorlevel 1 (
    echo Failed to download Maven
    exit /b 1
)

echo Extracting Maven...
powershell -Command "Expand-Archive -Path '%MAVEN_ZIP%' -DestinationPath '%MAVEN_USER_HOME%\wrapper\dists' -Force"
if errorlevel 1 (
    echo Failed to extract Maven
    exit /b 1
)

:runMaven
"%MAVEN_CMD%" %*
