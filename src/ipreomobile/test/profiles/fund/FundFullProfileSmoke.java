package ipreomobile.test.profiles.fund;

import ipreomobile.core.Logger;
import ipreomobile.data.*;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.funds.FundFullProfile;
import ipreomobile.ui.funds.FundRecentlyViewedTab;
import ipreomobile.ui.profiles.fullProfileTabs.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FundFullProfileSmoke extends BaseTest {

    FundFullProfile fundFullProfile;

    FundData fundData;
    InstitutionData institutionData;

    String fundName;
    String institutionName;
    String phone;
    String city;
    String currency;
    String defaultTicker;
    String managedBy;
    String customerName;

    @BeforeMethod
    public void setupTestData(Method testMethod){
        fundData = new FundData();
        fundData.setTestCaseName(testMethod.getName());
        fundName = fundData.getName();
        city = fundData.getCityName();
        managedBy = fundData.getManagedBy();

        institutionData = new InstitutionData();
        institutionName = institutionData.getName();
        phone = institutionData.getPhone();

        fundFullProfile = navigation.openFundProfile(fundName);

        currency = System.getProperty("test.currency") + "M";
        defaultTicker  = System.getProperty("test.defaultTicker");
        customerName = System.getProperty("test.customer");
    }

    @Test
    public void openFundFullProfileFromSearch(){
//        boolean isInCrm = true;
//        boolean isActive = true;
//        boolean isSubscribed = true;
//
//        fundFullProfile
//                .verifyTabsRowExists()
//                .verifyActiveTab("Details");
//        navigation
//                .verifyPageTitle("Fund Profile")
//                .verifyBackButtonPresent();
//
//        fundFullProfile.verifyPhone(phone)
//                .verifyActiveAndSubscribedBadge(isActive, isSubscribed)
//                .verifyBdVersusCrmInfo()
//                .verifyCrmBadge(isInCrm)
//                .verifyProfileName(fundName);
//
//        fundFullProfile.openMapPreview()
//                .verifyTitle("Fund Address")
//                .verifyAddressPart(city)
//                .openMap()
//                .verifyPageTitle("Map")
//                //.verifyLocationName(fundName)
//                .close();
//
//        fundFullProfile.addActivity()
//                .verifyExternalParticipantPresent(fundName)
//                .closeActivityDialog();
//
//        fundFullProfile.openFixDataOverlay()
//                .verifyField("Customer:", customerName)
//                .verifyField("Fund Name:", fundName)
//                .verifyWhatNeedsToBeFixedField()
//                .verifyThankYouField()
//                .cancel();
//
//        fundFullProfile.openFundContactsOverlay()
//                .goToManagedBy()
//                .verifyContactNamePresent(managedBy)
//                .verifyContactRelatedInstitutionNamePresent(managedBy)
//                .verifyContactEmailPresent(managedBy)
//                .verifyContactPhonePresent(managedBy)
//
//                .goToContactProfileAndVerifyName(managedBy);
//        navigation.back();

        fundFullProfile
                .goToInstitutionProfile()
                .verifyProfileName(institutionName);
        navigation.back();

        navigation.back();
        navigation
                .verifyActivePanel("Funds")
                .verifyActiveTabTitle("Search Results")
                .selectTab(UITitles.PanelTabs.RECENTLY_VIEWED);
        FundRecentlyViewedTab fundRecentlyViewedTab = new FundRecentlyViewedTab();
        fundRecentlyViewedTab
                .verifyItemPresentInList(fundName)
                .openFullProfile(fundName)
                .verifyProfileName(fundName);

    }

    @Test
    public void verifyDetailsTabOnFundFullProfile() {
        List<String> detailsTabLabels = fundData.getLabels();

        fundFullProfile.selectTab(UITitles.ProfileTab.DETAILS);
        FundDetailsProfileTab card = new FundDetailsProfileTab();
        for (String entry : detailsTabLabels) {
            card.verifyLabelPresent(entry);
            Assert.assertFalse(card.getValue(entry).isEmpty(), "Value for '" + entry + "' was empty, but should not be.");
        }
        card.verifyHoldingsChart("Asset Allocation");
        card.verifyHoldingsChart("Fixed Income Portfolio");
    }

    @Test
    public void verifyOwnershipTabOnFundFullProfile(){
        List<String> topBuySellTablesToVerify = new ArrayList<>(Arrays.asList(
                "TOP SELLS",
                "TOP BUYS"));

        fundFullProfile.selectTab(UITitles.ProfileTab.OWNERSHIP);
        FundOwnershipProfileTab card = new FundOwnershipProfileTab();
        card
                .verifyActiveTab(FundOwnershipProfileTab.EQ)
                .verifyTabPresent(FundOwnershipProfileTab.FI);
        //TODO SurveillanceData vs PublicData show

        String securityName = card.getFirstEquityInTopHoldingsTable();
        card.openFirstEquityProfileFromTopHoldingsTable()
                .verifyProfileName(securityName);
        navigation.back();

        for (String tableHeader : topBuySellTablesToVerify) {
            card.selectTopBuySellTable(tableHeader)
                    .verifyCurrencyInTopBuySellTableHeader(tableHeader);
            verifyEquityInTopBuysAndSellsTable(card);
        }

        card.selectFixedIncomeTab();
        card.verifyFixedIncomePortfolioChart();

        securityName = card.getFirstFiInTopHoldingsTable();
        card.openFirstFixedIncomeProfileFromTopHoldingsTable()
                .verifyProfileName(securityName);
        navigation.back();
    }

    private void verifyEquityInTopBuysAndSellsTable(FundOwnershipProfileTab card) {
        String topEquityName = card.getFirstCompanyNameInTopBuysAndSellsTable();
        card.openFirstEquityProfileFromTopBuysAndSellsTable()
                .verifyProfileName(topEquityName);
        Logger.logScreenshot("Opened '" + topEquityName + "' equity profile summary from the table.");
        navigation.back();
        card.verifyActiveTab(FundOwnershipProfileTab.EQ);
    }

    @Test
    public void verifyCurrentHoldingsTabOnFundFullProfile(){
        String equitySecurityName, fixedIncomeSecurityName, equityTicker, fixedIncomeTicker;

        EquityData equityData = new EquityData();
        equitySecurityName = equityData.getSecurityName();
        equityTicker = equityData.getTickerName();

        FixedIncomeData fixedIncomeData = new FixedIncomeData();
        fixedIncomeSecurityName = fixedIncomeData.getCouponName();

        fundFullProfile.selectTab(UITitles.ProfileTab.CURRENT_HOLDINGS);
        FundCurrentHoldingsProfileTab fundCurrentHoldingsProfileTab = new FundCurrentHoldingsProfileTab();
        fundCurrentHoldingsProfileTab
                .verifySecurityPresent(equitySecurityName)
                .verifyEquitySecurityDetailsPresent(equitySecurityName)
                .verifyTabPresent("Equity")
                .verifyTabPresent("Fixed Income")
                .verifyActiveTab("Equity");

        fundCurrentHoldingsProfileTab
                .selectEquitySecurity(equitySecurityName)
                .verifyProfileName(equitySecurityName);
        navigation.back();

        //TODO: rerun when TFS 230978 is fixed (cannot filter securities with symbols *%/)
        fundCurrentHoldingsProfileTab.verifySecurityAbsent(fixedIncomeSecurityName);

        fundCurrentHoldingsProfileTab.selectFixedIncomeTab();
        fundCurrentHoldingsProfileTab
                .verifySecurityPresent(fixedIncomeSecurityName)
                .verifyFixedIncomeSecurityDetailsPresent(fixedIncomeSecurityName)
                .selectFixedIncomeSecurity(fixedIncomeSecurityName)
                .verifyProfileName(fixedIncomeSecurityName);
        navigation.back();

        fundCurrentHoldingsProfileTab
                .verifySecurityAbsent(equitySecurityName)
                .filterBySymbolOrSecurityName(equityTicker)
                .verifyListEmpty();
    }

    @Test
    public void verifyActionsTabOnFundFullProfile(){
        String recentActivitySubject, recentTaskSubject;
        String activitySubject = new ActivityData().getSubject();
        String taskSubject = new TaskData().getSubject();

        fundFullProfile.selectTab(UITitles.ProfileTab.ACTIONS);
        FundActionsProfileTab actionsCard = new FundActionsProfileTab();

        actionsCard.verifyActiveTab("Recent");
        actionsCard.verifyActionsSummaryChartPresent();
        actionsCard.verifyRecentActionsBlockPresent();

        recentActivitySubject = actionsCard.getRecentActivityData().getSubject();
        actionsCard.openRecentActivity()
                .verifySubject(recentActivitySubject)
                .editActivity()
                .verifyExternalParticipantPresent(fundName)
                .closeActivityDialog();

        recentTaskSubject = actionsCard.getRecentTaskData().getSubject();
        actionsCard.openRecentTask()
                .verifySubject(recentTaskSubject)
                .editActivity()
                .verifyExternalParticipantPresent(fundName)
                .closeActivityDialog();

        actionsCard.selectActivitiesTab();
        actionsCard.selectActivity(activitySubject)
                .verifySubject(activitySubject)
                .editActivity()
                .verifyExternalParticipantPresent(fundName)
                .closeActivityDialog();
        actionsCard.verifyActiveTab("Activities");

        actionsCard.selectTasksTab();
        actionsCard.selectTask(taskSubject)
                .verifySubject(taskSubject)
                .editActivity()
                .verifyExternalParticipantPresent(fundName)
                .closeActivityDialog();
        actionsCard.verifyActiveTab("Tasks");
    }

    @Test
    public void verifyAdditionalInfoTabOnFundFullProfile() {
        List<String> entries = new ArrayList<>();
        entries.add("Telephone");

        fundFullProfile.selectTab(UITitles.ProfileTab.ADDITIONAL_INFO);
        BaseAdditionalInfoProfileTab card = new BaseAdditionalInfoProfileTab();
        for (String entry : entries) {
            card.verifyAdditionalInfoFieldPresent(entry);
        }
    }

}
