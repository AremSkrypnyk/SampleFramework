package ipreomobile.ui.blocks;

import ipreomobile.core.Driver;
import ipreomobile.core.SenchaWebElement;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.BaseOverlay;
import ipreomobile.ui.ScreenCard;
import org.openqa.selenium.By;
import org.testng.Assert;

public class ConfirmationDialog extends ScreenCard {
// TODO: redesign class to support native confirmation dialogs for different platforms
//    private static final String YES_BUTTON_XPATH                                = new XPathBuilder().byClassName("x-button-label").withText("Yes").build();
//    private static final String NO_BUTTON_XPATH                                 = new XPathBuilder().byClassName("x-button-label").withText("No").build();
//    private static final String OK_BUTTON_XPATH                                = new XPathBuilder().byClassName("x-button-label").withText("Ok").build();


    private static final String CONFIRMATION_DIALOG_CLASSNAME   = "x-msgbox";
    private static final String OUTSIDE_OF_THE_APPLICATION_TEXT = "This action will take you outside of the ipreomobile application. Do you wish to proceed?";

    private static final String OK_TEXT = "OK";
    private static final String YES_TEXT = "Yes";
    private static final String NO_TEXT = "No";

    private static final By CONTAINER_LOCATOR                   = By.className(CONFIRMATION_DIALOG_CLASSNAME);
    private static final By CONFIRMATION_DIALOG_TEXT_LOCATOR    = By.className("x-msgbox-text");
    private static final String BUTTON_XPATH = new XPathBuilder()
            .byText("%s")
            .build();

    private SenchaWebElement container;


    public ConfirmationDialog(){
        addCheckpointElement(By.className(CONFIRMATION_DIALOG_CLASSNAME))
                .mustBeVisible();
        addCheckpointElement(CONTAINER_LOCATOR)
                .mustBeVisible();
        setAnimationTimeout(0.5);
        waitReady();
        container = Driver.findVisible(CONTAINER_LOCATOR);
    }

    public ConfirmationDialog verifyShown(){
        Assert.assertNotNull(container, "No confirmation dialog was found on the screen.");
        return this;
    }

    public void clickYes(){
        clickButton(YES_TEXT);
    }

    public void clickNo(){
        clickButton(NO_TEXT);
    }

    public void clickOk(){
        clickButton(OK_TEXT);
    }

    public String getConfirmationDialogText(){
        return Driver.findVisible(CONFIRMATION_DIALOG_TEXT_LOCATOR, container).getText().trim();
    }

    public void verifyMessage(String expectedMessage) {
        Assert.assertEquals(getConfirmationDialogText(), expectedMessage, "Confirmation dialog message mismatch: ");
    }

    public void verify3rdPartyAppMessage() {
//        Assert.assertEquals(getConfirmationDialogText(), OUTSIDE_OF_THE_APPLICATION_TEXT); // TODO: update assertion to support native confirmation dialog
        clickNo();
    }

    public void clickButton(String buttonText) {
        By buttonLocator = By.xpath(String.format(BUTTON_XPATH, buttonText));
        By activeMaskLocator = BaseOverlay.getActiveMaskLocator();

        Driver.findVisibleNow(buttonLocator, container).click();
        resetCheckpointElements();
        addCheckpointElement(buttonLocator)
                .addInvisibleOrAbsentCondition();
        waitReady();

        BaseOverlay.waitMaskHidden(activeMaskLocator);
    }
}
