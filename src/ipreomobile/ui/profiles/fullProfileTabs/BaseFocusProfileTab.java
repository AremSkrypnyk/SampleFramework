package ipreomobile.ui.profiles.fullProfileTabs;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.ProfileTab;
import ipreomobile.ui.blocks.KeyValueTable;
import org.openqa.selenium.By;

import java.util.List;

public class BaseFocusProfileTab extends ProfileTab {

    protected String investmentApproachSubTab;
    protected String equitySubTab;
    protected String fixedIncomeSubTab;

    protected static final By EQUITY_TABLE_LOCATOR       = By.xpath(new XPathBuilder().byClassName("equityFocus", "table-name-value").build());
    protected static final By FIXED_INCOME_TABLE_LOCATOR = By.xpath(new XPathBuilder().byClassName("fiFocus", "table-name-value").build());

    protected KeyValueTable focusTable = new KeyValueTable(
            new XPathBuilder().byTag("div").byXpathPart(KeyValueTable.ODD_OR_EVEN_ROW_CLASS_CONDITION).build(),
            new XPathBuilder().byClassName("first-column").build(),
            new XPathBuilder().byClassName("last-column").build()
    );

    public void setInvestmentApproachSubTabTitle(String investmentApproachSubTabTitle) {
        this.investmentApproachSubTab = investmentApproachSubTabTitle;
    }

    public void setEquitySubTabTitle(String equitySubTabTitle) {
        this.equitySubTab = equitySubTabTitle;
    }

    public void setFixedIncomeSubTabTitle(String fixedIncomeSubTabTitle) {
        this.fixedIncomeSubTab = fixedIncomeSubTabTitle;
    }

    public void selectInvestmentApproachTab(){
        selectTab(investmentApproachSubTab);
    }

    public BaseFocusProfileTab selectEquityFocusTab(){
        selectTab(equitySubTab);
        focusTable.setContainer(Driver.findVisible(EQUITY_TABLE_LOCATOR));
        return this;
    }
    public BaseFocusProfileTab selectFixedIncomeFocusTab(){
        selectTab(fixedIncomeSubTab);
        focusTable.setContainer(Driver.findVisible(FIXED_INCOME_TABLE_LOCATOR));
        return this;
    }

    public String getFocusTableValue(String fieldName){
        return focusTable.getValue(fieldName);
    }

    public BaseFocusProfileTab verifyFocusTableValue(String fieldName, String expectedValue){
        focusTable.verifyValue(fieldName, expectedValue);
        return this;
    }

    public BaseFocusProfileTab verifyAllFocusTableLabelsPresent(List<String> labelNames){
        for (String label: labelNames) {
            focusTable.verifyKeyPresent(label);
        }
        return this;
    }

    public BaseFocusProfileTab verifyFocusTableLabelPresent(String fieldName){
        focusTable.verifyKeyPresent(fieldName);
        return this;
    }

    public BaseFocusProfileTab verifyEqInvestmentApproachPresence() {
        Driver.verifyExactTextPresentAndVisible("Equity Investment Approach");
        return this;
    }
    public BaseFocusProfileTab verifyEqInvestmentApproachAbsence() {
        Driver.verifyExactTextAbsent("Equity Investment Approach");
        return this;
    }

    public BaseFocusProfileTab verifyFiInvestmentApproachPresence() {
        Driver.verifyExactTextPresentAndVisible("Fixed Income Investment Approach");
        return this;
    }
    public BaseFocusProfileTab verifyFiInvestmentApproachAbsence() {
        Driver.verifyExactTextAbsent("Fixed Income Investment Approach");
        return this;
    }
}
