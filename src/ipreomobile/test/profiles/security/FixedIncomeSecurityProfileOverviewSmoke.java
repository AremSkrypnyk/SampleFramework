package ipreomobile.test.profiles.security;

import ipreomobile.data.FixedIncomeData;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.profiles.fullProfileTabs.FiSecurityCurrentHoldersProfileTab;
import ipreomobile.ui.profiles.fullProfileTabs.FiSecurityDetailsProfileTab;
import ipreomobile.ui.profiles.overviewCards.SummaryCard;
import ipreomobile.ui.profiles.overviewCards.TopHoldersCard;
import ipreomobile.ui.securities.FiSecurityFullProfile;
import ipreomobile.ui.securities.SecurityProfileOverview;
import ipreomobile.ui.securities.SecuritySearchTab;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static ipreomobile.ui.UITitles.ProfileCardTitle.*;

public class FixedIncomeSecurityProfileOverviewSmoke extends BaseTest {

    private SecuritySearchTab securitySearchTab;
    private SecurityProfileOverview securityProfileOverview;

    private FixedIncomeData fixedIncomeData;
    private String fixedIncomeName;
    private String fixedIncomeIssuerName;

    private FiSecurityDetailsProfileTab detailsProfileTab;
    private FiSecurityFullProfile fiSecurityFullProfile;

    @BeforeMethod
    public void setupTestData(Method testMethod){
        fixedIncomeData = new FixedIncomeData();
        fixedIncomeData.setTestCaseName(testMethod.getName());
        fixedIncomeName = fixedIncomeData.getCouponName();
        fixedIncomeIssuerName = fixedIncomeData.getIssuerName();

        navigation
                .openSecurities()
                .searchSecurities()
                .searchFixedIncomes()
                .setTickerOrSecurityName(fixedIncomeIssuerName)
                .search();
        securitySearchTab = new SecuritySearchTab();
        securitySearchTab.openProfileOverview(fixedIncomeName);
        securityProfileOverview = securitySearchTab.getActiveProfileOverview();

    }

    @Test
    public void verifySummaryCardOnFixedIncomeProfileOverview() {
        SummaryCard summaryCard;

        String maturityDate;
        String coupon;
        String buys;
        String buyIns;
        String sells;
        String sellOuts;
        String holders;
        String totalParHeld;

        securityProfileOverview
                .verifyCardPresence(SUMMARY)
                .verifyCardPresence(TOP_HOLDERS);

        summaryCard = new SummaryCard();

        maturityDate = summaryCard.getMaturityDate();
        coupon = summaryCard.getCouponRate();
        buys = summaryCard.getBuys();
        buyIns = summaryCard.getBuyIns();
        sells = summaryCard.getSells();
        sellOuts = summaryCard.getSellOuts();
        holders = summaryCard.getHolders();
        totalParHeld = summaryCard.getTotalParHeld();

        fiSecurityFullProfile = securitySearchTab
                .openFixedIncomeFullProfile(fixedIncomeName);

        fiSecurityFullProfile
                .verifyMaturityDate(maturityDate)
                //TODO: move '%' sign to verifyCouponRate logic
                .verifyCouponRate(coupon + "%")
                .verifyNumberOfBuyers(buys)
                .verifyNumberOfBuyIns(buyIns)
                .verifyNumberOfSellers(sells)
                .verifyNumberOfSellOuts(sellOuts)
                .verifyNumberOfHolders(holders)
                .verifyTotalParHeld(totalParHeld);

        fiSecurityFullProfile
                .selectTab(UITitles.ProfileTab.DETAILS);
        detailsProfileTab = new FiSecurityDetailsProfileTab();
        detailsProfileTab
                .verifyValue("Maturity", maturityDate);

        detailsProfileTab
                .verifyValue("Coupon", coupon + "%");

    }

    @Test
    public void verifyTopHoldersCardOnFixedIncomeProfileOverview(){
        TopHoldersCard topHoldersCard;
        FiSecurityCurrentHoldersProfileTab holdersProfileTab;

        securityProfileOverview
                .verifyCardPresence(SUMMARY)
                .verifyCardPresence(TOP_HOLDERS);

        topHoldersCard = new TopHoldersCard();

//TODO: implement valid mapping for institutions list in Top Holders
//        String institutionName = topHoldersCard.getInstitutionName(1);
//        String parHeld = topHoldersCard.getParHeld(institutionName);
//        String parChange = topHoldersCard.getParChange(institutionName);
//        String positionDate = topHoldersCard.getPositionDate(institutionName);
//
//        securitySearchTab
//                .openFixedIncomeFullProfile(fixedIncomeName)
//                .selectTab(UITitles.ProfileTab.CURRENT_HOLDERS);
//
//        holdersProfileTab = new FiSecurityCurrentHoldersProfileTab();
//
//        holdersProfileTab
//                .verifyInstitutionParHeld(institutionName, parHeld)
//                .verifyInstitutionParChange(institutionName, parChange)
//                .verifyInstitutionPositionDate(institutionName, positionDate)
        ;
    }

}
