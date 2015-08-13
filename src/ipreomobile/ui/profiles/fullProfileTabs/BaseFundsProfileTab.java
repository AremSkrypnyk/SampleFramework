package ipreomobile.ui.profiles.fullProfileTabs;

import ipreomobile.core.*;
import ipreomobile.templates.ui.ProfileTab;
import ipreomobile.templates.ui.ProfileTabList;
import ipreomobile.ui.funds.FundFullProfile;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;

public class BaseFundsProfileTab extends ProfileTab {
    private static final String ITEM_NAME_XPATH = new XPathBuilder().byClassName("header").build();
    private static final String CONTAINER_CLASS = "x-list-inner";

    private static final String DETAILS_CLASS = "details";
    private static final String MANAGED_BY_XPATH = new XPathBuilder().byClassName("managedby").build();
    private static final String NORMAL_PURCHASING_POWER_XPATH =  new XPathBuilder().byClassName(DETAILS_CLASS).byClassName("ppValue").build();
    private static final String NORMAL_PURCHASING_POWER_SHARES_XPATH =  new XPathBuilder().byClassName(DETAILS_CLASS).byClassName("ppShares").build();
    private static final String SUITABILITY_XPATH =  new XPathBuilder().byClassName(DETAILS_CLASS).byClassName("suitability").build();

    private static final String VALUES_CLASS = "values";
    private static final String SHARES_XPATH = new XPathBuilder().byClassName(VALUES_CLASS).byClassNameEquals("shares").build();
    private static final String SHARES_VALUE_XPATH = new XPathBuilder().byClassName(VALUES_CLASS).byTag("span").withClassNameEquals("shares-value").build();
    private static final String SHARES_VALUE_CHANGE_XPATH = new XPathBuilder().byClassName(VALUES_CLASS).byTag("p").withClassNameEquals("shares-value").withChildTag("*").withClassName("change-arrow").build();

    private static final String FUND_DETAILS_XPATH = new XPathBuilder().byClassName(DETAILS_CLASS).build();

    private static final String CURRENCY_SUFFIX = "(" + System.getProperty("test.currency") + "M)";
    private static final String DEFAULT_TICKER_SUFFIX = "(" + System.getProperty("test.defaultTicker") + ")";

    private static final String EQUITY_AUM_XPATH = new XPathBuilder().byTag("li").withTextContains("Equity AUM").build();
    private static final String CURRENCY = System.getProperty("test.currency") + "M";

    protected FundsProfileTabList fundsList;

    class FundsProfileTabList extends ProfileTabList {

        @Override
        protected SenchaWebElement getItemByXpath(String xpath)  {
            setListContainer(getActiveListContainer());
            return super.getItemByXpath(xpath);
        }

        public void verifyHoldersFundsSorting(int limit) {
            verifySortingBy("Holders", limit);
        }

        public void verifyOtherFundsSorting(int limit) {
            verifySortingBy("Other", limit);
        }

        private void verifySortingBy(String fundType, int limit){
            SenchaWebElement nextItem;
            String firstItemXpath = getItemsXpath()+"[1]";
            SenchaWebElement currentItem = getItemByXpath(firstItemXpath);

            for(int i=0; i < limit - 1; i++) {
                nextItem = getNextItem(currentItem);
                if (nextItem == null) {
                    return;
                }
                switch(fundType) {
                    case "Holders":
                        compareItemsByDefaultTickerValue(currentItem, nextItem);
                        break;
                    case "Other":
                        compareItemsByEquityAUM(currentItem, nextItem);
                        break;
                    default:
                        Logger.logError("Invalid fund type provided: expected 'Holders' or 'Other'.");
                }
                currentItem = nextItem;
                i++;
            }
        }

        private void compareItemsByDefaultTickerValue(SenchaWebElement firstItem, SenchaWebElement secondItem) {
            setSubtextXpath(SHARES_VALUE_XPATH);
            double tickerValue1 = getDefaultTickerValue(firstItem);
            double tickerValue2 = getDefaultTickerValue(secondItem);
            Verify.verifyMoreOrEquals(tickerValue1, tickerValue2, "Sorting by default ticker value "+DEFAULT_TICKER_SUFFIX+" is wrong on Holders Funds tab:");
        }

        private double getDefaultTickerValue(SenchaWebElement item) {
            String value = getItemSubtext(item);
            Verify.verifyContainsText(value, CURRENCY_SUFFIX, "Currency not found in fund details:");
            value = value.split(" \\(")[0].trim().replaceAll(",", "");
            return Double.parseDouble(value);
        }


        private void compareItemsByEquityAUM(SenchaWebElement firstItem, SenchaWebElement secondItem){
            double aumValue1 = getEquityAUMValue(firstItem);
            double aumValue2 = getEquityAUMValue(secondItem);
            Verify.verifyMoreOrEquals(aumValue1, aumValue2, "Sorting by Equity AUM (Assets Under Management) is wrong on Other Funds tab:");
        }

        private double getEquityAUMValue(SenchaWebElement item) {
            String value = Driver.findVisible(By.xpath(EQUITY_AUM_XPATH), item).getText();
            Verify.verifyContainsText(value, CURRENCY, "Currency not found in fund details:");
            String suffix = value.split(": ")[1].trim().replaceAll(",", "");
            return Double.parseDouble(suffix);
        }
    }

    public BaseFundsProfileTab(){
        fundsList = new FundsProfileTabList();
        fundsList.setItemNameXpath(ITEM_NAME_XPATH);
    }

    public FundFullProfile openFundProfile(String fundProfileName) {
        fundsList.select(fundProfileName);
        return new FundFullProfile();
    }

    public BaseFundsProfileTab selectHoldersTab(){
        selectTab("Holders");
        fundsList = new FundsProfileTabList();
        fundsList.setItemNameXpath(ITEM_NAME_XPATH);
        return this;
    }

    public BaseFundsProfileTab selectOtherTab(){
        selectTab("Other");
        fundsList = new FundsProfileTabList();
        fundsList.setItemNameXpath(ITEM_NAME_XPATH);
        return this;
    }

    public BaseFundsProfileTab verifyFundUnavailableInOffline(String fundProfileName){
        Assert.assertTrue(isFundUnavailableInOffline(fundProfileName), "Fund '" + fundProfileName +"' is available while expected to be unavailable in offline mode.");
        return this;
    }

    public BaseFundsProfileTab verifyFundAvailableInOffline(String fundProfileName){
        Assert.assertTrue(!isFundUnavailableInOffline(fundProfileName), "Fund '" + fundProfileName +"' is available while expected to be unavailable in offline mode.");
        return this;
    }

	public boolean isFundUnavailableInOffline(String fundProfileName){
        return fundsList.isItemUnavailableInOfflineMode(fundProfileName);
    }
    public BaseFundsProfileTab selectFundUnavailableOffline() {
        fundsList.selectItemInOfflineMode(false);
        return this;
    }

    public FundFullProfile selectFundAvailableOffline() {
        fundsList.selectItemInOfflineMode(true);
        return new FundFullProfile();
    }

    public BaseFundsProfileTab verifyHoldersFundsSortingOrder(int numberOfFundsToCheck){
        selectHoldersTab();
        fundsList.verifyHoldersFundsSorting(numberOfFundsToCheck);
        return this;
    }

    public BaseFundsProfileTab verifyOtherFundsSortingOrder(int numberOfFundsToCheck){
        selectOtherTab();
        fundsList.verifyOtherFundsSorting(numberOfFundsToCheck);
        return this;
    }

    public BaseFundsProfileTab verifyCurrencyInHoldersFundDetails(String fundName){
        String propertyValue;
        selectHoldersTab();
        propertyValue = getNormalPurchasingPower(fundName);
        Assert.assertTrue(propertyValue.endsWith(CURRENCY_SUFFIX),
                "Currency check failed. Found 'Normal Purchasing Power' value ["+propertyValue+"], but expected it to end with '"+CURRENCY_SUFFIX+"': ");
        propertyValue = getSharesValue(fundName);
        Assert.assertTrue(propertyValue.endsWith(CURRENCY_SUFFIX),
                "Currency check failed. Found 'Shares Value' value ["+propertyValue+"], but expected it to end with '"+CURRENCY_SUFFIX+"': ");
        propertyValue = getSharesValueChange(fundName);
        Assert.assertTrue(propertyValue.endsWith(CURRENCY_SUFFIX),
                "Currency check failed. Expected 'Shares Value Change' value ["+propertyValue+"], but expected it to end with '"+CURRENCY_SUFFIX+"': ");
        return this;
    }

    public BaseFundsProfileTab verifyCurrencyInOtherFundDetails(String fundName) {
        String detailsValue;
        selectOtherTab();
        detailsValue = getFundDetails(fundName);
        Assert.assertTrue(detailsValue.contains("Equity AUM "+CURRENCY_SUFFIX),
                "Currency check failed. Found fund details ["+detailsValue+"]. Expected 'Equity AUM' label to end with '"+CURRENCY_SUFFIX+"':");
        return this;
    }

    public BaseFundsProfileTab verifyDefaultTickerInHoldersFundDetails(String fundName) {
        selectHoldersTab();
        fundsList.setSubtextXpath(new XPathBuilder().byClassName(VALUES_CLASS).build());
        String fundValues = fundsList.getItemSubtext(fundName);
        Assert.assertTrue(fundValues.contains("Value "+ DEFAULT_TICKER_SUFFIX),
                "Ticker check failed. Found fund details ["+fundValues+"]. Expected 'Shares Value' label to end with '"+DEFAULT_TICKER_SUFFIX+"': ");
        Assert.assertTrue(fundValues.contains("Value Change "+ DEFAULT_TICKER_SUFFIX),
                "Ticker check failed. Found fund details ["+fundValues+"]. Expected 'Shares Value' label to end with '"+DEFAULT_TICKER_SUFFIX+"': ");
        return this;
    }

    public BaseFundsProfileTab verifyHoldersFundDetailsPresent(String fundName) {
        Assert.assertNotNull(getManagedBy(fundName), "No information was found in 'Managed By' field for fund '" + fundName);
        Assert.assertNotNull(getNormalPurchasingPower(fundName), "No information was found in 'Normal Purchasing Power' field for fund '" + fundName);
        Assert.assertNotNull(getNormalPurchasingPowerShares(fundName), "No information was found in 'Normal Purchasing Power Shares' field for fund '" + fundName);
        Assert.assertNotNull(getShares(fundName), "No information was found in 'Shares' field for fund '" + fundName);
        Assert.assertNotNull(getSharesValue(fundName), "No information was found in 'Shares value (default ticker)' field for fund '" + fundName);
        Assert.assertNotNull(getSharesValueChange(fundName), "No information was found in 'Shares value change (default ticker)' field for fund '"+fundName);
        return this;
    }

    public BaseFundsProfileTab verifyOtherFundDetailsPresent(String fundName) {
        Assert.assertNotNull(getManagedBy(fundName), "No information was found in 'Managed By' field for fund '" + fundName);
        Assert.assertNotNull(getFundDetails(fundName), "No information was found in 'Details' section for fund '"+fundName);
        return this;
    }

    public String getManagedBy(String fundName) {
        return getPropertyValue(fundName, MANAGED_BY_XPATH);
    }

    public String getNormalPurchasingPower(String fundName){
        selectHoldersTab();
        return getPropertyValue(fundName, NORMAL_PURCHASING_POWER_XPATH);
    }

    public String getNormalPurchasingPowerShares(String fundName){
        selectHoldersTab();
        return getPropertyValue(fundName, NORMAL_PURCHASING_POWER_SHARES_XPATH);
    }

    public String getSuitability(String fundName){
        selectHoldersTab();
        return getPropertyValue(fundName, SUITABILITY_XPATH);
    }

    public String getShares(String fundName){
        selectHoldersTab();
        return getPropertyValue(fundName, SHARES_XPATH);
    }

    public String getSharesValue(String fundName){
        selectHoldersTab();
        return getPropertyValue(fundName, SHARES_VALUE_XPATH);
    }

    public String getSharesValueChange(String fundName){
        selectHoldersTab();
        return getPropertyValue(fundName, SHARES_VALUE_CHANGE_XPATH);
    }

    public String getFundDetails(String fundName){
        selectOtherTab();
        return getPropertyValue(fundName, FUND_DETAILS_XPATH);
    }

    private SenchaWebElement getActiveListContainer(){
        return Driver.findVisible(By.className(CONTAINER_CLASS));
    }

    protected String getPropertyValue(String fundName, String propertyXpath) {
        String value = "";
        fundsList.setSubtextXpath(propertyXpath);
        try {
            value = fundsList.getItemSubtext(fundName);
        } catch (NoSuchElementException e) {
            Logger.logError("Cannot find property by XPath '"+propertyXpath+"'.");
        }
        return value;
    }

}

