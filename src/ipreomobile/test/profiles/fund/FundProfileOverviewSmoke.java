package ipreomobile.test.profiles.fund;

import ipreomobile.core.CollectionHelper;
import ipreomobile.data.ActivityData;
import ipreomobile.data.FundData;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.activities.ActivityDetailsOverview;
import ipreomobile.ui.funds.FundProfileOverview;
import ipreomobile.ui.funds.FundSearchTab;
import ipreomobile.ui.profiles.overviewCards.FundHoldingsCard;
import ipreomobile.ui.profiles.overviewCards.LastActionsCard;
import ipreomobile.ui.profiles.overviewCards.NextActionCard;
import ipreomobile.ui.profiles.overviewCards.NonOwnerFundHoldingsCard;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static ipreomobile.ui.UITitles.ProfileCardTitle.*;

public class FundProfileOverviewSmoke extends BaseTest {

    private FundSearchTab fundSearchTab;
    private FundProfileOverview fundProfileOverview;

    private FundData fundData;
    private String fundName;

    @BeforeMethod
    public void setupTestData(Method testMethod){
        fundData = new FundData();
        fundData.setTestCaseName(testMethod.getName());
        fundName = fundData.getName();

        fundSearchTab = navigation.searchFunds(fundName);
        fundSearchTab.openProfileOverview(fundName);
        fundProfileOverview = fundSearchTab.getActiveProfileOverview();
    }

    @Test
    public void verifyFundProfileOverview(){
        fundProfileOverview
                .verifyInstitutionName(fundData.getInstitutionName());

        fundProfileOverview
                .openFundContactsOverlay()
                .goToManagedBy()
                .verifyContactNamePresent(fundData.getManagedBy())
                .close();

        fundProfileOverview
                .openProfileInfoOverlay()
                .verifyAddressPart(fundData.getCityName())
                .verifyPhone(fundData.getPhone())
                .openMap()
                .done();

        fundProfileOverview
                .addActivity()
                .verifyExternalParticipantPresent(fundName)
                .closeActivityDialog();

        fundProfileOverview
                .verifyCardPresence(FUND_HOLDINGS)
                .verifyCardPresence(NEXT_ACTION)
                .verifyCardPresence(LAST_ACTIONS)
                .verifyCardPresence(POSITION_HISTORY)

                .verifyCardAbsence(NON_OWNER_FUND_HOLDINGS);
    }

    @Test
    public void verifyFundHoldingsCardOnFundProfileOverview(){
        String[] labels = {
                "Position",
                "Change",
                "S/O",
                "Style",
                "Type",
                "Turnover",
                "Equity Assets ("+System.getProperty("test.currency")+")",
                "Purchasing Power ("+System.getProperty("test.currency")+")"
        };
        FundHoldingsCard fundHoldingsCard = new FundHoldingsCard();
        fundHoldingsCard.verifyLabelsPresent(CollectionHelper.toList(labels));
    }

    //@Test
    //TODO: find fund with non-owner fund holdings
    public void verifyNonOwnerFundHoldingsCardOnFundProfileOverview(){
        String[] labels = {
                "Style",
                "Type",
                "Turnover",
                "Equity Assets ("+System.getProperty("test.currency")+")",
                "Purchasing Power ("+System.getProperty("test.currency")+")"
        };
        NonOwnerFundHoldingsCard fundHoldingsCard = new NonOwnerFundHoldingsCard();
        fundHoldingsCard.verifyLabelsPresent(CollectionHelper.toList(labels));
    }

    @Test
    public void verifyNextActionCardOnFundProfileOverview(){
        String subject, type, date;

        NextActionCard nextActionCard = new NextActionCard();
        nextActionCard
                .verifySubjectPresent()
                .verifyDatePresent()
                .verifyTypePresent()
                .verifyTimeSlotPresent();

        subject = nextActionCard.getSubject();
        type = nextActionCard.getType();
        date = nextActionCard.getDate();

        nextActionCard
                .openActivityDetails()
                .openActivityDetailsCard()
                .verifyActiveCardHeader("Activity Details")
                .verifySubject(subject)
                .verifyType(type)
                .verifyDate(date);
        nextActionCard.closeActiveOverlay();
    }

    @Test
    public void verifyLastActionsCardOnFundProfileOverview(){
        String subject, type;
        int actionsNumber;

        LastActionsCard lastActionsCard = new LastActionsCard();
        actionsNumber = lastActionsCard.getNumberOfActions();

        for (int i=1; i<= actionsNumber; i++) {
            subject = lastActionsCard.getSubjectByIndex(i);
            type = lastActionsCard.getType(subject);

            lastActionsCard
                    .openActivityDetails(subject)
                    .verifyActiveCardHeader("Notes")
                    .openActivityDetailsCard()
                    .verifySubject(subject)
                    .verifyType(type);
            lastActionsCard.closeActiveOverlay();
        }
    }

}
