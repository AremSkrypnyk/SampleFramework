package ipreomobile.ui.blocks;

import java.util.*;

import ipreomobile.core.*;
import ipreomobile.ui.ScreenCard;
import ipreomobile.ui.UITitles;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;

import org.testng.Assert;


public class BaseTabControl extends ScreenCard {
    private static int TAB_LOADING_TIMEOUT = 10;

    private String tabControlXpath;
    private String selectedTabClass = "x-button-pressed";
    private String disabledTabControlClass = "x-item-disabled";
    private SenchaWebElement container;

    private static final By TAB_CONTROL_LOCATOR = By.className("toggle-button");

    public static boolean performCheck = true;

    public void setTabControlXpath(String xpath) {
        this.tabControlXpath = xpath;
        setMaxWaitTimeout(TAB_LOADING_TIMEOUT);
        addCheckpointElement(By.xpath(tabControlXpath)).mustBeVisible();
    }

    public void setTabControlContainer(SenchaWebElement container) {
        this.container = container;
    }

    public void setSelectedTabClass(String condition) {
        this.selectedTabClass = condition;
    }

    public void selectTab(String tabName) {
        waitReady();
        if (!isSelected(tabName)) {
            String tabXpath = getTabXpath(tabName);
            SenchaWebElement tabToSelect = Driver.findVisible(By.xpath(tabXpath), container);

            try {
                tabToSelect.click();
            } catch (StaleElementReferenceException e) {
                Driver.pause(500);
                selectTab(tabName);
            }
            if (performCheck) verifyTabSelection(tabToSelect);
        }
    }

    public boolean isSelected(String tabName) {
        return getSelectedTabName().equals(tabName);
    }

    public String getSelectedTabName(){
        String selectedTabName = "";
        String selectedTabXpath = new XPathBuilder()
                .byXpathPart(tabControlXpath)
                .withClassName(selectedTabClass)
                .or()
                .byXpathPart(tabControlXpath)
                .byClassName(selectedTabClass)
                .build();
        try {
            selectedTabName = Driver.findIfExistsNow(By.xpath(selectedTabXpath), container).getText();
        }
        catch (NullPointerException e) {
            Logger.logDebug("No selected tab was found by XPath '"+selectedTabXpath+"'.");
        }
        return selectedTabName;
    }

    private void verifyTabSelection(SenchaWebElement tab) {
        addOneTimeCheckpoint(By.xpath(".")).addClassCondition(selectedTabClass).setParentItem(tab);
        if (!isReady()) {
            String selectedTabXPath = new XPathBuilder().byCurrentItem().byClassName(selectedTabClass).build();
            addOneTimeCheckpoint(By.xpath(selectedTabXPath)).setParentItem(tab);
            if (!isReady()) {
                throw new Error("Invalid tab selection: could not verify selected tab condition '" + selectedTabClass + "'.");
            }
        }
    }

    public void selectTab(Enum<?> tabId) {
        String tabName = UITitles.get(tabId);
        selectTab(tabName);
    }

    public void verifyActiveTab(String expectedTabName){
        verifyActiveTab(expectedTabName, "Active tab mismatch: ");
    }

    public void verifyActiveTab(String expectedTabName, String errorMessage){
        String actualTabName = getSelectedTabName();
        Assert.assertEquals(actualTabName.toUpperCase(), expectedTabName.toUpperCase(), errorMessage);
    }

    public void verifyActiveTab(Enum<?> expectedTabId){
        verifyActiveTab(expectedTabId, "Active tab mismatch: ");
    }

    public void verifyActiveTab(Enum<?> expectedTabId, String errorMessage){
        String actualTabName = getSelectedTabName();
        String expectedTabName = UITitles.get(expectedTabId);
        Assert.assertEquals(actualTabName.toUpperCase(), expectedTabName.toUpperCase(), errorMessage);
    }

    public boolean hasTab(String tabName) {
        waitReady();
        String tabXpath = getTabXpath(tabName);
        return Driver.findVisible(By.xpath(tabXpath)) != null;
    }

    public boolean hasTab(Enum<?> tabId) {
        waitReady();
        String tabName = UITitles.get(tabId);
        String tabXpath = getTabXpath(tabName);
        return Driver.findVisible(By.xpath(tabXpath)) != null;
    }

    public void verifyTabPresence(String tabName){
        if (!hasTab(tabName)) {
            Logger.logError("No tab named '" + tabName + "' was found. Tabs available by xpath '" + tabControlXpath + "': [" + String.join(", ", getTabNames()) + "].");
        }
    }

    public void verifyTabPresence(Enum<?> tabId){
        String tabName = UITitles.get(tabId);
        verifyTabPresence(tabName);
    }

    public void verifyTabControlExists(){
        Assert.assertTrue(Driver.findIfExists(By.xpath(tabControlXpath), container) != null, "Tab control was not found.");
    }


    private String getTabXpath(String tabName) {
        //return tabControlXpath + "[text()='" + tabName + "' or .//text()='" + tabName + "']";
        return new XPathBuilder().byXpathPart(tabControlXpath).withTextIgnoreCase(tabName.toLowerCase()).or().byXpathPart(tabControlXpath).withChildTextIgnoreCase(tabName.toLowerCase()).build();
    }

    private List<String> getTabNames() {
        List<String> tabNames = new ArrayList<>();
        List<SenchaWebElement> tabs = Driver.findAll(By.xpath(tabControlXpath));
        for (SenchaWebElement tab : tabs) {
            tabNames.add(tab.getText());
        }
        return tabNames;
    }

    public boolean isTabControlUnavailableInOfflineMode(){
        SenchaWebElement tabControl = Driver.findVisible(TAB_CONTROL_LOCATOR, container);
        if (tabControl != null){
            return tabControl.hasClass(disabledTabControlClass);
        } else {
            throw new NoSuchElementException("No tab control found");
        }
    }

    public void verifyTabControlUnavailableInOfflineMode(){
        Assert.assertTrue(isTabControlUnavailableInOfflineMode(), "Tab Control expected to be unavailable: ");
    }

}
