package ipreomobile.ui.blocks;

import ipreomobile.core.Driver;
import ipreomobile.core.Logger;
import ipreomobile.core.SenchaWebElement;
import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.ScreenCard;
import org.openqa.selenium.By;
import org.testng.Assert;

/**
 * Created by Artem_Skrypnyk on 9/29/2014.
 */
public class StatusIndicator extends ScreenCard {

    private static final int MAX_WAIT_TIMEOUT = 360;
    private static final String OFFLINE_MODE_TEXT = "OFFLINE MODE";
    private static final String UPDATE_ERROR_TEXT = "UPDATE ERROR";

    private static final By STATUS_INDICATOR_LOCATOR = By.className("status-indicator");
    private static final By STATUS_INDICATOR_TEXT_LOCATOR = By.className("status-indicator-text");
    private static final By UPDATE_ERROR_LOCATOR = By.xpath(new XPathBuilder()
            .byClassName("status-indicator").withChildTextIgnoreCase(UPDATE_ERROR_TEXT).build());

    public static boolean isStatusIndicatorDisplayed(){
        return Driver.isElementVisible(STATUS_INDICATOR_LOCATOR) && !getStatusIndicatorText().isEmpty();
    }

    public static String getStatusIndicatorText(){
        SenchaWebElement statusIndicatorText = Driver.findVisible(STATUS_INDICATOR_TEXT_LOCATOR);
        return (statusIndicatorText == null) ? "" : statusIndicatorText.getText();
    }

    public static boolean isOfflineModeEnabled(){
        return (isStatusIndicatorDisplayed()) &&
                (getStatusIndicatorText().equalsIgnoreCase(OFFLINE_MODE_TEXT));
    }

    public static boolean isUpdateErrorShown(){
        return (isStatusIndicatorDisplayed()) &&
                (getStatusIndicatorText().equalsIgnoreCase(UPDATE_ERROR_TEXT));
    }

    public StatusIndicator verifyOfflineModeEnabled(){
        Assert.assertTrue(isOfflineModeEnabled(), "Status indicator is not indicating offline mode!");
        return this;
    }

    public StatusIndicator verifyOnlineModeEnabled(){
        Assert.assertTrue(!isOfflineModeEnabled(), "Status indicator is indicating offline mode!");
        return this;
    }

    public StatusIndicator waitForUpdatingIsDone(){
        ScreenCard updatingIndicator = new ScreenCard();
        Logger.logMessage("Further testing requires update process to be finished. It can take up to ["+MAX_WAIT_TIMEOUT+" seconds].");
        updatingIndicator.setMaxWaitTimeout(MAX_WAIT_TIMEOUT);
        if (isStatusIndicatorDisplayed() && !getStatusIndicatorText().equalsIgnoreCase(OFFLINE_MODE_TEXT)) {
            updatingIndicator
                    .addCheckpointElement(STATUS_INDICATOR_LOCATOR)
                    .addVisibilityCondition(false);
            updatingIndicator
                    .addForceStopCheckpointElement(UPDATE_ERROR_LOCATOR)
                    .addVisibilityCondition(true);
        }
        updatingIndicator.waitReady();
        return this;
    }


}
