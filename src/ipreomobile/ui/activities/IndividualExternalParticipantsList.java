package ipreomobile.ui.activities;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;

public class IndividualExternalParticipantsList extends ExternalParticipantsList {

    private static final By ALL_BUTTON_LOCATOR = By.xpath(new XPathBuilder().byTag("div").withClassName("external-participants").byTag("span").withText("All").build());
    private static final String PARTICIPANT_STATE_SELECTED_CLASS_NAME = "x-item-selected";
    
    public IndividualExternalParticipantsList(){
        super();
        setStateTokenXpath(".");
        setStateSwitcherXpath(".");
    }

    public void selectExternalParticipant(String name){
        setStateTokenSelectedClassName(PARTICIPANT_STATE_SELECTED_CLASS_NAME);
        check(name);
    }

    public void deselectExternalParticipant(String name){
        setStateTokenSelectedClassName(PARTICIPANT_STATE_SELECTED_CLASS_NAME);
        uncheck(name);
    }

    public void selectAll(){
       SenchaWebElement allButton = Driver.findOne(ALL_BUTTON_LOCATOR);
        allButton.click();
    }
}
