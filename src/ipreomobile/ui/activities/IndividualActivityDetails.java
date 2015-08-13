package ipreomobile.ui.activities;

import ipreomobile.core.Driver;
import ipreomobile.core.ElementHelper;
import ipreomobile.core.XPathBuilder;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;

public class IndividualActivityDetails extends ActivityDetails {

    private static final By INDIVIDUAL_NOTES_FIELD_LOCATOR = By.xpath(new XPathBuilder()
            .byTag("p").withText("Individual Notes").byXpathPart("/..").byTag("div").withClassName("notes-field").build());

    public IndividualActivityDetails(){
        super();
    }

    public void setupIndividualNotes(String note){
        SenchaWebElement notesField = Driver.findOne(INDIVIDUAL_NOTES_FIELD_LOCATOR);
        notesField.bringToView();
        notesField.setText(note);
    }


}
