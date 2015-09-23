package ipreomobile.test.navigation;

import ipreomobile.core.CollectionHelper;
import ipreomobile.core.Logger;
import ipreomobile.data.InstitutionData;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.blocks.overlay.MapPreviewOverlay;
import ipreomobile.ui.institutions.*;
import ipreomobile.ui.search.InstitutionSearchPanel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static ipreomobile.core.Logger.logMessage;

public class InstitutionsNavigationSmoke extends BaseTest {
    //Test Data
    private InstitutionData institutionData;
    private String institutionName, institutionList;

    //Test Pages
    private InvestorsTab investorsTab;
    private InstitutionFullProfile institutionFullProfile;
    private InstitutionListsTab institutionListsTab;
    private InstitutionSearchTab institutionSearchResults;
    private InstitutionSearchPanel institutionsSearchPanel;


    private String getTestInstitutionName(){
        institutionData = new InstitutionData();
        return institutionData.getName();
    }

    @BeforeMethod
    public void setupTestData() {
        institutionData = new InstitutionData();
        institutionName = institutionData.getName();
        institutionList = institutionData.getListName();
    }

    @Test
    public void searchInstitutionsByDifferentParams(){
        String institutionType = "Municipality";
        String investmentCenterLocation = "Alabama";
        String cityName = "Boston";
        String institutionList = new InstitutionData().getListName();
        String messageTemplate = "%s institutions found for %s='%s'";

        institutionsSearchPanel = navigation.searchInstitutions();
        institutionsSearchPanel.selectSide(UITitles.SearchParams.ALL);
        institutionsSearchPanel.selectSearchIn(UITitles.SearchParams.COMPANY_CRM);
        institutionsSearchPanel.setInstitutionType(institutionType);
        institutionsSearchPanel.search();

        institutionSearchResults = new InstitutionSearchTab();
        Logger.logMessage(String.format(messageTemplate, institutionSearchResults.getSearchResultsNumber() + "", "Institution Type", institutionType));

        institutionsSearchPanel = navigation.searchInstitutions();
        institutionsSearchPanel.setInstitutionType("All");
        institutionsSearchPanel.setInvestmentCenterLocation(investmentCenterLocation);
        institutionsSearchPanel.search();

        institutionSearchResults = new InstitutionSearchTab();
        Logger.logMessage(String.format(messageTemplate, institutionSearchResults.getSearchResultsNumber() + "", "Investment Center / Location", investmentCenterLocation));

        institutionsSearchPanel = navigation.searchInstitutions();
        institutionsSearchPanel.setInvestmentCenterLocation("All");
        institutionsSearchPanel.setCityName(cityName);
        institutionsSearchPanel.search();

        institutionSearchResults = new InstitutionSearchTab();
        Logger.logMessage(String.format(messageTemplate, institutionSearchResults.getSearchResultsNumber() + "", "City Name", cityName));

        institutionsSearchPanel = navigation.searchInstitutions();
        institutionsSearchPanel.setCityName("");
        institutionsSearchPanel.selectInstitutionList(UITitles.ListType.MY_LISTS, institutionList);
        institutionsSearchPanel.search();

        institutionSearchResults = new InstitutionSearchTab();
        Logger.logMessage(String.format(messageTemplate, institutionSearchResults.getSearchResultsNumber() + "",
                "institution list", institutionList) + " in " + UITitles.get(UITitles.ListType.MY_LISTS));
    }

    @Test
    public void openProfileFromSurveillanceResults(){
        navigation.openInstitutions();
        investorsTab = new InvestorsTab();
        investorsTab.showSurveillanceData();

        institutionFullProfile = investorsTab.openFullProfile(institutionName);
        institutionFullProfile.verifyCrmBadge(true);
        Logger.logScreenshot("Opened institution from surveillance data");
    }

    @Test
    public void openInvestorProfileOverviewAndSummary() {
        investorsTab = new InvestorsTab();
        investorsTab
                .openProfileOverview(institutionName)
                .verifyProfileNameSelectedInList(institutionName);

        String institutionFullName = investorsTab.getProfileNameDisplayedInOverview();
        investorsTab
                .openFullProfile(institutionName)
                .verifyProfileName(institutionFullName);

        navigation.back();
        investorsTab.verifyProfileNameSelectedInList(institutionName);
    }

    @Test
    public void verifyListManagerFavoritesPersistence() {
        String[] myListsToCheck = new String[2];
        InstitutionData institutionWithList = new InstitutionData();
        institutionWithList.loadDataSetByIndex(1);
        myListsToCheck[0] = institutionWithList.getListName();

        institutionWithList.loadDataSetByIndex(2);
        myListsToCheck[1] = institutionWithList.getListName();

        navigation
                .openInstitutions()
                .selectListsTab();
        institutionListsTab = new InstitutionListsTab();

        institutionListsTab
                .goToMyLists()
                .check(myListsToCheck);
        institutionListsTab.verifyChecked(myListsToCheck);
        institutionListsTab
                .goToOtherLists()
                .goToMyLists()
                .verifyChecked(myListsToCheck);

        navigation.selectRecentlyViewedTab();
        navigation.selectListsTab();

        institutionListsTab
                .goToMyLists()
                .verifyChecked(myListsToCheck);

        navigation
                .openContacts()
                .selectRecentlyViewedTab();

        navigation
                .openInstitutions()
                .selectListsTab();

        institutionListsTab
                .goToMyLists()
                .verifyChecked(myListsToCheck);
        institutionListsTab.uncheck(myListsToCheck);
        institutionListsTab.verifyUnchecked(myListsToCheck);

        institutionListsTab
                .goToOtherLists()
                .goToMyLists()
                .verifyUnchecked(myListsToCheck);
    }

    @Test
    public void verifyListEntriesSelectionIsNotPersistent() {
        String myListName, otherListName;
        String[] institutionsInOtherList = new String[2];

        institutionData = new InstitutionData();
        institutionData.loadDataSetByTag("myList");
        myListName = institutionData.getListName();

        institutionData.loadDataSetByTag("otherList");
        otherListName = institutionData.getListName();

        institutionData.loadDataSetByTag("institutionFromOtherList1");
        institutionsInOtherList[0] = institutionData.getName();

        institutionData.loadDataSetByTag("institutionFromOtherList2");
        institutionsInOtherList[1] = institutionData.getName();

        navigation
                .openInstitutions()
                .selectListsTab();
        institutionListsTab = new InstitutionListsTab();

        institutionListsTab
                .goToListFromOtherLists(otherListName)
                .verifyListTitle(otherListName);

        institutionListsTab.check(institutionsInOtherList[0]);
        institutionListsTab.verifyChecked(institutionsInOtherList[0]);
        institutionListsTab.verifyUnchecked(institutionsInOtherList[1]);

        institutionListsTab.check(institutionsInOtherList[1]);
        institutionListsTab.verifyChecked(institutionsInOtherList);

        institutionListsTab
                .goToListFromMyLists(myListName)
                .verifyListTitle(myListName)

                .goToListFromOtherLists(otherListName)
                .verifyListTitle(otherListName)
                .verifyUnchecked(institutionsInOtherList);
    }

    @Test
    public void verifyInstitutionsSearchPanel() {
        String searchFilter = institutionName.split("\\s+")[0];

        institutionsSearchPanel = navigation.searchInstitutions();
        institutionsSearchPanel
                .setInstitutionName(searchFilter)
                .selectSide(UITitles.SearchParams.ALL)
                .selectInstitutionList()
                .selectListType(UITitles.ListType.MY_LISTS)
                .select(institutionList);
        institutionsSearchPanel.search();

        institutionSearchResults = new InstitutionSearchTab();
        int resultsNumber = institutionSearchResults.getSearchResultsNumber();
        logMessage("Found " + resultsNumber + " results for '" + searchFilter + "'");

        institutionSearchResults
                .openProfileOverview(institutionName)
                .verifyProfileNameSelectedInList(institutionName);

        institutionSearchResults
                .openFullProfile(institutionName);
        navigation.back();
        institutionSearchResults
                .verifyProfileNameSelectedInList(institutionName);
    }

    @Test
    public void verifyInstitutionsSearchResultsCount() {
        String searchFilter = institutionName.split("\\s+")[0];
        int institutionsFound;

        institutionSearchResults = navigation.searchInstitutions(searchFilter);
        institutionsFound = institutionSearchResults.getSearchResultsNumber();
        Logger.logScreenshot("Institution search is launched for the first time.");

        institutionSearchResults.searchIn(UITitles.SearchLocation.CONTACTS);
        institutionSearchResults.searchIn(UITitles.SearchLocation.INSTITUTIONS);
        Assert.assertEquals(institutionSearchResults.getSearchResultsNumber(), institutionsFound,
                "Different number of institution by search criteria '" + searchFilter + "' was found after repeating search.");
    }

    @Test
    public void verifyInstitutionRecentlyViewedTab(){
        institutionData = new InstitutionData();
        navigation
                .openInstitutions()
                .selectInvestorsTab()
                .openFullProfile(institutionData.getName());
        navigation.back();

        navigation
                .selectRecentlyViewedTab()
                .openFullProfile(institutionData.getName());
        navigation.back();
    }
}
