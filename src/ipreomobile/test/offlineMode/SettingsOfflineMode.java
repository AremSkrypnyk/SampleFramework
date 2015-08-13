package ipreomobile.test.offlineMode;

import ipreomobile.data.ActivityData;
import ipreomobile.data.ContactData;
import ipreomobile.data.InstitutionData;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.blocks.StatusIndicator;
import ipreomobile.ui.contacts.ActivityTab;
import ipreomobile.ui.contacts.ContactRecentlyViewedTab;
import ipreomobile.ui.institutions.InstitutionRecentlyViewedTab;
import ipreomobile.ui.institutions.InvestorsTab;
import ipreomobile.ui.settings.OfflineModeTab;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class SettingsOfflineMode extends BaseTest {

    //Test pages
    private OfflineModeTab offlineModeTab;
    private ActivityTab activityTab;
    private InvestorsTab investorsTab;
    private InstitutionRecentlyViewedTab institutionRecentlyViewedTab;
    private ContactRecentlyViewedTab contactRecentlyViewedTab;

    //Test data
    private ContactData contactData;
    private String contactName;

    private InstitutionData institutionData;
    private String institutionName;

    private ActivityData activityData;
    private String activitySubject;




    private static final String STORE_ACTIVITIES_FROM = "2 Months Ago";
    private static final String STORE_ACTIVITIES_TO = "The End of This Month";

    @BeforeMethod
    public void setupTestData(Method m){

        contactData = new ContactData();
        contactData.setTestCaseName(m.getName());
        contactName = contactData.getName();

        institutionData = new InstitutionData();
        institutionData.setTestCaseName(m.getName());
        institutionName = institutionData.getName();

        activityData = new ActivityData();
        activityData.setTestCaseName(m.getName());
        activitySubject = activityData.getSubject();

        offlineModeTab = (OfflineModeTab)navigation
                .openHamburger()
                .openSettings()
                .select(UITitles.SettingsTab.OFFLINE_MODE);

    }

    //226518
    @Test
    public void verifyControlsOnOfflineModeTab(){
        offlineModeTab
                .verifyCalendarActivityAndRelatedProfileStorageTitle()
                .verifyStoreActivitiesToggle()
                .verifyPreviouslyViewedProfileStorageTitle()
                .verifyPreviouslyViewedProfileStorageLastViewedField()
                .verifyStorageOnYourDeviceTitle()
                .verifyStorageOnYourDeviceTotalUsedField()
                .verifyClearedStoredDataButton()
                .verifyResetToDefaultsButton();
    }

    @Test
    public void verifyCalendarActivityAndRelatedProfileStorage(){

        offlineModeTab
                .turnOffStoringActivities();

        new StatusIndicator()
                .waitForUpdatingIsDone();

        navigation
                .switchToOfflineMode()
                .openCalendar();

        //TODO Add Calendar available items verification

        navigation.openContacts();
        activityTab = new ActivityTab();
        Assert.assertFalse(activityTab.isProfileUnavailableInOfflineMode(contactName),
                "Profile with " + contactName + " name expected to be available.");

        offlineModeTab = (OfflineModeTab)navigation
                .switchToOnlineMode()
                .openHamburger()
                .openSettings()
                .select(UITitles.SettingsTab.OFFLINE_MODE);

        offlineModeTab
                .selectStoreActivitiesFrom(STORE_ACTIVITIES_FROM)
                .selectStoreActivitiesTo(STORE_ACTIVITIES_TO);

        navigation.openContacts();
        activityTab = new ActivityTab();
        Assert.assertFalse(activityTab.isProfileUnavailableInOfflineMode(contactName),
                "Profile with " + contactName + " name expected to be available.");

        new StatusIndicator()
                .waitForUpdatingIsDone();

        navigation.switchToOfflineMode();

        Assert.assertFalse(activityTab.isProfileUnavailableInOfflineMode(contactName),
                "Profile with " + contactName + " name expected to be available.");
    }

    @Test
    public void verifyCachedActivitiesData(){

        offlineModeTab.turnOnStoringActivities();

        navigation.openCalendar();

        new StatusIndicator().waitForUpdatingIsDone();

        navigation.switchToOfflineMode();

        //TODO Add Calendar available items verification

        navigation.switchToOnlineMode();

        offlineModeTab = (OfflineModeTab)navigation
                .openHamburger()
                .openSettings()
                .select(UITitles.SettingsTab.OFFLINE_MODE);

        offlineModeTab
                .clearStoredData()
                .resetToDefaults()
                .turnOffStoringActivities();

        navigation.openContacts();

        activityTab = new ActivityTab();
        activityTab.openProfileOverview(contactName);

        new StatusIndicator().waitForUpdatingIsDone();

        navigation.switchToOfflineMode();

        navigation.openInstitutions();

        verifyFirst10InvestorsAvailable();

        navigation.openContacts();

        activityTab = new ActivityTab();

        Assert.assertFalse(activityTab.isProfileUnavailableInOfflineMode(contactName), "Contact with " + contactName + " name expected to be available");

        navigation.openInstitutions();

        investorsTab = new InvestorsTab();

        Assert.assertFalse(investorsTab.isProfileUnavailableInOfflineMode(institutionName));

        navigation.selectRecentlyViewedTab();

        institutionRecentlyViewedTab = new InstitutionRecentlyViewedTab();
        institutionRecentlyViewedTab.verifyItemPresentInList(institutionName);

        navigation
                .openContacts()
                .selectRecentlyViewedTab();

        contactRecentlyViewedTab = new ContactRecentlyViewedTab();
        contactRecentlyViewedTab.verifyItemPresentInList(contactName);
    }

    private void verifyFirst10InvestorsAvailable(){
        investorsTab = new InvestorsTab();

        String currentInvestor = investorsTab.getProfileNameSelectedInList();

        for (int i = 0; i < 10; i++) {
            Assert.assertFalse(investorsTab.isProfileUnavailableInOfflineMode(currentInvestor), "Investor with " + currentInvestor + " name expected to be available");

            currentInvestor = investorsTab
                    .openNextProfileOverview()
                    .getProfileNameSelectedInList();
            i++;
        }
    }
}
