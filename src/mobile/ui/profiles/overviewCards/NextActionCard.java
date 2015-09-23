package ipreomobile.ui.profiles.overviewCards;

import ipreomobile.core.Driver;
import ipreomobile.core.Logger;
import ipreomobile.core.Verify;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.ProfileOverviewCard;
import ipreomobile.ui.activities.ActivityDetailsOverview;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;

public class NextActionCard extends ProfileOverviewCard {

    private static final String HEADER = "Next Action";
    private static final By SUBJECT_LOCATOR = By.xpath( new XPathBuilder().byClassName("subject").build() );
    private static final By DATE_TIME_LOCATOR = By.xpath( new XPathBuilder().byClassName("date").build() );
    private static final By LINK_LOCATOR = By.xpath( new XPathBuilder().byClassName("secondary-link").build() );
    private static final By TYPE_LOCATOR = By.xpath( new XPathBuilder()
            .byClassName("secondary-link")
            .byClassName("last-action-label")
            .byIndex(2)
            .build());

    private static final int DATE_INDEX = 0;
    private static final int TIME_INDEX = 1;

    public NextActionCard(){
        setupCardByHeader(HEADER);
    }

    public String getDetails(){
        return Driver.findVisible(LINK_LOCATOR, container).getText();
    }

    public ActivityDetailsOverview openActivityDetails(){
        bringCardIntoView();
        Driver.findVisible(LINK_LOCATOR, container).click();
        return new ActivityDetailsOverview();
    }

    private SenchaWebElement getFieldAsElement(By locator){
        return Driver.findVisible(locator, container);
    }

    private String getFieldValue(By locator) {
       SenchaWebElement field = getFieldAsElement(locator);
        return (field == null) ? "" : field.getText().trim();
    }

    public String getSubject(){
        return getFieldValue(SUBJECT_LOCATOR);
    }

    public String getDateTime(){
        return getFieldValue(DATE_TIME_LOCATOR);
    }

    public String getDate(){
        String dateTime = getDateTime();
        return dateTime.split("-")[DATE_INDEX].trim();
    }

    public String getTimeSlot(){
        String dateTime = getDateTime();
        return dateTime.split("-")[TIME_INDEX].trim();
    }

    public String getType(){
        return getFieldValue(TYPE_LOCATOR);
    }

    public NextActionCard verifySubject(String expectedSubject){
        Verify.verifyEquals(getSubject(), expectedSubject, "Activity subject mismatch on '"+getScreenName()+"':");
        return this;
    }

    public NextActionCard verifyDate(String expectedDate){
        String actualDateTime = getDateTime();
        Verify.verifyContainsText(actualDateTime, expectedDate, "Activity date mismatch on '" + getScreenName() + "':");
        return this;
    }

    public NextActionCard verifyTime(String expectedTime){
        String actualDateTime = getDateTime();
        Verify.verifyContainsText(actualDateTime, expectedTime, "Activity time mismatch on '"+getScreenName()+"':");
        return this;
    }

    public NextActionCard verifyType(String expectedType){
        Verify.verifyEquals(getType(), expectedType, "Activity type mismatch on '"+getScreenName()+"':");
        return this;
    }

    public NextActionCard verifySubjectPresent(){
        Verify.verifyNotEmpty(getSubject(), "Activity subject was not found on '"+getScreenName()+"':");
        return this;
    }

    public NextActionCard verifyDatePresent(){
        Verify.verifyNotEmpty(getDate(), "Date was not found on '"+getScreenName()+"':");
        return this;
    }

    public NextActionCard verifyTimeSlotPresent(){
        Verify.verifyNotEmpty(getTimeSlot(), "Time slot was not found on '"+getScreenName()+"':");
        return this;
    }

    public NextActionCard verifyTypePresent(){
        Verify.verifyNotEmpty(getType(), "Activity type was not found on '"+getScreenName()+"':");
        return this;
    }
}
