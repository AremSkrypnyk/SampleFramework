package ipreomobile.test.profiles.security;

import ipreomobile.data.EquityData;
import ipreomobile.data.FundData;
import ipreomobile.data.InstitutionData;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.profiles.fullProfileTabs.*;
import ipreomobile.ui.securities.EqSecurityFullProfile;
import ipreomobile.ui.securities.SecuritySearchTab;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by artem_skrypnyk on 9/1/2014.
 */
public class EquitySecurityFullProfileSmoke extends BaseTest {

    private EquityData securityData;
    private String securityName, securityMarketDetails;
    private SecuritySearchTab securitySearchTab;
    private EqSecurityFullProfile equitySecurityProfileSummary;

    @BeforeMethod
    public void setupTestDate(Method m){
        securityData = new EquityData();
        securityData.setTestCaseName(m.getName());
        securityName = securityData.getSecurityName();
        securityMarketDetails = securityData.getSecurityMarketName();

        securitySearchTab = navigation.searchSecurities(securityName);
        equitySecurityProfileSummary = securitySearchTab.openEquityFullProfile(securityName, securityMarketDetails);

    }

    @Test
    public void verifyDetailsTabOnEquitySecurityFullProfile(){
        equitySecurityProfileSummary.selectTab(UITitles.ProfileTab.DETAILS);

        EqSecurityDetailsProfileTab eqSecurityDetailsProfileTab = new EqSecurityDetailsProfileTab();

        List<String> entries = securityData.getLabels();
        entries.forEach(eqSecurityDetailsProfileTab::verifyLabelPresent);

        eqSecurityDetailsProfileTab.verifyPriceChartPresent();
        eqSecurityDetailsProfileTab.verifyLastUpdatedTimeForPriceChart("08/27/2014  12:00 AM");
    }

    @Test
    public void verifyCurrentHoldersTabOnEquitySecurityFullProfile() {
        InstitutionData institutionData = new InstitutionData();
        FundData fundData = new FundData();

        String institutionName = institutionData.getName();
        String fundName = fundData.getName();

        equitySecurityProfileSummary.selectTab(UITitles.ProfileTab.CURRENT_HOLDERS);

        EqSecurityCurrentHoldersProfileTab eqSecurityCurrentHoldersProfileTab = new EqSecurityCurrentHoldersProfileTab();
        eqSecurityCurrentHoldersProfileTab.verifyContentLoaded();
        eqSecurityCurrentHoldersProfileTab.verifyActiveTab("Institutions");

        eqSecurityCurrentHoldersProfileTab.selectFundsTab();
        eqSecurityCurrentHoldersProfileTab.verifyHolderAbsent(institutionName);
        eqSecurityCurrentHoldersProfileTab.verifyHolderPresent(fundName);

        String errorMessageTag = " was not found on the page.";
        Assert.assertNotNull(eqSecurityCurrentHoldersProfileTab.getFundValue(fundName), "FundValue " + errorMessageTag);
        Assert.assertNotNull(eqSecurityCurrentHoldersProfileTab.getFundShares(fundName), "FundShares " + errorMessageTag);
        Assert.assertNotNull(eqSecurityCurrentHoldersProfileTab.getFundChange(fundName), "FundChange " + errorMessageTag);
        Assert.assertNotNull(eqSecurityCurrentHoldersProfileTab.getFundPercentSO(fundName), "FundPercentSO " + errorMessageTag);
        Assert.assertNotNull(eqSecurityCurrentHoldersProfileTab.getFundPercentPortfolio(fundName), "FundPercentPortfolio " + errorMessageTag);
        eqSecurityCurrentHoldersProfileTab.selectFund(fundName).verifyProfileName(fundName);
        navigation.back();
        equitySecurityProfileSummary.verifyActiveTab(UITitles.ProfileTab.CURRENT_HOLDERS);

        eqSecurityCurrentHoldersProfileTab.selectInstitutionsTab();
        eqSecurityCurrentHoldersProfileTab.verifyHolderAbsent(fundName);
        eqSecurityCurrentHoldersProfileTab.verifyHolderPresent(institutionName);

        Assert.assertNotNull(eqSecurityCurrentHoldersProfileTab.getInstitutionValue(institutionName), "InstitutionValue " + errorMessageTag);
        Assert.assertNotNull(eqSecurityCurrentHoldersProfileTab.getInstitutionShares(institutionName), "InstitutionShares " + errorMessageTag);
        Assert.assertNotNull(eqSecurityCurrentHoldersProfileTab.getInstitutionChange(institutionName), "InstitutionChange " + errorMessageTag);
        Assert.assertNotNull(eqSecurityCurrentHoldersProfileTab.getInstitutionPercentSO(institutionName), "InstitutionPercentSO " + errorMessageTag);
        Assert.assertNotNull(eqSecurityCurrentHoldersProfileTab.getInstitutionPercentPortfolio(institutionName), "InstitutionPercentPortfolio " + errorMessageTag);
        eqSecurityCurrentHoldersProfileTab.selectInstitution(institutionName).verifyProfileName(institutionName);
        navigation.back();
        equitySecurityProfileSummary.verifyActiveTab(UITitles.ProfileTab.CURRENT_HOLDERS);
    }

    @Test
    public void verifyAnalyticsTabOnEquitySecurityFullProfile(){
        equitySecurityProfileSummary.selectTab(UITitles.ProfileTab.ANALYTICS);

        EqSecurityAnalyticsProfileTab eqSecurityAnalyticsProfileTab = new EqSecurityAnalyticsProfileTab();
        eqSecurityAnalyticsProfileTab.selectConcentrationTab();
        eqSecurityAnalyticsProfileTab.verifyPublicDataIsDisplayed();

        eqSecurityAnalyticsProfileTab.verifyDiagram("Shareholder Concentration (as a % of Institutional Shares Held)");
        eqSecurityAnalyticsProfileTab.verifyDiagramLegend("Shareholder Concentration (as a % of Institutional Shares Held)");

        eqSecurityAnalyticsProfileTab.verifyDiagram("Shareholder Concentration (as a % of Total Shares Outstanding)");
        eqSecurityAnalyticsProfileTab.verifyDiagramLegend("Shareholder Concentration (as a % of Total Shares Outstanding)");

        eqSecurityAnalyticsProfileTab.selectStyleTab();
        eqSecurityAnalyticsProfileTab.verifyPublicDataIsDisplayed();

        eqSecurityAnalyticsProfileTab.verifyDiagram("Style Breakdown (as a % of Institution Shares Held)");
        eqSecurityAnalyticsProfileTab.verifyDiagramLegend("Style Breakdown (as a % of Institution Shares Held)");

        eqSecurityAnalyticsProfileTab.verifyDiagram("Ownership Trends by Investment Style (as a % of Institutional Shares Held)");
        eqSecurityAnalyticsProfileTab.verifyDiagramLegend("Ownership Trends by Investment Style (as a % of Institutional Shares Held)");

        eqSecurityAnalyticsProfileTab.selectTurnoverTab();
        eqSecurityAnalyticsProfileTab.verifyPublicDataIsDisplayed();

        eqSecurityAnalyticsProfileTab.verifyDiagram("Portfolio Turnover Breakdown (as a % of Institutional Shares Held)");
        eqSecurityAnalyticsProfileTab.verifyDiagramLegend("Portfolio Turnover Breakdown (as a % of Institutional Shares Held)");

        eqSecurityAnalyticsProfileTab.verifyDiagram("Ownership Trends by Portfolio Turnover (as a % of Institutional Shares Held)");
        eqSecurityAnalyticsProfileTab.verifyDiagramLegend("Ownership Trends by Portfolio Turnover (as a % of Institutional Shares Held)");


        eqSecurityAnalyticsProfileTab.showSurveillanceData();
        eqSecurityAnalyticsProfileTab.selectConcentrationTab();
        eqSecurityAnalyticsProfileTab.verifySurveillanceDataIsDisplayed();

        eqSecurityAnalyticsProfileTab.verifyDiagram("Shareholder Concentration (as a % of Institutional Shares Held)");
        eqSecurityAnalyticsProfileTab.verifyDiagramLegend("Shareholder Concentration (as a % of Institutional Shares Held)");

        eqSecurityAnalyticsProfileTab.verifyDiagram("Shareholder Concentration (as a % of Total Shares Outstanding)");
        eqSecurityAnalyticsProfileTab.verifyDiagramLegend("Shareholder Concentration (as a % of Total Shares Outstanding)");

        eqSecurityAnalyticsProfileTab.selectStyleTab();
        eqSecurityAnalyticsProfileTab.verifySurveillanceDataIsDisplayed();

        eqSecurityAnalyticsProfileTab.verifyDiagram("Style Breakdown (as a % of Institution Shares Held)");
        eqSecurityAnalyticsProfileTab.verifyDiagramLegend("Style Breakdown (as a % of Institution Shares Held)");

        eqSecurityAnalyticsProfileTab.verifyDiagram("Ownership Trends by Investment Style (as a % of Institutional Shares Held)");
        eqSecurityAnalyticsProfileTab.verifyDiagramLegend("Ownership Trends by Investment Style (as a % of Institutional Shares Held)");

        eqSecurityAnalyticsProfileTab.selectTurnoverTab();
        eqSecurityAnalyticsProfileTab.verifySurveillanceDataIsDisplayed();

        eqSecurityAnalyticsProfileTab.verifyDiagram("Portfolio Turnover Breakdown (as a % of Institutional Shares Held)");
        eqSecurityAnalyticsProfileTab.verifyDiagramLegend("Portfolio Turnover Breakdown (as a % of Institutional Shares Held)");

        eqSecurityAnalyticsProfileTab.verifyDiagram("Ownership Trends by Portfolio Turnover (as a % of Institutional Shares Held)");
        eqSecurityAnalyticsProfileTab.verifyDiagramLegend("Ownership Trends by Portfolio Turnover (as a % of Institutional Shares Held)");
    }

    @Test
    public void verifyTop10TabOnEquitySecurityFullProfile(){
//        InstitutionData institutionData = new InstitutionData();
//        //institutionData.loadDataSetByTag("holder");
//        //String institutionName = institutionData.getName();
//        List<String> institutionDataLabels = institutionData.getLabels();

//        FundData fundData = new FundData();
//        fundData.loadDataSetByTag("holder");
//        String fundName = fundData.getName();

        //DO NOT DELETE THIS STUFF!
/*        List<String> institutionEntries = new ArrayList<>();
        for (int i = 1; i <= numberOfHolderToCheck; i++) {
            institutionData.loadDataSet("holder_" + i);
            companyName = institutionData.getName();
            institutionEntries.add(companyName);
        }
        List<String> fundEntries = new ArrayList<>();
        for (int i = 1; i <= numberOfHolderToCheck; i++) {
            fundData.loadDataSet("holder_" + i);
            companyName = fundData.getName();
            fundEntries.add(companyName);
        }*/
        String institutionName, fundName;

        equitySecurityProfileSummary.selectTab(UITitles.ProfileTab.TOP_10);

        EqSecurityTop10ProfileProfileTab eqSecurityTop10ProfileTab = new EqSecurityTop10ProfileProfileTab();
        eqSecurityTop10ProfileTab
                .verifyContentLoaded()
                .verifyActiveTab("Institutions");
        eqSecurityTop10ProfileTab
                .verifyPublicDataIsDisplayed()
                .verifyActiveTop10Table("Top Holders");

        institutionName = eqSecurityTop10ProfileTab.getFirstInstitutionNameFromTop10Table();
        eqSecurityTop10ProfileTab
                .selectFirstInstitutionFromTop10Table()
                .verifyProfileName(institutionName);
        navigation.back();
        equitySecurityProfileSummary.verifyActiveTab(UITitles.ProfileTab.TOP_10);

        eqSecurityTop10ProfileTab
                .showSurveillanceData()
                .verifySurveillanceDataIsDisplayed();

        institutionName = eqSecurityTop10ProfileTab.getFirstInstitutionNameFromTop10Table();
        eqSecurityTop10ProfileTab
                .selectFirstInstitutionFromTop10Table()
                .verifyProfileName(institutionName);
        navigation.back();

        equitySecurityProfileSummary
                .verifyActiveTab(UITitles.ProfileTab.TOP_10);
        eqSecurityTop10ProfileTab
                .verifySurveillanceDataIsDisplayed()
                .verifyActiveTop10Table("Top Holders")
                .verifyActiveTab("Institutions");

//        institutionData.loadDataSetByTag("seller");
//        institutionName = institutionData.getName();
        eqSecurityTop10ProfileTab
                .goToTop10BuyersTable()
                .verifySurveillanceDataIsDisplayed();

        institutionName = eqSecurityTop10ProfileTab.getFirstInstitutionNameFromTop10Table();
        eqSecurityTop10ProfileTab
                .selectFirstInstitutionFromTop10Table()
                .verifyProfileName(institutionName);
        navigation.back();

        eqSecurityTop10ProfileTab
                .goToTop10SellersTable()
                .verifySurveillanceDataIsDisplayed();

        institutionName = eqSecurityTop10ProfileTab.getFirstInstitutionNameFromTop10Table();
        eqSecurityTop10ProfileTab
                .selectFirstInstitutionFromTop10Table()
                .verifyProfileName(institutionName);
        navigation.back();

        eqSecurityTop10ProfileTab
                .selectFundsTab()
                .verifyActiveTop10Table("Top Holders");
        fundName = eqSecurityTop10ProfileTab.getFirstFundNameFromTop10Table();
        eqSecurityTop10ProfileTab
                .selectFirstFundFromTop10Table()
                .verifyProfileName(fundName);
        navigation.back();
        eqSecurityTop10ProfileTab
                .verifyActiveTop10Table("Top Holders")
                .verifyActiveTab("Funds");

        eqSecurityTop10ProfileTab
                .goToTop10BuyersTable();
        fundName = eqSecurityTop10ProfileTab.getFirstFundNameFromTop10Table();
        eqSecurityTop10ProfileTab
                .selectFirstFundFromTop10Table()
                .verifyProfileName(fundName);
        navigation.back();
        eqSecurityTop10ProfileTab
                .verifyActiveTop10Table("Top Buyers")
                .verifyActiveTab("Funds");

        eqSecurityTop10ProfileTab
                .goToTop10SellersTable();
        fundName = eqSecurityTop10ProfileTab.getFirstFundNameFromTop10Table();
        eqSecurityTop10ProfileTab
                .selectFirstFundFromTop10Table()
                .verifyProfileName(fundName);
        navigation.back();
        eqSecurityTop10ProfileTab
                .verifyActiveTop10Table("Top Sellers")
                .verifyActiveTab("Funds");
    }

    @Test
    public void verifyPeersTabOnEquitySecurityFullProfile() {
        securityData.setTestCaseName("verifyCachedContentsOnEquitySecurityProfilePeersTab");

        securityData.loadDataSetByTag("myPeer");
        String myPeerName = securityData.getSecurityName();

        securityData.loadDataSetByTag("defaultPeer");
        String defaultPeerName = securityData.getSecurityName();

        equitySecurityProfileSummary.selectTab(UITitles.ProfileTab.PEERS);

        EqSecurityPeersProfileTab eqSecurityPeersProfileTab = new EqSecurityPeersProfileTab();
        eqSecurityPeersProfileTab.verifyActiveTab("Default Peer List");

        eqSecurityPeersProfileTab.selectMyPeerList();
        eqSecurityPeersProfileTab.verifyPeerAbsent(defaultPeerName);
        eqSecurityPeersProfileTab.verifyPeerPresent(myPeerName);

        String errorMessageTag = " was not found on the page.";
        Assert.assertNotNull(eqSecurityPeersProfileTab.getMarketCap(myPeerName), "MarketCap " + errorMessageTag);
        Assert.assertNotNull(eqSecurityPeersProfileTab.getSharesOutstanding(myPeerName), "SharesOutstanding " + errorMessageTag);
        Assert.assertNotNull(eqSecurityPeersProfileTab.getTrailing(myPeerName), "Trailing " + errorMessageTag);
        eqSecurityPeersProfileTab.selectMyPeer(myPeerName).verifyProfileName(myPeerName);
        navigation.back();
        equitySecurityProfileSummary.verifyActiveTab(UITitles.ProfileTab.PEERS);

        eqSecurityPeersProfileTab.selectDefaultPeerList();
        eqSecurityPeersProfileTab.verifyPeerAbsent(myPeerName);
        eqSecurityPeersProfileTab.verifyPeerPresent(defaultPeerName);

        Assert.assertNotNull(eqSecurityPeersProfileTab.getMarketCap(defaultPeerName), "MarketCap " + errorMessageTag);
        Assert.assertNotNull(eqSecurityPeersProfileTab.getSharesOutstanding(defaultPeerName), "SharesOutstanding " + errorMessageTag);
        Assert.assertNotNull(eqSecurityPeersProfileTab.getTrailing(defaultPeerName), "Trailing " + errorMessageTag);
        eqSecurityPeersProfileTab.selectDefaultPeer(defaultPeerName).verifyProfileName(defaultPeerName);
        navigation.back();
        equitySecurityProfileSummary.verifyActiveTab(UITitles.ProfileTab.PEERS);
    }
}
