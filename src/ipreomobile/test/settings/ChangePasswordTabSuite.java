package ipreomobile.test.settings;

import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.hamburgerItems.Hamburger;
import ipreomobile.ui.settings.ChangePasswordTab;
import ipreomobile.ui.settings.GeneralTab;
import ipreomobile.ui.hamburgerItems.SettingsView;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class ChangePasswordTabSuite extends BaseTest {
    private Hamburger hamburger;
    private SettingsView settingsView;

    @BeforeMethod
    public void openSettings(Method method) {
        hamburger = navigation.openHamburger();
        settingsView = hamburger.openSettings();
    }

    @Test
    public void verifyTitleAndControlsOnChangePasswordTab() {
        String expectedPageTitle = "Change Password";

        ChangePasswordTab changePasswordTab = settingsView.openChangePasswordTab();
        navigation.verifyPageTitle(expectedPageTitle);
        changePasswordTab
                .verifyInstructionText()
                .verifyInputPasswordFieldsPresence();
    }

    @Test
    public void changePasswordPositiveTest() {
        //TODO: implement test and ensure password resets
    }

    @Test
    public void verifyErrorMessageAfterChangingPasswordWithoutLowercase() {
        ChangePasswordTab changePasswordTab = settingsView.openChangePasswordTab();
        changePasswordTab
                .enterOldPassword("Password7")
                .enterNewPassword("PASSWORD7")
                .enterConfirmNewPassword("PASSWORD7")
                .clickSubmitButton()
                .verifyErrorMessage("The new password does not meet the minimum password requirements");
    }

    @Test
    public void verifyErrorMessageAfterChangingPasswordWithoutUppercase() {
        ChangePasswordTab changePasswordTab = settingsView.openChangePasswordTab();
        changePasswordTab
                .enterOldPassword("Password7")
                .enterNewPassword("password7")
                .enterConfirmNewPassword("password7")
                .clickSubmitButton()
                .verifyErrorMessage("The new password does not meet the minimum password requirements");
    }

    @Test
    public void verifyErrorMessageAfterChangingPasswordWithNotEnoughCharacters() {
        ChangePasswordTab changePasswordTab = settingsView.openChangePasswordTab();
        changePasswordTab
                .enterOldPassword("Password7")
                .enterNewPassword("Word7")
                .enterConfirmNewPassword("Word7")
                .clickSubmitButton()
                .verifyErrorMessage("The new password does not meet the minimum password requirements");
    }

    @Test
    public void verifyErrorMessageAfterChangingPasswordWithoutDigitOrSpecialCharacter() {
        ChangePasswordTab changePasswordTab = settingsView.openChangePasswordTab();
        changePasswordTab
                .enterOldPassword("Password7")
                .enterNewPassword("Password")
                .enterConfirmNewPassword("Password")
                .clickSubmitButton()
                .verifyErrorMessage("The new password does not meet the minimum password requirements");
    }

    @Test
    public void verifyErrorMessageWhenNewPassDiffersFromConfirmNewPass() {
        ChangePasswordTab changePasswordTab = settingsView.openChangePasswordTab();
        changePasswordTab
                .enterOldPassword("Password7")
                .enterNewPassword("Password1")
                .enterConfirmNewPassword("Password")
                .clickSubmitButton()
                .verifyErrorMessage("The Confirmed Password doesn't match the New Password field");
    }

    @Test
    public void verifyErrorMessageWhenNewPasswordDoesntDifferFromPrevFive() {
        ChangePasswordTab changePasswordTab = settingsView.openChangePasswordTab();
        changePasswordTab
                .enterOldPassword("Password7")
                .enterNewPassword("Password6")
                .enterConfirmNewPassword("Password6")
                .clickSubmitButton()
                .verifyErrorMessage("Please enter a different password from your previous passwords.");
    }
}
