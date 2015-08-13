package ipreomobile.ui.search;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.BaseSearchPanel;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.blocks.overlay.ListOverlayFilter;
import ipreomobile.ui.blocks.overlay.ListOverlayFilterWithTabs;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;

public class InstitutionSearchPanel extends BaseSearchPanel {
    private static final By INSTITUTION_TYPE_LOCATOR           = By.xpath(new XPathBuilder().byTag("div").withClassName("x-innerhtml").withChildText("Institution Type").build());
    private static final By INVESTMENT_CENTER_LOCATION_LOCATOR = By.xpath(new XPathBuilder().byTag("div").withClassName("x-innerhtml").withChildText("Investment Center / Locations").build());
    private static final By CITY_NAME_LOCATOR                  = By.xpath(new XPathBuilder().byTag("div").withClassName("input-box").withChildText("City Name").byTag("input").withClassName("x-input-search").build());
    private static final By INSTITUTION_LIST_LOCATOR           = By.xpath(new XPathBuilder().byTag("div").withClassName("x-innerhtml").withChildText("Institution Lists").build());

    public InstitutionSearchPanel(){}

    //SearchIn
    public InstitutionSearchPanel selectSearchIn(UITitles.SearchParams name){
        SearchTabControl tabControl = new SearchTabControl(UITitles.SearchType.SEARCH_IN);
        tabControl.selectTab(name);
        return this;
    }

    //Side
    public InstitutionSearchPanel selectSide(UITitles.SearchParams name){
        SearchTabControl tabControl = new SearchTabControl(UITitles.SearchType.SIDE);
        tabControl.selectTab(name);
        return this;
    }

    public ListOverlayFilter setInstitutionType(){
        Driver.get().findElement(INSTITUTION_TYPE_LOCATOR).click();
        return new ListOverlayFilter(UITitles.OverlayType.INSTITUTION_TYPE);
    }

    public InstitutionSearchPanel setInstitutionType(String institutionType){
        Driver.get().findElement(INSTITUTION_TYPE_LOCATOR).click();
        ListOverlayFilter overlay = new ListOverlayFilter(UITitles.OverlayType.INSTITUTION_TYPE);
        overlay.findAndSelect(institutionType);
        return this;
    }

    public ListOverlayFilter setInvestmentCenterLocation(){
        Driver.get().findElement(INVESTMENT_CENTER_LOCATION_LOCATOR).click();
        return new ListOverlayFilter(UITitles.OverlayType.INVESTMENT_CENTER_LOCATION);
    }

    public InstitutionSearchPanel setInvestmentCenterLocation(String investmentCenterLocation){
        Driver.get().findElement(INVESTMENT_CENTER_LOCATION_LOCATOR).click();
        ListOverlayFilter overlay = new ListOverlayFilter(UITitles.OverlayType.INVESTMENT_CENTER_LOCATION);
        overlay.findAndSelect(investmentCenterLocation);
        return this;
    }

    public InstitutionSearchPanel setCityName(String cityName){
        SenchaWebElement cityNameField = Driver.findVisible(CITY_NAME_LOCATOR);
        cityNameField.click();
        cityNameField.clear();
        cityNameField.sendKeys(cityName);
        return this;
    }

    public InstitutionSearchPanel setInstitutionName(String institutionName) {
        setSearchField(institutionName);
        return this;
    }

    public ListOverlayFilterWithTabs selectInstitutionList(){
        Driver.get().findElement(INSTITUTION_LIST_LOCATOR).click();
        return new ListOverlayFilterWithTabs(UITitles.OverlayType.INSTITUTION_LISTS);
    }

    public InstitutionSearchPanel selectInstitutionList(UITitles.ListType listType, String institutionList){
        Driver.get().findElement(INSTITUTION_LIST_LOCATOR).click();
        ListOverlayFilterWithTabs overlay = new ListOverlayFilterWithTabs(UITitles.OverlayType.INSTITUTION_LISTS);
        overlay.selectListType(listType);
        overlay.findAndSelect(institutionList);
        return this;
    }

    public InstitutionSearchPanel clearInstitutionName(){
        super.clearSearchFilter();
        return this;
    }
}
