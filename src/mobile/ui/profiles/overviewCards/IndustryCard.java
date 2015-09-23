package ipreomobile.ui.profiles.overviewCards;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.ProfileOverviewCard;
import ipreomobile.ui.UITitles;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class IndustryCard extends ProfileOverviewCard {
    private static final String HEADER = UITitles.get(UITitles.ProfileCardTitle.INDUSTRY);
    private static final String INDUSTRY_VALUE_XPATH_TEMPLATE = new XPathBuilder().byClassName("row").withChildTag("div").withText("%s").byClassName("value").build();
    private static final String MACRO_INDUSTRY_LABEL = "Macro";
    private static final String MID_INDUSTRY_LABEL = "Mid";
    private static final String MICRO_INDUSTRY_LABEL = "Micro";

    public IndustryCard(){
        setupCardByHeader(HEADER);
    }

    public String getMacroIndustryLabel(){
        WebElement macroIndustryField = Driver.findVisible(By.xpath(String.format(INDUSTRY_VALUE_XPATH_TEMPLATE, MACRO_INDUSTRY_LABEL)), container);
        Assert.assertNotNull(macroIndustryField, "No Macro Industry field was found on " + getScreenName());
        return macroIndustryField.getText();
    }

    public String getMidIndustryLabel(){
        WebElement midIndustryField = Driver.findVisible(By.xpath(String.format(INDUSTRY_VALUE_XPATH_TEMPLATE, MID_INDUSTRY_LABEL)), container);
        Assert.assertNotNull(midIndustryField, "No Mid Industry field was found on " + getScreenName());
        return midIndustryField.getText();
    }

    public String getMicroIndustryLabel(){
        WebElement microIndustryField = Driver.findVisible(By.xpath(String.format(INDUSTRY_VALUE_XPATH_TEMPLATE, MICRO_INDUSTRY_LABEL)), container);
        Assert.assertNotNull(microIndustryField, "No Micro Industry field was found on " + getScreenName());
        return microIndustryField.getText();
    }
}
