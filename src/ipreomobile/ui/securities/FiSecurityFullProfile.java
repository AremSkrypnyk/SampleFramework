package ipreomobile.ui.securities;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.FullProfile;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

public class FiSecurityFullProfile extends FullProfile {

    private static final By HEADER_LOCATOR              = By.xpath(new XPathBuilder().byClassName("fisecurity-summary").byClassName("title").build());
    private static final String SUBTITLE_XPATH          = new XPathBuilder().byClassName("header").byClassName("text").build();

    private static final By COUPON_LOCATOR              = By.xpath(new XPathBuilder().byClassName("fisecurity-summary-table").byTag("tr").byIndex(2).byTag("td").byIndex(1).build());
    private static final By MATURITY_DATE_LOCATOR       = By.xpath(new XPathBuilder().byClassName("fisecurity-summary-table").byTag("tr").byIndex(2).byTag("td").byIndex(2).build());
    private static final By NUMBER_OF_HOLDERS_LOCATOR   = By.xpath(new XPathBuilder().byClassName("fisecurity-summary-table").byTag("tr").byIndex(2).byTag("td").byIndex(3).build());
    private static final By TOTAL_PAR_HELD_LOCATOR      = By.xpath(new XPathBuilder().byClassName("fisecurity-summary-table").byTag("tr").byIndex(2).byTag("td").byIndex(4).build());

    private static final By NUMBER_OF_BUYERS_LOCATOR    = By.xpath(new XPathBuilder().byClassName("fisecurity-summary-table").byTag("tr").byIndex(4).byTag("td").byIndex(1).build());
    private static final By NUMBER_OF_BUY_INS_LOCATOR   = By.xpath(new XPathBuilder().byClassName("fisecurity-summary-table").byTag("tr").byIndex(4).byTag("td").byIndex(2).build());
    private static final By NUMBER_OF_SELLERS_LOCATOR   = By.xpath(new XPathBuilder().byClassName("fisecurity-summary-table").byTag("tr").byIndex(4).byTag("td").byIndex(3).build());
    private static final By NUMBER_OF_SELL_OUTS_LOCATOR = By.xpath(new XPathBuilder().byClassName("fisecurity-summary-table").byTag("tr").byIndex(4).byTag("td").byIndex(4).build());


    public FiSecurityFullProfile(){
        super();
        setHeaderLocator(HEADER_LOCATOR);
        waitReady();
    }

    public String getLocation(){
        String locationXpath = SUBTITLE_XPATH + "[1]";
        return Driver.findVisible(By.xpath(locationXpath)).getText();
    }

    public FiSecurityFullProfile verifyLocation(String expectedLocation){
        Assert.assertEquals(getLocation(), expectedLocation, "Location mismatch found: ");
        return this;
    }

    public String getExchangeName(){
        String exchangeXpath = SUBTITLE_XPATH + "[2]";
        return Driver.findVisible(By.xpath(exchangeXpath)).getText();
    }

    public FiSecurityFullProfile verifyExchangeName(String expectedExchangeName){
        Assert.assertEquals(getExchangeName(), expectedExchangeName, "Exchange name mismatch found: ");
        return this;
    }

    public String getCouponRate(){
        return Driver.findOne(COUPON_LOCATOR).getText();
    }

    public String getMaturityDate(){
        return getAttribute(MATURITY_DATE_LOCATOR);
    }

    public String getNumberOfHolders(){
        return getAttribute(NUMBER_OF_HOLDERS_LOCATOR);
    }

    public String getTotalParHeld(){
        return getAttribute(TOTAL_PAR_HELD_LOCATOR);
    }

    public String getNumberOfBuyers(){
        return getAttribute(NUMBER_OF_BUYERS_LOCATOR);
    }

    public String getNumberOfBuyIns(){
        return getAttribute(NUMBER_OF_BUY_INS_LOCATOR);
    }

    public String getNumberOfSellers(){
        return getAttribute(NUMBER_OF_SELLERS_LOCATOR);
    }

    public String getNumberOfSellOuts(){
        return getAttribute(NUMBER_OF_SELL_OUTS_LOCATOR);
    }

    private String getAttribute(By locator) {
       SenchaWebElement attributeElement = Driver.findIfExists(locator);
        return (attributeElement == null ? null : attributeElement.getText());
    }

    public FiSecurityFullProfile verifyCouponRate(String expectedCouponRate){
        String actualCouponRate = getCouponRate();
        Assert.assertEquals(actualCouponRate, expectedCouponRate, "Coupon value mismatch: expected [" + expectedCouponRate + "], but found [" + actualCouponRate + "].");
        return this;
    }

    public FiSecurityFullProfile verifyMaturityDate(String expectedMaturityDate){
        String actualMaturityDate = getMaturityDate();
        Assert.assertEquals(actualMaturityDate, expectedMaturityDate, "Maturity Date value mismatch: expected [" + expectedMaturityDate + "], but found [" + actualMaturityDate + "].");
        return this;
    }

    public FiSecurityFullProfile verifyNumberOfHolders(String expectedNumberOfHolders){
        String actualNumberOfHolders = getNumberOfHolders();
        Assert.assertEquals(actualNumberOfHolders, expectedNumberOfHolders, "Number Of Holders value mismatch: expected [" + expectedNumberOfHolders + "], but found [" + actualNumberOfHolders + "].");
        return this;
    }

    public FiSecurityFullProfile verifyTotalParHeld(String expectedTotalParHeld){
        String actualTotalParHeld = getTotalParHeld();
        Assert.assertEquals(actualTotalParHeld, expectedTotalParHeld, "Total Par Held value mismatch: expected [" + expectedTotalParHeld + "], but found [" + actualTotalParHeld + "].");
        return this;
    }

    public FiSecurityFullProfile verifyNumberOfBuyers(String expectedNumberOfBuyers){
        String actualNumberOfBuyers = getNumberOfBuyers();
        Assert.assertEquals(actualNumberOfBuyers, expectedNumberOfBuyers, "Number Of Buyers value mismatch: expected [" + expectedNumberOfBuyers + "], but found [" + actualNumberOfBuyers + "].");
        return this;
    }

    public FiSecurityFullProfile verifyNumberOfBuyIns(String expectedNumberOfBuyIns){
        String actualNumberOfBuyIns = getNumberOfBuyIns();
        Assert.assertEquals(actualNumberOfBuyIns, expectedNumberOfBuyIns, "Number Of Buy Ins value mismatch: expected [" + expectedNumberOfBuyIns + "], but found [" + actualNumberOfBuyIns + "].");
        return this;
    }

    public FiSecurityFullProfile verifyNumberOfSellers(String expectedNumberOfSellers){
        String actualNumberOfSellers = getNumberOfSellers();
        Assert.assertEquals(actualNumberOfSellers, expectedNumberOfSellers, "Number Of Sellers value mismatch: expected [" + expectedNumberOfSellers + "], but found [" + actualNumberOfSellers + "].");
        return this;
    }

    public FiSecurityFullProfile verifyNumberOfSellOuts(String expectedNumberOfSellOuts){
        String actualNumberOfSellOuts = getNumberOfSellOuts();
        Assert.assertEquals(actualNumberOfSellOuts, expectedNumberOfSellOuts, "Number Of Sell Outs value mismatch: expected [" + expectedNumberOfSellOuts + "], but found [" + actualNumberOfSellOuts + "].");
        return this;
    }



}
