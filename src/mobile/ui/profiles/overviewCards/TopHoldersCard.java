package ipreomobile.ui.profiles.overviewCards;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.ProfileOverviewCard;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.blocks.KeyValueTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class TopHoldersCard extends ProfileOverviewCard {

    private static final String PAR_CHANGE_XPATH = new XPathBuilder()
            .byClassName("row")
            .withChildTag("p").withText("%s")
            .byClassName("last-line")
            .byClassName("first")
            .byTag("span").byIndex(1)
            .build();

    private static final String POSITION_DATE_XPATH = new XPathBuilder()
            .byClassName("row")
            .withChildTag("p").withText("%s")
            .byClassName("last-line")
            .byClassName("first")
            .byTag("span").byIndex(2).build();


    //TODO investigation
    private KeyValueTable informationTable = new KeyValueTable(
            new XPathBuilder()
                    .byClassName("information")
                    .byClassName("column")
                    .byClassName("row")
                    .byClassName("first-line").withChildTag("p").withClassName("first").build(),
            new XPathBuilder().byClassName("first").build(),
            new XPathBuilder().byClassName("last").build()
    );
    private static final String HEADER = UITitles.get(UITitles.ProfileCardTitle.TOP_HOLDERS);

    public TopHoldersCard(){
        setupCardByHeader(HEADER);
        informationTable.setContainer(container);
    }

    public String getParHeld(String institutionName){
        return informationTable.getValue(institutionName);
    }

    public String getInstitutionName(int index){
        return informationTable.getKey(index);
    }

    public String getParChange(String institutionName){
        WebElement parChangeField = Driver.findVisible(By.xpath(String.format(PAR_CHANGE_XPATH, institutionName)), container);
        Assert.assertNotNull(parChangeField, "No Par Change for institution with " + institutionName + " name was found on " + getScreenName());
        return parChangeField.getText().trim();
    }

    public String getPositionDate(String institutionName){
        WebElement positionDateField = Driver.findVisible(By.xpath(String.format(POSITION_DATE_XPATH, institutionName)), container);
        Assert.assertNotNull(positionDateField, "No Position Date for institution with " + institutionName + " name was found on " + getScreenName());
        return positionDateField.getText().trim();
    }

}
