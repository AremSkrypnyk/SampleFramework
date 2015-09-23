package ipreomobile.test.navigation;

import ipreomobile.data.EquityData;
import ipreomobile.data.FixedIncomeData;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.securities.SecurityRecentlyViewedTab;
import ipreomobile.ui.securities.SecuritySearchTab;
import org.testng.annotations.Test;

public class Securities extends BaseTest {

    private SecuritySearchTab securitySearchTab;
    private SecurityRecentlyViewedTab recentlyViewedTab;

    private FixedIncomeData fixedIncomeData;
    private EquityData equityData;

    @Test
    public void verifySecurityRecentlyViewedTab(){
        equityData = new EquityData();
        fixedIncomeData = new FixedIncomeData();

        navigation
                .searchSecurities()
                .searchEquities()
                .setTickerOrSecurityName(equityData.getSecurityName())
                .search();
        securitySearchTab = new SecuritySearchTab();
        securitySearchTab.openEquityFullProfile(equityData.getSecurityName(), equityData.getSecurityMarketName());
        navigation.back();

        navigation
                .searchSecurities()
                .searchFixedIncomes()
                .setTickerOrSecurityName(fixedIncomeData.getCouponName())
                .search();

        securitySearchTab = new SecuritySearchTab();
        securitySearchTab.openFixedIncomeFullProfile(fixedIncomeData.getCouponName());
        navigation.back();

        navigation.selectRecentlyViewedTab();
        recentlyViewedTab = new SecurityRecentlyViewedTab();

        recentlyViewedTab.openEquityFullProfile(equityData.getSecurityName(), equityData.getSecurityMarketName());
        navigation.back();

        recentlyViewedTab.openFixedIncomeFullProfile(fixedIncomeData.getCouponName());
        navigation.back();
    }


}
