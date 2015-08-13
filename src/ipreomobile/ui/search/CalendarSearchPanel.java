package ipreomobile.ui.search;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.BaseSearchPanel;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.blocks.DatePicker;
import ipreomobile.ui.blocks.overlay.ListOverlayFilter;
import ipreomobile.ui.blocks.overlay.ListOverlaySearchMultiSelect;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

public class CalendarSearchPanel extends BaseSearchPanel {
    private static final By ACTIVITY_TYPE_LOCATOR  = By.xpath(new XPathBuilder().byClassName("x-innerhtml").withChildText("Type").build());
    private static final By ACTIVITY_TOPIC_LOCATOR = By.xpath(new XPathBuilder().byClassName("x-innerhtml").withChildText("Topic").build());
    private static final By START_DATE_LOCATOR     = By.xpath(new XPathBuilder().byClassName("x-field-select").withChildTag("input").withName("start_dt").build());
    private static final By END_DATE_LOCATOR       = By.xpath(new XPathBuilder().byClassName("x-field-select").withChildTag("input").withName("end_dt").build());
    private static final By SYMBOL_BUTTON_LOCATOR  = By.xpath(new XPathBuilder().byClassName("plus").build());

    public CalendarSearchPanel(){
    }

    public CalendarSearchPanel setSubject(String subject){
        super.setSearchField(subject);
        return this;
    }

    public ListOverlayFilter selectActivityType(){
        Driver.get().findElement(ACTIVITY_TYPE_LOCATOR).click();
        return new ListOverlayFilter(UITitles.OverlayType.ACTIVITY_TYPE);
    }

    public CalendarSearchPanel selectActivityType(String activityType){
        Driver.get().findElement(ACTIVITY_TYPE_LOCATOR).click();
        ListOverlayFilter overlay = new ListOverlayFilter(UITitles.OverlayType.ACTIVITY_TYPE);
        overlay.findAndSelect(activityType);
        return this;
    }

    public ListOverlayFilter selectActivityTopic(){
        Driver.get().findElement(ACTIVITY_TOPIC_LOCATOR).click();
        return new ListOverlayFilter(UITitles.OverlayType.ACTIVITY_TOPIC);
    }

    public CalendarSearchPanel selectActivityTopic(String activityTopic){
        Driver.get().findElement(ACTIVITY_TOPIC_LOCATOR).click();
        ListOverlayFilter overlay = new ListOverlayFilter(UITitles.OverlayType.ACTIVITY_TOPIC);
        overlay.findAndSelect(activityTopic);
        return this;
    }

    public DatePicker selectStartDate(){
        Driver.findVisible(START_DATE_LOCATOR).click();
        return new DatePicker();
    }

    public DatePicker selectEndDate(){
        Driver.findVisible(END_DATE_LOCATOR).click();
        return new DatePicker();
    }

    public CalendarSearchPanel selectStartDate(String date){
        selectStartDate().selectDate(date);
        return this;
    }

    public CalendarSearchPanel selectEndDate(String date){
        selectEndDate().selectDate(date);
        return this;
    }

    public ListOverlaySearchMultiSelect addSymbol(){
        Driver.get().findElement(SYMBOL_BUTTON_LOCATOR).click();
        return new ListOverlaySearchMultiSelect(UITitles.OverlayType.SYMBOL);
    }

    public CalendarSearchPanel addSymbol(String symbolToSearch, String... symbolsToSelect){
        Driver.get().findElement(SYMBOL_BUTTON_LOCATOR).click();
        ListOverlaySearchMultiSelect overlay = new ListOverlaySearchMultiSelect(UITitles.OverlayType.SYMBOL);
        overlay.findAndSelect(symbolToSearch, symbolsToSelect);
        overlay.close();
        return this;
    }

    public void verifyStartDate(String startDate, String description) {
        String actualStartDate = getDateFieldValue(START_DATE_LOCATOR);
        Assert.assertEquals(actualStartDate, startDate, description + "Actual start date doesn't correspond to expected one: ");
    }

    public void verifyEndDate(String endDate, String description) {
        String actualEndDate = getDateFieldValue(END_DATE_LOCATOR);
        Assert.assertEquals(actualEndDate, endDate, description + "Actual end date doesn't correspond to expected one: ");
    }

    private String getDateFieldValue(By fieldLocator){
       SenchaWebElement dateField = Driver.findVisible(fieldLocator);
        return dateField.findElement(By.className("x-input-text")).getAttribute("value");
    }

}
