package ipreomobile.ui.authentication;

import java.util.List;

import ipreomobile.core.*;
import ipreomobile.ui.ScreenCard;
import org.openqa.selenium.By;

import org.testng.Assert;

public class PinScreen extends ScreenCard {

    private static final String CREATE_PIN_TEXT = "Create a PIN";
    private static final String RE_ENTER_PIN_TEXT = "Re-enter PIN";
    private static final String PIN_MISMATCH_TEXT = "PINs do not match. Try Again.";
    private static final String INCORRECT_PIN_TEXT = "Incorrect PIN. Please Try Again.";
    private static final String ATTEMPTS_LEFT_TEXT = "Attempts left: %s";

    private static final String DEFAULT_PIN = "1111";
    private static final String APP_VERSION_LABEL_XPATH = new XPathBuilder()
            .byClassName("app-version")
            .withNoClassName("x-item-hidden")
            .build();
    private static final String FORGOT_PIN_BUTTON_TEXT = "Forgot Pin?";
    private static final String CANCEL_BUTTON_TEXT = "Cancel";
    private static final String DELETE_SYMBOL_BUTTON_CLASS = "cancel-btn";

    private static final String ACCEPT_SWITCHING_BUTTON_XPATH = new XPathBuilder().byTag("div").withClassName("x-msgbox").byTag("span").withText("Yes").build();
    private static final String DECLINE_SWITCHING_BUTTON_XPATH = new XPathBuilder().byTag("div").withClassName("x-msgbox").byTag("span").withText("No").build();

    private String testBrowser = System.getProperty("test.browser");
    private String actualPin;

    public PinScreen() {
        addCheckpointElement(By.xpath(APP_VERSION_LABEL_XPATH));
        setAnimationTimeout(2);
    }

    public PinScreen enterFirstPin() {
        return enterFirstPin(DEFAULT_PIN);
    }

    public PinScreen enterFirstPin(String pin) {
        actualPin = pin;
        enterPin(pin);
        return this;
    }

    public PinScreen enterSecondPin() {
        return enterSecondPin(actualPin);
    }

    public PinScreen enterSecondPin(String pin) {
        waitReady();
        waitUntilReEnterPinAllowed();
        enterPin(pin);
        return this;
    }

    public PinScreen verifyCreatePinHeader() {
        By headerLocator = getElementLocatorByText(CREATE_PIN_TEXT);
        addOneTimeCheckpoint(headerLocator).mustBeVisible();
        waitReady();
        Assert.assertNotNull( Driver.findVisibleNow(headerLocator), CREATE_PIN_TEXT + " header was not found." );
        return this;
    }

    public PinScreen verifyPinMismatchMessage() {
        By headerLocator = getElementLocatorByText(PIN_MISMATCH_TEXT);
        addOneTimeCheckpoint(headerLocator).mustBeVisible();
        waitReady();
        Assert.assertNotNull( Driver.findVisibleNow(headerLocator), PIN_MISMATCH_TEXT + " sub-header was not found." );
        return this;
    }

    public PinScreen verifyReEnterPinMessage() {
        By headerLocator = getElementLocatorByText(RE_ENTER_PIN_TEXT);
        addOneTimeCheckpoint(headerLocator).mustBeVisible();
        waitReady();
        Assert.assertNotNull( Driver.findVisibleNow(headerLocator), RE_ENTER_PIN_TEXT + " sub-header was not found." );
        return this;
    }

    public PinScreen verifyReEnterPinMessageAbsent() {
        waitReady();
        By headerLocator = getElementLocatorByText(RE_ENTER_PIN_TEXT);
        Assert.assertNull( Driver.findVisibleNow(headerLocator), RE_ENTER_PIN_TEXT + " sub-header was found, but not expected." );
        return this;
    }

    public PinScreen verifyIncorrectPinMessage() {
        By headerLocator = getElementLocatorByText(INCORRECT_PIN_TEXT);
        addOneTimeCheckpoint(headerLocator).mustBeVisible();
        waitReady();
        Assert.assertNotNull( Driver.findVisibleNow(headerLocator), INCORRECT_PIN_TEXT + " message was not found." );
        return this;
    }

    public PinScreen verifyAttemptsLeftNumber(int numberOfAttemptsLeft) {
        String attemptsLeftMessage = String.format(ATTEMPTS_LEFT_TEXT, numberOfAttemptsLeft+"");
        By headerLocator = getElementLocatorByText(attemptsLeftMessage);
        addOneTimeCheckpoint(headerLocator).mustBeVisible();
        waitReady();
        Assert.assertNotNull( Driver.findVisibleNow(headerLocator), INCORRECT_PIN_TEXT + " message was not found." );
        return this;
    }

    public PinScreen deleteLastSymbol(){
        waitReady();
        Driver.findVisibleNow(By.className(DELETE_SYMBOL_BUTTON_CLASS)).click();
        return this;
    }

    public void clickForgotPin(){
        waitReady();
        By buttonLocator = getElementLocatorByText(FORGOT_PIN_BUTTON_TEXT);
        Driver.findVisibleNow(buttonLocator).click();
        Driver.pause(Integer.parseInt(System.getProperty("animationLength")));
    }

    public void acceptPrompt(){
       SenchaWebElement acceptSwitchingButton = Driver.findOneNow(By.xpath(ACCEPT_SWITCHING_BUTTON_XPATH));
        acceptSwitchingButton.click();
    }

    public void declinePrompt(){
       SenchaWebElement declineSwitchingButton = Driver.findOneNow(By.xpath(DECLINE_SWITCHING_BUTTON_XPATH));
        declineSwitchingButton.click();
    }

    public PinScreen clickCancel(){
        waitReady();
        By buttonLocator = getElementLocatorByText(CANCEL_BUTTON_TEXT);
        Driver.findVisibleNow(buttonLocator).click();
        return this;
    }

    public PinScreen verifyForgotPinButtonShown(){
        waitReady();
        By buttonLocator = getElementLocatorByText(FORGOT_PIN_BUTTON_TEXT);
        Assert.assertNotNull(Driver.findVisibleNow(buttonLocator), "Forgot PIN button must be present on the screen.");
        return this;
    }

    public PinScreen verifyCancelButtonShown(){
        waitReady();
        By buttonLocator = getElementLocatorByText(CANCEL_BUTTON_TEXT);
        Assert.assertNotNull(Driver.findVisibleNow(buttonLocator), "Cancel button must be present on the screen.");
        return this;
    }

    public PinScreen verifyForgotPinButtonNotShown(){
        waitReady();
        By buttonLocator = getElementLocatorByText(FORGOT_PIN_BUTTON_TEXT);
        Assert.assertNull(Driver.findVisibleNow(buttonLocator), "Forgot PIN button must not be present on the screen.");
        return this;
    }

    public PinScreen verifyCancelButtonNotShown(){
        waitReady();
        By buttonLocator = getElementLocatorByText(CANCEL_BUTTON_TEXT);
        Assert.assertNull(Driver.findVisibleNow(buttonLocator), "Cancel button must not be present on the screen.");
        return this;
    }

    public String getVersion() {
        waitReady();
        String value = "";
       SenchaWebElement version = Driver.findVisible(By.xpath(APP_VERSION_LABEL_XPATH));
        if (version == null) {
            Logger.logError("No application version was found on Pin Screen.");
        } else {
            value = version.getText().split(" ")[1];
        }
        return value;
    }

    public PinScreen verifyVersion(String expectedVersion) {
        String actualVersion = getVersion();
        Assert.assertEquals(actualVersion.trim(), expectedVersion.trim(), "Version mismatch: ");
        return this;
    }

    private void enterPin(String pin) {
        waitReady();
        for (int i=0; i< pin.length(); i++) {
            clickPinButton(pin.charAt(i));
        }
    }

    private void clickPinButton(char buttonIndex) {
        By buttonLocator = By.xpath(new XPathBuilder().byText(buttonIndex + "").build());//getElementLocatorByText(buttonIndex + "");
        Driver.findVisible(buttonLocator).click();
    }

    public By getElementLocatorByText(String text) {
        return By.xpath(new XPathBuilder().byText(text).build());
    }


    //Maintenance section goes next. Please do not change.

    private static final String LOADING_MARKER_CSS = "webkit-transform";

    public void waitUntilReEnterPinAllowed() {
        if (testBrowser.equalsIgnoreCase("chrome") || testBrowser.equalsIgnoreCase("ipad")) {
            String reEnterPinXpath = new XPathBuilder()
                    .byClassName("pin-label")
                    .withChildText("Re-enter PIN").build();
            String reEnterPinAreaXpath = new XPathBuilder()
                    .byClassName("pin-area")
                    .withXpathPart("not(contains(@style, '" + LOADING_MARKER_CSS + "'))").build();

            ScreenCard reEnterPinCard = new ScreenCard();
            reEnterPinCard.addCheckpointElement(By.xpath(reEnterPinXpath))
                    .mustBeVisible();
            reEnterPinCard.addCheckpointElement(By.xpath(reEnterPinAreaXpath), true);
            reEnterPinCard.waitReady();
        }
    }

}
