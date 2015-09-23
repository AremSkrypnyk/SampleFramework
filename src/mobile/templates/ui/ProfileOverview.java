package ipreomobile.templates.ui;

import ipreomobile.core.*;
import ipreomobile.ui.ScreenCard;
import ipreomobile.ui.UITitles;
import org.openqa.selenium.By;
import org.testng.Assert;

import java.util.List;

public abstract class ProfileOverview extends ScreenCard implements ProfileOverviewController {
    private String profileType;
    private String headerXpath = new XPathBuilder().byClassName(PROFILE_HEADER_CLASS).build();
    private String profileNameXpath = new XPathBuilder().byClassName(PROFILE_HEADER_CLASS).byTag("div").withClassName("x-unsized x-title").build();
    private static final String PROFILE_HEADER_CLASS      = "profile-header-labels";
    private static final String PROFILE_CONTAINER_CLASS = "active-card";

    private static final By PROFILE_UNAVAILABLE_LOCATOR = By.xpath(new XPathBuilder()
            .byClassName("x-mask-error-message-body")
            .byText("This Profile Overview is unavailable in Offline Mode")
            .build());
    private ProfileOverviewCardsList cardsList;

    public ProfileOverview() {
        addLoadingIndicatorCheckpoint();
        if (!isDataUnavailable()) {
            addCheckpointChain(By.xpath(profileNameXpath), false)
                    .addClosingCheckpoint(true)
                    .addVisibilityCondition(true);
            profileType = this.getClass().getSimpleName().replace("ProfileOverview", "");
            cardsList = new ProfileOverviewCardsList();
        }
    }

    public boolean isCardPresent(UITitles.ProfileCardTitle cardHeader) {
        return (cardsList.getItem(UITitles.get(cardHeader)) != null);
    }

    public ProfileOverview verifyCardPresence(UITitles.ProfileCardTitle cardHeader) {
        cardsList.verifyItemPresence(UITitles.get(cardHeader));
        return this;
    }

    public ProfileOverview verifyCardAbsence(UITitles.ProfileCardTitle cardHeader) {
        cardsList.verifyItemAbsence(UITitles.get(cardHeader));
        return this;
    }

    public void setHeaderXpath(String xpath) {
        this.headerXpath = xpath;
        addCheckpointElement(By.xpath(headerXpath)).mustBeVisible();
    }

    public ProfileOverview waitProfileLoaded(String shortName) {
        if (!isProfileOverviewUnavailableInOfflineMode()) {
            SenchaWebElement summaryCard = Driver.findVisible(By.className(PROFILE_CONTAINER_CLASS));
            setCheckpointChainParentItem(summaryCard);
            waitReady();
            verifyFullProfileNameParts(shortName);
        }
        return this;
    }

    public boolean isProfileOverviewUnavailableInOfflineMode() {
        return Driver.isElementVisible(PROFILE_UNAVAILABLE_LOCATOR);
    }

    public boolean isDataUnavailable() {
        return Driver.isExactTextPresentAndVisible("No Data Available");
    }

    public String getProfileName(){
        By headerLocator = By.xpath(profileNameXpath);
        SenchaWebElement header = Driver.findVisible(headerLocator);
        if (header == null) {
            waitReady();
            header = Driver.findVisible(headerLocator);
        }
        if (header == null) {
            String message = "Failed to find visible header element. Found items by locator '"+headerLocator + "':";
            List<SenchaWebElement> headers = Driver.findAll(headerLocator);
            for (SenchaWebElement el: headers) {
                message += ElementHelper.describe(el);
            }

            throw new Error(message);
        }
        return header.getText();
    }

    @Override
    public void waitReady(){
        if (!isProfileOverviewUnavailableInOfflineMode() && !isDataUnavailable()) {
            super.waitReady();
        }
    }

    public ProfileOverview verifyProfileName(String expectedName) {
        String actualName = getProfileName().toUpperCase();
        expectedName = expectedName.trim().toUpperCase();
        Assert.assertEquals(actualName, expectedName, "Profile name displayed on "+profileType+" Profile is different from expected one: ");
        return this;
    }

    protected void verifyFullProfileNameParts(String expectedName){
        String fullName = getProfileName();
        Assert.assertTrue(StringHelper.containsAllWords(fullName.toUpperCase(), expectedName.toUpperCase()), "Active "+profileType+" Profile name mismatch: expected '" + expectedName + "', but found '" + fullName + "'.");
    }

}
