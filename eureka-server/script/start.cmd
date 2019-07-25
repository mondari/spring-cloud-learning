@echo off

cd ..
call mvn clean package
cd script
copy /y ..\target\eureka-server-0.0.1-SNAPSHOT.jar .\
start RunAsPeer1
start RunAsPeer2
start RunAsPeer3