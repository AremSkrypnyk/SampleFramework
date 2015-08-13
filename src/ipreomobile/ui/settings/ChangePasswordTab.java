package ipreomobile.ui.settings;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.ScreenCard;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

public class ChangePasswordTab extends ScreenCard {
    private static final By INSTRUCTIONS_LOCATOR = By.className("instructions");
    private static final By ERROR_MESSAGE_LOCATOR = By.className("error");
    private static final By SUBMIT_BUTTON_LOCATOR = By.className("submit-btn");

    private static final String INPUT_PASSWORD_XPATH = new XPathBuilder()
            .byClassName("input-box")
            .withChildText("%s")
            .byClassName("x-input-password").build();
    private static final String[] PASSWORD_INPUT_BOX_NAMES = {
            "Old Password",
            "New Password",
            "Confirm New Password",
    };

    public ChangePasswordTab() {
        addCheckpointElement(INSTRUCTIONS_LOCATOR);
        waitReady();
    }

    public ChangePasswordTab verifyInputPasswordFieldsPresence() {
        Driver.saveTimeout();
        Driver.nullifyTimeout();
        for (int i = 0; i < PASSWORD_INPUT_BOX_NAMES.length; i++) {
           SenchaWebElement inputBox = Driver.findIfExists(By.xpath(String.format(INPUT_PASSWORD_XPATH, PASSWORD_INPUT_BOX_NAMES[i])));
            Assert.assertNotNull(inputBox, "Couldn't find element with text '" + PASSWORD_INPUT_BOX_NAMES[i] + "'.");
        }
        Driver.resetTimeout();

        return this;
    }

    public ChangePasswordTab verifyInstructionText() {
        String[] instructionText = {
                "Your new password must be:",
                "At least 7 characters",
                "1 lower case",
                "1 upper case",
                "1 digit or special character",
                "The new password must be different from your last 5 passwords"
        };

        Driver.saveTimeout();
        Driver.nullifyTimeout();
        for (int i = 0; i < instructionText.length; i++) {
            Driver.verifyExactTextPresentAndVisible(instructionText[i]);
        }
        Driver.resetTimeout();

        return this;
    }

    public ChangePasswordTab enterOldPassword(String oldPass) {
       SenchaWebElement inputBox;

        inputBox = Driver.findIfExists(By.xpath(String.format(INPUT_PASSWORD_XPATH, PASSWORD_INPUT_BOX_NAMES[0])));
        inputBox.clear();
        inputBox.sendKeys(oldPass);

        return new ChangePasswordTab();
    }

    public ChangePasswordTab enterNewPassword(String newPass) {
       SenchaWebElement inputBox;

        inputBox = Driver.findIfExists(By.xpath(String.format(INPUT_PASSWORD_XPATH, PASSWORD_INPUT_BOX_NAMES[1])));
        inputBox.clear();
        inputBox.sendKeys(newPass);

        return new ChangePasswordTab();
    }

    public ChangePasswordTab enterConfirmNewPassword(String confirmNewPass) {
       SenchaWebElement inputBox;

        inputBox = Driver.findIfExists(By.xpath(String.format(INPUT_PASSWORD_XPATH, PASSWORD_INPUT_BOX_NAMES[2])));
        inputBox.clear();
        inputBox.sendKeys(confirmNewPass);

        return new ChangePasswordTab();
    }

    public ChangePasswordTab clickSubmitButton() {
        Driver.findIfExists(SUBMIT_BUTTON_LOCATOR).click();
        return new ChangePasswordTab();
    }

    public ChangePasswordTab verifyErrorMessage(String expectedErrorMessage) {
        Assert.assertEquals(Driver.findIfExists(ERROR_MESSAGE_LOCATOR).getText(), expectedErrorMessage, "Error message is not as expected.");
        return new ChangePasswordTab();
    }
}
