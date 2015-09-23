package ipreomobile.ui.activities;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;

/**
 * Created by Artem_Skrypnyk on 7/9/2014.
 */
public class GroupActivityOverlay extends ActivityOverlay {

    private static final By INDIVIDUAL_ACTIVITY_BUTTON_LOCATOR = By.xpath(new XPathBuilder().byTag("div").withClassName("toggle-button").byTag("span").withText("Individual").build());

    public GroupActivityOverlay(){
        super();
        setupActivityDetails();
        setupExternalParticipantsList();
    }

    @Override
    protected void setupActivityDetails(){
        details = new GroupActivityDetails();
    }

    @Override
    protected void setupExternalParticipantsList() {
        externalParticipants = new ExternalParticipantsList();
        externalParticipants.setListContainer(Driver.findVisible(EXTERNAL_PARTICIPANTS_LIST_CONTAINER_LOCATOR));
    }


    public IndividualActivityOverlay switchToIndividualActivity(){
       SenchaWebElement individualActivityButton = Driver.findOne(INDIVIDUAL_ACTIVITY_BUTTON_LOCATOR);
        individualActivityButton.click();
        acceptPrompt();
        return new IndividualActivityOverlay();
    }
}
