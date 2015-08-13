package ipreomobile.ui.profiles.overviewCards;

import ipreomobile.templates.ui.ProfileOverviewCard;
import ipreomobile.ui.UITitles;

public class ContactHoldingsCard extends BaseHoldingsCard {
    private static final String HEADER = UITitles.get(UITitles.ProfileCardTitle.CONTACT_HOLDINGS);

    public ContactHoldingsCard(){
        setupCardByHeader(HEADER);
        setupTable();
    }
}
