package ipreomobile.ui.blocks;

import ipreomobile.core.DateTimeHelper;
import ipreomobile.core.Logger;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.BaseOverlay;
import org.openqa.selenium.By;

public class DateTimePicker extends BaseDatePicker {

    private final By TITLE_SELECTOR                  = By.xpath(new XPathBuilder().byTag("div").withClassName("datetime-popup").byTag("div").byClassName("x-title").build());

    private static final int MONTH_DAY_SCROLLER_INDEX = 0;
    private static final int YEAR_SCROLLER_INDEX = 1;
    private static final int HOUR_SCROLLER_INDEX = 2;
    private static final int MINUTE_SCROLLER_INDEX = 3;
    private static final int DAY_TIME_SCROLLER_INDEX = 4;

    public DateTimePicker() {
        super();
        addCheckpointElement(TITLE_SELECTOR);
        waitReady();
    }

    public DateTimePicker selectMonthDay(String monthDay) {
        selectData(MONTH_DAY_SCROLLER_INDEX, monthDay);
        return this;
    }

    public DateTimePicker selectYear(String year) {
        selectData(YEAR_SCROLLER_INDEX, year);
        return this;
    }

    public DateTimePicker selectHours(String hours) {
        selectData(HOUR_SCROLLER_INDEX, hours);
        return this;
    }

    public DateTimePicker selectMinutes(String minutes) {
        selectData(MINUTE_SCROLLER_INDEX, minutes);
        return this;
    }

    public DateTimePicker selectPeriodOfDay(String periodOfDay) {
        selectData(DAY_TIME_SCROLLER_INDEX, periodOfDay);
        return this;
    }

    public DateTimePicker selectDate(String date) {
        selectYear(DateTimeHelper.getYearFromDateStr(date));
        selectMonthDay(DateTimeHelper.getMonthDayFromDateStr(date));
        return this;
    }

    public DateTimePicker selectTime(String time) {
        selectHours(DateTimeHelper.getHourFromTimeStr(time));
        selectMinutes(DateTimeHelper.getMinutesFromTimeStr(time));
        selectPeriodOfDay(DateTimeHelper.getPeriodFromTimeStr(time));
        return this;
    }

    public void selectDateAndTime(String dateTimeStr) {
        String dateStr = DateTimeHelper.getDateFromDateTimeStr(dateTimeStr);
        String timeStr = DateTimeHelper.getTimeFromDateTimeStr(dateTimeStr);
        selectDate(dateStr);
        selectTime(timeStr);
        Logger.logDebugScreenshot("Date and time selected before closing.");
        close();
    }

    public void close(){
        BaseOverlay.closeActiveOverlay();
    }

}
