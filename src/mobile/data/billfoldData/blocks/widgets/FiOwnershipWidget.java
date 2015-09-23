package ipreomobile.data.billfoldData.blocks.widgets;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.ScreenCard;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;

public class FiOwnershipWidget extends ScreenCard {

    private static final String TOP_10_VALUES_TABLE_XPATH = new XPathBuilder().byIdContains("_ucFIOBIR_tdThi").build();
    private static final String TOP_BUYS_TABLE_XPATH = new XPathBuilder().byIdContains("_topBuys_gv").build();
    private static final String TOP_BUYS_INS_TABLE_XPATH = new XPathBuilder().byIdContains("_topBuyIns_gv").build();
    private static final String TOP_SELLS_TABLE_XPATH = new XPathBuilder().byIdContains("_topSells_gv").build();

    private static final String PAR_HELD = new XPathBuilder().byTag("tr").withChildTextContains("%s").byTag("td").byIndex(2).build();
    private static final String PAR_CNG = new XPathBuilder().byTag("tr").withChildTextContains("%s").byTag("td").byIndex(3).build();
    private static final String FI_PORT = new XPathBuilder().byTag("tr").withChildTextContains("%s").byTag("td").byIndex(4).build();

    private static final String PAR_CNANGE = new XPathBuilder().byTag("tr").withChildTextContains("%s").byTag("td").byIndex(2).build();

    private SenchaWebElement top10ValuesTable;
    private SenchaWebElement topBuysTable;
    private SenchaWebElement topBuysInsTable;
    private SenchaWebElement topSellsTable;

    public FiOwnershipWidget(){
        addCheckpointElement(By.xpath(TOP_10_VALUES_TABLE_XPATH));
        waitReady();
    }

    private SenchaWebElement getTop10ValuesTable(){
        return (top10ValuesTable != null) ? top10ValuesTable : Driver.findVisible(By.xpath(TOP_10_VALUES_TABLE_XPATH));
    }

    public String getSecurityNameFromTop10(int index){
        String SECURITY_NAME_FROM_TOP_10_TABLE_XPATH = new XPathBuilder().byTag("tr").byTag("td").withChildTag("a").build();
        return Driver.findAll(By.xpath(SECURITY_NAME_FROM_TOP_10_TABLE_XPATH), getTop10ValuesTable()).get(index).getText().trim();
    }

    public String getParHeld(int index){
        return Driver.findVisible(By.xpath(String.format(PAR_HELD, getSecurityNameFromTop10(index))), getTop10ValuesTable()).getText().trim();
    }

    public String getParChg(int index){
        return Driver.findVisible(By.xpath(String.format(PAR_CNG, getSecurityNameFromTop10(index))), getTop10ValuesTable()).getText().trim();
    }

    public String getFiPort(int index){
        return Driver.findVisible(By.xpath(String.format(FI_PORT, getSecurityNameFromTop10(index))), getTop10ValuesTable()).getText().trim();
    }

    private SenchaWebElement getTopBuysTable(){
        return (topBuysTable != null) ? topBuysTable : Driver.findVisible(By.xpath(TOP_BUYS_TABLE_XPATH));
    }

    public String getSecurityNameFromTopBuysTable(int index){
        String SECURITY_NAME = new XPathBuilder().byTag("tr").byTag("td").withChildTag("a").build();
        return Driver.findAll(By.xpath(SECURITY_NAME), getTopBuysTable()).get(index).getText().trim();
    }

    public String getParChangeTopBuys(int index){
        return Driver.findVisible(By.xpath(String.format(PAR_CNANGE, getSecurityNameFromTopBuysTable(index))), getTopBuysTable()).getText().trim();
    }

    private SenchaWebElement getTopBuysInsTable(){
        return (topBuysInsTable != null) ? topBuysInsTable : Driver.findVisible(By.xpath(TOP_BUYS_INS_TABLE_XPATH));
    }

    public String getSecurityNameFromTopBuysInsTable(int index){
        String SECURITY_NAME = new XPathBuilder().byTag("tr").byTag("td").withChildTag("a").build();
        return Driver.findAll(By.xpath(SECURITY_NAME), getTopBuysInsTable()).get(index).getText().trim();
    }

    public String getParChangeTopBuyIns(int index){
        return Driver.findVisible(By.xpath(String.format(PAR_CNANGE, getSecurityNameFromTopBuysInsTable(index))), getTopBuysInsTable()).getText().trim();
    }

    private SenchaWebElement getTopSellsTable(){
        return (topSellsTable != null) ? topSellsTable : Driver.findVisible(By.xpath(TOP_SELLS_TABLE_XPATH));
    }

    public String getSecurityNameFromTopSellsTable(int index){
        String SECURITY_NAME = new XPathBuilder().byTag("tr").byTag("td").byTag("a").build();
        return Driver.findAll(By.xpath(SECURITY_NAME), getTopSellsTable()).get(index).getText().trim();
    }

    public String getParChangeTopSells(int index){
        return Driver.findVisible(By.xpath(String.format(PAR_CNANGE, getSecurityNameFromTopSellsTable(index))), getTopSellsTable()).getText().trim();
    }

}
