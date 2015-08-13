package ipreomobile.ui.activities;

import ipreomobile.core.*;
import ipreomobile.templates.ui.FullProfile;
import ipreomobile.templates.ui.ParticipantsList;
import ipreomobile.templates.ui.ProfileOverviewController;
import ipreomobile.ui.ScreenCard;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.blocks.Carousel;
import ipreomobile.ui.blocks.ConfirmationDialog;
import ipreomobile.ui.blocks.KeyValueTable;
import org.openqa.selenium.By;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static ipreomobile.ui.UITitles.ActivityCardTitle.*;

public class ActivityDetailsOverview extends ScreenCard implements ProfileOverviewController {
    private static final String CONTAINER_CLASS     = "view-activity-container";
    private static final By CARD_TITLE_LOCATOR = By.xpath(new XPathBuilder()
            .byClassName(CONTAINER_CLASS)
            .byClassName("title-bar")
            .byClassName("x-title").build());
    private static final By EDIT_LOCATOR = By.xpath(new XPathBuilder().byClassName("x-button").withChildTag("*").withClassName("edit").build());
    private static final By DELETE_LOCATOR = By.xpath(new XPathBuilder().byClassName("x-button").withChildTag("*").withClassName("trash").build());
    private static final List<UITitles.ActivityCardTitle> CARD_HEADERS = Arrays.asList(
            ACTIVITY_DETAILS,
            LOCATION_MAP,
            NOTES,
            EXTERNAL_PARTICIPANTS,
            INTERNAL_PARTICIPANTS,
            ALERT_RECIPIENTS
    );
    private static final By SUBHEADER_LOCATOR = By.xpath(new XPathBuilder()
            .byClassName(CONTAINER_CLASS)
            .byClassName("title-bar")
            .byClassName("x-title")
            .byClassName("name")
            .build());

    private static final By CALENDAR_LABEL_LOCATOR = By.className("calendar-body");
    private static final By SUBJECT_LOCATOR = By.className("subj");
    private static final By TIME_SLOT_LOCATOR = By.xpath(new XPathBuilder()
            .byClassName("header")
            .byTag("p")
            .withClassName("row")
            .withNoClassName("subj")
            .build());
    private static final By MONTH_LOCATOR = By.className("month");
    private static final By DAY_LOCATOR = By.className("day");

    private KeyValueTable activityDetails = new KeyValueTable(
            new XPathBuilder().byClassName("body").byClassName("row").build(),
            new XPathBuilder().byClassName("label").build(),
            new XPathBuilder().byClassName("value").build()
    );

    private Carousel carousel = new Carousel();
    private SenchaWebElement container;

    private ParticipantsList participantsList = new ParticipantsList();

    public ActivityDetailsOverview() {
        addLoadingIndicatorCheckpoint();
        addCheckpointElement(CARD_TITLE_LOCATOR).addVisibilityCondition(true);
        waitReady();
        carousel.setContainerXpath(new XPathBuilder().byClassName(CONTAINER_CLASS).build());
        container = carousel.getActiveCarouselItem();
    }

    public String getActiveCardHeader(){
        return Driver.findVisibleNow(CARD_TITLE_LOCATOR).getText().trim();
    }

    public String getActiveCardSubHeader(){
        String subHeaderText = "";
        SenchaWebElement subHeader = Driver.findVisibleNow(SUBHEADER_LOCATOR);
        if (subHeader == null) {
            Logger.logError("Cannot fetch subtitle from Activity Overview card '"+getActiveCardHeader()+"'.");
        } else {
            subHeaderText = subHeader.getText().trim();
        }
        return subHeaderText;
    }

    public ActivityDetailsOverview verifyActiveCardHeader(String expectedHeader){
        Verify.verifyStartsWith(getActiveCardHeader(), expectedHeader, "Invalid card is shown in Activity Profile Overview: ");
        return this;
    }

    public ActivityDetailsOverview verifyActiveCardSubHeader(String expectedSubHeader){
        Verify.verifyStartsWith(getActiveCardSubHeader(), expectedSubHeader, "Invalid card is shown in Activity Profile Overview: ");
        return this;
    }

    public ActivityDetailsOverview openActivityDetailsCard(){
        openCard(ACTIVITY_DETAILS);
        activityDetails.setContainer(carousel.getActiveCarouselItem());
        return this;
    }

    public ActivityDetailsOverview openLocationMapCard(){
        openCard(LOCATION_MAP);
        return this;
    }

    public ActivityDetailsOverview openNotesCard(){
        openCard(NOTES);
        return this;
    }

    public ActivityDetailsOverview openExternalParticipantsCard(){
        openCard(EXTERNAL_PARTICIPANTS);
        participantsList.setListContainer(carousel.getActiveCarouselItem());
        return this;
    }

    public ActivityDetailsOverview openInternalParticipantsCard(){
        openCard(INTERNAL_PARTICIPANTS);
        participantsList.setListContainer(carousel.getActiveCarouselItem());
        return this;
    }

    public ActivityDetailsOverview openAlertRecipientsCard(){
        openCard(ALERT_RECIPIENTS);
        participantsList.setListContainer(carousel.getActiveCarouselItem());
        return this;
    }

    public ActivityDetailsOverview verifyParticipantPresent(String participant) {
        if (participantsList.getItem(participant) == null && !participantsList.isItemPresentBySubtext(participant)) {
            throw new Error("Participant with name [" + participant + "] was not found among either contact or institution names.");
        }

        return this;
    }

    public FullProfile openProfile(String name) {
        participantsList.select(name);
        return new FullProfile();
    }

    public String getSubject(){
        String text;
        try {
            text = Driver.findVisibleNow(SUBJECT_LOCATOR, container).getText();
        } catch (NoSuchElementException e) {
            Logger.logError("This activity has no subject.");
            text = "";
        }
        return text;
    }

    public String getType(){
        return activityDetails.getValue("Type");
    }

    public ActivityDetailsOverview verifySubject(String expectedSubject) {
        String actualSubject = getSubject();
        Assert.assertEquals(actualSubject, expectedSubject, "Activity subject mismatch: ");
        return this;
    }

    public ActivityDetailsOverview verifyDate(String expectedDate) {
        String expectedMonthAndDay = DateTimeHelper.getMonthDayFromDateStr(expectedDate);
        String actualDayAndMonth = Driver.findVisible(MONTH_LOCATOR).getText() + " " + Driver.findVisible(DAY_LOCATOR).getText();
        Verify.verifyEquals(actualDayAndMonth, expectedMonthAndDay, "Expected action date mismatch:");
        return this;
    }

    public ActivityDetailsOverview verifyTimeSlot(String timeSlot) {
        SenchaWebElement timeSlotElement = Driver.findVisible(TIME_SLOT_LOCATOR, container);
        Verify.verifyContainsText(timeSlotElement.getText(), timeSlot, "Expected time was not found in activity header: ");
        return this;
    }

    public ActivityDetailsOverview verifyType(String expectedType){
        String actualType = getType();
        Assert.assertEquals(actualType, expectedType, "Activity Type mismatch: ");
        return this;
    }

    public GroupActivityOverlay editActivity() {
        SenchaWebElement editButton = getEditButton();
        Assert.assertNotNull(editButton, "'Edit' button was not found and canot be clicked.");
        editButton.click();
        return new GroupActivityOverlay();
    }

    public ActivityDetailsOverview verifyEditButtonUnavailable(){
        Verify.verifyTrue(getEditButton().isDisabled(), "Edit Button should be disabled, but found enabled.");
        return this;
    }

    public ActivityDetailsOverview verifyEditButtonAvailable(){
        Verify.verifyFalse(getEditButton().isDisabled(), "Edit Button should be enabled, but found disabled.");
        return this;
    }

    public ActivityDetailsOverview verifyDeleteButtonUnavailable(){
        Verify.verifyTrue(getDeleteButton().isDisabled(), "Delete Button should be disabled, but found enabled.");
        return this;
    }

    public ActivityDetailsOverview verifyDeleteButtonAvailable(){
        Verify.verifyFalse(getDeleteButton().isDisabled(), "Delete Button should be enabled, but found disabled.");
        return this;
    }

    private SenchaWebElement getEditButton() {
        return Driver.findVisible(EDIT_LOCATOR);
    }

    private SenchaWebElement getDeleteButton() {
        return Driver.findVisible(DELETE_LOCATOR);
    }

    private void openCard(UITitles.ActivityCardTitle cardTitle) {
        int index = CARD_HEADERS.indexOf(cardTitle);
        carousel.goToPage(index);
        String updatedHeaderXpath = new XPathBuilder()
                .byClassName(CONTAINER_CLASS)
                .byClassName("title-bar")
                .byClassName("x-title")
                .withChildTextContains(UITitles.get(cardTitle))
                .build();
        addOneTimeCheckpoint(By.xpath(updatedHeaderXpath));
        waitReady();
        container = carousel.getActiveCarouselItem();
    }

    public ProfileOverviewController waitProfileLoaded(String shortName) {
        waitReady();
        return this;
    }

    @Override
    public ProfileOverviewController verifyProfileName(String name) {
        //verifySubject(name);
        return this;
    }

    @Override
    public String getProfileName() {
        return getSubject();
    }

    public ConfirmationDialog clickDeleteActivity() {
        getDeleteButton().click();
        return new ConfirmationDialog();
    }

    public void deleteActivity() {
        ConfirmationDialog deleteConfirmation = clickDeleteActivity();
        deleteConfirmation.clickButton("Delete");
        ScreenCard activityDelete = new ScreenCard();
        activityDelete
                .addLoadingIndicatorCheckpoint()
                .addCheckpointElement(CARD_TITLE_LOCATOR)
                .addInvisibleOrAbsentCondition();
        activityDelete.waitReady();
    }
}
