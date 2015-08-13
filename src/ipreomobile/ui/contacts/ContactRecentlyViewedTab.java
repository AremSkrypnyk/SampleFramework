package ipreomobile.ui.contacts;

import ipreomobile.core.Logger;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.RecentlyViewedTab;

public class ContactRecentlyViewedTab extends RecentlyViewedTab {

    protected static String itemNameXpath     = new XPathBuilder().byClassName("info").byClassName("text").byTag("span").build();

    public ContactRecentlyViewedTab(){
    }

    @Override
    protected void setupProfileList() {
        qpl = new ContactProfileList();
        qpl.setItemNameXpath(itemNameXpath);
        qpl.setSubtextXpath(itemSubtextXpath);
        qpl.waitReady();
    }

    @Override
    protected void setupProfileOverview() {
        profile = new ContactProfileOverview();
    }

    @Override
    public ContactFullProfile openFullProfile(String name) {
        super.openFullProfile(name);
        Logger.logDebug("Executed TwoPane.openProfileSummary() method for list item '" + name + "'.");
        ContactFullProfile summary = new ContactFullProfile();
        summary.verifyProfileName(name);
        return summary;
    }
}
