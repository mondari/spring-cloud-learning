@echo off
call CopyApp
echo.
echo starting...
echo.
java -jar eureka-client-producer-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer2