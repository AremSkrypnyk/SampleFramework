package ipreomobile.ui.profiles.fullProfileTabs;

public class FundOwnershipProfileTab extends BaseOwnershipProfileTab {

    public FundOwnershipProfileTab(){

    }

    public FundOwnershipProfileTab verifyDefaultTicker(){
        super.verifyDefaultTicker();
        return this;
    }

    public FundOwnershipProfileTab verifyTickerInCurrentHoldingsTableHeader(){
        super.verifyTickerInCurrentHoldingsTableHeader();
        return this;
    }

    public FundOwnershipProfileTab verifyTickerInCurrentHoldingsTableEntries() {
        super.verifyTickerInCurrentHoldingsTableEntries();
        return this;
    }

    public FundOwnershipProfileTab verifyCurrencyInTopHoldingsHeader(){
        super.verifyCurrencyInTopHoldingsHeader();
        return this;
    }

    public FundOwnershipProfileTab verifyCurrencyInOwnershipSummaryTable(){
        super.verifyCurrencyInOwnershipSummaryTable();
        return this;
    }

    public FundOwnershipProfileTab verifyCurrencyInTopBuySellTableHeader(String header){
        super.verifyCurrencyInTopBuySellTableHeader(header);
        return this;
    }

    public FundOwnershipProfileTab verifyFixedIncomePortfolioChart(){
        super.verifyFixedIncomePortfolioChart();
        return this;
    }

    public FundOwnershipProfileTab selectTopBuySellTable(String expectedHeader) {
        super.selectTopBuySellTable(expectedHeader);
        return this;
    }

    public FundOwnershipProfileTab verifyPositionHistoryChart(){
        super.verifyPositionHistoryChart();
        return this;
    }

    public FundOwnershipProfileTab verifyEquityOwnershipChart(){
        super.verifyEquityOwnershipChart();
        return this;
    }

    public FundOwnershipProfileTab selectEquityTab(){
        super.selectEquityTab();
        return this;
    }

    public FundOwnershipProfileTab selectFixedIncomeTab(){
        super.selectFixedIncomeTab();
        return this;
    }

}
