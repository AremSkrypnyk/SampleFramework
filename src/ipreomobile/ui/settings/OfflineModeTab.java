package ipreomobile.ui.settings;

import ipreomobile.core.Driver;
import ipreomobile.core.ElementHelper;
import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.GlobalNavigation;
import ipreomobile.ui.ScreenCard;
import ipreomobile.ui.blocks.ConfirmationDialog;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

public class OfflineModeTab extends ScreenCard {

    private static final String TOGGLE_ON_CLASS                                             = "basetoggle-on";
    private static final String TOGGLE_OFF_CLASS                                            = "basetoggle-off";
    private static final String TOTAL_USED_DEFAULT_LABEL                                    = "0.0 Mb of ";
    private static final String CALENDAR_ACTIVITY_AND_RELATED_PROFILE_STORAGE_FIELD_LABEL   = "Calendar Activity and Related Profile Storage";
    private static final String PREVIOUSLY_VIEWED_PROFILE_STORAGE_FIELD_LABEL               = "Previously Viewed Profile Storage";
    private static final String STORAGE_ON_YOUR_DEVICE_FIELD_LABEL                          = "Storage on Your Device";
    private static final String STORE_ACTIVITIES_FROM_DEFAULT_LABEL                         = "1 Month Ago";
    private static final String STORE_ACTIVITIES_TO_DEFAULT_LABEL                           = "1 Month Ahead";

    private static final By CLEAR_STORED_DATA_BUTTON_LOCATOR    = By.xpath(new XPathBuilder().byClassName("clearStoredDataButton ").build());
    private static final By RESET_TO_DEFAULTS_BUTTON_LOCATOR    = By.xpath(new XPathBuilder().byClassName("resetToDefaultsButton ").build());
    private static final By STORE_ACTIVITIES_TOGGLE_LOCATOR     = By.xpath(new XPathBuilder().byTag("div").withClassName("x-toggle").build());
    private static final By TOTAL_USED_DEFAULT_FIELD_LOCATOR    = By.xpath(new XPathBuilder().byClassName("x-layout-box").byClassName("value ").withChildTextContains(TOTAL_USED_DEFAULT_LABEL).build());
    private static final By TOTAL_USED_FIELD_LOCATOR            = By.xpath(new XPathBuilder().byClassName("x-form-fieldset-inner").withChildTextContains("Total Used").build());
    private static final By LAST_VIEWED_FIELD_LOCATOR           = By.xpath(new XPathBuilder().byClassName("x-form-fieldset-inner").withChildTextContains("Last Viewed").build());

    private static final String FIELD_TITLE_TEMPLATE = new XPathBuilder().byClassName("x-form-fieldset-title")
            .withChildTextContains("%s").build();

    private static final By STORE_ACTIVITIES_FROM_FIELD_NAME_LOCATOR = By.xpath(new XPathBuilder().byClassName("x-button").byClassName("label").withText("From").build());
    private static final By STORE_ACTIVITIES_FROM_FIELD_VALUE_LOCATOR = By.xpath(new XPathBuilder().byClassName("x-button").withChildTag("span").withClassName("label")
            .withText("From").byClassName("value").build());

    private static final By STORE_ACTIVITIES_TO_FIELD_NAME_LOCATOR = By.xpath(new XPathBuilder().byClassName("x-button").byClassName("label").withText("To").build());
    private static final By STORE_ACTIVITIES_TO_FIELD_VALUE_LOCATOR = By.xpath(new XPathBuilder().byClassName("x-button").withChildTag("span").withClassName("label")
            .withText("To").byClassName("value").build());

    private static final String OPTION_LIST_TEMPLATE = new XPathBuilder().byClassName("options-list").byClassName("row").withChildTextContains("%s").build();

    private GlobalNavigation navigation = new GlobalNavigation();

    public OfflineModeTab() {
        addCheckpointElement(CLEAR_STORED_DATA_BUTTON_LOCATOR);
        waitReady();
    }

    public OfflineModeTab selectStoreActivitiesFrom(String data){
        Driver.findVisible(STORE_ACTIVITIES_FROM_FIELD_NAME_LOCATOR).click();
        addCheckpointElement(By.xpath(String.format(OPTION_LIST_TEMPLATE, "The Beginning of This Month")));
        waitReady();
        Driver.findVisible(By.xpath(String.format(OPTION_LIST_TEMPLATE, data))).click();
        navigation.back();
        return new OfflineModeTab();
    }

    public OfflineModeTab selectStoreActivitiesTo(String data){
        Driver.findVisible(STORE_ACTIVITIES_TO_FIELD_NAME_LOCATOR).click();
        addCheckpointElement(By.xpath(String.format(OPTION_LIST_TEMPLATE, "The End of This Month")));
        waitReady();
        Driver.findVisible(By.xpath(String.format(OPTION_LIST_TEMPLATE, data))).click();
        navigation.back();
        return new OfflineModeTab();
    }

    public OfflineModeTab turnOffStoringActivities(){
       SenchaWebElement storeActivitiesToggle = Driver.findVisible(STORE_ACTIVITIES_TOGGLE_LOCATOR);
        if (storeActivitiesToggle.hasClass(TOGGLE_ON_CLASS)){
            storeActivitiesToggle.click();
            ConfirmationDialog confirmationDialog = new ConfirmationDialog();
            confirmationDialog.clickYes();
        }
        return this;
    }

    public OfflineModeTab turnOnStoringActivities(){
       SenchaWebElement storeActivitiesToggle = Driver.findVisible(STORE_ACTIVITIES_TOGGLE_LOCATOR);
        if (storeActivitiesToggle.hasClass(TOGGLE_OFF_CLASS))
            storeActivitiesToggle.click();
        return this;
    }

    public OfflineModeTab clearStoredData(){
        Driver.findVisible(CLEAR_STORED_DATA_BUTTON_LOCATOR).click();
        addCheckpointElement(TOTAL_USED_DEFAULT_FIELD_LOCATOR);
        waitReady();

        return this;
    }

    public OfflineModeTab resetToDefaults(){
        Driver.findVisible(RESET_TO_DEFAULTS_BUTTON_LOCATOR).click();
        verifyFieldsHaveBeenResetToDefaults();
        return this;
    }

    public OfflineModeTab verifyCalendarActivityAndRelatedProfileStorageTitle(){
        SenchaWebElement field = Driver.findVisible(By.xpath(String.format(FIELD_TITLE_TEMPLATE, CALENDAR_ACTIVITY_AND_RELATED_PROFILE_STORAGE_FIELD_LABEL)));
        Assert.assertNotNull(field, CALENDAR_ACTIVITY_AND_RELATED_PROFILE_STORAGE_FIELD_LABEL + " title expected to not be null");
        return this;
    }

    public OfflineModeTab verifyStoreActivitiesToggle(){
        SenchaWebElement toggle = Driver.findVisible(STORE_ACTIVITIES_TOGGLE_LOCATOR);
        Assert.assertNotNull(toggle, "Toggle expected to not be null");
        return this;
    }

    public OfflineModeTab verifyPreviouslyViewedProfileStorageTitle(){
        SenchaWebElement field = Driver.findVisible(By.xpath(String.format(FIELD_TITLE_TEMPLATE, PREVIOUSLY_VIEWED_PROFILE_STORAGE_FIELD_LABEL)));
        Assert.assertNotNull(field, PREVIOUSLY_VIEWED_PROFILE_STORAGE_FIELD_LABEL + " title expected to not be null");
        return this;
    }

    public OfflineModeTab verifyPreviouslyViewedProfileStorageLastViewedField(){
        SenchaWebElement field = Driver.findVisible(LAST_VIEWED_FIELD_LOCATOR);
        Assert.assertNotNull(field, "Last Viewed field expected to not be null");
        return this;
    }

    public OfflineModeTab verifyStorageOnYourDeviceTitle(){
        SenchaWebElement field = Driver.findVisible(By.xpath(String.format(FIELD_TITLE_TEMPLATE, STORAGE_ON_YOUR_DEVICE_FIELD_LABEL)));
        Assert.assertNotNull(field, STORAGE_ON_YOUR_DEVICE_FIELD_LABEL + " title expected to not be null");
        return this;
    }

    public OfflineModeTab verifyStorageOnYourDeviceTotalUsedField(){
        SenchaWebElement field = Driver.findVisible(TOTAL_USED_FIELD_LOCATOR);
        Assert.assertNotNull(field,"Total Used field expected to not be null");
        return this;
    }

    public OfflineModeTab verifyClearedStoredDataButton(){
        SenchaWebElement button = Driver.findVisible(CLEAR_STORED_DATA_BUTTON_LOCATOR);
        Assert.assertNotNull(button, "Clear Stored Data button expected to not be null");
        return this;
    }

    public OfflineModeTab verifyResetToDefaultsButton(){
        SenchaWebElement button = Driver.findVisible(CLEAR_STORED_DATA_BUTTON_LOCATOR);
        Assert.assertNotNull(button, "Reset To Default button expected to not be null");
        return this;
    }
    private OfflineModeTab verifyFieldsHaveBeenResetToDefaults(){
        SenchaWebElement storeActivitiesToggle = Driver.findVisible(STORE_ACTIVITIES_TOGGLE_LOCATOR);
        Assert.assertTrue(storeActivitiesToggle.hasClass(TOGGLE_ON_CLASS), "Toggle wasn't reset");
        SenchaWebElement storeActivitiesFrom = Driver.findVisible(STORE_ACTIVITIES_FROM_FIELD_VALUE_LOCATOR);
        Assert.assertEquals(storeActivitiesFrom.getText().trim(), STORE_ACTIVITIES_FROM_DEFAULT_LABEL, "Store Activities From wasn't reset");
        SenchaWebElement storeActivitiesTo = Driver.findVisible(STORE_ACTIVITIES_TO_FIELD_VALUE_LOCATOR);
        Assert.assertEquals(storeActivitiesTo.getText().trim(), STORE_ACTIVITIES_TO_DEFAULT_LABEL, "Store Activities To wasn't reset");
        return this;
    }

}
