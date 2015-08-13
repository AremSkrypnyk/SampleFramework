package ipreomobile.templates.ui;

import ipreomobile.core.*;
import ipreomobile.ui.ScreenCard;
import ipreomobile.ui.blocks.BaseTabControl;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class ProfileTab extends ScreenCard {
    private String diagramBlockClassName = "charttable";
    private String diagramLegendClassName = "legend-item";
    private By searchFieldSelector;
    private static final String TAB_MESSAGE_TEXT_IN_OFFLINE_MODE = "This tab is not available in Offline Mode. More information may be available online.";
    private static final String SEARCHING_IN_OFFLINE_MODE_MESSAGE_TEXT = "Searching within a profile tab is disabled in Offline Mode.";
    private static final String SEARCHING_IN_OFFLINE_MODE_TOOLTIP_XPATH = new XPathBuilder()
            .byClassName("x-innerhtml")
            .byTag("p").withClassName("fum-text")
            .withText(SEARCHING_IN_OFFLINE_MODE_MESSAGE_TEXT).build();

    //private final String TAB_NAME_PREFIX = "[Profile Tab '"+ getScreenName().replace("Profile\\s+Tab", "") + "']: ";

    private static final String TAB_XPATH = new XPathBuilder().byClassName("profile-tabs-toolbar").byClassName("x-button-normal").build();
    private BaseTabControl toolbarTabControl = new BaseTabControl();

    private int tabLoadingTimeoutSeconds = Integer.parseInt(System.getProperty("test.timeout"));

    public ProfileTab(){
        toolbarTabControl.setTabControlXpath(TAB_XPATH);
        addLoadingIndicatorCheckpoint();
    }

    public ProfileTab setTabLoadingTimeoutSeconds(int tabLoadingTimeoutSeconds) {
        this.tabLoadingTimeoutSeconds = tabLoadingTimeoutSeconds;
        return this;
    }

    public String getScreenName(){
        return "[" + FullProfile.getActiveTabName() + "]: ";
    }

    public void waitReady(){
        super.waitReady();
        long startTime = System.nanoTime();
        long timeout = TimeUnit.NANOSECONDS.convert(tabLoadingTimeoutSeconds, TimeUnit.SECONDS);
        while (System.nanoTime() - startTime <= timeout) {
            if (isContentLoaded() || isProfileTabEmpty()) {
                return;
            }
        }
        throw new Error(getScreenName() + "Page is still not ready after "+tabLoadingTimeoutSeconds+" seconds.");
    }

    public ProfileTab setTabControlXpath(String xpath) {
        toolbarTabControl.setTabControlXpath(xpath);
        return this;
    }

    public By getSearchFieldSelector() {
        return searchFieldSelector;
    }

    public ProfileTab setSearchFieldSelector(By searchFieldSelector) {
        this.searchFieldSelector = searchFieldSelector;
        return this;
    }

    public ProfileTab selectTab(String tabName){
        closeActiveOverlay();
        if (isTabPresent(tabName)) {
            if (!isTabSelected(tabName)) {
                toolbarTabControl.selectTab(tabName);
                waitReady();
            }
        } else {
            throw new Error(getScreenName() + "Tab '"+tabName+"' is not available for Full Profile tab '"+getHeader()+"'.");
        }
        return this;
    }

    public boolean isTabPresent(String tabName) {
        return toolbarTabControl.hasTab(tabName);
    }

    public boolean isTabSelected(String tabName) { return toolbarTabControl.getSelectedTabName().equalsIgnoreCase(tabName);}

    public String getSelectedTab() { return toolbarTabControl.getSelectedTabName();}

    public ProfileTab verifyActiveTab(String expectedTabName) {
        toolbarTabControl.verifyActiveTab(expectedTabName);
        return this;
    }

    public ProfileTab verifyTabPresent(String expectedTabName) { toolbarTabControl.verifyTabPresence(expectedTabName); return this; }

    public String getHeaderXpath() {
        return headerXpath;
    }

    public String getHeader() {
        return Driver.findVisible(By.xpath(headerXpath)).getText();
    }

    public ProfileTab setHeaderXpath(String headerXpath) {
        this.headerXpath = headerXpath;
        return this;
    }

    public ProfileTab closeActiveOverlay(){
        if (BaseOverlay.isActiveMaskPresent()) {
            BaseOverlay.closeActiveOverlay();
        }
        return this;
    }

    private String headerXpath;

    public boolean verifyDiagram(String diagramName) {
        String diagramBlockXpath = new XPathBuilder().byClassName(diagramBlockClassName).withChildText(diagramName).build();
        SenchaWebElement diagramBlock = Driver.findIfExists(By.xpath(diagramBlockXpath));
        if (diagramBlock == null) {
            Logger.logError(getScreenName() + "No chart with title '" + diagramName + "' was found on current tab.");
            return false;
        }
        return true;
    }

    public boolean verifyDiagramLegend(String diagramName){
        String legendXpath = new XPathBuilder().byClassName(diagramBlockClassName).withChildText(diagramName).byClassName(diagramLegendClassName).build();
        SenchaWebElement legend = Driver.findIfExists(By.xpath(legendXpath));
        if (legend == null) {
            Logger.logError(getScreenName() + "No legend was found for chart '"+diagramName+"'.");
            return false;
        }
        return true;
    }

    public String getDiagramBlockClassName() {
        return diagramBlockClassName;
    }

    public ProfileTab setDiagramBlockClassName(String diagramBlockClassName) {
        this.diagramBlockClassName = diagramBlockClassName;
        return this;
    }

    public String getDiagramLegendClassName() {
        return diagramLegendClassName;
    }

    public ProfileTab setDiagramLegendClassName(String diagramLegendClassName) {
        this.diagramLegendClassName = diagramLegendClassName;
        return this;
    }

    protected SenchaWebElement getSearchFilter(){
        return Driver.findVisible(searchFieldSelector);
    }

    public ProfileTab setSearchFilter(String filter) {
        SenchaWebElement searchField = getSearchFilter();
        searchField.setText(filter);
        searchField.sendKeys(Keys.RETURN);
        waitReady();
        return this;
    }

    public boolean isSearchFilterUnavailableInOfflineMode(){
        SenchaWebElement searchField = getSearchFilter();
        return !searchField.isEnabled();
    }

    public void verifyFilterUnavailableOffline(){
        Assert.assertEquals(isSearchFilterUnavailableInOfflineMode(), true, "Search Filter unavailable in offline mode: ");
        getSearchFilter().click();
    }

    /////////////////TODO: this is a test section
    public boolean isContentLoaded() {
        String bodyXpath = "//div[contains(@class, 'profile-tabs')]/div[@class='x-dock x-dock-vertical x-sized']" +
                "/div[@class='x-dock-body']";
        SenchaWebElement tabContent = Driver.findIfExists(By.xpath(bodyXpath));
        boolean isLoaded;
        if (tabContent == null) {
            isLoaded = false;
        } else {
            if (tabContent.getText().isEmpty()) {
                isLoaded = false;
            } else {
                if (getEmptyProfileTabMessage().isEmpty()) {
                    isLoaded = true;
                } else {
                  isLoaded = false;
                }
            }
        }

        Logger.logDebug("Is content loaded: "+isLoaded);
        return isLoaded;
    };

    public ProfileTab verifyContentLoaded(){
        Assert.assertTrue(isContentLoaded(), getScreenName() + "Page is empty, while content should be loaded.");
        return this;
    }

    public boolean isProfileTabEmpty(){
        return !getEmptyProfileTabMessage().isEmpty();
    }

    public ProfileTab verifyProfileTabEmpty(){
        Assert.assertTrue(isProfileTabEmpty(), getScreenName() + "Page is not empty, while no content should be loaded.");
        return this;
    }

    public ProfileTab verifyEmptyProfileTabMessage(String expectedMessage){
        verifyProfileTabEmpty();
        String actualMessage = getEmptyProfileTabMessage();
        Verify.verifyEquals(actualMessage, expectedMessage, getScreenName() + "Empty page message mismatch:");
        return this;
    }

    public boolean isProfileTabAvailableInOfflineMode(){
        String message = getEmptyProfileTabMessage();
        return message == null || !message.equals(TAB_MESSAGE_TEXT_IN_OFFLINE_MODE);
    }

    public ProfileTab verifyProfileTabUnavailableInOfflineMode(){
        Verify.verifyFalse(isProfileTabAvailableInOfflineMode(), getScreenName() + " Tab available offline");
        return this;
    }

    private String getEmptyProfileTabMessage() {
        String message = "";
        String messageXpath = new XPathBuilder().byClassName("profile-empty-data").byClassName("x-centered").build();
        SenchaWebElement profileEmptyData = Driver.findVisibleNow(By.xpath(messageXpath));
        if (profileEmptyData != null) {
            message = profileEmptyData.getText();
        }
        return message;
    }


}
