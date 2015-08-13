package ipreomobile.data.billfoldData.blocks;

import ipreomobile.data.billfoldData.blocks.tables.tableRows.Top10TableRow;
import ipreomobile.data.billfoldData.blocks.tables.tableRows.TopBuyInsTableRow;
import ipreomobile.data.billfoldData.blocks.tables.tableRows.TopBuysTableRow;
import ipreomobile.data.billfoldData.blocks.tables.tableRows.TopSellsTableRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstitutionFiOwnershipWidget {

    public List<Top10TableRow> top10Table            = new ArrayList<>();
    public List<TopBuysTableRow> topBuysTable        = new ArrayList<>();
    public List<TopBuyInsTableRow> topBuyInsTable    = new ArrayList<>();
    public List<TopSellsTableRow> topSellsTable      = new ArrayList<>();
}
