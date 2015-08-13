package ipreomobile.test.profiles.security;

import ipreomobile.data.EquityData;
import ipreomobile.data.FixedIncomeData;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.profiles.fullProfileTabs.EqSecurityDetailsProfileTab;
import ipreomobile.ui.profiles.fullProfileTabs.FiSecurityDetailsProfileTab;
import ipreomobile.ui.profiles.overviewCards.IndustryCard;
import ipreomobile.ui.profiles.overviewCards.PriceHistoryCard;
import ipreomobile.ui.profiles.overviewCards.SharesCard;
import ipreomobile.ui.profiles.overviewCards.SummaryCard;
import ipreomobile.ui.securities.EqSecurityFullProfile;
import ipreomobile.ui.securities.SecurityProfileOverview;
import ipreomobile.ui.securities.SecuritySearchTab;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static ipreomobile.ui.UITitles.ProfileCardTitle.*;

public class EquitySecurityProfileOverviewSmoke extends BaseTest {

    private SecuritySearchTab securitySearchTab;
    private SecurityProfileOverview securityProfileOverview;

    private EquityData equityData;
    private EqSecurityFullProfile eqSecurityFullProfile;
    private String equityName;
    private String equityMarketName;

    @BeforeMethod
    public void setupTestData(Method testMethod){
        equityData = new EquityData();
        equityData.setTestCaseName(testMethod.getName());
        equityName = equityData.getSecurityName();
        equityMarketName = equityData.getSecurityMarketName();

        navigation
                .openSecurities()
                .searchSecurities()
                .setTickerOrSecurityName(equityName)
                .search();
        securitySearchTab = new SecuritySearchTab();
        securitySearchTab.openProfileOverview(equityName, equityMarketName);
        securityProfileOverview = securitySearchTab.getActiveProfileOverview();

    }

    @Test
    public void verifySharesCardOnEquityProfileOverview() {
        SharesCard sharesCard;
        EqSecurityDetailsProfileTab detailsProfileTab;
        String buys;

        securityProfileOverview
                .verifyCardPresence(SHARES)
                .verifyCardPresence(INDUSTRY)
                .verifyCardPresence(PRICE_HISTORY);

        sharesCard = new SharesCard();
        buys = sharesCard.getBuys();
        securitySearchTab
                .openEquityFullProfile(equityName, equityMarketName)
                .selectTab(UITitles.ProfileTab.DETAILS);
        detailsProfileTab = new EqSecurityDetailsProfileTab();
        detailsProfileTab
                .verifyValue("Buys ($M)", buys);

    }

    @Test
    public void verifyIndustryCardOnEquityProfileOverview() {
        IndustryCard industryCard;
        String macroOnProfileOverview;
        String macroOnFullProfile;

        securityProfileOverview
                .verifyCardPresence(SHARES)
                .verifyCardPresence(INDUSTRY)
                .verifyCardPresence(PRICE_HISTORY);

        industryCard = new IndustryCard();
        macroOnProfileOverview = industryCard.getMacroIndustryLabel();
        eqSecurityFullProfile = securitySearchTab
                .openEquityFullProfile(equityName, equityMarketName);

        macroOnFullProfile = eqSecurityFullProfile.getMarketCap("Macro");

        Assert.assertEquals(macroOnProfileOverview, macroOnFullProfile, "Macro Industry Cap mismatch: ");
    }

    @Test
    public void verifyPriceHistoryCardOnEquityProfileOverview() {
        PriceHistoryCard priceHistoryCard;

        priceHistoryCard = new PriceHistoryCard();
        priceHistoryCard.verifyDiagram();
    }


}
