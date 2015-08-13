package ipreomobile.ui.activities;

import ipreomobile.core.*;
import ipreomobile.templates.ui.BaseOverlay;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.blocks.DateTimePicker;
import org.openqa.selenium.By;

public class DateTimeSelectionOverlay extends BaseOverlay {

    private static final By STARTS_FIELD_LOCATOR = By.xpath(new XPathBuilder()
            .byTag("div").withClassName("select-date-time").byTag("span").withText("Starts").build());
    private static final By ENDS_FIELD_LOCATOR = By.xpath(new XPathBuilder()
            .byTag("div").withClassName("select-date-time").byTag("span").withText("Ends").build());
    private static final By ALL_DAY_TOGGLE_LOCATOR = By.xpath(new XPathBuilder()
            .byTag("div").withClassName("x-toggle").withIdContains("ext-slider").build());

    private static final String ALL_DAY__TOGGLE_ON_CLASS_NAME  = "basetoggle-on";
    private static final String ALL_DAY__TOGGLE_OFF_CLASS_NAME = "basetoggle-off";

    private static final By TIME_ZONE_BUTTON_XPATH = By.xpath(new XPathBuilder().byClassName("time-zone").build());

    private static final By CONTAINER_SELECTOR = By.xpath(new XPathBuilder().byClassName("datetime-popup").build());

    protected SenchaWebElement container;

    public DateTimeSelectionOverlay(){
        super(UITitles.OverlayType.DATE_TIME);
        resetCheckpointElements();
        addCheckpointElement(CONTAINER_SELECTOR).mustBeVisible();
        waitReady();
        container = Driver.findVisible(CONTAINER_SELECTOR);
    }

    public DateTimePicker selectStartDateTime(){
        Driver.findVisible(STARTS_FIELD_LOCATOR, container).click();
        return new DateTimePicker();
    }

    public void selectStartDateTime(String startDate){
        selectStartDateTime().selectDateAndTime(startDate);
    }

    public DateTimePicker selectEndDateTime(){
        Driver.findVisible(ENDS_FIELD_LOCATOR, container).click();
        return new DateTimePicker();
    }

    public void selectEndDateTime(String endDate){
        selectEndDateTime().selectDateAndTime(endDate);
    }

    public DateTimeSelectionOverlay turnOnAllDayActivity(){
        SenchaWebElement allDayToggle = Driver.findOne(ALL_DAY_TOGGLE_LOCATOR);
        if (allDayToggle.hasClass(ALL_DAY__TOGGLE_OFF_CLASS_NAME))
            allDayToggle.click();
        return this;
    }

    public DateTimeSelectionOverlay turnOffAllDayActivity(){
        SenchaWebElement allDayToggle = Driver.findOne(ALL_DAY_TOGGLE_LOCATOR, container);
        if (allDayToggle.hasClass(ALL_DAY__TOGGLE_ON_CLASS_NAME))
            allDayToggle.click();
        return this;
    }

    public TimeZoneOverlay selectTimeZone(String timeZone){
        Driver.findOne(TIME_ZONE_BUTTON_XPATH, container).click();
        return new TimeZoneOverlay(timeZone);
    }

}
