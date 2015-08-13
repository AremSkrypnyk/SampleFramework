package ipreomobile.ui.contacts;

import ipreomobile.templates.ui.QuickProfileList;

public class ContactProfileList extends QuickProfileList {

    public String getInstitutionName(String contactName) {
        return getItemSubtext(contactName);
    }

}
