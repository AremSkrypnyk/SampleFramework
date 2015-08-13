package ipreomobile.test.authentication;

import ipreomobile.core.Driver;
import ipreomobile.core.Logger;
import ipreomobile.data.ContactData;
import ipreomobile.templates.test.StartupTest;
import ipreomobile.ui.GlobalNavigation;
import ipreomobile.ui.authentication.ForgotPasswordScreen;
import ipreomobile.ui.authentication.LoginScreen;
import ipreomobile.ui.authentication.PinScreen;
import ipreomobile.ui.authentication.TermsAndConditionsScreen;
import ipreomobile.ui.blocks.ConfirmationDialog;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class StartupRegression extends StartupTest {

    private static final String VALID_PIN = "1111";
    private static final String INVALID_PIN = "1211";
    private static final int REENTER_PIN_ATTEMPTS = 4;
    private static final String CONFIRMATION_DIALOG_TEXT = "Continuing will erase all locally cached data and settings for the Ipreo Mobile Application and you will need to create a new PIN. Continue?";

    TermsAndConditionsScreen termsAndConditionsScreen;
    PinScreen pinScreen;
    LoginScreen loginScreen;
    GlobalNavigation navigation;
    ForgotPasswordScreen forgotPasswordScreen;

    @Test
    public void viewAndAcceptTermsAndConditions(){
        termsAndConditionsScreen = new TermsAndConditionsScreen();
        termsAndConditionsScreen
                .viewPrivacyPolicy()
                .tapAcceptButton();

        pinScreen = new PinScreen();
        pinScreen.verifyCreatePinHeader();
    }

    @Test
    public void reenterMismatchingPin(){
        passTermsAndConditionsScreen();
        pinScreen
                .enterFirstPin(VALID_PIN)
                .verifyReEnterPinMessage()
                .enterSecondPin(INVALID_PIN)
                .verifyPinMismatchMessage();
    }

    @Test
    public void reenterMatchingPin(){
        passTermsAndConditionsScreen();
        pinScreen
                .enterFirstPin(VALID_PIN)
                .verifyReEnterPinMessage()
                .enterSecondPin(VALID_PIN);

        loginScreen = new LoginScreen();
        loginScreen
                .verifyLoginFieldPresent()
                .verifyPasswordFieldPresent();
    }

    @Test
    public void testPinCorrection(){
        String firstPinPart = "112";
        String secondPinPart = "11";
        passTermsAndConditionsScreen();

        pinScreen
                .enterFirstPin(VALID_PIN)
                .verifyReEnterPinMessage()
                .enterSecondPin(firstPinPart)
                .deleteLastSymbol()
                .enterSecondPin(secondPinPart);

        loginScreen = new LoginScreen();
        loginScreen
                .verifyLoginFieldPresent()
                .verifyPasswordFieldPresent();
    }

    @Test
    public void loginToApplication(){
        passTermsAndConditionsScreen();
        passPinScreen();

        loginScreen.login();
        navigation = new GlobalNavigation();
    }

    @Test
    public void enterValidPinIn2ndInstance(){
        ContactData data = new ContactData();
        String contactName = data.getName();
        String landingPageTitle;

        passTermsAndConditionsScreen();
        passPinScreen();
        passLoginScreen();
        landingPageTitle = navigation.getPageTitle();

        navigation.openContactProfile(contactName);

        Driver.startApplication();

        pinScreen = new PinScreen();
        pinScreen
                .verifyForgotPinButtonShown()
                .enterFirstPin();
        navigation.waitApplicationReady();
        navigation.verifyPageTitle(landingPageTitle);
    }

    @Test
    public void enterInvalidPinIn2ndInstance(){
        passTermsAndConditionsScreen();
        passPinScreen();
        passLoginScreen();

        launchNewInstance();
        pinScreen = new PinScreen();
        pinScreen
                .enterFirstPin(INVALID_PIN)
                .verifyIncorrectPinMessage();
    }

    @Test
    public void enterInvalidPin5TimesIn2ndInstance(){
        passTermsAndConditionsScreen();
        passPinScreen();
        passLoginScreen();

        launchNewInstance();
        pinScreen = new PinScreen();
        pinScreen.enterFirstPin(INVALID_PIN);
        for (int i= REENTER_PIN_ATTEMPTS; i>0; i--) {
            pinScreen
                    .verifyAttemptsLeftNumber(i)
                    .enterFirstPin(INVALID_PIN);
        }

        pinScreen
                .verifyCreatePinHeader()
                .verifyForgotPinButtonNotShown();
    }

    @Test
    public void verifyForgotPasswordScreen(){
        passTermsAndConditionsScreen();
        passPinScreen();

        loginScreen.clickForgotPassword();
        forgotPasswordScreen = new ForgotPasswordScreen();
        forgotPasswordScreen.verifyCallMeNow();
        forgotPasswordScreen.clickReturnToLogin();
    }

    @Test
    public void tapForgotPinYes(){
        String newPin = "2222";

        passTermsAndConditionsScreen();
        passPinScreen();
        passLoginScreen();

        launchNewInstance();
        pinScreen = new PinScreen();
        pinScreen.clickForgotPin();

        ConfirmationDialog confirmForgotPinDialog = new ConfirmationDialog();
        confirmForgotPinDialog.verifyMessage(CONFIRMATION_DIALOG_TEXT);
        confirmForgotPinDialog.clickYes();

        pinScreen.enterFirstPin(newPin);
        pinScreen.enterSecondPin(newPin);
        loginScreen = new LoginScreen();
        loginScreen.login();

        termsAndConditionsScreen = new TermsAndConditionsScreen();
        termsAndConditionsScreen.tapAcceptButton();

        verifyApplicationStarted();

        launchNewInstance();
        pinScreen.enterFirstPin(newPin);
        navigation = new GlobalNavigation();
    }

    @Test
    public void tapForgotPinNo(){
        passTermsAndConditionsScreen();
        passPinScreen();
        passLoginScreen();

        launchNewInstance();
        pinScreen = new PinScreen();
        pinScreen.clickForgotPin();
        ConfirmationDialog confirmForgotPinDialog = new ConfirmationDialog();
        confirmForgotPinDialog.verifyMessage(CONFIRMATION_DIALOG_TEXT);
        confirmForgotPinDialog.clickNo();

        pinScreen.enterFirstPin();
        navigation = new GlobalNavigation();
    }

    private void passTermsAndConditionsScreen(){
        termsAndConditionsScreen = new TermsAndConditionsScreen();
        termsAndConditionsScreen.tapAcceptButton();
        pinScreen = new PinScreen();
    }

    private void passPinScreen() {
        pinScreen.enterFirstPin();
        pinScreen.enterSecondPin();
        loginScreen = new LoginScreen();
    }

    private void passLoginScreen(){
        loginScreen.login();
        verifyApplicationStarted();
    }

    private void launchNewInstance(){
        Driver.startApplication();
    }

    private void verifyApplicationStarted(){
        navigation = new GlobalNavigation();
    }


//    @Test
//    public void tapForgotPinYes2ndInstance(){ }
//
//    @Test
//    public void tapForgotPinNo2ndInstance(){ }

}
