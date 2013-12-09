rem @echo off
Echo servidor
cd src
java  -Djava.security.policy=rmi.policy
src.Server
pause