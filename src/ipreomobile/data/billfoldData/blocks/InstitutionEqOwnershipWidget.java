package ipreomobile.data.billfoldData.blocks;

import ipreomobile.data.billfoldData.blocks.tables.InstitutionHoldingsTable;
import ipreomobile.data.billfoldData.blocks.tables.OwnershipSummaryTable;
import ipreomobile.data.billfoldData.blocks.tables.tableRows.TopBuysSellsTableRow;
import ipreomobile.data.billfoldData.blocks.tables.tableRows.TopHoldingsTableRow;
import ipreomobile.ui.UITitles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstitutionEqOwnershipWidget {

    public Map<UITitles.DataSources, InstitutionHoldingsTable> institutionHoldingsTable = new HashMap<>();

    public OwnershipSummaryTable ownershipSummaryTable          = new OwnershipSummaryTable();

    public List<TopBuysSellsTableRow> topBuysSellsTable         = new ArrayList<>();

    public List<TopHoldingsTableRow> topHoldingsTable           = new ArrayList<>();

}
