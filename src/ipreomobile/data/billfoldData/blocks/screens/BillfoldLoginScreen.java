package ipreomobile.data.billfoldData.blocks.screens;

import ipreomobile.core.Driver;
import ipreomobile.core.SecureDataManager;
import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.ScreenCard;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

public class BillfoldLoginScreen extends ScreenCard {

    private String username;
    private String password;

    private static final String USER_NAME_FIELD_XPATH = new XPathBuilder().byClassName("feature_box").byIdContains("Login_UserName").build();
    private static final String PASSWORD_FIELD_XPATH = new XPathBuilder().byClassName("feature_box").byIdContains("Login_Password").build();
    private static final String LOGIN_BUTTON_XPATH = new XPathBuilder().byClassName("feature_box").byIdContains("LoginButton").build();

    public BillfoldLoginScreen(){
        this.username = System.getProperty("test.login");
        this.password = SecureDataManager.decrypt(System.getProperty("test.password"));
        addCheckpointElement(By.xpath(LOGIN_BUTTON_XPATH));
        waitReady();
        this.init();
    }

    public String getUsername(){
        return this.username;
    }

    private void init() {
       SenchaWebElement loginField = Driver.findVisible(By.xpath(USER_NAME_FIELD_XPATH));
        Assert.assertNotNull(loginField, "User name field not found. Check User name field xpath.");
        loginField.click();
        loginField.sendKeys(username);

       SenchaWebElement passwordField = Driver.findVisible(By.xpath(PASSWORD_FIELD_XPATH));
        Assert.assertNotNull(passwordField, "User Password not found. Check Password field xpath.");
        passwordField.click();
        passwordField.sendKeys(password);

       SenchaWebElement loginButton = Driver.findVisible(By.xpath(LOGIN_BUTTON_XPATH));
        Assert.assertNotNull(loginButton, "User Login button not found. Check check login button xpath.");
        loginButton.click();

    }

}
