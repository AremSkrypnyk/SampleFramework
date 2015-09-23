package ipreomobile.ui.contacts;

import ipreomobile.core.Driver;
import ipreomobile.core.Logger;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.ProfileOverview;
import ipreomobile.ui.activities.ActivityOverlay;
import ipreomobile.ui.activities.GroupActivityOverlay;
import ipreomobile.ui.blocks.overlay.ProfileInfoOverlay;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

public class ContactProfileOverview extends ProfileOverview {
    private static final String DEFAULT_CONTACT_NAME = "Ramin Arani";
    private static final String PROFILE_HEADER_CLASS = "quickview-profile-header";
    private static final String PROFILE_HEADER_TEXT_CLASS = "profile-header-labels";
    private static final By PROFILE_INFO_BUTTON_LOCATOR    = By.xpath(new XPathBuilder()
            .byClassName(PROFILE_HEADER_CLASS)
            .byClassName("contact")
            .build());
    private static final By ADD_ACTIVITY_BUTTON_LOCATOR    = By.xpath(new XPathBuilder()
            .byClassName(PROFILE_HEADER_CLASS)
            .byClassName("add-activity")
            .build());

    public ContactProfileOverview() {
        super();
        addCheckpointElement(By.className("active-card")).mustBeVisible();
        setMaxWaitTimeout(60);
        waitReady();
    }

    public ContactProfileOverview verifyContactName(String expectedName){
        verifyProfileName(expectedName);
        return this;
    }

    @Override
    public ContactProfileOverview waitProfileLoaded(String name) {
        if (System.getProperty("test.env").equals("mock")) {
            Logger.logMessage("Workaround for mock server.js: profile name is set to '" + DEFAULT_CONTACT_NAME + "'");
            name = DEFAULT_CONTACT_NAME;
        }
        super.waitProfileLoaded(name);
        return this;
    }

    @Override
    public ContactProfileOverview verifyProfileName(String expectedName){
        verifyFullProfileNameParts(expectedName);
        return this;
    }

    public ContactProfileOverview verifyJobFunction(String expectedJobTitle){
       SenchaWebElement container = Driver.findVisible(By.className(PROFILE_HEADER_TEXT_CLASS));
        Driver.verifyTextPartPresentAndVisible(expectedJobTitle, container);
        return this;
    }

    public ContactProfileOverview verifyCompanyName(String expectedCompanyName){
       SenchaWebElement container = Driver.findVisible(By.className(PROFILE_HEADER_TEXT_CLASS));
        Driver.verifyTextPartPresentAndVisible(expectedCompanyName, container);
        return this;
    }

    public ProfileInfoOverlay openProfileInfoOverlay(){
       SenchaWebElement profileInfoButton = Driver.findVisible(PROFILE_INFO_BUTTON_LOCATOR);
        Assert.assertNotNull(profileInfoButton, "Contact '" + getProfileName() + "' has no profile information, and no corresponding button exists.");
        profileInfoButton.click();
        return new ProfileInfoOverlay();
    }

    public ActivityOverlay addActivity(){
       SenchaWebElement addActivityButton = Driver.findVisible(ADD_ACTIVITY_BUTTON_LOCATOR);
        Assert.assertNotNull(addActivityButton, "No 'Add Activity' button was located on Profile Overview for '"+getProfileName()+".");
        addActivityButton.click();
        return new GroupActivityOverlay();
    }


}
