package ipreomobile.ui.profiles.fullProfileTabs;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.ProfileTab;
import ipreomobile.templates.ui.ProfileTabList;
import ipreomobile.templates.ui.FullProfile;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

/**
 * Created by artem_skrypnyk on 8/26/2014.
 */
public class BaseHoldingsAndHoldersProfileTab extends ProfileTab {

    protected static final String ITEM_NAME_XPATH         = new XPathBuilder().byClassName("header").build();
    protected static final String PROPERTY_XPATH = new XPathBuilder().byClassName("details").byTag("li").withTextContains("%s").byClassName("value").build();

    protected String containerClass;
    protected String searchFieldXpath;
    protected ProfileTabList itemsList;

    protected void initializeItemsList(){
        itemsList = new ProfileTabList(){

            @Override
            public SenchaWebElement getItem(String name){
                setListContainer(getActiveContainer(containerClass));
                return super.getItem(name);
            }
        };
        itemsList.setItemNameXpath(ITEM_NAME_XPATH);
    }

    public BaseHoldingsAndHoldersProfileTab(){
        super();
        initializeItemsList();
    }

    public BaseHoldingsAndHoldersProfileTab verifyProfileUnavailableInOffline(String profileName){
        Assert.assertTrue(isProfileUnavailableInOffline(profileName), "Profile '" + profileName +"' is available while expected to be unavailable in offline mode.");
        return this;
    }

    public BaseHoldingsAndHoldersProfileTab verifyProfileUnavailableInOffline(String profileName, String subtext){
        Assert.assertTrue(isProfileUnavailableInOffline(profileName, subtext), "Profile '" + profileName +"' with subtext '" + subtext + "' is available while expected to be unavailable in offline mode.");
        return this;
    }

    public boolean isProfileUnavailableInOffline(String profileName){
        return itemsList.isItemUnavailableInOfflineMode(profileName);
    }

    public boolean isProfileUnavailableInOffline(String name, String subtext){
        return itemsList.isItemUnavailableInOfflineMode(name, subtext);
    }

    public void setContainerClass(String containerClass) {
        this.containerClass = containerClass;
    }

    protected SenchaWebElement getActiveContainer(String containerClassName){
        return Driver.findVisible(By.className(containerClassName));
    }

    public boolean isResultSetEmpty() {
        return isProfileTabEmpty();
    }

    public void verifyListEmpty() {
        Assert.assertTrue(isResultSetEmpty(), "Result set contains some data, while expected to be empty: ");
    }

    protected String getProperty(String itemName, String propertyName) {
        String propertyXpath = String.format(PROPERTY_XPATH, propertyName);
        itemsList.setSubtextXpath(propertyXpath);
        return itemsList.getItemSubtext(itemName);
    }

    public FullProfile selectItemAvailableOffline(){
        itemsList.selectItemInOfflineMode(true);
        return new FullProfile();
    }

    public FullProfile selectItemUnavailableOffline(){
        itemsList.selectItemInOfflineMode(false);
        return new FullProfile();
    }
}
