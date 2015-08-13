package ipreomobile.test.profiles.institution;

import ipreomobile.core.Logger;
import ipreomobile.data.*;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.blocks.overlay.PrimaryContactsOverlay;
import ipreomobile.ui.institutions.InstitutionSearchTab;
import ipreomobile.ui.profiles.fullProfileTabs.*;
import org.testng.Assert;
import org.testng.annotations.Test;


import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.institutions.InstitutionFullProfile;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ipreomobile.core.DateTimeHelper.*;
import static ipreomobile.ui.UITitles.ProfileTab.*;

public class InstitutionFullProfileSmoke extends BaseTest {

    private static final String PRIORITY_ONE = "P1";
    private static final String PRIORITY_TWO = "P2";
    private InstitutionData institutionData;
    private String institutionName;

    private InstitutionSearchTab institutionSearchTab;
    private InstitutionFullProfile institutionFullProfile;
    private InstitutionDetailsProfileTab institutionDetailsProfileTab;
    private InstitutionOwnershipProfileTab institutionOwnershipProfileTab;
    private InstitutionTargetingProfileTab institutionTargetingProfileTab;
    private InstitutionFocusProfileTab institutionFocusProfileTab;
    private InstitutionActionsProfileTab institutionActionsProfileTab;
    private InstitutionFundsProfileTab institutionFundsProfileTab;
    private InstitutionCurrentHoldingsProfileTab institutionCurrentHoldingsProfileTab;
    private InstitutionContactsProfileTab institutionContactsProfileTab;
    private InstitutionAdditionalInfoProfileTab institutionAdditionalInfoProfileTab;

    private BankDetailsProfileTab bankDetailsProfileTab;
    private BankDetailsProfileTab creditUnionDetailsProfileTab;
    private BankFocusProfileTab bankFocusProfileTab;
    private BankFocusProfileTab creditUnionFocusProfileTab;

    @BeforeMethod
    public void setupTestData(Method testMethod) {
        institutionData = new InstitutionData();
        institutionData.setTestCaseName(testMethod.getName());
        institutionName = institutionData.getName();

        navigation.openInstitutions();
        navigation.openInstitutionProfile(institutionName);
        institutionFullProfile = new InstitutionFullProfile();
    }

    @Test(groups = PRIORITY_ONE)
    public void openFullProfileFromSearchResults() {
        institutionFullProfile
                .verifyProfileName(institutionName)
                .verifyTabsRowExists()
                .verifyActiveTab("Details");

        navigation
                .verifyPageTitle("Institution Profile")
                .verifyBackButtonPresent()
                .back()

                .selectRecentlyViewedTab()
                .verifyItemPresentInList(institutionName)

                .openFullProfile(institutionName)
                .verifyProfileName(institutionName);
    }

    @Test(groups = PRIORITY_TWO)
    public void verifyControlsOnInstitutionFullProfile() {
        String primaryContactName = institutionData.getPrimaryContact();
        ContactData contactWithEmail = new ContactData();
        String city = institutionData.getLocation();
        String customerName = System.getProperty("test.customer");

        boolean isActive = true,
                isSubscribed = true,
                isInCrm = true;

        institutionFullProfile
                .verifyActiveAndSubscribedBadge(isActive, isSubscribed)
                .verifyCrmBadge(isInCrm);

        institutionFullProfile
                .verifySide(institutionData.getSide())
                .verifyPhone(institutionData.getPhone())
                .verifyBdVersusCrmInfo()
                .verifyCompanySiteLink()

                .openPrimaryContactsOverlay()
                .verifyContactNamePresent(primaryContactName)

                .goToContactProfileAndVerifyName(primaryContactName)
                .verifyProfileName(primaryContactName);
        navigation.back();

        //TODO: redesign ConfirmationDialog class
/*        institutionFullProfile
                .openPrimaryContactsOverlay()
                .verifyContactEmail(contactWithEmail.getName(), contactEmail)
                .close();*/

        institutionFullProfile
                .openMapPreview()
                .verifyTitle("Institution Address")
                .verifyAddressPart(city)

                .openMap()
                .verifyPageTitle("Map")
                .verifyLocationName(institutionName)
                .close();

        institutionFullProfile
                .addActivity()
                .verifyExternalParticipantPresent(institutionName)
                .closeActivityDialog();

        institutionFullProfile
                .openFixDataOverlay()
                .verifyField("Customer:", customerName)
                .verifyField("Institution Name:", institutionName)
                .verifyWhatNeedsToBeFixedField()
                .verifyThankYouField()
                .cancel();

        institutionFullProfile
                .openLastActionOverlay()
                .editActivity()
                .verifyExternalParticipantPresent(institutionName)
                .closeActivityDialog();
    }

    @Test(groups = PRIORITY_TWO)
    public void verifyPrimaryContactsDataOnInstitutionFullProfile() {
        ContactData contactData = new ContactData();
        PrimaryContactsOverlay primaryContacts = institutionFullProfile.openPrimaryContactsOverlay();

        for (String contactName : primaryContacts.getContactNames()) {
            contactData.clear();
            contactData.setName(contactName);
            contactData.setCompanyName(institutionName);
            contactData.setPhone(primaryContacts.getContactPhone(contactName));
            contactData.setEmail(primaryContacts.getContactEmail(contactName));
            contactData.setJobFunction(primaryContacts.getContactJobFunction(contactName));

            primaryContacts.goToContactProfileAndVerifyName(contactName);
            navigation.back();
            institutionFullProfile.waitReady();
            institutionFullProfile.openPrimaryContactsOverlay();
        }
    }

    @Test(groups = PRIORITY_TWO)
    public void verifyLastActionOnInstitutionFullProfile() {
        ActivityData activity = new ActivityData();
        activity.clear();
        activity.setSubject(getCaseAndDateTimeBasedName("Action"));
        activity.setType("Analyst Day");

        String currentDate = getCurrentDateStr();
        activity.setStartDate(currentDate);
        activity.setEndDate(currentDate);

        String startTime = getTimeBeforeCurrent(30);
        String endTime = getTimeBeforeCurrent(10);

        activity.setStartTime(startTime);
        activity.setEndTime(endTime);
        Logger.logMessage("Time slot chosen for activity: " + activity.getTimeSlot());

        Logger.logDebug("Activity details: \n" + activity.describe());
        institutionFullProfile.addActivity()
                .setSubject(activity.getSubject())
                .selectType(activity.getType())
                .selectStartDateTime(activity.getStartDateTime())
                .verifyStartDateTime(activity.getStartDateTime())
                .selectEndDateTime(activity.getEndDateTime())
                .verifyEndDateTime(activity.getEndDateTime())
                .saveActivity();

        institutionFullProfile.verifyLastActionDate(activity.getStartDate());
        institutionFullProfile.openLastActionOverlay()
                .verifySubject(activity.getSubject())
                .verifyType(activity.getType())
                .verifyTimeSlot(activity.getTimeSlot());
        institutionFullProfile.closeOverlay();
    }

    @Test
    public void verifyDetailsTabOnInstitutionFullProfile() {
        List<String> detailsTabLabels = institutionData.getLabels();

        institutionFullProfile.selectTab(UITitles.ProfileTab.DETAILS);
        institutionDetailsProfileTab = new InstitutionDetailsProfileTab();
        for (String entry : detailsTabLabels) {
            institutionDetailsProfileTab
                    .verifyLabelPresent(entry);
            Assert.assertFalse(institutionDetailsProfileTab.getValue(entry).isEmpty(), "Value for '" + entry + "' was empty, but should not be.");
        }
        institutionDetailsProfileTab
                .verifyHoldingsChart("Asset Allocation")
                .verifyHoldingsChart("Fixed Income Portfolio");
    }

    @Test
    public void verifyOwnershipTabOnInstitutionFullProfile() {
        List<String> topBuySellTablesToVerify = new ArrayList<>(Arrays.asList(
                "TOP BUY-INS",
                "TOP SELLS",
                "TOP SELL-OUTS",
                "TOP BUYS"));
        String firstSecurityName;

        institutionFullProfile.selectTab(UITitles.ProfileTab.OWNERSHIP);
        institutionOwnershipProfileTab = new InstitutionOwnershipProfileTab();
        institutionOwnershipProfileTab
                .verifyActiveTab(InstitutionOwnershipProfileTab.EQ)
                .verifyTabPresent(InstitutionOwnershipProfileTab.FI);
        institutionOwnershipProfileTab
                .verifyPublicDataShown()
                .showSurveillanceData()
        //TODO: verify data when switching to surveillance results. Now only Eye button state is verified
                .verifySurveillanceDataShown()
                .showPublicData()
                .verifyDefaultTicker()
                .verifyCurrencyInTopHoldingsHeader()
                .verifyCurrencyInOwnershipSummaryTable()
                .verifyPositionHistoryChart()
                .verifyEquityOwnershipChart();

        firstSecurityName = institutionOwnershipProfileTab.getFirstEquityInTopHoldingsTable();
        institutionOwnershipProfileTab
                .openFirstEquityProfileFromTopHoldingsTable()
                .verifyProfileName(firstSecurityName);
        navigation.back();

        for (String tableHeader : topBuySellTablesToVerify) {
            institutionOwnershipProfileTab
                    .selectTopBuySellTable(tableHeader)
                .verifyCurrencyInTopBuySellTableHeader(tableHeader);
            verifyEquityInTopBuysAndSellsTable();
        }

        institutionOwnershipProfileTab
                .selectFixedIncomeTab();
        institutionOwnershipProfileTab
                .verifyFixedIncomePortfolioChart();

        firstSecurityName = institutionOwnershipProfileTab.getFirstFiInTopHoldingsTable();
        institutionOwnershipProfileTab
                .openFirstFixedIncomeProfileFromTopHoldingsTable()
                .verifyProfileName(firstSecurityName);
        navigation.back();

        for (String tableHeader : topBuySellTablesToVerify) {
            institutionOwnershipProfileTab
                    .selectTopBuySellTable(tableHeader)
                    .verifyCurrencyInTopBuySellTableHeader(tableHeader);
            verifyFixedIncomeInTopBuysAndSellsTable();
        }
    }

    private void verifyEquityInTopBuysAndSellsTable() {
        String topEquityName = institutionOwnershipProfileTab.getFirstCompanyNameInTopBuysAndSellsTable();
        institutionOwnershipProfileTab
                .openFirstEquityProfileFromTopBuysAndSellsTable()
                .verifyProfileName(topEquityName);
        Logger.logDebugScreenshot("Opened '" + topEquityName + "' equity profile summary from the table.");
        navigation.back();
        institutionOwnershipProfileTab
                .verifyActiveTab(InstitutionOwnershipProfileTab.EQ);
    }

    private void verifyFixedIncomeInTopBuysAndSellsTable() {
        String topFixedIncomeName = institutionOwnershipProfileTab.getFirstSecurityNameInTopBuysAndSellsTable();
        institutionOwnershipProfileTab
                .openFirstFixedIncomeProfileFromTopBuysAndSellsTable()
                .verifyProfileName(topFixedIncomeName);
        Logger.logDebugScreenshot("Opened '" + topFixedIncomeName + "'fixed income profile summary from the table.");
        navigation.back();
        institutionOwnershipProfileTab
                .verifyActiveTab(InstitutionOwnershipProfileTab.FI);
    }

    @Test
    public void verifyTargetingTabOnInstitutionFullProfile() {
        String[] growthFactors = {
                "Sales Growth",
                "EPS Growth",
                "L.T. Exp. Growth",
                "Margin Growth",
                "ROE"
        };
        String[] valuationFactors = {
                "Price / Sales",
                "Fwd. P / E",
                "P / Book",
                "EV / EBITDA",
                "P / CF"
        };
        String[] riskFactors = {
                "Mkt. Cap.",
                "Div. Yield",
                "Total Return",
                "Leverage",
                "EBIT / Int. Exp."
        };
        institutionFullProfile.selectTab(TARGETING);
        institutionTargetingProfileTab = new InstitutionTargetingProfileTab();
        institutionTargetingProfileTab
                .showTargetingChartsInfo()
                .hideTargetingChartsInfo()
                .verifyTargetingDetailsPresent()
                .verifyTargetingChartsInfo()

                .verifyFactorTableEntriesPresent("Growth", growthFactors)
                .verifyFactorTableEntriesPresent("Valuation", valuationFactors)
                .verifyFactorTableEntriesPresent("Risk", riskFactors);
    }

//failed on QX - known issue - a few rows with the same label
    @Test
    public void verifyFocusTabOnInstitutionFullProfile() {
        institutionData.loadDataSetByTag("equityFocus");
        List<String> eqEntries = institutionData.getLabels();

        institutionData.loadDataSetByTag("fixedIncomeFocus");
        List<String> fiEntries = institutionData.getLabels();

        institutionFullProfile.selectTab(UITitles.ProfileTab.FOCUS);
        institutionFocusProfileTab = new InstitutionFocusProfileTab();
        institutionFocusProfileTab
                .verifyActiveTab("Investment Approach");
//        institutionFocusProfileTab
//                .verifyEqInvestmentApproachPresence()
//                .verifyFiInvestmentApproachPresence();
//
//        institutionFocusProfileTab
//                .selectEquityFocusTab()
//                .verifyAllFocusTableLabelsPresent(eqEntries);

        institutionFocusProfileTab
                .selectFixedIncomeFocusTab()
                .verifyAllFocusTableLabelsPresent(fiEntries);
    }

    @Test
    public void verifyActionsTabOnInstitutionFullProfile() {
        String recentActivitySubject, recentTaskSubject;
        String activitySubject = new ActivityData().getSubject();
        String taskSubject = new TaskData().getSubject();

        institutionFullProfile.selectTab(UITitles.ProfileTab.ACTIONS);
        institutionActionsProfileTab = new InstitutionActionsProfileTab();

        institutionActionsProfileTab
                .verifyActiveTab("Recent");
        institutionActionsProfileTab
                .verifyActionsSummaryChartPresent()
                .verifyRecentActionsBlockPresent();

        recentActivitySubject = institutionActionsProfileTab.getRecentActivityData().getSubject();
        institutionActionsProfileTab
                .openRecentActivity()
                .verifySubject(recentActivitySubject);
        institutionActionsProfileTab
                .closeActionOverlay();

//TODO: verify external participant as text or subtext
//                .editActivity()
//                .verifyExternalParticipantPresent(institutionName)
//                .closeActivityDialog();

        recentTaskSubject = institutionActionsProfileTab.getRecentTaskData().getSubject();
        institutionActionsProfileTab
                .openRecentTask()
                .verifySubject(recentTaskSubject);
        institutionActionsProfileTab
                .closeActionOverlay();

//                .editActivity()
//                .verifyExternalParticipantPresent(institutionName)
//                .closeActivityDialog();

        institutionActionsProfileTab.selectActivitiesTab();
        institutionActionsProfileTab
                .selectActivity(activitySubject)
                .verifySubject(activitySubject);
        institutionActionsProfileTab
                .closeActionOverlay();

//                .editActivity()
//                .verifyExternalParticipantPresent(institutionName)
//                .closeActivityDialog();
        institutionActionsProfileTab
                .verifyActiveTab("Activities");

        institutionActionsProfileTab.selectTasksTab();
        institutionActionsProfileTab
                .selectTask(taskSubject)
                .verifySubject(taskSubject);
        institutionActionsProfileTab
                .closeActionOverlay();

//                .editActivity()
//                .verifyExternalParticipantPresent(institutionName)
//                .closeActivityDialog();
        institutionActionsProfileTab
                .verifyActiveTab("Tasks");
    }

    @Test
    public void verifyFundsTabOnInstitutionFullProfile() {
        int numberOfFundsToCheckSortingOrder = 5;
        String holdersFundName, otherFundName;
        FundData fundData = new FundData();

        fundData.loadDataSetByTag("holders");
        holdersFundName = fundData.getName();

        fundData.loadDataSetByTag("other");
        otherFundName = fundData.getName();

        String holdersTabName = "Holders";
        String otherTabName = "Other";

        institutionFullProfile.selectTab(UITitles.ProfileTab.FUNDS);
        institutionFundsProfileTab = new InstitutionFundsProfileTab();
        institutionFundsProfileTab
                .verifyActiveTab(holdersTabName)
                .verifyTabPresent(otherTabName);
        institutionFundsProfileTab
                .verifyHoldersFundDetailsPresent(holdersFundName)
                .verifyCurrencyInHoldersFundDetails(holdersFundName)
                .verifyDefaultTickerInHoldersFundDetails(holdersFundName)
                .verifyHoldersFundsSortingOrder(numberOfFundsToCheckSortingOrder)

                .openFundProfile(holdersFundName)
                .verifyProfileName(holdersFundName);
        navigation.back();

        institutionFundsProfileTab.waitReady();
        institutionFundsProfileTab
                .verifyActiveTab(holdersTabName);

        institutionFundsProfileTab
                .selectOtherTab();
        institutionFundsProfileTab
                .verifyOtherFundDetailsPresent(otherFundName)
                .verifyCurrencyInOtherFundDetails(otherFundName)
                .verifyOtherFundsSortingOrder(numberOfFundsToCheckSortingOrder)

                .openFundProfile(otherFundName)
                .verifyProfileName(otherFundName);
        navigation.back();

        institutionFundsProfileTab.waitReady();
        institutionFundsProfileTab
                .verifyActiveTab(otherTabName);
    }

    @Test
    public void verifyCurrentHoldingsTabOnInstitutionFullProfile() {
        String equitySecurityName, fixedIncomeSecurityName, equityTicker;

        EquityData equityData = new EquityData();
        equitySecurityName = equityData.getSecurityName();
        equityTicker = equityData.getTickerName();

        FixedIncomeData fixedIncomeData = new FixedIncomeData();
        fixedIncomeSecurityName = fixedIncomeData.getCouponName();

        institutionFullProfile.selectTab(UITitles.ProfileTab.CURRENT_HOLDINGS);
        institutionCurrentHoldingsProfileTab = new InstitutionCurrentHoldingsProfileTab();
        institutionCurrentHoldingsProfileTab
                .verifyActiveTab("Equity")
                .verifyTabPresent("Fixed Income");
        institutionCurrentHoldingsProfileTab
                .verifySecurityPresent(equitySecurityName)
                .verifyEquitySecurityDetailsPresent(equitySecurityName)

                .selectEquitySecurity(equitySecurityName)
                .verifyProfileName(equitySecurityName);
        navigation.back();

        institutionCurrentHoldingsProfileTab
                .verifySecurityAbsent(fixedIncomeSecurityName);

        institutionCurrentHoldingsProfileTab
                .selectFixedIncomeTab();
        institutionCurrentHoldingsProfileTab
                .verifySecurityPresent(fixedIncomeSecurityName)
                .verifyFixedIncomeSecurityDetailsPresent(fixedIncomeSecurityName)

                .selectFixedIncomeSecurity(fixedIncomeSecurityName)
                .verifyProfileName(fixedIncomeSecurityName);
        navigation.back();

        institutionCurrentHoldingsProfileTab
                .verifySecurityAbsent(equitySecurityName)
                .filterBySymbolOrSecurityName(equityTicker)
                .verifyListEmpty();
    }

    @Test
    public void verifyContactsTabOnInstitutionFullProfile() {
        String invalidContactName = "NoSuchName";
        String contactName = institutionData.getPrimaryContact();
        institutionFullProfile.selectTab(UITitles.ProfileTab.CONTACTS);
        institutionContactsProfileTab = new InstitutionContactsProfileTab();

        institutionContactsProfileTab
                .verifyActiveTab("CRM Contacts")
                .verifyTabPresent("Search Contacts");
        institutionContactsProfileTab
                .goToCrmContactAndVerifyName(contactName)
                .verifyProfileName(contactName);
        navigation.back();

        institutionContactsProfileTab
                .selectSearchContactsTab();
        institutionContactsProfileTab
                .filterContacts(contactName)
                .selectContact(contactName)
                .verifyProfileName(contactName);
        navigation.back();

        institutionContactsProfileTab
                .verifyActiveTab("Search Contacts");

        institutionContactsProfileTab
                .filterContacts(invalidContactName)
                .verifyListEmpty();
    }

    @Test
    public void verifyAdditionalInfoTabOnInstitutionFullProfile() {
        List<String> additionalInfoLabels = new ArrayList<>(Arrays.asList(
                "Telephone",
                "BD Account Type",
                "BD Account Style"
        ));

        institutionFullProfile.selectTab(UITitles.ProfileTab.ADDITIONAL_INFO);
        institutionAdditionalInfoProfileTab = new InstitutionAdditionalInfoProfileTab();
        for (String entry : additionalInfoLabels) {
            institutionAdditionalInfoProfileTab.verifyAdditionalInfoFieldPresent(entry);
        }
    }

    @Test
    public void verifyDetailsTabOnBankFullProfile() {
        List<String> detailsTabLabels = institutionData.getLabels();
        String bankName = institutionData.getName();
        navigation
                .verifyPageTitle("Bank Profile");
        institutionFullProfile
                .verifyProfileName(bankName)
                .verifyActiveTab("Details")
                .verifyTabsRowExists();
        bankDetailsProfileTab = new BankDetailsProfileTab();
        for (String entry : detailsTabLabels) {
            bankDetailsProfileTab
                    .verifyLabelPresent(entry);
            Assert.assertFalse(bankDetailsProfileTab.getValue(entry).isEmpty(), "Value for '" + entry + "' was empty, but should not be.");
        }
        bankDetailsProfileTab
                .verifyTablePresent("Maturity Breakdown")
                .verifyCurrencyInTable("Maturity Breakdown")
                .verifyTablePresent("Asset Breakdown")
                .verifyCurrencyInTable("Asset Breakdown")

                .verifyValue("Institution Type", "Bank")
                .verifyHoldingsChart("Asset Allocation")
                .verifyHoldingsChart("Fixed Income Portfolio");
    }

    @Test
    public void verifyFocusTabOnBankFullProfile() {
        institutionFullProfile.selectTab(FOCUS);
        bankFocusProfileTab = new BankFocusProfileTab();
        bankFocusProfileTab.verifyOverviewSectionPresent();
    }

    @Test
    public void verifyDetailsTabOnCreditUnionFullProfile() {
        List<String> detailsTabLabels = institutionData.getLabels();
        String creditUnionName = institutionData.getName();
        navigation
                .verifyPageTitle("Credit Union Profile");
        institutionFullProfile
                .verifyProfileName(creditUnionName)
                .verifyActiveTab("Details")
                .verifyTabsRowExists();

        creditUnionDetailsProfileTab = new BankDetailsProfileTab();
        for (String entry : detailsTabLabels) {
            creditUnionDetailsProfileTab
                    .verifyLabelPresent(entry);
            Assert.assertFalse(creditUnionDetailsProfileTab.getValue(entry).isEmpty(), "Value for '" + entry + "' was empty, but should not be.");
        }
        creditUnionDetailsProfileTab
                .verifyTablePresent("Asset Breakdown")
                .verifyCurrencyInTable("Asset Breakdown")

                .verifyValue("Institution Type", "Credit Union")
                .verifyHoldingsChart("Asset Allocation")
                .verifyHoldingsChart("Fixed Income Portfolio");
    }
    @Test
    public void verifyFocusTabOnCreditUnionFullProfile() {
        institutionFullProfile.selectTab(FOCUS);
        creditUnionFocusProfileTab = new BankFocusProfileTab();
        creditUnionFocusProfileTab.verifyAllFocusTableLabelsPresent(institutionData.getLabels());
    }




}
