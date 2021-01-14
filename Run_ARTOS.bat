@echo off
set mypath=%cd%
@echo %mypath%
mvn exec:java -Dexec.mainClass="io.rocos.ui_test.MasterRunner" -Dexec.args="-v -p=dev"
pause