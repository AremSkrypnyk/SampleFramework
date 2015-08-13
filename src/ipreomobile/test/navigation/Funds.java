package ipreomobile.test.navigation;

import ipreomobile.data.FundData;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.funds.FundRecentlyViewedTab;
import ipreomobile.ui.funds.FundSearchTab;
import org.testng.annotations.Test;

public class Funds extends BaseTest {
    FundData fundData;
    FundSearchTab fundSearchTab;
    FundRecentlyViewedTab recentlyViewedTab;

    @Test
    public void verifyFundRecentViewedTab(){
        fundData = new FundData();
        navigation.searchFunds(fundData.getName());
        fundSearchTab = new FundSearchTab();
        fundSearchTab.openFullProfile(fundData.getName());
        navigation.back();

        navigation.selectTab(UITitles.PanelTabs.RECENTLY_VIEWED);
        recentlyViewedTab = new FundRecentlyViewedTab();
        recentlyViewedTab.openFullProfile(fundData.getName());
        navigation.back();
    }
}
