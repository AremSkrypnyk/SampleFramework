package ipreomobile.ui.profiles.fullProfileTabs;

import ipreomobile.core.Driver;
import ipreomobile.ui.blocks.EyeButton;
import org.openqa.selenium.By;

public class InstitutionOwnershipProfileTab extends BaseOwnershipProfileTab {

    private static final By CURRENT_HOLDINGS_PUBLIC_LOCATOR       = By.className("holdings-public");
    private static final By CURRENT_HOLDINGS_SURVEILLANCE_LOCATOR = By.className("holdings-surveillance");

    private EyeButton eye = new EyeButton();

    public InstitutionOwnershipProfileTab(){

    }

    public InstitutionOwnershipProfileTab selectEquityTab(){
        super.selectEquityTab();
        return this;
    }

    public InstitutionOwnershipProfileTab selectFixedIncomeTab(){
        super.selectFixedIncomeTab();
        return this;
    }

    public InstitutionOwnershipProfileTab showSurveillanceData(){
        eye.showSurveillanceData();
        defaultTickerHoldings.setContainer(Driver.findOne(CURRENT_HOLDINGS_SURVEILLANCE_LOCATOR));
        waitReady();
        return this;
    }

    public InstitutionOwnershipProfileTab showPublicData(){
        eye.showPublicData();
        defaultTickerHoldings.setContainer(Driver.findOne(CURRENT_HOLDINGS_PUBLIC_LOCATOR));
        waitReady();
        return this;
    }

    public InstitutionOwnershipProfileTab verifyPositionHistoryChart(){
        super.verifyPositionHistoryChart();
        return this;
    }

    public InstitutionOwnershipProfileTab verifyEquityOwnershipChart(){
        super.verifyEquityOwnershipChart();
        return this;
    }

    public InstitutionOwnershipProfileTab verifySurveillanceDataShown(){
        eye.verifySurveillanceDataShown();
        return this;
    }

    public InstitutionOwnershipProfileTab verifyPublicDataShown(){
        eye.verifyPublicDataShown();
        return this;
    }

    public InstitutionOwnershipProfileTab verifyDefaultTicker(){
        super.verifyDefaultTicker();
        return this;
    }

    public InstitutionOwnershipProfileTab verifyTickerInCurrentHoldingsTableHeader(){
        super.verifyTickerInCurrentHoldingsTableHeader();
        return this;
    }

    public InstitutionOwnershipProfileTab verifyTickerInCurrentHoldingsTableEntries() {
        super.verifyTickerInCurrentHoldingsTableEntries();
        return this;
    }

    public InstitutionOwnershipProfileTab verifyCurrencyInTopHoldingsHeader(){
        super.verifyCurrencyInTopHoldingsHeader();
        return this;
    }

    public InstitutionOwnershipProfileTab verifyCurrencyInOwnershipSummaryTable(){
        super.verifyCurrencyInOwnershipSummaryTable();
        return this;
    }

    public InstitutionOwnershipProfileTab verifyCurrencyInTopBuySellTableHeader(String header){
        super.verifyCurrencyInTopBuySellTableHeader(header);
        return this;
    }

    public InstitutionOwnershipProfileTab verifyFixedIncomePortfolioChart(){
        super.verifyFixedIncomePortfolioChart();
        return this;
    }

    public InstitutionOwnershipProfileTab selectTopBuySellTable(String expectedHeader) {
        super.selectTopBuySellTable(expectedHeader);
        return this;
    }

}
