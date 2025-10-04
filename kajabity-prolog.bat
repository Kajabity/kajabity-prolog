@echo off
setlocal enabledelayedexpansion

rem --------------------------------------------
rem Run the Kajabity Prolog console application
rem --------------------------------------------

set "BASE_DIR=%~dp0"
set "JAR_DIR=%BASE_DIR%prolog-console\target"
set "JAVA_OPTS=--enable-native-access=ALL-UNNAMED"

rem Find the first matching JAR (e.g., prolog-console-0.1.0-SNAPSHOT.jar)
for %%f in ("%JAR_DIR%\prolog-console-*.jar") do (
    set "JAR_FILE=%%f"
    goto :found
)

echo ERROR: No prolog-console JAR found in %JAR_DIR%
exit /b 1

:found
echo Running: java %JAVA_OPTS% -jar "%JAR_FILE%" %*
echo.
java %JAVA_OPTS% -jar "%JAR_FILE%" %*

endlocal
