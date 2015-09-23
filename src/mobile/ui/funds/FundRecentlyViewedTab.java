package ipreomobile.ui.funds;

import ipreomobile.core.Logger;
import ipreomobile.templates.ui.RecentlyViewedTab;

public class FundRecentlyViewedTab extends RecentlyViewedTab {

    public FundRecentlyViewedTab(){
    }

    @Override
    protected void setupProfileList() {
        qpl = new FundProfileList();
        qpl.setItemNameXpath(itemNameXpath);
        qpl.setSubtextXpath(itemSubtextXpath);
        qpl.waitReady();
    }

    @Override
    protected void setupProfileOverview() {
        profile = new FundProfileOverview();
    }

    @Override
    public FundFullProfile openFullProfile(String name) {
        super.openFullProfile(name);
        Logger.logDebug("Executed TwoPane.openProfileSummary() method for list item '" + name + "'.");
        FundFullProfile summary = new FundFullProfile();
        summary.verifyProfileName(name);
        return summary;
    }
}
