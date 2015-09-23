package ipreomobile.ui.profiles.fullProfileTabs;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.ProfileTab;
import ipreomobile.templates.ui.ProfileTabList;
import ipreomobile.ui.contacts.ContactFullProfile;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

public class InstitutionContactsProfileTab extends ProfileTab {

    private ProfileTabList contactList;
    private static final String LIST_ITEM_XPATH = new XPathBuilder().byClassName("insititution-contact").build();
    private static final String ITEM_NAME_XPATH = new XPathBuilder().byClassName("value", "title").build();
    private static final String JOB_TITLE_XPATH = new XPathBuilder().byClassName("value", "role").build();
    private static final String EMAIL_XPATH = new XPathBuilder().byClassName("link", "email").build();

    private static final By CONTAINER_LOCATOR    = By.xpath(new XPathBuilder().byClassName("contacts-list").byXpathPart("[not(contains(@style, 'display: none '))]").build());
    private static final By SEARCH_FIELD_LOCATOR = By.className("x-input-search");

    protected void initializeContactList(){
        contactList = new ProfileTabList();
        contactList.setItemsXpath(LIST_ITEM_XPATH);
        contactList.setItemNameXpath(ITEM_NAME_XPATH);
        contactList.setListContainer(getActiveListContainer());
    }

    public InstitutionContactsProfileTab(){
        initializeContactList();
        setSearchFieldSelector(SEARCH_FIELD_LOCATOR);
    }
	public boolean isContactUnavailableInOfflineMode(String contactName){
        return contactList.isItemUnavailableInOfflineMode(contactName);
    }

    public InstitutionContactsProfileTab verifyContactUnavailableInOfflineMode(String contactName){
        Assert.assertTrue(isContactUnavailableInOfflineMode(contactName), "Contact '" + contactName +"' is available while expected to be unavailable in offline mode.");
        return this;
    }
    public ContactFullProfile selectContactAvailableOffline(){
        contactList.selectItemInOfflineMode(true);
        return new ContactFullProfile();
    }

    public InstitutionContactsProfileTab selectContactUnavailableOffline(){
        contactList.selectItemInOfflineMode(false);
        return this;
    }

    public InstitutionContactsProfileTab selectCrmContactsTab(){
        return selectContactsTab("CRM Contacts");
    }

    public InstitutionContactsProfileTab selectSearchContactsTab(){
        return selectContactsTab("Search Contacts");
    }

    public InstitutionContactsProfileTab filterContacts(String filter) {
        setSearchFilter(filter);
        waitReady();
        return this;
    }

    public void verifyContactPresent(String contactName) {
        filterContacts(contactName);
        contactList.verifyItemPresence(contactName);
    }

    public void verifyContactAbsent(String contactName) {
        filterContacts(contactName);
        Assert.assertTrue(isProfileTabEmpty(), "Contact '" + contactName + "' was found in the " + getSelectedTab() + " security list, while should not. Expected result set to be empty after filtering.");
    }

    public InstitutionContactsProfileTab verifyListEmpty(){
        Assert.assertTrue(isProfileTabEmpty(), "Contacts Card had to be empty: ");
        Driver.verifyExactTextPresentAndVisible("No matching Contacts could be found.");
        return this;
    }

    public ContactFullProfile selectContact(String contactName){
        contactList.setListContainer(getActiveListContainer());
        contactList.select(contactName);
        return new ContactFullProfile();
    }

    public ContactFullProfile goToCrmContactAndVerifyName(String contactName){
        selectCrmContactsTab();
        return selectContact(contactName);
    }

    public ContactFullProfile selectSearchContact(String contactName){
        selectSearchContactsTab();
        return selectContact(contactName);
    }


    private SenchaWebElement getActiveListContainer(){
        return Driver.findVisible(CONTAINER_LOCATOR);
    }

    private InstitutionContactsProfileTab selectContactsTab(String tabName) {
        selectTab(tabName);
        initializeContactList();
        return this;
    }


}
