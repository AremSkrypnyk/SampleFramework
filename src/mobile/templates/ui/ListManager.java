package ipreomobile.templates.ui;

import ipreomobile.core.Driver;
import ipreomobile.core.ElementHelper;
import ipreomobile.core.SenchaWebElement;
import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.blocks.BaseTabControl;
import org.openqa.selenium.By;
import org.testng.Assert;

public abstract class ListManager extends QuickProfileList {

    private static final String TAB_CONTROL_XPATH           = new XPathBuilder().byClassName("toggle-button").byClassName("x-button-normal").build();
    private static final String SEARCH_BOX_XPATH            = new XPathBuilder().byClassName("search-panel").byClassName("x-input-search").build();

    private static final String LIST_ITEM_XPATH             = new XPathBuilder().byClassName("active-qpl").byClassName("x-list-item").withChildTag("span").withClassName("favorite-icon").build();
    private static final String LIST_ITEM_NAME_XPATH        = new XPathBuilder().byClassName("info").byClassName("text").build();
    private static final By BOTTOM_MASK_BY                  = By.className("lists-navigation");

//    private static final String FAVORITE_ICON_XPATH         = new XPathBuilder().byCurrentItem().byClassName("favorite-icon").build();
    private static final String FAVORITE_ICON_XPATH         = new XPathBuilder().byClassName("favorite-icon").build();
    private static final String FAVORITE_ICON_CLASS_NAME    = "pressed";

    private static final String EMPTY_DATA_LABEL_XPATH      = new XPathBuilder().byClassName("empty-data").build();
    private static final String LISTS_HELP_XPATH            = new XPathBuilder().byClassName("lists-help").build();
    private static final String LISTS_HELP_HIDDEN_CLASS_NAME = "help-hidden";

    private static final String LISTS_MANAGER_TITLE_CLASS_NAME = "manager-title";
    private static final String LISTS_CONTROLS_CONTAINER_CLASS_NAME = "lists-controls";


    private SenchaWebElement searchBox;
    private BaseTabControl tabControl;

    public ListManager(){
        tabControl = new BaseTabControl();
        tabControl.setTabControlXpath(TAB_CONTROL_XPATH);
        tabControl.setTabControlContainer(Driver.findVisible(By.className(LISTS_CONTROLS_CONTAINER_CLASS_NAME)));
        searchBox = Driver.findIfExists(By.xpath(SEARCH_BOX_XPATH));

        setItemsXpath(LIST_ITEM_XPATH);
        setItemNameXpath(LIST_ITEM_NAME_XPATH);
        setBottomMaskBy(BOTTOM_MASK_BY);

        setStateTokenXpath(FAVORITE_ICON_XPATH);
        setStateSwitcherXpath(FAVORITE_ICON_XPATH);
        setStateTokenSelectedClassName(FAVORITE_ICON_CLASS_NAME);

        //addLoadingIndicatorCheckpoint(By.xpath(LOADING_INDICATOR_MASK_XPATH), true);
        addLoadingIndicatorCheckpoint();
        waitReady();
    }

    public void select(String name) {
        SenchaWebElement item = getItem(name);
        Assert.assertNotNull(item, "Item '" + name + "' was not found in the list.");
        click(item);
    }

    public void selectTab(UITitles.ListType tabTitle) {
        tabControl.setTabControlContainer(Driver.findVisible(By.className(LISTS_CONTROLS_CONTAINER_CLASS_NAME)));
        tabControl.selectTab(tabTitle);
        waitReady();
    }

    public void clickOnTab(UITitles.ListType tabTitle) {
        BaseTabControl.performCheck = false;
        tabControl.selectTab(tabTitle);
    }

    public boolean hasFavoriteLists(){
        SenchaWebElement helpText = Driver.findIfExists(By.xpath(LISTS_HELP_XPATH));
        return (helpText.hasClass(LISTS_HELP_HIDDEN_CLASS_NAME));
    }

    public void setFilter(String filter) {
        searchBox.clear();
        searchBox.sendKeys(filter);
        waitReady();
    }

    public void clearFilter() {
        searchBox.clear();
        waitReady();
    }

    public void clickOnFilter() {
        searchBox.click();
    }

    public boolean isSearchFilterUnavailableInOfflineMode(){
        return !searchBox.isEnabled();
    }

    public void verifySearchFilterUnavailableInOfflineMode(){
        Assert.assertTrue(isSearchFilterUnavailableInOfflineMode(), "Search filter expected to be unavailable: ");
    }

    public boolean isTabControlUnableInOfflineMode(){
        return tabControl.isTabControlUnavailableInOfflineMode();
    }

    public void verifyTabControlUnableInOfflineMode(){
        Assert.assertTrue(isTabControlUnableInOfflineMode(), "Tab Control expected to be unavailable: ");
    }

    public boolean isResultsListEmpty(){
        return Driver.isElementVisible(By.xpath(EMPTY_DATA_LABEL_XPATH));
    }

    public String getTabTitle(){
        return tabControl.getSelectedTabName();
    }

    public String getListManagerTitle(){
        return Driver.findVisible(By.className(LISTS_MANAGER_TITLE_CLASS_NAME)).getText().trim();
    }

}
