package ipreomobile.ui.settings;

import ipreomobile.core.Driver;
import ipreomobile.core.ElementHelper;
import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.GlobalNavigation;
import ipreomobile.ui.ScreenCard;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

public class GlobalizationTab extends ScreenCard {
    private static final By DATE_TIME_CONTAINER_LOCATOR = By.className("date-time-set");
    private static final By HELP_TEXT_LOCATOR           = By.className("info");
    private static final By TIME_ZONE_VALUE_LOCATOR     = By.xpath(new XPathBuilder().byClassName("timeZone-select").byClassName("value").build());

    private static final String VALUE_TEMPLATE          = new XPathBuilder().byClassName("x-container").byClassName("x-button")
            .withChildTextContains("%s").build();
    private static final String OPTION_LIST_TEMPLATE    = new XPathBuilder().byClassName("options-list").byClassName("row").withChildTextContains("%s").build();
    private static final String TOGGLE_TEMPLATE         = new XPathBuilder().byClassName("%s").byTag("div").withClassName("x-toggle").build();
    private static final By CURRENCY_VALUE_LOCATOR      = By.xpath(new XPathBuilder()
            .byClassName("x-container").byClassName("x-button").withChildTextContains("Currency").byClassName("value").build());
    private static final String TOGGLE_ON_CLASS  = "basetoggle-on";
    private static final String TOGGLE_OFF_CLASS = "basetoggle-off";

    private GlobalNavigation navigation = new GlobalNavigation();

    public GlobalizationTab() {
        addCheckpointElement(DATE_TIME_CONTAINER_LOCATOR);
        waitReady();
    }

    public GlobalizationTab verifyHelpText(String expectedText) {
        String actualText = Driver.findIfExists(HELP_TEXT_LOCATOR).getText();
        Assert.assertEquals(actualText, expectedText, "Text is not as expected. Actual text is " + actualText);
        return this;
    }

    public GlobalizationTab setLanguage(String language) {
        Driver.findVisible(By.xpath(String.format(VALUE_TEMPLATE, "Language"))).click();
        addCheckpointElement(By.xpath(String.format(OPTION_LIST_TEMPLATE, "English")));
        waitReady();
        Driver.findVisible(By.xpath(String.format(OPTION_LIST_TEMPLATE, language)));
        navigation.back();
        return new GlobalizationTab();
    }

    public GlobalizationTab setCurrency(String currencyName, String currencyLabel) {
        Driver.findVisible(By.xpath(String.format(VALUE_TEMPLATE, "Currency"))).click();
        addCheckpointElement(By.xpath(String.format(OPTION_LIST_TEMPLATE, "United States Dollar (USD)")));
        waitReady();
        navigation.verifyPageTitle("Currency");
        Driver.findVisible(By.xpath(String.format(OPTION_LIST_TEMPLATE, currencyName))).click();
        navigation.back();
        System.setProperty("test.currency", currencyLabel);
        return new GlobalizationTab();
    }

    public GlobalizationTab setNumberFormat(String numberFormat) {
        Driver.findVisible(By.xpath(String.format(VALUE_TEMPLATE, "Number Format"))).click();
        addCheckpointElement(By.xpath(String.format(OPTION_LIST_TEMPLATE, "100 000,23")));
        waitReady();
        Driver.findVisible(By.xpath(String.format(OPTION_LIST_TEMPLATE, numberFormat))).click();
        navigation.back();
        return new GlobalizationTab();
    }

    public GlobalizationTab setDateFormat(String dateFormat) {
        Driver.findVisible(By.xpath(String.format(VALUE_TEMPLATE, "Date Format"))).click();
        addCheckpointElement(By.xpath(String.format(OPTION_LIST_TEMPLATE, "dd.mm.yyyy")));
        waitReady();
        Driver.findVisible(By.xpath(String.format(OPTION_LIST_TEMPLATE, dateFormat))).click();
        navigation.back();
        return new GlobalizationTab();
    }

    public GlobalizationTab setTimeZone(String timeZone) {
        Driver.findVisible(By.xpath(String.format(VALUE_TEMPLATE, "Time Zone"))).click();
        addCheckpointElement(By.xpath(String.format(OPTION_LIST_TEMPLATE, "Central Standard Time")));
        waitReady();
        Driver.findVisible(By.xpath(String.format(OPTION_LIST_TEMPLATE, timeZone))).click();
        navigation.back();
        return new GlobalizationTab();
    }

    public GlobalizationTab turnOnAutomaticallySettingTimeZone() {
       SenchaWebElement autoTimeZoneToggle = Driver.findVisible(By.xpath(String.format(TOGGLE_TEMPLATE, "time-zone-set")));
        if (autoTimeZoneToggle.hasClass(TOGGLE_OFF_CLASS)){
            autoTimeZoneToggle.click();
        }
        return new GlobalizationTab();
    }

    public GlobalizationTab turnOffAutomaticallySettingTimeZone() {
       SenchaWebElement autoTimeZoneToggle = Driver.findVisible(By.xpath(String.format(TOGGLE_TEMPLATE, "time-zone-set")));
        if (autoTimeZoneToggle.hasClass(TOGGLE_ON_CLASS)){
            autoTimeZoneToggle.click();
        }
        return new GlobalizationTab();
    }

    public GlobalizationTab turnOn24HourTimeFormat() {
       SenchaWebElement twentyFourHourTimeToggle = Driver.findVisible(By.xpath(String.format(TOGGLE_TEMPLATE, "date-time-set")));
        if (twentyFourHourTimeToggle.hasClass(TOGGLE_OFF_CLASS)){
            twentyFourHourTimeToggle.click();
        }
        return new GlobalizationTab();
    }

    public GlobalizationTab turnOff24HourTimeFormat() {
       SenchaWebElement twentyFourHourTimeToggle = Driver.findVisible(By.xpath(String.format(TOGGLE_TEMPLATE, "date-time-set")));
        if (twentyFourHourTimeToggle.hasClass(TOGGLE_ON_CLASS)){
            twentyFourHourTimeToggle.click();
        }
        return new GlobalizationTab();
    }

    public GlobalizationTab verifyCurrencyValue(String expectedCurrency) {
        String actualCurrency = Driver.findVisible(CURRENCY_VALUE_LOCATOR).getText();
        Assert.assertEquals(actualCurrency, expectedCurrency, "Currency is not as expected. Actual currency is " + actualCurrency);
        return new GlobalizationTab();
    }

    public GlobalizationTab verifyTimeZoneValue(String expectedTimeZone) {
        String actualTimeZone = Driver.findVisible(TIME_ZONE_VALUE_LOCATOR).getText();
        Assert.assertEquals(actualTimeZone, expectedTimeZone, "Time Zone is not as expected. Actual time zone is " + actualTimeZone);
        return new GlobalizationTab();
    }
}
