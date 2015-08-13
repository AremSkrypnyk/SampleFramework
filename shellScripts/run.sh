sh buildProject.sh
export CLASSPATH="libs/testng-6.8.8.jar:libs/selenium-server-standalone-2.42.2.jar:libs/cglib-3.1.jar:libs/gson-2.2.4.jar:libs/guava-17.0.jar:libs/httpclient-4.3.4.jar:libs/java-client-2.0.0.jar:bin"
cd ..
option="${1}" 
  case ${option} in
    -d | -dir)
		value="${2}"
			case ${value} in
				"") echo "Output directory for reports was not set up as value is empty."
					exit 1
					;;
				*) echo "Output directory for report was set up to : ${2}"
				   java -cp $CLASSPATH org.testng.TestNG TestSuite_smoke.xml -d ${2}
			esac      			   
      				;;
    "")
	  echo "Output directory was not set up. Launch tests with default output directory."
	  java -cp $CLASSPATH org.testng.TestNG TestSuite_smoke.xml
	  ;;
	*)
	  echo "Invalid argument. Possible arguments : -d or -dir"
	  exit 1
	  ;;
  esac

