package ipreomobile.actions;

import ipreomobile.core.Driver;
import ipreomobile.core.Logger;
import ipreomobile.core.SecureDataManager;
import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.authentication.LoginScreen;
import ipreomobile.ui.authentication.PinScreen;
import ipreomobile.ui.authentication.TermsAndConditionsScreen;
import org.openqa.selenium.By;

public class Login {
	
	private String username;
	private String password;

    private static final String FORGOT_PIN_BUTTON_TEXT = "Forgot Pin?";
	
	public Login(String username, String password) {
		this.username = username;
		this.password = password;
		this.init();
	}

	public Login(String username) {
		this.username = username;
		this.password = SecureDataManager.decrypt(System.getProperty("user." + username));
		this.init();
	}

	public Login() {
		this.username = System.getProperty("test.login"); 
    	this.password = SecureDataManager.decrypt(System.getProperty("test.password"));
		this.init();
	}

    public String getUsername(){
        return this.username;
    }

	private void init() {
        By forgotPinButtonLocator = By.xpath(new XPathBuilder().byText(FORGOT_PIN_BUTTON_TEXT).build());

        if (! Driver.isElementVisible(forgotPinButtonLocator)) {
            TermsAndConditionsScreen launchApp = new TermsAndConditionsScreen();
            launchApp.tapAcceptButton();

            PinScreen pinScreen = new PinScreen();
            System.setProperty("test.version", pinScreen.getVersion());
            pinScreen.enterFirstPin();
            pinScreen.enterSecondPin();

            LoginScreen loginScreen = new LoginScreen();
            loginScreen.login(this.username, this.password);
        } else {
            PinScreen pinScreen = new PinScreen();
            pinScreen.clickForgotPin();
            pinScreen.acceptPrompt();
            pinScreen.enterFirstPin();
            pinScreen.enterSecondPin();

            LoginScreen loginScreen = new LoginScreen();
            loginScreen.login(this.username, this.password);

            TermsAndConditionsScreen launchApp = new TermsAndConditionsScreen();
            launchApp.tapAcceptButton();
        }


    }
}
