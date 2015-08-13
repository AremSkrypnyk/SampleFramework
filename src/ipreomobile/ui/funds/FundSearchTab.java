package ipreomobile.ui.funds;

import ipreomobile.templates.ui.SearchResultsTab;

public class FundSearchTab extends SearchResultsTab {
    @Override
    protected void setupProfileList() {
        qpl = new FundProfileList();
        super.setupProfileList();
    }

    @Override
    protected void setupProfileOverview() {
        profile = new FundProfileOverview();
    }

    @Override
    public FundFullProfile openFullProfile(String name){
        super.openFullProfile(name);
        return new FundFullProfile();
    }

    @Override
    public FundProfileOverview getActiveProfileOverview(){
        return (FundProfileOverview)profile;
    }

}
