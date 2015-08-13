package ipreomobile.ui.profiles.fullProfileTabs;

import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.securities.EqSecurityFullProfile;
import ipreomobile.ui.securities.FiSecurityFullProfile;
import org.openqa.selenium.By;
import org.testng.Assert;

public class BaseCurrentHoldingsProfileTab extends BaseHoldingsAndHoldersProfileTab {

    private static final By SEARCH_FIELD_LOCATOR = By.xpath(new XPathBuilder().byAttribute("placeholder", "Symbol / Security Name").build());
    private static final String ITEMS_XPATH      = new XPathBuilder().byClassName("list-detailed-item").build();
    private static final String EQUITY_SYMBOL_AS_SUBTEXT_XPATH = new XPathBuilder()
            .byClassName("details")
            .byTag("li").withTextContains("Symbol")
            .byTag("span").withClassName("value")
            .build();

    private static final String EQUITY_SUB_TAB = "Equity";
    private static final String FIXED_INCOME_SUB_TAB = "Fixed Income";

    public BaseCurrentHoldingsProfileTab(){
        super();
        setContainerClass("holdings-list");
        setSearchFieldSelector(SEARCH_FIELD_LOCATOR);
    }

    public boolean securityPresent(String securityName){
        return itemsList.getItem(securityName) != null;
    }

    public BaseCurrentHoldingsProfileTab selectEquityTab(){
        selectTab(EQUITY_SUB_TAB);
        initializeItemsList();
        return this;
    }

    public BaseCurrentHoldingsProfileTab selectFixedIncomeTab(){
        selectTab(FIXED_INCOME_SUB_TAB);
        initializeItemsList();
        return this;
    }

    public BaseCurrentHoldingsProfileTab filterBySymbolOrSecurityName(String filter) {
        setSearchFilter(filter);
        waitReady();
        return this;
    }

    public BaseCurrentHoldingsProfileTab clearFilterBySymbolOrSecurityName() {
        setSearchFilter("");
        waitReady();
        return this;
    }

    public BaseCurrentHoldingsProfileTab verifySecurityPresent(String securityName) {
        filterBySymbolOrSecurityName(securityName);
        itemsList.verifyItemPresence(securityName);
        return this;
    }

    public BaseCurrentHoldingsProfileTab verifySecurityAbsent(String securityName) {
        filterBySymbolOrSecurityName(securityName);
        Assert.assertTrue(isResultSetEmpty(), "Security '"+securityName+"' was found in the " + getSelectedTab() + " security list, while should not. Expected result set to be empty after filtering.");
        return this;
    }

    public EqSecurityFullProfile selectEquitySecurity(String securityName) {
        selectEquityTab();
        return openEquitySecurityProfile(securityName);
    }

    public EqSecurityFullProfile selectEquitySecurity(String securityName, String tickerName) {
        selectEquityTab();
        return openEquitySecurityProfileBySecurityNameAndTicker(securityName, tickerName);
    }

    public FiSecurityFullProfile selectFixedIncomeSecurity(String securityName) {
        selectFixedIncomeTab();
        return openFixedIncomeSecurityProfile(securityName);
    }

    public EqSecurityFullProfile openEquitySecurityProfile(String securityName) {
        itemsList.select(securityName);
        return new EqSecurityFullProfile();
    }

//    public EqSecurityFullProfile openEquitySecurityProfile(String securityName, String marketName) {
//        itemsList.select(securityName, marketName);
//        return new EqSecurityFullProfile();
//    }

    public EqSecurityFullProfile openEquitySecurityProfileBySecurityNameAndTicker(String securityName, String tickerName) {
        String currentSubtextXpath = itemsList.getSubtextXpath();
        itemsList.setSubtextXpath(EQUITY_SYMBOL_AS_SUBTEXT_XPATH);
        itemsList.select(securityName, tickerName);
        itemsList.setSubtextXpath(currentSubtextXpath);
        return new EqSecurityFullProfile();
    }

    public FiSecurityFullProfile openFixedIncomeSecurityProfile(String securityName) {
        itemsList.select(securityName);
        return new FiSecurityFullProfile();
    }

    public BaseCurrentHoldingsProfileTab verifyEquitySecurityDetailsPresent(String securityName) {
        Assert.assertNotEquals(getEquitySymbol(securityName), "", "No 'Symbol' value found for equity '"+securityName+"':");
        Assert.assertNotEquals(getEquityValue(securityName), "", "No 'Value' value found for equity '"+securityName+"':");
        Assert.assertNotEquals(getEquityValueChange(securityName), "", "No 'Value Change' value found for equity '"+securityName+"':");
        Assert.assertNotEquals(getEquityShares(securityName), "", "No 'Shares' value found for equity '"+securityName+"':");
        Assert.assertNotEquals(getEquityPercentPortfolio(securityName), "", "No '%Portfolio' value found for equity '"+securityName+"':");
        Assert.assertNotEquals(getEquityPercentSO(securityName), "", "No '%SO' value found for equity '"+securityName+"':");
        return this;
    }

    public BaseCurrentHoldingsProfileTab verifyFixedIncomeSecurityDetailsPresent(String securityName) {
        Assert.assertNotEquals(getFixedIncomeParChange(securityName), "", "No 'Par Change' value was found for fixed income '"+securityName+"':");
        Assert.assertNotEquals(getFixedIncomeParHeld(securityName), "", "No 'Par Held' value was found for fixed income '"+securityName+"':");
        Assert.assertNotEquals(getFixedIncomeValue(securityName), "", "No 'Value' value was found for fixed income '"+securityName+"':");
        Assert.assertNotEquals(getFixedIncomePercentFiPort(securityName), "", "No '%FI Port' value was found for fixed income '"+securityName+"':");
        Assert.assertNotEquals(getFixedIncomePositionDate(securityName), "", "No 'Position Date' value was found for fixed income '"+securityName+"':");
        return this;
    }

    public String getEquitySymbol(String securityName){
        verifyActiveTab(EQUITY_SUB_TAB);
        return getProperty(securityName, "Symbol:");
    }

    public void verifyEquitySymbol(String securityName, String expectedValue){
        Assert.assertEquals(getEquitySymbol(securityName), expectedValue, "Equity Symbol mismatch: ");
    }

    public String getEquityValue(String securityName){
        verifyActiveTab(EQUITY_SUB_TAB);
        return getProperty(securityName, "Value:");
    }
    public void verifyEquityValue(String securityName, String expectedValue){
        Assert.assertEquals(getEquityValue(securityName), expectedValue, "Equity Value mismatch: ");
    }

    public String getEquityValueChange(String securityName){
        verifyActiveTab(EQUITY_SUB_TAB);
        return getProperty(securityName, "Value Change:");
    }
    public void verifyEquityValueChange(String securityName, String expectedValue){
        Assert.assertEquals(getEquityValueChange(securityName), expectedValue, "Equity Value Change mismatch: ");
    }

    public String getEquityShares(String securityName){
        verifyActiveTab(EQUITY_SUB_TAB);
        return getProperty(securityName, "Shares:");
    }
    public void verifyEquityShares(String securityName, String expectedValue){
        Assert.assertEquals(getEquityShares(securityName), expectedValue, "Equity Shares mismatch: ");
    }

    public String getEquityPercentPortfolio(String securityName){
        verifyActiveTab(EQUITY_SUB_TAB);
        return getProperty(securityName, "% Portfolio:");
    }
    public void verifyEquityPercentPortfolio(String securityName, String expectedValue){
        Assert.assertEquals(getEquityPercentPortfolio(securityName), expectedValue, "Equity % Portfolio mismatch: ");
    }

    public String getEquityPercentSO(String securityName){
        verifyActiveTab(EQUITY_SUB_TAB);
        return getProperty(securityName, "% S/O:");
    }
    public void verifyEquityPercentSO(String securityName, String expectedValue){
        Assert.assertEquals(getEquityPercentSO(securityName), expectedValue, "Equity % S/O mismatch: ");
    }

    public String getFixedIncomeParHeld(String securityName){
        verifyActiveTab(FIXED_INCOME_SUB_TAB);
        return getProperty(securityName, "Par Held:");
    }
    public void verifyFixedIncomeParHeld(String securityName, String expectedValue){
        Assert.assertEquals(getFixedIncomeParHeld(securityName), expectedValue, "Fixed Income Par Held mismatch: ");
    }

    public String getFixedIncomeParChange(String securityName){
        verifyActiveTab(FIXED_INCOME_SUB_TAB);
        return getProperty(securityName, "Par Change:");
    }
    public void verifyFixedIncomeParChange(String securityName, String expectedValue){
        Assert.assertEquals(getFixedIncomeParChange(securityName), expectedValue, "Fixed Income Par Change mismatch: ");
    }

    public String getFixedIncomeValue(String securityName){
        verifyActiveTab(FIXED_INCOME_SUB_TAB);
        return getProperty(securityName, "Value:");
    }
    public void verifyFixedIncomeValue(String securityName, String expectedValue){
        Assert.assertEquals(getFixedIncomeValue(securityName), expectedValue, "Fixed Income Value mismatch: ");
    }

    public String getFixedIncomePercentFiPort(String securityName){
        verifyActiveTab(FIXED_INCOME_SUB_TAB);
        return getProperty(securityName, "% FI Port:");
    }
    public void verifyFixedIncomePercentFiPort(String securityName, String expectedValue){
        Assert.assertEquals(getFixedIncomePercentFiPort(securityName), expectedValue, "Fixed Income % FI Port mismatch: ");
    }

    public String getFixedIncomePositionDate(String securityName){
        verifyActiveTab(FIXED_INCOME_SUB_TAB);
        return getProperty(securityName, "Position Date:");
    }
    public void verifyFixedIncomePositionDate(String securityName, String expectedValue){
        Assert.assertEquals(getFixedIncomePositionDate(securityName), expectedValue, "Fixed Income Position Date mismatch: ");
    }

}
