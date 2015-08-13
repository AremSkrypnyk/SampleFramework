package ipreomobile.ui.hamburgerItems;

import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.SingleSelectListImpl;
import ipreomobile.ui.ScreenCard;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.settings.*;
import org.openqa.selenium.By;

public class SettingsView extends SingleSelectListImpl {
    private static final String LIST_ITEM_XPATH           = new XPathBuilder().byClassName("settings-navi").byClassName("x-list-item").build();
    private static final String LIST_ITEM_NAME_XPATH      = new XPathBuilder().byClassName("x-innerhtml").build();
    private static final String SELECTED_ITEM_CLASS_NAME  = "x-item-selected";
    private static final By     CONTAINER_LOCATOR         = By.className("settings-navi");

    public SettingsView() {
        setItemsXpath(LIST_ITEM_XPATH);
        setItemNameXpath(LIST_ITEM_NAME_XPATH);
        setSelectedItemClassName(SELECTED_ITEM_CLASS_NAME);
        addCheckpointElement(CONTAINER_LOCATOR);
        waitReady();
    }

    public ScreenCard select(UITitles.SettingsTab tabID) {
        select(UITitles.get(tabID));
        switch (tabID) {
            case GENERAL:
                return new GeneralTab();
            case OFFLINE_MODE:
                return new OfflineModeTab();
            case CHANGE_PASSWORD:
                return new ChangePasswordTab();
            case APPLICATION_FEEDBACK:
                return new ApplicationFeedbackTab();
            case GLOBALIZATION:
                return new GlobalizationTab();
            case TERMS_AND_CONDITIONS:
                return new TermsAndConditionsTab();
            case PRIVACY_POLICY:
                return new PrivacyPolicyTab();
            case CONTACT_US:
                return new ContactUsTab();
            default:
                throw new IllegalArgumentException();
        }
    }

    public GeneralTab openGeneralTab() {
        select("General");
        return new GeneralTab();
    }

    public ChangePasswordTab openChangePasswordTab() {
        select("Change Password");
        return new ChangePasswordTab();
    }

    public ApplicationFeedbackTab openApplicationFeedbackTab() {
        select("Application Feedback");
        return new ApplicationFeedbackTab();
    }

    public GlobalizationTab openGlobalizationTab() {
        select("Globalization");
        return new GlobalizationTab();
    }

    public ContactUsTab openContactUsTab() {
        select("Contact Us");
        return new ContactUsTab();
    }
}