package ipreomobile.ui.blocks;

import ipreomobile.core.Driver;
import ipreomobile.core.ElementHelper;
import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.ScreenCard;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

public class EyeButton {
    private static final boolean SURVEILLANCE_DATA_STATE = true;
    private static final boolean PUBLIC_DATA_STATE       = false;
    private static final By      EYE_BUTTON_LOCATOR      = By.xpath(new XPathBuilder().byClassName("x-button-normal").withChildTag("span").withClassName("eye").build());
    private static final String  EYE_PRESSED_CLASSNAME   = "pressed";
    private SenchaWebElement eyeButton;

    public void showSurveillanceData() {
        toggleEyeButtonState(SURVEILLANCE_DATA_STATE);
    }

    public void showPublicData(){
        toggleEyeButtonState(PUBLIC_DATA_STATE);
    }

    public void verifySurveillanceDataShown(){
        Assert.assertEquals(getEyeButtonState(), SURVEILLANCE_DATA_STATE, "Expected to find 'Surveillance Data' state, but 'Public Data' found instead.");
    }

    public void verifyPublicDataShown(){
        Assert.assertEquals(getEyeButtonState(), PUBLIC_DATA_STATE, "Expected to find 'Public Data' state, but 'Surveillance Data' found instead.");
    }

    private void toggleEyeButtonState(boolean state) {
        eyeButton = Driver.findVisible(EYE_BUTTON_LOCATOR);
        if (eyeButton.hasClass(EYE_PRESSED_CLASSNAME) != state) {
            eyeButton.click();
            ScreenCard eyeCard = new ScreenCard();
            eyeCard.addCheckpointElement(EYE_BUTTON_LOCATOR).addClassCondition(EYE_PRESSED_CLASSNAME, state);
            eyeCard.waitReady();
        }
    }

    private boolean getEyeButtonState(){
        eyeButton = Driver.findVisible(EYE_BUTTON_LOCATOR);
        return eyeButton.hasClass(EYE_PRESSED_CLASSNAME);
    }
}
