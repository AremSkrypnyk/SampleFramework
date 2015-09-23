package ipreomobile.test.profiles.institution;

import ipreomobile.data.ContactData;
import ipreomobile.data.InstitutionData;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.institutions.InstitutionProfileOverview;
import ipreomobile.ui.institutions.InstitutionSearchTab;
import ipreomobile.ui.profiles.overviewCards.LastActionsCard;
import ipreomobile.ui.profiles.overviewCards.NextActionCard;
import ipreomobile.ui.profiles.overviewCards.PositionHistoryCard;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static ipreomobile.ui.UITitles.ProfileCardTitle.*;

public class InstitutionProfileOverviewSmoke extends BaseTest {
    private InstitutionSearchTab institutionSearchTab;
    private InstitutionProfileOverview institutionProfileOverview;

    private InstitutionData institutionData;
    private String institutionName;

    @BeforeMethod
    public void setupTestData(Method testMethod){
        institutionData = new InstitutionData();
        institutionData.setTestCaseName(testMethod.getName());
        institutionName = institutionData.getName();

        institutionSearchTab = navigation.searchInstitutions(institutionName);
        institutionSearchTab.openProfileOverview(institutionName);
        institutionProfileOverview = institutionSearchTab.getActiveProfileOverview();
    }

    @Test
    public void verifyInstitutionProfileOverviewForInvestor() {
        String cityName = institutionSearchTab.getInstitutionCity(institutionName);

        institutionProfileOverview
                .verifyInstitutionName(institutionName)
                .verifyInstitutionType();

        institutionProfileOverview
                .openPrimaryContactsOverlay()
                .close();

        institutionProfileOverview
                .openProfileInfoOverlay()
                .verifyAddressPart(cityName)
                .verifyPhone(institutionData.getPhone())
                .close();
                //.openMap()
                //.done();

        institutionProfileOverview
                .addActivity()
                .verifyExternalParticipantPresent(institutionName)
                .closeActivityDialog();

        institutionProfileOverview
                .verifyCardPresence(INSTITUTIONAL_HOLDINGS)
                .verifyCardPresence(NEXT_ACTION)
                .verifyCardPresence(LAST_ACTIONS)
                .verifyCardPresence(POSITION_HISTORY)
                .verifyCardAbsence(NON_OWNER_INSTITUTIONAL_HOLDINGS);

        PositionHistoryCard positionHistoryCard = new PositionHistoryCard();
        positionHistoryCard.verifyHistoryDetailsChart();
    }

    @Test
    public void verifyInstitutionProfileOverviewForCompanyWithoutHoldings(){
        institutionName = "Qatar Investment Authority";
        String cityName;

        ContactData contactData = new ContactData();
        contactData.setTestCaseName("verifyDetailsTabOnInstitutionFullProfile");

        navigation
                .searchInstitutions(institutionName)
                .openProfileOverview(institutionName);
        cityName = institutionSearchTab.getInstitutionCity(institutionName);
        institutionProfileOverview = institutionSearchTab.getActiveProfileOverview();

        institutionProfileOverview
                .verifyInstitutionName(institutionName)
                .verifyInstitutionType();

        institutionProfileOverview
                .openProfileInfoOverlay()
                .verifyAddressPart(cityName)
                .close();

        institutionProfileOverview
                .addActivity()
                .verifyExternalParticipantPresent(institutionName)
                .closeActivityDialog();

        institutionProfileOverview
                .verifyCardPresence(NON_OWNER_INSTITUTIONAL_HOLDINGS)

                .verifyCardAbsence(INSTITUTIONAL_HOLDINGS);

    }

    @Test
    public void verifyNextActionCardOnInstitutionProfileOverview(){
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
    public void verifyLastActionsCardOnInstitutionProfileOverview(){
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
