package ipreomobile.ui.activities;

import ipreomobile.core.Logger;
import ipreomobile.core.XPathBuilder;
import ipreomobile.data.ActivityData;
import ipreomobile.templates.ui.FullProfile;
import ipreomobile.templates.ui.SearchResultsTab;
import ipreomobile.ui.ScreenCard;
import ipreomobile.ui.search.CalendarSearchPanel;
import org.openqa.selenium.By;

public class ActivitySearchTab extends SearchResultsTab {

    @Override
    protected void setupProfileList() {
        qpl = new ActivityProfileList();
        super.setupProfileList();
    }

    @Override
    protected void setupProfileOverview() {
        profile = new ActivityDetailsOverview();
    }

    @Override
    public FullProfile openFullProfile(String name) {
        Logger.logMessage("Profile Summary is not available for Activities.");
        return null;
    }

    public ActivitySearchTab deleteActivityBySubject(String subject) {
        openProfileOverview(subject);
        return deleteSelectedActivity();
    }

    public ActivitySearchTab deleteSelectedActivity(){
        Logger.logDebugScreenshot("Initiated Delete Activity.");
        ((ActivityDetailsOverview)profile)
                .clickDeleteActivity()
                .clickButton("Delete");
        Logger.logDebugScreenshot("Clicked 'Delete' button and confirmed deleting.");
        ScreenCard deletedActivity = new ScreenCard();
        deletedActivity.addLoadingIndicatorCheckpoint();
        Logger.logDebugScreenshot("Initiated waitReady() for delete operation.");
        deletedActivity.waitReady();
        Logger.logDebugScreenshot("Initiated waitReady() for profile overview.");
        qpl.waitReady();
        return this;
    }

    public ActivitySearchTab deleteAllActivitiesFromSearchResults(){
        int activitiesNumber = getSearchResultsNumber();
        Logger.logWarning("All activities shown in current search results will be deleted.");
        while (activitiesNumber > 0){
            deleteSelectedActivity();
            openSearch();
            new CalendarSearchPanel().search();
            activitiesNumber = new ActivitySearchTab().getSearchResultsNumber();
        }
        Logger.logMessage("All activities shown in search results have been deleted.");
        return this;
    }

    public boolean isActivityUpcoming(String name) {
        return getProfileList().isActivityUpcoming(name);
    }

    public ActivityData getSelectedActivityData() {
        return getProfileList().getSelectedActivityData();
    }

    public ActivityData getActivityData(String activitySubject) {
        return getProfileList().getActivityData(activitySubject);
    }

    public String getActivityType(String activitySubject){
        return getProfileList().getActivityType(activitySubject);
    }

    public String getActivityDate(String activitySubject){
        return getProfileList().getActivityDate(activitySubject);
    }

    public String getActivityTime(String activitySubject){
        return getProfileList().getActivityTime(activitySubject);
    }

    public ActivitySearchTab verifySelectedActivitySubject(String expectedSubject) {
        getActiveProfileOverview().verifySubject(expectedSubject);
        return this;
    }

    public ActivitySearchTab verifySelectedActivitySubject(ActivityData activityData) {
        getActiveProfileOverview().verifySubject(activityData.getSubject());
        return this;
    }

    public ActivitySearchTab verifySelectedActivityType(String expectedType) {
        getActiveProfileOverview().verifyType(expectedType);
        return this;
    }

    public ActivitySearchTab verifySelectedActivityType(ActivityData activityData) {
        getActiveProfileOverview().verifyType(activityData.getType());
        return this;
    }

    public ActivitySearchTab verifySelectedActivityTimeSlot(String timeSlot) {
        getActiveProfileOverview().verifyTimeSlot(timeSlot);
        return this;
    }

    public ActivitySearchTab verifySelectedActivityTimeSlot(ActivityData activityData) {
        String timeSlot = activityData.getTimeSlot();
        getActiveProfileOverview().verifyTimeSlot(timeSlot);
        return this;
    }

    public ActivityDetailsOverview getActiveProfileOverview(){
        return (ActivityDetailsOverview)profile;
    }

    private ActivityProfileList getProfileList(){
        return (ActivityProfileList)qpl;
    }



}
