package ipreomobile.data.billfoldData.blocks.widgets;

import ipreomobile.core.Driver;
import ipreomobile.core.SenchaWebElement;
import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.ScreenCard;
import org.openqa.selenium.By;

public class EquityOwnershipWidget extends ScreenCard {

    private static final String EQUITY_OWNERSHIP_WIDGET_TITLE_XPATH = new XPathBuilder().byIdContains("ucEC_tio").build();
    private static final String DEFAULT_TICKER_HOLDINGS_TABLE_XPATH = new XPathBuilder().byIdContains("_tblOwnershipSummaryIR").build();
    private static final String OWNERSHIP_SUMMARY_TABLE_XPATH = new XPathBuilder().byIdContains("_ucOS_tbl").build();
    private static final String TOP_HOLDINGS_TABLE_XPATH = new XPathBuilder().byIdContains("_ucTH_ps").build();
    private static final String TOP_BUYS_AND_SELLS_TABLE_XPATH = new XPathBuilder().byIdContains("_ucOBIR_tdOSC").build();

    private static final String CURRENT_POSITION_IN_DEFAULT_TICKER_VALUE_CHANGE = new XPathBuilder().byTag("tr").withIdContains("_trValue").byClassName("fieldValue").build();
    private static final String CURRENT_POSITION_IN_DEFAULT_TICKER_SHARES_CHANGE = new XPathBuilder().byTag("tr").withIdContains("_trShares").byClassName("fieldValue").build();
    private static final String PERCENT_OF_PORTFOLIO = new XPathBuilder().byIdContains("_trPort").withChildTextContains("of Portfolio").byClassName("fieldValue").build();
    private static final String PERCENT_OF_SHARES_OUTSTANDING = new XPathBuilder().byIdContains("_trSharesOut").byClassName("fieldValue").build();
    private static final String DEFAULT_TICKER_RANK_IN_PORTFOLIO = new XPathBuilder().byIdContains("_trPortRank").byClassName("fieldValue").build();
    private static final String HOLDER_RANK = new XPathBuilder().byIdContains("_trHolderRank").byClassName("fieldValue").build();
    private static final String INDUSTRY_VALUE_CHANGE = new XPathBuilder().byIdContains("_trIndusrty").byClassName("fieldValue").build();
    private static final String PEER_VALUE_CHANGE = new XPathBuilder().byIdContains("_trPeer").byClassName("fieldValue").build();


    private static final String OWNERSHIP_SUMMARY_TABLE_VALUE_TEMPLATE = new XPathBuilder().byTag("tr").withChildTag("td").withTextContains("%s").byTag("td").byIndex(2).build();

    private static final String NUMBER_OF_HOLDINGS = "# of Holdings";
    private static final String PORTFOLIO_VALUE = "Portfolio Value ";
    private static final String NEW_POSITIONS = "New Positions ";
    private static final String INCREASED_POSITIONS = "Increased Positions ";
    private static final String DECREASED_POSITIONS = "Decreased Positions ";
    private static final String UNCHANGED_POSITIONS = "Unchanged Positions ";

    private static final String PERCENT_PORT = new XPathBuilder().byTag("tr").withChildTextContains("%s").byTag("td").byIndex(2).build();
    private static final String PERCENT_CHG = new XPathBuilder().byTag("tr").withChildTextContains("%s").byTag("td").byIndex(3).build();

    private static final String VALUE_CHANGE = new XPathBuilder().byTag("tr").withChildTextContains("%s").byTag("td").byIndex(2).build();
    private static final String SHARES_CHANGE = new XPathBuilder().byTag("tr").withChildTextContains("%s").byTag("td").byIndex(3).build();

    private SenchaWebElement holdingsTable;
    private SenchaWebElement ownershipSummaryTable;
    private SenchaWebElement topHoldingsTable;
    private SenchaWebElement topBuysAndSellsTable = null;

    public EquityOwnershipWidget(){
        addCheckpointElement(By.xpath(EQUITY_OWNERSHIP_WIDGET_TITLE_XPATH));
        waitReady();
    }

    private SenchaWebElement getHoldingsTable(){
        return (holdingsTable != null) ? holdingsTable : Driver.findVisible(By.xpath(DEFAULT_TICKER_HOLDINGS_TABLE_XPATH));
    }

    public String getCurrentPositionInDefaultTickerValueChange(){
        SenchaWebElement currentPositionInDefaultTickerValueChange = Driver.findVisible(By.xpath(CURRENT_POSITION_IN_DEFAULT_TICKER_VALUE_CHANGE), getHoldingsTable());
        return (currentPositionInDefaultTickerValueChange != null) ? currentPositionInDefaultTickerValueChange.getText().trim() : null;
    }

    public String getCurrentPositionInDefaultTickerSharesChange(){
        SenchaWebElement currentPositionInDefaultTickerSharesChange = Driver.findVisible(By.xpath(CURRENT_POSITION_IN_DEFAULT_TICKER_SHARES_CHANGE), getHoldingsTable());
        return (currentPositionInDefaultTickerSharesChange != null) ? currentPositionInDefaultTickerSharesChange.getText().trim() : null;
    }

    public String getPercentOfPortfolio(){
        SenchaWebElement percentOfPortfolio = Driver.findVisible(By.xpath(PERCENT_OF_PORTFOLIO), getHoldingsTable());
        return (percentOfPortfolio != null) ? percentOfPortfolio.getText().trim() : null;
    }

    public String getPercentOfSharesOutstanding(){
        SenchaWebElement percentOfSharesOutstanding = Driver.findVisible(By.xpath(PERCENT_OF_SHARES_OUTSTANDING), getHoldingsTable());
        return (percentOfSharesOutstanding != null) ? percentOfSharesOutstanding.getText().trim() : null;
    }

    public String getDefaultTickerRankInPortfolio(){
        SenchaWebElement defaultTickerRankInPortfolio = Driver.findVisible(By.xpath(DEFAULT_TICKER_RANK_IN_PORTFOLIO), getHoldingsTable());
        return (defaultTickerRankInPortfolio != null)? defaultTickerRankInPortfolio.getText().trim() : null;
    }

    public String getHolderRank(){
        SenchaWebElement holderRank = Driver.findVisible(By.xpath(HOLDER_RANK), getHoldingsTable());
        return (holderRank != null) ? holderRank.getText().trim() : null;
    }

    public String getIndustryValueChange(){
        SenchaWebElement industryValueChange = Driver.findVisible(By.xpath(INDUSTRY_VALUE_CHANGE), getHoldingsTable());
        return (industryValueChange != null) ? industryValueChange.getText().trim() : null;
    }

    public String getPeerValueChange(){
        SenchaWebElement peerValueChange = Driver.findVisible(By.xpath(PEER_VALUE_CHANGE), getHoldingsTable());
        return (peerValueChange != null) ? peerValueChange.getText().trim() : null;
    }

    private SenchaWebElement getOwnershipSummaryTable(){
        return (ownershipSummaryTable != null) ? ownershipSummaryTable : Driver.findVisible(By.xpath(OWNERSHIP_SUMMARY_TABLE_XPATH));
    }

    public String getNumberOfHoldings(){
        SenchaWebElement numberOfHoldings = Driver.findVisible(By.xpath(String.format(OWNERSHIP_SUMMARY_TABLE_VALUE_TEMPLATE, NUMBER_OF_HOLDINGS)), getOwnershipSummaryTable());
        return (numberOfHoldings != null) ? numberOfHoldings.getText().trim() : null;
    }

    public String getPortfolioValue(){
        SenchaWebElement portfolioValue = Driver.findVisible(By.xpath(String.format(OWNERSHIP_SUMMARY_TABLE_VALUE_TEMPLATE, PORTFOLIO_VALUE)), getOwnershipSummaryTable());
        return (portfolioValue != null) ? portfolioValue.getText().trim() : null;
    }

    public String getNewPositions(){
        SenchaWebElement newPositions = Driver.findVisible(By.xpath(String.format(OWNERSHIP_SUMMARY_TABLE_VALUE_TEMPLATE, NEW_POSITIONS)), getOwnershipSummaryTable());
        return (newPositions != null) ? newPositions.getText().trim() : null;
    }

    public String getIncreasedPositions(){
        SenchaWebElement increasedPositions = Driver.findVisible(By.xpath(String.format(OWNERSHIP_SUMMARY_TABLE_VALUE_TEMPLATE, INCREASED_POSITIONS)), getOwnershipSummaryTable());
        return (increasedPositions != null) ? increasedPositions.getText().trim() : null;
    }

    public String getDecreasedPositions(){
        SenchaWebElement decreasedPositions = Driver.findVisible(By.xpath(String.format(OWNERSHIP_SUMMARY_TABLE_VALUE_TEMPLATE, DECREASED_POSITIONS)), getOwnershipSummaryTable());
        return (decreasedPositions != null) ? decreasedPositions.getText().trim() : null;
    }

    public String getUnchangedPositions(){
        SenchaWebElement unchangedPositions = Driver.findVisible(By.xpath(String.format(OWNERSHIP_SUMMARY_TABLE_VALUE_TEMPLATE, UNCHANGED_POSITIONS)), getOwnershipSummaryTable());
        return (unchangedPositions != null) ? unchangedPositions.getText().trim() : null;
    }

    private SenchaWebElement getTopHoldingsTable(){
        return (topHoldingsTable != null) ? topHoldingsTable : Driver.findVisible(By.xpath(TOP_HOLDINGS_TABLE_XPATH));
    }

    public String getInstitutionNameFromTopHoldingsTable(int index){
        String INSTITUTION_NAME_FROM_TOP_HOLDINGS_TABLE_XPATH = new XPathBuilder().byTag("tr").byIndex(index).withChildTag("a").byTag("a").build();
        return Driver.findVisible(By.xpath(INSTITUTION_NAME_FROM_TOP_HOLDINGS_TABLE_XPATH), getTopHoldingsTable()).getText().trim();
    }

    public String getPercentPort(int index){
        SenchaWebElement percentPort = Driver.findVisible(By.xpath(String.format(PERCENT_PORT, getInstitutionNameFromTopHoldingsTable(index))), getTopHoldingsTable());
        return (percentPort != null) ? percentPort.getText().trim() : null;
    }

    public String getPercentChg(int index){
        SenchaWebElement percentChg = Driver.findVisible(By.xpath(String.format(PERCENT_CHG, getInstitutionNameFromTopHoldingsTable(index))), getTopHoldingsTable());
        return (percentChg != null) ? percentChg.getText().trim() : null;
    }

    private SenchaWebElement getTopBuysAndSellsTable(){
        return (topBuysAndSellsTable != null) ? topBuysAndSellsTable : Driver.findVisible(By.xpath(TOP_BUYS_AND_SELLS_TABLE_XPATH));
    }

    public String getInstitutionNameFromTopBuysAndSellsTable(int index){
        String INSTITUTION_NAME_FROM_TOP_BUYS_AND_SELLS_TABLE_XPATH = new XPathBuilder().byTag("tr").byIndex(index).withChildTag("a").byTag("a").build();
        return Driver.findVisible(By.xpath(INSTITUTION_NAME_FROM_TOP_BUYS_AND_SELLS_TABLE_XPATH), getTopBuysAndSellsTable()).getText().trim();
    }

    public String getValueChange(int index){
        //Quotes workaround
        String institutionName = getInstitutionNameFromTopBuysAndSellsTable(index);
        SenchaWebElement valueChange = Driver.findVisible(By.xpath(new XPathBuilder().byTag("tr").withChildTextContains(institutionName).byTag("td").byIndex(2).build()), getTopBuysAndSellsTable());
        return (valueChange != null) ? valueChange.getText().trim() : null;
    }

    public String getSharesChange(int index){
        String institutionName = getInstitutionNameFromTopBuysAndSellsTable(index);
        SenchaWebElement sharesChange = Driver.findVisible(By.xpath(new XPathBuilder().byTag("tr").withChildTextContains(institutionName).byTag("td").byIndex(3).build()), getTopBuysAndSellsTable());
        return (sharesChange != null) ? sharesChange.getText().trim() : null;
    }
}

