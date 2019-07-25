@echo off
call CopyApp
echo.
echo starting...
echo.
java -jar eureka-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer3