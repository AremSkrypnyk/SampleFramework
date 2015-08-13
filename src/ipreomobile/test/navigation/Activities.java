package ipreomobile.test.navigation;

import ipreomobile.core.DateTimeHelper;
import ipreomobile.data.ActivityData;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.activities.ActivitySearchTab;
import ipreomobile.ui.search.CalendarSearchPanel;
import org.testng.annotations.Test;

public class Activities extends BaseTest {

    CalendarSearchPanel searchPanel;
    ActivityData data;

    @Test
    public void verifyDatePickerOnActivitySearchPanel(){
        String earlierDate  = "01/01/2010";
        String middleDate   = "06/06/2012";
        String laterDate    = "12/31/2014";
        String testDescription;

        navigation.searchActivities();
        searchPanel = new CalendarSearchPanel();

        searchPanel.selectStartDate(earlierDate);
        searchPanel.selectEndDate(middleDate);

        testDescription = "Start and end date must be set now.";

        searchPanel.verifyStartDate(earlierDate, testDescription);
        searchPanel.verifyEndDate(middleDate, testDescription);

        testDescription = "Setting activity Start Date after End Date. End Date must be updated automatically.";

        searchPanel.selectStartDate(laterDate);
        searchPanel.verifyEndDate(laterDate, testDescription);

        testDescription = "Setting activity End Date before Start Date. Start Date must be updated automatically.";

        searchPanel.selectEndDate(middleDate);
        searchPanel.verifyStartDate(middleDate, testDescription);

        testDescription = "Resetting date. Date field must become empty.";

        searchPanel.selectStartDate().reset();
        searchPanel.verifyStartDate("", testDescription);
        searchPanel.verifyEndDate(middleDate, testDescription);

        testDescription = "Cancel date selection.";

        String newYear = DateTimeHelper.getYearFromDateStr(earlierDate);
        searchPanel.selectEndDate().selectYear(newYear).cancel();
        searchPanel.verifyEndDate(middleDate, testDescription);

    }

    @Test
    //TODO: null pointer
    public void searchActivityByDate(){
        data = new ActivityData();
        String activitySubject = data.getSubject();
        String activityDate = data.getStartDate();

        navigation.searchActivities();
        searchPanel = new CalendarSearchPanel();
        searchPanel.selectStartDate().selectDate(activityDate);
        searchPanel.selectEndDate().selectDate(activityDate);
        searchPanel.search();

        ActivitySearchTab searchTab = new ActivitySearchTab();
        searchTab.openProfileOverview(activitySubject);
        searchTab.verifyProfileNameSelectedInList(activitySubject);
    }

}
