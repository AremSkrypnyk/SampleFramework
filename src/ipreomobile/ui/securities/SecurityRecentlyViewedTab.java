package ipreomobile.ui.securities;

import ipreomobile.templates.ui.RecentlyViewedTab;

public class SecurityRecentlyViewedTab extends RecentlyViewedTab {

    public SecurityRecentlyViewedTab(){

    }

    @Override
    protected void setupProfileList() {
        qpl = new SecurityProfileList();
        qpl.setItemNameXpath(itemNameXpath);
        qpl.setSubtextXpath(itemSubtextXpath);
        qpl.waitReady();
    }

    @Override
    protected void setupProfileOverview() {
        profile = new SecurityProfileOverview();
    }

    public EqSecurityFullProfile openEquityFullProfile(String securityName, String marketName){
        openFullProfile(securityName, marketName);
        return new EqSecurityFullProfile();
    }

    public FiSecurityFullProfile openFixedIncomeFullProfile(String name){
        openFullProfile(name);
        return new FiSecurityFullProfile();
    }

//    public TwoPane verifyItemPresentInList(String name) {
//        qpl.verifyItemPresent(name);
//        return this;
//    }
//
//    public TwoPane verifyItemPresentInList(String name, String subtext) {
//        qpl.verifyItemPresent(name, subtext);
//        return this;
//    }
}
