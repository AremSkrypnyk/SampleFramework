package ipreomobile.ui.institutions;

import ipreomobile.core.Logger;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.TwoPane;
import ipreomobile.ui.blocks.EyeButton;

public class InvestorsTab extends TwoPane {

//    private static final String EYE_BUTTON_XPATH    = new XPathBuilder().byClassName("investors-toolbar-btn").withChildTag("span").withClassName("eye").build();
//    private static final String EYE_PRESSED_CLASS   = "pressed";
//    private SenchaWebElement eyeButton;

    private String investorsXpath = new XPathBuilder().byClassName("investor-item").build();

    private EyeButton eye = new EyeButton();

    public InvestorsTab(){
        super();
        setupProfileList();
        if(isProfileOverviewAvailable())
            setupProfileOverview();
    }

    @Override
    protected void setupProfileList() {
        qpl = new InvestorProfileList();
    }

    @Override
    protected void setupProfileOverview() {
        profile = new InstitutionProfileOverview();
    }

    @Override
    public InstitutionFullProfile openFullProfile(String name) {
        super.openFullProfile(name);
        Logger.logDebug("Executed TwoPane.openProfileSummary() method for list item '"+name+"'.");
        InstitutionFullProfile summary = new InstitutionFullProfile();
        summary.verifyProfileName(name);
        return summary;
    }

    public void showSurveillanceData() {
        eye.showSurveillanceData();
        qpl.waitReady();
    }

    public void showPublicData(){
        eye.showPublicData();
        qpl.waitReady();
    }

/*    public boolean isItemUnavailable(String name){
        String itemsXpath = qpl.getItemsXpath();
        qpl.setItemsXpath(investorsXpath);
        boolean result = qpl.isItemUnavailable(name);
        qpl.setItemsXpath(itemsXpath);
        return result;
    }*/
}
