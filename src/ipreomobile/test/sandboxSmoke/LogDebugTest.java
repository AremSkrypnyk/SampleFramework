package ipreomobile.test.sandboxSmoke;

import ipreomobile.core.Driver;
import ipreomobile.core.Logger;
import ipreomobile.templates.test.EmptyTest;
import ipreomobile.ui.CheckpointElement;
import ipreomobile.ui.ScreenCard;
import ipreomobile.ui.authentication.LoginScreen;
import ipreomobile.ui.authentication.PinScreen;
import ipreomobile.ui.authentication.TermsAndConditionsScreen;
import org.testng.annotations.Test;

public class LogDebugTest extends EmptyTest {
    @Test
    public void testDebugLogger(){
        Logger.setDebugMode(true);
        Logger.logDebug("DEFAULT DEBUG PROPERTIES: ALL OFF EXCEPT DRIVER.MESSAGE");
        Logger.logDebug("STARTING APPLICATION...");
        Driver.startApplication();
        Logger.logDebug("TERMS AND CONDITIONS SCREEN...");
        TermsAndConditionsScreen termsAndConditionsScreen = new TermsAndConditionsScreen();
        Logger.logDebug("ACCEPTING TERMS AND CONDITIONS...");
        termsAndConditionsScreen.tapAcceptButton();

        Logger.configureDebugOutput()
                .disableMessage(Driver.class)
                .enableStackTrace(Driver.class)
                .enableScreenshot(Driver.class);

        Logger.logDebug("DRIVER: MESSAGES DISABLED, STACKTRACE ENABLED, SCREENSHOT ENABLED...");
        Logger.logDebug("INITIALIZING PIN SCREEN...");
        PinScreen pinScreen = new PinScreen();
        Logger.logDebug("ENTERING FIRST PIN...");
        pinScreen.enterFirstPin();

        Logger.configureDebugOutput()
                .enableMessage(CheckpointElement.class)
                .enableScreenshot(ScreenCard.class);

        Logger.logDebug("CHECKPOINT_ELEMENT: MESSAGE ENABLED.");
        Logger.logDebug("SCREEN_CARD: MESSAGE DISABLED, STACKTRACE DISABLED, SCREENSHOT ENABLED...");
        Logger.logDebug("ENTERING SECOND PIN...");
        pinScreen.enterSecondPin();

        Logger.logDebug("LOGIN SCREEN...");
        LoginScreen screen = new LoginScreen();
        Logger.logDebug("LOGGING IN...");
        screen.login();
    }
}