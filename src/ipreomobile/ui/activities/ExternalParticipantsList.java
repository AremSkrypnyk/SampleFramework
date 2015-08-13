package ipreomobile.ui.activities;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.MultiSelectListImpl;
import ipreomobile.ui.ScreenCard;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.blocks.overlay.ListOverlaySearchMultiSelect;
import org.openqa.selenium.By;
import org.testng.Assert;
import ipreomobile.core.SenchaWebElement;

public class ExternalParticipantsList extends MultiSelectListImpl {

    private static final String LIST_ITEM_XPATH           = new XPathBuilder().byClassName("x-list-item").build();
    private static final String ITEM_NAME_XPATH           = new XPathBuilder().byClassName("list-simple-item").byClassName("info").byClassNameEquals("text").build();
    private static final String ORGANIZER_SWITCHER_XPATH  = new XPathBuilder().byCurrentItem().byClassName("organizer").build();
    private static final String ACCEPT_SWITCHER_XPATH     = new XPathBuilder().byCurrentItem().byClassName("accept").build();
    private static final String DECLINE_SWITCHER_XPATH    = new XPathBuilder().byCurrentItem().byClassName("decline").build();
    private static final String STATE_SELECTED_CLASS_NAME = "selected";

    private static final By HEADER_LOCATOR = By.xpath(new XPathBuilder()
            .byClassName("x-title").withChildTextContains("Participants").build());

    private static final By ADD_EXTERNAL_PARTICIPANT_BUTTON_SELECTOR = By.xpath(new XPathBuilder()
            .byTag("div").withClassName("external-participants").byTag("div").withClassName("add-participant").build());

    public ExternalParticipantsList() {
        addCheckpointElement(HEADER_LOCATOR).mustBeVisible();
        setItemNameXpath(ITEM_NAME_XPATH);
        setStateTokenXpath(LIST_ITEM_XPATH);
        waitReady();
    }

    public void markItemAsOrganizer(String name) {
        setStateTokenXpath(ORGANIZER_SWITCHER_XPATH);
        setStateSwitcherXpath(ORGANIZER_SWITCHER_XPATH);
        setStateTokenSelectedClassName(STATE_SELECTED_CLASS_NAME);
        check(name);
    }

    public void unmarkItemAsOrganizer(String name) {
        setStateTokenXpath(ORGANIZER_SWITCHER_XPATH);
        setStateSwitcherXpath(ORGANIZER_SWITCHER_XPATH);
        setStateTokenSelectedClassName(STATE_SELECTED_CLASS_NAME);
        uncheck(name);
    }

    public void markAcceptParticipationForItem(String name) {
        setStateTokenXpath(ACCEPT_SWITCHER_XPATH);
        setStateSwitcherXpath(ACCEPT_SWITCHER_XPATH);
        setStateTokenSelectedClassName(STATE_SELECTED_CLASS_NAME);
        check(name);
    }

    public void unmarkAcceptParticipationForItem(String name) {
        setStateTokenXpath(ACCEPT_SWITCHER_XPATH);
        setStateSwitcherXpath(ACCEPT_SWITCHER_XPATH);
        setStateTokenSelectedClassName(STATE_SELECTED_CLASS_NAME);
        uncheck(name);
    }

    public void markDeclineParticipationForItem(String name) {
        setStateTokenXpath(DECLINE_SWITCHER_XPATH);
        setStateSwitcherXpath(DECLINE_SWITCHER_XPATH);
        setStateTokenSelectedClassName(STATE_SELECTED_CLASS_NAME);
        check(name);
    }

    public void unmarkDeclineParticipationForItem(String name) {
        setStateTokenXpath(DECLINE_SWITCHER_XPATH);
        setStateSwitcherXpath(DECLINE_SWITCHER_XPATH);
        setStateTokenSelectedClassName(STATE_SELECTED_CLASS_NAME);
        uncheck(name);
    }

    public AddExternalParticipants clickOnAddExternalParticipantsButton() {
       SenchaWebElement addExternalParticipantsButton = Driver.findOne(ADD_EXTERNAL_PARTICIPANT_BUTTON_SELECTOR);
        addExternalParticipantsButton.click();
        return new AddExternalParticipants();
    }

    public void verifyParticipantPresent(String participant) {
        if (getItem(participant) == null && !isItemPresentBySubtext(participant)) {
            throw new Error("Participant with name [" + participant + "] was not found among either contact or institution names.");
        }
    }


    public class AddExternalParticipants extends ScreenCard {
        //TODO:implement support of back button
        private final By SEARCH_CONTACT_FIELD_SELECTOR     = By.xpath(new XPathBuilder().byTag("span").withClassName("x-button-label").withText("Search Contact").build());
        private final By CONTACT_LIST_FIELD_SELECTOR       = By.xpath(new XPathBuilder().byTag("span").withClassName("x-button-label").withText("Contact Lists").build());
        private final By SEARCH_INSTITUTION_FIELD_SELECTOR = By.xpath(new XPathBuilder().byTag("span").withClassName("x-button-label").withText("Search Institution").build());
        private final By INSTITUTION_LIST_FIELD_SELECTOR   = By.xpath(new XPathBuilder().byTag("span").withClassName("x-button-label").withText("Institution Lists").build());


        public void addInstitutionsFromSearch(String searchFilter, String... names) {
           SenchaWebElement searchInstitutionButton = Driver.findOne(SEARCH_INSTITUTION_FIELD_SELECTOR);
            searchInstitutionButton.click();
            addItemsFromSearch(UITitles.OverlayType.SEARCH_INSTITUTION, searchFilter, names);
        }

        public void addContactsFromSearch(String searchFilter, String... names) {
           SenchaWebElement searchContactButton = Driver.findOne(SEARCH_CONTACT_FIELD_SELECTOR);
            searchContactButton.click();
            addItemsFromSearch(UITitles.OverlayType.SEARCH_CONTACT, searchFilter, names);
        }

        private void addItemsFromSearch(UITitles.OverlayType overlayType, String searchFilter, String... names){
            ListOverlaySearchMultiSelect addContacts = new ListOverlaySearchMultiSelect(overlayType);
            addContacts.setSearchFilter(searchFilter);
            addContacts.check(names);
            addContacts.close();
        }


        //TODO:need refactoring
        public void addInstitutionsFromLists(String institutionList, String... institutionNames) {
           SenchaWebElement institutionListsButton = Driver.findOne(INSTITUTION_LIST_FIELD_SELECTOR);
            institutionListsButton.click();
            addItemsFromList(UITitles.OverlayType.INSTITUTION_LISTS, institutionList, institutionNames);
        }

        public void addContactsFromLists(String contactList, String... contactNames) {
           SenchaWebElement contactListsButton = Driver.findOne(CONTACT_LIST_FIELD_SELECTOR);
            contactListsButton.click();
            addItemsFromList(UITitles.OverlayType.CONTACT_LISTS, contactList, contactNames);
        }

        private void addItemsFromList(UITitles.OverlayType overlayType, String list, String... names){
            ListOverlayWithTabsOnActivity lists = new ListOverlayWithTabsOnActivity(overlayType);
            ListOverlaySearchMultiSelect addItems = lists.selectList(list);
            addItems.check(names);
            addItems.close();
        }

        public ListOverlayWithTabsOnActivity addInstitutionsFromLists() {
           SenchaWebElement institutionListsButton = Driver.findOne(INSTITUTION_LIST_FIELD_SELECTOR);
            institutionListsButton.click();
            return new ListOverlayWithTabsOnActivity(UITitles.OverlayType.INSTITUTION_LISTS);
        }

        public ListOverlayWithTabsOnActivity addContactsFromLists() {
           SenchaWebElement contactListsButton = Driver.findOne(CONTACT_LIST_FIELD_SELECTOR);
            contactListsButton.click();
            return new ListOverlayWithTabsOnActivity(UITitles.OverlayType.CONTACT_LISTS);
        }
    }
}


