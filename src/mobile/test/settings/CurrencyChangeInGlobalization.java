package ipreomobile.test.settings;

import ipreomobile.data.*;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.contacts.ContactFullProfile;
import ipreomobile.ui.funds.FundFullProfile;
import ipreomobile.ui.hamburgerItems.Hamburger;
import ipreomobile.ui.hamburgerItems.SettingsView;
import ipreomobile.ui.institutions.InstitutionFullProfile;
import ipreomobile.ui.profiles.fullProfileTabs.*;
import ipreomobile.ui.profiles.overviewCards.ContactHoldingsCard;
import ipreomobile.ui.profiles.overviewCards.FundHoldingsCard;
import ipreomobile.ui.profiles.overviewCards.InstitutionalHoldingsCard;
import ipreomobile.ui.securities.EqSecurityFullProfile;
import ipreomobile.ui.securities.FiSecurityFullProfile;
import ipreomobile.ui.settings.GlobalizationTab;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ipreomobile.ui.UITitles.ProfileTab.*;

public class CurrencyChangeInGlobalization extends BaseTest {
    private Hamburger        hamburger;
    private SettingsView     settingsView;
    private GlobalizationTab globalizationTab;
    private InstitutionData  institutionData;
    private String           institutionName;
    private FundData         fundData;
    private String           fundName;
    private ContactData      contactData;
    private EquityData       equityData;
    private FixedIncomeData  fixedIncomeData;
    private String           equitySecurityName;
    private String           equitySecurityMarketName;
    private String           fixedIncomeSecurityName;


    @BeforeMethod
    public void openSettingsAndChangeCurrency(Method method) {
        institutionData = new InstitutionData();
        institutionName = institutionData.getName();
        fundData = new FundData();
        fundName = fundData.getName();
        contactData = new ContactData();
        equityData = new EquityData();
        equitySecurityName = equityData.getSecurityName();
        equitySecurityMarketName = equityData.getSecurityMarketName();
        fixedIncomeData = new FixedIncomeData();
        fixedIncomeSecurityName = fixedIncomeData.getCouponName();

        hamburger = navigation.openHamburger();
        settingsView = hamburger.openSettings();
        globalizationTab = settingsView.openGlobalizationTab();
        globalizationTab.setCurrency("Euro (EUR)", "€");
    }

    @Test
    public void verifyCurrencyInInstitutions() {
        String[] ownershipTableHeadersInInstitutionProfile = {
                "TOP BUYS",
                "TOP BUY-INS",
                "TOP SELLS",
                "TOP SELL-OUTS"
        };
        //Test pages
        InstitutionFullProfile institutionFullProfile;
        InstitutionDetailsProfileTab institutionDetailsProfileTab;
        InstitutionOwnershipProfileTab institutionOwnershipProfileTab;
        InstitutionTargetingProfileTab institutionTargetingTab;
        InstitutionFundsProfileTab institutionsFundsProfileTab;

        //Test data
        institutionData.setTestCaseName("verifyDetailsTabOnInstitutionFullProfile");
        fundData.setTestCaseName("verifyFundsTabOnInstitutionFullProfile");
        fundData.loadDataSetByTag("holders");
        String holdersFundName = fundData.getName();
        fundData.loadDataSetByTag("other");
        String otherFundName = fundData.getName();

        // Institution Profile Overview
        navigation
                .searchInstitutions(institutionName)

                .openProfileOverview(institutionName);
        InstitutionalHoldingsCard institutionalHoldingsCard = new InstitutionalHoldingsCard();
        institutionalHoldingsCard.verifyCurrency();

        // Institution Full Profile
        institutionFullProfile = navigation.openInstitutionProfile(institutionName);

        institutionFullProfile.selectTab(DETAILS);
        institutionDetailsProfileTab = new InstitutionDetailsProfileTab();
        institutionDetailsProfileTab
                .verifyAllLabelsPresent(institutionData.getLabels());

        institutionFullProfile.selectTab(OWNERSHIP);
        institutionOwnershipProfileTab = new InstitutionOwnershipProfileTab();
        institutionOwnershipProfileTab
                .verifyCurrencyInOwnershipSummaryTable()
                .verifyCurrencyInTopHoldingsHeader();
        for (String tableHeader : ownershipTableHeadersInInstitutionProfile) {
            institutionOwnershipProfileTab
                    .verifyCurrencyInTopBuySellTableHeader(tableHeader);
        }

        institutionFullProfile.selectTab(TARGETING);
        institutionTargetingTab = new InstitutionTargetingProfileTab();
        institutionTargetingTab
                .verifyTargetingDetailsPresent();

        institutionFullProfile.selectTab(FUNDS);
        institutionsFundsProfileTab = new InstitutionFundsProfileTab();
        institutionsFundsProfileTab
                .verifyCurrencyInHoldersFundDetails(holdersFundName)
                .verifyCurrencyInOtherFundDetails(otherFundName);
    }

    @Test
    public void verifyCurrencyInFunds() {
        String[] ownershipTableHeadersInFundProfile = {
                "TOP BUYS",
                "TOP SELLS",
        };
        // Test pages
        FundFullProfile fundFullProfile;
        FundDetailsProfileTab fundDetailsProfileTab;
        FundOwnershipProfileTab fundOwnershipProfileTab;

        // Test data
        fundData.setTestCaseName("verifyDetailsTabOnFundFullProfile");

        // Fund Profile Overview
        navigation
                .searchFunds(fundName)

                .openProfileOverview(fundName);
        FundHoldingsCard fundHoldingsCard = new FundHoldingsCard();
        fundHoldingsCard
                .verifyCurrency();

        // Fund Full Profile
        fundFullProfile = navigation.openFundProfile(fundName);

        fundFullProfile.selectTab(DETAILS);
        fundDetailsProfileTab = new FundDetailsProfileTab();
        fundDetailsProfileTab
                .verifyAllLabelsPresent(fundData.getLabels());

        fundFullProfile.selectTab(OWNERSHIP);
        fundOwnershipProfileTab = new FundOwnershipProfileTab();
        fundOwnershipProfileTab
                .verifyCurrencyInOwnershipSummaryTable()
                .verifyCurrencyInTopHoldingsHeader();


        for (String tableHeader : ownershipTableHeadersInFundProfile) {
            fundOwnershipProfileTab
                    .verifyCurrencyInTopBuySellTableHeader(tableHeader);
        }
    }

    @Test
    public void verifyCurrencyInContacts() {
        // Test pages
        ContactFullProfile contactFullProfile;
        ContactOwnershipProfileTab contactOwnershipProfileTab;
        ContactFundsProfileTab contactFundsProfileTab;

        // Test data
        contactData.setTestCaseName("verifyFundsTabOnContactFullProfile");
        String contactName = contactData.getName();
        fundData.setTestCaseName("verifyFundsTabOnContactFullProfile");
        fundData.loadDataSetByTag("other");
        String fundName = fundData.getName();

        // Contact Profile Overview
        navigation
                .searchContacts(contactName)
                .openProfileOverview(contactName);
        ContactHoldingsCard contactHoldingsCard = new ContactHoldingsCard();
        InstitutionalHoldingsCard institutionalHoldingsCard = new InstitutionalHoldingsCard();
        contactHoldingsCard
                .verifyCurrency();
        institutionalHoldingsCard
                .verifyCurrency();

        // Contact Full Profile
        contactFullProfile = navigation.openContactProfile(contactName);

        contactFullProfile.selectTab(OWNERSHIP);
        contactOwnershipProfileTab = new ContactOwnershipProfileTab();
        contactOwnershipProfileTab
                .verifyCurrencyInTableHeader();

        contactFullProfile.selectTab(FUNDS);
        contactFundsProfileTab = new ContactFundsProfileTab();
        contactFundsProfileTab
                .verifyCurrencyInOtherFundDetails(fundName);
    }

    @Test
    public void verifyCurrencyInEquitySecurities() {
        // Test pages
        EqSecurityFullProfile eqSecurityFullProfile;
        EqSecurityDetailsProfileTab eqSecurityDetailsProfileTab;
        EqSecurityTop10ProfileProfileTab eqSecurityTop10ProfileProfileTab;
        EqSecurityCurrentHoldersProfileTab eqSecurityCurrentHoldersProfileTab;
        EqSecurityPeersProfileTab eqSecurityPeersProfileTab;

        // Test data
        equityData.setTestCaseName("verifyDetailsTabOnEquitySecurityFullProfile");
        List<String> equityDetailsLabels = equityData.getLabels();

        equityData.setTestCaseName("verifyCachedContentsOnEquitySecurityProfilePeersTab");
        equityData.loadDataSetByTag("defaultPeer");
        String defaultPeer = equityData.getSecurityName();

        equityData.setTestCaseName("verifyCurrencyInEquitySecurities");
        equityData.loadDataSetByTag("myPeer");
        String myPeer      = equityData.getSecurityName();

        institutionData.setTestCaseName("verifyListEntriesSelectionIsNotPersistent");

        // Eq Security Full Profile
        eqSecurityFullProfile = navigation.openEquitySecurityProfile(equitySecurityName, equitySecurityMarketName);

        eqSecurityFullProfile.selectTab(DETAILS);
        eqSecurityDetailsProfileTab = new EqSecurityDetailsProfileTab();
        eqSecurityDetailsProfileTab
                .verifyAllLabelsPresent(equityDetailsLabels);

        eqSecurityFullProfile.selectTab(TOP_10);
        eqSecurityTop10ProfileProfileTab = new EqSecurityTop10ProfileProfileTab();
        eqSecurityTop10ProfileProfileTab
                .verifyCurrencyInInInstitutionsTab()
                .verifyCurrencyInFundsTab();

        eqSecurityFullProfile.selectTab(CURRENT_HOLDERS);
        eqSecurityCurrentHoldersProfileTab = new EqSecurityCurrentHoldersProfileTab();
        eqSecurityCurrentHoldersProfileTab
                .verifyCurrencyInDetailsForInstitutionProfile(institutionData.getName())
                .verifyCurrencyInDetailsForFundProfile(fundName);

        eqSecurityFullProfile.selectTab(PEERS);
        eqSecurityPeersProfileTab = new EqSecurityPeersProfileTab();
        eqSecurityPeersProfileTab
                .verifyCurrencyInDetailsForDefaultPeerList(defaultPeer)
                .verifyCurrencyInDetailsForMyPeerList(myPeer);
    }

    @Test
    public void verifyCurrencyInFixedIncomeSecurities() {
        // Test pages
        FiSecurityFullProfile fiSecurityFullProfile;
        FiSecurityCurrentHoldersProfileTab fiSecurityCurrentHoldersProfileTab;

        // Fixed Security Full Profile
        fiSecurityFullProfile = navigation.openFixedIncomeSecurityProfile(fixedIncomeSecurityName);

        String institutionName = fundData.getInstitutionName();
        fundData.setTestCaseName("verifyCurrencyInFixedIncomeSecurities");
        String fundName = fundData.getName();

        fiSecurityFullProfile.selectTab(CURRENT_HOLDERS);
        fiSecurityCurrentHoldersProfileTab = new FiSecurityCurrentHoldersProfileTab();
        fiSecurityCurrentHoldersProfileTab
                .verifyCurrencyInDetailsForInstitutionProfile(institutionName)
                .verifyCurrencyInDetailsForFundProfile(fundName);
    }
}
