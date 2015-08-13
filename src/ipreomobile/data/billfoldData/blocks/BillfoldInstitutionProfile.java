package ipreomobile.data.billfoldData.blocks;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.ScreenCard;
import ipreomobile.data.billfoldData.blocks.widgets.BdDetailsWidget;
import ipreomobile.data.billfoldData.blocks.widgets.EquityOwnershipWidget;
import ipreomobile.data.billfoldData.blocks.widgets.FiOwnershipWidget;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;

import java.util.List;
import java.util.stream.Collectors;

public class BillfoldInstitutionProfile extends ScreenCard{

    private static final String AJAX_PROGRESS_TITLE_CONTAINER_XPATH = new XPathBuilder().byClassName("progressContainer").build();

    private static final String LABEL_NAME_XPATH            = new XPathBuilder().byIdContains("_lbName").build();
    private static final String SIDE_XPATH                  = new XPathBuilder().byIdContains("_lblSide").build();
    private static final String LAST_ACTION_XPATH           = new XPathBuilder().byIdContains("_hlLastActivity").build();

    private static final String PRIMARY_CONTACTS_XPATH      = new XPathBuilder().byIdContains("_pnlVisibleLinks").byTag("a").build();

    private static final String PUBLIC_DATA_XPATH           = new XPathBuilder().byIdContains("_rblOwnershipView_0").build();
    private static final String SURVEILLANCE_DATA_XPATH     = new XPathBuilder().byIdContains("_rblOwnershipView_1").build();

    private BdDetailsWidget  bdDetailsWidget;
    private EquityOwnershipWidget equityOwnershipWidget;
    private FiOwnershipWidget fiOwnershipWidget;

    public BillfoldInstitutionProfile(){
        addCheckpointElement(By.xpath(LABEL_NAME_XPATH));
        waitReady();
    }

    public String getName(){
        return Driver.findVisible(By.xpath(LABEL_NAME_XPATH)).getText().trim();
    }

    public String getSide(){
        return Driver.findVisible(By.xpath(SIDE_XPATH)).getText().trim();
    }

    public String getLastActionDate(){
       SenchaWebElement lastAction = Driver.findVisible(By.xpath(LAST_ACTION_XPATH));
        return (lastAction == null) ? null : lastAction.getText().trim();
    }

    public List<String> getPrimaryContacts(){
        List<SenchaWebElement> primaryContacts =  Driver.findAll(By.xpath(PRIMARY_CONTACTS_XPATH));
        return primaryContacts.stream().map(contact -> contact.getText().trim()).collect(Collectors.toList());
    }

    public BillfoldInstitutionProfile showPublicData(){
        Driver.findVisible(By.xpath(PUBLIC_DATA_XPATH)).click();
        addCheckpointElement(By.xpath(AJAX_PROGRESS_TITLE_CONTAINER_XPATH)).addVisibilityCondition(false);
        waitReady();
        equityOwnershipWidget = null;
        return this;
    }

    public BillfoldInstitutionProfile showSurveillanceData(){
        Driver.findVisible(By.xpath(SURVEILLANCE_DATA_XPATH)).click();
        addCheckpointElement(By.xpath(AJAX_PROGRESS_TITLE_CONTAINER_XPATH)).addVisibilityCondition(false);
        waitReady();
        equityOwnershipWidget = null;
        return this;
    }

    public BdDetailsWidget getBdDetailsWidget(){
        return (bdDetailsWidget != null) ? bdDetailsWidget : new BdDetailsWidget();
    }

    public EquityOwnershipWidget getEquityOwnershipWidget(){
        return (equityOwnershipWidget != null) ? equityOwnershipWidget : new EquityOwnershipWidget();
    }

    public FiOwnershipWidget getFiOwnershipWidget(){
        return (fiOwnershipWidget != null) ? fiOwnershipWidget : new FiOwnershipWidget();
    }
}
