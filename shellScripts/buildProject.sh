#!/bin/sh
cd ..
mkdir -p -m a=rwx bin
javac -cp "libs/testng-6.8.8.jar:libs/selenium-server-standalone-2.42.2.jar:libs/cglib-3.1.jar:libs/gson-2.2.4.jar:libs/guava-17.0.jar:libs/httpclient-4.3.4.jar:libs/java-client-2.0.0.jar" $(find ./src/* | grep .java) -d ./bin
cp -a -R src/ipreomobile/data/storage bin/ipreomobile/data/storage
cd shellScripts