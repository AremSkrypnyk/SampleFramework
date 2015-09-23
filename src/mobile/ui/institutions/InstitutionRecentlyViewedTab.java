package ipreomobile.ui.institutions;

import ipreomobile.core.Logger;
import ipreomobile.templates.ui.RecentlyViewedTab;

public class InstitutionRecentlyViewedTab extends RecentlyViewedTab {

    public InstitutionRecentlyViewedTab(){
    }

    @Override
    protected void setupProfileList() {
        qpl = new InstitutionProfileList();
        qpl.setItemNameXpath(itemNameXpath);
        qpl.setSubtextXpath(itemSubtextXpath);
        qpl.waitReady();
    }

    @Override
    protected void setupProfileOverview() {
        profile = new InstitutionProfileOverview();
    }

    @Override
    public InstitutionFullProfile openFullProfile(String name) {
        super.openFullProfile(name);
        Logger.logDebug("Executed TwoPane.openProfileSummary() method for list item '" + name + "'.");
        InstitutionFullProfile summary = new InstitutionFullProfile();
        summary.verifyProfileName(name);
        return summary;
    }

}
