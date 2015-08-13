package ipreomobile.templates.ui;

import ipreomobile.core.*;
import ipreomobile.ui.ScreenCard;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.activities.ActivityOverlay;
import ipreomobile.ui.blocks.Carousel;
import org.openqa.selenium.By;
import org.testng.Assert;

import java.util.List;

public abstract class ListsTab extends TwoPane implements MultiSelectListController {
    private static String carouselXpath = new XPathBuilder().byTag("div").withClassName("listsIndex").build();
    private Carousel carousel = new Carousel();

    private static final String GO_TO_LIST_MANAGER_BUTTON_XPATH = new XPathBuilder().byClassName("active-qpl").byClassName("content-toolbar").byClassName("x-button", "list").build();
    private static final String GO_TO_LISTS_BUTTON_XPATH = new XPathBuilder().byClassName("active-qpl").byClassName("x-toolbar").byClassName("x-button", "arrow-right").build();
    private static final String BUTTONS_LOCATION_XPATH = new XPathBuilder().byClassName("active-qpl").byClassName("x-button").build();

    private static final String LIST_ITEM_XPATH = new XPathBuilder().byClassName("active-qpl").byClassName("x-list-item").withChildTag("div").withClassName("list-simple-item").build();
    private static final String LIST_ITEM_NAME_XPATH = new XPathBuilder().byClassName("info").byClassName("text").build();
    private static final String ITEM_SUBTEXT_XPATH = new XPathBuilder().byClassName("info").byClassName("subtext").build();
    private static final String LIST_TITLE_XPATH = new XPathBuilder().byClassName("active-qpl").byClassName("content-toolbar").byClassName("x-title").build();

    private static final String CHECKMARK_XPATH = new XPathBuilder().byCurrentItem().build();
    //    private static final String CHECKMARK_ICON_XPATH = new XPathBuilder().byCurrentItem().byClassName("checkmark").build();
    private static final String CHECKMARK_ICON_XPATH = new XPathBuilder().byClassName("checkmark").build();
    private static final String CHECKMARK_CLASS_NAME = "selected";
    private static final String HELP_LIST_CLASS_NAME = "lists-help";

    private static final String ADD_ACTIVITY_XPATH = new XPathBuilder().byClassName("x-toolbar-inner").byClassName("add-activity").build();
    private static final By FUM_MESSAGE_LOCATOR    = By.className("fum-tooltip");

    private static final String LIST_MANAGER_ICON_XPATH = new XPathBuilder().byClassName("lists-navigation").byClassName("x-button").withChildTag("span").withClassName("list").build();
    private static final String LIST_ICON_XPATH = new XPathBuilder().byClassName("lists-navigation").byClassName("x-button").withChildTag("span").withClassName("circle").build();
    private static final String FAVORITE_ICON_XPATH = new XPathBuilder().byClassName("lists-navigation").byClassName("x-button").withChildTag("span").withClassName("favorite").build();
    private static final String SELECTED_ICON_CLASSNAME = "x-button-pressed";
    private static final String UNAVAILABLE_ITEM_CLASSNAME = "unavailable";

    public ListsTab() {
        super();
        if (isListManagerActive()) {
            setupListManager();
        } else {
            setupProfileList();
            setupProfileOverview();
        }
        addLoadingIndicatorCheckpoint();
        carousel.setContainerXpath(carouselXpath);
    }

    protected void setupProfileList() {
        qpl.setItemsXpath(LIST_ITEM_XPATH);
        qpl.setItemNameXpath(LIST_ITEM_NAME_XPATH);
        qpl.setListTitleXpath(LIST_TITLE_XPATH);
        qpl.setSubtextXpath(ITEM_SUBTEXT_XPATH);

        qpl.setStateSwitcherXpath(CHECKMARK_ICON_XPATH);
        qpl.setStateTokenXpath(CHECKMARK_XPATH);
        qpl.setStateTokenSelectedClassName(CHECKMARK_CLASS_NAME);
        qpl.addLoadingIndicatorCheckpoint();
        qpl.waitReady();
    }

    protected abstract void setupListManager();

    public String getActiveListName() {
        return (isListManagerActive()) ? getProfileNameSelectedInList() : (qpl.getListTitle());
    }

    public void verifyActiveListName(String name) {
        Assert.assertEquals(getActiveListName(), name, "Active list name mismatch: ");
    }

    @Override
    public String getListTitle() {
        return (isListManagerActive()) ? getQplAsListManager().getTabTitle() : (qpl.getListTitle());
    }

    @Override
    public ListsTab verifyListTitle(String name) {
        Assert.assertEquals(getActiveListName(), name, "List title mismatch: ");
        return this;
    }

    public ListsTab goToListManager() {
        if (!isListManagerActive()) {
            List<SenchaWebElement> buttons = Driver.findAll(By.xpath(GO_TO_LIST_MANAGER_BUTTON_XPATH));
            buttons.get(0).click();
            setupListManager();
        }
        return this;
    }

    public ListsTab goToLists() {
        if (isListManagerActive()) {
            Driver.get().findElement(By.xpath(GO_TO_LISTS_BUTTON_XPATH)).click();
            setupProfileList();
            setupProfileOverview();
        }
        return this;
    }

    public ListsTab selectTab(UITitles.ListType tabTitle) {
        goToListManager();
        getQplAsListManager().selectTab(tabTitle);
        getQplAsListManager().waitReady();
        return this;
    }

    public ListsTab clickOnTab(UITitles.ListType tabTitle) {
        goToListManager();
        getQplAsListManager().clickOnTab(tabTitle);
        return this;
    }

    public ListsTab verifySelectedTab(UITitles.ListType tabTitle) {
        goToListManager();
        String selectedTab = getQplAsListManager().getTabTitle();
        Assert.assertTrue(selectedTab.equals(UITitles.get(tabTitle)), "Wrong Tab was selected");
        return this;
    }

    public ListsTab goToList(String name) {
        if (!isListManagerActive()) {
            goToListManager();
        }
        getQplAsListManager().select(name);
        setupProfileList();
        setupProfileOverview();
        return this;
    }

    public ListsTab goToNextList() {
        if (!isListManagerActive()) {
            carousel.goForward();
            setupProfileList();
            setupProfileOverview();
        }
        return this;
    }

    public ListsTab goToPreviousList() {
        if (carousel.getActivePageIndex() > 1) {
            carousel.goBackward();
            setupProfileList();
            setupProfileOverview();
        }
        return this;
    }

    public ListsTab goToMyLists() {
        selectTab(UITitles.ListType.MY_LISTS);
        return this;
    }

    public ListsTab goToOtherLists() {
        selectTab(UITitles.ListType.OTHER_LISTS);
        return this;
    }

    public ListsTab goToListFromMyLists(String name) {
        return goToMyLists().goToList(name);
    }

    public ListsTab goToListFromOtherLists(String name) {
        return goToOtherLists().goToList(name);
    }

    public ListsTab clickActivityButton() {
        Driver.findVisible(By.xpath(ADD_ACTIVITY_XPATH)).click();
        return this;
    }

    public ActivityOverlay addActivity() {
        Driver.findVisible(By.xpath(ADD_ACTIVITY_XPATH)).click();
        return null;
    }

    @Override
    public void check(String... names) {
        qpl.check(names);
    }

    @Override
    public void uncheck(String... names) {
        qpl.uncheck(names);
    }

    @Override
    public boolean areChecked(String... names) {
        return qpl.areChecked(names);
    }


    public ListsTab verifyChecked(String... names) {
        qpl.verifyChecked(names);
        return this;
    }

    public void verifyChecked(List<String> names) {
        qpl.verifyChecked(StringHelper.listToArray(names));
    }

    public void verifyUnchecked(String... names) {
        qpl.verifyUnchecked(names);
    }

    @Override
    public boolean isChecked(String name) {
        return qpl.isChecked(name);
    }

    protected boolean isListManagerActive() {
        ScreenCard buttonsHome = new ScreenCard();
        buttonsHome.addCheckpointElement(By.xpath(BUTTONS_LOCATION_XPATH));
        buttonsHome.waitReady();
        return Driver.isElementVisible(By.xpath(GO_TO_LISTS_BUTTON_XPATH));
    }

    private ListManager getQplAsListManager() {
        return (ListManager) qpl;
    }

    public boolean isSearchFilterUnavailableInOfflineMode() {
        return ((ListManager) qpl).isSearchFilterUnavailableInOfflineMode();
    }

    public ListsTab verifySearchFilterUnavailableInOfflineMode() {
        ((ListManager) qpl).verifySearchFilterUnavailableInOfflineMode();
        return this;
    }

    public boolean isTabControlUnavailableInOfflineMode(){
        return ((ListManager) qpl).isTabControlUnableInOfflineMode();
    }

    public ListsTab verifyTabControlUnavailableInOfflineMode(){
        ((ListManager) qpl).verifyTabControlUnableInOfflineMode();
        return this;
    }

    public ListsTab setFilter(String name){
        ((ListManager) qpl).setFilter(name);
        addLoadingIndicatorCheckpoint();
        waitReady();
        return this;
    }

    public ListsTab clickOnFilter(){
        ((ListManager) qpl).clickOnFilter();
        return this;
    }

    public ListsTab clearFilter(){
        ((ListManager) qpl).clearFilter();
        return this;
    }

    public boolean isListUnavailableInOfflineMode(String listName){
        return qpl.isItemUnavailableInOfflineMode(listName);
    }

    public Boolean isResultsListEmpty(){
        return ((ListManager) qpl).isResultsListEmpty();
    }

    public ListsTab verifyResultsListEmpty(){
        Assert.assertTrue(isResultsListEmpty(), "Result set expected to be empty");
        return this;
    }

    public ListsTab verifyListManagerTitle() {
        Assert.assertTrue(getQplAsListManager().getListManagerTitle().equals("List Manager"), "List Manager title mismatch: ");
        return this;
    }

    public ListsTab verifyPickingFavouritesInstructions() {
        SenchaWebElement listsHelp = Driver.findVisible(By.className(HELP_LIST_CLASS_NAME));
        Assert.assertFalse(listsHelp.getText().isEmpty(), "Lists Help doesn't contain instructions: ");
        return this;
    }

    public ListsTab verifyTooltip(String messageText) {
        SenchaWebElement fumMessage = Driver.findVisible(FUM_MESSAGE_LOCATOR);
        Assert.assertNotNull(fumMessage, "Tooltip was not found on the Lists Tab");
        Verify.verifyContainsText(fumMessage.getText(), messageText, "Invalid tooltip message found:");
        waitTooltipClosed();
        return this;
    }

    protected void waitTooltipClosed() {
        addOneTimeCheckpoint(FUM_MESSAGE_LOCATOR).addVisibilityCondition(false);
        waitReady();
    }

    public ListsTab clickGoToListButton(){
        Driver.findVisible(By.xpath(GO_TO_LISTS_BUTTON_XPATH)).click();
        return this;
    }

    public ListsTab verifyListManagerIconActive(){
        SenchaWebElement listsManagerIcon = Driver.findVisible(By.xpath(LIST_MANAGER_ICON_XPATH));
        Assert.assertTrue(listsManagerIcon.hasClass(SELECTED_ICON_CLASSNAME), "List Manager Icon wasn't marked as Active");
        return this;
    }

    public ListsTab verifyListIconActive(){
        SenchaWebElement circleIcon = Driver.findVisible(By.xpath(LIST_ICON_XPATH));
        Assert.assertTrue(circleIcon.hasClass(SELECTED_ICON_CLASSNAME), "List Icon (Circle) wasn't marked as Active");
        return this;
    }

    public ListsTab verifyFavoriteListIconActive(){
        SenchaWebElement favoriteIcon = Driver.findVisible(By.xpath(FAVORITE_ICON_XPATH));
        Assert.assertTrue(favoriteIcon.hasClass(SELECTED_ICON_CLASSNAME), "Favorite List Icon wasn't marked as Active");
        return this;
    }

    public String getNextListName(String listName){
        return qpl.getNextItemName(listName);
    }

    public String getPreviousListName(String listName){
        return qpl.getPreviousItemName(listName);
    }

    public String getNextProfileName(String profileName){
        return qpl.getNextItemName(profileName);
    }

    public ListsTab click(String name){
        qpl.click(name);
        return this;
    }

    public ListsTab verifyItemFirstInList(String name){
        qpl.verifyItemFirstInList(name);
        return this;
    }
}