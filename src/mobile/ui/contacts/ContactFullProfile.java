package ipreomobile.ui.contacts;

import ipreomobile.core.Driver;
import ipreomobile.core.SenchaWebElement;
import ipreomobile.core.Verify;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.FullProfile;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.activities.ActivityDetailsOverview;
import ipreomobile.ui.activities.GroupActivityOverlay;
import ipreomobile.ui.blocks.ConfirmationDialog;
import ipreomobile.ui.blocks.overlay.MapPreviewOverlay;
import ipreomobile.ui.institutions.InstitutionFullProfile;
import org.apache.commons.lang3.text.WordUtils;
import org.openqa.selenium.By;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class ContactFullProfile extends FullProfile {
    private static final By HEADER_LOCATOR           = By.xpath(new XPathBuilder().byClassName("contact-summary").byClassName("title").build());
    private static final By BUY_SELL_SIDE_LOCATOR    = By.xpath(new XPathBuilder().byClassName("side", "visible").build());
    private static final By CRM_BADGE_LOCATOR        = By.className("crm-contact");
    private static final By STATUS_BADGE_LOCATOR     = By.className("record-status");
    private static final By INSTITUTION_LINK_LOCATOR = By.className("investor-name");
    private static final By JOB_FUNCTION_LOCATOR     = By.className("subtitle");
    private static final By PHONE_LOCATOR            = By.className("phone");
    private static final By FAX_LOCATOR              = By.className("fax");
    private static final By EMAIL_BADGE_LOCATOR      = By.className("email-preview");
    private static final By EMAIL_PREVIEW_LOCATOR    = By.className("contact-email-overlay");
    private static final By LAST_ACTION_LINK_LOCATOR = By.className("last-action");
    private static final By ADD_ACTIVITY_LOCATOR     = By.className("add-activity");
    private static final By MAP_LOCATOR              = By.className("map");

    public ContactFullProfile(){
        super();
        addCheckpointElement(By.className("profile-summary")).mustBeVisible();
        addCheckpointElement(By.className("contact-summary")).mustBeVisible();

//        setCrmBadgeClass(CRM_BADGE_LOCATOR);
//        setHeaderXpath(HEADER_LOCATOR);
        setCrmBadgeLocator(CRM_BADGE_LOCATOR);
        setHeaderLocator(HEADER_LOCATOR);
        waitReady();
    }

    public String getSide(){
        waitReady();
        return WordUtils.capitalizeFully(Driver.findVisible(BUY_SELL_SIDE_LOCATOR).getText());
    }

    public ContactFullProfile verifyProfileName(String name) {
        verifyProfileNameParts(name);
        return this;
    }

    public ContactFullProfile verifySide(String expectedSide){
        Verify.verifyEqualsIgnoreCase(getSide(), expectedSide, "Side verification failed: ");
        return this;
    }

    public ContactFullProfile verifyActiveAndSubscribedBadge(boolean isActive, boolean isSubscribed){
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

    public ContactFullProfile verifyBdVersusCrmInfo(){
        waitReady();
        openBdVersusCrmOverlay();

        List<String> bdCrmRows = new ArrayList<>();
        bdCrmRows.add("Institution Name");
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

    public String getJobFunction(){
        return getProfileAttribute(JOB_FUNCTION_LOCATOR);
    }

    public ContactFullProfile verifyJobFunction(String expectedPosition){
        Assert.assertEquals(getJobFunction(), expectedPosition, "Position mismatch for contact '"+getProfileName()+"':");
        return this;
    }

    public String getPhone(){
        return getProfileAttribute(PHONE_LOCATOR);
    }


    public ContactFullProfile verifyPhone(String expectedPhone){
        if (expectedPhone != null && !expectedPhone.isEmpty()) {
            Assert.assertEquals(getPhone(), expectedPhone, "Phone mismatch for contact '" + getProfileName() + "':");
        }
        return this;
    }

    public String getFax(){
        return getProfileAttribute(FAX_LOCATOR);
    }

    public ContactFullProfile verifyFax(String expectedFax){
        Assert.assertEquals(getFax(), expectedFax, "Fax mismatch for contact '"+getProfileName()+"':");
        return this;
    }

    public String getInstitutionName(){
        return getProfileAttribute(INSTITUTION_LINK_LOCATOR);
    }

    public ContactFullProfile verifyInstitutionName(String expectedInstitutionName){
        Assert.assertEquals(getInstitutionName(), expectedInstitutionName, "Institution name on Contact Profile is different from expected one: ");
        return this;
    }

    public InstitutionFullProfile goToInstitutionProfile(){
        Driver.findVisible(INSTITUTION_LINK_LOCATOR).click();
        return new InstitutionFullProfile();
    }

    public ContactFullProfile clickInstitutionProfileLink(){
        Driver.findVisible(INSTITUTION_LINK_LOCATOR).click();
        return this;
    }


    public void openEmailPreviewOverlay(){
        Driver.findVisible(EMAIL_BADGE_LOCATOR).click();
    }

    public ContactFullProfile verifyEmail(String expectedEmail){
        if (expectedEmail != null && !expectedEmail.isEmpty()) {
            openEmailPreviewOverlay();
            SenchaWebElement emailPreview = Driver.findVisible(EMAIL_PREVIEW_LOCATOR);
            SenchaWebElement emailLink = Driver.findVisible(By.className("email"), emailPreview);
            Assert.assertEquals(emailLink.getText(), expectedEmail, "Contact e-mail is different from expected one: ");
            closeOverlay();
        }
        return this;
    }

    public ContactFullProfile verifyEmailPreviewOverlay(){
        openEmailPreviewOverlay();

        SenchaWebElement emailPreview = Driver.findVisible(EMAIL_PREVIEW_LOCATOR);
        SenchaWebElement emailLink = Driver.findVisible(By.className("email"), emailPreview);

        emailLink.click();

        ConfirmationDialog dialog = new ConfirmationDialog();
        dialog
                .verifyShown()
                .verify3rdPartyAppMessage();
        closeOverlay();
        return this;
    }

    public ActivityDetailsOverview openLastActionOverlay(){
        waitReady();
        Driver.findVisible(LAST_ACTION_LINK_LOCATOR).click();
        return new ActivityDetailsOverview();
    }

    public GroupActivityOverlay addActivity(){
        Driver.findVisible(ADD_ACTIVITY_LOCATOR).click();
        return new GroupActivityOverlay();
    }

    public ContactFullProfile clickAddActivityButton(){
        Driver.findVisible(ADD_ACTIVITY_LOCATOR).click();
        return this;
    }

    public MapPreviewOverlay openMapPreview(){
        Driver.findVisible(MAP_LOCATOR).click();
        return new MapPreviewOverlay(UITitles.OverlayType.CONTACT_ADDRESS);
    }

}
