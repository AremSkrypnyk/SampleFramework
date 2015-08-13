package ipreomobile.test.offlineMode;

import ipreomobile.data.ContactData;
import ipreomobile.data.FundData;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.hamburgerItems.Hamburger;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.contacts.ContactFullProfile;
import ipreomobile.ui.contacts.ContactRecentlyViewedTab;
import ipreomobile.ui.contacts.ContactSearchTab;
import ipreomobile.ui.hamburgerItems.SettingsView;
import ipreomobile.ui.profiles.fullProfileTabs.ContactActionsProfileTab;
import ipreomobile.ui.profiles.fullProfileTabs.ContactFocusProfileTab;
import ipreomobile.ui.profiles.fullProfileTabs.ContactFundsProfileTab;
import ipreomobile.ui.settings.OfflineModeTab;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Artem_Skrypnyk on 10/9/2014.
 */
public class ContactsInOfflineMode extends BaseTest {

    private ContactData contactData;
    private String contactName;

    private ContactRecentlyViewedTab contactRecentlyViewedTab;
    private ContactFullProfile contactFullProfile;

    private static final String UNAVAILABLE_PROFILE_MESSAGE = "This Profile is unavailable to you while in Offline Mode.";
    private static final String UNAVAILABLE_SEARCHING_MESSAGE = "Searching within a profile tab is disabled in Offline Mode.";
    private static final String UNAVAILABLE_TAB_MESSAGE = "This tab is not available in Offline Mode. More information may be available online.";
    private static final String UNAVAILABLE_LINK_MESSAGE = "This Link is unavailable to you while in Offline Mode";
    private static final String UNAVAILABLE_FEATURE_MESSAGE = "This Feature is disabled in Offline Mode.";

    @BeforeMethod
    private void setupTestData(Method m){
        contactData = new ContactData();
        contactData.setTestCaseName(m.getName());
        contactName = contactData.getName();

        Hamburger hamburger = navigation.openHamburger();
        SettingsView settingsView = hamburger.openSettings();
        OfflineModeTab offlineModeTab = (OfflineModeTab) settingsView.select(UITitles.SettingsTab.OFFLINE_MODE);
        offlineModeTab.turnOffStoringActivities();
    }

    @Test
    //Test Case 220392, part 2
    public void verifyContactFullProfileInOfflineMode() {
        List<String> tabsAvailableOffline = new ArrayList<>(Arrays.asList(
                "Details",
                "Ownership",
                "Actions",
                "Additional Info"
        ));

        List<String> tabsUnavailableOffline = new ArrayList<>(Arrays.asList(
                //TODO investigate cached Focus tab
                "Focus",
                "Funds"
        ));
        navigation
                .searchContacts(contactName)
                .openProfileOverview(contactName);
        contactRecentlyViewedTab = (ContactRecentlyViewedTab) navigation.selectRecentlyViewedTab();
        contactRecentlyViewedTab
                .verifyItemPresentInList(contactName);

        navigation.switchToOfflineMode();

        contactFullProfile = contactRecentlyViewedTab.openFullProfile(contactName);

        contactFullProfile
                .verifyProfileName(contactName);
        contactFullProfile
                .clickInstitutionProfileLink()
                .verifyTooltip("This Profile is unavailable to you while in Offline Mode.");
        contactFullProfile
                .clickAddActivityButton()
                .verifyTooltip("This Feature is disabled in Offline Mode.")
                .clickFixDataLink()
                .verifyTooltip("This Feature is disabled in Offline Mode.");

        contactFullProfile
                .openMapPreview()
                .verifyTitle("Contact Address")
                .verifyMapMessage("Maps Unavailable in Offline Mode")
                .verifyAddressPresent()
                .close();

        for (String tabName : tabsAvailableOffline) {
            contactFullProfile
                    .selectTab(tabName)
                    .verifyContentLoaded();
        }

        contactFullProfile.selectTab("Actions");
        ContactActionsProfileTab contactActionsProfileTab = new ContactActionsProfileTab();
        contactActionsProfileTab
                .clickRecentActivity();
        //TODO investigate cached recent activity & task data
//        contactFullProfile
//                .verifyTooltip("This Activity is unavailable to you while in Offline Mode.");
//        contactActionsProfileTab
//                .clickRecentTask();
//        contactFullProfile
//                .verifyTooltip("This Task is unavailable to you while in Offline Mode.");

        contactActionsProfileTab
                .clickActivityAvailableOffline()
                .verifyDeleteButtonUnavailable()
                .verifyEditButtonUnavailable();
        contactFullProfile
                .closeOverlay();

        contactActionsProfileTab
                .clickActivityUnavailableOffline();
        contactFullProfile
                .verifyTooltip("This Activity is unavailable to you while in Offline Mode.");

        for (String tabName : tabsUnavailableOffline) {
            contactFullProfile
                    .selectTab(tabName)
                    .verifyEmptyProfileTabMessage("This tab is not available in Offline Mode. More information may be available online.");
        }
    }

    @Test
    public void verifyCachedContentsOnContactProfileFundsTab(){
        ContactFundsProfileTab contactFundsProfileTab;
        String holdersFundName, otherFundName;
        FundData fundData = new FundData();

        fundData.loadDataSetByTag("holders");
        holdersFundName = fundData.getName();

        fundData.loadDataSetByTag("other");
        otherFundName = fundData.getName();

        contactFullProfile = navigation.openContactProfile(contactName);
        contactFullProfile
                .selectTab("Funds");
        contactFundsProfileTab = new ContactFundsProfileTab();

        contactFundsProfileTab
                .selectHoldersTab()
                .openFundProfile(holdersFundName)
                .waitReady();
        navigation.back();
        contactFundsProfileTab
                .verifyActiveTab("Holders");

        contactFundsProfileTab
                .selectOtherTab()
                .openFundProfile(otherFundName)
                .waitReady();
        navigation.back();
        contactFundsProfileTab
                .verifyActiveTab("Other");

        navigation.switchToOfflineMode();

        contactFundsProfileTab
                .selectHoldersTab()
                .selectFundUnavailableOffline();
        contactFullProfile
                .verifyTooltip(UNAVAILABLE_PROFILE_MESSAGE);
        contactFundsProfileTab
                .selectFundAvailableOffline()
                .verifyProfileName(holdersFundName);
        navigation
                .verifyPageTitle("Fund Profile")
                .back();
        contactFullProfile
                .verifyActiveTab("Funds");
        contactFundsProfileTab
                .verifyActiveTab("Holders");

        contactFundsProfileTab
                .selectOtherTab()
                .selectFundUnavailableOffline();
        contactFullProfile
                .verifyTooltip(UNAVAILABLE_PROFILE_MESSAGE);
        contactFundsProfileTab
                .selectFundAvailableOffline()
                .verifyProfileName(otherFundName);
        navigation
                .verifyPageTitle("Fund Profile")
                .back();
        contactFullProfile
                .verifyActiveTab("Funds");
        contactFundsProfileTab
                .verifyActiveTab("Other");
    }

    @Test
    public void verifyCachedContentsOnContactProfileFocusTab(){
        contactName = contactData.getName();
        ContactSearchTab searchTab = navigation.searchContacts(contactName);
        navigation.switchToOfflineMode();
        contactFullProfile = searchTab.openFullProfile(contactName);
        contactFullProfile
                .selectTab("Focus");
        ContactFocusProfileTab contactFocusProfileTab = new ContactFocusProfileTab();

        contactFocusProfileTab
                .verifyProfileTabUnavailableInOfflineMode();

        navigation.switchToOnlineMode();

        contactFullProfile
                .selectTab("Funds");
        contactFullProfile
                .selectTab("Focus")
                .verifyActiveTab("Equity")
                .verifyContentLoaded();
    }
}
