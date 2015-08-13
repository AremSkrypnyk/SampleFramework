package ipreomobile.data.billfoldData.blocks.tables.tableRows;


public class TopBuysSellsTableRow {

    private String securityName;
    private String valueChange;
    private String sharesChange;

    public TopBuysSellsTableRow(String securityName, String valueChange, String sharesChange){
        this.securityName = securityName;
        this.valueChange = valueChange;
        this.sharesChange = sharesChange;
    }
}
