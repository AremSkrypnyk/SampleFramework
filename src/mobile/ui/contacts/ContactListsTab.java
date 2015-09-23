package ipreomobile.ui.contacts;

import ipreomobile.core.Driver;
import ipreomobile.templates.ui.FullProfile;
import ipreomobile.templates.ui.ListsTab;
import ipreomobile.ui.activities.ActivityOverlay;
import ipreomobile.ui.activities.IndividualActivityOverlay;
import ipreomobile.ui.institutions.InstitutionFullProfile;
import org.openqa.selenium.By;

public class ContactListsTab extends ListsTab {

    @Override
    protected void setupListManager(){
        qpl = new ContactListManager();
    }

    @Override
    protected void setupProfileList(){
        qpl = new ContactProfileList();
        super.setupProfileList();
    }

    @Override
    protected void setupProfileOverview(){ profile = new ContactProfileOverview(); }

    public IndividualActivityOverlay addActivity() {
        super.addActivity();
        return new IndividualActivityOverlay();
    }

    public ContactFullProfile openFullProfile(String name){
        super.openFullProfile(name);
        return new ContactFullProfile();
    }

}