package ipreomobile.ui.profiles.fullProfileTabs;

import ipreomobile.core.Driver;
import org.testng.Assert;

import static ipreomobile.core.Logger.logError;

public class FiSecurityCurrentHoldersProfileTab extends BaseCurrentHoldersProfileTab {

    private String currency = System.getProperty("test.currency") + "M";

    public FiSecurityCurrentHoldersProfileTab(){
        super();
        setContainerClass("fisecurity-currentholders");
    }

    public String getInstitutionValue(String institutionName){
        verifyActiveTab(INSTITUTIONS_SUB_TAB);
        return getProperty(institutionName, "Value (" + currency + "):");
    }

    public FiSecurityCurrentHoldersProfileTab verifyInstitutionValue(String institutionName, String expectedValue){
        Assert.assertEquals(getInstitutionValue(institutionName), expectedValue, "Institution Value mismatch: ");
        return this;
    }

    public String getInstitutionParHeld(String institutionName){
        verifyActiveTab(INSTITUTIONS_SUB_TAB);
        return getProperty(institutionName, "Par Held (" + currency + "):");
    }

    public FiSecurityCurrentHoldersProfileTab verifyInstitutionParHeld(String institutionName, String expectedValue){
        Assert.assertEquals(getInstitutionParHeld(institutionName), expectedValue, "Institution Par Held mismatch: ");
        return this;
    }

    public String getInstitutionParChange(String institutionName){
        verifyActiveTab(INSTITUTIONS_SUB_TAB);
        return getProperty(institutionName, "Par Change (" + currency + "):");
    }

    public FiSecurityCurrentHoldersProfileTab verifyInstitutionParChange(String institutionName, String expectedValue){
        Assert.assertEquals(getInstitutionParChange(institutionName), expectedValue, "Institution Par Change mismatch: ");
        return this;
    }

    public String getInstitutionPositionDate(String institutionName){
        verifyActiveTab(INSTITUTIONS_SUB_TAB);
        return getProperty(institutionName, "Position Date:");
    }

    public FiSecurityCurrentHoldersProfileTab verifyInstitutionPositionDate(String institutionName, String expectedValue){
        Assert.assertEquals(getInstitutionPositionDate(institutionName), expectedValue, "Institution Position Date mismatch: ");
        return this;
    }

    public String getInstitutionFIAssetsUnderManagement(String institutionName){
        verifyActiveTab(INSTITUTIONS_SUB_TAB);
        return getProperty(institutionName, "FI Assets Under Management (" + currency + "):");
    }

    public FiSecurityCurrentHoldersProfileTab verifyInstitutionFIAssetsUnderManagement(String institutionName, String expectedValue){
        Assert.assertEquals(getInstitutionFIAssetsUnderManagement(institutionName), expectedValue, "Institution FI Assets Under Management mismatch: ");
        return this;
    }

    public String getInstitutionInvestmentCenter(String institutionName){
        verifyActiveTab(INSTITUTIONS_SUB_TAB);
        return getProperty(institutionName, "Investment Center:");
    }

    public FiSecurityCurrentHoldersProfileTab verifyInstitutionInvestmentCenter(String institutionName, String expectedValue){
        Assert.assertEquals(getInstitutionInvestmentCenter(institutionName), expectedValue, "Institution Investment Center mismatch: ");
        return this;
    }


    //The same methods provided for FUNDS as well because it makes sense from business logic side.
    public String getFundValue(String fundName){
        verifyActiveTab(FUNDS_SUB_TAB);
        return getProperty(fundName, "Value (" + currency + "):");
    }

    public FiSecurityCurrentHoldersProfileTab verifyFundValue(String fundName, String expectedValue){
        Assert.assertEquals(getFundValue(fundName), expectedValue, "Fund Value mismatch: ");
        return this;
    }

    public String getFundParHeld(String fundName){
        verifyActiveTab(FUNDS_SUB_TAB);
        return getProperty(fundName, "Par Held (" + currency + "):");
    }

    public FiSecurityCurrentHoldersProfileTab verifyFundParHeld(String fundName, String expectedValue){
        Assert.assertEquals(getFundParHeld(fundName), expectedValue, "Fund Par Held mismatch: ");
        return this;
    }

    public String getFundParChange(String fundName){
        verifyActiveTab(FUNDS_SUB_TAB);
        return getProperty(fundName, "Par Change (" + currency + "):");
    }

    public FiSecurityCurrentHoldersProfileTab verifyFundParChange(String fundName, String expectedValue){
        Assert.assertEquals(getFundParChange(fundName), expectedValue, "Fund Par Change mismatch: ");
        return this;
    }

    public String getFundPositionDate(String fundName){
        verifyActiveTab(FUNDS_SUB_TAB);
        return getProperty(fundName, "Position Date:");
    }

    public FiSecurityCurrentHoldersProfileTab verifyFundPositionDate(String fundName, String expectedValue){
        Assert.assertEquals(getFundPositionDate(fundName), expectedValue, "Fund Position Date mismatch: ");
        return this;
    }

    public String getFundFIAssetsUnderManagement(String fundName){
        verifyActiveTab(FUNDS_SUB_TAB);
        return getProperty(fundName, "FI Assets Under Management (" + currency + "):");
    }

    public FiSecurityCurrentHoldersProfileTab verifyFundFIAssetsUnderManagement(String fundName, String expectedValue){
        Assert.assertEquals(getFundFIAssetsUnderManagement(fundName), expectedValue, "Fund FI Assets Under Management mismatch: ");
        return this;
    }

    public String getFundInvestmentCenter(String fundName){
        verifyActiveTab(FUNDS_SUB_TAB);
        return getProperty(fundName, "Investment Center:");
    }

    public FiSecurityCurrentHoldersProfileTab verifyFundInvestmentCenter(String fundName, String expectedValue){
        Assert.assertEquals(getFundInvestmentCenter(fundName), expectedValue, "Fund Investment Center mismatch: ");
        return this;
    }

    private void verifyCurrencyInDetailsForProfile(String profileName) {
        String[] propertiesWithCurrency = {
                "Par Held (" + currency + "):",
                "Par Change (" + currency + "):",
                "Value (" + currency + "):",
                "FI Assets Under Management (" + currency + "):",
        };

        for (int i = 0; i < propertiesWithCurrency.length; i++) {
            try {
                getProperty(profileName, propertiesWithCurrency[i]);
            } catch (AssertionError e) {
                logError("Property with currency [" + propertiesWithCurrency[i] + "] was not found. " + e.getMessage(), e);
            }
        }

    }

    public FiSecurityCurrentHoldersProfileTab verifyCurrencyInDetailsForInstitutionProfile(String institutionProfileName) {
        selectInstitutionsTab();
        verifyCurrencyInDetailsForProfile(institutionProfileName);
        return this;
    }

    public FiSecurityCurrentHoldersProfileTab verifyCurrencyInDetailsForFundProfile(String fundProfileName) {
        selectFundsTab();
        verifyCurrencyInDetailsForProfile(fundProfileName);
        return this;
    }

}
