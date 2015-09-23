package ipreomobile.ui.profiles.overviewCards;

import ipreomobile.core.Driver;
import ipreomobile.core.Logger;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.ProfileOverviewCard;
import ipreomobile.ui.UITitles;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class PriceHistoryCard extends ProfileOverviewCard {

    private static final String HEADER = UITitles.get(UITitles.ProfileCardTitle.PRICE_HISTORY);

    private static final String DIAGRAM_XPATH = new XPathBuilder().byTag("div").withIdContains("main").byClassName("x-surface").build();

    public PriceHistoryCard(){
        setupCardByHeader(HEADER);
    }

    public void verifyDiagram() {
        WebElement diagram = Driver.findIfExists(By.xpath(DIAGRAM_XPATH));
        Assert.assertNotNull(diagram, getScreenName() +"No chart was found on " + HEADER + " card.");
    }
}
