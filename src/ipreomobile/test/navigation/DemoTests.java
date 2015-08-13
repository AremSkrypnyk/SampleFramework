package ipreomobile.test.navigation;

import ipreomobile.core.Driver;
import ipreomobile.core.Logger;
import ipreomobile.core.StringHelper;
import ipreomobile.data.ActivityData;
import ipreomobile.data.ContactData;
import ipreomobile.data.InstitutionData;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.hamburgerItems.Hamburger;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.activities.ActivitySearchTab;
import ipreomobile.ui.activities.GroupActivityOverlay;
import ipreomobile.ui.activities.IndividualActivityOverlay;
import ipreomobile.ui.blocks.overlay.*;
import ipreomobile.ui.contacts.ActivityTab;
import ipreomobile.ui.contacts.ContactFullProfile;
import ipreomobile.ui.contacts.ContactListsTab;
import ipreomobile.ui.contacts.ContactSearchTab;
import ipreomobile.ui.institutions.InstitutionFullProfile;
import ipreomobile.ui.institutions.InstitutionListsTab;
import ipreomobile.ui.institutions.InstitutionSearchTab;
import ipreomobile.ui.institutions.InvestorsTab;
import ipreomobile.ui.search.CalendarSearchPanel;
import ipreomobile.ui.search.ContactSearchPanel;
import ipreomobile.ui.search.InstitutionSearchPanel;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;

public class DemoTests extends BaseTest{


    //Test Data
    InstitutionData institutionData;
    ActivityData activityData;
    ContactData contactData;

    //Test Pages
    private InstitutionListsTab institutionlistsTab;

    ContactSearchTab contactSearchResults;
    ContactSearchPanel contactSearchPanel;
    ContactFullProfile contactFullProfile;
    ContactListsTab contactListsTab;

    InstitutionListsTab institutionListsTab;
    InstitutionFullProfile institutionFullProfile;
    InstitutionSearchPanel institutionSearchPanel;
    InstitutionSearchTab institutionSearchResults;
    InvestorsTab investorsTab;

    MapPreviewOverlay mapPreview;

    CalendarSearchPanel calendarSearchPanel;

    @Test
    public void frameworkDemoInstitutions() {
        String institutionType = "Municipality";
        String investmentCenterLocation = "Alabama";
        String cityName = "Boston";
        String institutionList = "Issuers";
        String messageTemplate = "%s institutions found for search criteria %s = '%s'.";
        String[] myListsToCheck     = {"Issuers", "Fund List"};
        String[] otherListsToCheck  = {"CRM only", "Janus Institutions List"};

        institutionData = new InstitutionData();
        String institutionName = institutionData.getName();

        Logger.logMessage("Introducing institutions test.");

        navigation.openInstitutions();
        investorsTab = new InvestorsTab();
        investorsTab.showSurveillanceData();

        Logger.logScreenshot("Surveillance Data shown.");

        investorsTab.showPublicData();

        Logger.logScreenshot("Public Data shown.");

        institutionSearchPanel = navigation.searchInstitutions();
        institutionSearchPanel.selectSide(UITitles.SearchParams.ALL);
        institutionSearchPanel.selectSearchIn(UITitles.SearchParams.ALL);
        institutionSearchPanel.setInstitutionType(institutionType);

        Logger.logScreenshot("Searching by Institution Type.");

        institutionSearchPanel.search();

        institutionSearchResults = new InstitutionSearchTab();
        Logger.logScreenshot(String.format(messageTemplate, institutionSearchResults.getSearchResultsNumber() + "", "Institution Type", institutionType));

        institutionSearchPanel = navigation.searchInstitutions();
        institutionSearchPanel.reset();
        institutionSearchPanel.selectSide(UITitles.SearchParams.ALL);
        institutionSearchPanel.selectSearchIn(UITitles.SearchParams.ALL);
        institutionSearchPanel.setInvestmentCenterLocation(investmentCenterLocation);

        Logger.logScreenshot("Searching by Institution Type.");

        institutionSearchPanel.search();

        institutionSearchResults = new InstitutionSearchTab();
        Logger.logScreenshot(String.format(messageTemplate, institutionSearchResults.getSearchResultsNumber() + "", "Investment Center / Location", investmentCenterLocation));

        institutionSearchPanel = navigation.searchInstitutions();
        institutionSearchPanel.reset();
        institutionSearchPanel.setCityName(cityName);
        institutionSearchPanel.selectSide(UITitles.SearchParams.ALL);
        institutionSearchPanel.selectSearchIn(UITitles.SearchParams.ALL);

        Logger.logScreenshot("Searching by Investment Center / Location.");

        institutionSearchPanel.search();

        institutionSearchResults = new InstitutionSearchTab();
        Logger.logScreenshot(String.format(messageTemplate, institutionSearchResults.getSearchResultsNumber() + "", "City Name", cityName));

        institutionSearchPanel = navigation.searchInstitutions();
        institutionSearchPanel.reset();
        institutionSearchPanel.selectInstitutionList(UITitles.ListType.MY_LISTS, "Issuers");
        institutionSearchPanel.selectSide(UITitles.SearchParams.ALL);
        institutionSearchPanel.selectSearchIn(UITitles.SearchParams.ALL);

        Logger.logScreenshot("Searching by institution list Issuers.");

        institutionSearchPanel.search();

        institutionSearchResults = new InstitutionSearchTab();
        Logger.logScreenshot(String.format(messageTemplate, institutionSearchResults.getSearchResultsNumber() + "",
                "institution list", institutionList) + " in " + UITitles.get(UITitles.ListType.MY_LISTS));

        navigation.selectTab(UITitles.PanelTabs.LISTS);
        institutionListsTab = new InstitutionListsTab();
        institutionListsTab
                .goToMyLists()
                .check(myListsToCheck);
        institutionListsTab.verifyChecked(myListsToCheck);

        Logger.logScreenshot(StringHelper.nameArrayToString(myListsToCheck) + " are checked as expected.");

        institutionListsTab.goToOtherLists()
                .check(otherListsToCheck);
        institutionListsTab.verifyChecked(otherListsToCheck);

        Logger.logScreenshot(StringHelper.nameArrayToString(otherListsToCheck) + " are checked as expected.");

        institutionListsTab.goToMyLists();
        institutionListsTab.verifyChecked(myListsToCheck);
        institutionListsTab.uncheck(myListsToCheck);
        institutionListsTab.verifyUnchecked(myListsToCheck);

        Logger.logScreenshot(StringHelper.nameArrayToString(myListsToCheck) + " are unchecked as expected.");

        institutionListsTab.goToOtherLists();
        institutionListsTab.verifyChecked(otherListsToCheck);
        institutionListsTab.uncheck(otherListsToCheck);
        institutionListsTab.verifyUnchecked(otherListsToCheck);

        Logger.logScreenshot(StringHelper.nameArrayToString(otherListsToCheck) + " are unchecked as expected.");

        String listWithEntries = "Ashwin Institution List";
        String listToSwitchTo = "Issuers";
        String[] institutionsToCheck  = {
                "Fidelity Management & Research Company",
                "Fidelity Management & Research (Japan), Inc."
        };

        institutionListsTab = new InstitutionListsTab();

        institutionListsTab.goToListFromOtherLists(listWithEntries);
        institutionListsTab.verifyListTitle(listWithEntries);

        institutionListsTab.check(institutionsToCheck[0]);
        institutionListsTab.verifyChecked(institutionsToCheck[0]);

        Logger.logScreenshot(institutionsToCheck[0] + " is checked, " + institutionsToCheck[1] + " is unchecked.");

        institutionListsTab.verifyUnchecked(institutionsToCheck[1]);

        institutionListsTab.check(institutionsToCheck[1]);
        institutionListsTab.verifyChecked(institutionsToCheck);

        institutionListsTab.goToListFromMyLists(listToSwitchTo);
        institutionListsTab.verifyListTitle(listToSwitchTo);

        institutionListsTab.goToListFromOtherLists(listWithEntries);
        institutionListsTab.verifyListTitle(listWithEntries);

        Logger.logScreenshot(listWithEntries + " is opened.");

        institutionListsTab.verifyUnchecked(institutionsToCheck);

        navigation.openInstitutionProfile(institutionName);
        institutionFullProfile = new InstitutionFullProfile();
        institutionFullProfile.verifyCompanySiteLink();

        UITitles.ProfileTab[] tabs = {
                UITitles.ProfileTab.DETAILS,
                UITitles.ProfileTab.ACTIONS,
                UITitles.ProfileTab.OWNERSHIP,
                UITitles.ProfileTab.TARGETING,
                UITitles.ProfileTab.FOCUS,
                UITitles.ProfileTab.ACTIONS,
                UITitles.ProfileTab.FUNDS,
                UITitles.ProfileTab.CURRENT_HOLDINGS,
                UITitles.ProfileTab.CONTACTS,
                UITitles.ProfileTab.ADDITIONAL_INFO,
        };

        for (int i=0; i<tabs.length; i++) {
            institutionFullProfile.selectTab(tabs[i]);
            Logger.logScreenshot("Visited tab '"+UITitles.get(tabs[i])+"'.");
        }

        navigation.back();

        String institutionName1 = institutionName;
        String institutionName2 = "Fidelity International Limited - FIL Investment Management (Singapore), LTD";

        Hamburger hamburger = navigation.openHamburger();
        Driver.pause(1000);
        GroupActivityOverlay groupActivity = hamburger.addActivity();

        groupActivity.setSubject("Test activity created by automation script");
        groupActivity.selectType("Analyst Day");
        groupActivity.addInstitutionsAsExternalParticipantsFromSearch("Fidelity", institutionName1, institutionName2);
        groupActivity.checkParticipantAsOrganizer(institutionName1);
        groupActivity.checkAcceptParticipation(institutionName2);
        groupActivity.checkDeclineParticipation(institutionName1);
        groupActivity.saveActivity();
        Driver.pause(5000);
    }

    @Test
    public void frameworkDemoContacts() {
        contactData = new ContactData();
        String shortContactName = contactData.getName();
        navigation.openContactProfile(shortContactName);

        contactFullProfile = new ContactFullProfile();
        contactFullProfile.verifyActiveTab("Details");

        boolean isActive = true, isSubscribed = true;
        contactFullProfile.verifyActiveAndSubscribedBadge(isActive, isSubscribed);
        contactFullProfile.verifyBdVersusCrmInfo();
        contactFullProfile.verifyEmailPreviewOverlay();
        Logger.logMessage(contactFullProfile.getJobFunction());
        Logger.logMessage(contactFullProfile.getSide());
        Logger.logMessage(contactFullProfile.getFax());
        Logger.logMessage(contactFullProfile.getPhone());

        contactFullProfile.verifyInstitutionName(contactData.getCompanyName());

        String companyName = contactFullProfile.getInstitutionName();
        contactFullProfile.goToInstitutionProfile();
        institutionFullProfile = new InstitutionFullProfile();
        institutionFullProfile.verifyProfileName(companyName);
        navigation.back();

        UITitles.ProfileTab[] tabs = {
                UITitles.ProfileTab.DETAILS,
                UITitles.ProfileTab.ACTIONS,
                UITitles.ProfileTab.OWNERSHIP,
                UITitles.ProfileTab.FOCUS,
                UITitles.ProfileTab.ACTIONS,
                UITitles.ProfileTab.FUNDS,
                UITitles.ProfileTab.ADDITIONAL_INFO,
        };

        for (int i=0; i<tabs.length; i++) {
            contactFullProfile.selectTab(tabs[i]);
            Logger.logScreenshot("Visited tab '"+UITitles.get(tabs[i])+"'.");
        }

        FixDataOverlay fixDataForm = contactFullProfile.openFixDataOverlay();
        fixDataForm.verifyField("Customer:", "Artem Skrypnyk");
        fixDataForm.verifyField("Contact Name:", contactData.getFullName());
        fixDataForm.verifyField("Institution Name:", companyName);
        fixDataForm.cancel();

        activityData = new ActivityData();
        activityData.loadDataSetByIndex(1);
        String overviewSubject = activityData.getSubject();
        activityData.loadDataSetByIndex(2);
        String summarySubject = activityData.getSubject();

        navigation.openContacts();
        navigation.selectTab(UITitles.PanelTabs.ACTIVITY);
        ActivityTab activityTab = new ActivityTab();

        activityTab.openProfileOverview(overviewSubject);
        activityTab.verifyProfileNameSelectedInList(overviewSubject);

        activityTab.openFullProfile(summarySubject);
        navigation.back();
        activityTab.verifyProfileNameSelectedInList(summarySubject);

        String[] otherListsToCheck  = {"CRM-Only contacts", "Janus contacts list"};

        navigation.selectTab(UITitles.PanelTabs.LISTS);
        contactListsTab = new ContactListsTab();
        contactListsTab.goToOtherLists();
        contactListsTab.check(otherListsToCheck);
        contactListsTab.verifyChecked(otherListsToCheck);

        contactListsTab.goToList(otherListsToCheck[1]);
        contactListsTab.check("Andrew Acker");
    }

    @Test
    public void testAddActivityDialogForInstitutions() {
        String institutionName1 = "Fidelity Management & Research Company";
        String institutionName2 = "Fidelity International Limited - FIL Investment Management (Singapore), LTD";

        String listForContacts = "ECU list";
        String listContactName1 = "Jeffrey Porter";
        String listContactName2 = "Steven Zakely";

        Hamburger hamburger = navigation.openHamburger();
        Driver.pause(1000);
        GroupActivityOverlay groupActivity = hamburger.addActivity();
        IndividualActivityOverlay individualActivity = groupActivity.switchToIndividualActivity();

        individualActivity.setSubject("Test activity created by automation script");

        individualActivity.selectType("Analyst Day");
        individualActivity.addInstitutionsAsExternalParticipantsFromSearch("Fidelity", institutionName1, institutionName2);
        individualActivity.addContactsAsExternalParticipantsFromOtherLists(listForContacts, listContactName1, listContactName2);
        individualActivity.selectAllParticipants();
        individualActivity.inputIndividualNotes("Note for all");
        individualActivity.setupDateTimeSlot().turnOnAllDayActivity().turnOffAllDayActivity().close();
/*        individualActivity.selectParticipant(institutionName2);
        individualActivity.deselectParticipant(institutionName1);
        individualActivity.markDeclineParticipation(institutionName1);*/
        individualActivity.saveActivity();
        Driver.pause(5000);
    }

    //@Test
    public void testAddActivityDialogForContacts() throws InterruptedException {
        String searchFilterForContacts = "Arani";
        String contactName1 = "Geetha Arani";
        String contactName2 = "Ramin Arani";

        String listForInstitutions = "Issuers";
        String institutionFromList = "Apple, Inc.";

        String listForContacts = "ECU list";
        String listContactName1 = "Jeffrey Porter";
        String listContactName2 = "Steven Zakely";

        Hamburger hamburger = navigation.openHamburger();
        Thread.sleep(1000);
        GroupActivityOverlay groupActivity = hamburger.addActivity();
        Thread.sleep(1000);
        groupActivity.setSubject("Test activity created by automation script for contacts").selectType("One-on-One");
        groupActivity.setNotes("Test activity notes - input");
        groupActivity.addContactsAsExternalParticipantsFromSearch(searchFilterForContacts, contactName1, contactName2).checkParticipantAsOrganizer(contactName1).
                checkAcceptParticipation(contactName2).checkDeclineParticipation(contactName1);

        groupActivity.addInstitutionsAsExternalParticipantsFromMyLists(listForInstitutions, institutionFromList);
        groupActivity.addContactsAsExternalParticipantsFromOtherLists(listForContacts, listContactName1, listContactName2);

        groupActivity.checkParticipantAsOrganizer(listContactName2).uncheckParticipantAsOrganizer(contactName1).uncheckAcceptParticipation(listContactName1).uncheckDeclineParticipation(contactName1);
        groupActivity.selectTopic("Financials").selectLocation("Maidan").
                selectSymbols("GOOG", "Google, Inc.");
        groupActivity.selectMacroIndustry("Financials").
                selectMidIndustry("Banking").
                selectMicroIndustry("Banks");
        groupActivity.setNotes("Trololo");
        groupActivity.setupDateTimeSlot().selectStartDateTime().selectYear("2014").selectMonthDay("Jul 29").selectHours("09").selectMinutes("10").selectPeriodOfDay("AM").close();
        //groupActivity.setupDate().selectEndDate().selectYear("2014").selectMonthDay("Jul 29").selectHours("07").selectMinutes("30").selectPeriodOfDay("PM").close();
        groupActivity.setupDateTimeSlot().selectEndDateTime().selectDate("06/25/2013").selectTime("06:15 AM").close();
        Thread.sleep(1000);
        groupActivity.saveActivity();
        Thread.sleep(5000);
    }

    @Test
    //TODO: No visible item was found in the list
    public void verifyListManagerFavoritesPersistence() {
        String[] myListsToCheck     = {"Issuers", "Fund List"};
        String[] otherListsToCheck  = {"CRM only", "Janus Institutions List"};

        navigation.openInstitutions();
        navigation.selectTab(UITitles.PanelTabs.LISTS);
        institutionlistsTab = new InstitutionListsTab();

        institutionlistsTab.goToMyLists();
        institutionlistsTab.check(myListsToCheck);
        institutionlistsTab.verifyChecked(myListsToCheck);

        institutionlistsTab.goToOtherLists();
        institutionlistsTab.check(otherListsToCheck);
        institutionlistsTab.verifyChecked(otherListsToCheck);

        institutionlistsTab.goToMyLists();
        institutionlistsTab.verifyChecked(myListsToCheck);

        navigation.selectTab(UITitles.PanelTabs.RECENTLY_VIEWED);
        navigation.selectTab(UITitles.PanelTabs.LISTS);

        institutionlistsTab.goToMyLists();
        institutionlistsTab.verifyChecked(myListsToCheck);

        institutionlistsTab.goToOtherLists();
        institutionlistsTab.verifyChecked(otherListsToCheck);

        navigation.openContacts();
        navigation.selectTab(UITitles.PanelTabs.RECENTLY_VIEWED);
        navigation.openInstitutions();
        navigation.selectTab(UITitles.PanelTabs.LISTS);

        institutionlistsTab.goToMyLists();
        institutionlistsTab.verifyChecked(myListsToCheck);
        institutionlistsTab.uncheck(myListsToCheck);
        institutionlistsTab.verifyUnchecked(myListsToCheck);

        institutionlistsTab.goToOtherLists();
        institutionlistsTab.verifyChecked(otherListsToCheck);
        institutionlistsTab.uncheck(otherListsToCheck);
        institutionlistsTab.verifyUnchecked(otherListsToCheck);
        institutionlistsTab.goToList(otherListsToCheck[1]);

        institutionlistsTab.goToMyLists();
        institutionlistsTab.verifyUnchecked(myListsToCheck);
        institutionlistsTab.goToOtherLists();
        institutionlistsTab.verifyUnchecked(otherListsToCheck);
    }

    //@Test
    //TODO: new test data
    public void verifyContactCanBeFoundByCompanyName(){
        contactData = new ContactData();
        String contactName = contactData.getName();
        String companyName;

        navigation.searchContacts(contactName);
        contactSearchResults = new ContactSearchTab();
        companyName = contactSearchResults.getInstitutionName(contactName);

        contactSearchPanel = navigation.searchContacts();
        contactSearchPanel.setContactName("");
        contactSearchPanel.setInstitutionName(companyName);
        contactSearchPanel.search();

        contactSearchResults = new ContactSearchTab();
        contactSearchResults.verifyItemIsPresentInSearchResults(contactName);
    }

    //@Test
    //TODO: null pointer
    public void searchActivityByDate(){
        activityData = new ActivityData();
        String activitySubject = activityData.getSubject();
        String activityDate = activityData.getStartDate();

        navigation.searchActivities();
        calendarSearchPanel = new CalendarSearchPanel();
        calendarSearchPanel.selectStartDate().selectDate(activityDate);
        calendarSearchPanel.selectEndDate().selectDate(activityDate);
        calendarSearchPanel.search();

        ActivitySearchTab searchTab = new ActivitySearchTab();
        searchTab.openProfileOverview(activitySubject);
        searchTab.verifyProfileNameSelectedInList(activitySubject);
    }

    @Test
    public void institutionsSearchDemoTest() {
        InstitutionSearchPanel institutionSearchPanel = new InstitutionSearchPanel();
        ListOverlayFilter institutionTypeOverlay;
        InstitutionSearchTab institutionSearchTab;

        String INSTITUTION_NAME_FROM_SELL_SIDE = "Paradigm Capital, Inc.";
        String INSTITUTION_NAME_FROM_BUY_SIDE  = "Fidelity Management & Research (UK), Inc.";
        String EXISTING_INSTITUTION_TYPE       = "Bank";
        String INSTITUTION_LOCATION_NAME       = "United Kingdom";
        String INSTITUTION_CITY_NAME           = "London";
        String INSTITUTIONS_LIST_NAME          = "Test Fidelity List";

        openInstitutionSearchPanel();
        institutionSearchPanel.setInstitutionType(EXISTING_INSTITUTION_TYPE);

        institutionTypeOverlay = institutionSearchPanel.setInstitutionType();
        institutionTypeOverlay.verifySelectedItemName(EXISTING_INSTITUTION_TYPE);
        institutionTypeOverlay.close();

        institutionSearchPanel.selectSearchIn(UITitles.SearchParams.COMPANY_CRM);
        institutionSearchPanel.search();
        institutionSearchTab = new InstitutionSearchTab();
        assertFalse(institutionSearchTab.isResultSetEmpty(), "Result list is empty, but shouldn't be. Expected institutions from Company CRM");

        openInstitutionSearchPanel();
        institutionSearchPanel.selectSearchIn(UITitles.SearchParams.ALL);
        institutionSearchPanel.setSearchField(INSTITUTION_NAME_FROM_SELL_SIDE);
        institutionSearchPanel.selectSide(UITitles.SearchParams.SELL_SIDE);
        institutionSearchPanel.search();
        institutionSearchTab = new InstitutionSearchTab();
        institutionSearchTab.openFullProfile(INSTITUTION_NAME_FROM_SELL_SIDE);
        InstitutionFullProfile summary = new InstitutionFullProfile();
        summary.waitReady();

        navigation.back();
        institutionSearchTab.verifyProfileNameSelectedInList(INSTITUTION_NAME_FROM_SELL_SIDE);

        openInstitutionSearchPanel();
        institutionSearchPanel.clearSearchFilter();
        institutionSearchPanel.selectSide(UITitles.SearchParams.ALL);
        institutionSearchPanel.setInvestmentCenterLocation(INSTITUTION_LOCATION_NAME);
        institutionSearchPanel.search();
        institutionSearchTab = new InstitutionSearchTab();
        assertFalse(institutionSearchTab.isResultSetEmpty(), "Result list is empty, but shouldn't be");

        openInstitutionSearchPanel();
        institutionSearchPanel.setSearchField(INSTITUTION_NAME_FROM_BUY_SIDE.substring(0, 1));
        institutionSearchPanel.setCityName(INSTITUTION_CITY_NAME);
        institutionSearchPanel.selectInstitutionList(UITitles.ListType.OTHER_LISTS, INSTITUTIONS_LIST_NAME);
        institutionSearchPanel.search();
        institutionSearchTab = new InstitutionSearchTab();
        institutionSearchTab.openFullProfile(INSTITUTION_NAME_FROM_BUY_SIDE);
        summary = new InstitutionFullProfile();
        summary.waitReady();

        openInstitutionSearchPanel();
        institutionSearchPanel.reset();
        institutionSearchPanel.search();
        institutionSearchTab = new InstitutionSearchTab();
        assertFalse(institutionSearchTab.isResultSetEmpty(), "Result list is empty, but shouldn't be");
    }

    private void openInstitutionSearchPanel(){
        this.institutionSearchPanel = navigation.searchInstitutions();
    }

    @Test
    public void contactsSearchDemoTest() {
        ContactSearchPanel contactSearchPanel = new ContactSearchPanel();
        ContactSearchTab contactSearchTab;
        ContactFullProfile summary;
        ContactData contactData;

        String CONTACT_LIST_NAME               = "Filelity_contacts";
        String INSTITUTION_LIST_NAME           = "Fidelity_Inst_list";

        contactData = new ContactData();

        openContactSearchPanel();
        contactSearchPanel.setSearchField(contactData.getName());
        contactSearchPanel.search();
        contactSearchTab = new ContactSearchTab();
        contactSearchTab.openFullProfile(contactData.getName());
        new ContactFullProfile();


        openContactSearchPanel();
        contactSearchPanel.clearSearchFilter();
        contactSearchPanel.selectSearchIn(UITitles.SearchParams.COMPANY_CRM);
        contactSearchPanel.selectSide(UITitles.SearchParams.ALL);
        contactSearchPanel.setSearchField(contactData.getName().substring(0, 3));
        contactSearchPanel.search();
        contactSearchTab = new ContactSearchTab();
        contactSearchTab.openFullProfile(contactData.getName());

        openContactSearchPanel();
        contactSearchPanel.reset();
        contactSearchPanel.setInstitutionName(contactData.getCompanyName());
        contactSearchPanel.setContactLocation(contactData.getLocation());
        contactSearchPanel.setCityName(contactData.getCityName());
        contactSearchPanel.search();
        contactSearchTab = new ContactSearchTab();
        summary = contactSearchTab.openFullProfile(contactData.getName());
        summary.verifyInstitutionName(contactData.getCompanyName());

        openContactSearchPanel();
        contactSearchPanel.setPrimaryJobFunction(contactData.getJobFunction());
        contactSearchPanel.search();
        contactSearchTab = new ContactSearchTab();
        summary = contactSearchTab.openFullProfile(contactData.getName());
        summary.verifyJobFunction(contactData.getJobFunction());

        openContactSearchPanel();
        contactSearchPanel.reset();
        contactSearchPanel.selectContactLists(UITitles.ListType.OTHER_LISTS, CONTACT_LIST_NAME);
        ListOverlayFilterWithTabs contactListOverlay = contactSearchPanel.selectContactLists();
        contactListOverlay.selectListType(UITitles.ListType.OTHER_LISTS);
        contactListOverlay.verifySelectedItemName(CONTACT_LIST_NAME);
        contactListOverlay.close();
        contactSearchPanel.search();
        contactSearchTab = new ContactSearchTab();
        Assert.assertFalse(contactSearchTab.isResultSetEmpty(), "No results found. Expected: contacts from '" + CONTACT_LIST_NAME + "' list.");

        openContactSearchPanel();
        contactSearchPanel.reset();
        contactSearchPanel.selectInstitutionLists(UITitles.ListType.OTHER_LISTS, INSTITUTION_LIST_NAME);
        contactSearchPanel.setContactLocation(contactData.getLocation());
        contactSearchPanel.setCityName(contactData.getCityName());
        contactSearchPanel.search();
        contactSearchTab = new ContactSearchTab();
        Assert.assertFalse(contactSearchTab.isResultSetEmpty(), "No results found. Expected: contacts from '" + INSTITUTION_LIST_NAME + "' list.");

        openContactSearchPanel();
        contactSearchPanel.setPrimaryJobFunction(contactData.getJobFunction());
        contactSearchPanel.search();
        contactSearchTab = new ContactSearchTab();
        summary = contactSearchTab.openFullProfile(contactData.getName());
        summary.verifyJobFunction(contactData.getJobFunction());
        summary.verifyInstitutionName(contactData.getCompanyName());
        navigation.back();

        contactSearchTab.verifyProfileNameSelectedInList(contactData.getName());
    }

    private void openContactSearchPanel(){
        this.contactSearchPanel = navigation.searchContacts();
    }

    @Test
    public void activitySearchDemoTest(){
        CalendarSearchPanel calendarSearchPanel = new CalendarSearchPanel();
        ActivitySearchTab activitySearchTab;
        ListOverlaySearchMultiSelect addSymbol;

        openActivitySearchPanel();
        calendarSearchPanel.setSearchField("Act");
        calendarSearchPanel.search();
        activitySearchTab = new ActivitySearchTab();
        Assert.assertTrue(!activitySearchTab.isResultSetEmpty(), "Expected to find activities in search results: ");

        openActivitySearchPanel();
        calendarSearchPanel.setSearchField("Test Act");
        calendarSearchPanel.search();
        activitySearchTab = new ActivitySearchTab();
        Assert.assertTrue(!activitySearchTab.isResultSetEmpty(), "Expected to find activities in search results: ");

        openActivitySearchPanel();
        calendarSearchPanel.selectActivityType("Analyst Day");
        calendarSearchPanel.selectActivityTopic("Cash Flow");
        calendarSearchPanel.addSymbol("GO", "Google, Inc. CL A");
        addSymbol = calendarSearchPanel.addSymbol();
        addSymbol.setSearchFilter("GO");
        Assert.assertTrue(addSymbol.isChecked("Google, Inc. CL A"), "Google, Inc. CL A expected to be checked :");
        addSymbol.uncheck("Google, Inc. CL A");
        addSymbol.setSearchFilter("AAPL");
        addSymbol.checkBySubtext("Apple, Inc.", "AAPL, NASDAQ Global Market");
        addSymbol.close();

        calendarSearchPanel.addSymbol("MSFT", "Microsoft Corporation");
        addSymbol = calendarSearchPanel.addSymbol();
        addSymbol.setSearchFilter("MSFT");
        Assert.assertTrue(addSymbol.isChecked("Microsoft Corporation"), "Microsoft Corporation expected to be checked :");
        addSymbol.setSearchFilter("AAPL");
        Assert.assertTrue(addSymbol.isChecked("Apple, Inc.", "AAPL, NASDAQ Global Market"), "Apple, Inc. expected to be checked :");
        addSymbol.uncheckBySubtext("Apple, Inc.", "AAPL, NASDAQ Global Market");
        addSymbol.close();
        calendarSearchPanel.clearSearchFilter();
        calendarSearchPanel.search();
        activitySearchTab = new ActivitySearchTab();
        activitySearchTab.openProfileOverview("Final test group activity");

        openActivitySearchPanel();
        calendarSearchPanel.reset();
        calendarSearchPanel.setSearchField("Partic");
        calendarSearchPanel.selectActivityType("Group Meeting");
        calendarSearchPanel.selectActivityTopic("M&A");
        calendarSearchPanel.selectStartDate("05/21/2012");
        calendarSearchPanel.selectEndDate("06/24/2014");
        calendarSearchPanel.search();
        activitySearchTab = new ActivitySearchTab();
        Assert.assertTrue(!activitySearchTab.isResultSetEmpty(), "Expected to find activities in search results: ");
    }

    private void openActivitySearchPanel(){
        this.calendarSearchPanel = navigation.searchActivities();
    }
}
