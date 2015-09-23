package ipreomobile.test.search;

import ipreomobile.data.EquityData;
import ipreomobile.data.FixedIncomeData;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.securities.SecuritySearchTab;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Securities extends BaseTest {
    SecuritySearchTab searchResultsTab;
    EquityData equityData;
    FixedIncomeData fixedIncomeData;

    String equityName;
    String tickerName;
    String securityMarketName;
    String equityIssuerName;

    String couponName;
    String fixedIncomeIssuerName;
    String cusip;

    private static final String NON_VALID_COMPANY_NAME          = "Aple, Inc.";
    private static final String NON_VALID_TICKER_NAME           = "AAAA";
    private static final String NON_VALID_CUSIP                 = "012345678";

    @BeforeMethod
    public void setupData(){
        equityData = new EquityData();
        fixedIncomeData = new FixedIncomeData();

        equityName = equityData.getSecurityName();
        tickerName = equityData.getTickerName();
        securityMarketName = equityData.getSecurityMarketName();
        equityIssuerName = equityData.getIssuerName();

        couponName = fixedIncomeData.getCouponName();
        fixedIncomeIssuerName = fixedIncomeData.getIssuerName();
        cusip = fixedIncomeData.getCusip();

        navigation.openSecurities();
    }

    @Test
    public void verifyTickerNameParameterInSecuritySearch(){
        String selectedProfileName;
        navigation
                .searchSecurities()
                .searchEquities()
                .setSearchField(NON_VALID_TICKER_NAME)
                .search();
        searchResultsTab = new SecuritySearchTab();
        searchResultsTab.verifyResultSetEmpty("The result set should be empty, security with '" + NON_VALID_TICKER_NAME + "' ticker doesn't exist.");

        navigation
                .searchSecurities()
                .searchEquities()
                .setSearchField(tickerName)
                .search();
        searchResultsTab = new SecuritySearchTab();
        searchResultsTab
                .openEquityFullProfile(equityName, securityMarketName)
                .verifyTicker(tickerName);
        navigation.back();
        searchResultsTab.verifyProfileNameSelectedInList(equityName);

        navigation
                .searchSecurities()
                .searchFixedIncomes()
                .setSearchField(couponName)
                .search();
        searchResultsTab = new SecuritySearchTab();
        searchResultsTab
                .openFixedIncomeFullProfile(couponName)
                .verifyProfileName(couponName);
        navigation.back();

        navigation
                .searchSecurities()
                .clearTickerOrSecurityNameField()
                .setIsinCusip(cusip)
                .search();
        searchResultsTab = new SecuritySearchTab();
        selectedProfileName = searchResultsTab.getProfileNameSelectedInList();
        searchResultsTab
                .openFixedIncomeFullProfile(selectedProfileName)
                .verifyProfileName(selectedProfileName);
    }

    @Test
    public void verifyCompanyNameParameterInSecuritySearch(){
        String fixedIncomeCouponNameLeadingSymbols = couponName.substring(0, 3);
        navigation
                .searchSecurities()
                .setTickerOrSecurityName(NON_VALID_COMPANY_NAME)
                .search();

        searchResultsTab = new SecuritySearchTab();
        searchResultsTab.verifyResultSetEmpty("The result set should be empty. Security with '" + NON_VALID_COMPANY_NAME + "' ticker doesn't exist.");

        navigation
                .searchSecurities()
                .searchEquities()
                .setTickerOrSecurityName(equityName)
                .setIssuerCompanyName(equityIssuerName)
                .search();
        searchResultsTab = new SecuritySearchTab();
        searchResultsTab
                .openEquityFullProfile(equityName, securityMarketName)
                .verifyTicker(tickerName);
        navigation.back();
        searchResultsTab.verifyProfileNameSelectedInList(equityName);

        navigation
                .searchSecurities()
                .reset()
                .searchFixedIncomes()
                .setTickerOrSecurityName(fixedIncomeCouponNameLeadingSymbols)
                .setIssuerCompanyName(fixedIncomeIssuerName)
                .search();
        searchResultsTab = new SecuritySearchTab();
        searchResultsTab
                .openFixedIncomeFullProfile(couponName)
                .verifyProfileName(couponName);
    }

    @Test
    public void verifyAllParametersInSecuritySearch(){
        String equitySecurityNameLeadingSymbols = equityName.substring(0, 5);
        String fixedIncomeCouponNameFirstLeadingSymbol = couponName.substring(0, 1);
        navigation
                .searchSecurities()
                .searchEquities()
                .setTickerOrSecurityName(equitySecurityNameLeadingSymbols)
                .search();

        searchResultsTab = new SecuritySearchTab();
        searchResultsTab.openEquityFullProfile(equityName, securityMarketName);

        navigation
                .searchSecurities()
                .setTickerOrSecurityName(couponName)
                .searchFixedIncomes()
                .setIsinCusip(cusip)
                .setIssuerCompanyName(fixedIncomeIssuerName)
                .search();
        searchResultsTab = new SecuritySearchTab();
        searchResultsTab.openFixedIncomeFullProfile(couponName);

        navigation
                .searchSecurities()
                .clearTickerOrSecurityNameField()
                .search();
        searchResultsTab = new SecuritySearchTab();
        searchResultsTab.openFixedIncomeFullProfile(couponName);

        navigation
                .searchSecurities()
                .setTickerOrSecurityName(fixedIncomeCouponNameFirstLeadingSymbol)
                .setIsinCusip(" ")
                .search();
        searchResultsTab = new SecuritySearchTab();
        searchResultsTab.openFixedIncomeFullProfile(couponName);

        navigation
                .searchSecurities()
                .setIssuerCompanyName(NON_VALID_TICKER_NAME)
                .setIsinCusip(cusip)
                .search();
        searchResultsTab = new SecuritySearchTab();
        searchResultsTab.verifyResultSetEmpty("No results should be found for invalid security name '"+NON_VALID_TICKER_NAME+"' and empty ISIN/CUSIP");

        navigation
                .searchSecurities()
                .setTickerOrSecurityName(tickerName)
                .setIsinCusip(NON_VALID_CUSIP)
                .search();
        searchResultsTab = new SecuritySearchTab();
        searchResultsTab.verifyResultSetEmpty("No results should be found for invalid CUSIP '"+NON_VALID_CUSIP+"'");

    }

}
