package ipreomobile.ui.profiles.overviewCards;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.ProfileOverviewCard;
import org.openqa.selenium.By;
import org.testng.Assert;

public class PositionHistoryCard extends ProfileOverviewCard {
    private static final String HEADER = "Position History";

    private static final By CHART_LOCATOR = By.xpath(new XPathBuilder().byClassName("chart-container").build());
    private static final By DETAILS_LOCATOR = By.xpath(new XPathBuilder().byClassName("history-details").build());

    public PositionHistoryCard(){
        setupCardByHeader(HEADER);
    }

    public PositionHistoryCard verifyHistoryDetailsChart(){
        Assert.assertNotNull(Driver.findIfExistsNow(CHART_LOCATOR, container), "Position History chart was not found.");
        Assert.assertNotNull(Driver.findIfExistsNow(DETAILS_LOCATOR, container), "History Details table was not found.");
        return this;
    }
}
