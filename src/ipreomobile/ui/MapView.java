package ipreomobile.ui;

import ipreomobile.core.Driver;
import ipreomobile.core.Logger;
import ipreomobile.core.Verify;
import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.blocks.ConfirmationDialog;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

public class MapView extends ScreenCard {
    private static final By START_FIELD_SELECTOR = By.xpath(new XPathBuilder().byPlaceholder("Start").build());
    private static final By END_FIELD_SELECTOR = By.xpath(new XPathBuilder().byPlaceholder("End").build());

    private static final By START_TARGET_BUTTON_SELECTOR = By.xpath(new XPathBuilder().byClassName("target").byIndex(1).build());
    private static final By END_TARGET_BUTTON_SELECTOR = By.xpath(new XPathBuilder().byClassName("target").byIndex(2).build());

    private static final By DIRECTIONS_SELECTOR = By.className("get-directions");
    private static final By DONE_BUTTON_SELECTOR = By.xpath(new XPathBuilder().byText("Done").build());

    private static final By LOCATION_NAME_SELECTOR = By.xpath(new XPathBuilder().byClassName("info-content").byClassName("info-text").build());
    private static final By LOCATION_INFO_SELECTOR = By.xpath(new XPathBuilder().byClassName("info-content").byClassName("details-icon").build());

    private static final By MAP_SELECTOR = By.xpath(new XPathBuilder().byClassName("MicrosoftMap", "large").build());
    private static final By TITLE_SELECTOR = By.xpath(new XPathBuilder().byClassName("view-on-map")
            .byClassName("x-toolbar")
            .byClassName("x-title")
            .build());

    private static final By MESSAGE_BOX_LOCATOR = By.xpath(new XPathBuilder()
            .byClassName("x-msgbox")
            .build()
    );

    private Boolean confirmationDialogShown = false;

    public MapView(){
        addLoadingIndicatorCheckpoint();
        waitReady();

        Driver.saveTimeout();
        Driver.setTimeout(1);
       SenchaWebElement messageBox = Driver.findVisibleNow(MESSAGE_BOX_LOCATOR);
        Driver.restoreSavedTimeout();

        if (messageBox == null) {
            addCheckpointElement(MAP_SELECTOR).mustBeVisible();
            addCheckpointElement(LOCATION_NAME_SELECTOR).mustBeVisible();
            addCheckpointElement(DONE_BUTTON_SELECTOR).mustBeVisible();
            setAnimationTimeout(1);
            waitReady();
        } else {
            confirmationDialogShown = true;
        }
    }

    public MapView verifyPageTitle(String expectedTitle) {
        Assert.assertEquals(getPageTitle().toLowerCase(), expectedTitle.toLowerCase(), "Map title mismatch: ");
        return this;
    }

    public String getPageTitle(){
        return Driver.findVisible(TITLE_SELECTOR).getText().trim();
    }

    public MapView verifyLocationName(String expectedName) {
        Verify.verifyEquals(getLocationName(), expectedName, "Location name mismatch: ");
        return this;
    }

    public String getLocationName(){
        return Driver.findVisible(LOCATION_NAME_SELECTOR).getText().trim();
    }

    public void close(){
        done();
    }

    public void done() {
        if (confirmationDialogShown) {
            new ConfirmationDialog().clickOk();
        }
       SenchaWebElement doneButton = getDoneButton();
        if (doneButton != null) {
            doneButton.click();
        } else {
            Logger.logError("Done button is not available on this screen.");
        }
        new GlobalNavigation().waitApplicationReady();
    }

    private SenchaWebElement getDoneButton(){
       SenchaWebElement button = Driver.findVisibleNow(DONE_BUTTON_SELECTOR);
        return button;
    }
}
