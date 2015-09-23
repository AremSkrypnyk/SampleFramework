package ipreomobile.templates.ui;

import ipreomobile.core.XPathBuilder;

public abstract class RecentlyViewedTab extends TwoPane {
    protected static String itemNameXpath     = new XPathBuilder().byClassName("info").byClassName("text").build();
    protected static String itemSubtextXpath  = new XPathBuilder().byClassName("info").byClassName("subtext").build();

    public RecentlyViewedTab(){
        super();
        if (!isActiveTabEmpty()){
            setupProfileList();
            if (isProfileOverviewAvailable())
                setupProfileOverview();
        }
    }

    //TODO verify number of recently viewed items
}
