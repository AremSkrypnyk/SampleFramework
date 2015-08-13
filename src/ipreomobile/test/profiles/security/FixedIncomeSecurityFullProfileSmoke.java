package ipreomobile.test.profiles.security;

import ipreomobile.data.FixedIncomeData;
import ipreomobile.data.FundData;
import ipreomobile.data.InstitutionData;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.profiles.fullProfileTabs.FiDebtSecuritiesOfIssuerProfileTab;
import ipreomobile.ui.profiles.fullProfileTabs.FiSecurityCurrentHoldersProfileTab;
import ipreomobile.ui.profiles.fullProfileTabs.FiSecurityDetailsProfileTab;
import ipreomobile.ui.search.SecuritySearchPanel;
import ipreomobile.ui.securities.FiSecurityFullProfile;
import ipreomobile.ui.securities.SecuritySearchTab;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by artem_skrypnyk on 9/1/2014.
 */
public class FixedIncomeSecurityFullProfileSmoke extends BaseTest {

    private FixedIncomeData securityData;
    private SecuritySearchPanel securitySearchPanel;
    private SecuritySearchTab securitySearchTab;
    private FiSecurityFullProfile fiSecurityProfileSummary;
    private String couponName;

    @BeforeMethod
    public void setupTestDate(Method m){
        securityData = new FixedIncomeData();
        securityData.setTestCaseName(m.getName());
        couponName = securityData.getCouponName();
        securitySearchPanel = navigation.searchSecurities();
        securitySearchPanel.setSearchField(couponName);
        securitySearchPanel.selectAssetClass(UITitles.AssetClass.FIXED_INCOME);
        securitySearchPanel.search();

        securitySearchTab = new SecuritySearchTab();
        fiSecurityProfileSummary = securitySearchTab.openFixedIncomeFullProfile(couponName);
    }

    @Test
    public void verifyProfileSummaryOnFixedIncomeSecurityFullProfile(){
        String errorMessageTag = " was not found on the page.";
        Assert.assertNotNull(fiSecurityProfileSummary.getCouponRate(), "Coupon rate" + errorMessageTag);
        Assert.assertNotNull(fiSecurityProfileSummary.getMaturityDate(), "Maturity Date " + errorMessageTag);
        Assert.assertNotNull(fiSecurityProfileSummary.getNumberOfHolders(), "# of Holders: " + errorMessageTag);
        Assert.assertNotNull(fiSecurityProfileSummary.getTotalParHeld(), "Total par held : " + errorMessageTag);

        Assert.assertNotNull(fiSecurityProfileSummary.getNumberOfBuyers(), "# of Buyers: " + errorMessageTag);
        Assert.assertNotNull(fiSecurityProfileSummary.getNumberOfBuyIns(), "# of Buy-Ins: " + errorMessageTag);
        Assert.assertNotNull(fiSecurityProfileSummary.getNumberOfSellers(), "# of Sellers: " + errorMessageTag);
        Assert.assertNotNull(fiSecurityProfileSummary.getNumberOfSellOuts(), "# of Sell-Outs: " + errorMessageTag);
    }

    @Test
    public void verifyDetailsTabOnFixedIncomeSecurityFullProfile(){
        fiSecurityProfileSummary.selectTab(UITitles.ProfileTab.DETAILS);
        FiSecurityDetailsProfileTab fiSecurityDetailsProfileTab = new FiSecurityDetailsProfileTab();
        List<String> entries = securityData.getLabels();
        entries.forEach(fiSecurityDetailsProfileTab::verifyLabelPresent);
    }

    @Test
    public void verifyCurrentHoldersTabOnFixedIncomeSecurityFullProfile() {
        String institutionName, fundName;
        InstitutionData institutionData = new InstitutionData();
        institutionName = institutionData.getName();

        FundData fundData = new FundData();
        fundName = fundData.getName();

        fiSecurityProfileSummary.selectTab(UITitles.ProfileTab.CURRENT_HOLDERS);

        FiSecurityCurrentHoldersProfileTab fiSecurityCurrentHoldersProfileTab = new FiSecurityCurrentHoldersProfileTab();
        fiSecurityCurrentHoldersProfileTab.verifyActiveTab("Institutions");

        fiSecurityCurrentHoldersProfileTab.selectFundsTab();
        fiSecurityCurrentHoldersProfileTab.verifyHolderAbsent(institutionName);
        fiSecurityCurrentHoldersProfileTab.verifyHolderPresent(fundName);

        String errorMessageTag = " was not found on the page.";
        Assert.assertNotNull(fiSecurityCurrentHoldersProfileTab.getFundParHeld(fundName) , "FundParHeld " + errorMessageTag);
        Assert.assertNotNull(fiSecurityCurrentHoldersProfileTab.getFundParChange(fundName), "FundParChange " + errorMessageTag);
        Assert.assertNotNull(fiSecurityCurrentHoldersProfileTab.getFundValue(fundName), "FundValue " + errorMessageTag);
        Assert.assertNotNull(fiSecurityCurrentHoldersProfileTab.getFundPositionDate(fundName), "FundPositionDate " + errorMessageTag);
        Assert.assertNotNull(fiSecurityCurrentHoldersProfileTab.getFundFIAssetsUnderManagement(fundName), "FundFIAssetsUnderManagement " + errorMessageTag);
        Assert.assertNotNull(fiSecurityCurrentHoldersProfileTab.getFundInvestmentCenter(fundName), "FundInvestmentCenter " + errorMessageTag);
        fiSecurityCurrentHoldersProfileTab.selectFund(fundName).verifyProfileName(fundName);
        navigation.back();
        fiSecurityProfileSummary.verifyActiveTab(UITitles.ProfileTab.CURRENT_HOLDERS);

        fiSecurityCurrentHoldersProfileTab.selectInstitutionsTab();
        fiSecurityCurrentHoldersProfileTab.verifyHolderAbsent(fundName);
        fiSecurityCurrentHoldersProfileTab.verifyHolderPresent(institutionName);

        Assert.assertNotNull(fiSecurityCurrentHoldersProfileTab.getInstitutionParHeld(institutionName), "InstitutionParHeld " + errorMessageTag);
        Assert.assertNotNull(fiSecurityCurrentHoldersProfileTab.getInstitutionParChange(institutionName), "InstitutionParChange " + errorMessageTag);
        Assert.assertNotNull(fiSecurityCurrentHoldersProfileTab.getInstitutionValue(institutionName), "InstitutionValue " + errorMessageTag);
        Assert.assertNotNull(fiSecurityCurrentHoldersProfileTab.getInstitutionPositionDate(institutionName), "InstitutionPositionDate " + errorMessageTag);
        Assert.assertNotNull(fiSecurityCurrentHoldersProfileTab.getInstitutionFIAssetsUnderManagement(institutionName), "InstitutionFIAssetsUnderManagement " + errorMessageTag);
        Assert.assertNotNull(fiSecurityCurrentHoldersProfileTab.getInstitutionInvestmentCenter(institutionName), "InstitutionInvestmentCenter " + errorMessageTag);
        fiSecurityCurrentHoldersProfileTab.selectInstitution(institutionName).verifyProfileName(institutionName);
        navigation.back();
        fiSecurityProfileSummary.verifyActiveTab(UITitles.ProfileTab.CURRENT_HOLDERS);

    }

    @Test
    public void verifyDebtSecuritiesOfIssuerTabOnFixedIncomeSecurityFullProfile(){
        String fixedIncomeProfileName;

        FixedIncomeData securityData  = new FixedIncomeData();
        securityData.loadDataSetByTag("debtSecurityOfIssuer");
        fixedIncomeProfileName = securityData.getCouponName();

        fiSecurityProfileSummary.selectTab(UITitles.ProfileTab.DEBT_SECURITIES_OF_ISSUER);

        FiDebtSecuritiesOfIssuerProfileTab fiDebtSecuritiesOfIssuerProfileTab = new FiDebtSecuritiesOfIssuerProfileTab();

        String errorMessageTag = " was not found on the page.";
        Assert.assertNotNull(fiDebtSecuritiesOfIssuerProfileTab.getCusipIsin(fixedIncomeProfileName), "CusipIsin" + errorMessageTag);
        Assert.assertNotNull(fiDebtSecuritiesOfIssuerProfileTab.getCoupon(fixedIncomeProfileName), "Coupon" + errorMessageTag);
        Assert.assertNotNull(fiDebtSecuritiesOfIssuerProfileTab.getMaturity(fixedIncomeProfileName), "Maturity" + errorMessageTag);
        Assert.assertNotNull(fiDebtSecuritiesOfIssuerProfileTab.getSpRating(fixedIncomeProfileName), "SpRating" + errorMessageTag);
        Assert.assertNotNull(fiDebtSecuritiesOfIssuerProfileTab.getNumberOfHolders(fixedIncomeProfileName), "NumberOfHolders" + errorMessageTag);
        Assert.assertNotNull(fiDebtSecuritiesOfIssuerProfileTab.getCurrency(fixedIncomeProfileName), "Currency" + errorMessageTag);

        fiDebtSecuritiesOfIssuerProfileTab.openSecurityProfile(fixedIncomeProfileName);
    }
}
