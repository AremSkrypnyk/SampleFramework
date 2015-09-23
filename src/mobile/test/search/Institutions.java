package ipreomobile.test.search;

import ipreomobile.data.InstitutionData;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.blocks.overlay.ListOverlayFilter;
import ipreomobile.ui.institutions.InstitutionSearchTab;
import ipreomobile.ui.search.InstitutionSearchPanel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import static org.testng.Assert.*;

public class Institutions extends BaseTest{

    InstitutionSearchPanel institutionSearchPanel;
    ListOverlayFilter institutionTypeOverlay;
    InstitutionSearchTab institutionSearchTab;

    InstitutionData institutionData;
    String institutionNameFromBuySide;
    String institutionNameFromSellSide;
    String institutionType;
    String institutionJobTitle;
    String institutionListName;
    String locationName;
    String cityName;

    private static final String DEFAULT_INSTITUTION_TYPE                = "All";

    private static final String NON_EXISTING_INSTITUTION_TYPE           = "Bakn";
    private static final String LOCATION_TYPE                           = "Investment Center";
    private static final String INSTITUTION_JOB_TITLE_BY_DEFAULT        = "Broker";
    private static final String INCORRECT_INSTITUTION_LOCATION_NAME     = "United Kingdmo";
    private static final String INCORRECT_INSTITUTION_CITY_NAME         = "Londno";

    private int NUMBER_OF_INSTITUTIONS_TO_CHECK                 = 5;

    @BeforeMethod
    public void setupData(){
        institutionData             = new InstitutionData();
        institutionNameFromBuySide  = institutionData.getName();
        institutionType             = institutionData.getType();
        institutionListName         = institutionData.getListName();
        cityName                    = institutionData.getLocation();

        institutionData.setTestCaseName("verifyAllParametersInInstitutionSearch");
        institutionNameFromSellSide = institutionData.getName();
        institutionJobTitle         = institutionData.getType();
        locationName                = institutionData.getLocation();
    }

    @Test
    public void verifyTypeParameterInInstitutionSearch(){

        openInstitutionSearchPanel();
        institutionSearchPanel.setInstitutionType(institutionType);

        institutionSearchPanel
                .setInstitutionType()
                .verifySelectedItemName(institutionType)
                .close();

        institutionTypeOverlay = institutionSearchPanel.setInstitutionType();
        institutionTypeOverlay
                .setSearchFilter(NON_EXISTING_INSTITUTION_TYPE)
                .verifyResultListEmpty();

        String firstCharactersInInstitutionType = institutionType.substring(0, 1);

        institutionTypeOverlay
                .setSearchFilter(firstCharactersInInstitutionType)
                .verifySelectedItemName(institutionType);

        institutionTypeOverlay
                .setSearchFilter(" ")
                .select(DEFAULT_INSTITUTION_TYPE);

        institutionSearchPanel
                .setInstitutionType()
                .verifySelectedItemName(DEFAULT_INSTITUTION_TYPE)
                .close();
    }

    @Test
    public void verifySearchInSideParameterInInstitutionSearch(){

        openInstitutionSearchPanel();
        institutionSearchPanel
                .selectSearchIn(UITitles.SearchParams.ALL)
                .selectSide(UITitles.SearchParams.ALL)
                .search();

        institutionSearchTab = new InstitutionSearchTab();
        verifyDescendingOrderOfAssetValues(NUMBER_OF_INSTITUTIONS_TO_CHECK);

        openInstitutionSearchPanel();
        institutionSearchPanel
                .selectSearchIn(UITitles.SearchParams.BD_ONLY)
                .selectSide(UITitles.SearchParams.SELL_SIDE)
                .search();

        verifyProfileJobTitle(INSTITUTION_JOB_TITLE_BY_DEFAULT, NUMBER_OF_INSTITUTIONS_TO_CHECK);

        openInstitutionSearchPanel();
        institutionSearchPanel
                .selectSearchIn(UITitles.SearchParams.BD_ONLY)
                .selectSide(UITitles.SearchParams.SELL_SIDE)
                .setInstitutionType(institutionJobTitle)
                .search();

        institutionSearchTab = new InstitutionSearchTab();
        institutionSearchTab
                .openFullProfile(institutionNameFromSellSide)
                .verifySide(UITitles.Side.SELL_SIDE);

        String firstCharactersInInstitutionNameFromBuySide = institutionNameFromBuySide.substring(0, 2);

        openInstitutionSearchPanel();
        institutionSearchPanel
                .setInstitutionName(firstCharactersInInstitutionNameFromBuySide)
                .selectSearchIn(UITitles.SearchParams.COMPANY_CRM)
                .selectSide(UITitles.SearchParams.BUY_SIDE)
                .search();

        institutionSearchTab = new InstitutionSearchTab();
        institutionSearchTab
                .openFullProfile(institutionNameFromBuySide)
                .verifySide(UITitles.Side.BUY_SIDE)
                .verifyCrmBadge(true);
    }

    @Test
    public void verifyInvestmentCenterCityNameParametersInInstitutionSearch(){

        openInstitutionSearchPanel();

        ListOverlayFilter investmentLocation = institutionSearchPanel.setInvestmentCenterLocation();
        investmentLocation.verifyAlphabeticalSortingOrder(NUMBER_OF_INSTITUTIONS_TO_CHECK);

        String firstCharactersInInstitutionCity = cityName.substring(0, 1);

        investmentLocation
                .setSearchFilter(firstCharactersInInstitutionCity)
                .select(cityName, LOCATION_TYPE);

        investmentLocation =institutionSearchPanel.setInvestmentCenterLocation();

        investmentLocation
                .setSearchFilter(firstCharactersInInstitutionCity)
                .verifyAlphabeticalSortingOrder(NUMBER_OF_INSTITUTIONS_TO_CHECK);

        investmentLocation
                .setSearchFilter(INCORRECT_INSTITUTION_LOCATION_NAME);
        assertTrue(investmentLocation.isResultListEmpty(), "Result list isn't empty, but should be. The are no Location" +
                " center with '" + INCORRECT_INSTITUTION_LOCATION_NAME + "' name");

        String firstCharactersInInstitutionLocation = locationName.substring(0, 2);

        investmentLocation
                .setSearchFilter(firstCharactersInInstitutionLocation)
                .select(locationName);

        institutionSearchPanel
                .setCityName(INCORRECT_INSTITUTION_CITY_NAME)
                .search();

        institutionSearchTab = new InstitutionSearchTab();
        assertTrue(institutionSearchTab.isResultSetEmpty(), "Result list isn't empty, but should be. The are no City" +
                " name with '" + INCORRECT_INSTITUTION_CITY_NAME + "' name");

        openInstitutionSearchPanel();
        institutionSearchPanel
                .setCityName(cityName)
                .search();

        verifyDescendingOrderOfAssetValues(NUMBER_OF_INSTITUTIONS_TO_CHECK);
    }

    @Test
    public void verifyInstitutionListsParameterInInstitutionSearch(){

        openInstitutionSearchPanel();

        institutionSearchPanel
                .selectInstitutionList(UITitles.ListType.MY_LISTS, institutionListName)
                .selectInstitutionList()
                .verifySelectedItemName(institutionListName)
                .close();
        institutionSearchPanel.search();

        institutionSearchTab = new InstitutionSearchTab();
        assertFalse(institutionSearchTab.isResultSetEmpty(), "The result set is empty. Expected : institution(s) from '" + institutionListName + "' list.");
    }

    @Test
    public void verifyAllParametersInInstitutionSearch(){

        openInstitutionSearchPanel();
        institutionSearchPanel.setInstitutionType(institutionType);

        institutionSearchPanel
                .setInstitutionType()
                .verifySelectedItemName(institutionType)
                .close();

        institutionSearchPanel
                .selectSearchIn(UITitles.SearchParams.COMPANY_CRM)
                .search();

        institutionSearchTab = new InstitutionSearchTab();
        assertFalse(institutionSearchTab.isResultSetEmpty(), "Result list is empty, but shouldn't be. Expected institutions from Company CRM");

        openInstitutionSearchPanel();
        institutionSearchPanel
                .selectSearchIn(UITitles.SearchParams.ALL)
                .setInstitutionName(institutionNameFromSellSide)
                .selectSide(UITitles.SearchParams.SELL_SIDE)
                .search();

        institutionSearchTab = new InstitutionSearchTab();
        institutionSearchTab
                .openFullProfile(institutionNameFromSellSide)
                .verifyProfileName(institutionNameFromSellSide);


        navigation.back();
        institutionSearchTab.verifyProfileNameSelectedInList(institutionNameFromSellSide);

        openInstitutionSearchPanel();
        institutionSearchPanel
                .clearInstitutionName()
                .selectSide(UITitles.SearchParams.ALL)
                .setInvestmentCenterLocation(locationName)
                .search();

        institutionSearchTab = new InstitutionSearchTab();
        assertFalse(institutionSearchTab.isResultSetEmpty(), "Result list is empty, but shouldn't be");

        String firstCharactersInInstitutionNameFromBuySide = institutionNameFromBuySide.substring(0, 2);

        openInstitutionSearchPanel();
        institutionSearchPanel
                .setInstitutionName(firstCharactersInInstitutionNameFromBuySide)
                .setCityName(cityName)
                .selectInstitutionList(UITitles.ListType.MY_LISTS, institutionListName)
                .search();

        institutionSearchTab = new InstitutionSearchTab();
        institutionSearchTab
                .openFullProfile(institutionNameFromBuySide)
                .verifyProfileName(institutionNameFromBuySide);

        openInstitutionSearchPanel();

        institutionSearchPanel
                .reset()
                .search();

        institutionSearchTab = new InstitutionSearchTab();
        assertFalse(institutionSearchTab.isResultSetEmpty(), "Result list is empty, but shouldn't be");
    }

    private void openInstitutionSearchPanel(){
        institutionSearchPanel = navigation.searchInstitutions();

    }

    private void verifyDescendingOrderOfAssetValues(int iteration){
        double previousAssetValue;
        double currentAssetValue;
        InstitutionSearchTab institutionSearchTab = new InstitutionSearchTab();

        for (int i = 0; i < iteration; i++) {
            previousAssetValue = institutionSearchTab.getEquityAssetValueForActiveProfile();
            institutionSearchTab.openNextProfileOverview();
            currentAssetValue = institutionSearchTab.getEquityAssetValueForActiveProfile();

            assertTrue(previousAssetValue >= currentAssetValue, "Wrong sorting order detected: active institution "
                    + institutionSearchTab.getProfileNameSelectedInList() + " has equity asset '" + currentAssetValue + "' and the previous one has '"
                    + previousAssetValue + "'. Expected institution list to be sorted in descending order by equity asset value, but found ascending");
        }
    }

    private void verifyProfileJobTitle(String jobTitle){
        String recentJobTitle = institutionSearchTab.getInstitutionJobTitle();
        assertEquals(recentJobTitle, jobTitle.trim(), "Profile has wrong Job Title. Expected '" + jobTitle +
                "', but found '"+ recentJobTitle + "'. Check institution job title for '"+institutionSearchTab.getProfileNameSelectedInList()+"'.");
    }

    private void verifyProfileJobTitle(String jobTitle, int profilesNumberForVerification){
        for (int i = 0; i < profilesNumberForVerification; i++) {
            verifyProfileJobTitle(jobTitle);
            if(i != profilesNumberForVerification - 1)institutionSearchTab.openNextProfileOverview();
        }
    }

}
