package ipreomobile.ui.profiles.overviewCards;

import ipreomobile.ui.UITitles;

public class NonOwnerFundHoldingsCard extends BaseHoldingsCard {
    private static final String HEADER = UITitles.get(UITitles.ProfileCardTitle.NON_OWNER_FUND_HOLDINGS);
    public NonOwnerFundHoldingsCard(){
        setupCardByHeader(HEADER);
        setupTable();
    }
}
