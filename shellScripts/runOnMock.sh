sh startServer.sh &
SERV=$!
java -cp "libs/testng-6.8.8.jar:libs/selenium-server-standalone-2.41.0.jar:bin" org.testng.TestNG TestSuite_series.xml
pkill -TERM -P $SERV

