package ipreomobile.test.offlineMode;

import ipreomobile.core.Driver;
import ipreomobile.data.EquityData;
import ipreomobile.data.FundData;
import ipreomobile.data.InstitutionData;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.funds.FundFullProfile;
import ipreomobile.ui.institutions.InstitutionFullProfile;
import ipreomobile.ui.profiles.fullProfileTabs.EqSecurityCurrentHoldersProfileTab;
import ipreomobile.ui.profiles.fullProfileTabs.EqSecurityPeersProfileTab;
import ipreomobile.ui.securities.EqSecurityFullProfile;
import ipreomobile.ui.securities.SecurityRecentlyViewedTab;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Artem_Skrypnyk on 10/9/2014.
 */
public class EquitySecuritiesInOfflineMode extends BaseTest {

    private static final String UNAVAILABLE_PROFILE_MESSAGE = "This Profile is unavailable to you while in Offline Mode.";
    private static final String UNAVAILABLE_SEARCHING_MESSAGE = "Searching within a profile tab is disabled in Offline Mode.";
    private static final String UNAVAILABLE_TAB_MESSAGE = "This tab is not available in Offline Mode. More information may be available online.";
    private static final String UNAVAILABLE_LINK_MESSAGE = "This Link is unavailable to you while in Offline Mode";
    private static final String UNAVAILABLE_FEATURE_MESSAGE = "This Feature is disabled in Offline Mode.";

    private InstitutionData institutionData;
    private FundData fundData;
    private EquityData equityData;
    private String institutionName;
    private String fundName;
    private String equityName;
    private String marketName;

    private SecurityRecentlyViewedTab securityRecentlyViewedTab;
    private EqSecurityFullProfile eqSecurityFullProfile;

    @BeforeMethod
    private void setupTestData(Method m){
        institutionData = new InstitutionData();
        institutionData.setTestCaseName(m.getName());
        institutionName = institutionData.getName();

        fundData = new FundData();
        fundData.setTestCaseName(m.getName());
        fundName = fundData.getName();

        equityData = new EquityData();
        equityData.setTestCaseName(m.getName());
        equityName = equityData.getSecurityName();
        marketName = equityData.getSecurityMarketName();
    }

    @Test
    public void verifyEqSecurityFullProfileInOfflineMode() {
        List<String> tabsAvailableOffline = new ArrayList<>(Arrays.asList(
                "Details",
                //TODO Check cached Current Holders data
                "Current Holders",
                "Analytics"
        ));

        List<String> tabsUnavailableOffline = new ArrayList<>(Arrays.asList(
                "Top 10",
                "Peers"
        ));
        navigation
                .searchSecurities(equityName)
                .openProfileOverview(equityName, marketName);
        securityRecentlyViewedTab = (SecurityRecentlyViewedTab) navigation.selectRecentlyViewedTab();
        securityRecentlyViewedTab
                .verifyItemPresentInList(equityName);

        Driver.switchToOfflineMode();

        eqSecurityFullProfile = securityRecentlyViewedTab.openEquityFullProfile(equityName, marketName);

        eqSecurityFullProfile
                .verifyProfileName(equityName);

        for (String tabName : tabsAvailableOffline) {
            eqSecurityFullProfile
                    .selectTab(tabName)
                    .verifyContentLoaded();
        }

        for (String tabName : tabsUnavailableOffline) {
            eqSecurityFullProfile
                    .selectTab(tabName)
                    .verifyEmptyProfileTabMessage(UNAVAILABLE_TAB_MESSAGE);
        }
    }

    @Test
    public void verifyCachedContentsOnEquitySecurityProfileCurrentHoldersTab(){
        EqSecurityCurrentHoldersProfileTab eqSecurityCurrentHoldersProfileTab;

        eqSecurityFullProfile = navigation.openEquitySecurityProfile(equityName, marketName);
        eqSecurityFullProfile
                .selectTab("Current Holders");
        eqSecurityCurrentHoldersProfileTab = new EqSecurityCurrentHoldersProfileTab();

        eqSecurityCurrentHoldersProfileTab
                .selectInstitutionsTab()
                .filterByHolderName(institutionName.substring(0, 2))
                .selectInstitution(institutionName)
                .waitReady();
        navigation.back();
        eqSecurityCurrentHoldersProfileTab
                .verifyActiveTab("Institutions");
        eqSecurityCurrentHoldersProfileTab.
                clearFilterByHolderName();

        eqSecurityCurrentHoldersProfileTab
                .selectFundsTab()
                .filterByHolderName(fundName.substring(0, 2))
                .selectFund(fundName)
                .waitReady();
        navigation.back();
        eqSecurityCurrentHoldersProfileTab
                .verifyActiveTab("Funds");
        eqSecurityCurrentHoldersProfileTab.
                clearFilterByHolderName();

        Driver.switchToOfflineMode();

        eqSecurityCurrentHoldersProfileTab
                .selectInstitutionsTab()
                .selectItemUnavailableOffline();
        eqSecurityFullProfile.
                verifyTooltip(UNAVAILABLE_PROFILE_MESSAGE);

        eqSecurityCurrentHoldersProfileTab
                .verifyFilterUnavailableOffline();
        eqSecurityFullProfile.
                verifyTooltip(UNAVAILABLE_SEARCHING_MESSAGE);

        eqSecurityCurrentHoldersProfileTab
                .selectItemAvailableOffline();
        new InstitutionFullProfile()
                .verifyProfileName(institutionName);
        navigation
                .verifyPageTitle("Institution Profile")
                .back();
        eqSecurityFullProfile.verifyActiveTab("Current Holders");
        eqSecurityCurrentHoldersProfileTab.verifyActiveTab("Institutions");

        eqSecurityCurrentHoldersProfileTab
                .selectFundsTab()
                .selectItemUnavailableOffline();
        eqSecurityFullProfile.
                verifyTooltip(UNAVAILABLE_PROFILE_MESSAGE);

        eqSecurityCurrentHoldersProfileTab
                .verifyFilterUnavailableOffline();
        eqSecurityFullProfile.
                verifyTooltip(UNAVAILABLE_SEARCHING_MESSAGE);

        eqSecurityCurrentHoldersProfileTab
                .selectItemAvailableOffline();
        new FundFullProfile()
                .verifyProfileName(fundName);
        navigation
                .verifyPageTitle("Fund Profile")
                .back();
        eqSecurityFullProfile.verifyActiveTab("Current Holders");
        eqSecurityCurrentHoldersProfileTab.verifyActiveTab("Funds");

    }

    @Test
    public void verifyCachedContentsOnEquitySecurityProfilePeersTab(){
        EqSecurityPeersProfileTab eqSecurityPeersCard;

        equityData.loadDataSetByTag("myPeer");
        String myPeerSecurityName = equityData.getSecurityName();
        equityData.loadDataSetByTag("defaultPeer");
        String defaultPeerSecurityName = equityData.getSecurityName();

        eqSecurityFullProfile = navigation.openEquitySecurityProfile(equityName, marketName);
        eqSecurityFullProfile
                .selectTab("Peers");
        eqSecurityPeersCard = new EqSecurityPeersProfileTab();

        eqSecurityPeersCard
                .selectDefaultPeerList()
                .openSecurity(defaultPeerSecurityName)
                .waitReady();
        navigation.back();
        eqSecurityPeersCard
                .verifyActiveTab("Default Peer List");

        eqSecurityPeersCard
                .selectMyPeerList()
                .openSecurity(myPeerSecurityName)
                .waitReady();
        navigation.back();
        eqSecurityPeersCard
                .verifyActiveTab("My Peer List");

        Driver.switchToOfflineMode();

        eqSecurityPeersCard
                .selectDefaultPeerList()
                .selectItemUnavailableOffline();
        eqSecurityFullProfile.
                verifyTooltip(UNAVAILABLE_PROFILE_MESSAGE);

        eqSecurityPeersCard
                .openSecurity(defaultPeerSecurityName)
                .verifyProfileName(defaultPeerSecurityName);
        navigation
                .verifyPageTitle("Security Profile")
                .back();
        eqSecurityFullProfile.verifyActiveTab("Peers");
        eqSecurityPeersCard.verifyActiveTab("Default Peer List");

        eqSecurityPeersCard
                .selectMyPeerList()
                .selectItemUnavailableOffline();
        eqSecurityFullProfile.
                verifyTooltip(UNAVAILABLE_PROFILE_MESSAGE);

        eqSecurityPeersCard
                .openSecurity(myPeerSecurityName)
                .verifyProfileName(myPeerSecurityName);
        navigation
                .verifyPageTitle("Security Profile")
                .back();
        eqSecurityFullProfile.verifyActiveTab("Peers");
        eqSecurityPeersCard.verifyActiveTab("My Peer List");

    }
}
