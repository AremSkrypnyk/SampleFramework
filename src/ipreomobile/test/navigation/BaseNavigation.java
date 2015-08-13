package ipreomobile.test.navigation;

import ipreomobile.data.*;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.templates.ui.ProfileTab;
import ipreomobile.templates.ui.TwoPane;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.contacts.*;
import ipreomobile.ui.funds.FundFullProfile;
import ipreomobile.ui.funds.FundRecentlyViewedTab;
import ipreomobile.ui.institutions.*;

import ipreomobile.ui.profiles.fullProfileTabs.*;
import ipreomobile.ui.search.SecuritySearchPanel;
import ipreomobile.ui.securities.EqSecurityFullProfile;
import ipreomobile.ui.securities.FiSecurityFullProfile;
import ipreomobile.ui.securities.SecurityRecentlyViewedTab;
import ipreomobile.ui.securities.SecuritySearchTab;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class BaseNavigation extends BaseTest {

    InstitutionData institutionData;
    ContactData contactData;
    EquityData equityData;
    FixedIncomeData fixedIncomeData;
    FundData fundData;

    ContactFullProfile contactFullProfile;
    InstitutionFullProfile institutionFullProfile;
    EqSecurityFullProfile eqSecurityFullProfile;
    FiSecurityFullProfile fiSecurityFullProfile;
    FundFullProfile fundFullProfile;

    String institutionName;
    String contactName;
    String securityName;
    String securityMarketName;
    String couponName;
    String fundName;

    ProfileTab activeProfileTab;
    TwoPane activeTab;

    @BeforeMethod
    public void setupTestData(Method testMethod){
        this.institutionData = new InstitutionData();
        this.institutionName = institutionData.getName();

        this.contactData = new ContactData();
        this.contactName = contactData.getName();

        this.equityData = new EquityData();
        this.equityData.setTestCaseName(testMethod.getName());

        this.fixedIncomeData = new FixedIncomeData();
        this.fixedIncomeData.setTestCaseName(testMethod.getName());

        this.securityName = equityData.getSecurityName();
        this.securityMarketName = equityData.getSecurityMarketName();
        this.couponName = fixedIncomeData.getCouponName();

        this.fundData = new FundData();
        this.fundName = fundData.getName();
    }

    @Test
    public void navigateBetweenInstitutionTabs(){
        navigation.openInstitutions();

        navigation.selectTab(UITitles.PanelTabs.INVESTORS);
        activeTab = new InvestorsTab();
        Assert.assertTrue(activeTab.isContentLoaded(), "Institutions Investors Tab was expected to contain data:");
        Assert.assertFalse(activeTab.isActiveTabEmpty(), "Institutions Investors Tab was expected to be empty:");
        Assert.assertFalse(((InvestorsTab)activeTab).isProfileUnavailableInOfflineMode(institutionName), "Item with " + institutionName + " name on Institutions Investors Tab was expected to be unavailable:");

        navigation.selectTab(UITitles.PanelTabs.RECENTLY_VIEWED);
        activeTab = new InstitutionRecentlyViewedTab();
        Assert.assertTrue(activeTab.isContentLoaded(), "Institutions Recently Viewed Tab was expected to contain data:");
        Assert.assertFalse(activeTab.isActiveTabEmpty(), "Institutions Recently Viewed Tab was expected to be empty:");

        navigation.selectTab(UITitles.PanelTabs.LISTS);
        activeTab = new InstitutionListsTab();
        Assert.assertTrue(activeTab.isContentLoaded(), "Institutions List Tab was expected to contain data:");
        Assert.assertFalse(activeTab.isActiveTabEmpty(), "Institutions List Tab was expected to be empty:");

        navigation.switchToOfflineMode();
        Assert.assertTrue(activeTab.isContentLoaded(), "Institutions List Tab was expected to contain cached data:");
        Assert.assertFalse(activeTab.isActiveTabEmpty(), "Institutions List Tab was expected to be empty:");
        Assert.assertTrue(((InstitutionListsTab)activeTab).isSearchFilterUnavailableInOfflineMode(), "Searching on Institutions List Tab was expected to be unavailable:");
        Assert.assertTrue(((InvestorsTab)activeTab).isProfileUnavailableInOfflineMode(institutionName), "Item with " + institutionName + " name on Institutions Lists Tab was expected to be unavailable:");
    }

    @Test
    public void verifyInstitutionProfileTabReady(){
        new InvestorsTab().openFullProfile(institutionName);
        institutionFullProfile = new InstitutionFullProfile();

        institutionFullProfile.selectTab("Details");
        activeProfileTab = new InstitutionDetailsProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Institution Details Tab was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Institution Details Tab was expected to be empty:");

        institutionFullProfile.selectTab("Ownership");
        activeProfileTab = new InstitutionOwnershipProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Institution Ownership Tab (Equity Tab) was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Institution Ownership Tab (Equity Tab) was expected to be empty:");

        ((InstitutionOwnershipProfileTab) activeProfileTab).selectFixedIncomeTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Institution Ownership Tab (Fixed Income Tab) was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Institution Ownership Tab (Fixed Income Tab) was expected to be empty:");

        institutionFullProfile.selectTab("Targeting");
        activeProfileTab = new InstitutionTargetingProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Institution Targeting Tab was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Institution Targeting Tab was expected to be empty:");

        institutionFullProfile.selectTab("Focus");
        activeProfileTab = new InstitutionFocusProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Institution Focus Tab (Investment Approach Tab) was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Institution Focus Tab (Investment Approach Tab) was expected to be empty:");
        ((InstitutionFocusProfileTab) activeProfileTab).selectEquityFocusTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Institution Focus Tab (Equity Focus Tab) was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Institution Focus Tab (Equity Focus Tab) was expected to be empty:");
        ((InstitutionFocusProfileTab) activeProfileTab).selectFixedIncomeFocusTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Institution Focus Tab (Fixed Income Focus Tab) was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Institution Focus Tab (Fixed Income Focus Tab) was expected to be empty:");

        institutionFullProfile.selectTab("Actions");
        activeProfileTab = new InstitutionActionsProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Institution Actions Tab (Recent Tab) was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Institution Actions Tab (Recent Tab) was expected to be empty:");
        ((InstitutionActionsProfileTab) activeProfileTab).selectActivitiesTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Institution Actions Tab (Activities Tab) was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Institution Actions Tab (Activities Tab) was expected to be empty:");
        ((InstitutionActionsProfileTab) activeProfileTab).selectTasksTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Institution Actions Tab (Tasks Tab) was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Institution Actions Tab (Tasks Tab) was expected to be empty:");

        institutionFullProfile.selectTab("Funds");
        activeProfileTab = new InstitutionFundsProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Institution Funds Tab (Holders Tab) was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Institution Funds Tab (Holders Tab) was expected to be empty:");
        ((InstitutionFundsProfileTab) activeProfileTab).selectOtherTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Institution Funds Tab (Others Tab) was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Institution Funds Tab (Others Tab) was expected to be empty:");

        institutionFullProfile.selectTab("Current Holdings");
        activeProfileTab = new InstitutionCurrentHoldingsProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Institution Current Holdings Tab (Equity Tab) was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Institution Current Holdings Tab (Equity Tab) was expected to be empty:");
        ((InstitutionCurrentHoldingsProfileTab) activeProfileTab).filterBySymbolOrSecurityName("Google");
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Institution Current Holdings Tab (Equity Tab) filtered by valid SecurityName was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Institution Current Holdings Tab (Equity Tab) filtered by valid SecurityName was expected to be empty:");
        ((InstitutionCurrentHoldingsProfileTab) activeProfileTab).filterBySymbolOrSecurityName("Lebanon");
        Assert.assertFalse(activeProfileTab.isContentLoaded(), "Institution Current Holdings Tab (Equity Tab) filtered by invalid SecurityName was expected to contain data:");
        Assert.assertTrue(activeProfileTab.isProfileTabEmpty(), "Institution Current Holdings Tab (Equity Tab) filtered by invalid SecurityName was expected to be empty:");
        ((InstitutionCurrentHoldingsProfileTab) activeProfileTab).selectFixedIncomeTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Institution Current Holdings Tab (Fixed Income Tab) was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Institution Current Holdings Tab (Fixed Income Tab) was expected to be empty:");
        ((InstitutionCurrentHoldingsProfileTab) activeProfileTab).filterBySymbolOrSecurityName("Google");
        Assert.assertFalse(activeProfileTab.isContentLoaded(), "Institution Current Holdings Tab (Fixed Income Tab) filtered by invalid SecurityName was expected to contain data:");
        Assert.assertTrue(activeProfileTab.isProfileTabEmpty(), "Institution Current Holdings Tab (Fixed Income Tab) filtered by invalid SecurityName was expected to be empty:");
        ((InstitutionCurrentHoldingsProfileTab) activeProfileTab).filterBySymbolOrSecurityName("Lebanon");
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Institution Current Holdings Tab (Fixed Income Tab) filtered by valid SecurityName was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Institution Current Holdings Tab (Fixed Income Tab) filtered by valid SecurityName was expected to be empty:");

        institutionFullProfile.selectTab("Contacts");
        activeProfileTab = new InstitutionContactsProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Institution Contacts Tab (CRM Contacts Tab) was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Institution Contacts Tab (CRM Contacts Tab) was expected to be empty:");
        ((InstitutionContactsProfileTab) activeProfileTab).selectSearchContactsTab();
        Assert.assertFalse(activeProfileTab.isContentLoaded(), "Institution Contacts Tab (Search Contacts Tab) was expected to contain data:");
        Assert.assertTrue(activeProfileTab.isProfileTabEmpty(), "Institution Contacts Tab (Search Contacts Tab) was expected to be empty:");
        ((InstitutionContactsProfileTab) activeProfileTab).filterContacts("Abigail");
        Assert.assertFalse(activeProfileTab.isContentLoaded(), "Institution Contacts Tab (Search Contacts Tab) filtered by invalid search criteria was expected to contain data:");
        Assert.assertTrue(activeProfileTab.isProfileTabEmpty(), "Institution Contacts Tab (Search Contacts Tab) filtered by invalid search criteria was expected to be empty:");
        ((InstitutionContactsProfileTab) activeProfileTab).filterContacts("Johnson");
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Institution Contacts Tab (Search Contacts Tab) filtered by valid search criteria was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Institution Contacts Tab (Search Contacts Tab) filtered by valid search criteria was expected to be empty:");

        institutionFullProfile.selectTab("Additional Info");
        activeProfileTab = new InstitutionAdditionalInfoProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Institution Additional Info Tab was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Institution Additional Info Tab was expected to be empty:");


        navigation.switchToOfflineMode();

        institutionFullProfile.selectTab("Funds");
        activeProfileTab = new InstitutionFundsProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Institution Funds Tab (Holders Tab) was expected to contain cached data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Institution Funds Tab (Holders Tab) was expected to be empty:");
        ((InstitutionFundsProfileTab) activeProfileTab).selectOtherTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Institution Funds Tab (Others Tab) was expected to contain cached data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Institution Funds Tab (Others Tab) was expected to be empty:");

        institutionFullProfile.selectTab("Current Holdings");
        activeProfileTab = new InstitutionCurrentHoldingsProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Institution Current Holdings Tab (Equity Tab) was expected to contain cached data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Institution Current Holdings Tab (Equity Tab) was expected to be empty:");
        Assert.assertTrue(activeProfileTab.isSearchFilterUnavailableInOfflineMode(), "Searching on Institution Current Holdings Tab (Equity Tab) was expected to be unable:");
        Assert.assertTrue(((InstitutionCurrentHoldingsProfileTab) activeProfileTab).isProfileUnavailableInOffline(securityName), "Item with " + securityName +
                " name on Institution Current Holdings Tab (Equity Tab) was expected to be unavailable:");

        ((InstitutionCurrentHoldingsProfileTab) activeProfileTab).selectFixedIncomeTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Institution Current Holdings Tab (Fixed Income Tab) was expected to contain cached data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Institution Current Holdings Tab (Fixed Income Tab) was expected to be empty:");
        Assert.assertTrue(activeProfileTab.isSearchFilterUnavailableInOfflineMode(), "Searching on Institution Current Holdings Tab (Fixed Income Tab) was expected to be unable:");
        Assert.assertTrue(((InstitutionCurrentHoldingsProfileTab) activeProfileTab).isProfileUnavailableInOffline(couponName), "Item with " + couponName +
                " name on Institution Current Holdings Tab (Fixed Income Tab) was expected to be unavailable:");

        institutionFullProfile.selectTab("Contacts");
        activeProfileTab = new InstitutionContactsProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Institution Contacts Tab (CRM Contacts Tab) was expected to contain cached data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Institution Contacts Tab (CRM Contacts Tab) was expected to be empty:");
        ((InstitutionContactsProfileTab) activeProfileTab).selectSearchContactsTab();
        Assert.assertFalse(activeProfileTab.isContentLoaded(), "Institution Contacts Tab (Search Contacts Tab) was expected to contain cached data:");
        Assert.assertTrue(activeProfileTab.isProfileTabEmpty(), "Institution Contacts Tab (Search Contacts Tab) was expected to be empty:");
        Assert.assertTrue(activeProfileTab.isSearchFilterUnavailableInOfflineMode(), "Searching on Institution Contacts Tab (Search Contacts Tab) was expected to be unable:");
    }

    @Test
    public void navigateBetweenContactTabs(){
        navigation.openContacts();

        navigation.selectTab(UITitles.PanelTabs.ACTIVITY);
        activeTab = new ActivityTab();
        Assert.assertTrue(activeTab.isContentLoaded(), "Contacts Activity Tab was expected to contain data:");
        Assert.assertFalse(activeTab.isActiveTabEmpty(), "Contacts Activity Tab was expected to be empty:");

        navigation.selectTab(UITitles.PanelTabs.RECENTLY_VIEWED);
        activeTab = new ContactRecentlyViewedTab();
        Assert.assertTrue(activeTab.isContentLoaded(), "Contacts Recently Viewed Tab was expected to contain data:");
        Assert.assertFalse(activeTab.isActiveTabEmpty(), "Contacts Recently Viewed Tab was expected to be empty:");

        navigation.selectTab(UITitles.PanelTabs.LISTS);
        activeTab = new ContactListsTab();
        Assert.assertTrue(activeTab.isContentLoaded(), "Contacts List Tab was expected to contain data:");
        Assert.assertFalse(activeTab.isActiveTabEmpty(), "Contacts List Tab was expected to be empty:");

        navigation.switchToOfflineMode();
        Assert.assertTrue(activeTab.isContentLoaded(), "Contacts List Tab was expected to contain cached data:");
        Assert.assertFalse(activeTab.isActiveTabEmpty(), "Contacts List Tab was expected to be empty:");
        Assert.assertTrue(((ContactListsTab)activeTab).isSearchFilterUnavailableInOfflineMode(), "Searching on Contact List Tab was expected to be unable:");
    }

    @Test
    public void verifyContactProfileTabReady(){

        contactFullProfile = navigation.openContactProfile(contactName);

        contactFullProfile.selectTab("Details");
        activeProfileTab = new ContactDetailsProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Contact Details Tab was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Contact Details Tab was expected to be empty:");

        contactFullProfile.selectTab("Ownership");
        activeProfileTab = new ContactOwnershipProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Contact Ownership Tab was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Contact Ownership Tab was expected to be empty:");

        contactFullProfile.selectTab("Focus");
        activeProfileTab = new ContactFocusProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Contact Focus Tab was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Contact Focus Tab was expected to be empty:");

        contactFullProfile.selectTab("Actions");
        activeProfileTab = new ContactActionsProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Contact Actions Tab (Recent Tab) was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Contact Actions Tab (Recent Tab) was expected to be empty:");
        ((ContactActionsProfileTab) activeProfileTab).selectActivitiesTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Contact Actions Tab (Activities Tab) was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Contact Actions Tab (Activities Tab) was expected to be empty:");

        contactFullProfile.selectTab("Funds");
        activeProfileTab = new ContactFundsProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Contact Funds Tab was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Contact Funds Tab was expected to be empty:");

        contactFullProfile.selectTab("Additional Info");
        activeProfileTab = new ContactAdditionalInfoProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Contact Additional Info Tab was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Contact Additional Info Tab was expected to be empty:");


        navigation.switchToOfflineMode();

        contactFullProfile.selectTab("Focus");
        activeProfileTab = new ContactFocusProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Contact Focus Tab was expected to contain cached data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Contact Focus Tab was expected to be empty:");

        contactFullProfile.selectTab("Funds");
        activeProfileTab = new ContactFundsProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Contact Funds Tab was expected to contain cached data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Contact Funds Tab was expected to be empty:");
    }

    @Test
    public void navigateBetweenSecurityTabs(){
        navigation.openSecurities();

        navigation.selectTab(UITitles.PanelTabs.RECENTLY_VIEWED);
        activeTab = new SecurityRecentlyViewedTab();
        Assert.assertFalse(activeTab.isContentLoaded(), "Securities Recently Viewed Tab was expected to be empty, but some content was found: ");
        Assert.assertTrue(activeTab.isActiveTabEmpty(), "Securities Recently Viewed Tab was expected to be empty, but no corresponding message found: ");
    }

    @Test
    public void verifyEquitySecurityProfileTabReady() {

        navigation.searchSecurities(securityName);
        new SecuritySearchTab().openFullProfile(securityName, securityMarketName);

        eqSecurityFullProfile = new EqSecurityFullProfile();

        eqSecurityFullProfile.selectTab("Details");
        activeProfileTab = new EqSecurityDetailsProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Equity Security Details Tab was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Equity Security Details Tab was expected to be empty:");

        eqSecurityFullProfile.selectTab("Top 10");
        activeProfileTab = new EqSecurityTop10ProfileProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Equity Security Top 10 Tab (Institutions Tab) was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Equity Security Top 10 Tab (Institutions Tab) was expected to be empty:");
        ((EqSecurityTop10ProfileProfileTab) activeProfileTab).selectFundsTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Equity Security Top 10 Tab (Funds Tab) was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Equity Security Top 10 Tab (Funds Tab) was expected to be empty:");

        eqSecurityFullProfile.selectTab("Current Holders");
        activeProfileTab = new EqSecurityCurrentHoldersProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Equity Security Current Holdings Tab (Institutions Tab) was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Equity Security Current Holdings Tab (Institutions Tab) was expected to be empty:");
        ((EqSecurityCurrentHoldersProfileTab) activeProfileTab).filterByHolderName("The Vanguard Group");
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Equity Security Current Holdings Tab (Institutions Tab) filtered by valid HolderName was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Equity Security Current Holdings Tab (Institutions Tab) filtered by valid HolderName was expected to be empty:");
        ((EqSecurityCurrentHoldersProfileTab) activeProfileTab).filterByHolderName("SPDR S&P 500 ETF");
        Assert.assertFalse(activeProfileTab.isContentLoaded(), "Equity Security Current Holdings Tab (Institutions Tab) filtered by invalid HolderName was expected to contain data:");
        Assert.assertTrue(activeProfileTab.isProfileTabEmpty(), "Equity Security Current Holdings Tab (Institutions Tab) filtered by invalid HolderName was expected to be empty:");

        ((EqSecurityCurrentHoldersProfileTab) activeProfileTab).selectFundsTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Equity Security Current Holdings Tab (Funds Tab) was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Equity Security Current Holdings Tab (Funds Tab) was expected to be empty:");
        ((EqSecurityCurrentHoldersProfileTab) activeProfileTab).filterByHolderName("SPDR S&P 500 ETF");
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Equity Security Current Holdings Tab (Funds Tab) filtered by valid HolderName was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Equity Security Current Holdings Tab (Funds Tab) filtered by valid HolderName was expected to be empty:");
        ((EqSecurityCurrentHoldersProfileTab) activeProfileTab).filterByHolderName("SPDR S&P 500 ETF1");
        Assert.assertFalse(activeProfileTab.isContentLoaded(), "Equity Security Current Holdings Tab (Funds Tab) filtered by invalid HolderName was expected to contain data:");
        Assert.assertTrue(activeProfileTab.isProfileTabEmpty(), "Equity Security Current Holdings Tab (Funds Tab) filtered by invalid HolderName was expected to be empty:");

        eqSecurityFullProfile.selectTab("Analytics");
        activeProfileTab = new EqSecurityAnalyticsProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Equity Security Analytics Tab (Concentration Tab) was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Equity Security Analytics Tab (Concentration Tab) was expected to be empty:");
        ((EqSecurityAnalyticsProfileTab) activeProfileTab).selectStyleTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Equity Security Analytics Tab (Style Tab) was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Equity Security Analytics Tab (Style Tab) was expected to be empty:");
        ((EqSecurityAnalyticsProfileTab) activeProfileTab).selectTurnoverTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Equity Security Analytics Tab (Turnover Tab) was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Equity Security Analytics Tab (Turnover Tab) was expected to be empty:");

        eqSecurityFullProfile.selectTab("Peers");
        activeProfileTab = new EqSecurityPeersProfileTab();
        Assert.assertFalse(activeProfileTab.isContentLoaded(), "Equity Security Peers Tab was expected to contain data:");
        Assert.assertTrue(activeProfileTab.isProfileTabEmpty(), "Equity Security Peers Tab was expected to be empty:");


        navigation.switchToOfflineMode();

        eqSecurityFullProfile.selectTab("Current Holders");
        activeProfileTab = new EqSecurityCurrentHoldersProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Equity Security Current Holdings Tab (Institutions Tab) was expected to contain cached data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Equity Security Current Holdings Tab (Institutions Tab) was expected to be empty:");
        Assert.assertTrue(activeProfileTab.isSearchFilterUnavailableInOfflineMode(), "Searching on Equity Security Current Holdings Tab (Institutions Tab) was expected to be unable:");
        ((EqSecurityCurrentHoldersProfileTab) activeProfileTab).selectFundsTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Equity Security Current Holdings Tab (Funds Tab) was expected to contain cached data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Equity Security Current Holdings Tab (Funds Tab) was expected to be empty:");
        Assert.assertTrue(activeProfileTab.isSearchFilterUnavailableInOfflineMode(), "Searching on Equity Security Current Holdings Tab (Funds Tab) was expected to be unable:");
    }

    @Test
    public void verifyFixedIncomeSecurityProfileTabReady() {
        navigation.openSecurities();
        SecuritySearchPanel securitySearchPanel = (SecuritySearchPanel)navigation.openSearch();
        securitySearchPanel.selectAssetClass(UITitles.AssetClass.FIXED_INCOME);
        securitySearchPanel.setSearchField(couponName);
        securitySearchPanel.search();
        new SecuritySearchTab().openFullProfile(couponName);

        fiSecurityFullProfile = new FiSecurityFullProfile();

        fiSecurityFullProfile.selectTab("Details");
        activeProfileTab = new FiSecurityDetailsProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Fixed Income Security Details Tab was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Fixed Income Security Details Tab was expected to be empty:");

        fiSecurityFullProfile.selectTab("Current Holders");
        activeProfileTab = new FiSecurityCurrentHoldersProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Fixed Income Security Current Holdings Tab (Institutions Tab) was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Fixed Income Security Current Holdings Tab (Institutions Tab) was expected to be empty:");
        ((FiSecurityCurrentHoldersProfileTab) activeProfileTab).filterByHolderName("Franklin Advisers, Inc.");
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Fixed Income Security Current Holdings Tab (Institutions Tab) filtered by valid HolderName was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Fixed Income Security Current Holdings Tab (Institutions Tab) filtered by valid HolderName was expected to be empty:");
        ((FiSecurityCurrentHoldersProfileTab) activeProfileTab).filterByHolderName("Franklin Income Fund");
        Assert.assertFalse(activeProfileTab.isContentLoaded(), "Fixed Income Security Current Holdings Tab (Institutions Tab) filtered by invalid HolderName was expected to contain data:");
        Assert.assertTrue(activeProfileTab.isProfileTabEmpty(), "Fixed Income Security Current Holdings Tab (Institutions Tab) filtered by invalid HolderName was expected to be empty:");

        ((FiSecurityCurrentHoldersProfileTab) activeProfileTab).selectFundsTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Fixed Income Security Current Holdings Tab (Funds Tab) was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Fixed Income Security Current Holdings Tab (Funds Tab) was expected to be empty:");
        ((FiSecurityCurrentHoldersProfileTab) activeProfileTab).filterByHolderName("M&G Corporate Bond Fund");
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Fixed Income Security Current Holdings Tab (Funds Tab) filtered by valid HolderName was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Fixed Income Security Current Holdings Tab (Funds Tab) filtered by valid HolderName was expected to be empty:");
        ((FiSecurityCurrentHoldersProfileTab) activeProfileTab).filterByHolderName("Franklin Advisers, Inc.");
        Assert.assertFalse(activeProfileTab.isContentLoaded(), "Fixed Income Security Current Holdings Tab (Funds Tab) filtered by invalid HolderName was expected to contain data:");
        Assert.assertTrue(activeProfileTab.isProfileTabEmpty(), "Fixed Income Security Current Holdings Tab (Funds Tab) filtered by invalid HolderName was expected to be empty:");

        fiSecurityFullProfile.selectTab("Debt Securities Of Issuer");
        activeProfileTab = new FiDebtSecuritiesOfIssuerProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Fixed Income Security Debt Securities Of Issuer Tab was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Fixed Income Security Debt Securities Of Issuer Tab was expected to be empty:");


        navigation.switchToOfflineMode();

        fiSecurityFullProfile.selectTab("Current Holders");
        activeProfileTab = new EqSecurityCurrentHoldersProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Fixed Income Security Current Holdings Tab (Institutions Tab) was expected to contain cached data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Fixed Income Security Current Holdings Tab (Institutions Tab) was expected to be empty:");
        Assert.assertTrue(activeProfileTab.isSearchFilterUnavailableInOfflineMode(), "Searching on Fixed Income Security Current Holdings Tab (Institutions Tab) was expected to be unable:");

        ((EqSecurityCurrentHoldersProfileTab) activeProfileTab).selectFundsTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Fixed Income Security Current Holdings Tab (Funds Tab) was expected to contain cached data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Fixed Income Security Current Holdings Tab (Funds Tab) was expected to be empty:");
        Assert.assertTrue(activeProfileTab.isSearchFilterUnavailableInOfflineMode(), "Searching on Fixed Income Security Current Holdings Tab (Funds Tab) was expected to be unable:");
    }

    @Test
    public void navigateBetweenFundTabs(){
        navigation.openFunds();

        navigation.selectTab(UITitles.PanelTabs.RECENTLY_VIEWED);
        activeTab = new FundRecentlyViewedTab();
        Assert.assertFalse(activeTab.isContentLoaded(), "Funds Recently Viewed Tab was expected to contain data:");
        Assert.assertTrue(activeTab.isActiveTabEmpty(), "Funds Recently Viewed Tab was expected to be empty:");
    }

    @Test
    public void verifyFundProfileTabReady(){
        navigation.openFundProfile(fundName);

        fundFullProfile = new FundFullProfile();

        fundFullProfile.selectTab("Details");
        activeProfileTab = new FundDetailsProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Fund Details Tab was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Fund Details Tab was expected to be empty:");

        fundFullProfile.selectTab("Ownership");
        activeProfileTab = new FundOwnershipProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Fund Details Tab (Equity Tab) was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Fund Details Tab (Equity Tab) was expected to be empty:");
        ((FundOwnershipProfileTab) activeProfileTab).selectFixedIncomeTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Fund Details Tab (Fixed Income Tab) was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Fund Details Tab (Fixed Income Tab) was expected to be empty:");

        fundFullProfile.selectTab("Current Holdings");
        activeProfileTab = new FundCurrentHoldingsProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Fund Current Holdings Tab (Equity Tab) was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Fund Current Holdings Tab (Equity Tab) was expected to be empty:");
        ((FundCurrentHoldingsProfileTab) activeProfileTab).filterBySymbolOrSecurityName("Apple");
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Fund Current Holdings Tab (Equity Tab) filtered by valid SecurityName was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Fund Current Holdings Tab (Equity Tab) filtered by valid SecurityName was expected to be empty:");
        ((FundCurrentHoldingsProfileTab) activeProfileTab).filterBySymbolOrSecurityName("Co Res");
        Assert.assertFalse(activeProfileTab.isContentLoaded(), "Fund Current Holdings Tab (Equity Tab) filtered by invalid SecurityName was expected to contain data:");
        Assert.assertTrue(activeProfileTab.isProfileTabEmpty(), "Fund Current Holdings Tab (Equity Tab) filtered by invalid SecurityName was expected to be empty:");

        ((FundCurrentHoldingsProfileTab) activeProfileTab).selectFixedIncomeTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Fund Current Holdings Tab (Fixed Income Tab) was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Fund Current Holdings Tab (Fixed Income Tab) was expected to be empty:");
        ((FundCurrentHoldingsProfileTab) activeProfileTab).filterBySymbolOrSecurityName("Concho Res");
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Fund Current Holdings Tab (Equity Tab) filtered by valid SecurityName was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Fund Current Holdings Tab (Equity Tab) filtered by valid SecurityName was expected to be empty:");
        ((FundCurrentHoldingsProfileTab) activeProfileTab).filterBySymbolOrSecurityName("Google");
        Assert.assertFalse(activeProfileTab.isContentLoaded(), "Fund Current Holdings Tab (Equity Tab) filtered by invalid SecurityName was expected to contain data:");
        Assert.assertTrue(activeProfileTab.isProfileTabEmpty(), "Fund Current Holdings Tab (Equity Tab) filtered by invalid SecurityName was expected to be empty:");

        fundFullProfile.selectTab("Actions");
        activeProfileTab = new FundActionsProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Fund Actions Tab (Recent Tab) was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Fund Actions Tab (Recent Tab) was expected to be empty:");
        ((FundActionsProfileTab) activeProfileTab).selectActivitiesTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Fund Actions Tab (Activities Tab) was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Fund Actions Tab (Activities Tab) was expected to be empty:");

        fundFullProfile.selectTab("Additional Info");
        activeProfileTab = new FundAdditionalInfoProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Fund Additional Info Tab was expected to contain data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Fund Additional Info Tab was expected to be empty:");


        navigation.switchToOfflineMode();

        fundFullProfile.selectTab("Current Holdings");
        activeProfileTab = new FundCurrentHoldingsProfileTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Fund Current Holdings Tab (Equity Tab) was expected to contain cached data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Fund Current Holdings Tab (Equity Tab) was expected to be empty:");
        Assert.assertTrue(activeProfileTab.isSearchFilterUnavailableInOfflineMode(), "Searching on Fund Current Holdings Tab (Equity Tab) was expected to be unable:");
        ((FundCurrentHoldingsProfileTab) activeProfileTab).selectFixedIncomeTab();
        Assert.assertTrue(activeProfileTab.isContentLoaded(), "Fund Current Holdings Tab (Equity Tab) was expected to contain cached data:");
        Assert.assertFalse(activeProfileTab.isProfileTabEmpty(), "Fund Current Holdings Tab (Equity Tab) was expected to be empty:");
        Assert.assertTrue(activeProfileTab.isSearchFilterUnavailableInOfflineMode(), "Searching on Fund Current Holdings Tab (Equity Tab) was expected to be unable:");
    }
}
