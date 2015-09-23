package ipreomobile.ui.profiles.overviewCards;

import ipreomobile.core.Driver;
import ipreomobile.core.SenchaWebElement;
import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.UITitles;
import org.openqa.selenium.By;

public class NonOwnerInstitutionalHoldingsCard extends BaseHoldingsCard  {
    private static final String HEADER = UITitles.get(UITitles.ProfileCardTitle.NON_OWNER_INSTITUTIONAL_HOLDINGS);
    private By containerLocator = By.xpath(new XPathBuilder()
            .byClassName("card-nonownerholdings")
            .withNoClassName("x-item-hidden")
            .build());

    public NonOwnerInstitutionalHoldingsCard(){
        setupCardByHeader(HEADER);
        setupTable();
    }

}
