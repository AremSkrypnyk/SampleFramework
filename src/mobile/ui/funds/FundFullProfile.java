package ipreomobile.ui.funds;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.FullProfile;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.activities.ActivityDetailsOverview;
import ipreomobile.ui.activities.GroupActivityOverlay;
import ipreomobile.ui.blocks.overlay.FundContactsOverlay;
import ipreomobile.ui.blocks.overlay.MapPreviewOverlay;
import ipreomobile.ui.institutions.InstitutionFullProfile;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class FundFullProfile extends FullProfile {
    private static final By INSTITUTION_LINK_LOCATOR = By.className("investor-name");
    private static final By PHONE_LOCATOR            = By.className("phone");
    private static final By FAX_LOCATOR              = By.className("fax");
    private static final By CRM_BADGE_LOCATOR        = By.className("crm-fund");
    private static final By HEADER_LOCATOR           = By.xpath(new XPathBuilder().byClassName("fund-summary").byClassName("title").build());
    private static final By STATUS_BADGE_LOCATOR     = By.className("record-status");
    private static final By ADD_ACTIVITY_LOCATOR     = By.className("add-activity");
    private static final By LAST_ACTION_LINK_LOCATOR = By.className("last-action");
    private static final By MAP_LOCATOR              = By.className("map");
    private static final By MANAGED_BY_LINK_LOCATOR  = By.className("primary-contacts");

    public FundFullProfile(){
        super();
        setHeaderLocator(HEADER_LOCATOR);
        setCrmBadgeLocator(CRM_BADGE_LOCATOR);
        waitReady();
    }


    public InstitutionFullProfile goToInstitutionProfile(){
        getInstitutionLink().click();
        return new InstitutionFullProfile();
    }

    public FundFullProfile clickInstitutionProfileLink(){
        Driver.findVisible(INSTITUTION_LINK_LOCATOR).click();
        return this;
    }

    public String getInstitutionName(){
        return getInstitutionLink().getText();
    }

    public String getPhone(){
        waitReady();
        return Driver.findVisible(PHONE_LOCATOR).getText();
    }

    public FundFullProfile verifyPhone(String expectedPhone) {
        Assert.assertEquals(getPhone(), expectedPhone, "Phone number mismatch: ");
        return this;
    }

    public String getFax(){
        waitReady();
        return Driver.findVisible(FAX_LOCATOR).getText();
    }

    public FundFullProfile verifyFax(String expectedFax){
        Assert.assertEquals(getFax(), expectedFax, "Fax mismatch for contact '"+getProfileName()+"':");
        return this;
    }

    public FundFullProfile verifyBdVersusCrmInfo(){
        openBdVersusCrmOverlay();

        List<String> bdCrmRows = new ArrayList<>();
        bdCrmRows.add("Fund Name");
        bdCrmRows.add("Address");
        bdCrmRows.add("City");
        bdCrmRows.add("State / Province");
        bdCrmRows.add("Country");
        bdCrmRows.add("Postal Code");
        bdCrmRows.add("Phone");

        verifyBdVersusCrmInfo(bdCrmRows);
        closeOverlay();
        return this;
    }

    public FundFullProfile verifyActiveAndSubscribedBadge(boolean isActive, boolean isSubscribed){
       SenchaWebElement statusBadge = Driver.findOne(STATUS_BADGE_LOCATOR);
        String message;
        if (isActive && isSubscribed) {
            message = "Active & Subscribed";
        } else if (isActive && !isSubscribed) {
            message = "No message set yet";
        } else {
            message = "No message set yet";
        }
        statusBadge.click();
        verifyTooltip(message);
        waitTooltipClosed();
        return this;
    }

    public FundFullProfile verifyManagedByOverlay(){

        return this;
    }

    private SenchaWebElement getInstitutionLink(){
        return Driver.findVisible(INSTITUTION_LINK_LOCATOR);
    }

    public GroupActivityOverlay addActivity(){
        Driver.findVisible(ADD_ACTIVITY_LOCATOR).click();
        return new GroupActivityOverlay();
    }

    public FundFullProfile clickAddActivityButton(){
        Driver.findVisible(ADD_ACTIVITY_LOCATOR).click();
        return this;
    }

    public MapPreviewOverlay openMapPreview(){
        Driver.findVisible(MAP_LOCATOR).click();
        return new MapPreviewOverlay(UITitles.OverlayType.FUND_ADDRESS);
    }

    public FundContactsOverlay openFundContactsOverlay(){
        Driver.findVisible(MANAGED_BY_LINK_LOCATOR).click();
        return new FundContactsOverlay();
    }

    public ActivityDetailsOverview openLastActionOverlay(){
        Driver.findVisible(LAST_ACTION_LINK_LOCATOR).click();
        return new ActivityDetailsOverview();
    }
}
