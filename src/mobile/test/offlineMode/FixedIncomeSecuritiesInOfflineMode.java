package ipreomobile.test.offlineMode;

import ipreomobile.core.Driver;
import ipreomobile.data.FixedIncomeData;
import ipreomobile.data.FundData;
import ipreomobile.data.InstitutionData;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.funds.FundFullProfile;
import ipreomobile.ui.institutions.InstitutionFullProfile;
import ipreomobile.ui.profiles.fullProfileTabs.FiDebtSecuritiesOfIssuerProfileTab;
import ipreomobile.ui.profiles.fullProfileTabs.FiSecurityCurrentHoldersProfileTab;
import ipreomobile.ui.search.SecuritySearchPanel;
import ipreomobile.ui.securities.FiSecurityFullProfile;
import ipreomobile.ui.securities.SecurityRecentlyViewedTab;
import ipreomobile.ui.securities.SecuritySearchTab;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Artem_Skrypnyk on 10/9/2014.
 */
public class FixedIncomeSecuritiesInOfflineMode extends BaseTest {

    private static final String UNAVAILABLE_PROFILE_MESSAGE = "This Profile is unavailable to you while in Offline Mode.";
    private static final String UNAVAILABLE_SEARCHING_MESSAGE = "Searching within a profile tab is disabled in Offline Mode.";
    private static final String UNAVAILABLE_TAB_MESSAGE = "This tab is not available in Offline Mode. More information may be available online.";
    private static final String UNAVAILABLE_LINK_MESSAGE = "This Link is unavailable to you while in Offline Mode";
    private static final String UNAVAILABLE_FEATURE_MESSAGE = "This Feature is disabled in Offline Mode.";

    private InstitutionData institutionData;
    private FundData fundData;
    private FixedIncomeData securityData;
    private String institutionName;
    private String fundName;
    private String couponName;

    private FiSecurityFullProfile fiSecurityFullProfile;
    private SecuritySearchTab securitySearchTab;
    private SecurityRecentlyViewedTab securityRecentlyViewedTab;

    @BeforeMethod
    private void setupTestData(Method m){
        institutionData = new InstitutionData();
        institutionData.setTestCaseName(m.getName());
        institutionName = institutionData.getName();

        fundData = new FundData();
        fundData.setTestCaseName(m.getName());
        fundName = fundData.getName();

        securityData = new FixedIncomeData();
        securityData.setTestCaseName(m.getName());
        couponName = securityData.getCouponName();
    }

    @Test
    public void verifyFiSecurityFullProfileInOfflineMode() {
        List<String> tabsAvailableOffline = new ArrayList<>(Arrays.asList(
                "Details",
                //TODO Check cached Current Holders data
                "Current Holders"
        ));

        List<String> tabsUnavailableOffline = new ArrayList<>(Arrays.asList(
                "Debt Securities Of Issuer"
        ));
        SecuritySearchPanel securitySearchPanel = (SecuritySearchPanel)navigation
                .openSecurities()
                .openSearch();
        securitySearchPanel.setSearchField(couponName);
        securitySearchPanel.selectAssetClass(UITitles.AssetClass.FIXED_INCOME);
        securitySearchPanel.search();
        securitySearchTab = new SecuritySearchTab();
        securitySearchTab.openProfileOverview(couponName);

        securityRecentlyViewedTab = (SecurityRecentlyViewedTab) navigation.selectRecentlyViewedTab();
        securityRecentlyViewedTab
                .verifyItemPresentInList(couponName);

        Driver.switchToOfflineMode();

        fiSecurityFullProfile = securityRecentlyViewedTab.openFixedIncomeFullProfile(couponName);

        fiSecurityFullProfile
                .verifyProfileName(couponName);

        for (String tabName : tabsAvailableOffline) {
            fiSecurityFullProfile
                    .selectTab(tabName)
                    .verifyContentLoaded();
        }

        for (String tabName : tabsUnavailableOffline) {
            fiSecurityFullProfile
                    .selectTab(tabName)
                    .verifyEmptyProfileTabMessage(UNAVAILABLE_TAB_MESSAGE);
        }
    }


    @Test
    public void verifyCachedContentsOnFixedIncomeSecurityProfileCurrentHoldersTab(){
        FiSecurityCurrentHoldersProfileTab fiSecurityCurrentHoldersProfileTab;

        fiSecurityFullProfile = navigation.openFixedIncomeSecurityProfile(couponName);
        fiSecurityFullProfile
                .selectTab("Current Holders");
        fiSecurityCurrentHoldersProfileTab = new FiSecurityCurrentHoldersProfileTab();

        fiSecurityCurrentHoldersProfileTab
                .selectInstitutionsTab()
                .filterByHolderName(institutionName.substring(0, 2))
                .selectInstitution(institutionName)
                .waitReady();
        navigation.back();
        fiSecurityCurrentHoldersProfileTab
                .verifyActiveTab("Institutions");
        fiSecurityCurrentHoldersProfileTab.
                clearFilterByHolderName();

        fiSecurityCurrentHoldersProfileTab
                .selectFundsTab()
                .filterByHolderName(fundName.substring(0, 2))
                .selectFund(fundName)
                .waitReady();
        navigation.back();
        fiSecurityCurrentHoldersProfileTab
                .verifyActiveTab("Funds");
        fiSecurityCurrentHoldersProfileTab.
                clearFilterByHolderName();

        Driver.switchToOfflineMode();

        fiSecurityCurrentHoldersProfileTab
                .selectInstitutionsTab()
                .selectItemUnavailableOffline();
        fiSecurityFullProfile.
                verifyTooltip(UNAVAILABLE_PROFILE_MESSAGE);

        fiSecurityCurrentHoldersProfileTab
                .verifyFilterUnavailableOffline();
        fiSecurityFullProfile.
                verifyTooltip(UNAVAILABLE_SEARCHING_MESSAGE);

        fiSecurityCurrentHoldersProfileTab
                .selectInstitution(institutionName);
        new InstitutionFullProfile()
                .verifyProfileName(institutionName);
        navigation
                .verifyPageTitle("Institution Profile")
                .back();
        fiSecurityFullProfile.verifyActiveTab("Current Holders");
        fiSecurityCurrentHoldersProfileTab.verifyActiveTab("Institutions");

        fiSecurityCurrentHoldersProfileTab
                .selectFundsTab()
                .selectItemUnavailableOffline();
        fiSecurityFullProfile.
                verifyTooltip(UNAVAILABLE_PROFILE_MESSAGE);

        fiSecurityCurrentHoldersProfileTab
                .verifyFilterUnavailableOffline();
        fiSecurityFullProfile.
                verifyTooltip(UNAVAILABLE_SEARCHING_MESSAGE);

        fiSecurityCurrentHoldersProfileTab
                .selectFund(fundName);
        new FundFullProfile()
                .verifyProfileName(fundName);
        navigation
                .verifyPageTitle("Fund Profile")
                .back();
        fiSecurityFullProfile.verifyActiveTab("Current Holders");
        fiSecurityCurrentHoldersProfileTab.verifyActiveTab("Funds");

    }

    @Test
    public void verifyCachedContentsOnFixedIncomeSecurityProfileDebtSecuritiesOfIssuerTab(){
        FiDebtSecuritiesOfIssuerProfileTab fiDebtSecuritiesOfIssuerCard;

        fiSecurityFullProfile = navigation.openFixedIncomeSecurityProfile(couponName);
        fiSecurityFullProfile
                .selectTab(UITitles.ProfileTab.DEBT_SECURITIES_OF_ISSUER);
        fiDebtSecuritiesOfIssuerCard = new FiDebtSecuritiesOfIssuerProfileTab();

        String nextItemName = fiDebtSecuritiesOfIssuerCard
                .getNextItemName(couponName);


        fiDebtSecuritiesOfIssuerCard
                .openSecurityProfile(nextItemName)
                .verifyProfileName(nextItemName)
                .waitReady();
        navigation.back();

        Driver.switchToOfflineMode();

        fiDebtSecuritiesOfIssuerCard
                .selectItemUnavailableOffline();
        fiSecurityFullProfile.
                verifyTooltip(UNAVAILABLE_PROFILE_MESSAGE);

        fiDebtSecuritiesOfIssuerCard
                .openSecurityProfile(nextItemName)
                .verifyProfileName(nextItemName);
        navigation
                .verifyPageTitle("Security Profile")
                .back();
        fiSecurityFullProfile.verifyActiveTab(UITitles.ProfileTab.DEBT_SECURITIES_OF_ISSUER);
    }
}
