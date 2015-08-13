package ipreomobile.ui.profiles.overviewCards;

import ipreomobile.core.Driver;
import ipreomobile.core.Verify;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.ProfileOverviewCard;
import ipreomobile.ui.activities.ActivityDetailsOverview;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

import java.util.List;

public class LastActionsCard extends ProfileOverviewCard {

    private static final String HEADER = "Last Actions";
    private static final String SUBJECT_CLASS = "subject";
    private static final String DATE_CLASS = "date";
    private static final String LINK_CLASS = "secondary-link";
    private static final String TYPE_CLASS = "last-action-label";

    private static final String ACTION_ITEM_CLASS = "last-action";

    public LastActionsCard(){
        setupCardByHeader(HEADER);
    }

    public String getActionDetailsBySubject(String expectedSubject){
       SenchaWebElement link = getActionLinkBySubject(expectedSubject);
        Assert.assertNotNull(link, "No description was found for activity with subject'"+expectedSubject+"'.");
        return link.getText();
    }

    public LastActionsCard verifyTypeBySubject(String actionSubject, String expectedType){
        Verify.verifyContainsText(getActionDetailsBySubject(actionSubject), expectedType, "Action type mismatch on '"+getScreenName()+"':");
        return this;
    }

    public LastActionsCard verifyDateBySubject(String actionSubject, String expectedDate){
        Verify.verifyContainsText(getActionDetailsBySubject(actionSubject), expectedDate, "Action date mismatch on '"+getScreenName()+"':");
        return this;
    }

    public LastActionsCard verifySubjectPresent(String actionSubject) {
       SenchaWebElement link = getActionLinkBySubject(actionSubject);
        Assert.assertNotNull(link, "No action with subject '"+ actionSubject +"' was found on '"+getScreenName()+"'.");
        return this;
    }

    public String getDate(String subject){
        String dateXpath = new XPathBuilder()
                .byClassName(ACTION_ITEM_CLASS)
                .withChildTag("*").withClassName(SUBJECT_CLASS).withText(subject)
                .byClassName(DATE_CLASS)
                .build();
        return Driver.findVisible(By.xpath(dateXpath), container).getText();
    }

    public String getType(String subject){
        String typeXpath = new XPathBuilder()
                .byClassName(ACTION_ITEM_CLASS)
                .withChildTag("*").withClassName(SUBJECT_CLASS).withText(subject)
                .byClassName(TYPE_CLASS)
                .byIndex(2)
                .build();
        return Driver.findVisibleNow(By.xpath(typeXpath), container).getText();
    }

    public ActivityDetailsOverview openActivityDetails(String subject){
       SenchaWebElement link = getActionLinkBySubject(subject);
        Assert.assertNotNull(link, "No link was found for activity with subject'"+subject+"'.");
        link.click();
        return new ActivityDetailsOverview();
    }

    public ActivityDetailsOverview openActivityDetails(int index){
       SenchaWebElement link = getActionLinkByIndex(index);
        Assert.assertNotNull(link, "No link was found for activity with index'"+index+"'.");
        link.click();
        return new ActivityDetailsOverview();
    }

    public int getNumberOfActions(){
        String detailsXPath = new XPathBuilder()
                .byClassName(ACTION_ITEM_CLASS)
                .withChildTag("*").withClassName(SUBJECT_CLASS)
                .byClassName(LINK_CLASS)
                .build();
        return Driver.findAllNow(By.xpath(detailsXPath), container).size();
    }

    public String getSubjectByIndex(int index){
        String subjectXpath = new XPathBuilder()
                .byClassName(ACTION_ITEM_CLASS)
                .byClassName(SUBJECT_CLASS)
                .build();
        List<SenchaWebElement> subjects = Driver.findAllNow(By.xpath(subjectXpath), container);
        return subjects.get(index-1).getText();
    }

    private SenchaWebElement getActionLinkBySubject(String subject){
        String detailsXPath = new XPathBuilder()
                .byClassName(ACTION_ITEM_CLASS)
                .withChildTag("*").withClassName(SUBJECT_CLASS).withText(subject)
                .byClassName(LINK_CLASS)
                .build();
        return Driver.findVisibleNow(By.xpath(detailsXPath), container);
    }

    private SenchaWebElement getActionLinkByIndex(int index){
        String detailsXPath = new XPathBuilder()
                .byClassName(ACTION_ITEM_CLASS)
                .byClassName(LINK_CLASS)
                .build();
        List<SenchaWebElement> links = Driver.findAllNow(By.xpath(detailsXPath), container);
        return links.get(index-1);
    }

}
