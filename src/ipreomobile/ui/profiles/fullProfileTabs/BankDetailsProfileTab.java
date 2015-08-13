package ipreomobile.ui.profiles.fullProfileTabs;

import ipreomobile.core.Driver;
import ipreomobile.core.ElementHelper;
import ipreomobile.core.Verify;
import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.GlobalNavigation;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

import java.util.List;

public class BankDetailsProfileTab extends BaseDetailsProfileTab {
    private static final String TABLE_XPATH = new XPathBuilder()
            .byClassName("table-name-value")
            .withChildTag("div")
            .withClassName("header")
            .withChildTextContains("%s")
            .build();

    private static final By SUBHEADER_COLUMNS_LOCATOR = By.xpath(new XPathBuilder().byClassName("even", "subheader").byClassName("column").build());

    private static final String CURRENCY_SUFFIX = "(" + System.getProperty("test.currency") + "M)";

    public BankDetailsProfileTab verifyTablePresent(String tableName) {
        Assert.assertNotNull(getTableByHeader(tableName), "Table '"+tableName+"' was not found on Details tab.");
        return this;
    }

    public BankDetailsProfileTab verifyCurrencyInTable(String tableName) {
       SenchaWebElement table = getTableByHeader(tableName);
        List<SenchaWebElement> columnHeaders = Driver.findAll(SUBHEADER_COLUMNS_LOCATOR, table);
        columnHeaders.remove(0).bringToView();
        Assert.assertTrue(columnHeaders.size() >=0, "Currency check failed: invalid number of column headers was found for table '"+tableName+"': ");
        for (SenchaWebElement columnHeader: columnHeaders) {
            String header = columnHeader.getText();
            if (headerContainsCurrency(header)) {
                Verify.verifyTrue(columnHeader.getText().endsWith(CURRENCY_SUFFIX), "Expected currency was not found in table '" + tableName + "', column '"
                        + columnHeader.getText() + "', " + new GlobalNavigation().getPageTitle());
            }
        }
        return this;
    }

    private SenchaWebElement getTableByHeader(String tableName){
        String fullTableXpath = String.format(TABLE_XPATH, tableName);
        return Driver.findIfExists(By.xpath(fullTableXpath));
    }

    private boolean headerContainsCurrency(String header){
        return header.contains("(") && header.contains(")");
    }

}
