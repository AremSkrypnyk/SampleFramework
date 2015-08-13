package ipreomobile.ui.institutions;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.FullProfile;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.activities.ActivityDetailsOverview;
import ipreomobile.ui.activities.GroupActivityOverlay;
import ipreomobile.ui.blocks.ConfirmationDialog;
import ipreomobile.ui.blocks.overlay.MapPreviewOverlay;
import ipreomobile.ui.blocks.overlay.PrimaryContactsOverlay;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class InstitutionFullProfile extends FullProfile {
    private static final By HEADER_LOCATOR           = By.xpath(new XPathBuilder().byClassName("institution-summary").byClassName("title").build());
    private static final By SITE_LINK_LOCATOR        = By.xpath(new XPathBuilder().byClassName("header").byClassName("url").build());
    private static final By BUY_SELL_SIDE_LOCATOR    = By.xpath(new XPathBuilder().byClassName("side", "visible").build());
    private static final By CRM_BADGE_LOCATOR        = By.className("crm-institution");
    private static final By STATUS_BADGE_LOCATOR     = By.className("record-status");
    private static final By LAST_ACTION_LINK_LOCATOR = By.className("last-action");
    private static final By PHONE_LOCATOR            = By.className("phone");
    private static final By PRIMARY_CONTACTS_LOCATOR = By.className("primary-contacts");
    private static final By ADD_ACTIVITY_LOCATOR     = By.className("add-activity");
    private static final By MAP_LOCATOR              = By.className("map");

    public InstitutionFullProfile(){
        super();
        addCheckpointElement(By.className("profile-summary")).mustBeVisible();
        addCheckpointElement(By.className("institution-summary")).mustBeVisible();

        setCrmBadgeLocator(CRM_BADGE_LOCATOR);
        setHeaderLocator(HEADER_LOCATOR);
        waitReady();
    }

    public String getSide(){
        return getProfileAttribute(BUY_SELL_SIDE_LOCATOR);
    }

    public InstitutionFullProfile verifySide(String expectedSide){
        Assert.assertEquals(getSide().toLowerCase(), expectedSide.toLowerCase(), "Side mismatch:");
        return this;
    }

    public InstitutionFullProfile verifySide(UITitles.Side side){
        Assert.assertEquals(getSide().toLowerCase(), UITitles.get(side).toLowerCase(), "Side mismatch:");
        return this;
    }

    public String getPhone(){
        return getProfileAttribute(PHONE_LOCATOR);
    }

    public InstitutionFullProfile verifyPhone(String expectedPhone) {
        Assert.assertEquals(getPhone(), expectedPhone, "Phone number mismatch: ");
        return this;
    }

    public InstitutionFullProfile verifyCompanySiteLink(){
        clickCompanySiteLink();
        ConfirmationDialog dialog = new ConfirmationDialog();
        dialog.verify3rdPartyAppMessage();
        return this;
    }

    public InstitutionFullProfile clickCompanySiteLink(){
       SenchaWebElement companyLink = Driver.findOne(SITE_LINK_LOCATOR);
        companyLink.click();
        return this;
    }

    public InstitutionFullProfile verifyActiveAndSubscribedBadge(boolean isActive, boolean isSubscribed){
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

    public InstitutionFullProfile verifyBdVersusCrmInfo(){
        openBdVersusCrmOverlay();
        List<String> bdCrmRows = new ArrayList<>();
        bdCrmRows.add("Institution Name");
        bdCrmRows.add("Address");
        bdCrmRows.add("City");
        bdCrmRows.add("State / Province");
        bdCrmRows.add("Country");
        bdCrmRows.add("Postal Code");
        bdCrmRows.add("Phone");
        bdCrmRows.add("Website");
        verifyBdVersusCrmInfo(bdCrmRows);
        closeOverlay();
        return this;
    }

    public PrimaryContactsOverlay openPrimaryContactsOverlay(){
       SenchaWebElement primaryContactsActionButton = Driver.findOne(PRIMARY_CONTACTS_LOCATOR);
        primaryContactsActionButton.click();

        return new PrimaryContactsOverlay();
    }

    public ActivityDetailsOverview openLastActionOverlay(){
        Driver.findVisible(LAST_ACTION_LINK_LOCATOR).click();
        return new ActivityDetailsOverview();
    }

    public void verifyLastActionDate(String date) {
       SenchaWebElement lastActionLink = Driver.findVisible(LAST_ACTION_LINK_LOCATOR);
        Assert.assertTrue(lastActionLink.getText().contains(date), "Last Action date mismatch: expected "+date+", but found "+lastActionLink.getText());
    }

    public InstitutionFullProfile clickAddActivityButton(){
        Driver.findVisible(ADD_ACTIVITY_LOCATOR).click();
        return this;
    }

    public GroupActivityOverlay addActivity(){
        clickAddActivityButton();
        return new GroupActivityOverlay();
    }

    public MapPreviewOverlay openMapPreview(){
        Driver.findVisible(MAP_LOCATOR).click();
        return new MapPreviewOverlay(UITitles.OverlayType.INSTITUTION_ADDRESS);
    }

}
