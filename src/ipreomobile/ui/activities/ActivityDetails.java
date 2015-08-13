package ipreomobile.ui.activities;

import ipreomobile.core.Driver;
import ipreomobile.core.ElementHelper;
import ipreomobile.core.SenchaWebElement;
import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.ScreenCard;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.VerticalScroller;
import ipreomobile.ui.blocks.overlay.ListOverlayFilter;
import ipreomobile.ui.blocks.overlay.ListOverlaySearchMultiSelect;
import org.openqa.selenium.By;
import org.testng.Assert;

public abstract class ActivityDetails extends ScreenCard {

    //TODO:UI Coverage for internalParticipants, alertRecipients

    String CLEAR_ICON_CLASSNAME = "x-clear-icon";

    private static final By DETAILS_CONTAINER_LOCATOR = By.xpath(new XPathBuilder().byClassName("activity-form").build());
    private static final By SUBJECT_FIELD_LOCATOR = By.xpath(new XPathBuilder().byClassName("subject-field").byClassName("x-input-el").build());
    private static final By TYPE_FIELD_LOCATOR = By.xpath(new XPathBuilder().byClassName("activity-field").byClassName("label").withText("Type").build());
    private static final By TOPIC_FIELD_LOCATOR = By.xpath(new XPathBuilder().byClassName("activity-field").byClassName("label").withText("Topic").build());
    private static final By LOCATION_FIELD_LOCATOR = By.xpath(new XPathBuilder().byClassName("location-field").byClassName("label").withText("Location").build());
    private static final By SYMBOL_BUTTON_LOCATOR = By.xpath(new XPathBuilder().byClassName("symbol-button").build());
    private static final By NOTES_FIELD_LOCATOR = By.xpath(new XPathBuilder()
            .byClassName("dialog-field").withNoClassName("x-item-hidden")
            .byClassName("input-box", "notes-field", "editable-outer").build());
    private static final By DATE_TIME_BUTTON_LOCATOR = By.xpath(new XPathBuilder().byClassName("date-time").build());
    private static final By MACRO_FIELD_LOCATOR = By.xpath(new XPathBuilder().byClassName("industry-field").byClassName("label").withText("Macro").build());
    private static final By MID_FIELD_LOCATOR = By.xpath(new XPathBuilder().byClassName("industry-field").byClassName("label").withText("Mid").build());
    private static final By MICRO_FIELD_LOCATOR = By.xpath(new XPathBuilder().byClassName("industry-field").byClassName("label").withText("Micro").build());

    private static final By HEADER_CHECKPOINT_LOCATOR = By.className("details-header");
    private static final By ACTIVITY_CONTROL_PANEL_LOCATOR = By.xpath(new XPathBuilder().byClassName("activity-form").byClassName("x-toolbar").build());

    private SenchaWebElement container;

    public ActivityDetails(){
        addCheckpointElement(HEADER_CHECKPOINT_LOCATOR).mustBeVisible();
        addCheckpointElement(TYPE_FIELD_LOCATOR).mustBeVisible();
        waitReady();
        container = Driver.findVisible(DETAILS_CONTAINER_LOCATOR);
    }

    public void setupSubject(String subject){
        SenchaWebElement subjectField = Driver.findOne(SUBJECT_FIELD_LOCATOR, container);
        subjectField.bringToView();
        subjectField.sendKeys(subject);
    }

    public void setupNotes(String note){
        SenchaWebElement notesField = Driver.findOne(NOTES_FIELD_LOCATOR, container);
        notesField.bringToView();
        clearField(notesField);
        inputTextIntoField(notesField, note);
    }

    protected void inputTextIntoField(SenchaWebElement field, String text){
        field.sendKeys(text);
    }

    protected void clearField(SenchaWebElement field){
        SenchaWebElement clearIcon = Driver.findVisible(By.className(CLEAR_ICON_CLASSNAME), field);
        if (clearIcon != null)
            clearIcon.click();
    }

    protected void clickOnField(By fieldSelector){
        SenchaWebElement field = Driver.findFirst(fieldSelector, container);
        Assert.assertNotNull(field, "Cannot click on field : no such field was found.");
        SenchaWebElement panelOverlappingActivityForm = Driver.findVisible(ACTIVITY_CONTROL_PANEL_LOCATOR);
        field.bringToView(panelOverlappingActivityForm);
        field.click();
    }

    public ListOverlayFilter openTypeOverlay(){
        clickOnField(TYPE_FIELD_LOCATOR);
        return new ListOverlayFilter(UITitles.OverlayType.ACTIVITY_TYPE);
    }

    public ListOverlayFilter openTopicOverlay(){
        clickOnField(TOPIC_FIELD_LOCATOR);
        return new ListOverlayFilter(UITitles.OverlayType.ACTIVITY_TOPIC);
    }

    public ListOverlayFilter openMacroOverlay(){
        clickOnField(MACRO_FIELD_LOCATOR);
        return new ListOverlayFilter(UITitles.OverlayType.MACRO);
    }

    //TODO:check that field is enabled
    public ListOverlayFilter openMidOverlay(){
        clickOnField(MID_FIELD_LOCATOR);
        return new ListOverlayFilter(UITitles.OverlayType.MID);
    }

    //TODO:check that field is enabled
    public ListOverlayFilter openMicroOverlay(){
        clickOnField(MICRO_FIELD_LOCATOR);
        return new ListOverlayFilter(UITitles.OverlayType.MICRO);
    }

    //TODO: add location
    public ListOverlayFilter openLocationOverlay(){
        clickOnField(LOCATION_FIELD_LOCATOR);
        return new ListOverlayFilter(UITitles.OverlayType.LOCATION);
    }

    public ListOverlaySearchMultiSelect openSymbolOverlay(){
        clickOnField(SYMBOL_BUTTON_LOCATOR);
        return new ListOverlaySearchMultiSelect(UITitles.OverlayType.SYMBOL);
    }

    public DateTimeSelectionOverlay openDateTimeSelection(){
        clickOnField(DATE_TIME_BUTTON_LOCATOR);
        return new DateTimeSelectionOverlay();
    }
}
