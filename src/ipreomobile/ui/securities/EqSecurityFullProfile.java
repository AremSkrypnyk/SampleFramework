package ipreomobile.ui.securities;

import ipreomobile.core.Driver;
import ipreomobile.core.Logger;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.FullProfile;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

public class EqSecurityFullProfile extends FullProfile {
    private static final By TICKER_LOCATOR = By.className("ticker");
    private static final By HEADER_LOCATOR = By.xpath(new XPathBuilder().byClassName("security-summary").byClassName("title").build());

    private static final String SUBTITLE_XPATH   = new XPathBuilder().byClassName("header").byClassName("text").build();
    private static final String MARKET_CAP_XPATH = new XPathBuilder()
            .byClassName("info")
            .byClassName("group")
            .withChildTag("div").withClassName("subheader").withText("%s")
            .byClassName("text").build();

    public EqSecurityFullProfile(){
        super();
        setHeaderLocator(HEADER_LOCATOR);
        waitReady();
    }

    public String getLocation(){
        String locationXpath = SUBTITLE_XPATH + "[1]";
        return Driver.findVisible(By.xpath(locationXpath)).getText();
    }

    public EqSecurityFullProfile verifyLocation(String expectedLocation){
        Assert.assertEquals(getLocation(), expectedLocation, "Location mismatch found: ");
        return this;
    }

    public String getTicker(){
        return Driver.findVisible(TICKER_LOCATOR).getText();
    }

    public EqSecurityFullProfile verifyTicker(String expectedTicker){
        Assert.assertEquals(getTicker(), "(" + expectedTicker + ")", "Ticker mismatch found: ");
        return this;
    }

    public String getExchangeName(){
        String exchangeXpath = SUBTITLE_XPATH + "[2]";
        return Driver.findVisible(By.xpath(exchangeXpath)).getText();
    }

    public EqSecurityFullProfile verifyExchangeName(String expectedExchangeName){
        Assert.assertEquals(getExchangeName(), expectedExchangeName, "Exchange name mismatch found: ");
        return this;
    }

    public String getMarketCap(String capType){
        String value = "";
        String marketCapXpath = String.format(MARKET_CAP_XPATH, capType);
       SenchaWebElement marketCap = Driver.findVisible(By.xpath(marketCapXpath));
        if (marketCap == null) {
            Logger.logError("No market cap '"+capType+"' found. Allowed: Macro, Mid, Micro.");
        } else {
            value = marketCap.getText();
        }
        return value;
    }

    @Override
    public String getProfileName(){
        String nameWithTicker = super.getProfileName();
        String ticker = getTicker();
        return nameWithTicker.replace(ticker, "").trim();
    }

    @Override
    public EqSecurityFullProfile verifyProfileName(String name) {
        String actualName = getProfileName().toUpperCase();
        String ticker = getTicker();
        String expectedName = name.replace(ticker, "").trim().toUpperCase();
        Assert.assertEquals(actualName, expectedName, "Profile name displayed on "+profileType+" Profile is different from expected one: ");
        return this;
    }

}
