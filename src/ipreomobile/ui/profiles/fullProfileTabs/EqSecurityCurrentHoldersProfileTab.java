package ipreomobile.ui.profiles.fullProfileTabs;

import ipreomobile.core.Driver;
import org.openqa.selenium.By;
import org.testng.Assert;

import static ipreomobile.core.Logger.logError;

public class EqSecurityCurrentHoldersProfileTab extends BaseCurrentHoldersProfileTab {

    public EqSecurityCurrentHoldersProfileTab(){
        super();
        setContainerClass("eqsecurity-currentholders");
    }

    public String getInstitutionValue(String institutionName){
        verifyActiveTab(INSTITUTIONS_SUB_TAB);
        return getProperty(institutionName, "Value (" + System.getProperty("test.currency") + "M):");
    }

    public void verifyInstitutionValue(String institutionName, String expectedValue){
        Assert.assertEquals(getInstitutionValue(institutionName), expectedValue, "Institution Value mismatch: ");
    }

    public String getInstitutionShares(String institutionName){
        verifyActiveTab(INSTITUTIONS_SUB_TAB);
        return getProperty(institutionName, "Shares:");
    }

    public void verifyInstitutionShares(String institutionName, String expectedValue){
        Assert.assertEquals(getInstitutionShares(institutionName), expectedValue, "Institution Shares mismatch: ");
    }

    public String getInstitutionChange(String institutionName){
        verifyActiveTab(INSTITUTIONS_SUB_TAB);
        return getProperty(institutionName, "Change:");
    }

    public void verifyInstitutionChange(String institutionName, String expectedValue){
        Assert.assertEquals(getInstitutionChange(institutionName), expectedValue, "Institution Change mismatch: ");
    }

    public String getInstitutionPercentSO(String institutionName){
        verifyActiveTab(INSTITUTIONS_SUB_TAB);
        return getProperty(institutionName, "% S / O:");
    }

    public void verifyInstitutionPercentSO(String institutionName, String expectedValue){
        Assert.assertEquals(getInstitutionPercentSO(institutionName), expectedValue, "Institution % SO mismatch: ");
    }

    public String getInstitutionPercentPortfolio(String institutionName){
        verifyActiveTab(INSTITUTIONS_SUB_TAB);
        return getProperty(institutionName, "% Portfolio:");
    }

    public void verifyInstitutionPercentPortfolio(String institutionName, String expectedValue){
        Assert.assertEquals(getInstitutionPercentPortfolio(institutionName), expectedValue, "Institution % Portfolio mismatch: ");
    }


//The same methods provided for FUNDS as well because it makes sense from business logic side.
    public String getFundValue(String fundName){
        verifyActiveTab(FUNDS_SUB_TAB);
        return getProperty(fundName, "Value ");
    }

    public void verifyFundValue(String fundName, String expectedValue){
        Assert.assertEquals(getFundValue(fundName), expectedValue, "Fund Value mismatch: ");
    }

    public String getFundShares(String fundName){
        verifyActiveTab(FUNDS_SUB_TAB);
        return getProperty(fundName, "Shares:");
    }

    public void verifyFundShares(String fundName, String expectedValue){
        Assert.assertEquals(getFundShares(fundName), expectedValue, "Fund Shares mismatch: ");
    }

    public String getFundChange(String fundName){
        verifyActiveTab(FUNDS_SUB_TAB);
        return getProperty(fundName, "Change:");
    }

    public void verifyFundChange(String fundName, String expectedValue){
        Assert.assertEquals(getFundChange(fundName), expectedValue, "Fund Change mismatch: ");
    }

    public String getFundPercentSO(String fundName){
        verifyActiveTab(FUNDS_SUB_TAB);
        return getProperty(fundName, "% S / O:");
    }

    public void verifyFundPercentSO(String fundName, String expectedValue){
        Assert.assertEquals(getFundPercentSO(fundName), expectedValue, "Fund % SO mismatch: ");
    }

    public String getFundPercentPortfolio(String fundName){
        verifyActiveTab(FUNDS_SUB_TAB);
        return getProperty(fundName, "% Portfolio:");
    }

    public void verifyFundPercentPortfolio(String fundName, String expectedValue){
        Assert.assertEquals(getFundPercentPortfolio(fundName), expectedValue, "Fund % Portfolio mismatch: ");
    }

    private void verifyCurrencyInDetailsForProfile(String profileName) {
        String propertyWithCurrency = "Value (" + System.getProperty("test.currency") + "M):";

        try {
            getProperty(profileName, propertyWithCurrency);
        } catch (AssertionError e) {
            logError("Property with currency ["+propertyWithCurrency+"] was not found. " + e.getMessage(), e);
        }
    }

    public EqSecurityCurrentHoldersProfileTab verifyCurrencyInDetailsForInstitutionProfile(String institutionProfileName) {
        selectInstitutionsTab();
        verifyCurrencyInDetailsForProfile(institutionProfileName);
        return this;
    }

    public EqSecurityCurrentHoldersProfileTab verifyCurrencyInDetailsForFundProfile(String fundProfileName) {
        selectFundsTab();
        verifyCurrencyInDetailsForProfile(fundProfileName);
        return this;
    }
}
