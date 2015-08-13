package ipreomobile.ui.profiles.overviewCards;

import ipreomobile.core.Driver;
import ipreomobile.templates.ui.ProfileOverviewCard;

public class CoverageCard extends ProfileOverviewCard {
    public CoverageCard(){
        setupCardByHeader("Coverage");
    }

    public CoverageCard verifyLabels(){
        Driver.verifyExactTextPresentAndVisible("Market Cap", container);
        return this;
    }
}
