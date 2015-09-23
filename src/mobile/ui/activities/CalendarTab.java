package ipreomobile.ui.activities;

import ipreomobile.core.Driver;
import ipreomobile.templates.ui.TwoPane;
import ipreomobile.test.activity.AddActivity;
import org.openqa.selenium.By;

public class CalendarTab extends TwoPane {

    private static final By ADD_ACTIVITY_SELECTOR = By.className("add-event");

    public CalendarTab() {
        addCheckpointElement(ADD_ACTIVITY_SELECTOR).mustBeVisible();
        waitReady();
    }

    public GroupActivityOverlay addActivity(){
        Driver.findVisible(ADD_ACTIVITY_SELECTOR).click();
        return new GroupActivityOverlay();
    }

    @Override
    protected void setupProfileList() {

    }

    @Override
    protected void setupProfileOverview() {

    }


}
