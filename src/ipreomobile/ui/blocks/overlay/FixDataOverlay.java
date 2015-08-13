package ipreomobile.ui.blocks.overlay;

import ipreomobile.core.Driver;
import ipreomobile.core.SenchaWebElement;
import ipreomobile.core.Verify;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.BaseOverlay;
import ipreomobile.ui.UITitles;
import org.openqa.selenium.By;
import org.testng.Assert;

public class FixDataOverlay extends BaseOverlay {

    private static final By THANK_YOU_FIELD_LOCATOR  = By.xpath(new XPathBuilder().byClassName("thankyou").build());
    private static final String THANK_YOU_FIELD_TEXT = "As always, we appreciate your help! Thank you.";

    private static final By WHAT_NEEDS_TO_BE_FIXED_FIELD_LOCATOR  = By.xpath(new XPathBuilder().byClassName("x-input-text").build());
    private static final String WHAT_NEEDS_TO_BE_FIXED_FIELD_TEXT = "What Needs to Be Fixed?";

    private static final String KEY_XPATH = new XPathBuilder()
            .byCurrentItem()
            .byTag("div").withClassName("group")
            .byTag("div").withClassName("label").withText("%s").build();
    private static final String VALUE_XPATH = new XPathBuilder()
            .byCurrentItem()
            .byTag("div").withClassName("group")
            .withChildTag("div").withClassName("label").withText("%s")
            .byTag("div").withClassName("value").build();

    private static final By CANCEL_LOCATOR = By.xpath(new XPathBuilder()
            .byCurrentItem()
            .byText("Cancel").build());
    private static final By SUBMIT_LOCATOR = By.xpath(new XPathBuilder()
            .byCurrentItem()
            .byClassName("x-button")
            .withChildText("Submit").build());


    private SenchaWebElement container;

    public FixDataOverlay() {
        super(UITitles.OverlayType.FIX_DATA);
        container = Driver.findVisible(By.className("fixdata-overlay"));
    }

    public FixDataOverlay verifyField(String fieldName, String fieldValue) {
        String keyXpath = String.format(KEY_XPATH, fieldName);
        SenchaWebElement key = Driver.findVisible(By.xpath(keyXpath), container);
        Assert.assertNotNull(key, "Field '" + fieldName + "' was not found in Fix Data form.");

        String valueXpath = String.format(VALUE_XPATH, fieldName);
        SenchaWebElement value = Driver.findVisible(By.xpath(valueXpath), container);
        Assert.assertNotNull(value, "Value for field '" + fieldName + "' was not found in Fix Data form.");
        Assert.assertEquals(value.getText().trim(), fieldValue);
        return this;
    }

    public FixDataOverlay verifyThankYouField(){
        String text = Driver.findVisible(THANK_YOU_FIELD_LOCATOR, container).getText().trim();
        Assert.assertNotNull(text, "Message for Thank You field was not found in Fix Data form.");
        Assert.assertEquals(text, THANK_YOU_FIELD_TEXT, "Thank You field has wrong message displayed. Actual : " +
                text + ". Expected : " + THANK_YOU_FIELD_TEXT);
        return this;
    }

    public FixDataOverlay verifyWhatNeedsToBeFixedField(){
        SenchaWebElement textField = Driver.findVisible(WHAT_NEEDS_TO_BE_FIXED_FIELD_LOCATOR, container);
        Assert.assertNotNull(textField, "What Needs To Be Fixed field was not found in Fix Data form.");
        String innerText = textField.getAttribute("placeholder");
        Assert.assertNotNull(innerText, "Message for What Needs To Be Fixed field was not found in Fix Data form.");
        Assert.assertEquals(innerText, WHAT_NEEDS_TO_BE_FIXED_FIELD_TEXT, "What Needs To Be Fixed field has wrong message displayed." +
                "Actual: " + innerText+ ". Expected : " + WHAT_NEEDS_TO_BE_FIXED_FIELD_TEXT);
        verifySubmitButtonDisabled();
        textField.sendKeys("test text");
        verifySubmitButtonEnabled();
        textField.clear();
        verifySubmitButtonDisabled();
        return this;
    }

    public void cancel(){
        By maskLocator = getActiveMaskLocator();
        Driver.findVisible(CANCEL_LOCATOR, container).click();
        waitMaskHidden(maskLocator);
    }

    public void submit(){
        By maskLocator = getActiveMaskLocator();
        Driver.findVisible(SUBMIT_LOCATOR, container).click();
        waitMaskHidden(maskLocator);
    }

    private FixDataOverlay verifySubmitButtonDisabled(){
        boolean isDisabled = Driver.findVisible(SUBMIT_LOCATOR, container).getAttribute("class").contains("x-item-disabled");
        Verify.verifyTrue(isDisabled, "Submit button has to be disabled, because no text was entered into the text field: ");
        return this;
    }

    private FixDataOverlay verifySubmitButtonEnabled(){
        boolean isDisabled = Driver.findVisible(SUBMIT_LOCATOR, container).getAttribute("class").contains("x-item-disabled");
        Verify.verifyFalse(isDisabled, "Submit button has to be enabled, because some text was entered into the text field: ");
        return this;
    }

}
