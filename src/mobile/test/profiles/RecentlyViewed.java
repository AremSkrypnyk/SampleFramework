package ipreomobile.test.profiles;

import ipreomobile.data.*;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.contacts.ActivityTab;
import ipreomobile.ui.contacts.ContactRecentlyViewedTab;
import ipreomobile.ui.contacts.ContactSearchTab;
import ipreomobile.ui.funds.FundRecentlyViewedTab;
import ipreomobile.ui.funds.FundSearchTab;
import ipreomobile.ui.institutions.InstitutionRecentlyViewedTab;
import ipreomobile.ui.institutions.InstitutionSearchTab;
import ipreomobile.ui.institutions.InvestorsTab;
import ipreomobile.ui.securities.EqSecurityFullProfile;
import ipreomobile.ui.securities.SecurityRecentlyViewedTab;
import ipreomobile.ui.securities.SecuritySearchTab;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class RecentlyViewed extends BaseTest {

    private InvestorsTab investorsTab;
    private ActivityTab activityTab;
    private InstitutionSearchTab institutionSearchTab;
    private ContactSearchTab contactSearchTab;
    private FundSearchTab fundSearchTab;
    private InstitutionRecentlyViewedTab institutionsRecentlyViewedTab;
    private ContactRecentlyViewedTab contactRecentlyViewedTab;
    private FundRecentlyViewedTab fundRecentlyViewedTab;
    private SecuritySearchTab securitySearchTab;
    private SecurityRecentlyViewedTab securityRecentlyViewedTab;

    private InstitutionData institutionData;
    private ContactData contactData;
    private FundData fundData;
    private EquityData equityData;
    private FixedIncomeData fixedIncomeData;
    private String institutionName;
    private String contactName;
    private String fundName;
    private String securityName;
    private String marketName;
    private String couponName;


    @BeforeMethod
    private void setupTestData(Method m){
        institutionData = new InstitutionData();
        institutionData.setTestCaseName(m.getName());
        institutionName = institutionData.getName();

        contactData = new ContactData();
        contactData.setTestCaseName(m.getName());
        contactName = contactData.getName();

        fundData = new FundData();
        fundData.setTestCaseName(m.getName());
        fundName = fundData.getName();

        equityData = new EquityData();
        equityData.setTestCaseName(m.getName());
        securityName = equityData.getSecurityName();
        marketName = equityData.getSecurityMarketName();

        fixedIncomeData = new FixedIncomeData();
        couponName = fixedIncomeData.getCouponName();

        navigation.openFunds();
    }

    @Test
    //Test Case 220390, part 1
    public void verifyRecentlyViewedInstitutionsList() {
        String investorName, foundInstitutionName;
        ArrayList<String> institutionsInSearchResults = new ArrayList<>();
        int numberOfProfilesToOpen = 20;

        investorsTab = navigation.selectInvestorsTab();
        investorName = investorsTab.getProfileNameSelectedInList();
        investorsTab.verifyProfileNameDisplayedInOverview(investorName);
        navigation
                .selectRecentlyViewedTab()
                .verifyItemPresentInList(investorName);

        navigation
                .searchInstitutions()
                .search();

        institutionSearchTab = new InstitutionSearchTab();
        foundInstitutionName = institutionSearchTab.getProfileNameDisplayedInOverview();
        navigation
                .selectRecentlyViewedTab()
                .verifyItemPresentInList(foundInstitutionName);

        navigation.selectSearchResultsTab();
        for (int i = 0; i < numberOfProfilesToOpen; i++) {
            institutionSearchTab.openNextProfileOverview();
            String institutionProfileName = institutionSearchTab.getProfileNameDisplayedInOverview();
            institutionsInSearchResults.add(institutionProfileName);
        }

        institutionsRecentlyViewedTab = (InstitutionRecentlyViewedTab) navigation.selectRecentlyViewedTab();
        institutionsInSearchResults.forEach(institutionsRecentlyViewedTab::verifyItemPresentInList);

        //TODO: use investorName when the bug with only 20 Recently Viewed institutions displayed is fixed
        String temporaryInstitutionName = institutionsInSearchResults.get(0);

        institutionsRecentlyViewedTab
//                .verifyItemPresentInList(investorName)
//                .verifyItemPresentInList(foundInstitutionName)
//
//                .openProfileSummary(foundInstitutionName)
//                .verifyProfileName(foundInstitutionName);
                .verifyItemPresentInList(temporaryInstitutionName)
                .openFullProfile(temporaryInstitutionName)
                .verifyProfileName(temporaryInstitutionName);

        navigation.back();
    }

    @Test
    //Test Case 220392, part 1
    public void verifyRecentlyViewedContactsList(){

        String contactName, foundContactName;
        ArrayList<String> contactsInSearchResults = new ArrayList<>();
        int numberOfProfilesToOpen = 20;

        activityTab = navigation.selectActivityTab();
        contactName = activityTab.getProfileNameSelectedInList();
        activityTab.verifyProfileNameDisplayedInOverview(contactName);
        navigation
                .selectRecentlyViewedTab()
                .verifyItemPresentInList(contactName);

        navigation
                .searchContacts()
                .setInstitutionName(institutionName)
                .search();

        contactSearchTab = new ContactSearchTab();
        foundContactName = contactSearchTab.getProfileNameDisplayedInOverview();
        navigation
                .selectRecentlyViewedTab()
                .verifyItemPresentInList(foundContactName);

        navigation.selectSearchResultsTab();
        for (int i = 0; i < numberOfProfilesToOpen; i++) {
            contactSearchTab.openNextProfileOverview();
            String contactProfileName = contactSearchTab.getProfileNameDisplayedInOverview();
            contactsInSearchResults.add(contactProfileName);
        }

        contactRecentlyViewedTab = (ContactRecentlyViewedTab) navigation.selectRecentlyViewedTab();
        contactsInSearchResults.forEach(contactRecentlyViewedTab::verifyItemPresentInList);

        //TODO: use investorName when the bug with only 20 Recently Viewed institutions displayed is fixed
        String temporaryContactName = contactsInSearchResults.get(0);

        contactRecentlyViewedTab
                .verifyItemPresentInList(temporaryContactName)
                .openFullProfile(temporaryContactName)
                .verifyProfileName(temporaryContactName);

        navigation.back();
    }

    @Test
    public void verifyRecentlyViewedFundsList() {
        String fundName;
        ArrayList<String> fundsInSearchResults = new ArrayList<>();
        int numberOfProfilesToOpen = 20;

        navigation.searchFunds().search();

        fundSearchTab = new FundSearchTab();

        fundName = fundSearchTab.getProfileNameSelectedInList();
        fundSearchTab.verifyProfileNameDisplayedInOverview(fundName);
        navigation
                .selectRecentlyViewedTab()
                .verifyItemPresentInList(fundName);

        navigation
                .selectSearchResultsTab();

        for (int i = 0; i < numberOfProfilesToOpen; i++) {
            fundSearchTab.openNextProfileOverview();
            String fundProfileName = fundSearchTab.getProfileNameDisplayedInOverview();
            fundsInSearchResults.add(fundProfileName);
        }

        fundRecentlyViewedTab = (FundRecentlyViewedTab) navigation.selectRecentlyViewedTab();
        fundsInSearchResults.forEach(fundRecentlyViewedTab::verifyItemPresentInList);

        //TODO: use investorName when the bug with only 20 Recently Viewed institutions displayed is fixed
        String temporaryFundName = fundsInSearchResults.get(0);

        fundRecentlyViewedTab
                .verifyItemPresentInList(temporaryFundName)
                .openFullProfile(temporaryFundName)
                .verifyProfileName(temporaryFundName);

        navigation.back();
    }

    @Test
    public void verifyRecentlyViewedSecuritiesList() {
        //String securityName;
        ArrayList<String> securitiesInSearchResults = new ArrayList<>();
        ArrayList<String> securitiesMarketNamesInSearchResults = new ArrayList<>();
        int numberOfProfilesToOpen = 20;

        navigation.clearStoredData();

        navigation.searchSecurities()
                .setSearchField(securityName)
                .search();

        securitySearchTab = new SecuritySearchTab();

        securitySearchTab.openProfileOverview(securityName, marketName);
        securitySearchTab.verifyProfileNameDisplayedInOverview(securityName);
        navigation
                .selectRecentlyViewedTab()
                .verifyItemPresentInList(securityName, marketName);

        navigation.searchSecurities()
                .clearSearchFilter()
                .search();
        for (int i = 0; i < numberOfProfilesToOpen; i++) {
            securitySearchTab.openNextProfileOverview();
            String securityProfileFullName = securitySearchTab.getProfileNameSelectedInList();
            String securityProfileName = securitySearchTab.getProfileNameDisplayedInOverview();
            String securityMarketName = securitySearchTab.getListItemSubtext(securityProfileFullName);
            securitiesInSearchResults.add(securityProfileName);
            securitiesMarketNamesInSearchResults.add(securityMarketName);
        }

        securityRecentlyViewedTab = (SecurityRecentlyViewedTab) navigation.selectRecentlyViewedTab();
        securitiesInSearchResults.forEach(securityRecentlyViewedTab::verifyItemPresentInList);

        //TODO: use investorName when the bug with only 20 Recently Viewed institutions displayed is fixed
        String temporarySecurityName = securitiesInSearchResults.get(0);
        String temporarySecuritySubtextName = securitiesMarketNamesInSearchResults.get(0);

        securityRecentlyViewedTab
                .verifyItemPresentInList(temporarySecurityName, temporarySecuritySubtextName)
                .openFullProfile(temporarySecurityName, temporarySecuritySubtextName);
        new EqSecurityFullProfile().verifyProfileName(temporarySecurityName);

        navigation.back();
    }

    @Test
    public void verifyRecentlyPreViewedProfile(){
        navigation.openInstitutions();

        investorsTab = new InvestorsTab();

        String firstProfileSelectedOnInvestorsTab = investorsTab.getProfileNameSelectedInList();
        investorsTab.openNextProfileOverview();
        String secondProfileNameSelectedOnInvestorsTab = investorsTab.getProfileNameSelectedInList();

        institutionsRecentlyViewedTab = (InstitutionRecentlyViewedTab) navigation.selectRecentlyViewedTab();

        String firstProfileSelectedOnRecentlyViewedTab = secondProfileNameSelectedOnInvestorsTab;
        String secondProfileSelectedOnRecentlyViewedTab = firstProfileSelectedOnInvestorsTab;

        institutionsRecentlyViewedTab.verifyProfileNameSelectedInList(firstProfileSelectedOnRecentlyViewedTab);
        institutionsRecentlyViewedTab.openNextProfileOverview();
        institutionsRecentlyViewedTab.verifyProfileNameSelectedInList(secondProfileSelectedOnRecentlyViewedTab);

        institutionsRecentlyViewedTab
                .openFullProfile(secondProfileSelectedOnRecentlyViewedTab)
                .verifyProfileName(secondProfileSelectedOnRecentlyViewedTab);
    }
}
