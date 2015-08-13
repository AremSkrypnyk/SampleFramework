package ipreomobile.ui.institutions;

import ipreomobile.templates.ui.SearchResultsTab;

public class InstitutionSearchTab extends SearchResultsTab {

    @Override
    protected void setupProfileList() {
        qpl = new InstitutionProfileList();
        super.setupProfileList();
    }

    @Override
    protected void setupProfileOverview() {
        profile = new InstitutionProfileOverview();
    }

    public String getInstitutionLocation(String name) {
        return getListItemSubtext(name);
    }

    public String getInstitutionCity(String name){
        return getInstitutionLocation(name).split(",")[0];
    }

    public InstitutionFullProfile openFullProfile(String name){
        super.openFullProfile(name);
        return new InstitutionFullProfile();
    }

    public double getEquityAssetValueForActiveProfile(){
        return ((InstitutionProfileOverview)profile).getHoldingsCard().getEquityAssetValue();
    }

    public String getInstitutionJobTitle(){
        return new InstitutionProfileOverview().getInstitutionType();
    }

    public InstitutionProfileOverview getActiveProfileOverview(){
        return (InstitutionProfileOverview)profile;
    }
}
