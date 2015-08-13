package ipreomobile.templates.test;

import ipreomobile.core.*;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


@Listeners(TestListener.class)
public class EmptyTest {
    private List<String> suiteProperties = new ArrayList<>(Arrays.asList(
            "login",
            "password",
            "customer",
            "defaultTicker",
            "currency",
            "browser",
            "layout",
            "emulationMode"
    ));
    LinkedList<String> suitePropertyKeys = new LinkedList<>();

    private static final String AUTO_TEST_PREFIX = "AUTO";

    @BeforeClass
    public void testSuiteSetup(){
        setupSuiteDependentProperties();
    }


    @BeforeMethod
    public void testCaseSetup(){
        Logger.logMessage("Test method is derived from EmptyTest. Driver is initialized. No page is loaded.");
        Driver.init();
    }

    @AfterMethod
    public void cleanup() {
        Driver.tearDown();
    }

    public String getCaseBasedName(String entityName) {
        return String.join(" ", AUTO_TEST_PREFIX, Logger.getTestName(), "-", StringUtils.capitalize(entityName));
    }

    public String getCaseAndDateBasedName(String entityName) {
        String dateStr = DateTimeHelper.getCurrentDateStr();
        return String.join(" ", AUTO_TEST_PREFIX, Logger.getTestName(), "-", StringUtils.capitalize(entityName), dateStr);
    }

    public String getCaseAndTimeBasedName(String entityName) {
        String timeStr = DateTimeHelper.getCurrentTimeStr();
        return String.join(" ", AUTO_TEST_PREFIX, Logger.getTestName(), "-", StringUtils.capitalize(entityName), timeStr);
    }

    public String getCaseAndDateTimeBasedName(String entityName) {
        String dateStr = DateTimeHelper.getCurrentDateStr();
        String timeStr = DateTimeHelper.getCurrentTimeStr();
        return String.join(" ", AUTO_TEST_PREFIX, Logger.getTestName(), "-", StringUtils.capitalize(entityName), dateStr, timeStr);
    }

    public void verifyExactTextPresence(String text) {
        Driver.verifyExactTextPresentAndVisible(text);
    }
    public void verifyExactTextPresence(String text, SenchaWebElement parentElement) {
        Driver.verifyExactTextPresentAndVisible(text, parentElement);
    }

    public void verifyTextPartPresence(String text) {
        Driver.verifyTextPartPresentAndVisible(text);
    }

    public void verifyTextPartPresence(String text, SenchaWebElement parentElement) {
        Driver.verifyTextPartPresentAndVisible(text, parentElement);
    }

    protected void setupSuiteDependentProperties() {
        String specificKey = "." + System.getProperty("test.product") + "." + System.getProperty("test.env");
        String envKey = "." + System.getProperty("test.env");
        String commonKey = "";

        suitePropertyKeys.add(specificKey);
        suitePropertyKeys.add(envKey);
        suitePropertyKeys.add(commonKey);

        String suiteName = this.getClass().getSimpleName();
        Logger.logMessage("Suite '"+suiteName+"' has started.");

        for (String propName : suiteProperties) {
            try {
                String propValue = getFieldValue(propName, suiteName);
                if (propValue != null && !propValue.isEmpty()) {
                    System.setProperty("test." + propName, propValue);
                }
            } catch (NullPointerException e) {
                throw new Error("Property '"+propName+"' is missing for test "+suiteName+".");
            }
        }
    }

    private String getFieldValue(String fieldName, String suiteName){
        String value = "";
        for (int i=0; i<suitePropertyKeys.size(); i++) {
            value = System.getProperty(suiteName + "." + fieldName + suitePropertyKeys.get(i));
            if (value != null)
                break;
        }
        return value;
    }





}
