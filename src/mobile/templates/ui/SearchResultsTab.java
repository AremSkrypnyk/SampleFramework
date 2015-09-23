package ipreomobile.templates.ui;

import ipreomobile.core.Driver;
import ipreomobile.core.Logger;
import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.UITitles;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

public abstract class SearchResultsTab extends TwoPane {
    private static String itemNameXpath     = new XPathBuilder().byClassName("info").byClassName("text").build();
    private static String itemSubtextXpath  = new XPathBuilder().byClassName("info").byClassName("subtext").build();
    private static String titleXpath        = new XPathBuilder().byClassName("results-toolbar-title").build();

    private static final String SEARCH_IN_LINK_CLASS = "execute-item";
    private static final String EMPTY_RESULT_LIST_CLASS = "no-items";

    public SearchResultsTab(){
        super();
        if (!isResultSetEmpty()) {
            setupProfileList();
            setupProfileOverview();
        }
    }

    protected void setupProfileList() {
        //qpl.setItemsXpath(itemsXpath);
        qpl.setItemNameXpath(itemNameXpath);
        qpl.setListTitleXpath(titleXpath);
        qpl.setSubtextXpath(itemSubtextXpath);
        qpl.waitReady();
    }

//    public static String getItemsXpath() {
//        return itemsXpath;
//    }
//
//    public static void setItemsXpath(String itemsXpath) {
//        SearchResultsTab.itemsXpath = itemsXpath;
//    }

    public static String getItemNameXpath() {
        return itemNameXpath;
    }

    public static void setItemNameXpath(String itemNameXpath) {
        SearchResultsTab.itemNameXpath = itemNameXpath;
    }

    public static String getTitleXpath() {
        return titleXpath;
    }

    public static void setTitleXpath(String titleXpath) {
        SearchResultsTab.titleXpath = titleXpath;
    }

    public SearchResultsTab openProfileOverview(String name) {
        if (isResultSetEmpty()) {
            Logger.logError("Unable to open profile '"+name+"', because the results set is empty.");
        } else {
            super.openProfileOverview(name);
        }
        return this;
    }

    public SearchResultsTab openProfileOverview(String name, String subtext) {
        if (isResultSetEmpty()) {
            Logger.logError("Unable to open profile '"+name+"' with '" + subtext + "' subtext, because the results set is empty.");
        } else {
            super.openProfileOverview(name, subtext);
        }
        return this;
    }

    public void searchIn(UITitles.SearchLocation searchLocation) {
        String location = UITitles.get(searchLocation);
       SenchaWebElement searchLink = Driver.findIfExists(By.xpath(new XPathBuilder().byClassName(SEARCH_IN_LINK_CLASS).withText(location).build()));
        if (searchLink == null) {
            throw new Error("Search in '"+location+"' is impossible: corresponding link was not found.");
        }
        searchLink.click();
        addLoadingIndicatorCheckpoint();
        waitReady();
    }

    public int getSearchResultsNumber(){
        this.waitReady();
        if (isResultSetEmpty()) {
            return 0;
        } else {
            qpl.waitReady();
            String results = qpl.getListTitle();
            return Integer.parseInt(results.split("\\s+")[0]);
        }
    }

    public boolean isResultSetEmpty(){
        return Driver.isElementVisible(By.className(EMPTY_RESULT_LIST_CLASS));
    }

    public SearchResultsTab verifyResultSetEmpty(){
        return verifyResultSetEmpty("No results should be shown for this search: ");
    }

    public SearchResultsTab verifyResultSetEmpty(String errorMessage){
        Assert.assertTrue(isResultSetEmpty(), errorMessage);
        return this;
    }

    public SearchResultsTab verifyResultSetNotEmpty(){
        return verifyResultSetNotEmpty("Some results should be shown for this search: ");
    }

    public SearchResultsTab verifyResultSetNotEmpty(String message){
        Assert.assertFalse(isResultSetEmpty(), message);
        return this;
    }

    public void verifyItemIsPresentInSearchResults(String name) {
        Assert.assertNotNull(qpl.getItem(name), "'"+name+"' was not found in Search Results, but expected.");
    }

    public SearchResultsTab verifyItemIsAbsentInSearchResults(String name) {
        Assert.assertNull(qpl.getItem(name), "'"+name+"' was found in Search Results, but not expected.");
        return this;
    }

}
