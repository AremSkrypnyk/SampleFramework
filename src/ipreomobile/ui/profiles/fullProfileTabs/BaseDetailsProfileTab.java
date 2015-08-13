package ipreomobile.ui.profiles.fullProfileTabs;

import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.ProfileTab;
import ipreomobile.ui.blocks.KeyValueTable;

import java.util.List;

/**
 * Created by artem_skrypnyk on 8/29/2014.
 */
public class BaseDetailsProfileTab extends ProfileTab {

    protected String tableRowXpath = new XPathBuilder().byClassName("columns", "row").build();
    protected String keyColumnXpath = new XPathBuilder().byClassName("label").build();
    protected String valueColumnXpath = new XPathBuilder().byClassName("value").build();
    protected KeyValueTable table = new KeyValueTable(
            tableRowXpath,
            keyColumnXpath,
            valueColumnXpath
    );

    public String getValue(String label) {
        return table.getValue(label);
    }

    public BaseDetailsProfileTab verifyValue(String fieldName, String expectedValue){
        table.verifyValue(fieldName, expectedValue);
        return this;
    }

    public BaseDetailsProfileTab verifyLabelPresent(String fieldName){
        table.verifyKeyPresent(fieldName);
        return this;
    }

    public BaseDetailsProfileTab verifyAllLabelsPresent(List<String> fieldNames){
        for (String fieldName : fieldNames) {
            table.verifyKeyPresent(fieldName);
        }
        return this;
    }

    public BaseDetailsProfileTab verifyHoldingsChart(String chartName) {
        verifyDiagram(chartName);
        verifyDiagramLegend(chartName);
        return this;
    }
}
