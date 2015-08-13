package ipreomobile.ui.blocks.overlay;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.blocks.Carousel;
import org.openqa.selenium.By;
import org.testng.Assert;

public class FundContactsOverlay extends PrimaryContactsOverlay {
    private static final String CONTAINER_CLASS = "contacts-carousel";
    private String CELL_XPATH = new XPathBuilder()
            .byClassName("x-body")
            .byClassName("row")
            .byTag("div").withClassName("%s")
            .build();

    private static final String NAME_CLASS              = "name";
    private static final String PHONE_CLASS             = "phone";
    private static final String INSTITUTION_NAME_CLASS  = "institutionName";
    private static final String EMAIL_CLASS             = "email";

    private static final String ASSERTION_ERROR = "Found mismatch of %s for contact %s: ";

    private Carousel carousel = new Carousel();

    private static final int PRIMARY_CONTACTS_INDEX = 0;
    private static final int MANAGED_BY_INDEX = 1;
    public FundContactsOverlay(){
        addCheckpointElement(By.className(CONTAINER_CLASS));
        waitReady();

        String containerXpath = new XPathBuilder().byClassName(CONTAINER_CLASS).build();
        carousel.setContainerXpath(containerXpath);
        container = carousel.getActiveCarouselItem();
    }

    public FundContactsOverlay goToPrimaryContacts(){
        carousel.goToPage(PRIMARY_CONTACTS_INDEX);
        container = carousel.getActiveCarouselItem();
        return this;
    }

    public FundContactsOverlay goToManagedBy(){
        carousel.goToPage(MANAGED_BY_INDEX);
        container = carousel.getActiveCarouselItem();
        return this;
    }

    public FundContactsOverlay verifyContactNamePresent(String contactName){
        super.verifyContactNamePresent(contactName);
        return this;
    }

    public String getContactRelatedInstitutionName(String contactName){
        return getContactPropertyField(contactName, INSTITUTION_NAME_CLASS).getText();
    }

    public FundContactsOverlay verifyContactRelatedInstitutionNamePresent(String contactName){
        Assert.assertNotNull(getContactPropertyField(contactName, INSTITUTION_NAME_CLASS),
                "Contact '"+contactName+"' institution was not found in Primary Contacts list.");
        return this;
    }

    public FundContactsOverlay verifyContactRelatedInstitutionName(String contactName, String expectedContactRelatedInstitutionName){
        verifyContactProperty(contactName, INSTITUTION_NAME_CLASS, expectedContactRelatedInstitutionName);
        return this;
    }

    protected void setContainer() {
        container = carousel.getActiveCarouselItem();
    };

}
