package ipreomobile.test.activity;

import ipreomobile.core.DateTimeHelper;
import ipreomobile.core.Logger;
import ipreomobile.core.StringHelper;
import ipreomobile.data.ActivityData;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.activities.ActivitySearchTab;
import org.testng.annotations.*;

import java.lang.reflect.Method;

public class UserViewsQuickAddActivityHPCard extends BaseTest {

    private ActivityData activityData;
    private String activitySubject;
    private String suiteName;

    @BeforeMethod
    public void setupTestData(Method testMethod){
        activityData = new ActivityData();
        activityData.setTestCaseName(testMethod.getName());

        activityData.setStartDate(DateTimeHelper.getCurrentDateStr() );
        activityData.setEndDate(DateTimeHelper.getCurrentDateStr());

        String time = DateTimeHelper.getRoundedTimeStr( DateTimeHelper.getTimeAfterCurrent(30) );
        activityData.setStartTime(time);

        suiteName = StringHelper.splitByCapitals(this.getClass().getSimpleName());
    }

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

    @Test
    public void verifyAddActivityCardControls(){
        activitySubject = suiteName + getCaseBasedName("Activity");
        //TODO: verify header
        //TODO: expand/collapse/expand again
        //TODO: verify 'save' button is unavailable without Type
        //TODO: open selection overlays, verify titles
        //TODO: select Type, verify populated value,
        //TODO: verify 'Save' button appears
        //TODO: select date, verify populated value

        //TODO: save activity
        //TODO: verify 'Success' banner
        //TODO: verify all fields are empty
        //TODO: verify 'Save' button disappears
    }

    @Test
    public void verifyDiscardActivityPrompt(){
        //TODO: populate all fields
        //TODO: navigate to Institutions
        //TODO: verify prompt, decline navigation
        //TODO: verify fields remain populated
        //TODO: navigate to Institutions
        //TODO: select 'Discard' button
        //TODO: verify navigation was performed
        //TODO: back to Home Page
        //TODO: verify Quick Add Activity fields empty
    }

    @Test(dependsOnMethods = "verifyAddActivityCardControls")
    public void addOneContactTwice(){}

    @Test(dependsOnMethods = "verifyAddActivityCardControls")
    public void addMultipleContacts(){}

    @Test(dependsOnMethods = "verifyAddActivityCardControls")
    public void navigateToFullAddActivity(){}

    private void removeAllRelatedActivities(){
        String subjectFilter = suiteName;
        navigation
                .searchActivities()
                .setSubject(subjectFilter)
                .search();
        ActivitySearchTab searchTab = new ActivitySearchTab();
        searchTab.deleteAllActivitiesFromSearchResults();
        Logger.logScreenshot("Successfully removed all activities with subject starting with [" + suiteName + "]");
    }

}
