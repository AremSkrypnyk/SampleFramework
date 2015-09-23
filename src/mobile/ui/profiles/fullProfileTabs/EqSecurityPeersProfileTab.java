package ipreomobile.ui.profiles.fullProfileTabs;


import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.FullProfile;
import ipreomobile.templates.ui.ProfileTab;
import ipreomobile.templates.ui.ProfileTabList;
import ipreomobile.ui.securities.EqSecurityFullProfile;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

import static ipreomobile.core.Logger.logError;

public class EqSecurityPeersProfileTab extends ProfileTab {

    protected static final String ITEM_NAME_XPATH           = new XPathBuilder().byClassName("header").build();
    protected static final String LIST_CONTAINER_CLASSNAME  = "eqsecurity-peer";
    protected String unavailableItemFlagXpath               = new XPathBuilder().byClassName("list-detailed-item").build();

    protected static final String DEFAULT_PEER_LIST = "Default Peer List";
    protected static final String MY_PEER_LIST = "My Peer List";

    protected static final String PROPERTY_XPATH = new XPathBuilder().byClassName("details").byTag("li").withTextContains("%s").byClassName("value").build();

    protected ProfileTabList itemsList;

    protected void initializeItemsList(){
        itemsList = new ProfileTabList(){

            @Override
            public SenchaWebElement getItem(String name){
                setListContainer(getActiveContainer(LIST_CONTAINER_CLASSNAME));
                return super.getItem(name);
            }
        };
        itemsList.setItemNameXpath(ITEM_NAME_XPATH);
        //itemsList.setUnavailableFlagXpath(unavailableItemFlagXpath);
    }

    public EqSecurityPeersProfileTab(){
        super();
        initializeItemsList();
    }


    protected SenchaWebElement getActiveContainer(String containerClassName){
        return Driver.findVisible(By.className(containerClassName));
    }

    public FullProfile selectItemAvailableOffline(){
        itemsList.selectItemInOfflineMode(true);
        return new FullProfile();
    }

    public FullProfile selectItemUnavailableOffline(){
        itemsList.selectItemInOfflineMode(false);
        return new FullProfile();
    }

    public EqSecurityPeersProfileTab selectDefaultPeerList(){
        selectTab(DEFAULT_PEER_LIST);
        initializeItemsList();
        return this;
    }

    public EqSecurityPeersProfileTab selectMyPeerList(){
        selectTab(MY_PEER_LIST);
        initializeItemsList();
        return this;
    }

    protected String getProperty(String itemName, String propertyName) {
        String propertyXpath = String.format(PROPERTY_XPATH, propertyName);
        itemsList.setSubtextXpath(propertyXpath);
        return itemsList.getItemSubtext(itemName);
    }

    public String getMarketCap(String securityName){
        return getProperty(securityName, "Market Cap");
    }

    public String getSharesOutstanding(String securityName){
        return getProperty(securityName, "Shares Outstanding");
    }

    public String getTrailing(String securityName){
        return getProperty(securityName, "Trailing");
    }

    public FullProfile openSecurity(String name){
        itemsList.select(name);
        return new FullProfile();
    }

    public void verifyPeerPresent(String peerName) {
        itemsList.verifyItemPresence(peerName);
    }

    public boolean isResultSetEmpty() {
        return isProfileTabEmpty();
    }

    public void verifyPeerAbsent(String peerName) {
        itemsList.verifyItemAbsence(peerName);
    }

    public EqSecurityFullProfile selectDefaultPeer(String defaultPeerName) {
        selectDefaultPeerList();
        itemsList.select(defaultPeerName);
        return new EqSecurityFullProfile();
    }

    public EqSecurityFullProfile selectMyPeer(String myPeerName) {
        selectMyPeerList();
        itemsList.select(myPeerName);
        return new EqSecurityFullProfile();
    }


    private void verifyCurrencyInDetailsForPeerList(String profileName) {
        String propertyWithCurrency = "Market Cap(" + System.getProperty("test.currency") + "M):";

        try {
            getProperty(profileName, propertyWithCurrency);
        } catch (AssertionError e) {
            logError("Property with currency[" + propertyWithCurrency + "] was not found. " + e.getMessage(), e);
        }
    }

    public EqSecurityPeersProfileTab verifyCurrencyInDetailsForDefaultPeerList(String profileName) {
        selectDefaultPeerList();
        verifyCurrencyInDetailsForPeerList(profileName);
        return this;
    }

    public EqSecurityPeersProfileTab verifyCurrencyInDetailsForMyPeerList(String profileName) {
        selectMyPeerList();
        verifyCurrencyInDetailsForPeerList(profileName);
        return this;
    }
}
