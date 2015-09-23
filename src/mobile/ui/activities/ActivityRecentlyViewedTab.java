package ipreomobile.ui.activities;

import ipreomobile.templates.ui.RecentlyViewedTab;

public class ActivityRecentlyViewedTab extends RecentlyViewedTab {

    public ActivityRecentlyViewedTab(){
        super();
        setupProfileList();
        setupProfileOverview();
    }

    @Override
    protected void setupProfileList() {
        qpl = new ActivityProfileList();
        qpl.setItemNameXpath(itemNameXpath);
        qpl.setSubtextXpath(itemSubtextXpath);
        qpl.waitReady();
    }

    @Override
    protected void setupProfileOverview() {
        profile = new ActivityDetailsOverview();
    }

}
