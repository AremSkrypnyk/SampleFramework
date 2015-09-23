package ipreomobile.ui;

import ipreomobile.core.Driver;
import ipreomobile.core.ElementHelper;
import ipreomobile.core.Logger;
import ipreomobile.core.SenchaWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;

public class VerticalScroller extends ScreenCard {

    private String listId;
    public static final String GET_SCROLLABLE_COMMAND = "Ext.getCmp('%s').getScrollable().getScroller()";
    public static final String SET_OFFSET_COMMAND = "Ext.ComponentQuery.query('#%s')[0]._setOffset(%s)";

    public VerticalScroller(String id){
        init(id);
    }

    private void init(String id) {
        this.listId = id;
        this.setAnimationTimeout(1);
    }

    public void scrollBy(int yOffset){
        try {
            Driver.executeJS(String.format(GET_SCROLLABLE_COMMAND, listId) + ".scrollBy(0, " + yOffset + ", true)");
        } catch (WebDriverException e) {
            Logger.logDebug("Scrollable container: " +
                            ElementHelper.describe(Driver.findFirst(By.id(listId))) +
                            "\nUnable to scroll by getScroller(). Trying to use setOffset.");
            Driver.executeJS(String.format(SET_OFFSET_COMMAND, listId, -yOffset));
        }
        waitReady();
    }

    public void scrollByElementHeight(SenchaWebElement element){
        scrollBy(element.getSize().height);
    }

    public void scrollBetweenElements(SenchaWebElement scrollFrom, SenchaWebElement scrollTo){
        int yOffset = yOffsetCalculation(scrollFrom, scrollTo);
        scrollBy(yOffset);
        waitReady();
    }

    public void scrollToTop(){
        try {
            Driver.executeJS(String.format(GET_SCROLLABLE_COMMAND, listId) + ".scrollToTop(true)");
        } catch (WebDriverException e) {
            Logger.logDebug("Scrollable container: " +
                    ElementHelper.describe(Driver.findFirst(By.id(listId))) +
                    "\nUnable to scroll by getScroller(). Trying to use setOffset.");
            Driver.executeJS(String.format(SET_OFFSET_COMMAND, listId, 0));
        }
        waitReady();
    }

    //temporary solution until scrollToEnd is not fixed
    public void scrollToEnd(){
        //Driver.executeJS(String.format(GET_SCROLLABLE_COMMAND, listId) + ".scrollToEnd(true)");
        Driver.executeJS(String.format(GET_SCROLLABLE_COMMAND, listId) + ".scrollToEnd().scrollToEnd().scrollBy(0, 1, true)");
        waitReady();
    }

    public boolean isElementWithinScrollableContainerBounds(SenchaWebElement element) {
        SenchaWebElement scrollableContainer = Driver.findOne(By.id(listId));
        int scrollableContainerYCoordinateUpper = scrollableContainer.getLocation().getY();
        int scrollableContainerYCoordinateLower = scrollableContainerYCoordinateUpper + scrollableContainer.getSize().getHeight();
        return (element.isDisplayed() &&
                element.getLocation().getY() + element.getSize().getHeight() <= scrollableContainerYCoordinateLower &&
                element.getLocation().getY() >= scrollableContainerYCoordinateUpper);
    }

    public void bringElementIntoView(SenchaWebElement element) {
        if (!isElementWithinScrollableContainerBounds(element)) {
            SenchaWebElement scrollableContainer = Driver.findOne(By.id(listId));

            int scrollableContainerYCoordinateUpper = scrollableContainer.getLocation().getY();
            int scrollableContainerYCoordinateLower = scrollableContainerYCoordinateUpper + scrollableContainer.getSize().getHeight();

            int elementYCoordinateUpper = element.getLocation().getY();
            int elementYCoordinateLower = elementYCoordinateUpper + element.getSize().getHeight();

            int diff;
            //if element top part is hidden, we have to swipe down -> scroll up -> diff < 0
            if (elementYCoordinateUpper < scrollableContainerYCoordinateUpper) {
                diff = elementYCoordinateUpper - scrollableContainerYCoordinateUpper;
            } else {
                //if element bottom part is hidden, we have to swipe up -> scroll down -> delta > 0
                if (elementYCoordinateLower > scrollableContainerYCoordinateLower) {
                    diff = elementYCoordinateLower - scrollableContainerYCoordinateLower;
                    //
                } else if (elementYCoordinateUpper != scrollableContainerYCoordinateUpper && elementYCoordinateLower != scrollableContainerYCoordinateLower) {
                    throw new Error("Cannot determine element position:\n" + ElementHelper.describe(scrollableContainer) + "\n" + ElementHelper.describe(element));
                } else {
                    diff = 0;
                    Logger.logMessage("No scrolling will be performed.");
                }
            }
            scrollBy(diff);
        }
    }



    private int yOffsetCalculation(SenchaWebElement scrollFrom, SenchaWebElement scrollTo){
        return scrollFrom.getLocation().getY() - scrollTo.getLocation().getY();
    }

}
