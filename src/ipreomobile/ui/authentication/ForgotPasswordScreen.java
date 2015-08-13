package ipreomobile.ui.authentication;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.ScreenCard;
import ipreomobile.ui.blocks.ConfirmationDialog;
import org.openqa.selenium.By;

public class ForgotPasswordScreen extends ScreenCard {

    private static final String RETURN_TO_LOGIN_TEXT    = "Return to Login";
    private static final String CALL_ME_NOW_TEXT        = "CallMeNow@Ipreo.com";
    private static final String FORGOT_PASSWORD_TEXT    = "Forgot Password?";

    public ForgotPasswordScreen() {
        addCheckpointElement(getElementLocatorByText(FORGOT_PASSWORD_TEXT)).mustBeVisible();
        addCheckpointElement(getElementLocatorByText(RETURN_TO_LOGIN_TEXT)).mustBeVisible();
        waitReady();
    }

    public void clickReturnToLogin(){
        By locator = getElementLocatorByText(RETURN_TO_LOGIN_TEXT);
        Driver.findVisible(locator).click();
        new LoginScreen();
    }

    public ConfirmationDialog clickCallMeNow(){
        By locator = getElementLocatorByText(CALL_ME_NOW_TEXT);
        Driver.findVisible(locator).click();
        return new ConfirmationDialog();
    }

    public By getElementLocatorByText(String text) {
        return By.xpath(new XPathBuilder().byText(text).build());
    }

    public void verifyCallMeNow(){
        clickCallMeNow().verify3rdPartyAppMessage();
    }

}
