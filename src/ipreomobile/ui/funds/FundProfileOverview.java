package ipreomobile.ui.funds;

import ipreomobile.core.Driver;
import ipreomobile.core.Verify;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.ProfileOverview;
import ipreomobile.ui.activities.ActivityOverlay;
import ipreomobile.ui.activities.GroupActivityOverlay;
import ipreomobile.ui.blocks.overlay.FundContactsOverlay;
import ipreomobile.ui.blocks.overlay.ProfileInfoOverlay;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

public class FundProfileOverview extends ProfileOverview {
    private static final String PROFILE_HEADER_CLASS = "quickview-profile-header";
    private static final String PROFILE_HEADER_TEXT_CLASS = "profile-header-labels";
    private static final By INSTITUTION_NAME_LOCATOR     = By.xpath(new XPathBuilder()
            .byClassName(PROFILE_HEADER_TEXT_CLASS)
            .byClassName("x-label")
            .build());
    private static final By FUND_CONTACTS_LOCATOR = By.xpath(new XPathBuilder()
            .byClassName(PROFILE_HEADER_CLASS)
            .byClassName("contacts")
            .build());
    private static final By PROFILE_INFO_BUTTON_LOCATOR    = By.xpath(new XPathBuilder()
            .byClassName(PROFILE_HEADER_CLASS)
            .byClassName("institution")
            .build());
    private static final By ADD_ACTIVITY_BUTTON_LOCATOR    = By.xpath(new XPathBuilder()
            .byClassName(PROFILE_HEADER_CLASS)
            .byClassName("add-activity")
            .build());

    public FundProfileOverview(){
        super();
        addCheckpointElement(By.className("active-card")).mustBeVisible();
        addCheckpointElement(By.className("profile-header-labels")).mustBeVisible();
        waitReady();
    }

    public FundContactsOverlay openFundContactsOverlay(){
       SenchaWebElement fundContactsButton = Driver.findVisible(FUND_CONTACTS_LOCATOR);
        Assert.assertNotNull(fundContactsButton, "Fund '" + getProfileName() + "' has no contacts information " +
                "(Primary Contacts and Managed By), " +
                "and no corresponding button exists.");
        fundContactsButton.click();
        return new FundContactsOverlay();
    }

    public ProfileInfoOverlay openProfileInfoOverlay(){
       SenchaWebElement profileInfoButton = Driver.findVisible(PROFILE_INFO_BUTTON_LOCATOR);
        Assert.assertNotNull(profileInfoButton, "Institution '" + getProfileName() + "' has no profile information (address and phone), and no corresponding button exists.");
        profileInfoButton.click();
        return new ProfileInfoOverlay();
    }

    public ActivityOverlay addActivity(){
       SenchaWebElement addActivityButton = Driver.findVisible(ADD_ACTIVITY_BUTTON_LOCATOR);
        Assert.assertNotNull(addActivityButton, "No 'Add Activity' button was located on Profile Overview for '"+getProfileName()+".");
        addActivityButton.click();
        return new GroupActivityOverlay();
    }

    public String getInstitutionName(){
        return Driver.findVisible(INSTITUTION_NAME_LOCATOR).getText();
    }

    public FundProfileOverview verifyInstitutionName(String expectedName){
        Verify.verifyEquals(getInstitutionName(), expectedName, getScreenName() + ": Institution name mismatch:");
        return this;
    }

}
