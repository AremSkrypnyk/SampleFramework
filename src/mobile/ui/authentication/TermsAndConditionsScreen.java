package ipreomobile.ui.authentication;

import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.ScreenCard;
import ipreomobile.ui.VerticalScroller;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;

import ipreomobile.core.Driver;

public class TermsAndConditionsScreen extends ScreenCard {

    private static final String TERMS_CONDITIONS_TEXT = "Terms & Conditions";
    private static final String TERMS_CONDITIONS_XPATH = new XPathBuilder().byTag("*").withText(TERMS_CONDITIONS_TEXT).build();

    private static final String ACCEPT_TERMS_TEXT = "Accept Terms and Conditions";
    private static final String ACCEPT_TERMS_XPATH = new XPathBuilder().byTag("span").withText(ACCEPT_TERMS_TEXT).build();

    private static final String VIEW_POLICY_TEXT = "View Our Privacy Policy";
    private static final String VIEW_POLICY_XPATH = new XPathBuilder().byTag("span").withText(VIEW_POLICY_TEXT).build();

    private static final String ACCEPT_TERMS_TEXT_FOR_PHONES = "Accept";
    private static final String ACCEPT_TERMS_XPATH_FOR_PHONES = new XPathBuilder().byTag("span").withText(ACCEPT_TERMS_TEXT_FOR_PHONES).build();

    private static final String VIEW_POLICY_TEXT_FOR_PHONES  = "Privacy Policy";
    private static final String VIEW_POLICY_XPATH_FOR_PHONES  = new XPathBuilder().byTag("span").withText(VIEW_POLICY_TEXT_FOR_PHONES).build();

    private static final String DONE_TEXT = "Done";
    private static final String DONE_XPATH = new XPathBuilder().byTag("*").withText(DONE_TEXT).build();

    private static final String SCROLLABLE_LIST_ID = "ext-loginTnC-1";

    private VerticalScroller scroller;
    private SenchaWebElement acceptTermsAndConditionsButton;
    private SenchaWebElement viewPrivacyPolicyButton;
    private SenchaWebElement donePrivacyPolicyButton;
    private String testBrowser = System.getProperty("test.browser");

    public TermsAndConditionsScreen() {
        scroller = new VerticalScroller(SCROLLABLE_LIST_ID);
        addCheckpointElement(By.cssSelector("#ext-loginTnC-1")).mustBeVisible();
        addCheckpointElement(By.xpath(TERMS_CONDITIONS_XPATH)).mustBeVisible();
        waitReady();
    }

    public void tapAcceptButton() {
        By acceptLocator;

        switch (System.getProperty("test.browser")) {
            case "ipad_simulator":
            case "ipad":
            case "android_tablet":
            case "chrome":
                acceptLocator = By.xpath(ACCEPT_TERMS_XPATH);
                break;
            case "iphone_simulator":
            case "android_phone":
            case "iphone":
                acceptLocator = By.xpath(ACCEPT_TERMS_XPATH_FOR_PHONES);
                break;
            default:
                throw new AssertionError("Unsupported browser: " + System.getProperty("test.browser"));
        }


        scroller.scrollToEnd();
        waitPageBottomReached();
        acceptTermsAndConditionsButton = Driver.findVisible(acceptLocator);
        acceptTermsAndConditionsButton.click();
    }

    public TermsAndConditionsScreen viewPrivacyPolicy() {
        By privacyPolicyLocator;
        By doneLocator;
        switch (System.getProperty("test.browser")){
            case "ipad_simulator":
            case "ipad":
            case "android_tablet":
            case "chrome":
                privacyPolicyLocator = By.xpath(VIEW_POLICY_XPATH);
                break;
            case "iphone_simulator":
            case "android_phone":
            case "iphone":
                privacyPolicyLocator = By.xpath(VIEW_POLICY_XPATH_FOR_PHONES);
                break;
            default:
                throw new AssertionError("Unsupported browser: " + System.getProperty("test.browser"));
        }
        doneLocator = By.xpath(DONE_XPATH);

        scroller.scrollToEnd();
        waitPageBottomReached();
        viewPrivacyPolicyButton = Driver.findOne(privacyPolicyLocator);
        viewPrivacyPolicyButton.click();

        donePrivacyPolicyButton = Driver.findOne(doneLocator);
        donePrivacyPolicyButton.click();
        return this;
    }

    private void waitPageBottomReached() {
        if (testBrowser.equalsIgnoreCase("chrome")) {
            resetCheckpointElements();
            addCheckpointElement(By.xpath(ACCEPT_TERMS_XPATH)).mustBeVisible();
            addCheckpointElement(By.xpath(VIEW_POLICY_XPATH)).mustBeVisible();
            waitReady();
        }
    }
}
