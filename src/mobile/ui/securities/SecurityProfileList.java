package ipreomobile.ui.securities;

import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.QuickProfileList;

public class SecurityProfileList extends QuickProfileList {

    //private static final String LIST_ITEM_XPATH         = new XPathBuilder().byClassName("x-list-item").build();
    private static final String LIST_ITEM_NAME_XPATH    = new XPathBuilder().byClassName("list-simple-item").byClassName("text").build();
    private static final String LIST_TITLE_XPATH        = new XPathBuilder().byClassName("results-toolbar-title").build();

    public SecurityProfileList(){
        //setItemsXpath(LIST_ITEM_XPATH);
        setItemNameXpath(LIST_ITEM_NAME_XPATH);
        setListTitleXpath(LIST_TITLE_XPATH);
        setNonStrictNameEqualityFlag(true);
    }
}
