//package ipreomobile.ui.blocks.overlay;
//
//import ipreomobile.core.Driver;
//import ipreomobile.core.Logger;
//import ipreomobile.core.XPathBuilder;
//import ipreomobile.templates.ui.BaseOverlay;
//import ipreomobile.ui.UITitles;
//import ipreomobile.ui.contacts.ContactFullProfile;
//import org.openqa.selenium.By;
//import ipreomobile.core.SenchaWebElement;
//import org.testng.Assert;
//
//public class ManagedByOverlay extends BaseOverlay {
//
//    private static final By CONTAINER_LOCATOR = By.className("managed-by-overlay");
//    private String CELL_XPATH = new XPathBuilder()
//            .byClassName("x-body")
//            .byClassName("row")
//            .byTag("div").withClassName("%s")
//            .build();
//
//    private static final String ASSERTION_ERROR = "Found mismatch of %s for contact %s: ";
//
//    private static final String NAME_CLASS              = "name";
//    private static final String PHONE_CLASS             = "phone";
//    private static final String INSTITUTION_NAME_CLASS  = "institutionName";
//    private static final String EMAIL_CLASS             = "email";
//
//    private SenchaWebElement container;
//
//    public ManagedByOverlay() {
//        super(UITitles.OverlayType.MANAGED_BY);
//    }
//
//    public String getContactNames(){
//        container = Driver.findVisible(CONTAINER_LOCATOR);
//        String nameXpath = new XPathBuilder().byCurrentItem().byClassName(NAME_CLASS).withNoClassName("header").build();
//       SenchaWebElement contactNameElement = Driver.findVisible(By.xpath(nameXpath), container);
//        return contactNameElement.getText().trim();
//    }
//
//    public ManagedByOverlay verifyContactName(String contactName){
//        container = Driver.findVisible(CONTAINER_LOCATOR);
//        Assert.assertNotNull(Driver.findVisible(By.className(NAME_CLASS), container), "Contact '"+contactName+"' was not found in Primary Contacts list.");
//        return this;
//    }
//
//    public ContactFullProfile goToContactProfile(String contactName) {
//       SenchaWebElement contactLink = getContactPropertyField(contactName, NAME_CLASS);
//        Assert.assertNotNull(contactLink, "Contact name '"+contactName+"' was not found in Primary Contacts overlay.");
//        contactLink.click();
//        ContactFullProfile contactProfile = new ContactFullProfile();
//        contactProfile.verifyProfileName(contactName);
//        return contactProfile;
//    }
//
//    private SenchaWebElement getContactPropertyField(String contactName, String fieldClass) {
//       SenchaWebElement container = Driver.findVisible(CONTAINER_LOCATOR);
//        String valueCellXpath = String.format(CELL_XPATH, fieldClass);
//       SenchaWebElement cell = Driver.findVisibleNow(By.xpath(valueCellXpath), container);
//        if (cell == null) {
//            Logger.logError("No " + fieldClass + " was found for contact '" + contactName + "' in Primary Contacts table.");
//        }
//        return cell;
//    }
//
//    public String getContactPhone(String contactName){
//        return getContactPropertyField(contactName, PHONE_CLASS).getText();
//    }
//
//    public ManagedByOverlay verifyContactPhone(String contactName){
//        container = Driver.findVisibleNow(CONTAINER_LOCATOR);
//        Assert.assertNotNull(Driver.findVisible(By.className(PHONE_CLASS), container), "Contact '"+contactName+"' was not found in Primary Contacts list.");
//        return this;
//    }
//
//    public ManagedByOverlay verifyContactPhone(String contactName, String expectedContactPhone){
//        verifyContactProperty(contactName, PHONE_CLASS, expectedContactPhone);
//        return this;
//    }
//
//    public String getContactEmail(String contactName){
//        return getContactPropertyField(contactName, EMAIL_CLASS).getText();
//    }
//
//    public ManagedByOverlay verifyContactEmail(String contactName){
//        container = Driver.findVisibleNow(CONTAINER_LOCATOR);
//        Assert.assertNotNull(Driver.findVisible(By.className(EMAIL_CLASS), container), "Contact '"+contactName+"' was not found in Primary Contacts list.");
//        return this;
//    }
//
//    public ManagedByOverlay verifyContactEmail(String contactName, String expectedContactEmail){
//        verifyContactProperty(contactName, EMAIL_CLASS, expectedContactEmail);
//        return this;
//    }
//
//    public String getContactRelatedInstitutionName(String contactName){
//        return getContactPropertyField(contactName, INSTITUTION_NAME_CLASS).getText();
//    }
//
//    public ManagedByOverlay verifyContactRelatedInstitutionName(String contactName){
//        container = Driver.findVisible(CONTAINER_LOCATOR);
//        Assert.assertNotNull(Driver.findVisible(By.className(INSTITUTION_NAME_CLASS), container), "Contact '"+contactName+"' was not found in Primary Contacts list.");
//        return this;
//    }
//
//    public ManagedByOverlay verifyContactRelatedInstitutionName(String contactName, String expectedContactRelatedInstitutionName){
//        verifyContactProperty(contactName, INSTITUTION_NAME_CLASS, expectedContactRelatedInstitutionName);
//        return this;
//    }
//
//    private void verifyContactProperty(String contactName, String fieldClass, String expectedValue) {
//        String assertionError = String.format(ASSERTION_ERROR, fieldClass, contactName);
//        Assert.assertEquals(getContactPropertyField(contactName, fieldClass).getText().trim(), expectedValue, assertionError);
//    }
//
//}
