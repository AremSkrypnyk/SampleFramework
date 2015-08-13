package ipreomobile.ui;

import ipreomobile.core.*;
import ipreomobile.templates.ui.BaseSearchPanel;
import ipreomobile.templates.ui.ListsTab;
import ipreomobile.templates.ui.RecentlyViewedTab;
import ipreomobile.templates.ui.SearchResultsTab;
import ipreomobile.ui.activities.ActivitySearchTab;
import ipreomobile.ui.blocks.BaseTabControl;
import ipreomobile.ui.blocks.overlay.MapPreviewOverlay;
import ipreomobile.ui.contacts.*;
import ipreomobile.ui.funds.FundFullProfile;
import ipreomobile.ui.funds.FundRecentlyViewedTab;
import ipreomobile.ui.funds.FundSearchTab;
import ipreomobile.ui.hamburgerItems.Hamburger;
import ipreomobile.ui.institutions.*;
import ipreomobile.ui.search.*;
import ipreomobile.ui.securities.EqSecurityFullProfile;
import ipreomobile.ui.securities.FiSecurityFullProfile;
import ipreomobile.ui.securities.SecurityRecentlyViewedTab;
import ipreomobile.ui.securities.SecuritySearchTab;
import ipreomobile.ui.settings.OfflineModeTab;
import org.openqa.selenium.By;

import org.testng.Assert;

import java.util.List;

public class GlobalNavigation extends ScreenCard {

	private static final String TAB_XPATH = new XPathBuilder().byClassName("pageheader-h2")
            .byClassName("x-button-normal").build();
    private static final String PANELS_XPATH = new XPathBuilder().byClassName("frux-main-navigation")
            .byClassName("x-vertical")
            .byClassName("x-button-normal")
            .build();
    private static final String TITLE_XPATH = new XPathBuilder().byClassName("navigation-layout")
            .byClassName("x-title").build();
    private static final String SELECTED_PANEL_CONDITION = "x-button-pressed";
    private static final String SELECTED_TAB_CONDITION = "selected-label";

    private static final String MAP_PREVIEW_BUTTON_XPATH = new XPathBuilder().byClassName("location").build();

    private static final String INSTITUTIONS_CLASS = "institutions";
    private static final String CONTACTS_CLASS = "contacts";
    private static final String CALENDAR_CLASS = "calendar";
    private static final String SECURITIES_CLASS = "securities";
    private static final String FUNDS_CLASS = "funds";
    private static final String MENU_CLASS = "menu";
    private static final String BACK_BUTTON_CLASS = "x-button-b1back";
    private static final String SEARCH_BOX_CLASS = "search-criteria-inner";

    private static final By FUM_MESSAGE_LOCATOR    = By.className("fum-tooltip");

    private BaseTabControl tabControl = new BaseTabControl();
    private BaseTabControl panelControl = new BaseTabControl();

    public GlobalNavigation(){
        String[] checkpointButtons = {INSTITUTIONS_CLASS, CONTACTS_CLASS, CALENDAR_CLASS, SECURITIES_CLASS, FUNDS_CLASS, SEARCH_BOX_CLASS, MENU_CLASS};
        for (int i=0; i< checkpointButtons.length; i++) {
            addCheckpointElement(By.className(checkpointButtons[i])).mustBeVisible();
        }
        waitReady();
        waitApplicationReady();

        tabControl.setTabControlXpath(TAB_XPATH);
        tabControl.setSelectedTabClass(SELECTED_TAB_CONDITION);

        panelControl.setTabControlXpath(PANELS_XPATH);
        panelControl.setSelectedTabClass(SELECTED_PANEL_CONDITION);
    }

    public static By getTitleSelector(){
        return By.xpath(TITLE_XPATH);
    }
    public String getPageTitle(){
        return Driver.findVisible(By.xpath(TITLE_XPATH)).getText();
    }

    public UITitles.Panels getSelectedPanel(){
        List<SenchaWebElement> panelButtons = Driver.findAll(By.xpath(PANELS_XPATH));
        UITitles.Panels selectedPanel = null;
        for(int i=0; i<panelButtons.size(); i++){
            if (panelButtons.get(i).getAttribute("class").contains(SELECTED_PANEL_CONDITION)){
                selectedPanel = UITitles.getPanels().get(i);
            }
        }
        return selectedPanel;
    }

    public String getSelectedTabName(){
        return panelControl.getSelectedTabName();
    }

    public Hamburger openHamburger(){
        getButton(MENU_CLASS).click();
        return new Hamburger();
    }

    public GlobalNavigation verifyPageTitle(String expectedTitle) {
        String actualTitle = getPageTitle().toUpperCase();
        Assert.assertEquals(actualTitle, expectedTitle.toUpperCase(),
                "Actual page title is different from expected one: ");
        return this;
    }

    public GlobalNavigation verifyPageTitle(String expectedTitle, String errorMessage) {
        String actualTitle = getPageTitle().toUpperCase();
        Assert.assertEquals(actualTitle, expectedTitle.toUpperCase(),
                errorMessage);
        return this;
    }

    public GlobalNavigation verifyActiveTabTitle(String expectedTitle) {
        tabControl.verifyActiveTab(expectedTitle.toUpperCase(), "Invalid main navigation tab was found active: ");

        return this;
    }

    public GlobalNavigation verifyActivePanel(String panelName) {
        panelControl.verifyActiveTab(panelName.toUpperCase(), "Invalid main navigation panel was found active: ");

        return this;
    }

    public GlobalNavigation switchToOnlineMode() {
        Driver.switchToOnlineMode();
        return this;
    }

    public GlobalNavigation switchToOfflineMode() {
        Driver.switchToOfflineMode();
        return this;
    }

    public GlobalNavigation openInstitutions() {
        openPanel(INSTITUTIONS_CLASS);
        return this;
    }

    public InstitutionSearchPanel searchInstitutions(){
        return (InstitutionSearchPanel) openSearchByContentType(INSTITUTIONS_CLASS);
    }

    public InstitutionSearchTab searchInstitutions(String name){
        Assert.assertNotNull(name, "Cannot search for Institution: name is NULL.");
        return (InstitutionSearchTab)performSearchByContentType(INSTITUTIONS_CLASS, name);
    }

    public InstitutionFullProfile openInstitutionProfile(String name) {
        searchInstitutions(name).openFullProfile(name);
        return new InstitutionFullProfile();
    }

    public GlobalNavigation openContacts() {
        openPanel(CONTACTS_CLASS);
        return this;
    }

    public ContactSearchPanel searchContacts(){
        return (ContactSearchPanel) openSearchByContentType(CONTACTS_CLASS);
    }

    public ContactSearchTab searchContacts(String name){
        Assert.assertNotNull(name, "Cannot search for Contact: name is NULL.");
        return (ContactSearchTab)performSearchByContentType(CONTACTS_CLASS, name);
    }

    public ContactFullProfile openContactProfile(String name) {
        searchContacts(name).openFullProfile(name);
        return new ContactFullProfile();
    }

    public GlobalNavigation openCalendar() {
        openPanel(CALENDAR_CLASS);
        return this;
    }

    public CalendarSearchPanel searchActivities(){
        return (CalendarSearchPanel) openSearchByContentType(CALENDAR_CLASS);
    }

    public ActivitySearchTab searchActivities(String subject){
        Assert.assertNotNull(subject, "Cannot search for Activity: subject is NULL.");
        return (ActivitySearchTab) performSearchByContentType(CALENDAR_CLASS, subject);
    }

    public GlobalNavigation openSecurities() {
        openPanel(SECURITIES_CLASS);
        return this;
    }

    public SecuritySearchPanel searchSecurities(){
        return (SecuritySearchPanel) openSearchByContentType(SECURITIES_CLASS);
    }

    public SecuritySearchTab searchSecurities(String tickerOrName){
        Assert.assertNotNull(tickerOrName, "Cannot search for Security: identifier is NULL.");
        return (SecuritySearchTab) performSearchByContentType(SECURITIES_CLASS, tickerOrName);
    }

    public void openSecurityProfile(String name) {
        searchSecurities(name).openFullProfile(name);
    }

    public void openSecurityProfile(String name, String subtext) {
        searchSecurities(name).openFullProfile(name, subtext);
    }

    public EqSecurityFullProfile openEquitySecurityProfile(String name, String subtext) {
        openSecurityProfile(name, subtext);
        return new EqSecurityFullProfile();
    }

    public FiSecurityFullProfile openFixedIncomeSecurityProfile(String name) {
        ((SecuritySearchPanel)openSecurities().openSearch().setSearchField(name)).selectAssetClass(UITitles.AssetClass.FIXED_INCOME).search();
        new SecuritySearchTab().openFixedIncomeFullProfile(name);
        return new FiSecurityFullProfile();
    }

    public GlobalNavigation openFunds() {
        openPanel(FUNDS_CLASS);
        return this;
    }

    public FundSearchPanel searchFunds(){
        return (FundSearchPanel) openSearchByContentType(FUNDS_CLASS);
    }

    public FundSearchTab searchFunds(String name){
        Assert.assertNotNull(name, "Cannot search for Fund: name is NULL.");
        return (FundSearchTab) performSearchByContentType(FUNDS_CLASS, name);
    }

    public FundFullProfile openFundProfile(String name) {
        searchFunds(name).openFullProfile(name);
        return new FundFullProfile();
    }

    public void selectTab(UITitles.PanelTabs tabName) {
        tabControl.selectTab(tabName);
    }

    public InvestorsTab selectInvestorsTab(){
        panelControl.selectTab(UITitles.Panels.INSTITUTIONS);
        tabControl.selectTab(UITitles.PanelTabs.INVESTORS);
        return new InvestorsTab();
    }

    public ActivityTab selectActivityTab(){
        panelControl.selectTab(UITitles.Panels.CONTACTS);
        tabControl.selectTab(UITitles.PanelTabs.ACTIVITY);
        return new ActivityTab();
    }

    public GlobalNavigation clickOnListsTab(){
        BaseTabControl.performCheck = false;
        tabControl.selectTab(UITitles.PanelTabs.LISTS);
        return this;
    }

    public ListsTab selectListsTab(){
        tabControl.selectTab(UITitles.PanelTabs.LISTS);
        switch (getSelectedPanel()) {
            case INSTITUTIONS:
                return new InstitutionListsTab();
            case CONTACTS:
                return new ContactListsTab();
            default:
                throw new Error("Unknown panel type '"+getSelectedPanel()+"'.");
        }
    }

    public RecentlyViewedTab selectRecentlyViewedTab(){
        tabControl.selectTab(UITitles.PanelTabs.RECENTLY_VIEWED);
        switch (getSelectedPanel()) {
            case INSTITUTIONS:
                return new InstitutionRecentlyViewedTab();
            case CONTACTS:
                return new ContactRecentlyViewedTab();
            case FUNDS:
                return new FundRecentlyViewedTab();
            case SECURITIES:
                return new SecurityRecentlyViewedTab();
            default:
                throw new Error("Unknown panel type '"+getSelectedPanel()+"'.");
        }
    }

    public SearchResultsTab selectSearchResultsTab(){
        tabControl.selectTab(UITitles.PanelTabs.SEARCH_RESULTS);
        switch (getSelectedPanel()) {
            case INSTITUTIONS:
                return new InstitutionSearchTab();
            case CONTACTS:
                return new ContactSearchTab();
            case FUNDS:
                return new FundSearchTab();
            case SECURITIES:
                return new SecuritySearchTab();
            case CALENDAR:
                return new ActivitySearchTab();
            default:
                throw new Error("Unknown panel type '"+getSelectedPanel()+"'.");
        }
    }

    public BaseSearchPanel openSearch() {
        Logger.logDebug("Search Box will be obtained.");
        SenchaWebElement searchBox = getButton(SEARCH_BOX_CLASS);
        Logger.logDebug(searchBox.inspect());
        searchBox.highlight();
        Logger.logDebugScreenshot("Search Box highlighted");
        searchBox.click();
        Logger.logDebugScreenshot("Search Box clicked.");
        return getSearchPanel(getSelectedPanel());
    }

    private void performDefaultSearch(String searchFilter) {
        BaseSearchPanel panel = openSearch();
        panel.reset();
        panel.setSearchField(searchFilter);
        panel.search();
    }

    public MapPreviewOverlay openMapPreview(){
        UITitles.Panels currentTitle = getSelectedPanel();
        UITitles.OverlayType expectedOverlayType = null;
        switch (currentTitle) {
            case INSTITUTIONS:
                expectedOverlayType = UITitles.OverlayType.INSTITUTION_ADDRESS;
                break;
            case CONTACTS:
                expectedOverlayType = UITitles.OverlayType.CONTACT_ADDRESS;
                break;
            default:
                throw new Error("No overlay type for map preview is defined for panel "+currentTitle);
        }
        Logger.logDebugScreenshot("Preparing to open map preview "+ UITitles.get(expectedOverlayType));
        SenchaWebElement showLocationButton = Driver.findVisible(By.xpath(MAP_PREVIEW_BUTTON_XPATH));
        if (showLocationButton == null) {
            throw new Error("'Show Location' button is not available on the screen");
        } else {
            showLocationButton.click();
        }
        return new MapPreviewOverlay(expectedOverlayType);
    }

    public GlobalNavigation back() {
        SenchaWebElement backButton = getBackButton();
        if (backButton != null) {
            backButton.click();
        } else {
            Logger.logError("Back button is not available on this screen.");
        }
        waitApplicationReady();
        return this;
    }

    public boolean isBackButtonPresent(){
        return getBackButton() != null;
    }

    public GlobalNavigation verifyBackButtonPresent(){
        Assert.assertTrue(isBackButtonPresent(), "Back button should be present on the screen, but it was not found.");
        return this;
    }

    public GlobalNavigation clearStoredData(){
        OfflineModeTab offlineModeTab = (OfflineModeTab)
                openHamburger()
                .openSettings()
                .select(UITitles.SettingsTab.OFFLINE_MODE);
        offlineModeTab.clearStoredData();
        return this;
    }

    private SenchaWebElement getBackButton(){
        SenchaWebElement button = Driver.findVisibleNow(By.className(BACK_BUTTON_CLASS));
        return button;
    }



    public GlobalNavigation waitApplicationReady(){
        ScreenCard appCard = new ScreenCard();
        appCard.addLoadingIndicatorCheckpoint();
        appCard.waitReady();
        return this;
    }

    private void openPanel(String panelClass) {
        String selectedPanelName = getSelectedTabName();
        if (!selectedPanelName.equalsIgnoreCase(panelClass)) {
            getButton(panelClass).click();
            tabControl.waitReady();
            waitReady();
            waitApplicationReady();
        }
    }

    private BaseSearchPanel getSearchPanel(UITitles.Panels panel){

        switch (panel){
            case INSTITUTIONS:
                return new InstitutionSearchPanel();
            case CONTACTS:
                return new ContactSearchPanel();
            case CALENDAR:
                return new CalendarSearchPanel();
            case SECURITIES:
                return new SecuritySearchPanel();
            case FUNDS:
                return new FundSearchPanel();
            default: return null;
        }
    }

    private SenchaWebElement getButton(String buttonClass) {
        return Driver.findVisible(By.className(buttonClass));
    }

    private BaseSearchPanel openSearchByContentType(String contentPanelClass) {
        openPanel(contentPanelClass);
        return openSearch();
    }

    private SearchResultsTab performSearchByContentType(String contentPanelClass, String searchFilter) {
        openPanel(contentPanelClass);
        performDefaultSearch(searchFilter);
        switch (contentPanelClass) {
            case INSTITUTIONS_CLASS:    return new InstitutionSearchTab();
            case CONTACTS_CLASS:        return new ContactSearchTab();
            case CALENDAR_CLASS:        return new ActivitySearchTab();
            case SECURITIES_CLASS:      return new SecuritySearchTab();
            case FUNDS_CLASS:           return new FundSearchTab();
        }
        throw new Error("Cannot return search results tab for content type "+contentPanelClass+".");
    }

    public GlobalNavigation verifyTooltip(String messageText) {
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
}

