package ipreomobile.ui.profiles.fullProfileTabs;

import ipreomobile.core.Driver;
import ipreomobile.core.Logger;
import ipreomobile.core.Verify;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.BaseOverlay;
import ipreomobile.templates.ui.ProfileTab;
import ipreomobile.ui.blocks.KeyValueTable;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

public class InstitutionTargetingProfileTab extends ProfileTab {

    private static final By TARGETING_OVERLAY_LOCATOR = By.className("targeting-overlay");
    private static final By DONE_BUTTON_LOCATOR       = By.xpath(new XPathBuilder().byText("Done").build());
    private static final By SHOW_INFO_BUTTON_LOCATOR  = By.className("ipreo-info");

    private static final String TARGETING_OVERLAY_TITLE       = "Targeting Charts";
    private static final String TARGETING_OVERLAY_TITLE_CLASS = "x-title";

    private static final String FACTOR_TABLE_XPATH       = new XPathBuilder().byClassName("factor-table")
            .withChildText("%s").build();
    private static final String FACTOR_TABLE_ENTRY_XPATH = new XPathBuilder().byCurrentItem().byTag("*").withChildText("%s").build();

    private static final String CURRENCY_SUFFIX = "(" + System.getProperty("test.currency") + "M)";

    private KeyValueTable details = new KeyValueTable(
            new XPathBuilder().byClassName("profile-details").byClassName("row").build(),
            new XPathBuilder().byClassName("label").build(),
            new XPathBuilder().byClassName("value").build()
    );

    public InstitutionTargetingProfileTab verifyDetailsData(String label, String value){
        details.verifyValue(label, value);
        return this;
    }

    public InstitutionTargetingProfileTab verifyFactorTableEntriesPresent(String tableName, String ... entryNames) {
        String factorTableXpath = String.format(FACTOR_TABLE_XPATH, tableName);
       SenchaWebElement factorTable = Driver.findVisible(By.xpath(factorTableXpath));
        for (String entry: entryNames) {
            String factorEntryXpath = String.format(FACTOR_TABLE_ENTRY_XPATH, entry);
            if (Driver.findVisible(By.xpath(factorEntryXpath), factorTable) == null) {
                Logger.logError("No row '"+entry+"' was found in Factor Table '"+tableName+"'.");
            }
        }
        return this;
    }

    public String getDetailsData(String label){
        return details.getValue(label);
    }

    public InstitutionTargetingProfileTab showTargetingChartsInfo(){
        Driver.findVisible(SHOW_INFO_BUTTON_LOCATOR).click();
        return this;
    }

    public InstitutionTargetingProfileTab hideTargetingChartsInfo(){
        By maskSelector = BaseOverlay.getActiveMaskLocator();
        Driver.findVisible(DONE_BUTTON_LOCATOR).click();
        BaseOverlay.waitMaskHidden(maskSelector);
        return this;
    }

    public InstitutionTargetingProfileTab verifyTargetingChartsInfo(){
        showTargetingChartsInfo();
       SenchaWebElement overlayContainer = Driver.findVisible(TARGETING_OVERLAY_LOCATOR);
        String actualTitle = Driver.findVisible(By.className(TARGETING_OVERLAY_TITLE_CLASS), overlayContainer).getText();
        Assert.assertEquals(
                actualTitle,
                TARGETING_OVERLAY_TITLE,
                "Targeting charts information overlay title '"+TARGETING_OVERLAY_TITLE_CLASS+"' was not found; found '"+actualTitle+"' instead.");
        hideTargetingChartsInfo();
        return this;
    }

    public InstitutionTargetingProfileTab verifyTargetingDetailsPresent(){
        Verify.verifyNotNull(getDetailsData("Suitability"), "Expected to find 'Suitability' value on Targeting tab: ");
        Verify.verifyNotNull(getDetailsData("Normal Purchasing Power"), "Expected to find 'Normal Purchasing Power' value on Targeting tab: ");
        Verify.verifyNotNull(getDetailsData("Normal Purchasing Power Value "+CURRENCY_SUFFIX), "Expected to find 'Normal Purchasing Power Value "+CURRENCY_SUFFIX+"' value on Targeting tab: ");

        Verify.verifyNotNull(getDetailsData("Priority Rank"), "Expected to find 'Priority Rank' value on Targeting tab: ");
        Verify.verifyNotNull(getDetailsData("Stretch Purchasing Power"), "Expected to find 'Stretch Purchasing Power' value on Targeting tab: ");
        Verify.verifyNotNull(getDetailsData("Stretch Purchasing Power Value "+CURRENCY_SUFFIX), "Expected to find 'Stretch Purchasing Power Value "+CURRENCY_SUFFIX+"' value on Targeting tab: ");
        return this;
    }


}
