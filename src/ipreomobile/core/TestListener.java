package ipreomobile.core;

import ipreomobile.data.TestDataStorage;
import org.testng.*;
import ipreomobile.ui.UITitles;
import org.testng.xml.XmlSuite;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class TestListener extends TestListenerAdapter {
	
    private static final String SYSTEM_PROPERTIES_FILE_NAME = "test.properties";
    private List<String> envProperties = new ArrayList<>(Arrays.asList(
            "baseURL",
            "timeout",
            "animationLength",
            "login",
            "password",
            "customer",
            "defaultTicker",
            "currency"));

    LinkedList<String> environmentPropertyKeys = new LinkedList<>();


	@Override
	public void onStart(ITestContext tc){
        setupGlobalProperties(SYSTEM_PROPERTIES_FILE_NAME);
        setupEnvironmentProperties();

        tc.getSuite().getXmlSuite().setConfigFailurePolicy(XmlSuite.CONTINUE);

		Logger.init(tc.getSuite().getName());
        UITitles.init();
        TestDataStorage.init();
	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		Logger.logMessage("Test [" + Logger.getTestName() + "] has passed within [" + StringHelper.durationToTimeStr(tr.getEndMillis() - tr.getStartMillis()) + "].");
	}

    @Override
    public void onTestStart(ITestResult tr){
        Logger.logTestStarted(tr.getName());
    }
	
	@Override
	public void onTestFailure(ITestResult tr) {
		Logger.logError(tr);
	}

    @Override
    public void onConfigurationFailure(ITestResult tr) {
        Logger.logError(tr);
        Driver.tearDown();
        throw new SkipException("Skipping the test due to configuration failure");
    }

    private void setupGlobalProperties(String fileToReadName) {
        Properties properties = new Properties();
        FileInputStream propFile;
        try {
            propFile = new FileInputStream(fileToReadName);
            properties.load(propFile);
        } catch (IOException e) {
            throw new Error("Failed to read properties file '" + fileToReadName+"': "+e.getMessage(), e);
        }

        @SuppressWarnings("unchecked")
        Enumeration<String> e = (Enumeration<String>) properties.propertyNames();
        while (e.hasMoreElements()) {
            String key = e.nextElement();
            System.setProperty(key, properties.getProperty(key));
        }
    }

    private void setupEnvironmentProperties() {
        String specificKey = "." + System.getProperty("test.product") + "." + System.getProperty("test.env");
        String envKey = "." + System.getProperty("test.env");
        String commonKey = "";

        environmentPropertyKeys.add(specificKey);
        environmentPropertyKeys.add(envKey);
        environmentPropertyKeys.add(commonKey);

        for (String prop : envProperties) {
            try {
                System.setProperty("test." + prop, getFieldValue(prop));
            } catch (NullPointerException e) {
                throw new Error("Property '"+prop+"' is missing.");
            }
        }
    }

    private String getFieldValue(String fieldName){
        String value = "";
        //LinkedList<String> keys = getFieldKeys(fieldName);
        for (int i=0; i<environmentPropertyKeys.size(); i++) {
            value = System.getProperty("test." + fieldName + environmentPropertyKeys.get(i));
            if (value != null)
                break;
        }
        return value;
    }

    private LinkedList<String> getFieldKeys(String fieldName) {
        LinkedList<String> keys = new LinkedList<>();

        String specificKey = "." + System.getProperty("test.product") + "." + System.getProperty("test.env");
        String envKey = "." + System.getProperty("test.env");
        String commonKey = "";

        keys.add(specificKey);
        keys.add(envKey);
        keys.add(commonKey);

        return keys;
    }
}
