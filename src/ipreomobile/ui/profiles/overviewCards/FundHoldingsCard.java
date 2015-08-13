package ipreomobile.ui.profiles.overviewCards;

import ipreomobile.ui.UITitles;

public class FundHoldingsCard extends BaseHoldingsCard {
    private static final String HEADER = UITitles.get(UITitles.ProfileCardTitle.FUND_HOLDINGS);
    public FundHoldingsCard(){
        setupCardByHeader(HEADER);
        setupTable();
    }
}
