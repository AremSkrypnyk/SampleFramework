package ipreomobile.ui.profiles.fullProfileTabs;

import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.ProfileTab;
import ipreomobile.ui.blocks.KeyValueTable;

import java.util.List;

public class BaseAdditionalInfoProfileTab extends ProfileTab {
    private KeyValueTable infoTable = new KeyValueTable(
            new XPathBuilder().byClassName("row").build(),
            new XPathBuilder().byClassName("label").build(),
            new XPathBuilder().byClassName("value").build()
    );

    public void verifyAdditionalInfoField(String fieldName, String fieldValue) {
        infoTable.verifyValue(fieldName, fieldValue);
    }

    public String getAdditionalInfoField(String fieldName) {
        return infoTable.getValue(fieldName);
    }

    public BaseAdditionalInfoProfileTab verifyAdditionalInfoFieldPresent(String fieldName) {
        infoTable.verifyValuePresent(fieldName);
        return this;
    }

    public BaseAdditionalInfoProfileTab verifyAdditionalInfoFieldsPresent(List<String> fieldNames) {
        for (String fieldName : fieldNames) {
            infoTable.verifyValuePresent(fieldName);
        }
        return this;
    }
}
