package ipreomobile.ui.institutions;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.ListsTab;
import ipreomobile.ui.activities.ActivityOverlay;
import ipreomobile.ui.activities.GroupActivityOverlay;
import org.openqa.selenium.By;

public class InstitutionListsTab extends ListsTab {

    private static final String LIST_ITEMS_XPATH = new XPathBuilder().byClassName("x-list-item").byClassName("info").build();

    @Override
    protected void setupListManager(){
        qpl = new InstitutionListManager();
    }

    @Override
    protected void setupProfileList(){
        qpl = new InstitutionProfileList();
        super.setupProfileList();
    }

    @Override
    protected void setupProfileOverview(){ profile = new InstitutionProfileOverview(); }


    public GroupActivityOverlay addActivity() {
        super.addActivity();
        return new GroupActivityOverlay();
    }

    public InstitutionFullProfile openFullProfile(String name){
        super.openFullProfile(name);
        return new InstitutionFullProfile();
    }

/*    public boolean isItemUnavailable(String name){
        String itemsXpath = qpl.getItemsXpath();
        qpl.setItemsXpath(LIST_ITEMS_XPATH);
        boolean result = qpl.isItemUnavailable(name);
        qpl.setItemsXpath(itemsXpath);
        return result;
    }*/

}
