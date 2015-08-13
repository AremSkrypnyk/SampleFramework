package ipreomobile.test.offlineMode;

import ipreomobile.core.Driver;
import ipreomobile.data.EquityData;
import ipreomobile.data.FixedIncomeData;
import ipreomobile.data.FundData;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.funds.FundFullProfile;
import ipreomobile.ui.funds.FundRecentlyViewedTab;
import ipreomobile.ui.profiles.fullProfileTabs.FundCurrentHoldingsProfileTab;
import ipreomobile.ui.profiles.fullProfileTabs.FundOwnershipProfileTab;
import ipreomobile.ui.securities.EqSecurityFullProfile;
import ipreomobile.ui.securities.FiSecurityFullProfile;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Artem_Skrypnyk on 10/9/2014.
 */
public class FundsInOfflineMode extends BaseTest {

    private FundData fundData;
    private EquityData equityData;
    private FixedIncomeData fixedIncomeData;
    private String fundName;

    private String equityName, marketName, couponName, tickerName;

    private FundRecentlyViewedTab fundRecentlyViewedTab;
    private FundFullProfile fundFullProfile;


    private static final String UNAVAILABLE_PROFILE_MESSAGE = "This Profile is unavailable to you while in Offline Mode.";
    private static final String UNAVAILABLE_SEARCHING_MESSAGE = "Searching within a profile tab is disabled in Offline Mode.";
    private static final String UNAVAILABLE_TAB_MESSAGE = "This tab is not available in Offline Mode. More information may be available online.";
    private static final String UNAVAILABLE_LINK_MESSAGE = "This Link is unavailable to you while in Offline Mode";
    private static final String UNAVAILABLE_FEATURE_MESSAGE = "This Feature is disabled in Offline Mode.";


    @BeforeMethod
    private void setupTestData(Method m){

        fundData = new FundData();
        fundData.setTestCaseName(m.getName());
        fundName = fundData.getName();

        equityData = new EquityData();
        equityData.setTestCaseName(m.getName());

        equityName = equityData.getSecurityName();
        marketName = equityData.getSecurityMarketName();
        tickerName = equityData.getTickerName();

        fixedIncomeData = new FixedIncomeData();
        fixedIncomeData.setTestCaseName(m.getName());
        couponName = fixedIncomeData.getCouponName();
    }
    
    @Test
    public void verifyFundFullProfileInOfflineMode() {
        List<String> tabsAvailableOffline = new ArrayList<>(Arrays.asList(
                "Details",
                "Ownership",
                "Additional Info"
        ));

        List<String> tabsUnavailableOffline = new ArrayList<>(Arrays.asList(
                "Current Holdings",
                "Actions"
        ));

        navigation.clearStoredData();

        fundName = fundData.getName();

        navigation
                .searchFunds(fundName)
                .openProfileOverview(fundName);
        fundRecentlyViewedTab = (FundRecentlyViewedTab) navigation.selectRecentlyViewedTab();
        fundRecentlyViewedTab
                .verifyItemPresentInList(fundName);

        fundFullProfile = fundRecentlyViewedTab.openFullProfile(fundName);

        fundFullProfile
                .verifyProfileName(fundName);

        Driver.switchToOfflineMode();

        fundFullProfile
                .clickInstitutionProfileLink()
                .verifyTooltip("This Profile is unavailable to you while in Offline Mode");
        fundFullProfile
                .clickAddActivityButton()
                .verifyTooltip("This Feature is disabled in Offline Mode.")
                .clickFixDataLink()
                .verifyTooltip("This Feature is disabled in Offline Mode.");

        fundFullProfile
                .openMapPreview()
                .verifyTitle("Fund Address")
                .verifyMapMessage("Maps Unavailable in Offline Mode")
                .verifyAddressPresent()
                .close();

        for (String tabName : tabsAvailableOffline) {
            fundFullProfile
                    .selectTab(tabName)
                    .verifyContentLoaded();
        }

        fundFullProfile
                .selectTab("Ownership");
        FundOwnershipProfileTab fundOwnershipProfileTab = new FundOwnershipProfileTab();
        fundOwnershipProfileTab
                .verifyActiveTab("Equity")
                .verifyTabPresent("Fixed Income");
        fundOwnershipProfileTab
                .clickFirstSecurityInTopHoldingsTable();
        fundFullProfile
                .verifyTooltip(UNAVAILABLE_PROFILE_MESSAGE);
        fundOwnershipProfileTab
                .clickFirstSecurityInTopBuysAndSellsTable();
        fundFullProfile
                .verifyTooltip(UNAVAILABLE_PROFILE_MESSAGE);

        fundOwnershipProfileTab
                .selectFixedIncomeTab();
        fundOwnershipProfileTab
                .clickFirstSecurityInTopHoldingsTable();
        fundFullProfile
                .verifyTooltip(UNAVAILABLE_PROFILE_MESSAGE);
        fundOwnershipProfileTab
                .clickFirstSecurityInTopBuysAndSellsTable();
        fundFullProfile
                .verifyTooltip(UNAVAILABLE_PROFILE_MESSAGE);

        //TODO Check cached Activity data
//        fundFullProfile.selectTab("Actions");
//        FundActionsProfileTab fundActionsProfileTab = new FundActionsProfileTab();
//        fundActionsProfileTab
//                .clickRecentActivity();
//        fundFullProfile
//                .verifyTooltip("This Activity is unavailable to you while in Offline Mode.");
//        fundActionsProfileTab
//                .clickRecentTask();
//        fundFullProfile
//                .verifyTooltip("This Task is unavailable to you while in Offline Mode.");
//
//        fundActionsProfileTab
//                .clickActivityAvailableOffline()
//                .verifyDeleteButtonUnavailable()
//                .verifyEditButtonUnavailable();
//        fundFullProfile
//                .closeOverlay();
//
//        fundActionsProfileTab
//                .clickActivityUnavailableOffline();
//        fundFullProfile
//                .verifyTooltip("This Activity is unavailable to you while in Offline Mode.");

        for (String tabName : tabsUnavailableOffline) {
            fundFullProfile
                    .selectTab(tabName)
                    .verifyEmptyProfileTabMessage(UNAVAILABLE_TAB_MESSAGE);
        }
    }

    @Test
    public void verifyCachedContentsOnFundProfileCurrentHoldingsTab(){
        FundCurrentHoldingsProfileTab fundCurrentHoldingsProfileTab;
        String equitySecurityNameLeadingSymbols = equityName.substring(0, 2);

        fundFullProfile = navigation.openFundProfile(fundName);
        fundFullProfile
                .selectTab("Current Holdings");
        fundCurrentHoldingsProfileTab = new FundCurrentHoldingsProfileTab();

        fundCurrentHoldingsProfileTab
                .selectEquityTab()
                .filterBySymbolOrSecurityName(equitySecurityNameLeadingSymbols)
                .openEquitySecurityProfileBySecurityNameAndTicker(equityName, tickerName)
                .waitReady();
        navigation.back();
        fundCurrentHoldingsProfileTab
                .verifyActiveTab("Equity");
        fundCurrentHoldingsProfileTab.
                clearFilterBySymbolOrSecurityName();

        fundCurrentHoldingsProfileTab
                .selectFixedIncomeTab()
                .filterBySymbolOrSecurityName(couponName.substring(0, 3))
                .openFixedIncomeSecurityProfile(couponName)
                .waitReady();
        navigation.back();
        fundCurrentHoldingsProfileTab
                .verifyActiveTab("Fixed Income");
        fundCurrentHoldingsProfileTab.
                clearFilterBySymbolOrSecurityName();

        Driver.switchToOfflineMode();

        fundCurrentHoldingsProfileTab
                .selectEquityTab()
                .selectItemUnavailableOffline();
        fundFullProfile.
                verifyTooltip(UNAVAILABLE_PROFILE_MESSAGE);

        fundCurrentHoldingsProfileTab
                .verifyFilterUnavailableOffline();
        fundFullProfile.
                verifyTooltip(UNAVAILABLE_SEARCHING_MESSAGE);

        fundCurrentHoldingsProfileTab
                .selectItemAvailableOffline();
        new EqSecurityFullProfile()
                .verifyProfileName(equityName);
        navigation
                .verifyPageTitle("Security Profile")
                .back();
        fundFullProfile.verifyActiveTab("Current Holdings");
        fundCurrentHoldingsProfileTab.verifyActiveTab("Equity");

        fundCurrentHoldingsProfileTab
                .selectFixedIncomeTab()
                .selectItemUnavailableOffline();
        fundFullProfile.
                verifyTooltip(UNAVAILABLE_PROFILE_MESSAGE);

        fundCurrentHoldingsProfileTab
                .verifyFilterUnavailableOffline();
        fundFullProfile.
                verifyTooltip(UNAVAILABLE_SEARCHING_MESSAGE);

        fundCurrentHoldingsProfileTab
                .selectItemAvailableOffline();
        new FiSecurityFullProfile()
                .verifyProfileName(couponName);
        navigation
                .verifyPageTitle("Security Profile")
                .back();
        fundFullProfile.verifyActiveTab("Current Holdings");
        fundCurrentHoldingsProfileTab.verifyActiveTab("Fixed Income");

    }
}
