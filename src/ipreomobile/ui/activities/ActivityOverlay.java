package ipreomobile.ui.activities;

import ipreomobile.core.DateTimeHelper;
import ipreomobile.core.Driver;
import ipreomobile.core.Logger;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.BaseOverlay;
import ipreomobile.ui.ScreenCard;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.blocks.overlay.ListOverlaySearchMultiSelect;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

/**
 * Created by Artem_Skrypnyk on 7/9/2014.
 */
public abstract class ActivityOverlay extends ScreenCard {

    private static final By START_DATE_LOCATOR = By.xpath(new XPathBuilder().byClassName("value").byClassName("start-date").build());
    private static final By END_DATE_LOCATOR = By.xpath(new XPathBuilder().byClassName("value").byClassName("end-date").build());

    private static final By SAVE_BUTTON_LOCATOR = By.xpath(new XPathBuilder().byTag("div").withClassName("save-button").build());
    private static final By CANCEL_BUTTON_LOCATOR = By.xpath(new XPathBuilder().byTag("span").withClassName("x-button").withText("Cancel").build());

    private static final By ACTIVITY_BUTTON_LOCATOR = By.xpath(new XPathBuilder().byTag("div").withClassName("toggle-button").byTag("span").withText("Activity").build());
    private static final By TASK_BUTTON_LOCATOR = By.xpath(new XPathBuilder().byTag("div").withClassName("toggle-button").byTag("span").withText("Task").build());

    private static final By ACCEPT_SWITCHING_BUTTON_LOCATOR = By.xpath(new XPathBuilder().byTag("div").withClassName("x-msgbox").byTag("span").withText("Yes").build());
    private static final By DECLINE_SWITCHING_BUTTON_LOCATOR = By.xpath(new XPathBuilder().byTag("div").withClassName("x-msgbox").byTag("span").withText("No").build());

    protected static final By EXTERNAL_PARTICIPANTS_LIST_CONTAINER_LOCATOR = By.className("external-participants-list");

    protected ActivityDetails details;
    protected ExternalParticipantsList externalParticipants;

    protected abstract void setupActivityDetails();
    protected abstract void setupExternalParticipantsList();

    public void saveActivity(){
       SenchaWebElement saveButton = Driver.findVisible(SAVE_BUTTON_LOCATOR);
        saveButton.click();
        while (BaseOverlay.getActiveMask() != null) {
            BaseOverlay.waitMaskHidden(BaseOverlay.getActiveMaskLocator());
        }
    }

    public void closeActivityDialog(){
        By activeMaskLocator = BaseOverlay.getActiveMaskLocator();
       SenchaWebElement cancelButton = Driver.findVisible(CANCEL_BUTTON_LOCATOR);
        cancelButton.click();
        acceptPrompt();
        BaseOverlay.waitMaskHidden(activeMaskLocator);
    }

    protected void acceptPrompt(){
        Driver.saveTimeout();
        Driver.setTimeout(2);
       SenchaWebElement acceptSwitchingButton = Driver.findVisible(ACCEPT_SWITCHING_BUTTON_LOCATOR);
        if (acceptSwitchingButton != null) {
            acceptSwitchingButton.click();
        }
        Driver.restoreSavedTimeout();
    }

    protected void declinePrompt(){
       SenchaWebElement declineSwitchingButton = Driver.findOne(DECLINE_SWITCHING_BUTTON_LOCATOR);
        declineSwitchingButton.click();
    }

    public TaskOverlay switchToTask(){
       SenchaWebElement taskButton = Driver.findOne(TASK_BUTTON_LOCATOR);
        taskButton.click();
        acceptPrompt();
        return new TaskOverlay();
    }

    public IndividualActivityOverlay switchToActivity(){
       SenchaWebElement activityButton = Driver.findOne(ACTIVITY_BUTTON_LOCATOR);
        activityButton.click();
        acceptPrompt();
        return new IndividualActivityOverlay();
    }

    public ActivityOverlay setSubject(String subject){
        details.setupSubject(subject);
        return this;
    }

    public ActivityOverlay selectStartDateTime(String dateTime){
        Logger.logDebug("Select Start date:");
        DateTimeSelectionOverlay datePicker = setupDateTimeSlot();
        Logger.logDebugScreenshot("Opened the overlay, not selecting start date yet.");
        datePicker.selectStartDateTime(dateTime);
        return this;
    }

    public ActivityOverlay selectEndDateTime(String dateTime){
        Logger.logDebug("Select End date:");
        DateTimeSelectionOverlay datePicker = setupDateTimeSlot();
        Logger.logDebugScreenshot("Opened the overlay, not selecting end date yet.");
        datePicker.selectEndDateTime(dateTime);
        return this;
    }

    public DateTimeSelectionOverlay setupDateTimeSlot(){
        return details.openDateTimeSelection();
    }

    public ActivityOverlay setNotes(String note){
        details.setupNotes(note);
        return this;
    }

    public ActivityOverlay selectType(String typeName){
        details.openTypeOverlay().select(typeName);
        return this;
    }

    public ActivityOverlay selectTopic(String topicName){
        details.openTopicOverlay().select(topicName);
        return this;
    }

    public ActivityOverlay selectMacroIndustry(String macroIndustryName){
        details.openMacroOverlay().select(macroIndustryName);
        return this;
    }

    public ActivityOverlay selectMidIndustry(String midIndustryName){
        details.openMidOverlay().select(midIndustryName);
        return this;
    }

    public ActivityOverlay selectMicroIndustry(String microIndustryName){
        details.openMicroOverlay().select(microIndustryName);
        return this;
    }

    public ActivityOverlay selectLocation(String locationName){
        details.openLocationOverlay().select(locationName);
        return this;
    }

    public ActivityOverlay selectSymbols(String searchFilter, String... names){
        ListOverlaySearchMultiSelect symbolOverlay = details.openSymbolOverlay();
        symbolOverlay.setSearchFilter(searchFilter);
        symbolOverlay.check(names);
        symbolOverlay.close();
        return this;
    }

    public ActivityOverlay checkParticipantAsOrganizer(String name){
        externalParticipants.markItemAsOrganizer(name);
        return this;
    }

    public ActivityOverlay uncheckParticipantAsOrganizer(String name){
        externalParticipants.unmarkItemAsOrganizer(name);
        return this;
    }

    public ActivityOverlay checkDeclineParticipation(String name){
        externalParticipants.markDeclineParticipationForItem(name);
        return this;
    }

    public ActivityOverlay uncheckDeclineParticipation(String name){
        externalParticipants.unmarkDeclineParticipationForItem(name);
        return this;
    }

    public ActivityOverlay checkAcceptParticipation(String name){
        externalParticipants.markAcceptParticipationForItem(name);
        return this;
    }

    public ActivityOverlay uncheckAcceptParticipation(String name){
        externalParticipants.unmarkAcceptParticipationForItem(name);
        return this;
    }


    public ActivityOverlay addInstitutionsAsExternalParticipantsFromSearch(String searchFilter, String... names){
        ExternalParticipantsList.AddExternalParticipants addExternalParticipants = externalParticipants.clickOnAddExternalParticipantsButton();
        addExternalParticipants.addInstitutionsFromSearch(searchFilter, names);
        return this;
    }

    public ActivityOverlay addContactsAsExternalParticipantsFromSearch(String searchFilter, String... names){
        ExternalParticipantsList.AddExternalParticipants addExternalParticipants = externalParticipants.clickOnAddExternalParticipantsButton();
        addExternalParticipants.addContactsFromSearch(searchFilter, names);
        return this;
    }


    public ActivityOverlay addInstitutionsAsExternalParticipantsFromMyLists(String institutionList, String... institutionNames){
        ExternalParticipantsList.AddExternalParticipants addExternalParticipants = externalParticipants.clickOnAddExternalParticipantsButton();
        ListOverlaySearchMultiSelect list = addExternalParticipants.addInstitutionsFromLists().selectListType(UITitles.ListType.MY_LISTS).selectList(institutionList);
        list.check(institutionNames);
        list.close();
        return this;
    }

    public ActivityOverlay addInstitutionsAsExternalParticipantsFromOtherLists(String institutionList, String... institutionNames){
        ExternalParticipantsList.AddExternalParticipants addExternalParticipants = externalParticipants.clickOnAddExternalParticipantsButton();
        ListOverlaySearchMultiSelect list = addExternalParticipants.addInstitutionsFromLists().selectListType(UITitles.ListType.OTHER_LISTS).selectList(institutionList);
        list.check(institutionNames);
        list.close();
        return this;
    }

    public ActivityOverlay addContactsAsExternalParticipantsFromMyLists(String contactList, String... contactNames){
        ExternalParticipantsList.AddExternalParticipants addExternalParticipants = externalParticipants.clickOnAddExternalParticipantsButton();
        ListOverlaySearchMultiSelect list = addExternalParticipants.addContactsFromLists().selectListType(UITitles.ListType.MY_LISTS).selectList(contactList);
        list.check(contactList);
        list.close();
        return this;
    }

    public ActivityOverlay addContactsAsExternalParticipantsFromOtherLists(String contactList, String... contactNames){
        ExternalParticipantsList.AddExternalParticipants addExternalParticipants = externalParticipants.clickOnAddExternalParticipantsButton();
        ListOverlaySearchMultiSelect list = addExternalParticipants.addContactsFromLists().selectListType(UITitles.ListType.OTHER_LISTS).selectList(contactList);
        list.check(contactNames);
        list.close();
        return this;
    }

    public ExternalParticipantsList.AddExternalParticipants addExternalParticipants(){
        return externalParticipants.clickOnAddExternalParticipantsButton();
    }

    public ActivityOverlay verifyStartDateTime(String expectedDateTime) {
       SenchaWebElement startDateLabel = Driver.findVisible(START_DATE_LOCATOR);
        DateTimeHelper.compareDateTimeValues(startDateLabel.getText().trim(), expectedDateTime, "Start date mismatch in Activity Details section: ");
        Logger.logDebugScreenshot("Verifying activity Start DateTime: Expected to find "+expectedDateTime);
        return this;
    }

    public ActivityOverlay verifyEndDateTime(String expectedDateTime) {
       SenchaWebElement endDateLabel = Driver.findVisible(END_DATE_LOCATOR);
        DateTimeHelper.compareDateTimeValues(endDateLabel.getText().trim(), expectedDateTime, "End date mismatch in Activity Details section: ");
        Logger.logDebugScreenshot("Verifying activity End DateTime: Expected to find "+expectedDateTime);
        return this;
    }

    public ActivityOverlay verifyExternalParticipantPresent(String name) {
        externalParticipants.verifyParticipantPresent(name);
        return this;
    }

    public ActivityOverlay verifyExternalParticipantAbsent(String name) {
        externalParticipants.verifyItemAbsence(name);
        return this;
    }


}
