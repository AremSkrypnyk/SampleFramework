package ipreomobile.ui.profiles.overviewCards;

import ipreomobile.ui.UITitles;

public class InstitutionalHoldingsCard extends BaseHoldingsCard {
    private static final String HEADER = UITitles.get(UITitles.ProfileCardTitle.INSTITUTIONAL_HOLDINGS);

    public InstitutionalHoldingsCard(){
        setupCardByHeader(HEADER);
        setupTable();
    }

}
