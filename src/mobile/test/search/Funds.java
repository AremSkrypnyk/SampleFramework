package ipreomobile.test.search;

import ipreomobile.data.FundData;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.blocks.overlay.ListOverlayFilter;
import ipreomobile.ui.funds.FundFullProfile;
import ipreomobile.ui.funds.FundSearchTab;
import ipreomobile.ui.search.FundSearchPanel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Funds extends BaseTest {
    FundSearchPanel fundSearchPanel;
    FundSearchTab fundSearchTab;
    ListOverlayFilter overlay;
    FundFullProfile fullProfile;
    FundData fundData;
    String fundType;
    String fundName;
    String fundLocation;
    String fundCityName;

    private static final String DEFAULT_TYPE                    = "All";
    private static final String NON_VALID_FUND_TYPE             = "Boom";

    private static final String NON_VALID_FUND_LOCATION_NAME    = "United Statse";
    private static final String NON_VALID_FUND_CITY_NAME        = "Bostn";

    private int ALL_FUNDS_NUMBER                                 = 120000;

    private int NUMBER_OF_PROFILES_TO_CHECK = 5;

    @BeforeMethod
    public void setupData(){
        fundData        = new FundData();
        fundType        = fundData.getType();
        fundName        = fundData.getName();
        fundLocation    = fundData.getLocation();
        fundCityName    = fundData.getCityName();
    }

    @Test
    public void verifyTypeParameterInFundSearch(){

        openFundSearchPanel();
        fundSearchPanel.selectFundType(fundType);
        String firstCharactersInFundType = fundType.substring(0, 1);

        fundSearchPanel
                .selectFundType()
                .setSearchFilter(firstCharactersInFundType)
                .verifySelectedItemName(fundType)
                .close();

        fundSearchPanel
                .selectFundType()
                .setSearchFilter(NON_VALID_FUND_TYPE)
                .verifyResultListEmpty()
                .setSearchFilter(firstCharactersInFundType)
                .verifySelectedItemName(fundType)

                .setSearchFilter(" ")
                .select(DEFAULT_TYPE);

        fundSearchPanel
                .selectFundType()
                .verifySelectedItemName(DEFAULT_TYPE)
                .close();
    }

    @Test
    public void verifySearchInParameterInFundSearch(){

        openFundSearchPanel();
        fundSearchPanel
                .selectSearchIn(UITitles.SearchParams.COMPANY_CRM)
                .search();
        fundSearchTab = new FundSearchTab();
        verifyFundsInCrm(NUMBER_OF_PROFILES_TO_CHECK);

        openFundSearchPanel();
        fundSearchPanel
                .selectSearchIn(UITitles.SearchParams.ALL)
                .search();

        fundSearchTab = new FundSearchTab();
        Assert.assertTrue(fundSearchTab.getSearchResultsNumber()> ALL_FUNDS_NUMBER, "Seems to not all funds were found. Number of funds should be more then " + ALL_FUNDS_NUMBER);

        openFundSearchPanel();
        fundSearchPanel
                .selectSearchIn(UITitles.SearchParams.BD_ONLY)
                .search();

        fundSearchTab = new FundSearchTab();
        Assert.assertFalse(fundSearchTab.isResultSetEmpty(), "Results set is empty. Expected funds from BD_Only");
    }

    @Test
    public void verifyCityInvestmentCenterLocationParameterInFundSearch(){

        openFundSearchPanel();
        String nonValidFundLocationSubstring = NON_VALID_FUND_LOCATION_NAME.substring(0, 1);

        fundSearchPanel
                .selectInvestmentCenterLocation()
                .setSearchFilter(NON_VALID_FUND_LOCATION_NAME)
                .verifyResultListEmpty()

                .setSearchFilter(nonValidFundLocationSubstring)
                .select(fundLocation);

        fundSearchPanel
                .selectInvestmentCenterLocation()
                .setSearchFilter(nonValidFundLocationSubstring)
                .verifyAlphabeticalSortingOrder(NUMBER_OF_PROFILES_TO_CHECK)
                .close();

        fundSearchPanel.search();

        fundSearchTab = new FundSearchTab();
        fundSearchTab.verifyItemPresentInList(fundName);
    }

    @Test
    public void verifyCityNameParameterInFundSearch(){

        openFundSearchPanel();
        fundSearchPanel
                .setCityName(NON_VALID_FUND_CITY_NAME)
                .search();

        fundSearchTab = new FundSearchTab();
        Assert.assertTrue(fundSearchTab.isResultSetEmpty(), "Results set is not empty, but should be. Funds with '" + NON_VALID_FUND_CITY_NAME + "' city name found, but not expected.");

        openFundSearchPanel();
        fundSearchPanel
                .setCityName(fundCityName)
                .search();

        fundSearchTab = new FundSearchTab();
        fundSearchTab.verifyItemPresentInList(fundName);
    }

    @Test
    public void verifyAllParametersInFundSearch(){

        openFundSearchPanel();
        fundSearchPanel
                .selectFundType(fundType)
                .selectSearchIn(UITitles.SearchParams.ALL)
                .selectInvestmentCenterLocation(fundLocation)
                .setCityName(fundCityName)
                .setSearchField(fundName)
                .search();

        fundSearchTab = new FundSearchTab();
        fundSearchTab.openFullProfile(fundName).waitReady();
        navigation.back();
        fundSearchTab.verifyProfileNameSelectedInList(fundName);
    }

    private void openFundSearchPanel(){
        this.fundSearchPanel = navigation.searchFunds();
    }

    private void verifyFundsInCrm(int numberOfFundsToCheck){
        for (int i = 0; i < numberOfFundsToCheck; i++) {
            fullProfile = fundSearchTab.openFullProfile(fundSearchTab.getProfileNameSelectedInList());
            fullProfile.waitReady();
            fullProfile.verifyCrmBadge(true);
            navigation.back();
            if(i != 1) fundSearchTab.openNextProfileOverview();
        }
    }
}
