package ipreomobile.test.activity;

import ipreomobile.core.DateTimeHelper;
import ipreomobile.core.Driver;
import ipreomobile.core.Logger;
import ipreomobile.data.ActivityData;
import ipreomobile.data.ContactData;
import ipreomobile.data.InstitutionData;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.hamburgerItems.Hamburger;
import ipreomobile.ui.activities.*;
import ipreomobile.ui.institutions.InstitutionFullProfile;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

/**
 * Created by Artem_Skrypnyk on 7/31/2014.
 */
public class AddActivity extends BaseTest {

    private InstitutionFullProfile institutionFullProfile;
    private InstitutionData institutionData;
    private ContactData contactData;
    private String contactList, contactFromList1, contactFromList2;
    private String searchFilterForContacts, contactName1, contactName2;
    private String institutionList, institutionFromList;
    private String searchFilterForInstitutions, institutionName1, institutionName2;

    @BeforeMethod
    public void setupTestData(Method testMethod){
        institutionData = new InstitutionData();
        contactData = new ContactData();
        institutionData.setTestCaseName(testMethod.getName());
        contactData.setTestCaseName(testMethod.getName());

        contactList = contactData.getListName();
        searchFilterForContacts = contactData.getLabels().get(0);

        contactData.loadDataSetByTag("contactFromList1");
        contactFromList1 = contactData.getName();
        contactData.loadDataSetByTag("contactFromList2");
        contactFromList2 = contactData.getName();
        contactData.loadDataSetByTag("contactFromSearch1");
        contactName1 = contactData.getName();
        contactData.loadDataSetByTag("contactFromSearch2");
        contactName2 = contactData.getName();

        institutionList = institutionData.getListName();
        searchFilterForInstitutions = institutionData.getLabels().get(0);
        institutionData.loadDataSetByTag("institutionFromList");
        institutionFromList = institutionData.getName();
        institutionData.loadDataSetByTag("institutionFromSearch1");
        institutionName1 = institutionData.getName();
        institutionData.loadDataSetByTag("institutionFromSearch2");
        institutionName2 = institutionData.getName();

    }

    @Test
    public void verifyAddIndividualActivityDialog() {
        String subject = getCaseAndDateBasedName("Activity");
        String comment = getCaseAndDateBasedName("Comment");

        String currentDate = DateTimeHelper.getCurrentDateStr();
        String currentTime = DateTimeHelper.getCurrentTimeStr();

        Hamburger hamburger = navigation.openHamburger();
        Driver.pause(1000);
        GroupActivityOverlay groupActivity = hamburger.addActivity();
        IndividualActivityOverlay individualActivity = groupActivity.switchToIndividualActivity();

        individualActivity
                .setSubject(subject)
                .selectType("Analyst Day")
                .selectTopic("Financials")
                .selectLocation("Maidan")
                .selectSymbols("GOOG", "Google, Inc. CL B")
                .selectMacroIndustry("Financials")
                .selectMidIndustry("Banking")
                .selectMicroIndustry("Banks");

        individualActivity
                .inputIndividualNotes(comment);

        individualActivity
                .addInstitutionsAsExternalParticipantsFromSearch(searchFilterForInstitutions, institutionName1, institutionName2)
                .addContactsAsExternalParticipantsFromOtherLists(contactList, contactFromList1, contactFromList2);

        individualActivity
                .setupDateTimeSlot()
                .selectStartDateTime()
                .selectYear(DateTimeHelper.getYearFromDateStr(currentDate))
                .selectMonthDay(DateTimeHelper.getMonthDayFromDateStr(currentDate))
                .selectHours(DateTimeHelper.getHourFromTimeStr(currentTime))
                .selectMinutes("10")
                .selectPeriodOfDay("AM")
                .close();

        individualActivity
                .setupDateTimeSlot()
                .selectEndDateTime()
                .selectDate(currentDate)
                .selectHours(DateTimeHelper.getHourFromTimeStr(currentTime))
                .selectMinutes("20")
                .selectPeriodOfDay("AM")
                .close();

        individualActivity
                .selectParticipant(institutionName2)
                .deselectParticipant(institutionName1)
                .checkDeclineParticipation(institutionName1)

                .saveActivity();

    }

    @Test
    public void verifyAddTaskDialog() {
        String subject = getCaseAndDateBasedName("Activity");
        String comment = getCaseAndDateBasedName("Comment");

        String currentDate = DateTimeHelper.getCurrentDateStr();
        String currentTime = DateTimeHelper.getCurrentTimeStr();

        Hamburger hamburger = navigation.openHamburger();
        Driver.pause(1000);
        GroupActivityOverlay groupActivity = hamburger.addActivity();
        TaskOverlay task = groupActivity.switchToTask();

        task
                .setSubject(subject)
                .selectType("Analyst Day")
                .setNotes(comment);

        task
                .addInstitutionsAsExternalParticipantsFromSearch(searchFilterForInstitutions, institutionName1, institutionName2)
                .addContactsAsExternalParticipantsFromOtherLists(contactList, contactFromList1, contactFromList2);

        task
                .setupDateTimeSlot()
                .selectStartDateTime()
                .selectYear(DateTimeHelper.getYearFromDateStr(currentDate))
                .selectMonthDay(DateTimeHelper.getMonthDayFromDateStr(currentDate))
                .selectHours(DateTimeHelper.getHourFromTimeStr(currentTime))
                .selectMinutes("10")
                .selectPeriodOfDay("AM")
                .close();

        task
                .setupDateTimeSlot()
                .selectEndDateTime()
                .selectDate(currentDate)
                .selectHours(DateTimeHelper.getHourFromTimeStr(currentTime))
                .selectMinutes("20")
                .selectPeriodOfDay("AM")
                .close();

        task
                .saveActivity();
    }

    @Test
    public void verifyGroupAddActivityDialog() throws InterruptedException {
        String subject = getCaseAndDateBasedName("Activity");
        String comment = getCaseAndDateBasedName("Comment");

        String currentDate = DateTimeHelper.getCurrentDateStr();
        String currentTime = DateTimeHelper.getCurrentTimeStr();

        Hamburger hamburger = navigation.openHamburger();
        Driver.pause(1000);
        GroupActivityOverlay groupActivity = hamburger.addActivity();
        Driver.pause(1000);
        groupActivity
                .setSubject(subject)
                .selectType("One-on-One")
                .setNotes(comment)

                .addContactsAsExternalParticipantsFromSearch(searchFilterForContacts, contactName1, contactName2)
                .checkParticipantAsOrganizer(contactName1)
                .checkAcceptParticipation(contactName2)
                .checkDeclineParticipation(contactName1)
                .addInstitutionsAsExternalParticipantsFromSearch(searchFilterForInstitutions, institutionName1, institutionName2);

        groupActivity
                .addInstitutionsAsExternalParticipantsFromMyLists(institutionList, institutionFromList);
        groupActivity
                .addContactsAsExternalParticipantsFromOtherLists(contactList, contactFromList1, contactFromList2);

        groupActivity
                .checkParticipantAsOrganizer(contactFromList2)
                .uncheckParticipantAsOrganizer(contactName1)
                .uncheckAcceptParticipation(contactFromList1)
                .uncheckDeclineParticipation(contactName1);

        groupActivity
                .selectTopic("Financials")
                .selectLocation("Maidan")
                .selectSymbols("GOOG", "Google, Inc. CL B");

        groupActivity
                .selectMacroIndustry("Financials")
                .selectMidIndustry("Banking")
                .selectMicroIndustry("Banks");

        groupActivity
                .setupDateTimeSlot()
                .selectStartDateTime()
                .selectYear(DateTimeHelper.getYearFromDateStr(currentDate))
                .selectMonthDay(DateTimeHelper.getMonthDayFromDateStr(currentDate))
                .selectHours(DateTimeHelper.getHourFromTimeStr(currentTime))
                .selectMinutes("10")
                .selectPeriodOfDay("AM")
                .close();
        groupActivity
                .setupDateTimeSlot()
                .selectEndDateTime()
                .selectDate(currentDate)
                .selectHours(DateTimeHelper.getHourFromTimeStr(currentTime))
                .selectMinutes("20")
                .selectPeriodOfDay("AM")
                .close();
        Driver.pause(1000);
        groupActivity.saveActivity();
        Driver.pause(5000);

        navigation.searchActivities(subject);
        ActivitySearchTab activitySearchTab = new ActivitySearchTab();
        activitySearchTab.openProfileOverview(subject);
        ActivityDetailsOverview activityOverview = activitySearchTab.getActiveProfileOverview();
        activityOverview
                .verifySubject(subject)
                .deleteActivity();

        navigation
                .searchActivities(subject)
                .verifyResultSetEmpty("Activity with subject ["+subject+"] has just been deleted and should not appear in Search Results.");

    }

    //TODO: update string date formatter for date verification
    @Test
    public void verifyCreatingActivitiesWithDifferentTimeSlots(){
        createActivityWithGivenTimeSlot("1:00 AM", "1:20 AM");
        createActivityWithGivenTimeSlot("1:05 AM", "1:25 AM");
        createActivityWithGivenTimeSlot("1:10 AM", "1:30 AM");
        createActivityWithGivenTimeSlot("1:15 AM", "1:35 AM");
        createActivityWithGivenTimeSlot("1:20 AM", "1:40 AM");
        createActivityWithGivenTimeSlot("1:25 AM", "1:45 AM");
        createActivityWithGivenTimeSlot("1:30 AM", "1:50 AM");
        createActivityWithGivenTimeSlot("1:35 AM", "1:55 AM");
        createActivityWithGivenTimeSlot("1:40 AM", "2:00 AM");
        createActivityWithGivenTimeSlot("1:45 AM", "2:05 AM");
        createActivityWithGivenTimeSlot("1:50 AM", "2:10 AM");
        createActivityWithGivenTimeSlot("1:55 AM", "2:15 AM");
    }

    private void createActivityWithGivenTimeSlot(String startTime, String endTime) {
        ActivityData activity = new ActivityData();
        activity.clear();
        activity.setSubject( getCaseAndDateTimeBasedName("Action") );
        activity.setType("Analyst Day");

        String currentDate = DateTimeHelper.getCurrentDateStr();
        activity.setStartDate(currentDate);
        activity.setEndDate(currentDate);

        activity.setStartTime(startTime);
        activity.setEndTime(endTime);
        Logger.logMessage("Time slot chosen for activity: " + activity.getTimeSlot());
        navigation.openHamburger();
        Driver.pauseWebVersion(1000);
        ActivityOverlay overlay = new Hamburger().addActivity();
        Driver.pause(1000);

        overlay .setSubject(activity.getSubject())
                .selectType(        activity.getType())
                .selectStartDateTime(activity.getStartDateTime())
                .verifyStartDateTime(activity.getStartDateTime())
                .selectEndDateTime(activity.getEndDateTime())
                .verifyEndDateTime(activity.getEndDateTime())
                .saveActivity();
        navigation.searchActivities(activity.getSubject());
        ActivitySearchTab searchTab = new ActivitySearchTab();
        searchTab.openProfileOverview(activity.getSubject());
        searchTab.verifySelectedActivityTimeSlot(activity);

    }
}
