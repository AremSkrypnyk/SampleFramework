package ipreomobile.test.profiles.contact;

import ipreomobile.data.ActivityData;
import ipreomobile.data.ContactData;
import ipreomobile.data.FundData;
import ipreomobile.data.TaskData;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.contacts.ContactFullProfile;
import ipreomobile.ui.contacts.ContactSearchTab;
import ipreomobile.ui.profiles.fullProfileTabs.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContactFullProfileSmoke extends BaseTest {

    private ContactFullProfile contactFullProfile;

    private ContactData contactData;
    private String contactName;
    private String defaultTicker;

    private ContactSearchTab searchTab;
    private ContactDetailsProfileTab contactDetailsProfileTab;
    private ContactOwnershipProfileTab contactOwnershipProfileTab;
    private ContactFocusProfileTab contactFocusProfileTab;
    private ContactActionsProfileTab contactActionsProfileTab;
    private ContactFundsProfileTab contactFundsProfileTab;
    private ContactAdditionalInfoProfileTab contactAdditionalInfoProfileTab;

    @BeforeMethod
    public void setupTestData(Method testMethod){
        contactData = new ContactData();
        contactData.setTestCaseName(testMethod.getName());
        contactName = contactData.getName();
        contactFullProfile = navigation.openContactProfile(contactName);
        defaultTicker = System.getProperty("test.defaultTicker");
    }

    @Test
    public void openFullProfileFromSearchResults() {
        contactFullProfile
                .verifyTabsRowExists()
                .verifyActiveTab("Details")
                .verifyProfileName(contactName);

        navigation
                .verifyPageTitle("Contact Profile")
                .verifyBackButtonPresent()
                .back();

        searchTab = new ContactSearchTab();
        searchTab
                .verifyItemPresentInList(contactName)
                .openFullProfile(contactName);

        contactFullProfile
                .verifyTabsRowExists()
                .verifyActiveTab("Details")
                .verifyProfileName(contactName);

        navigation
                .verifyPageTitle("Contact Profile")
                .verifyBackButtonPresent();
    }

    @Test
    public void verifyControlsOnContactFullProfile(){
        String companyName = contactData.getCompanyName();
        String contactSide = contactData.getSide();
        String jobFunction = contactData.getJobFunction();
        String city = contactData.getCityName();
        String customerName = System.getProperty("test.customer");

        boolean isActive = true,
                isSubscribed = true,
                isInCrm = true;

        contactFullProfile
                .verifyActiveAndSubscribedBadge(isActive, isSubscribed)
                .verifyCrmBadge(isInCrm);

        contactFullProfile
                .verifyBdVersusCrmInfo()
                .verifySide(contactSide)
                .verifyJobFunction(jobFunction)

                .verifyInstitutionName(companyName)
                .goToInstitutionProfile()
                .verifyProfileName(companyName);
        navigation.back();

        contactFullProfile
                .openMapPreview()
                .verifyTitle("Contact Address")
                .verifyAddressPart(city)

                .openMap()
                .verifyPageTitle("Map")
                //.verifyLocationName(contactName)
                .close();

        contactFullProfile
                .verifyEmailPreviewOverlay();

        contactFullProfile
                .addActivity()
                .verifyExternalParticipantPresent(contactName)
                .closeActivityDialog();

        contactFullProfile
                .openFixDataOverlay()
                .verifyField("Customer:", customerName)
                .verifyField("Contact Name:", contactData.getFullName())
                .verifyField("Institution Name:", companyName)
                .verifyWhatNeedsToBeFixedField()
                .verifyThankYouField()
                .cancel();

        contactFullProfile
                .openLastActionOverlay()
                .editActivity()
                .verifyExternalParticipantPresent(contactName)
                .closeActivityDialog();
    }

    @Test
    public void verifyDetailsTabOnContactFullProfile() {
        contactDetailsProfileTab = new ContactDetailsProfileTab();
        contactDetailsProfileTab
                .verifyLabelPresent("Education History")
                .verifyLabelPresent("Biography");
    }

    @Test
    public void verifyOwnershipTabOnContactFullProfile() {
        contactFullProfile.selectTab(UITitles.ProfileTab.OWNERSHIP);
        contactOwnershipProfileTab = new ContactOwnershipProfileTab();
        contactOwnershipProfileTab
                .verifyValuePresent("Current Position in " + defaultTicker + " - Value / Change")
                .verifyCurrencyInTableHeader();
//                .verifyValuePresent("Current Position in " + defaultTicker + " - Shares / Change")
//                .verifyValuePresent("% Portfolio")
//                .verifyValuePresent("% of Shares Outstanding")
//                .verifyValuePresent("Industry Value / Change")
//                .verifyValuePresent("Peer Value / Change");
    }

    @Test
    public void verifyFocusTabOnContactFullProfile() {
        List<String> equityFocusTableLabels = new ArrayList<>(Arrays.asList(
                "Country",
                "Style",
                "Industry Mid",
                "Industry Macro",
                "Strategy",
                "EQ Instrument",
                "Market Cap",
                "Orientation"));
        List<String> fixedIncomeFocusTableLabels = new ArrayList<>(Arrays.asList(
                "FI Instrument",
                "Country",
                "Industry Mid",
                "Industry Macro",
                "Currency",
                "SP Rating Value",
                "Moodys Rating Value",
                "Orientation"));

        contactFullProfile.selectTab(UITitles.ProfileTab.FOCUS);
        contactFocusProfileTab = new ContactFocusProfileTab();
        contactFocusProfileTab
                .verifyActiveTab("Equity")
                .verifyTabPresent("Fixed Income");
        contactFocusProfileTab
                .verifyAllFocusTableLabelsPresent(equityFocusTableLabels);

        contactFocusProfileTab
                .selectFixedIncomeFocusTab()
                .verifyAllFocusTableLabelsPresent(fixedIncomeFocusTableLabels);
    }

    @Test
    public void verifyActionsTabOnContactFullProfile(){
        String recentActivitySubject, recentTaskSubject;
        String activitySubject = new ActivityData().getSubject();
        String taskSubject = new TaskData().getSubject();

        contactFullProfile.selectTab(UITitles.ProfileTab.ACTIONS);
        contactActionsProfileTab = new ContactActionsProfileTab();

        contactActionsProfileTab
                .verifyActiveTab("Recent");
        contactActionsProfileTab
                .verifyActionsSummaryChartPresent()
                .verifyRecentActionsBlockPresent();

        recentActivitySubject = contactActionsProfileTab.getRecentActivityData().getSubject();
        contactActionsProfileTab
                .openRecentActivity()
                .verifySubject(recentActivitySubject)

                .editActivity()
                .verifyExternalParticipantPresent(contactName)
                .closeActivityDialog();
        contactActionsProfileTab
                .verifyActiveTab("Recent");

        recentTaskSubject = contactActionsProfileTab.getRecentTaskData().getSubject();
        contactActionsProfileTab
                .openRecentTask()
                .verifySubject(recentTaskSubject)

                .editActivity()
                .verifyExternalParticipantPresent(contactName)
                .closeActivityDialog();
        contactActionsProfileTab
                .verifyActiveTab("Recent");

        contactActionsProfileTab
                .selectActivitiesTab()
                .selectActivity(activitySubject)
                .verifySubject(activitySubject)

                .editActivity()
                .verifyExternalParticipantPresent(contactName)
                .closeActivityDialog();
        contactActionsProfileTab
                .verifyActiveTab("Activities");

        contactActionsProfileTab
                .selectTasksTab()
                .selectTask(taskSubject)
                .verifySubject(taskSubject)

                .editActivity()
                .verifyExternalParticipantPresent(contactName)
                .closeActivityDialog();
        contactActionsProfileTab
                .verifyActiveTab("Tasks");
    }

    @Test
    public void verifyFundsTabOnContactFullProfile() {
        String holdersFund, otherFund;
        int numberOfFundsToCheckSortingOrder = 5;

        FundData data = new FundData();
        data.loadDataSetByTag("holders");
        holdersFund = data.getName();

        data.loadDataSetByTag("other");
        otherFund = data.getName();

        contactFullProfile.selectTab(UITitles.ProfileTab.FUNDS);
        contactFundsProfileTab = new ContactFundsProfileTab();

        contactFundsProfileTab
                .verifyActiveTab("Holders")
                .verifyTabPresent("Other");

        contactFundsProfileTab
                .verifyHoldersFundDetailsPresent(holdersFund)
                .verifyCurrencyInHoldersFundDetails(holdersFund)
                .verifyDefaultTickerInHoldersFundDetails(holdersFund)
                .verifyHoldersFundsSortingOrder(numberOfFundsToCheckSortingOrder)

                .openFundProfile(holdersFund)
                .verifyProfileName(holdersFund);
        navigation.back();

        contactFundsProfileTab.waitReady();
        contactFundsProfileTab
                .verifyActiveTab("Holders");

        contactFundsProfileTab
                .selectOtherTab();
        contactFundsProfileTab
                .verifyCurrencyInOtherFundDetails(otherFund)
                .verifyOtherFundsSortingOrder(numberOfFundsToCheckSortingOrder)

                .openFundProfile(otherFund)
                .verifyProfileName(otherFund);
        navigation.back();

        contactFundsProfileTab
                .verifyActiveTab("Other");
    }

    @Test
    public void verifyAdditionalInfoTabOnContactFullProfile() {
        List<String> additionalInfoLabels = new ArrayList<>(Arrays.asList(
                "Home Phone"
        ));
        contactFullProfile.selectTab(UITitles.ProfileTab.ADDITIONAL_INFO);
        contactAdditionalInfoProfileTab = new ContactAdditionalInfoProfileTab();
        contactAdditionalInfoProfileTab
                .verifyAdditionalInfoFieldsPresent(additionalInfoLabels);
    }

}
