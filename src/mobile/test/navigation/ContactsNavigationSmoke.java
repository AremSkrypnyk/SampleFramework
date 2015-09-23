package ipreomobile.test.navigation;

import ipreomobile.actions.Action;
import ipreomobile.core.Logger;
import ipreomobile.data.ActivityData;
import ipreomobile.data.ContactData;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.blocks.overlay.FixDataOverlay;
import ipreomobile.ui.blocks.overlay.MapPreviewOverlay;
import ipreomobile.ui.contacts.ActivityTab;
import ipreomobile.ui.contacts.ContactFullProfile;
import ipreomobile.ui.contacts.ContactRecentlyViewedTab;
import ipreomobile.ui.contacts.ContactSearchTab;
import ipreomobile.ui.institutions.InstitutionFullProfile;
import ipreomobile.ui.search.ContactSearchPanel;
import org.testng.annotations.Test;

public class ContactsNavigationSmoke extends BaseTest{

    ContactSearchTab searchResults;
    ContactData contactData;


    @Test
    public void searchContactByName(){
        contactData = new ContactData();
        String contactName = contactData.getName();

        navigation.searchContacts(contactName);
        searchResults = new ContactSearchTab();
        searchResults.openProfileOverview(contactName);
        searchResults.openFullProfile(contactName);
    }

    @Test
    public void verifyContactCanBeFoundByCompanyName(){
        contactData = new ContactData();
        String contactName = contactData.getName();
        String companyName = contactData.getCompanyName();

        navigation
                .searchContacts()
                .setInstitutionName(companyName)
                .search();

        searchResults = new ContactSearchTab();
        searchResults.verifyItemIsPresentInSearchResults(contactName);
    }

    @Test
    public void verifyContactRecentlyViewedTab(){
        contactData = new ContactData();
        navigation
                .searchContacts(contactData.getName())
                .openFullProfile(contactData.getName());
        navigation.back();

        navigation
                .selectRecentlyViewedTab()
                .verifyItemPresentInList(contactData.getName());
    }


}




