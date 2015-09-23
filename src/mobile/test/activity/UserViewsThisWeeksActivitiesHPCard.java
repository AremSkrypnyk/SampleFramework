package ipreomobile.test.activity;

import ipreomobile.core.DateTimeHelper;
import ipreomobile.core.Logger;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.activities.ActivitySearchTab;
import ipreomobile.ui.activities.CalendarTab;
import ipreomobile.ui.activities.GroupActivityOverlay;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserViewsThisWeeksActivitiesHPCard extends BaseTest {

    @BeforeClass
    public void cleanupActivitiesOnStart(){
        this.testCaseSetup();
        removeAllRelatedActivities();
        super.cleanup();
    }

    @AfterClass(alwaysRun = true)
    public void cleanupActivitiesOnEnd(){
        this.testCaseSetup();
        removeAllRelatedActivities();
        super.cleanup();
    }

    @Test()
    public void userHasNoActivitiesNext30Days(){
        String date = DateTimeHelper.addDaysToCurrentDate(31);
        String activitySubject = createActivity(date, null);

        navigation
                .searchActivities(activitySubject)
                .openProfileOverview(activitySubject);
        Logger.logScreenshot("Activity in 31 day");


        //TODO: verify that Get Started: Plan Your Meeting card is present
        //TODO: verify static card controls
        //TODO: verify we can create activity from this card
        //TODO: verify we can close it (next sprint)
    }

    @Test(dependsOnMethods = "userHasNoActivitiesNext30Days")
    public void userHasActivityNext30Days(){
        String date = DateTimeHelper.addDaysToCurrentDate(30);
        String activitySubject = createActivity(date, null);

        navigation
                .searchActivities(activitySubject)
                .openProfileOverview(activitySubject);
        Logger.logScreenshot("Activity in 30 days");

        //TODO: verify that get started card is absent
        //TODO: verify that this week's activities is absent

        removeAllRelatedActivities();
    }

    @Test(dependsOnMethods = "userHasActivityNext30Days")
    public void userHasActivityNextWeek(){
        String date = DateTimeHelper.getNextSaturday();
        String activitySubject = createActivity(date, null);

        navigation
                .searchActivities(activitySubject)
                .openProfileOverview(activitySubject);
        Logger.logScreenshot("Activity next Saturday");

        //TODO: verify that get started card is absent
        //TODO: verify that this week's activities is absent

    }

    @Test(dependsOnMethods = "userHasActivityNextWeek")
    public void userHasActivityThisWeek(){
        int minutesDelta = 20;
        String date = DateTimeHelper.getCurrentDateStr();
        String time = DateTimeHelper.getTimeAfterCurrent(minutesDelta);
        String activitySubject = createActivity(date, time);

        navigation
                .searchActivities(activitySubject)
                .openProfileOverview(activitySubject);
        Logger.logScreenshot("Activity this week (later today)");

        //TODO: verify that get started card is absent
        //TODO: verify that this week's activities is present
        //TODO: verify static card controls
        //TODO: verify the number of activities
        //TODO: verify we can open activity overview for any event
        //TODO: verify we can go to Calendar from this card
    }

    private String createActivity(String date, String time) {
        String subject = getCaseBasedName("Activity");
        String type = "Analyst Day";

        navigation.openCalendar();
        CalendarTab calendar = new CalendarTab();
        GroupActivityOverlay activityOverlay = calendar.addActivity();
        activityOverlay
                .setSubject(subject)
                .selectType(type);
        if (time == null) {
            activityOverlay
                    .setupDateTimeSlot()
                    .selectStartDateTime()
                    .selectDate(date)
                    .close();
        } else {
            activityOverlay
                    .setupDateTimeSlot()
                    .selectStartDateTime()
                    .selectDate(date)
                    .selectTime(time)
                    .close();
        }
        activityOverlay
                .setupDateTimeSlot()
                .selectEndDateTime()
                .selectDate(date)
                .close();
        activityOverlay.saveActivity();
        return subject;
    }


    private void removeAllRelatedActivities(){
        String startDate = DateTimeHelper.addDaysToCurrentDate(-10);
        String endDate = DateTimeHelper.addDaysToCurrentDate(35);
        navigation
                .searchActivities()
                .selectStartDate(startDate)
                .selectEndDate(endDate)
                .search();
        ActivitySearchTab searchTab = new ActivitySearchTab();
        searchTab.deleteAllActivitiesFromSearchResults();
        Logger.logScreenshot("Successfully removed all activities within ["+startDate+" - " + endDate + "]");
    }

//    private void removeAllActivitiesFromSearchResults(){
//        ActivitySearchTab searchResultsTab = new ActivitySearchTab();
//        int activitiesNumber = searchResultsTab.getSearchResultsNumber();
//
//        while (activitiesNumber > 0){
//            searchResultsTab
//                    .deleteSelectedActivity();
//            navigation
//                    .searchActivities()
//                    .search();
//            searchResultsTab = new ActivitySearchTab();
//            activitiesNumber = searchResultsTab.getSearchResultsNumber();
//        }
//    }

}
