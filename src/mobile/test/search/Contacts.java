package ipreomobile.test.search;

import ipreomobile.data.ContactData;
import ipreomobile.data.InstitutionData;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.blocks.overlay.ListOverlayFilter;
import ipreomobile.ui.blocks.overlay.ListOverlayFilterWithTabs;
import ipreomobile.ui.blocks.overlay.ListOverlaySearch;
import ipreomobile.ui.contacts.ContactFullProfile;
import ipreomobile.ui.contacts.ContactSearchTab;
import ipreomobile.ui.search.ContactSearchPanel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class Contacts extends BaseTest {

    ContactSearchPanel contactSearchPanel;
    ContactSearchTab contactSearchTab;
    ContactFullProfile fullProfile;
    ContactData contactData;
    InstitutionData institutionData;

    String institutionName;
    String institutionListName;
    String companyName;
    String contactName;
    String contactLocation;
    String contactCityName;
    String contactJobFunction;
    String contactListName;

    private static final String NON_VALID_LOCATION_CENTER_NAME  = "United Statse";
    private static final String DEFAULT_NAME                    = "All";

    private int NUMBER_OF_CONTACTS_TO_CHECK                     = 5;
    private int ALL_CONTACTS_NUMBER                             = 140000;

    @BeforeMethod
    public void setupData(Method testMethod){
        contactData             = new ContactData();
        contactData.setTestCaseName(testMethod.getName());
        institutionData         = new InstitutionData();
        institutionData.setTestCaseName(testMethod.getName());

        institutionName         = institutionData.getName();
        institutionListName     = institutionData.getListName();
        companyName             = contactData.getCompanyName();
        contactName                    = contactData.getName();
        contactLocation                = contactData.getLocation();
        contactCityName                = contactData.getCityName();
        contactJobFunction             = contactData.getJobFunction();
        contactListName         = contactData.getListName();
    }

    @Test
    public void verifySearchInSideParametersInContactSearch(){

        openContactSearchPanel();
        contactSearchPanel
                .selectSearchIn(UITitles.SearchParams.ALL)
                .selectSide(UITitles.SearchParams.ALL)
                .search();
        contactSearchTab = new ContactSearchTab();
        Assert.assertTrue(contactSearchTab.getSearchResultsNumber() > ALL_CONTACTS_NUMBER, "Not all contacts were found in default search. Number of contacts should be more then " + ALL_CONTACTS_NUMBER);

        openContactSearchPanel();
        contactSearchPanel
                .selectSearchIn(UITitles.SearchParams.BD_ONLY)
                .selectSide(UITitles.SearchParams.SELL_SIDE)
                .search();
        verifyContactsSide(UITitles.Side.SELL_SIDE, NUMBER_OF_CONTACTS_TO_CHECK);

        openContactSearchPanel();
        contactSearchPanel
                .selectSearchIn(UITitles.SearchParams.COMPANY_CRM)
                .selectSide(UITitles.SearchParams.BUY_SIDE)
                .setInstitutionName(institutionName)
                .search();
        openCurrentContactFullProfile();
        fullProfile.verifyCrmBadge(true);
    }

    @Test
    public void verifyInstitutionNameParameterInContactSearch(){

        openContactSearchPanel();

        String firstCharactersInContactName = contactName.substring(0, 2);

        contactSearchPanel
                .setContactName(firstCharactersInContactName)
                .setInstitutionName(companyName)
                .search();

        contactSearchTab = new ContactSearchTab();
        contactSearchTab.openFullProfile(contactName);

        openContactSearchPanel();
        contactSearchPanel
                .setInstitutionName()
                .clickSelected();

        contactSearchPanel
                .setInstitutionName()
                .verifyResultListEmpty();
    }

    @Test
    public void verifyContactLocationCityNameParametersInContactSearch(){

        openContactSearchPanel();

        String firstCharactersInContactName = contactName.substring(0, 3);

        contactSearchPanel
                .setContactName(firstCharactersInContactName)
                .setContactLocation()
                .verifySelectedItemName(DEFAULT_NAME)

                .setSearchFilter(NON_VALID_LOCATION_CENTER_NAME)
                .verifyResultListEmpty()
                .close();

        contactSearchPanel
                .setContactLocation(contactLocation)
                .setCityName(contactCityName)
                .search();

        contactSearchTab = new ContactSearchTab();
        contactSearchTab.verifyItemPresentInList(contactName);
    }

    @Test
    public void verifyPrimaryJobFunctionParameterInContactSearch(){

        openContactSearchPanel();
        contactSearchPanel
                .setPrimaryJobFunction()
                .verifyAlphabeticalSortingOrder(NUMBER_OF_CONTACTS_TO_CHECK)
                .setSearchFilter(contactJobFunction)
                .select(contactJobFunction);

        contactSearchPanel
                .setPrimaryJobFunction()
                .setSearchFilter(contactJobFunction)
                .verifySelectedItemName(contactJobFunction)
                .close();
        contactSearchPanel.search();

        contactSearchTab = new ContactSearchTab();
        openCurrentContactFullProfile();
        fullProfile.verifyJobFunction(contactJobFunction);
    }

    @Test
    public void verifyContactListParameterInContactSearch(){

        openContactSearchPanel();
        contactSearchPanel
                .selectContactLists()
                .selectListType(UITitles.ListType.MY_LISTS)
                .select(contactListName);
        contactSearchPanel.search();

        contactSearchTab = new ContactSearchTab();
        openCurrentContactFullProfile();
        fullProfile.verifyInstitutionName(companyName);
    }

    @Test
    public void verifyInstitutionListParameterInContactSearch(){

        openContactSearchPanel();
        contactSearchPanel
                .selectInstitutionLists()
                .selectListType(UITitles.ListType.MY_LISTS)
                .select(institutionListName);
        contactSearchPanel.search();

        contactSearchTab = new ContactSearchTab();
        openCurrentContactFullProfile();
        fullProfile.verifyInstitutionName(companyName);
    }

    @Test
    public void verifyContactAllCriteriaSetParameterInContactSearch(){

        openContactSearchPanel();
        contactSearchPanel
                .setSearchField(contactName)
                .search();

        contactSearchTab = new ContactSearchTab();
        contactSearchTab.openFullProfile(contactName);
        new ContactFullProfile();

        String firstCharactersInContactName = contactName.substring(0, 3);

        openContactSearchPanel();
        contactSearchPanel
                .clearSearchFilter()
                .selectSearchIn(UITitles.SearchParams.COMPANY_CRM)
                .selectSide(UITitles.SearchParams.ALL)
                .setSearchField(firstCharactersInContactName)
                .search();

        contactSearchTab = new ContactSearchTab();
        contactSearchTab.openFullProfile(contactName);

        openContactSearchPanel();
        contactSearchPanel
                .reset()
                .setContactName(firstCharactersInContactName)
                .setInstitutionName(companyName)
                .setContactLocation(contactLocation)
                .setCityName(contactCityName)
                .search();

        contactSearchTab = new ContactSearchTab();
        contactSearchTab
                .openFullProfile(contactName)
                .verifyInstitutionName(companyName);

        openContactSearchPanel();
        contactSearchPanel
                .setPrimaryJobFunction(contactJobFunction)
                .search();

        contactSearchTab = new ContactSearchTab();
        contactSearchTab
                .openFullProfile(contactName)
                .verifyJobFunction(contactJobFunction);

        openContactSearchPanel();
        contactSearchPanel
                .reset()
                .selectContactLists(UITitles.ListType.MY_LISTS, contactListName);

        contactSearchPanel
                .selectContactLists()
                .selectListType(UITitles.ListType.MY_LISTS)
                .verifySelectedItemName(contactListName)
                .close();

        contactSearchPanel.search();
        contactSearchTab = new ContactSearchTab();
        Assert.assertFalse(contactSearchTab.isResultSetEmpty(), "No results found. Expected: contacts from '" + contactListName + "' list.");

        openContactSearchPanel();
        contactSearchPanel
                .reset()
                .selectInstitutionLists(UITitles.ListType.MY_LISTS, institutionListName)
                .setContactLocation(contactLocation)
                .setCityName(contactCityName)
                .search();

        contactSearchTab = new ContactSearchTab();
        Assert.assertFalse(contactSearchTab.isResultSetEmpty(), "No results found. Expected: contacts from '" + institutionListName + "' list.");

        openContactSearchPanel();
        contactSearchPanel
                .setPrimaryJobFunction(contactJobFunction)
                .search();

        contactSearchTab = new ContactSearchTab();

        contactSearchTab
                .openFullProfile(contactName)
                .verifyJobFunction(contactJobFunction)
                .verifyInstitutionName(companyName);
        navigation.back();

        contactSearchTab.verifyProfileNameSelectedInList(contactName);
    }

    private void openContactSearchPanel(){
        this.contactSearchPanel = navigation.searchContacts();
    }

    private void openCurrentContactFullProfile(){
        contactSearchTab.waitReady();
        fullProfile = contactSearchTab.openFullProfile(contactSearchTab.getProfileNameSelectedInList());
        fullProfile.waitReady();
    }

    private void verifyContactsSide(UITitles.Side side, int numberOfContactsToCheck){
        for (int i = 0; i < numberOfContactsToCheck; i++) {
            openCurrentContactFullProfile();
            fullProfile.verifySide(UITitles.get(side));
            navigation.back();
            if (i!=1) contactSearchTab.openNextProfileOverview();
        }
    }

}
