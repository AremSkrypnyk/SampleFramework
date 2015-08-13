package ipreomobile.ui.blocks.overlay;

import ipreomobile.core.*;
import ipreomobile.templates.ui.BaseOverlay;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.blocks.ConfirmationDialog;
import ipreomobile.ui.contacts.ContactFullProfile;
import org.openqa.selenium.By;
import org.testng.Assert;

import java.util.List;

public class PrimaryContactsOverlay extends BaseOverlay {

    private static final String NAME_CLASS = "name";
    private static final String JOB_FUNCTION_CLASS = "function";
    private static final String EMAIL_CLASS = "email";
    private static final String PHONE_CLASS = "phone";
    private static final String ASSERTION_ERROR = "Found mismatch of %s for contact %s: ";
    private static final By CONTAINER_LOCATOR = By.className("primary-contacts-overlay");

    private String CELL_XPATH = new XPathBuilder().byCurrentItem()
            .byClassName("row").withChildTag("div").withClassName(NAME_CLASS).withText("%s")
            .byClassName("%s").build();

    protected SenchaWebElement container;

    public PrimaryContactsOverlay() {
        super(UITitles.OverlayType.PRIMARY_CONTACTS);
    }

    public ContactFullProfile goToContactProfileAndVerifyName(String contactName) {
        SenchaWebElement contactLink = getContactPropertyField(contactName, NAME_CLASS);
        Assert.assertNotNull(contactLink, "Contact name '"+contactName+"' was not found in Primary Contacts overlay.");
        contactLink.click();
        ContactFullProfile contactProfile = new ContactFullProfile();
        contactProfile.verifyProfileName(contactName);
        return contactProfile;
    }

    public List<String> getContactNames(){
        container = Driver.findVisible(CONTAINER_LOCATOR);
        String nameXpath = new XPathBuilder().byCurrentItem().byClassName(NAME_CLASS).withNoClassName("header").build();
        List<SenchaWebElement> contactNameElements = Driver.findAll(By.xpath(nameXpath), container);
        return ElementHelper.getTextValues(contactNameElements);
    }

    public PrimaryContactsOverlay verifyContactNamePresent(String contactName){
        container = Driver.findVisible(CONTAINER_LOCATOR);
        Assert.assertNotNull(Driver.findVisible(By.className(NAME_CLASS), container), "Contact '"+contactName+"' was not found in Primary Contacts list.");
        return this;
    }

    public String getContactJobFunction(String contactName){
        return getContactPropertyField(contactName, JOB_FUNCTION_CLASS).getText();
    }

    public PrimaryContactsOverlay verifyContactJobFunction(String contactName, String expectedJobFunction) {
        verifyContactProperty(contactName, JOB_FUNCTION_CLASS, expectedJobFunction);
        return this;
    }

    public String getContactEmail(String contactName){
        return getContactPropertyField(contactName, EMAIL_CLASS).getText();
    }

    public PrimaryContactsOverlay verifyContactEmail(String contactName, String expectedContactEmail){
        String assertionError = String.format(ASSERTION_ERROR, EMAIL_CLASS, contactName);
        SenchaWebElement contactEmailField = getContactPropertyField(contactName, EMAIL_CLASS);
        Assert.assertEquals(contactEmailField.getText(), expectedContactEmail, assertionError);

        contactEmailField.click();
        ConfirmationDialog dialog = new ConfirmationDialog();
        dialog.verify3rdPartyAppMessage();
        return this;
    }


    public String getContactPhone(String contactName){
        return getContactPropertyField(contactName, PHONE_CLASS).getText();
    }

    public PrimaryContactsOverlay verifyContactPhone(String contactName, String expectedContactPhone){
        verifyContactProperty(contactName, PHONE_CLASS, expectedContactPhone);
        return this;
    }

    public PrimaryContactsOverlay verifyContactPhonePresent(String contactName){
        Assert.assertNotNull(getContactPropertyField(contactName, PHONE_CLASS),
                "Contact '"+contactName+"' phone was not found in Primary Contacts list.");
        return this;
    }

    public PrimaryContactsOverlay verifyContactEmailPresent(String contactName){
        Assert.assertNotNull(getContactPropertyField(contactName, EMAIL_CLASS),
                "Contact '"+contactName+"'email  was not found in Primary Contacts list.");
        return this;
    }

    protected void setContainer(){
        container = Driver.findVisible(CONTAINER_LOCATOR);
    }

    protected void verifyContactProperty(String contactName, String fieldClass, String expectedValue) {
        String assertionError = String.format(ASSERTION_ERROR, fieldClass, contactName);
        Assert.assertEquals(getContactPropertyField(contactName, fieldClass), expectedValue, assertionError);
    }

    protected SenchaWebElement getContactPropertyField(String contactName, String fieldClass) {
        setContainer();
        String valueCellXpath = String.format(CELL_XPATH, contactName, fieldClass);
        SenchaWebElement cell = Driver.findFirstNow(By.xpath(valueCellXpath), container);
        if (cell == null) {
            Logger.logError("No " + fieldClass + " was found for contact '" + contactName + "' in Primary Contacts table.");
        }
        return cell;
    }


}

