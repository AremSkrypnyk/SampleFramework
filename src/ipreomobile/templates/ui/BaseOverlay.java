package ipreomobile.templates.ui;

import ipreomobile.core.Driver;
import ipreomobile.core.SenchaWebElement;
import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.ScreenCard;
import ipreomobile.ui.UITitles;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

public class BaseOverlay extends ScreenCard implements OverlayController {
    private static final String OVERLAY_CONTAINER_CLASS = "x-panel-b1";
    private static final String OVERLAY_TITLE_XPATH     = new XPathBuilder().byClassName("x-title").byClassName("x-innerhtml").withText("%s").build();
    private static final String MASK_CLASS = "x-mask";

    private UITitles.OverlayType type;

    public BaseOverlay(UITitles.OverlayType type) {
        this.type = type;
        addCheckpointElement(By.xpath(String.format(OVERLAY_TITLE_XPATH, UITitles.get(type)))).mustBeVisible();
    }

    //Main Container
    public static SenchaWebElement getActiveOverlay(){
        return Driver.findVisible(By.className(OVERLAY_CONTAINER_CLASS));
    }

    public UITitles.OverlayType getType() {
        return this.type;
    }

    public void close(){
        closeActiveOverlay();
    }

    public static void closeActiveOverlay(){
        SenchaWebElement mask = getActiveMask();
        By maskLocator = By.id(mask.getAttribute("id"));
        clickEmptyCorner(mask);
        waitMaskHidden(maskLocator);
    }

    public static void waitMaskHidden(By maskLocator) {
        if (maskLocator != null) {
            ScreenCard maskCard = new ScreenCard();
            maskCard.addCheckpointElement(maskLocator)
                    .addVisibilityCondition(false);
            maskCard.waitReady();
        }
    }

    @Override
    public boolean checkType(UITitles.OverlayType type) {
        return this.type.equals(type);
    }

    public static SenchaWebElement getActiveMask(){
        Driver.saveTimeout();
        Driver.setTimeout(1);
        SenchaWebElement activeMask = Driver.findVisibleNow(By.className(MASK_CLASS));
        Driver.restoreSavedTimeout();
        return activeMask;
    }

    public static boolean isActiveMaskPresent() {
//        int timeout = Driver.getTimeout();
//        Driver.nullifyTimeout();
        boolean isMaskPresent = (getActiveMask() != null);
//        Driver.setTimeout(timeout);
        return isMaskPresent;
    }

    public static By getActiveMaskLocator(){
        By activeMaskLocator = null;
        SenchaWebElement activeMask = getActiveMask();
        if (activeMask != null) {
            activeMaskLocator = By.id(activeMask.getAttribute("id"));
        }
        return activeMaskLocator;
    }

    private static void clickEmptyCorner(SenchaWebElement mask) {
        //TODO: implement click using JS
        if (System.getProperty("test.browser").equalsIgnoreCase("chrome")) {
            int offsetFromCorner = 10;
            Actions clicker = new Actions(Driver.get());
            clicker.moveToElement(mask, offsetFromCorner, offsetFromCorner).click().perform();
        } else {
            mask.click();
        }
    }
}
