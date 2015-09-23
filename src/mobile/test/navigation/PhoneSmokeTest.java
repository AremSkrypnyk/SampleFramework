package ipreomobile.test.navigation;

import ipreomobile.core.Driver;
import ipreomobile.core.Logger;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.templates.test.EmptyTest;
import ipreomobile.ui.authentication.LoginScreen;
import ipreomobile.ui.authentication.PinScreen;
import ipreomobile.ui.authentication.TermsAndConditionsScreen;
import org.testng.annotations.Test;

public class PhoneSmokeTest extends EmptyTest {
    private TermsAndConditionsScreen termsAndConditionsScreen;
    private PinScreen pinScreen;
    private LoginScreen loginScreen;

    @Test
    public void startApplication(){
        Driver.startApplication();
        termsAndConditionsScreen = new TermsAndConditionsScreen();
        termsAndConditionsScreen
                .viewPrivacyPolicy()
                .tapAcceptButton();

        pinScreen = new PinScreen();
        pinScreen
                .enterFirstPin()
                .enterSecondPin();

        loginScreen = new LoginScreen();
        loginScreen.login();
        Logger.logScreenshot("Logged in: phone");
    }
}
