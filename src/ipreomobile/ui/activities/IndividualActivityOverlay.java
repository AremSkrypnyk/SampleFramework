package ipreomobile.ui.activities;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;

public class IndividualActivityOverlay extends ActivityOverlay {

    private static final By GROUP_ACTIVITY_BUTTON_LOCATOR = By.xpath(new XPathBuilder().byTag("div").withClassName("toggle-button").byTag("span").withText("Group").build());

    public IndividualActivityOverlay(){
        super();
        setupActivityDetails();
        setupExternalParticipantsList();
    }

    @Override
    protected void setupActivityDetails(){
        details = new IndividualActivityDetails();
    }

    @Override
    protected void setupExternalParticipantsList() {
        externalParticipants = new IndividualExternalParticipantsList();
        externalParticipants.setListContainer(Driver.findVisible(EXTERNAL_PARTICIPANTS_LIST_CONTAINER_LOCATOR));
    }

    public IndividualActivityOverlay selectParticipant(String name){
        ((IndividualExternalParticipantsList)externalParticipants).selectExternalParticipant(name);
        return this;
    }

    public IndividualActivityOverlay deselectParticipant(String name){
        ((IndividualExternalParticipantsList)externalParticipants).deselectExternalParticipant(name);
        return this;
    }

    public IndividualActivityOverlay selectAllParticipants(){
        ((IndividualExternalParticipantsList)externalParticipants).selectAll();
        return this;
    }

    public IndividualActivityOverlay inputIndividualNotes(String note){
        ((IndividualActivityDetails)details).setupIndividualNotes(note);
        return this;
    }

    public GroupActivityOverlay switchToGroupActivity(){
       SenchaWebElement groupActivityButton = Driver.findOne(GROUP_ACTIVITY_BUTTON_LOCATOR);
        groupActivityButton.click();
        acceptPrompt();
        return new GroupActivityOverlay();
    }
}
