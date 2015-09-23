package ipreomobile.ui.profiles.fullProfileTabs;

import ipreomobile.core.Driver;
import ipreomobile.core.Logger;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.ProfileTab;
import ipreomobile.ui.blocks.Carousel;
import ipreomobile.ui.blocks.KeyValueTable;
import ipreomobile.ui.securities.EqSecurityFullProfile;
import ipreomobile.ui.securities.FiSecurityFullProfile;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

public class BaseOwnershipProfileTab extends ProfileTab {
    private static final String TABLE_ROW_BY_ODD_EVEN_AND_ROW_XPATH         = new XPathBuilder().byClassName("row").byXpathPart(KeyValueTable.ODD_OR_EVEN_ROW_CLASS_CONDITION).build();
    private static final String TABLE_ROW_BY_ODD_AND_EVEN_XPATH             = new XPathBuilder().byTag("div").byXpathPart(KeyValueTable.ODD_OR_EVEN_ROW_CLASS_CONDITION).build();

    public static final String EQ = "Equity";
    public static final String FI = "Fixed Income";

    protected Carousel topBuySellCarousel = new Carousel();
    protected Carousel chartsEquityCarousel = new Carousel();

    private static final By CURRENT_HOLDINGS_PUBLIC_SELECTOR                = By.className("holdings-public");
    private static final By OWNERSHIP_SUMMARY_LOCATOR                       = By.className("summary-table");
    private static final By TOP_HOLDINGS_LOCATOR                            = By.className("top-values-table");

    private static final String TOP_BUY_SELL_CAROUSEL_XPATH                 = new XPathBuilder().byClassName("top-buy-sell-carousel").build();
    private static final String CHARTS_CAROUSEL_XPATH                       = new XPathBuilder().byClassName("equity-ownership-chart-carousel").build();

    private String currency = System.getProperty("test.currency") + "M";
    private String defaultTicker = System.getProperty("test.defaultTicker");

    protected KeyValueTable ownershipSummary = new KeyValueTable( TABLE_ROW_BY_ODD_AND_EVEN_XPATH );
    protected KeyValueTable topHoldings = new KeyValueTable( TABLE_ROW_BY_ODD_EVEN_AND_ROW_XPATH );
    protected KeyValueTable topBuySellTable = new KeyValueTable( TABLE_ROW_BY_ODD_EVEN_AND_ROW_XPATH );
    protected KeyValueTable defaultTickerHoldings = new KeyValueTable(
            TABLE_ROW_BY_ODD_AND_EVEN_XPATH,
            new XPathBuilder().byClassName("first-column").build(),
            new XPathBuilder().byClassName("last-column").build()
    );

    public BaseOwnershipProfileTab(){
        setupTopBuySellCarousel();

        setupDefaultTickerTable();
        setupOwnershipSummaryTable();
        setupTopHoldingsTable();
        setupTopBuySellTable();
    }

    private void setupDefaultTickerTable(){
       SenchaWebElement defaultTickerCurrentHoldingsContainer = Driver.findIfExists(CURRENT_HOLDINGS_PUBLIC_SELECTOR);
        Assert.assertNotNull(defaultTickerCurrentHoldingsContainer, getScreenName() + ": " + System.getProperty("test.defaultTicker") + " current holdings table is missing.");
        defaultTickerHoldings.setContainer(defaultTickerCurrentHoldingsContainer);
    }

    private void setupOwnershipSummaryTable(){
       SenchaWebElement ownershipSummaryContainer = Driver.findIfExists(OWNERSHIP_SUMMARY_LOCATOR);
        Assert.assertNotNull(ownershipSummaryContainer, getScreenName() + ": ownership summary table is missing.");
        defaultTickerHoldings.setContainer(ownershipSummaryContainer);
    }

    private void setupTopHoldingsTable(){
       SenchaWebElement topHoldingsContainer = Driver.findIfExists(TOP_HOLDINGS_LOCATOR);
        Assert.assertNotNull(topHoldingsContainer, getScreenName() + ": top holdings table is missing.");
        defaultTickerHoldings.setContainer(topHoldingsContainer);
    }

    private void setupTopBuySellTable(){
       SenchaWebElement topBuySellTableContainer = topBuySellCarousel.getActiveCarouselItem();
        Assert.assertNotNull(topBuySellTableContainer, getScreenName() + ": Top Buy / Sell table container is not found.");
        topBuySellTable.setContainer(topBuySellCarousel.getActiveCarouselItem());
    }

    protected void setupTopBuySellCarousel(){
        topBuySellCarousel.setContainerXpath(TOP_BUY_SELL_CAROUSEL_XPATH);
        topBuySellCarousel.setAnimationTimeout(2);
    }

    public BaseOwnershipProfileTab selectEquityTab(){
        selectTab("Equity");
        setupDefaultTickerTable();
        setupOwnershipSummaryTable();
        setupTopHoldingsTable();
        setupTopBuySellTable();
        waitReady();
        return this;
    }

    public BaseOwnershipProfileTab selectFixedIncomeTab(){
        selectTab("Fixed Income");
        setupTopHoldingsTable();
        setupTopBuySellTable();
        waitReady();
        return this;
    }

    public String getFirstEquityInTopHoldingsTable(){
        selectEquityTab();
        return topHoldings.getKey(1);
    }

    public BaseOwnershipProfileTab verifyPositionHistoryChart(){
        if (setupChartCarousel()) {
            chartsEquityCarousel.goToPage(0);
            verifyDiagram("Position History");
        }
        return this;
    }

    public BaseOwnershipProfileTab verifyEquityOwnershipChart(){
        if (setupChartCarousel()) {
            chartsEquityCarousel.goToPage(1);
            verifyDiagram("Equity Ownership");
        }
        return this;
    }

    public BaseOwnershipProfileTab verifyDefaultTicker(){
        verifyTickerInCurrentHoldingsTableHeader();
        verifyTickerInCurrentHoldingsTableEntries();
        return this;
    }

    public BaseOwnershipProfileTab verifyTickerInCurrentHoldingsTableHeader(){
        String tableHeader = defaultTicker + " Holdings";
        defaultTickerHoldings.verifyHeader(tableHeader);
        return this;
    }

    public BaseOwnershipProfileTab verifyTickerInCurrentHoldingsTableEntries() {
        defaultTickerHoldings.verifyKeyPresent("Current Position in " + defaultTicker + " - Value / Change");
        defaultTickerHoldings.verifyKeyPresent("Current Position in " + defaultTicker + " - Shares / Change");
        return this;
    }

    public BaseOwnershipProfileTab verifyCurrencyInTopHoldingsHeader(){
        String header = "Top Holdings ("+currency+")";
        String currentlyActiveTab = getSelectedTab();

        selectEquityTab();
        topHoldings.verifyHeader(header);

        selectFixedIncomeTab();
        topHoldings.verifyHeader(header);

        switch (currentlyActiveTab) {
            case EQ: selectEquityTab(); break;
            case FI: selectFixedIncomeTab(); break;
            default: throw new Error("Unknown Ownership tab: '"+currentlyActiveTab+"'.");
        }
        return this;
    }

    public BaseOwnershipProfileTab verifyCurrencyInOwnershipSummaryTable(){
        ownershipSummary.verifyKeyPresent("Portfolio Value("+currency+")");
        ownershipSummary.verifyKeyPresent("New Positions("+currency+")");
        ownershipSummary.verifyKeyPresent("Increased Positions("+currency+")");
        ownershipSummary.verifyKeyPresent("Decreased Positions("+currency+")");
        ownershipSummary.verifyKeyPresent("Unchanged Positions("+currency+")");
        return this;
    }

    public EqSecurityFullProfile openFirstEquityProfileFromTopHoldingsTable(){
        selectEquityTab();
        clickFirstSecurityInTopHoldingsTable();
        return new EqSecurityFullProfile();
    }

    public EqSecurityFullProfile openEquityProfileFromTopHoldingsTable(String equityName){
        selectEquityTab();
        topHoldings.clickKeyCell(equityName);
        return new EqSecurityFullProfile();
    }

    public FiSecurityFullProfile openFirstFixedIncomeProfileFromTopHoldingsTable(){
        selectFixedIncomeTab();
        clickFirstSecurityInTopHoldingsTable();
        return new FiSecurityFullProfile();
    }

    public BaseOwnershipProfileTab clickFirstSecurityInTopHoldingsTable(){
        topHoldings.clickKeyCell(1);
        return this;
    }

    public String getFirstFiInTopHoldingsTable(){
        selectFixedIncomeTab();
        return topHoldings.getKey(1);
    }

    public FiSecurityFullProfile openFixedIncomeProfileFromTopHoldingsTable(String fixedIncomeName){
        selectFixedIncomeTab();
        topHoldings.clickKeyCell(fixedIncomeName);
        return new FiSecurityFullProfile();
    }

    public BaseOwnershipProfileTab verifyCurrencyInTopBuySellTableHeader(String header){
        selectTopBuySellTable(header);
        topBuySellTable.verifyHeader(header + " (" +currency+")");
        return this;
    }

    public String getFirstCompanyNameInTopBuysAndSellsTable(){
        if (getSelectedTab().equals(FI)) {
            throw new Error("Company names are listed in Top Buy/Sell table " +
                    "on Equity sub-tab only. Fixed Income sub-tab contains Security names.");
        }
        return topBuySellTable.getKey(1);
    }

    public String getFirstSecurityNameInTopBuysAndSellsTable(){
        if (getSelectedTab().equals(EQ)) {
            throw new Error("Security names are listed in Top Buy/Sell table " +
                    "on Fixed Income sub-tab only. Equity sub-tab contains Company names.");
        }
        return topBuySellTable.getKey(1);
    }

    public EqSecurityFullProfile openFirstEquityProfileFromTopBuysAndSellsTable(){
        clickFirstSecurityInTopBuysAndSellsTable();
        return new EqSecurityFullProfile();
    }

    public FiSecurityFullProfile openFirstFixedIncomeProfileFromTopBuysAndSellsTable(){
        clickFirstSecurityInTopBuysAndSellsTable();
        return new FiSecurityFullProfile();
    }

    public BaseOwnershipProfileTab clickFirstSecurityInTopBuysAndSellsTable(){
        topBuySellTable.clickKeyCell(1);
        return this;
    }

    public BaseOwnershipProfileTab verifyFixedIncomePortfolioChart(){
        verifyDiagram("Fixed Income Portfolio");
        verifyDiagramLegend("Fixed Income Portfolio");
        return this;
    }

    public BaseOwnershipProfileTab selectTopBuySellTable(String expectedHeader) {
        expectedHeader = expectedHeader + " ("+currency+")";
        String currentHeader = getActiveTopBuySellTableHeader();
        if (!currentHeader.equalsIgnoreCase(expectedHeader)) {
            topBuySellCarousel.goToPage(0);
            while (!getActiveTopBuySellTableHeader().equalsIgnoreCase(expectedHeader)) {
                if (topBuySellCarousel.hasNext()) {
                    topBuySellCarousel.goForward();
                } else {
                    throw new Error("Could not reach '"+expectedHeader+"' table using Carousel: no such table found.");
                }
            }
        }
        topBuySellTable.setContainer(topBuySellCarousel.getActiveCarouselItem());
        return this;
    }

    protected boolean setupChartCarousel(){
        selectEquityTab();
        if (Driver.findIfExists(By.xpath(CHARTS_CAROUSEL_XPATH)) != null) {
            chartsEquityCarousel.setContainerXpath(CHARTS_CAROUSEL_XPATH);
            chartsEquityCarousel.setAnimationTimeout(2);
            return true;
        } else {
            Logger.logError("Cannot initialize Chart table: no chart carousel found.");
            return false;
        }
    }

    protected String getActiveTopBuySellTableHeader(){
       SenchaWebElement container = topBuySellCarousel.getActiveCarouselItem();
        topBuySellTable.setContainer(container);
        return topBuySellTable.getHeader().toUpperCase();
    }

}
