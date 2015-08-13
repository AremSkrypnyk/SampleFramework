package ipreomobile.templates.ui;

import ipreomobile.core.Driver;
import ipreomobile.core.Logger;
import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.ScreenCard;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

public abstract class TwoPane extends ScreenCard {
    protected QuickProfileList qpl;
    protected ProfileOverviewController profile;

    private static final String OFFLINE_ERROR_MESSAGE_CLASSNAME     = "x-mask-error-message-body";
    private static final String EMPTY_LIST_MESSAGE_CLASSNAME        = "x-list-emptytext";
    private static final String CONTENT_BODY_XPATH                  = new XPathBuilder().byClassName("content-container").byClassName("x-body").build();

    private static final By SEARCH_BOX_SELECTOR                    = By.className("search-criteria-inner");

    public TwoPane() {
        addLoadingIndicatorCheckpoint();
        waitReady();
    }

    protected abstract void setupProfileList();

    protected abstract void setupProfileOverview();

    public ProfileOverviewController getActiveProfileOverview(){
        profile.waitReady();
        return profile;
    }

    public TwoPane openProfileOverview(String name) {
        qpl.waitReady();
        Logger.logDebugScreenshot("Before opening profile overview for entity '" + name + "'.");
        qpl.select(name);
        Logger.logDebugScreenshot("Activated list item '" + getProfileNameSelectedInList() + "'.");
        verifyProfileOverviewLoaded(name);
        return this;
    }

    public TwoPane openProfileOverview(String name, String subtext) {
        qpl.waitReady();
        Logger.logDebugScreenshot("Before opening profile overview for entity '" + name + "' with '" + subtext + "' subtext.");
        qpl.select(name, subtext);
        Logger.logDebugScreenshot("Activated list item '" + getProfileNameSelectedInList() + "'.");
        verifyProfileOverviewLoaded(name, subtext);
        return this;
    }

    public TwoPane openNextProfileOverview() {
        qpl.waitReady();
        qpl.selectNext();
        String profileName = qpl.getSelectedItemName();
        verifyProfileOverviewLoaded(profileName);
        return this;
    }

    protected TwoPane verifyProfileOverviewLoaded(String name) {
        if (qpl.isItemUnavailableInOfflineMode(name)) {
            Logger.logMessage("Profile Overview is empty, because item '" + name + "' is disabled in this list.");
        } else {
            setupProfileOverview();
            profile.waitProfileLoaded(name);
            Logger.logDebugScreenshot("Loaded profile overview for '" + getProfileNameSelectedInList() + "'.");
        }
        return this;
    }

    protected TwoPane verifyProfileOverviewLoaded(String name, String subtext) {
        if (qpl.isItemUnavailableInOfflineMode(name, subtext)) {
            Logger.logMessage("Profile Overview is empty, because item '" + name + "' is disabled in this list.");
        } else {
            setupProfileOverview();
            profile.waitProfileLoaded(name);
            Logger.logDebugScreenshot("Loaded profile overview for '" + getProfileNameSelectedInList() + "'.");
        }
        return this;
    }

    public boolean isProfileOverviewAvailable(){
        return Driver.findVisible(By.className(OFFLINE_ERROR_MESSAGE_CLASSNAME)) == null;
    }

    public TwoPane verifyProfileOverviewAvailable(){
        Assert.assertTrue(isProfileOverviewAvailable(), "Profile overview expected to be unavailable:");
        return this;
    }

	public boolean isProfileUnavailableInOfflineMode(String profileName){
        return qpl.isItemUnavailableInOfflineMode(profileName);
    }

    public boolean isProfileUnavailableInOfflineMode(String profileName, String profileSubject){
        return qpl.isItemUnavailableInOfflineMode(profileName, profileSubject);
    }
    public FullProfile openFullProfile(String name) {
        Logger.logDebugScreenshot("Before opening full profile for entity '" + name + "'.");
        openProfileOverview(name);
        if (qpl.isSelectedItemUnavailableInOfflineMode()) {
            Logger.logMessage("Full profile cannot be displayed, because item '" + name + "' is disabled in this list.");
        } else {
            qpl.clickSelected();
            Logger.logDebugScreenshot("Clicked entity '" + name + "' for the second time.");
        }
        return null;
    }

    public FullProfile openFullProfile(String name, String subtext) {
        Logger.logDebugScreenshot("Before opening full profile for entity '" + name + "' with '" + subtext + "' subtext.");
        openProfileOverview(name, subtext);
        if (qpl.isSelectedItemUnavailableInOfflineMode()) {
            Logger.logMessage("Full profile cannot be displayed, because item '" + name + "' with '" + subtext + "' subtext is disabled in this list.");
        } else {
            qpl.clickSelected();
            Logger.logDebugScreenshot("Clicked entity '" + name + "' with '" + subtext + "' subtext for the second time.");
        }
        return null;
    }

    public void openSearch() {
        SenchaWebElement searchBox = Driver.findVisible(SEARCH_BOX_SELECTOR);
        searchBox.click();
    }

    public String getProfileNameSelectedInList() {
        qpl.waitReady();
        return qpl.getSelectedItemName();
    }

    public TwoPane verifyProfileNameSelectedInList(String name) {
        Assert.assertEquals(getProfileNameSelectedInList(), name, "Active profile name highlighted in Quick Profile List is different from expected one:");
        return this;
    }

    public String getListItemSubtext(String name) {
        return qpl.getItemSubtext(name);
    }

    public String getListTitle() {
        qpl.waitReady();
        return qpl.getListTitle();
    }

    public TwoPane verifyListTitle(String name) {
        Assert.assertEquals(getListTitle(), name, "Active list title mismatch: ");
        return this;
    }

    public String getProfileNameDisplayedInOverview() {
        return profile.getProfileName();
    }

    public TwoPane verifyProfileNameDisplayedInOverview(String expectedName) {
        profile.verifyProfileName(expectedName);
        return this;
    }

    public TwoPane verifyItemPresentInList(String name) {
        qpl.verifyItemPresence(name);
        return this;
    }

    public TwoPane verifyItemPresentInList(String name, String subtext) {
        qpl.verifyItemPresence(name, subtext);
        return this;
    }

    public boolean isContentLoaded() {
       SenchaWebElement tabContent = Driver.findIfExists(By.xpath(CONTENT_BODY_XPATH));
        boolean isLoaded;
        if (tabContent == null) {
            isLoaded = false;
        } else {
            if (tabContent.getText().isEmpty()) {
                isLoaded = false;
            } else {
                isLoaded = getEmptyListMessage().isEmpty();
            }
        }

        Logger.logDebug("Is content loaded: " + isLoaded);
        return isLoaded;
    }

    public boolean isActiveTabEmpty() {
        return !getEmptyListMessage().isEmpty();
    }
    private String getEmptyListMessage() {
        String message = "";
        String messageXpath = new XPathBuilder().byClassName(EMPTY_LIST_MESSAGE_CLASSNAME).build();
        Driver.saveTimeout();
        Driver.setTimeout(1);
       SenchaWebElement profileEmptyData = Driver.findVisibleNow(By.xpath(messageXpath));
        Driver.restoreSavedTimeout();
        if (profileEmptyData != null) {
            message = profileEmptyData.getText();
        }
        return message;
    }

//    public boolean isItemUnavailable(String name){
//        return qpl.isItemUnavailable(name);
//    }
//
//    public boolean isItemUnavailable(String name, String subtext){
//        return qpl.isItemUnavailable(name, subtext);
//    }

    public void setItemsXpath(String xpath){
        qpl.setItemsXpath(xpath);
    }

    public String getItemsXpath(){
        return qpl.getItemsXpath();
    }
}