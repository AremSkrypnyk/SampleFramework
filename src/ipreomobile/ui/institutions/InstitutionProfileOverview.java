package ipreomobile.ui.institutions;

import ipreomobile.core.Driver;
import ipreomobile.core.Logger;
import ipreomobile.core.Verify;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.ProfileOverview;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.activities.ActivityOverlay;
import ipreomobile.ui.activities.GroupActivityOverlay;
import ipreomobile.ui.blocks.overlay.PrimaryContactsOverlay;
import ipreomobile.ui.blocks.overlay.ProfileInfoOverlay;
import ipreomobile.ui.profiles.overviewCards.BaseHoldingsCard;
import ipreomobile.ui.profiles.overviewCards.InstitutionalHoldingsCard;
import ipreomobile.ui.profiles.overviewCards.NonOwnerInstitutionalHoldingsCard;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

public class InstitutionProfileOverview extends ProfileOverview {

    private static final String DEFAULT_INSTITUTION_NAME = "Fidelity Management And Research Company";
    private static final String PROFILE_HEADER_CLASS = "quickview-profile-header";
    private static final String PROFILE_HEADER_TEXT_CLASS = "profile-header-labels";
    private static final By INSTITUTION_TYPE_LOCATOR     = By.xpath(new XPathBuilder()
            .byClassName(PROFILE_HEADER_TEXT_CLASS)
            .byClassName("x-label")
            .build());
    private static final By PRIMARY_CONTACTS_LOCATOR = By.xpath(new XPathBuilder()
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

    public InstitutionProfileOverview() {
        super();
        addCheckpointElement(By.className("active-card")).mustBeVisible();
        addCheckpointElement(By.className(PROFILE_HEADER_TEXT_CLASS)).mustBeVisible();
        waitReady();
    }

    public InstitutionProfileOverview verifyInstitutionName(String expectedName){
        verifyProfileName(expectedName);
        return this;
    }

    @Override
    public InstitutionProfileOverview waitProfileLoaded(String name) {
        if (System.getProperty("test.env").equals("mock")) {
            Logger.logMessage("Workaround for mock server.js: profile name is set to '" + DEFAULT_INSTITUTION_NAME + "'");
            name = DEFAULT_INSTITUTION_NAME;
        }
        super.waitProfileLoaded(name);
        return this;
    }

    public String getInstitutionType(){
        return  Driver.findVisible(INSTITUTION_TYPE_LOCATOR).getText();
    }

    public BaseHoldingsCard getHoldingsCard() {
        if (isCardPresent(UITitles.ProfileCardTitle.INSTITUTIONAL_HOLDINGS)) {
            return new InstitutionalHoldingsCard();
        } else if (isCardPresent(UITitles.ProfileCardTitle.NON_OWNER_INSTITUTIONAL_HOLDINGS)) {
            return new NonOwnerInstitutionalHoldingsCard();
        }
        throw new Error("No Holdings card was found on Institution Profile Overview ["+getProfileName() +"].");
    }

    public InstitutionProfileOverview verifyInstitutionType(){
        String institutionTypeInHeader = getInstitutionType();
        String institutionTypeInCard = getHoldingsCard().getValue("Type");
        Verify.verifyEquals(institutionTypeInHeader, institutionTypeInCard,
                "Institution Type in Profile Overview header (actual) and in Holdings card (expected) do not match: ");
        return this;
    }

    public PrimaryContactsOverlay openPrimaryContactsOverlay(){
       SenchaWebElement primaryContactsButton = Driver.findVisible(PRIMARY_CONTACTS_LOCATOR);
        Assert.assertNotNull(primaryContactsButton, "Institution '" + getProfileName() + "' has no primary contacts, and no corresponding button exists.");
        primaryContactsButton.click();
        return new PrimaryContactsOverlay();
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
}

