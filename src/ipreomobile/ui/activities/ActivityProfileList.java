package ipreomobile.ui.activities;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.data.ActivityData;
import ipreomobile.templates.ui.QuickProfileList;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActivityProfileList extends QuickProfileList {

    private static final By ACTIVITY_AVAILABLE_LOCATOR = By.xpath(new XPathBuilder().byCurrentItem().byClassName("available").build());

    private static final int ACTIVITY_TYPE_INDEX = 1;
    private static final int ACTIVITY_DATE_INDEX = 2;
    private static final int ACTIVITY_TIME_INDEX = 3;

    private static final Pattern ACTIVITY_DETAILS_PATTERN = Pattern.compile("([A-Za-z\\s]*)\\s+([0-9/]*)\\s+(.*)");

    public boolean isActivityUpcoming(String name) {
       SenchaWebElement item = getItem(name);
        return (Driver.findIfExists(ACTIVITY_AVAILABLE_LOCATOR, item) != null);
    }

    public ActivityData getSelectedActivityData() {
        String itemName = getSelectedItemName();
        return getActivityData(itemName);
    }

    public ActivityData getActivityData(String activitySubject) {
        ActivityData data = new ActivityData();
        data.clear();
        data.setSubject(activitySubject);

        String subtext = getItemSubtext(activitySubject);
        Matcher m = ACTIVITY_DETAILS_PATTERN.matcher(subtext);
        if (m.matches()) {
            data.setType(m.group(ACTIVITY_TYPE_INDEX));
            data.setStartDate(m.group(ACTIVITY_DATE_INDEX));
            data.setStartTime(m.group(ACTIVITY_TIME_INDEX));
        }
        return data;
    }

    public String getActivityType(String activitySubject){
        String subtext = getItemSubtext(activitySubject);
        return ACTIVITY_DETAILS_PATTERN.matcher(subtext).group(ACTIVITY_TYPE_INDEX);
    }

    public String getActivityDate(String activitySubject){
        String subtext = getItemSubtext(activitySubject);
        return ACTIVITY_DETAILS_PATTERN.matcher(subtext).group(ACTIVITY_DATE_INDEX);
    }

    public String getActivityTime(String activitySubject){
        String subtext = getItemSubtext(activitySubject);
        return ACTIVITY_DETAILS_PATTERN.matcher(subtext).group(ACTIVITY_TIME_INDEX);
    }

}
