package ipreomobile.templates.ui;

import ipreomobile.core.Driver;
import ipreomobile.ui.GlobalNavigation;
import ipreomobile.ui.ScreenCard;
import ipreomobile.ui.UITitles;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public abstract class BaseSearchPanel extends ScreenCard {

    private static final By SEARCH_BUTTON_LOCATOR   = By.className("performSearchButton");
    private static final By RESET_BUTTON_LOCATOR    = By.className("resetButton");
    private static final By CANCEL_BUTTON_LOCATOR   = By.className("cancelButton");
    private static final By SEARCH_FILTER_LOCATOR   = By.className("x-input-search");
    private static final By CLEAR_BUTTON_LOCATOR    = By.className("x-clear-icon");

    private OverlayController activeOverlay;

    public BaseSearchPanel(){
        addCheckpointElement(SEARCH_BUTTON_LOCATOR).mustBeVisible();
        waitReady();
    }

    public SearchResultsTab search(){
        Driver.findIfExists(SEARCH_BUTTON_LOCATOR).click();
        new GlobalNavigation().waitApplicationReady();
        return null;
    }

    public BaseSearchPanel reset(){
        Driver.findIfExists(RESET_BUTTON_LOCATOR).click();
        return this;
    }

    public BaseSearchPanel cancel(){
        Driver.findIfExists(CANCEL_BUTTON_LOCATOR).click();
        return this;
    }

    public BaseSearchPanel setSearchField(String string){
       SenchaWebElement searchField = Driver.findIfExists(SEARCH_FILTER_LOCATOR);
        searchField.click();
        searchField.clear();
        searchField.sendKeys(string);
        return this;
    }

    public BaseSearchPanel clearSearchFilter(){
        Driver.findIfExists(CLEAR_BUTTON_LOCATOR).click();
        return this;
    }

    protected boolean checkActiveOverlay(UITitles.OverlayType type) {
        return activeOverlay.checkType(type);
    }


}

