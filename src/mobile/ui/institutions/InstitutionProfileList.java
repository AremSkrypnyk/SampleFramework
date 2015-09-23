package ipreomobile.ui.institutions;

import ipreomobile.templates.ui.QuickProfileList;

public class InstitutionProfileList extends QuickProfileList {

    public String getLocation(String institutionName){
        return getItemSubtext(institutionName);
    }
}
