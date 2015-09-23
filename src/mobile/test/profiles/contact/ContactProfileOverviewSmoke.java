package ipreomobile.test.profiles.contact;

import ipreomobile.data.ContactData;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.contacts.ContactProfileOverview;
import ipreomobile.ui.contacts.ContactSearchTab;
import ipreomobile.ui.profiles.overviewCards.LastActionsCard;
import ipreomobile.ui.profiles.overviewCards.NextActionCard;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static ipreomobile.ui.UITitles.ProfileCardTitle.*;

public class ContactProfileOverviewSmoke extends BaseTest {

    private ContactSearchTab contactSearchTab;
    private ContactProfileOverview contactProfileOverview;


    private ContactData contactData;
    private String contactName;

    @BeforeMethod
    public void setupTestData(Method testMethod){
        contactData = new ContactData();
        contactData.setTestCaseName(testMethod.getName());
        contactName = contactData.getName();

        contactSearchTab = navigation.searchContacts(contactName);
        contactSearchTab.openProfileOverview(contactName);
        contactProfileOverview = contactSearchTab.getActiveProfileOverview();
    }

    @Test
    public void verifyContactProfileOverview() {
        contactProfileOverview
                .verifyCardPresence(CONTACT_HOLDINGS)
                .verifyCardPresence(INSTITUTIONAL_HOLDINGS)
                .verifyCardPresence(NEXT_ACTION)
                .verifyCardPresence(LAST_ACTIONS)
                .verifyCardPresence(BIOGRAPHY)
                .verifyCardPresence(COVERAGE)

                .verifyCardAbsence(NON_OWNER_INSTITUTIONAL_HOLDINGS)
                .verifyCardAbsence(NON_OWNER_CONTACT_HOLDINGS);

        String companyName = contactSearchTab.getInstitutionName(contactName);
        contactProfileOverview
                .verifyContactName(contactName)
                .verifyCompanyName(companyName)
                .verifyJobFunction(contactData.getJobFunction())

                .openProfileInfoOverlay()
                .verifyAddressPart(contactData.getCityName())
                .verifyPhone(contactData.getPhone())
                .openMap()
                .done();

        contactProfileOverview
                .verifyCardPresence(CONTACT_HOLDINGS)
                .verifyCardPresence(INSTITUTIONAL_HOLDINGS)
                .verifyCardAbsence(NON_OWNER_INSTITUTIONAL_HOLDINGS)
                .verifyCardAbsence(NON_OWNER_CONTACT_HOLDINGS);

    }

    @Test
    public void verifyNextActionCardOnContactProfileOverview() {
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
    public void verifyLastActionsCardOnContactProfileOverview(){
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
