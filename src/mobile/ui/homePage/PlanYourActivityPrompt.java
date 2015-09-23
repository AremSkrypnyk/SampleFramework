package ipreomobile.ui.homePage;

import ipreomobile.core.Driver;
import ipreomobile.ui.ScreenCard;
import ipreomobile.ui.activities.GroupActivityOverlay;
import org.openqa.selenium.By;
import org.testng.Assert;

public class PlanYourActivityPrompt extends ScreenCard {
    private static final By PLAN_YOUR_ACTIVITY_CONTAINER_LOCATOR = null;
    private static final By ADD_IT_BUTTON_LOCATOR = null;

    public PlanYourActivityPrompt() {
        addCheckpointElement(PLAN_YOUR_ACTIVITY_CONTAINER_LOCATOR);
        waitReady();
    }

    public GroupActivityOverlay clickAddIt() {
        Assert.assertNotNull(ADD_IT_BUTTON_LOCATOR, "Add It! button was not found.");
        Driver.findVisible(ADD_IT_BUTTON_LOCATOR).click();
        return new GroupActivityOverlay();
    }

    public void close() {
        // TODO: implement
    }
}
