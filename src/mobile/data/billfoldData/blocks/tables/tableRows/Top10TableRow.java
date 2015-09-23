package ipreomobile.data.billfoldData.blocks.tables.tableRows;

public class Top10TableRow {

    private String securityName;
    private String parHeld;
    private String parChg;
    private String percentFiPort;

    public Top10TableRow(String securityName, String parHeld, String parChg, String percentFiPort){
        this.parHeld = parHeld;
        this.parChg = parChg;
        this.percentFiPort = percentFiPort;
        this.securityName = securityName;
    }
}
