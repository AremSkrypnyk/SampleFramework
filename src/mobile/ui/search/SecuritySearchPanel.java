package ipreomobile.ui.search;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.BaseSearchPanel;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.blocks.BaseTabControl;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;

public class SecuritySearchPanel extends BaseSearchPanel {
    private static final By ISSUER_COMPANY_LOCATOR = By.xpath(new XPathBuilder().byTag("div").withClassName("input-box").withChildText("Issuer / Company Name").byTag("input").withClassName("x-input-search").build());
    private static final By ISIN_CUSIP_LOCATOR     = By.xpath(new XPathBuilder().byTag("div").withClassName("input-box").withChildText("ISIN # / CUSIP").byTag("input").withClassName("x-input-search").build());
    private static final String TAB_CONTROL_XPATH  = new XPathBuilder().byTag("div").withClassName("criteria-panel")
                                                                        .byTag("div").withClassName("x-segmentedbutton-inner").withChildText("Equity")
                                                                        .byTag("div").withClassName("x-button-normal").build();

    public SecuritySearchPanel selectAssetClass(UITitles.AssetClass assetClass){
        BaseTabControl tabControl = new BaseTabControl();
        tabControl.setTabControlXpath(TAB_CONTROL_XPATH);
        tabControl.selectTab(assetClass);
        return this;
    }

    public SecuritySearchPanel searchEquities(){
        selectAssetClass(UITitles.AssetClass.EQUITY);
        return this;
    }

    public SecuritySearchPanel searchFixedIncomes(){
        selectAssetClass(UITitles.AssetClass.FIXED_INCOME);
        return this;
    }

    public SecuritySearchPanel setTickerOrSecurityName(String tickerOrSecurityName){
        setSearchField(tickerOrSecurityName);
        return this;
    }

    public SecuritySearchPanel clearTickerOrSecurityNameField(){
        clearSearchFilter();
        return this;
    }

    public SecuritySearchPanel setIssuerCompanyName(String issuerCompanyName){
        SenchaWebElement name = Driver.findVisible(ISSUER_COMPANY_LOCATOR);
        name.click();
        name.clear();
        name.sendKeys(issuerCompanyName);
        return this;
    }

    public SecuritySearchPanel setIsinCusip(String isinCusip){
        SenchaWebElement name = Driver.findVisible(ISIN_CUSIP_LOCATOR);
        name.click();
        name.clear();
        name.sendKeys(isinCusip);
        return this;
    }

    public SecuritySearchPanel reset(){
        super.reset();
        return this;
    }
}
