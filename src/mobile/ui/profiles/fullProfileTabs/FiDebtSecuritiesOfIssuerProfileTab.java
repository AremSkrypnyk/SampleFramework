package ipreomobile.ui.profiles.fullProfileTabs;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;

import ipreomobile.templates.ui.FullProfile;
import ipreomobile.templates.ui.ProfileTab;
import ipreomobile.templates.ui.ProfileTabList;
import ipreomobile.ui.securities.FiSecurityFullProfile;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;

public class FiDebtSecuritiesOfIssuerProfileTab extends ProfileTab {

    ProfileTabList securityProfileList;
    private static final String ITEMS_XPATH = new XPathBuilder().byClassName("x-list-item").build();
    private static final String ITEM_NAME_XPATH = new XPathBuilder().byClassName("header").build();
    private static final String CONTAINER_CLASS = "x-list-inner";

    private static final String VALUE_CLASS = "value";
    private static final String DETAILS_CLASS = "details";
    private static final String CUSIP_ISIN_XPATH = new XPathBuilder().byClassName(DETAILS_CLASS).byTag("li").withTextContains("CUSIP / ISIN").byClassName(VALUE_CLASS).build();
    private static final String COUPON_XPATH = new XPathBuilder().byClassName(DETAILS_CLASS).byTag("li").withTextContains("Coupon").byClassName(VALUE_CLASS).build();
    private static final String MATURITY_XPATH = new XPathBuilder().byClassName(DETAILS_CLASS).byTag("li").withTextContains("Maturity").byClassName(VALUE_CLASS).build();
    private static final String SP_RATING_XPATH = new XPathBuilder().byClassName(DETAILS_CLASS).byTag("li").withTextContains("S&P Rating").byClassName(VALUE_CLASS).build();
    private static final String NUMBER_OF_HOLDERS_XPATH = new XPathBuilder().byClassName(DETAILS_CLASS).byTag("li").withTextContains("Number Of Holders").byClassName(VALUE_CLASS).build();
    private static final String CURRENCY_XPATH = new XPathBuilder().byClassName(DETAILS_CLASS).byTag("li").withTextContains("Currency").byClassName(VALUE_CLASS).build();
    private static final String UNAVAILABLE_FLAG_XPATH = new XPathBuilder().byCurrentItem().byClassName("list-detailed-item").build();

    protected void initializeItemsList(){
        securityProfileList = new ProfileTabList(){
            @Override
            public void select(String name) {
               SenchaWebElement item = getItem(name);
                click(item);
                new FiSecurityFullProfile();
            }

            @Override
            public String getScreenName(){
                return "Fixed Income Profile List";
            }
        };
        securityProfileList.setItemsXpath(ITEMS_XPATH);
        securityProfileList.setItemNameXpath(ITEM_NAME_XPATH);
        securityProfileList.setListContainer(getActiveListContainer());
        //securityProfileList.setUnavailableFlagXpath(UNAVAILABLE_FLAG_XPATH);
    }

    public FiDebtSecuritiesOfIssuerProfileTab(){
        initializeItemsList();
    }

    public FiSecurityFullProfile openSecurityProfile(String securityProfileName) {
        securityProfileList.select(securityProfileName);
        return new FiSecurityFullProfile();
    }

    public String getCusipIsin(String securityName) {
        securityProfileList.setSubtextXpath(CUSIP_ISIN_XPATH);
        return securityProfileList.getItemSubtext(securityName);
    }

    public String getCoupon(String securityName) {
        securityProfileList.setSubtextXpath(COUPON_XPATH);
        return securityProfileList.getItemSubtext(securityName);
    }

    public String getMaturity(String securityName) {
        securityProfileList.setSubtextXpath(MATURITY_XPATH);
        return securityProfileList.getItemSubtext(securityName);
    }

    public String getSpRating(String securityName) {
        securityProfileList.setSubtextXpath(SP_RATING_XPATH);
        return securityProfileList.getItemSubtext(securityName);
    }

    public String getNumberOfHolders(String securityName) {
        securityProfileList.setSubtextXpath(NUMBER_OF_HOLDERS_XPATH);
        return securityProfileList.getItemSubtext(securityName);
    }

    public String getCurrency(String securityName) {
        securityProfileList.setSubtextXpath(CURRENCY_XPATH);
        return securityProfileList.getItemSubtext(securityName);
    }

    public FullProfile selectItemAvailableOffline(){
        initializeItemsList();
        securityProfileList.selectItemInOfflineMode(true);
        return new FullProfile();
    }

    public FullProfile selectItemUnavailableOffline(){
        initializeItemsList();
        securityProfileList.selectItemInOfflineMode(false);
        return new FullProfile();
    }

//    public FiDebtSecuritiesOfIssuerCard select(String name){
//        securityProfileList.select(name);
//        return this;
//    }

    public String getNextItemName(String name){
        return securityProfileList.getNextItemName(name);
    }

    private SenchaWebElement getActiveListContainer(){
        return Driver.findVisible(By.className(CONTAINER_CLASS));
    }
}
