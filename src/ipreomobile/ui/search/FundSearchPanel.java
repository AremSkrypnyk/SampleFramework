package ipreomobile.ui.search;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.BaseSearchPanel;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.blocks.overlay.ListOverlayFilter;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;

public class FundSearchPanel extends BaseSearchPanel {
    private static final By CITY_NAME_LOCATOR                  = By.xpath(new XPathBuilder().byTag("div").withClassName("input-box").withChildText("City Name").byTag("input").withClassName("x-input-search").build());
    private static final By INVESTMENT_CENTER_LOCATION_LOCATOR = By.xpath(new XPathBuilder().byTag("div").withClassName("x-innerhtml").withChildText("Investment Center / Locations").build());
    private static final By FUND_TYPE_LOCATOR                  = By.xpath(new XPathBuilder().byTag("div").withClassName("x-innerhtml").withChildText("Fund Type").build());

    public FundSearchPanel selectSearchIn(UITitles.SearchParams searchParameter){
        SearchTabControl tabControl = new SearchTabControl(UITitles.SearchType.SEARCH_IN);
        tabControl.selectTab(searchParameter);
        return this;
    }

    public FundSearchPanel setCityName(String string){
        SenchaWebElement cityName = Driver.findVisible(CITY_NAME_LOCATOR);
        cityName.click();
        cityName.clear();
        cityName.sendKeys(string);
        return this;
    }

    public ListOverlayFilter selectInvestmentCenterLocation(){
        Driver.get().findElement(INVESTMENT_CENTER_LOCATION_LOCATOR).click();
        return new ListOverlayFilter(UITitles.OverlayType.INVESTMENT_CENTER_LOCATION);
    }

    public FundSearchPanel selectInvestmentCenterLocation(String investmentCenterLocation){
        Driver.get().findElement(INVESTMENT_CENTER_LOCATION_LOCATOR).click();
        ListOverlayFilter overlay = new ListOverlayFilter(UITitles.OverlayType.INVESTMENT_CENTER_LOCATION);
        overlay.findAndSelect(investmentCenterLocation);
        return this;
    }

    public ListOverlayFilter selectFundType(){
        Driver.get().findElement(FUND_TYPE_LOCATOR).click();
        return new ListOverlayFilter(UITitles.OverlayType.FUND_TYPE);
    }

    public FundSearchPanel selectFundType(String fundType){
        Driver.get().findElement(FUND_TYPE_LOCATOR).click();
        ListOverlayFilter overlay = new ListOverlayFilter(UITitles.OverlayType.FUND_TYPE);
        overlay.findAndSelect(fundType);
        return this;
    }
}
