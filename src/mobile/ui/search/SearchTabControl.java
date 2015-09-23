package ipreomobile.ui.search;

import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.blocks.BaseTabControl;


public class SearchTabControl extends BaseTabControl {
    public SearchTabControl(UITitles.SearchType params) {
        String xpath = "";
        switch (params) {
            case SEARCH_IN:
                xpath = new XPathBuilder().byTag("div").withClassName("criteria-panel").byTag("div").withClassName("x-segmentedbutton-inner").withChildText("Company CRM").byTag("div").withClassName("x-button-normal").build();
                break;
            case SIDE:
                xpath = new XPathBuilder().byTag("div").withClassName("criteria-panel").byTag("div").withClassName("x-segmentedbutton-inner").withChildText("Buy Side").byTag("div").withClassName("x-button-normal").build();
                break;
        }
        setTabControlXpath(xpath);
        //setSelectedTabClass("x-button-pressed");
    }
}
