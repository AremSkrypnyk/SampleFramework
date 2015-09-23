package ipreomobile.templates.ui;

import ipreomobile.core.Driver;
import ipreomobile.core.ElementHelper;
import ipreomobile.core.SenchaWebElement;
import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.ScreenCard;
import org.openqa.selenium.By;
import org.testng.Assert;

public class ProfileOverviewCard extends ScreenCard {

    private static final String HEADER_CLASS = "card-title";
    protected SenchaWebElement container;
    private String containerClass = "base-card";
    private String expectedHeader;

    protected void setupCardByHeader(String expectedHeader){
        this.expectedHeader = expectedHeader;
        bringCardIntoView();
        //setContainer();
    }

    public String getHeader(){
        String headerXpath = new XPathBuilder().byClassName(HEADER_CLASS).build();
        return Driver.findVisible(By.xpath(headerXpath), container).getText();
    }

    public ProfileOverviewCard closeActiveOverlay(){
        BaseOverlay.closeActiveOverlay();
        return this;
    }

    protected void bringCardIntoView(){
        container = new ProfileOverviewCardsList().getItem(expectedHeader);
        Assert.assertNotNull(container, "No card with header '"+expectedHeader+"' was found on Profile Overview.");
    }

//    protected void setContainer(){
//        String containerXpath = new XPathBuilder()
//                .byClassName(containerClass)
//                .withChildTag("div")
//                .withClassName(HEADER_CLASS)
//                .withText(expectedHeader)
//                .build();
//        container = Driver.findVisibleNow(By.xpath(containerXpath));
//    }

    private void checkContainer(){
        Assert.assertNotNull(container, "Failed to find Profile Overview Card '"+expectedHeader+"' container.");
    }

}
