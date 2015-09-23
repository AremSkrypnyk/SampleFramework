package ipreomobile.templates.ui;
import ipreomobile.core.XPathBuilder;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

public class ParticipantsList extends SingleSelectListImpl {

    public ParticipantsList(){
        setItemNameXpath(new XPathBuilder().byClassName("info").byClassName("text").build());
    }

    @Override
    public void select(String name) {
       SenchaWebElement item = getItem(name);
        Assert.assertNotNull(item, "Cannot select participant '" + name + "': no such item exists in the list.");
        click(item);
    }

    public void verifyParticipantPresent(String participant) {
        if (getItem(participant) == null && !isItemPresentBySubtext(participant)) {
            throw new Error("Participant with name [" + participant + "] was not found among either contact or institution names.");
        }
    }
}
