package ipreomobile.ui.settings;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.GlobalNavigation;
import ipreomobile.ui.ScreenCard;
import org.openqa.selenium.By;

public class ContactUsTab extends ScreenCard {
    private static final By FORM_FIELDSET_LOCATOR = By.xpath(new XPathBuilder().byClassName("grouped-input").withChildTextContains("Global Offices").build());
    private static final String ITEM_TEMPLATE = new XPathBuilder().byClassName("grouped-input").byClassName("x-button").withChildTextContains("%s").build();
    private static final By ADDRESS_CARD_LOCATOR = By.className("adress");
    private static final By CONTACTS_TABLE_LOCATOR = By.className("contacts-table");
    private GlobalNavigation navigation = new GlobalNavigation();

    public ContactUsTab() {
        addCheckpointElement(FORM_FIELDSET_LOCATOR);
        waitReady();
    }

    public ContactUsTab verifyAddressCard(String[] addresses) {
        select("Global Offices");
        for (String address : addresses) {
            Driver.verifyTextPartPresentAndVisible(address, Driver.findVisible(ADDRESS_CARD_LOCATOR));
        }
        navigation.back();
        return new ContactUsTab();
    }

    public ContactUsTab verifyContactsInTheAmericasTab(String[] contacts) {
        select("The Americas");
        for (String contact : contacts) {
            Driver.verifyTextPartPresentAndVisible(contact);
        }
        navigation.back();
        return new ContactUsTab();
    }

    public ContactUsTab verifyContactsInEuropeAndMENATab(String[] contacts) {
        select("Europe & MENA");
        for (String contact : contacts) {
            Driver.verifyTextPartPresentAndVisible(contact);
        }
        navigation.back();
        return new ContactUsTab();
    }

    public ContactUsTab verifyContactsInAsiaPacificTab(String[] contacts) {
        select("Asia-Pacific");
        for (String contact : contacts) {
            Driver.verifyTextPartPresentAndVisible(contact);
        }
        navigation.back();
        return new ContactUsTab();
    }

    private void select(String itemName) {
        Driver.findVisible(By.xpath(String.format(ITEM_TEMPLATE, itemName))).click();
    }
}
