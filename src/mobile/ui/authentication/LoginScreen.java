package ipreomobile.ui.authentication;

import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.ScreenCard;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;

import ipreomobile.core.Driver;
import ipreomobile.core.SecureDataManager;
import org.testng.Assert;

public class LoginScreen extends ScreenCard {

    private static final String FORGOT_PASSWORD_TEXT    = "Forgot Password?";
    private static final String PRIVACY_POLICY_TEXT     = "Privacy Policy";
    private static final String ABOUT_TEXT              = "About";
    private static final String CONTACT_TEXT            = "Contact";
    private static final String HELP_TEXT               = "Help";

    private SenchaWebElement loginField;
    private SenchaWebElement passwordField;
    private SenchaWebElement loginButton;

    public LoginScreen() {
        addCheckpointElement(By.cssSelector(".login-credentials")).mustBeVisible();
/*        Logger.logDebug("Pause on login started.");
        Driver.pause(1000);
        Logger.logDebug("Pause on login ended.");*/
        waitReady();
    }

    public void login() {
    	login(	System.getProperty("test.login"), 
    			SecureDataManager.decrypt(System.getProperty("test.password")));
    }

    public void login(String username, String password) {
        initFields();
        loginField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();
    }

    public LoginScreen verifyLoginFieldPresent(){
        initFields();
        Assert.assertTrue(this.loginField.isDisplayed());
        return this;
    }
    
    public LoginScreen verifyPasswordFieldPresent(){
        initFields();
        Assert.assertTrue(this.passwordField.isDisplayed());
        return this;
    }

    public ForgotPasswordScreen clickForgotPassword(){
        By locator = getElementLocatorByText(FORGOT_PASSWORD_TEXT);
        Driver.findVisible(locator).click();
        return new ForgotPasswordScreen();
    }

    public void clickPrivacyPolicy(){
        By locator = getElementLocatorByText(PRIVACY_POLICY_TEXT);
        Driver.findVisible(locator).click();
    }

    public void clickContact(){
        By locator = getElementLocatorByText(CONTACT_TEXT);
        Driver.findVisible(locator).click();
    }

    public void clickHelp(){
        By locator = getElementLocatorByText(HELP_TEXT);
        Driver.findVisible(locator).click();
    }

    public By getElementLocatorByText(String text) {
        return By.xpath(new XPathBuilder().byText(text).build());
    }

    private void initFields() {
        By loginFieldLocator;
        By passwordFieldLocator;
        By loginButtonLocator;

        loginFieldLocator = By.xpath("//input[@type='email']");
        passwordFieldLocator = By.xpath("//input[@type='password']");
        loginButtonLocator = By.xpath("//span[text()='Log In']");

        this.loginField = Driver.findVisible(loginFieldLocator);
        this.passwordField= Driver.findVisible(passwordFieldLocator);
        this.loginButton = Driver.findVisible(loginButtonLocator);
    }
        
}
