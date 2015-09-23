package ipreomobile.ui.profiles.fullProfileTabs;

import ipreomobile.core.Driver;
import ipreomobile.core.Verify;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.ProfileTab;
import ipreomobile.ui.blocks.KeyValueTable;
import org.openqa.selenium.By;
import org.testng.Assert;

public class ContactOwnershipProfileTab extends ProfileTab {
    private KeyValueTable valueTable = new KeyValueTable(
            new XPathBuilder().byClassName("table-name-value")
                    .byTag("div")
                    .withNoClassName("header")
                    .byXpathPart(KeyValueTable.ODD_OR_EVEN_ROW_CLASS_CONDITION)
                    .build(),
            new XPathBuilder().byClassName("first-column").build(),
            new XPathBuilder().byClassName("last-column").build()
    );

    public String getContactHolding(String value) {
        return valueTable.getValue(value);
    }

    public ContactOwnershipProfileTab verifyContactHolding(String value, String contactHolding) {
        valueTable.verifyValue(value, contactHolding);
        return this;
    }

    public ContactOwnershipProfileTab verifyValuePresent(String value) {
        valueTable.verifyValuePresent(value);
        return this;
    }

    public ContactOwnershipProfileTab verifyCurrencyInTableHeader() {
        String tableHeader = valueTable.getHeader();
        String expectedCurrency = "(" + System.getProperty("test.currency") + "M)";
        Verify.verifyContainsText(tableHeader, expectedCurrency,
                "No expected currency expression [" + expectedCurrency + "] was found in table header on "+getScreenName()+".");
        return this;
    }
}
