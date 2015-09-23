package ipreomobile.ui.blocks;

import ipreomobile.core.*;
import ipreomobile.templates.ui.SingleSelectListImpl;
import ipreomobile.ui.UITitles;
import org.openqa.selenium.By;
import org.testng.Assert;

public class DatePicker extends BaseDatePicker {

    private static final By TITLE_LOCATOR            = By.xpath(new XPathBuilder()
            .byClassName("time-picker-title").withChildText(UITitles.get(UITitles.PickerType.SELECT_DATE)).build());

    private static final By DONE_BUTTON_LOCATOR      = By.xpath(new XPathBuilder().byTag("div").withClassName("x-button").withChildText("Done").build());
    private static final By RESET_BUTTON_LOCATOR     = By.xpath(new XPathBuilder().byTag("span").withClassName("x-button-label").withText("Reset").build());
    private static final By CANCEL_BUTTON_LOCATOR    = By.xpath(new XPathBuilder().byTag("span").withClassName("x-button-label").withText("Cancel").build());

    private static final int MONTH_SCROLLER_INDEX = 0;
    private static final int DAY_SCROLLER_INDEX = 1;
    private static final int YEAR_SCROLLER_INDEX = 2;

    public DatePicker() {
        super();
        addCheckpointElement(TITLE_LOCATOR);
        waitReady();
    }

    public DatePicker selectMonth(String month) {
        selectData(MONTH_SCROLLER_INDEX, month);
        return this;
    }

    public DatePicker selectDay(String day) {
        selectData(DAY_SCROLLER_INDEX, day);
        return this;
    }

    public DatePicker selectYear(String year) {
        selectData(YEAR_SCROLLER_INDEX, year);
        return this;
    }


    public void selectDate(String date) {
        selectYear(DateTimeHelper.getYearFromDateStr(date));
        selectMonth(DateTimeHelper.getMonthFromDateStr(date));

        String day = DateTimeHelper.getDayFromDateStr(date);
        if (day.startsWith("0")) {
            day = day.substring(1);
        }
        selectDay(day);
        done();
    }

    @Override
    public void select(String name) {
        click(name);
    }


    public void done() {
        SenchaWebElement doneButton = Driver.findVisible(DONE_BUTTON_LOCATOR, getActivePicker());
        doneButton.click();
    }

    public void reset() {
        SenchaWebElement resetButton = Driver.findVisible(RESET_BUTTON_LOCATOR, getActivePicker());
        resetButton.click();
    }

    public void cancel() {
        SenchaWebElement cancelButton = Driver.findVisible(CANCEL_BUTTON_LOCATOR, getActivePicker());
        cancelButton.click();
    }

}
