package ipreomobile.ui.contacts;

import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.SearchResultsTab;

public class ContactSearchTab extends SearchResultsTab {

    private static String itemNameXpath     = new XPathBuilder().byClassName("info").byClassName("text").byTag("span").build();
    private static String itemSubtextXpath  = new XPathBuilder().byClassName("info").byClassName("subtext").byTag("span").build();

    public String getInstitutionName(String name){
        return getProfileList().getInstitutionName(name);
    }

    private ContactProfileList getProfileList(){
        return (ContactProfileList)qpl;
    }

    @Override
    protected void setupProfileList() {
        qpl = new ContactProfileList();
        super.setupProfileList();
        qpl.setItemNameXpath(itemNameXpath);
        qpl.setSubtextXpath(itemSubtextXpath);
    }

    @Override
    protected void setupProfileOverview() {
        profile = new ContactProfileOverview();
    }

    @Override
    public ContactFullProfile openFullProfile(String name){
        super.openFullProfile(name);
        return new ContactFullProfile();
    }

    @Override
    public ContactProfileOverview getActiveProfileOverview(){
        return (ContactProfileOverview)profile;
    }
}
