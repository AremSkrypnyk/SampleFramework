package ipreomobile.ui.homePage;

import ipreomobile.core.Driver;
import ipreomobile.core.SenchaWebElement;
import ipreomobile.templates.ui.SingleSelectListImpl;
import ipreomobile.ui.activities.ActivityDetailsOverview;
import ipreomobile.ui.activities.CalendarTab;
import org.openqa.selenium.By;
import org.testng.Assert;

public class ThisWeeksActivitiesCard extends SingleSelectListImpl {
    private static final By     WEEKS_ACTIVITIES_CARD_CONTAINER_LOCATOR = null;
    private static final By     UPCOMING_ACTIVITIES_BUTTON_LOCATOR      = null;
    private static final String ITEM_XPATH    = null;
    private static final String GET_ITEM_BY_INDEX_TEMPLATE = null;
    private static final String ITEM_NAME_XPATH = null;

    public ThisWeeksActivitiesCard() {
        addCheckpointElement(WEEKS_ACTIVITIES_CARD_CONTAINER_LOCATOR);
        waitReady();
        setItemsXpath(ITEM_XPATH);
        setItemNameXpath(ITEM_NAME_XPATH);
    }

    public ActivityDetailsOverview selectActivityBySubject(String subject) {
        select(subject);
        return new ActivityDetailsOverview();
    }

    public ActivityDetailsOverview selectActivityByNumber(int number) {
        String activityXpath = String.format(GET_ITEM_BY_INDEX_TEMPLATE, "" + number);
        SenchaWebElement item = getItemByXpath(activityXpath);

        Assert.assertNotNull(item, "No activity was found by following number: [" + number + "].");
        click(item);
        waitItemSelected(item);
        return new ActivityDetailsOverview();
    }

    public CalendarTab viewAllUpcomingActivities() {
        Assert.assertNotNull(UPCOMING_ACTIVITIES_BUTTON_LOCATOR, "Upcoming Activities button was not found.");
        Driver.findVisible(UPCOMING_ACTIVITIES_BUTTON_LOCATOR).click();
        return new CalendarTab();
    }
}
