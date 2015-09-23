package ipreomobile.ui.blocks.overlay;

import ipreomobile.core.Driver;
import ipreomobile.core.Logger;
import ipreomobile.core.Verify;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.BaseOverlay;
import ipreomobile.ui.MapView;
import ipreomobile.ui.UITitles;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

public class ProfileInfoOverlay extends BaseOverlay {

    private static final By PHONE_LOCATOR = By.xpath(new XPathBuilder()
            .byClassName("phone")
            .byClassName("value")
            .build()
    );

    private static final By ADDRESS_LOCATOR = By.xpath(new XPathBuilder()
                    .byClassName("address")
                    .byClassName("value")
                    .build()
    );

    private static final By MAP_LOCATOR = By.xpath(new XPathBuilder()
                    .byClassName("profile-info-map")
                    .build()
    );

    private static String fieldXpath = new XPathBuilder()
            .byAttribute("data", "%s")
            .byClassName("value")
            .build();

    public ProfileInfoOverlay(){
        super(UITitles.OverlayType.PROFILE_INFO);
        resetCheckpointElements();

        addCheckpointElement(By.className("profile-info")).mustBeVisible();
        waitReady();
    }

    public String getAddress(){
        By locator = By.xpath(new XPathBuilder().byClassName("address").byClassName("value").build());
       SenchaWebElement element = Driver.findVisibleNow(locator);
        String value = "";
        if (element == null) {
            Logger.logError("No address was found on "+ getScreenName());
        } else {
            value = element.getText();
        }
        return value;
    }

    public String getPhone(){
        return getFieldValue("phone");
    }
    public String getMobilePhone(){
        return getFieldValue("mobile_phone");
    }
    public String getHomePhone(){
        return getFieldValue("home_phone");
    }
    public String getOtherPhone(){
        return getFieldValue("other_phone");
    }
    public String getEmail(){
        return getFieldValue("email");
    }
    public String getSecondaryEmail(){
        return getFieldValue("secondary_email");
    }
    public String getOtherEmail(){
        return getFieldValue("other_email");
    }

    private String getFieldValue(String fieldClass) {
        By locator = By.xpath(String.format(fieldXpath, fieldClass));
       SenchaWebElement element = Driver.findVisible(locator);
        String value = "";
        if (element == null) {
            Logger.logError("No "+fieldClass+" was found on "+ getScreenName());
        } else {
            value = element.getText();
        }
        return value;
    }

    public ProfileInfoOverlay verifyPhone(String expectedPhone){
        Verify.verifyEquals(getPhone(), expectedPhone, "Phone number mismatch on "+ getScreenName() +": ");
        return this;
    }
    public ProfileInfoOverlay verifyAddress(String expectedAddress){
        Verify.verifyEquals(getAddress(), expectedAddress, "Address mismatch on "+ getScreenName() +": ");
        return this;
    }
    public ProfileInfoOverlay verifyAddressPart(String addressPart){
        Verify.verifyContainsText(getAddress(), addressPart, "Expected address part was not found on "+ getScreenName() + "");
        return this;
    }
    public ProfileInfoOverlay verifyMobilePhone(String expectedPhone){
        Verify.verifyEquals(getMobilePhone(), expectedPhone, "Mobile phone number mismatch on "+ getScreenName() +": ");
        return this;
    }
    public ProfileInfoOverlay verifyHomePhone(String expectedPhone){
        Verify.verifyEquals(getHomePhone(), expectedPhone, "Home phone number mismatch on "+ getScreenName() +": ");
        return this;
    }
    public ProfileInfoOverlay verifyOtherPhone(String expectedPhone){
        Verify.verifyEquals(getOtherPhone(), expectedPhone, "Other phone number mismatch on "+ getScreenName() +": ");
        return this;
    }
    public ProfileInfoOverlay verifyEmail(String expectedEmail){
        Verify.verifyEquals(getEmail(), expectedEmail, "E-mail address mismatch on "+ getScreenName() +": ");
        return this;
    }
    public ProfileInfoOverlay verifySecondaryEmail(String expectedEmail){
        Verify.verifyEquals(getSecondaryEmail(), expectedEmail, "Secondary e-mail address mismatch on "+ getScreenName() +": ");
        return this;
    }
    public ProfileInfoOverlay verifyOtherEmail(String expectedEmail){
        Verify.verifyEquals(getOtherEmail(), expectedEmail, "Other e-mail address mismatch on "+ getScreenName() +": ");
        return this;
    }
    public MapView openMap(){
       SenchaWebElement address = Driver.findVisible(ADDRESS_LOCATOR);
        Assert.assertNotNull(address, "No map was found on "+getScreenName()+".");
        address.click();
        return new MapView();
    }
}
