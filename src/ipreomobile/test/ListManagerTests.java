package ipreomobile.test;

import ipreomobile.core.Logger;
import ipreomobile.data.ContactData;
import ipreomobile.data.InstitutionData;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.contacts.ContactListsTab;
import ipreomobile.ui.institutions.InstitutionListsTab;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static ipreomobile.ui.UITitles.ListType.*;

public class ListManagerTests extends BaseTest {

    InstitutionListsTab institutionListsTab;
    ContactListsTab contactListsTab;
    InstitutionData institutionData;
    ContactData contactData;
    String institutionsListName;
    String contactsListName;
    String institutionName;
    String contactName;

    public static final String SELECT_LIST_FUM_MASSAGE_TEXT = "Select a list or add to Favorites to view.";
    public static final String ADD_ACTIVITY_FUM_MASSAGE_TEXT = "Select list items to create an activity.";
    public static final String THIS_FEATURE_IS_DISABLED_FUM_MASSAGE_TEXT = "This Feature is disabled in Offline Mode.";
    public static final String THIS_PROFILE_IS_DISABLED_FUM_MASSAGE_TEXT = "This Profile is unavailable to you while in Offline Mode.";
    public static final String INSTITUTION_OTHER_LIST_NAME = "Institutions List";
    public static final String CONTACT_OTHER_LIST_NAME = "Contact List";
    public static final String INCORRECT_LIST_NAME = "gxxhnfggjnxf";


    @BeforeMethod
    public void setupData(){
        institutionData = new InstitutionData();
        institutionName = institutionData.getName();
        institutionsListName = institutionData.getListName();

        contactData = new ContactData();
        contactName = contactData.getName();
        contactsListName = contactData.getListName();
    }

    @Test
    public void verifyInstitutionsListManager(){
        navigation
                .openInstitutions()
                .selectListsTab();

        institutionListsTab = new InstitutionListsTab();

        institutionListsTab
                .verifyListManagerTitle()
                .verifyPickingFavouritesInstructions()
                .verifySelectedTab(MY_LISTS);

        institutionListsTab
                .clickGoToListButton()
                .verifyTooltip(SELECT_LIST_FUM_MASSAGE_TEXT);

        institutionListsTab
                .goToList(institutionsListName)
                .verifyListTitle(institutionsListName)
                .verifyListIconActive();

        institutionListsTab
                .clickActivityButton()
                .verifyTooltip(ADD_ACTIVITY_FUM_MASSAGE_TEXT);

        institutionListsTab
                .openProfileOverview(institutionName)
                .verifyProfileNameDisplayedInOverview(institutionName);

        institutionListsTab
                .openNextProfileOverview();

        String institutionNameToSelect = institutionListsTab.getProfileNameSelectedInList();

        institutionListsTab
                .openFullProfile(institutionNameToSelect)
                .verifyProfileName(institutionNameToSelect);
        navigation.back();

        institutionListsTab.check(institutionName);
        institutionListsTab
                .addActivity()
                .closeActivityDialog();

        navigation
                .openContacts()
                .openInstitutions()
                .selectListsTab();

        institutionListsTab
                .verifyListTitle(institutionsListName)
                .verifyChecked(institutionName)
                .goToListManager()
                .verifyListManagerIconActive()
                .check(institutionsListName);

        String listNameToCheck = institutionListsTab.getNextListName(institutionsListName);

        institutionListsTab.check(listNameToCheck);
        institutionListsTab
                .selectTab(OTHER_LISTS)
                .check(INSTITUTION_OTHER_LIST_NAME);

        institutionListsTab
                .goToList(INSTITUTION_OTHER_LIST_NAME)
                .goToListManager();

        institutionListsTab
                .verifyChecked(INSTITUTION_OTHER_LIST_NAME)
                .uncheck(INSTITUTION_OTHER_LIST_NAME);

        institutionListsTab
                .selectTab(MY_LISTS)
                .verifyChecked(institutionsListName, listNameToCheck)
                .uncheck(institutionsListName, listNameToCheck);

        institutionListsTab
                .setFilter(institutionsListName)
                .verifyItemPresentInList(institutionsListName);

        institutionListsTab
                .setFilter(INCORRECT_LIST_NAME)
                .verifyResultsListEmpty();
    }

    @Test
    public void verifyContactsListManager(){
        navigation
                .openContacts()
                .selectListsTab();

        contactListsTab = new ContactListsTab();

        contactListsTab
                .verifyListManagerTitle()
                .verifyPickingFavouritesInstructions()
                .verifySelectedTab(MY_LISTS);

        contactListsTab
                .clickGoToListButton()
                .verifyTooltip(SELECT_LIST_FUM_MASSAGE_TEXT);

        contactListsTab
                .goToList(contactsListName)
                .verifyListTitle(contactsListName)
                .verifyListIconActive();

        contactListsTab
                .clickActivityButton()
                .verifyTooltip(ADD_ACTIVITY_FUM_MASSAGE_TEXT)
                .openProfileOverview(contactName);

        contactListsTab
                .verifyProfileNameDisplayedInOverview(contactName)
                .openNextProfileOverview();

        String contactNameToSelect = contactListsTab.getProfileNameSelectedInList();

        contactListsTab
                .openFullProfile(contactNameToSelect)
                .verifyProfileName(contactNameToSelect);
        navigation.back();

        contactListsTab.check(contactName);
        contactListsTab
                .addActivity()
                .closeActivityDialog();

        navigation
                .openInstitutions()
                .openContacts()
                .selectListsTab();

        contactListsTab
                .verifyListTitle(contactsListName)
                .verifyChecked(contactName);

        contactListsTab
                .goToListManager()
                .verifyListManagerIconActive()
                .check(contactsListName);

        String listNameToCheck = contactListsTab.getPreviousListName(contactsListName);

        contactListsTab.check(listNameToCheck);
        contactListsTab
                .selectTab(OTHER_LISTS)
                .check(CONTACT_OTHER_LIST_NAME);

        contactListsTab
                .goToList(CONTACT_OTHER_LIST_NAME)
                .goToListManager();

        contactListsTab
                .verifyChecked(CONTACT_OTHER_LIST_NAME)
                .uncheck(CONTACT_OTHER_LIST_NAME);

        Logger.logError("The following scenario is failing due to the issue #228440. If no failure follows, remove this.");
        contactListsTab
                .selectTab(MY_LISTS);

        contactListsTab
                .verifyChecked(contactsListName, listNameToCheck)
                .uncheck(contactsListName, listNameToCheck);

        contactListsTab
                .setFilter(contactsListName)
                .verifyItemPresentInList(contactsListName);

        contactListsTab
                .setFilter(INCORRECT_LIST_NAME)
                .verifyResultsListEmpty();

    }

    @Test
    public void verifyInstitutionsListManagerInOfflineMode(){

        navigation
                .openInstitutions();

        navigation
                .switchToOfflineMode()
                .clickOnListsTab()
                .verifyTooltip(THIS_FEATURE_IS_DISABLED_FUM_MASSAGE_TEXT);

        navigation
                .switchToOnlineMode()
                .selectListsTab();

        institutionListsTab = new InstitutionListsTab();

        institutionListsTab
                .goToList(institutionsListName)
                .verifyListIconActive()
                .openProfileOverview(institutionName);

        institutionListsTab.check(institutionName);

        institutionListsTab
                .goToListManager()
                .verifyListManagerIconActive()
                .check(institutionsListName);

        navigation.switchToOfflineMode();

        institutionListsTab
                .verifyListManagerTitle()
                .verifySelectedTab(MY_LISTS);

        institutionListsTab
                .verifySearchFilterUnavailableInOfflineMode()
                .clickOnFilter()
                .verifyTooltip(THIS_FEATURE_IS_DISABLED_FUM_MASSAGE_TEXT);

        institutionListsTab
                .verifyTabControlUnavailableInOfflineMode()
                .clickOnTab(OTHER_LISTS)
                .verifyTooltip(THIS_FEATURE_IS_DISABLED_FUM_MASSAGE_TEXT);

        institutionListsTab
                .goToList(institutionsListName)
                .verifyListTitle(institutionsListName)
                .verifyFavoriteListIconActive()

                .clickActivityButton()
                .verifyTooltip(THIS_FEATURE_IS_DISABLED_FUM_MASSAGE_TEXT)
                .verifyItemFirstInList(institutionListsTab.getProfileNameSelectedInList())
                .verifyChecked(institutionName);

        institutionListsTab
                .openFullProfile(institutionName)
                .verifyProfileName(institutionName);

        navigation.back();

        navigation
                .clickOnListsTab()
                .verifyTooltip(THIS_FEATURE_IS_DISABLED_FUM_MASSAGE_TEXT);
    }

    @Test
    public void verifyContactsListManagerInOfflineMode(){

        navigation
                .openContacts()
                .switchToOfflineMode()
                .clickOnListsTab()
                .verifyTooltip(THIS_FEATURE_IS_DISABLED_FUM_MASSAGE_TEXT)
                .switchToOnlineMode()
                .selectListsTab();

        contactListsTab = new ContactListsTab();

        contactListsTab
                .goToList(contactsListName)
                .verifyListIconActive()
                .openProfileOverview(contactName);
        contactListsTab.check(contactName);
        contactListsTab
                .goToListManager()
                .verifyListManagerIconActive()
                .check(contactsListName);

        navigation.switchToOfflineMode();

        contactListsTab
                .verifyListManagerTitle()
                .verifySelectedTab(MY_LISTS);

        contactListsTab
                .verifySearchFilterUnavailableInOfflineMode()
                .clickOnFilter()
                .verifyTooltip(THIS_FEATURE_IS_DISABLED_FUM_MASSAGE_TEXT);

        contactListsTab
                .verifyTabControlUnavailableInOfflineMode()
                .clickOnTab(OTHER_LISTS)
                .verifyTooltip(THIS_FEATURE_IS_DISABLED_FUM_MASSAGE_TEXT);

        contactListsTab
                .goToList(contactsListName)
                .verifyListTitle(contactsListName)
                .verifyFavoriteListIconActive()
                .clickActivityButton()
                .verifyTooltip(THIS_FEATURE_IS_DISABLED_FUM_MASSAGE_TEXT)
                .verifyItemFirstInList(contactListsTab.getProfileNameSelectedInList())
                .verifyChecked(contactName);

        contactListsTab
                .openFullProfile(contactName)
                .verifyProfileName(contactName);

        navigation.back();

        navigation
                .clickOnListsTab()
                .verifyTooltip(THIS_FEATURE_IS_DISABLED_FUM_MASSAGE_TEXT);
    }
}
