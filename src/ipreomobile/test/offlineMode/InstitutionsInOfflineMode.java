package ipreomobile.test.offlineMode;

import ipreomobile.data.*;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.hamburgerItems.Hamburger;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.institutions.InstitutionFullProfile;
import ipreomobile.ui.institutions.InstitutionRecentlyViewedTab;
import ipreomobile.ui.profiles.fullProfileTabs.*;
import ipreomobile.ui.securities.EqSecurityFullProfile;
import ipreomobile.ui.securities.FiSecurityFullProfile;
import ipreomobile.ui.settings.OfflineModeTab;
import ipreomobile.ui.hamburgerItems.SettingsView;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Artem_Skrypnyk on 9/29/2014.
 */
public class InstitutionsInOfflineMode extends BaseTest{

    private static final String UNAVAILABLE_PROFILE_MESSAGE = "This Profile is unavailable to you while in Offline Mode.";
    private static final String UNAVAILABLE_SEARCHING_MESSAGE = "Searching within a profile tab is disabled in Offline Mode.";
    private static final String UNAVAILABLE_TAB_MESSAGE = "This tab is not available in Offline Mode. More information may be available online.";
    private static final String UNAVAILABLE_LINK_MESSAGE = "This Link is unavailable to you while in Offline Mode";
    private static final String UNAVAILABLE_FEATURE_MESSAGE = "This Feature is disabled in Offline Mode.";

    private InstitutionData institutionData;
    private String institutionName;

    private InstitutionFullProfile institutionFullProfile;
    private InstitutionRecentlyViewedTab institutionsRecentlyViewedTab;

    @BeforeMethod
    public void setupTestData(Method m) {
        institutionData = new InstitutionData();
        institutionData.setTestCaseName(m.getName());
        institutionName = institutionData.getName();

        Hamburger hamburger = navigation.openHamburger();
        SettingsView settingsView = hamburger.openSettings();
        OfflineModeTab offlineModeTab = (OfflineModeTab) settingsView.select(UITitles.SettingsTab.OFFLINE_MODE);
        offlineModeTab.turnOffStoringActivities();
    }

    @Test
    public void verifyInstitutionFullProfileInOfflineMode() {
        List<UITitles.ProfileTab> tabsAvailableOffline = new ArrayList<>(Arrays.asList(
                UITitles.ProfileTab.DETAILS,
                UITitles.ProfileTab.OWNERSHIP,
                UITitles.ProfileTab.TARGETING,
                UITitles.ProfileTab.FOCUS,
                UITitles.ProfileTab.ACTIONS,
                UITitles.ProfileTab.ADDITIONAL_INFO
        ));

        List<UITitles.ProfileTab> tabsUnavailableOffline = new ArrayList<>(Arrays.asList(
                UITitles.ProfileTab.FUNDS,
                UITitles.ProfileTab.CURRENT_HOLDINGS,
                UITitles.ProfileTab.CONTACTS
        ));

        navigation
                .searchInstitutions(institutionName)
                .openProfileOverview(institutionName);
        institutionsRecentlyViewedTab = (InstitutionRecentlyViewedTab) navigation.selectRecentlyViewedTab();
        institutionsRecentlyViewedTab
                .verifyItemPresentInList(institutionName);

        navigation.switchToOfflineMode();

        institutionFullProfile = institutionsRecentlyViewedTab.openFullProfile(institutionName);

        institutionFullProfile
                .verifyProfileName(institutionName);
        //TFS224540
        institutionFullProfile
                .clickCompanySiteLink()
                .verifyTooltip(UNAVAILABLE_LINK_MESSAGE);
        institutionFullProfile
                .clickAddActivityButton()
                .verifyTooltip(UNAVAILABLE_FEATURE_MESSAGE)
                .clickFixDataLink()
                .verifyTooltip(UNAVAILABLE_FEATURE_MESSAGE);

        institutionFullProfile
                .openMapPreview()
                .verifyTitle("Institution Address")
                .verifyMapMessage("Maps Unavailable in Offline Mode")
                .verifyAddressPresent()
                .close();

        for (UITitles.ProfileTab tabName : tabsAvailableOffline) {
            institutionFullProfile
                    .selectTab(tabName)
                    .verifyContentLoaded();
        }

        institutionFullProfile
                .selectTab("Ownership");
        InstitutionOwnershipProfileTab institutionOwnershipProfileTab = new InstitutionOwnershipProfileTab();
        institutionOwnershipProfileTab
                .verifyActiveTab("Equity")
                .verifyTabPresent("Fixed Income");
        institutionOwnershipProfileTab
                .clickFirstSecurityInTopHoldingsTable();
        institutionFullProfile
                .verifyTooltip(UNAVAILABLE_PROFILE_MESSAGE);
        institutionOwnershipProfileTab
                .clickFirstSecurityInTopBuysAndSellsTable();
        institutionFullProfile
                .verifyTooltip(UNAVAILABLE_PROFILE_MESSAGE);

        institutionOwnershipProfileTab
                .selectFixedIncomeTab();
        institutionOwnershipProfileTab
                .clickFirstSecurityInTopHoldingsTable();
        institutionFullProfile
                .verifyTooltip(UNAVAILABLE_PROFILE_MESSAGE);
        institutionOwnershipProfileTab
                .clickFirstSecurityInTopBuysAndSellsTable();
        institutionFullProfile
                .verifyTooltip(UNAVAILABLE_PROFILE_MESSAGE);

        institutionFullProfile.selectTab("Actions");
        InstitutionActionsProfileTab institutionActionsProfileTab = new InstitutionActionsProfileTab();
        institutionActionsProfileTab
                .clickRecentActivity();
        institutionFullProfile
                .verifyTooltip("This Activity is unavailable to you while in Offline Mode.");
        institutionActionsProfileTab
                .clickRecentTask();
        institutionFullProfile
                .verifyTooltip("This Task is unavailable to you while in Offline Mode.");

        institutionActionsProfileTab
                .clickActivityAvailableOffline()
                .verifyDeleteButtonUnavailable()
                .verifyEditButtonUnavailable();
        institutionFullProfile
                .closeOverlay();

        institutionActionsProfileTab
                .clickActivityUnavailableOffline();
        institutionFullProfile
                .verifyTooltip("This Activity is unavailable to you while in Offline Mode.");

        for (UITitles.ProfileTab tabName : tabsUnavailableOffline) {
            institutionFullProfile
                    .selectTab(tabName)
                    .verifyEmptyProfileTabMessage(UNAVAILABLE_TAB_MESSAGE);
        }
    }

    @Test
    public void verifyCachedContentsOnInstitutionProfileFundsTab(){
        InstitutionFundsProfileTab institutionFundsProfileTab;
        String holdersFundName, otherFundName;
        FundData fundData = new FundData();
        fundData.setTestCaseName("verifyCachedContentsOnContactProfileFundsTab");

        fundData.loadDataSetByTag("holders");
        holdersFundName = fundData.getName();

        fundData.loadDataSetByTag("other");
        otherFundName = fundData.getName();

        institutionFullProfile = navigation.openInstitutionProfile(institutionName);
        institutionFullProfile
                .selectTab("Funds");
        institutionFundsProfileTab = new InstitutionFundsProfileTab();

        institutionFundsProfileTab
                .selectHoldersTab()
                .openFundProfile(holdersFundName)
                .waitReady();
        navigation.back();
        institutionFundsProfileTab
                .verifyActiveTab("Holders");

        institutionFundsProfileTab
                .selectOtherTab()
                .openFundProfile(otherFundName)
                .waitReady();
        navigation.back();
        institutionFundsProfileTab
                .verifyActiveTab("Other");

        navigation.switchToOfflineMode();

        institutionFundsProfileTab
                .selectHoldersTab()
                .selectFundUnavailableOffline();
        institutionFullProfile
                .verifyTooltip(UNAVAILABLE_PROFILE_MESSAGE);
        institutionFundsProfileTab
                .selectFundAvailableOffline()
                .verifyProfileName(holdersFundName);
        navigation
                .verifyPageTitle("Fund Profile")
                .back();
        institutionFullProfile
                .verifyActiveTab("Funds");
        institutionFundsProfileTab
                .verifyActiveTab("Holders");

        institutionFundsProfileTab
                .selectOtherTab()
                .selectFundUnavailableOffline();
        institutionFullProfile
                .verifyTooltip(UNAVAILABLE_PROFILE_MESSAGE);
        institutionFundsProfileTab
                .selectFundAvailableOffline()
                .verifyProfileName(otherFundName);
        navigation
                .verifyPageTitle("Fund Profile")
                .back();
        institutionFullProfile
                .verifyActiveTab("Funds");
        institutionFundsProfileTab
                .verifyActiveTab("Other");
    }

    @Test
    public void verifyCachedContentsOnInstitutionProfileCurrentHoldingsTab(){
        InstitutionCurrentHoldingsProfileTab institutionCurrentHoldingsProfileTab;
        EquityData equityData = new EquityData();
        FixedIncomeData securityData = new FixedIncomeData();
        //String securityName = securityData.getSecurityName();
        String equityName = equityData.getSecurityName();
        String couponName = securityData.getCouponName();
        String equitySecurityNameLeadingSymbols = equityName.substring(0, 2);

        institutionFullProfile = navigation.openInstitutionProfile(institutionName);
        institutionFullProfile
                .selectTab("Current Holdings");
        institutionCurrentHoldingsProfileTab = new InstitutionCurrentHoldingsProfileTab();

        institutionCurrentHoldingsProfileTab
                .selectEquityTab()
                .filterBySymbolOrSecurityName(equitySecurityNameLeadingSymbols)
                .openEquitySecurityProfile(equityName);
        navigation.back();
        institutionCurrentHoldingsProfileTab
                .verifyActiveTab("Equity");
        institutionCurrentHoldingsProfileTab.
                clearFilterBySymbolOrSecurityName();

        institutionCurrentHoldingsProfileTab
                .selectFixedIncomeTab()
                .filterBySymbolOrSecurityName(couponName.substring(0, 2))
                .openFixedIncomeSecurityProfile(couponName)
                .waitReady();
        navigation.back();
        institutionCurrentHoldingsProfileTab
                .verifyActiveTab("Fixed Income");
        institutionCurrentHoldingsProfileTab.
                clearFilterBySymbolOrSecurityName();

        navigation.switchToOfflineMode();

        institutionCurrentHoldingsProfileTab
                .selectEquityTab()
                .selectItemUnavailableOffline();
        institutionFullProfile.
                verifyTooltip(UNAVAILABLE_PROFILE_MESSAGE);

        institutionCurrentHoldingsProfileTab
                .verifyFilterUnavailableOffline();
        institutionFullProfile.
                verifyTooltip(UNAVAILABLE_SEARCHING_MESSAGE);

        institutionCurrentHoldingsProfileTab
                .selectItemAvailableOffline();
        new EqSecurityFullProfile()
                .verifyProfileName(equityName);
        navigation
                .verifyPageTitle("Security Profile")
                .back();
        institutionFullProfile.verifyActiveTab("Current Holdings");
        institutionCurrentHoldingsProfileTab.verifyActiveTab("Equity");

        institutionCurrentHoldingsProfileTab
                .selectFixedIncomeTab()
                .selectItemUnavailableOffline();
        institutionFullProfile.
                verifyTooltip(UNAVAILABLE_PROFILE_MESSAGE);

        institutionCurrentHoldingsProfileTab
                .verifyFilterUnavailableOffline();
        institutionFullProfile.
                verifyTooltip(UNAVAILABLE_SEARCHING_MESSAGE);

        institutionCurrentHoldingsProfileTab
                .selectItemAvailableOffline();
        new FiSecurityFullProfile()
                .verifyProfileName(couponName);
        navigation
                .verifyPageTitle("Security Profile")
                .back();
        institutionFullProfile.verifyActiveTab("Current Holdings");
        institutionCurrentHoldingsProfileTab.verifyActiveTab("Fixed Income");

    }

    @Test
    public void verifyCachedContentsOnInstitutionProfileContactsTab(){
        InstitutionContactsProfileTab institutionContactsProfileTab;
        ContactData contactData = new ContactData();
        String contactName = contactData.getName();

        institutionFullProfile = navigation.openInstitutionProfile(institutionName);
        institutionFullProfile.selectTab("Contacts");
        institutionContactsProfileTab = new InstitutionContactsProfileTab();
        institutionContactsProfileTab
                .verifyContentLoaded()
                .verifyActiveTab("CRM Contacts")
                .verifyTabPresent("Search Contacts");

        institutionContactsProfileTab
                .selectCrmContactsTab()
                .selectContact(contactName)
                .waitReady();
        navigation
                .verifyPageTitle("Contact Profile")
                .back();
        institutionContactsProfileTab
                .verifyActiveTab("CRM Contacts");

        institutionContactsProfileTab
                .selectSearchContactsTab()
                .filterContacts(contactName)
                .selectContact(contactName)
                .waitReady();
        navigation.back();
        institutionContactsProfileTab
                .verifyActiveTab("Search Contacts");

        navigation.switchToOfflineMode();

        institutionContactsProfileTab
                .selectCrmContactsTab()
                .selectContactUnavailableOffline();
        institutionFullProfile.
                verifyTooltip(UNAVAILABLE_PROFILE_MESSAGE);

        institutionContactsProfileTab
                .selectContactAvailableOffline()
                .verifyProfileName(contactName);
        navigation
                .verifyPageTitle("Contact Profile")
                .back();
        institutionFullProfile.verifyActiveTab("Contacts");
        institutionContactsProfileTab.verifyActiveTab("CRM Contacts");

        institutionContactsProfileTab
                .selectSearchContactsTab()
                .verifyEmptyProfileTabMessage(UNAVAILABLE_SEARCHING_MESSAGE)
                .verifyFilterUnavailableOffline();
    }

/*    @Test
    public void verifyFundsTabInOfflineModeOnInstitutionFullProfile(){
        FundData fundData = new FundData();
        fundData.loadDataSetByTag("fund_available_in_offline_mode");
        String fundNameAvailableInOfflineMode = fundData.getName();
        fundData.loadDataSetByTag("fund_unavailable_in_offline_mode");
        String fundNameUnavailableInOfflineMode = fundData.getName();
        InstitutionFundsProfileTab fundsTab;
        StatusIndicator statusIndicator = new StatusIndicator();

        navigation
                .openInstitutions()
                .openInstitutionProfile(institutionName);
        institutionFullProfile = new InstitutionFullProfile();

        institutionFullProfile.selectTab(UITitles.ProfileTab.FUNDS);
        fundsTab = new InstitutionFundsProfileTab();

        fundsTab
                .selectHoldersTab()
                .openFundProfile(fundNameAvailableInOfflineMode);
        navigation
                .back()
                .switchToOfflineMode();
        statusIndicator
                .verifyOfflineModeEnabled();
        fundsTab
                .verifyFundUnavailableInOffline(fundNameUnavailableInOfflineMode)
                .verifyFundAvailableInOffline(fundNameAvailableInOfflineMode)
                .openFundProfile(fundNameAvailableInOfflineMode);
    }



*/
}
