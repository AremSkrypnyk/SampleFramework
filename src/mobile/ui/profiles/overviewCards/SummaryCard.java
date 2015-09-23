package ipreomobile.ui.profiles.overviewCards;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.ProfileOverviewCard;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.blocks.KeyValueTable;
import ipreomobile.core.SenchaWebElement;

public class SummaryCard extends ProfileOverviewCard {
    private KeyValueTable informationTable = new KeyValueTable(
            new XPathBuilder()
                    .byClassName("information", "row")
                    .byClassName("column")
                    .byClassName("row")
                    .byClassName("column").withChildTag("p").withClassName("label").build(),
            new XPathBuilder().byClassName("label").build(),
            new XPathBuilder().byClassName("value").build()
    );
    private static final String HEADER = UITitles.get(UITitles.ProfileCardTitle.SUMMARY);

    public SummaryCard(){
        setupCardByHeader(HEADER);
        informationTable.setContainer(container);
    }

    public String getMaturityDate(){
        return informationTable.getValue("Maturity");
    }

    public String getCouponRate(){
        return informationTable.getValue("Coupon");
    }

    public String getBuys(){
        return informationTable.getValue("Buys");
    }

    public String getSells(){
        return informationTable.getValue("Sells");
    }

    public String getBuyIns(){
        return informationTable.getValue("Buy Ins");
    }

    public String getSellOuts(){
        return informationTable.getValue("Sell Outs");
    }

    public String getHolders(){
        return informationTable.getValue("Holders");
    }

    public String getTotalParHeld(){
        return informationTable.getValue("Total par held");
    }
}
