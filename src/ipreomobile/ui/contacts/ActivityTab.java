package ipreomobile.ui.contacts;

import ipreomobile.core.ElementHelper;
import ipreomobile.core.Logger;
import ipreomobile.data.ActivityData;
import ipreomobile.templates.ui.TwoPane;
import ipreomobile.core.SenchaWebElement;

import java.util.ArrayList;
import java.util.List;


public class ActivityTab extends TwoPane {

    public ActivityTab(){
        super();
        setupProfileList();
        setupProfileOverview();
    }

    @Override
    protected void setupProfileList() {
        qpl = new ContactActivityList();
    }

    @Override
    protected void setupProfileOverview() {
        profile = new ContactProfileOverview();
    }


    @Override
    protected ActivityTab verifyProfileOverviewLoaded(String contactName){
        if (!qpl.isSelectedItemUnavailableInOfflineMode()) {
//            String contactName = getSelectedActivityData().getContactName();
            super.verifyProfileOverviewLoaded(contactName);
        }
        return this;
    }

    @Override
    public ActivityTab openProfileOverview(String name){
        qpl.waitReady();
        Logger.logDebugScreenshot("Before opening profile overview for entity '" + name + "'.");

        qpl.selectFirstWithName(name);
        Logger.logDebugScreenshot("Activated list item '" + getProfileNameSelectedInList() + "'.");
        verifyProfileOverviewLoaded(name);
        return this;
    }


//    public ActivityTab openProfileOverviewByContactNameAndActivitySubject(String contactName, String activitySubject){
//        qpl.select(contactName, activitySubject);
//        return this;
//    }

    public ActivityData getSelectedActivityData(){
        return ((ContactActivityList)qpl).getSelectedActivityData();
    }

    public ActivityData getActivityData(String name){
        return ((ContactActivityList)qpl).getActivityData(name);
    }

//    public ActivityData getActivityData(int index) {
//        return ((ContactActivityList) qpl).getActivityData(index);
//    }
}
