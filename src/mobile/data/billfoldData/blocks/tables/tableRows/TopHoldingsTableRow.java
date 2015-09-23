package ipreomobile.data.billfoldData.blocks.tables.tableRows;

public class TopHoldingsTableRow {

    public TopHoldingsTableRow(String securityName, String percentPort, String percentChg){
        this.percentPort = percentPort;
        this.percentChg = percentChg;
        this.securityName = securityName;
    }

    private String securityName;
    private String percentPort;
    private String percentChg;

}
