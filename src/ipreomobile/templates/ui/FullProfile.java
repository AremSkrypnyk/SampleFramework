package ipreomobile.templates.ui;

import ipreomobile.core.*;
import ipreomobile.ui.GlobalNavigation;
import ipreomobile.ui.ScreenCard;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.blocks.BaseTabControl;
import ipreomobile.ui.blocks.overlay.FixDataOverlay;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.testng.Assert;

import java.util.List;

public class FullProfile extends ScreenCard {
    private BaseTabControl tabControl = new BaseTabControl();

    private static final String TAB_CONTROL_XPATH  = new XPathBuilder().byClassName("x-tabbar-inner").byClassName("x-tab-normal").build();
    private static final String BD_CRM_ENTRY_XPATH = new XPathBuilder().byCurrentItem().byText("%s").build();
    private static final String ACTIVE_TAB_CLASS   = "x-tab-active";
    private static final String CRM_MESSAGE_TEXT   = "in your CRM";

    private static final By FUM_MESSAGE_LOCATOR    = By.className("fum-tooltip");
    private static final By BD_CRM_BADGE_CLASS     = By.className("bd-crm");
    private static final By BD_CRM_OVERLAY_LOCATOR = By.className("bd-crm-overlay");
    private static final By FIX_DATA_LINK_LOCATOR  = By.className("reportIssue");

    protected String profileType;
    private By headerLocator = By.xpath(new XPathBuilder().byClassName("summary-content").byClassName("header").byClassName("title").build());
    private By crmBadgeLocator;

    public FullProfile() {
        tabControl.setTabControlXpath(TAB_CONTROL_XPATH);
        tabControl.setSelectedTabClass(ACTIVE_TAB_CLASS);
        addLoadingIndicatorCheckpoint();
        profileType = this.getClass().getSimpleName().replace("FullProfile", "");
    }

    public void setHeaderLocator(By locator) {
        this.headerLocator = locator;
        addCheckpointElement(headerLocator).mustBeVisible();
    }

    public String getScreenName(){
        return Driver.findVisible(GlobalNavigation.getTitleSelector()).getText();
    }

    public static String getActiveTabName(){
        BaseTabControl tabControl = new BaseTabControl();
        tabControl.setTabControlXpath(TAB_CONTROL_XPATH);
        tabControl.setSelectedTabClass(ACTIVE_TAB_CLASS);
        String tabName = StringUtils.capitalize(tabControl.getSelectedTabName().toLowerCase());
        return "Profile Tab " + StringHelper.splitByCapitals(tabName);
    }

    public void setCrmBadgeLocator(By locator) {
        this.crmBadgeLocator = locator;
    }

    public String getProfileName(){
        return Driver.findVisible(headerLocator).getText().trim();
    }

    public ProfileTab selectTab(String tabName) {
        tabControl.selectTab(tabName);
        waitReady();
        ProfileTab profileTab = new ProfileTab();
        profileTab.waitReady();
        return profileTab;
    }

    public ProfileTab selectTab(UITitles.ProfileTab tabName) {
        return selectTab(UITitles.get(tabName));
    }

    public FullProfile verifyTabsRowExists(){
        tabControl.verifyTabControlExists();
        return this;
    }

    public FullProfile verifyActiveTab(String tabName){
        tabControl.verifyActiveTab(tabName);
        return this;
    }

    public FullProfile verifyActiveTab(UITitles.ProfileTab tabName){
        tabControl.verifyActiveTab(tabName);
        return this;
    }

    public FullProfile verifyProfileName(String name) {
        String actualName = getProfileName().toUpperCase();
        String expectedName = name.trim().toUpperCase();
        Assert.assertEquals(actualName, expectedName, "Profile name displayed on "+profileType+" Profile is different from expected one: ");
        return this;
    }

    public FullProfile openBdVersusCrmOverlay(){
        SenchaWebElement bdVsCrmBadge = Driver.findVisible(BD_CRM_BADGE_CLASS);
        if (bdVsCrmBadge != null) {
            bdVsCrmBadge.click();
        } else {
            Logger.logError("No 'BD vs CRM' action button was found on the "+profileType+" Profile ["+getProfileName()+"]");
        }
        return this;
    }

    public FullProfile clickFixDataLink(){
        Driver.findVisible(FIX_DATA_LINK_LOCATOR).click();
        return this;
    }

    public FixDataOverlay openFixDataOverlay(){
        clickFixDataLink();
        return new FixDataOverlay();
    }

    public FullProfile verifyCrmBadge(boolean isInCrm) {
        SenchaWebElement crmBadge = Driver.findIfExists(crmBadgeLocator);
        if (isInCrm) {
            Verify.verifyNotNull(crmBadge, profileType + "'" + getProfileName() + "' expected to be in CRM, but no corresponding badge found.");
            crmBadge.click();
            verifyTooltip(CRM_MESSAGE_TEXT);
            waitTooltipClosed();
        } else {
            Verify.verifyNull(crmBadge, profileType + "'" + getProfileName() + "' was not expected to be in CRM, but the corresponding badge was found.");
        }
        return this;
    }

    public FullProfile closeOverlay(){
        BaseOverlay.closeActiveOverlay();
        return this;
    }

    protected SenchaWebElement getBdCrmOverlay(){
        return Driver.findVisible(BD_CRM_OVERLAY_LOCATOR);
    }

    protected FullProfile verifyBdVersusCrmInfo(List<String> entries) {
        SenchaWebElement bdCrmOverlay = getBdCrmOverlay();
        for (String entry : entries) {
            Verify.verifyMoreOrEquals(Driver.findAll(By.xpath(String.format(BD_CRM_ENTRY_XPATH, entry)), bdCrmOverlay).size(), 1,
                    "BD vs CRM table must contain 1 row (or 2 for [Address]) with label [" + entry + "] for " + profileType + " Profile [" + getProfileName() + "].");
        }
        return this;
    }

    public FullProfile verifyTooltip(String messageText) {
        SenchaWebElement fumMessage = Driver.findVisible(FUM_MESSAGE_LOCATOR);
        Assert.assertNotNull(fumMessage, "Tooltip was not found on "+profileType+" Profile ["+getProfileName()+"].");
        Verify.verifyContainsText(fumMessage.getText(), messageText, "Invalid tooltip message found:");
        waitTooltipClosed();
        return this;
    }

    protected void waitTooltipClosed(){
        addOneTimeCheckpoint(FUM_MESSAGE_LOCATOR).addVisibilityCondition(false);
        waitReady();
    }

    protected void verifyProfileNameParts(String shortName){
        String fullName = getProfileName();
        Assert.assertTrue(StringHelper.containsAllWords(fullName.toUpperCase(), shortName.toUpperCase()), "Active profile name mismatch: expected '" + shortName + "', but found '" + fullName + "'.");
    }

    protected String getProfileAttribute(By attributeSelector) {
        SenchaWebElement label = Driver.findVisible(attributeSelector);
        return (label != null) ? label.getText(): "";
    }

}
