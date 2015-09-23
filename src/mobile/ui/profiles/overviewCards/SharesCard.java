package ipreomobile.ui.profiles.overviewCards;

import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.ProfileOverviewCard;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.blocks.KeyValueTable;

public class SharesCard extends ProfileOverviewCard {
    private KeyValueTable informationTable = new KeyValueTable(
            new XPathBuilder()
                    .byClassName("information", "row")
                    .byClassName("column")
                    .byClassName("row")
                    .byClassName("column").build(),
            new XPathBuilder().byClassName("label").build(),
            new XPathBuilder().byClassName("value").build()
    );
    private static final String HEADER = UITitles.get(UITitles.ProfileCardTitle.SHARES);

    public SharesCard(){
        setupCardByHeader(HEADER);
        informationTable.setContainer(container);
    }

    public String getBuys(){
        return informationTable.getValue("buys");
    }

    public String getSells(){
        return informationTable.getValue("Sells");
    }

    public String getBuyIns(){
        return informationTable.getValue("buy ins");
    }

    public String getSellOuts(){
        return informationTable.getValue("Sell outs");
    }

    public String getSo(){
        return informationTable.getValue("S/O");
    }

    public String getInstitutionalShares(){
        return informationTable.getValue("institutional shares");
    }

    public String getShareType(){
        return informationTable.getValue("Share type");
    }
}
