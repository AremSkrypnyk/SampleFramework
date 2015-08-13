package ipreomobile.test.settings;

import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.hamburgerItems.Hamburger;
import ipreomobile.ui.hamburgerItems.SettingsView;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class SettingsTestSuite extends BaseTest {
    private Hamburger hamburger;
    private SettingsView settingsView;

    @BeforeMethod
    public void openSettings(Method method) {
        hamburger = navigation.openHamburger();
        settingsView = hamburger.openSettings();
    }

    @Test
    public void verifyMenuItemsInSettings() {
        String defaultActiveMenuItem = "General";

        String[] menuItems = {
                "General",
                "Offline Mode",
                "Change Password",
                "Application Feedback",
                "Globalization",
                "Terms & Conditions",
                "Privacy Policy",
                "Contact Us",
        };

        for (int i = 0; i < menuItems.length; i++) {
            settingsView.verifyItemPresence(menuItems[i]);
        }
        settingsView.verifySelectedItemName(defaultActiveMenuItem);
        navigation.verifyPageTitle(defaultActiveMenuItem);
    }

    @Test //TODO: add version verification
    public void verifyApplicationInformationOnGeneralTab() {
        String expectedProductName = System.getProperty("test.product").toUpperCase(); //"BDC"

        settingsView
                .openGeneralTab()

                .verifyProductName(expectedProductName)
                .verifyVersion();
    }

    @Test
    public void verifyApplicationFeedbackTab() {
        settingsView
                .openApplicationFeedbackTab()

                .verifyControls()
                .verifyTextPresent()
                .verifySubmitButtonDisabled();
        navigation.verifyPageTitle("Application Feedback");
    }

    @Test
    public void verifySubmitButtonIsEnabledAfterRatingApp() {
        settingsView
                .openApplicationFeedbackTab()

                .setStarRating(5)
                .verifySubmitButtonEnabled();
    }

    @Test
    public void verifySubmitButtonIsEnabledAfterEnteringText() {
        settingsView
                .openApplicationFeedbackTab()

                .setQuestionsOrCommentsFieldValue("test")
                .verifySubmitButtonEnabled();
    }
}