package ipreomobile.ui.profiles.fullProfileTabs;

import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.funds.FundFullProfile;
import ipreomobile.ui.institutions.InstitutionFullProfile;
import org.openqa.selenium.By;
import org.testng.Assert;

/**
 * Created by artem_skrypnyk on 8/27/2014.
 */
public class BaseCurrentHoldersProfileTab extends BaseHoldingsAndHoldersProfileTab {

    private static final String SEARCH_FIELD_XPATH = new XPathBuilder().byAttribute("placeholder", "%s").build();

    protected static final String INSTITUTIONS_SUB_TAB = "Institutions";
    protected static final String FUNDS_SUB_TAB = "Funds";

    public BaseCurrentHoldersProfileTab(){
        super();
        setSearchFieldSelector(By.xpath(String.format(SEARCH_FIELD_XPATH, "Institution Name")));
    }

    public boolean holderPresent(String holderName){
        return itemsList.getItem(holderName) != null;
    }

    public BaseCurrentHoldersProfileTab selectInstitutionsTab(){
        selectTab(INSTITUTIONS_SUB_TAB);
        initializeItemsList();
        setSearchFieldSelector(By.xpath(String.format(SEARCH_FIELD_XPATH, "Institution Name")));
        return this;
    }

    public BaseCurrentHoldersProfileTab selectFundsTab(){
        selectTab(FUNDS_SUB_TAB);
        initializeItemsList();
        setSearchFieldSelector(By.xpath(String.format(SEARCH_FIELD_XPATH, "Fund Name")));
        return this;
    }

    public BaseCurrentHoldersProfileTab filterByHolderName(String filter) {
        setSearchFilter(filter);
        waitReady();
        return this;
    }

    public BaseCurrentHoldersProfileTab clearFilterByHolderName() {
        setSearchFilter("");
        waitReady();
        return this;
    }

    public void verifyHolderPresent(String holderName) {
        filterByHolderName(holderName);
        itemsList.verifyItemPresence(holderName);
    }

    public void verifyHolderAbsent(String holderName) {
        filterByHolderName(holderName);
        Assert.assertTrue(isResultSetEmpty(), "Company '" + holderName + "' was found in the " + getSelectedTab() + " list, while should not. Expected result set to be empty after filtering.");
    }

    public InstitutionFullProfile selectInstitution(String institutionName) {
        selectInstitutionsTab();
        itemsList.select(institutionName);
        return new InstitutionFullProfile();
    }

    public FundFullProfile selectFund(String fundName) {
        selectFundsTab();
        itemsList.select(fundName);
        return new FundFullProfile();
    }



}
