package ipreomobile.core;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import ipreomobile.ui.blocks.StatusIndicator;
import org.apache.commons.exec.OS;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;

public class Driver {

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private static final int LONG_SIDE_TABLET = 1024;
    private static final int SHORT_SIDE_TABLET = 850;

    private static final int LONG_SIDE_PHONE = 667;
    private static final int SHORT_SIDE_PHONE = 375;


    private static final String LAYOUT = "--window-size=%s,%s";
    private static final int NUMBER_OF_ELEMENTS_TO_DESCRIBE = 5;
    private static final String ERROR_MESSAGE_CLASS = "x-mask-error-message";
    private static final String SERVICE_ERROR_MESSAGE = "Could not retrieve data from the service.";



    private static int currentTimeout;
    private static int savedTimeout;

    public static void startApplication(){
        if (System.getProperty("test.browser").equalsIgnoreCase("chrome")) {
            goTo(System.getProperty("test.baseURL"));
            if (Driver.get().getTitle().contains("Service Unavailable")) {
                throw new Error("Service unavailable.");
            }
        }
    }

	public static void goTo(String url){
    	driver.get().get(url);
    }

    public static boolean isInitialized() {
        return driver.get() != null;
    }

    public static boolean isElementVisible(By locator) {
        return isElementVisible(locator, null);
    }

    public static boolean isElementVisible(By locator, SenchaWebElement parentItem) {
        return findVisibleNow(locator, parentItem) != null;
    }

    public static void pause(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Logger.logError("Failed to pause test execution for "+millis+" millis", e);
        }
    }

    public static void pauseWebVersion(long millis){
        if (System.getProperty("test.browser").equalsIgnoreCase("chrome")) {
            pause(millis);
        }
    }

    public static SenchaWebElement findOne(By by) {
        return getOne(findAll(by), by);
    }

    public static SenchaWebElement findOne(By by, SenchaWebElement parentItem) {
        return getOne(findAll(by, parentItem), by);
    }

    public static SenchaWebElement findOneNow(By by) {
        return findOneNow(by, null);
    }

    public static SenchaWebElement findOneNow(By by, SenchaWebElement parentItem) {
        SenchaWebElement result;
        Driver.saveTimeout();
        Driver.nullifyTimeout();
        result = findOne(by, parentItem);
        Driver.restoreSavedTimeout();
        return result;
    }

    private static SenchaWebElement getOne(List<SenchaWebElement> foundElements, By by) {
        if (foundElements.isEmpty()){
            throw new NoSuchElementException("No elements found by selector ["+by.toString()+"].");
        } else if (foundElements.size() > 1) {
            String errorMessage = "More than 1 element was found by selector ["+by.toString()+"]: found "+foundElements.size()+" elements.";
            for (int i=0; i<foundElements.size(); i++) {
                errorMessage += ("\n" + ElementHelper.describe(foundElements.get(i)));
                if (i == NUMBER_OF_ELEMENTS_TO_DESCRIBE) {
                    errorMessage += ("\n..."+(NUMBER_OF_ELEMENTS_TO_DESCRIBE-foundElements.size())+" more elements hidden.");
                    break;
                }
            }
            throw new Error(errorMessage);
        }
        return foundElements.get(0);
    }

    public static SenchaWebElement findFirst(By by) {
        return findFirst(by, null);
    }

    public static SenchaWebElement findFirst(By by, SenchaWebElement parentItem) {
        SenchaWebElement firstElement;
        List<SenchaWebElement> foundElements = findAll(by, parentItem);
        if (foundElements.isEmpty()) {
            throw new NoSuchElementException("No elements found by selector [" + by.toString() + "].");
        }
        return foundElements.get(0);
    }

    public static SenchaWebElement findFirstNow(By by) {
        return findFirstNow(by, null);
    }

    public static SenchaWebElement findFirstNow(By by, SenchaWebElement parentItem) {
        SenchaWebElement result;
        Driver.saveTimeout();
        Driver.nullifyTimeout();
        result = findFirst(by, parentItem);
        Driver.restoreSavedTimeout();
        return result;
    }

    public static SenchaWebElement findVisible(By by) {
        return findVisible(by, null);
    }

    public static SenchaWebElement findVisible(By by, SenchaWebElement parentItem) {
        long waitVisibleTimeout = currentTimeout * 1000000000;
        int retryTimeoutMillis = 200;
        List<SenchaWebElement> foundItems;
        List<SenchaWebElement> visibleItems;

        long startTime = System.nanoTime();
        try {
            do {
                foundItems = findAll(by, parentItem);
                visibleItems = new ArrayList<>();
                for (SenchaWebElement item : foundItems) {
                    if (item.isDisplayed()) {
                        visibleItems.add(item);
                    }
                }
                if (visibleItems.size() == 1) {
                    return visibleItems.get(0);
                } else if (visibleItems.size() >= 1) {
                    return ElementHelper.getTopElement(visibleItems);
                }
                pause(retryTimeoutMillis);
            } while (System.nanoTime() - startTime <= waitVisibleTimeout);
        } catch (StaleElementReferenceException e) {
            if (parentItem != null && !parentItem.isDetachedFromDom()) {
                return findVisible(by, parentItem);
            }
        }
        return null;
    }

    public static SenchaWebElement findVisibleNow(By by) {
        return findVisibleNow(by, null);
    }

    public static SenchaWebElement findVisibleNow(By by, SenchaWebElement parentItem) {
        SenchaWebElement result;
        Driver.saveTimeout();
        Driver.nullifyTimeout();
        result = findVisible(by, parentItem);
        Driver.restoreSavedTimeout();
        return result;
    }

    public static SenchaWebElement findIfExists(By by) {
        return findIfExists(by, null);
    }

    public static SenchaWebElement findIfExists(By by, SenchaWebElement parentItem) {
        List<SenchaWebElement> foundElements = findAll(by, parentItem);
        return (foundElements.isEmpty()) ? null : foundElements.get(0);
    }

    public static SenchaWebElement findIfExistsNow(By by) {
        return findIfExistsNow(by, null);
    }

    public static SenchaWebElement findIfExistsNow(By by, SenchaWebElement parentItem) {
        SenchaWebElement result;
        Driver.saveTimeout();
        Driver.nullifyTimeout();
        result = findIfExists(by, parentItem);
        Driver.restoreSavedTimeout();
        return result;
    }

    public static List<SenchaWebElement> findAll(By by) {
        return findAll(by, null);
    }

    public static List<SenchaWebElement> findAll(By by, SenchaWebElement parentItem) {
        List<WebElement> result;
        long startTime = System.nanoTime();
        Assert.assertNotNull(by, "Cannot find elements: the selector is null.");
        Assert.assertNotNull(driver.get(), "Driver appears to be not initialized or destructed at some moment.");
        if (parentItem != null) {
            by = appendXpathWithCurrentItem(by);
        }
        result = (parentItem == null)
                ? driver.get().findElements(by)
                : validateParentItem(parentItem).findElements(by);
        List<SenchaWebElement> wrappedElements = new ArrayList<>();
        for (WebElement el : result) {
            if (el instanceof RemoteWebElement) {
                wrappedElements.add(new SenchaWebElement(((RemoteWebElement) el)));
            } else {
                throw new Error("Unexpected WebElement implementation: cannot cast SenchaWebElement to "+el.getClass()+".");
            }
        }
        Logger.logDebug("Finding all elements by "+by.toString()+" took "
                + TimeUnit.SECONDS.convert((System.nanoTime()-startTime), TimeUnit.NANOSECONDS) + " seconds.");
        return wrappedElements;
    }

    public static List<SenchaWebElement> findAllNow(By by) {
        return findAllNow(by, null);
    }

    public static List<SenchaWebElement> findAllNow(By by, SenchaWebElement parentItem) {
        List <SenchaWebElement> result;
        Driver.saveTimeout();
        Driver.nullifyTimeout();
        result = findAll(by, parentItem);
        Driver.restoreSavedTimeout();
        return result;
    }

    private static By appendXpathWithCurrentItem(By by){
        String xpathPrefix = "By.xpath: ";
        String selectorString = by.toString();
        if (selectorString.startsWith(xpathPrefix)) {
            selectorString = selectorString.replace(xpathPrefix, "");
            if (!selectorString.startsWith(".")) {
                selectorString = "." + selectorString;
                Logger.logDebug("Appended checkpoint element xpath with '.'");
                return By.xpath(selectorString);

            }
        }
        return by;
    }

    public static void verifyTextPartPresentAndVisible(String textPart) {
        verifyTextPartPresentAndVisible(textPart, null);
    }

    public static void verifyTextPartPresentAndVisible(String textPart, SenchaWebElement parentItem) {
        if ( !isExactTextPresentAndVisible(textPart, parentItem) ) {
            Logger.logError("Element with text part '" + textPart + "' was not found on the screen, but expected.");
        }
    }

    public static void verifyExactTextPresentAndVisible(String exactText){
        verifyExactTextPresentAndVisible(exactText, null);
    }
    public static void verifyExactTextPresentAndVisible(String exactText, SenchaWebElement parentItem) {
        if (!isExactTextPresentAndVisible(exactText, parentItem)) {
            Logger.logError("Element with exact text '" + exactText + "' was NOT found on the screen, but expected.");
        }
    }

    public static void verifyExactTextAbsent(String exactText){
        verifyExactTextAbsent(exactText, null);
    }

    public static void verifyExactTextAbsent(String exactText, SenchaWebElement parentItem) {
        if (isExactTextPresentAndVisible(exactText, parentItem)) {
            Logger.logError("Element with exact text '" + exactText + "' was found on the screen, but NOT expected.");
        }
    }

    public static boolean isExactTextPresentAndVisible(String exactText){
        return isExactTextPresentAndVisible(exactText, null);
    }

    public static boolean isExactTextPresentAndVisible(String exactText, SenchaWebElement parentItem){
        boolean strictEquality = true;
        return isTextPresentAndVisible(exactText, parentItem, strictEquality);
    }

    public static boolean isTextPartPresentAndVisible(String textPart) {
        return isTextPartPresentAndVisible(textPart, null);
    }

    public static boolean isTextPartPresentAndVisible(String textPart, SenchaWebElement parentItem) {
        boolean strictEquality = false;
        return isTextPresentAndVisible(textPart, parentItem, strictEquality);
    }

    private static boolean isTextPresentAndVisible(String text, SenchaWebElement parentItem, boolean isEqualityStrict) {
        String textXpath = (isEqualityStrict
                ? new XPathBuilder().byText(text).build()
                : new XPathBuilder().byTextContains(text).build()
        );
        return Driver.isElementVisible(By.xpath(textXpath), parentItem);
    }

    private static SenchaWebElement validateParentItem(SenchaWebElement parentItem) {
        try {
            parentItem.isEnabled();
        } catch (Throwable t) {
            Logger.logError("Parent item no longer exists", t);
        }
        return parentItem;
    }

    public static File getScreenshot(){
    	return ((TakesScreenshot)driver.get()).getScreenshotAs(OutputType.FILE);
    }

    public static void switchToOfflineMode(){
        switch (System.getProperty("test.browser")) {
            case "chrome":
                Driver.executeJS("Base.util.Connection.emulateOffline()");
                break;
            default:
                throw new AssertionError("Not yet implemented for " + System.getProperty("test.browser"));
        }
    }

    public static void switchToOnlineMode(){
        switch (System.getProperty("test.browser")) {
            case "chrome":
                Driver.executeJS("Base.util.Connection.restoreOnline()");
                break;
            default:
                throw new AssertionError("Not yet implemented for " + System.getProperty("test.browser"));
        }
    }

    public static Object executeJS( String script, Object... objects){
        return ((JavascriptExecutor)driver.get()).executeScript(script, objects);
    }

//    public static void sendKeys(SenchaWebElement element, CharSequence text){
//        String errorMessageForSendKeys = "cannot focus element";
//        try{
//            element.sendKeys(text);
//        }
//        catch(WebDriverException e){
//            if (e.getMessage().contains(errorMessageForSendKeys)){
//                Actions action = new Actions(Driver.get());
//                action.sendKeys(element, text).perform();
//            }
//        }
//    }
//
//    public static void setText(SenchaWebElement element, CharSequence text) {
//        element.clear();
//        sendKeys(element, text);
//    }

    public static void setTimeout(int seconds) {
    	get().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    	get().manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
        Logger.logDebug("Driver timeout has been set to " + seconds + " seconds.");
        currentTimeout = seconds;
    }

    public static void stopIfServicesAreUnavailable(){
        SenchaWebElement errorMessage = Driver.findVisibleNow(By.className(ERROR_MESSAGE_CLASS));
        if (errorMessage != null && errorMessage.getText() == SERVICE_ERROR_MESSAGE) {
            throw new Error("Services are unavailable. Interrupting the test execution.");
        }
    }

    public static void stopOnUpdateError(){
        Assert.assertFalse(StatusIndicator.isUpdateErrorShown(), "Application update failed. Interrupting the test execution.");
    }

    public static int getTimeout() {
        return currentTimeout;
    }
    
    public static void resetTimeout() {
    	setTimeout(Integer.parseInt(System.getProperty("test.timeout")));
    }

    public static void saveTimeout(){
        savedTimeout = currentTimeout;
    }
    public static void restoreSavedTimeout(){
        Assert.assertNotNull(savedTimeout, "Cannot restore saved timeout: saveTimeout() method has not been called.");
        currentTimeout = savedTimeout;
    }

    
    public static void nullifyTimeout() {
    	setTimeout(0);
    }
  
    public static WebDriver get() {
        return driver.get();
    }
	
	public static void set(WebDriver driverInput){
		driver.set(driverInput);
	}

    public static void init() {
        WebDriver driverInput;
        switch (System.getProperty("test.browser")) {
            case "chrome":
                driverInput = initChromeDriver();
                break;
            case "ipad":
                driverInput = initIpadDriver();
                break;
            case "iphone":
                driverInput = initIphoneDriver();
                break;
            case "ipad_simulator":
                driverInput = initIpadSimulatorDriver();
               break;
            case "iphone_simulator":
                driverInput = initIphoneSimulatorDriver();
                break;
            case "android_tablet":
                driverInput = initAndroidTabletDriver();
                break;
            case "android_phone":
                driverInput = initAndroidPhoneDriver();
                break;
            default:
                throw new Error("Unsupported browser: " + System.getProperty("test.browser"));
        }
        driverInput.manage().timeouts().implicitlyWait(
                Integer.parseInt(System.getProperty("test.timeout")),
                TimeUnit.SECONDS
        );
        currentTimeout = Integer.parseInt(System.getProperty("test.timeout"));
        Driver.set(driverInput);
    }

    private static WebDriver initIpadDriver(){
        WebDriver ipadDriver;
        DesiredCapabilities ipadCapabilities = new DesiredCapabilities();
        ipadCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
        ipadCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.1");
        ipadCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        ipadCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPad 2");
        ipadCapabilities.setCapability(MobileCapabilityType.APP, "com.ipreo.mobile.qx.bdc");
        try {
            ipadDriver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), ipadCapabilities);
        } catch (MalformedURLException e1) {
            throw new Error("Unexpected error during remote WebDriver setup: " + e1.getMessage(), e1);
        }

        return ipadDriver;
    }

    private static WebDriver initIphoneDriver(){
        WebDriver iphoneDriver;
        DesiredCapabilities iphoneCapabilities = new DesiredCapabilities();
        iphoneCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
        iphoneCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.1");
        iphoneCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        iphoneCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone");
        iphoneCapabilities.setCapability(MobileCapabilityType.APP, "com.ipreo.ipreomobile.qx.bdcphone");
        try {
            iphoneDriver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), iphoneCapabilities);
        } catch (MalformedURLException e1) {
            throw new Error("Unexpected error during remote WebDriver setup: " + e1.getMessage(), e1);
        }

        return iphoneDriver;
    }

    private static WebDriver initIpadSimulatorDriver(){
        WebDriver ipadSimulatorDriver;
        DesiredCapabilities ipadSimulatorCapabilities = new DesiredCapabilities();
        ipadSimulatorCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
        ipadSimulatorCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.1");
        ipadSimulatorCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        ipadSimulatorCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPad Air");
        ipadSimulatorCapabilities.setCapability(MobileCapabilityType.APP, "/Users/maksym/Documents/Mobile/builds/BD Corporate.app");
        try {
            ipadSimulatorDriver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), ipadSimulatorCapabilities);
        } catch (MalformedURLException e1) {
            throw new Error("Unexpected error during remote WebDriver setup: " + e1.getMessage(), e1);
        }

        return ipadSimulatorDriver;
    }

    private static WebDriver initIphoneSimulatorDriver(){
        WebDriver iphoneSimulatorDriver;
        DesiredCapabilities iphoneSimulatorCapabilities = new DesiredCapabilities();
        iphoneSimulatorCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
        iphoneSimulatorCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.1");
        iphoneSimulatorCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        iphoneSimulatorCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
        iphoneSimulatorCapabilities.setCapability(MobileCapabilityType.APP, "/Users/artem_skrypnyk/Documents/AS/builds/BD Corporate.app");
        try {
            iphoneSimulatorDriver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), iphoneSimulatorCapabilities);
        } catch (MalformedURLException e1) {
            throw new Error("Unexpected error during remote WebDriver setup: " + e1.getMessage(), e1);
        }

        return iphoneSimulatorDriver;
    }

    private static WebDriver initChromeDriver(){
        WebDriver chromeDriver;
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--disable-web-security");
        switch(System.getProperty("test.emulationMode")) {
            case "tablet":
                if (System.getProperty("test.layout").equals("landscape"))
                    option.addArguments(String.format(LAYOUT, LONG_SIDE_TABLET, SHORT_SIDE_TABLET));
                else
                    option.addArguments(String.format(LAYOUT, SHORT_SIDE_TABLET, LONG_SIDE_TABLET));
                break;
            case "phone":
                if (System.getProperty("test.layout").equals("landscape"))
                    option.addArguments(String.format(LAYOUT, LONG_SIDE_PHONE, SHORT_SIDE_PHONE));
                else
                    option.addArguments(String.format(LAYOUT, SHORT_SIDE_PHONE, LONG_SIDE_PHONE));
                break;
            default:
                throw new Error("Chrome Driver supports only 2 emulation modes: tablet or phone. '"
                        +System.getProperty("test.emulationMode") + "' is not supported.");
        }


        if (OS.isFamilyWindows())
            System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        if (OS.isFamilyUnix())
            System.setProperty("webdriver.chrome.driver", "drivers/chromedriver_linux");
        if (OS.isFamilyMac())
            System.setProperty("webdriver.chrome.driver", "drivers/chromedriver_osx");

        chromeDriver = new ChromeDriver(option);

        return chromeDriver;
    }

    private static WebDriver initAndroidTabletDriver(){
        WebDriver androidTabletDriver;
        DesiredCapabilities androidTabletCapabilities = new DesiredCapabilities();
        androidTabletCapabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        androidTabletCapabilities.setCapability("automationName", "Selendroid");
        androidTabletCapabilities.setCapability("platformName", "Android");
        androidTabletCapabilities.setCapability("app", "/Users/artem_skrypnyk/Documents/AS/builds/BDC_QX.apk");
        androidTabletCapabilities.setCapability("appPackage", "com.ipreo.ipreomobile.bdc");
        androidTabletCapabilities.setCapability("appActivity", "com.ipreo.bd.BD");
        try {
            androidTabletDriver = new RemoteWebDriver(new URL("http://localhost:4723/wd/hub"), androidTabletCapabilities);
        } catch (MalformedURLException e1) {
            throw new Error("Unexpected error during remote WebDriver setup: " + e1.getMessage(), e1);
        }

        return androidTabletDriver;
    }

    private static WebDriver initAndroidPhoneDriver(){
        WebDriver androidPhoneDriver;
        DesiredCapabilities androidPhoneCapabilities = new DesiredCapabilities();
        androidPhoneCapabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        androidPhoneCapabilities.setCapability("automationName", "Selendroid");
        androidPhoneCapabilities.setCapability("platformName", "Android");
        androidPhoneCapabilities.setCapability("app", "/Users/artem_skrypnyk/Documents/AS/builds/bdc_phone_qx.apk");
        androidPhoneCapabilities.setCapability("appPackage", "com.ipreo.ipreomobile.bdcphone");
        androidPhoneCapabilities.setCapability("appActivity", "com.ipreo.bd.BD");
        try {
            androidPhoneDriver = new RemoteWebDriver(new URL("http://localhost:4723/wd/hub"), androidPhoneCapabilities);
        } catch (MalformedURLException e1) {
            throw new Error("Unexpected error during remote WebDriver setup: " + e1.getMessage(), e1);
        }

        return androidPhoneDriver;
    }

	public static void tearDown() {
        if (Driver.isInitialized()) {
            Driver.get().quit();
        } else {
            throw new Error("Driver was not initialized and cannot be terminated.");
        }
	}

}
