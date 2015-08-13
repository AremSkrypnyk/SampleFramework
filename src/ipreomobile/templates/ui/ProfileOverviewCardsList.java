package ipreomobile.templates.ui;

import ipreomobile.core.Driver;
import ipreomobile.core.SenchaWebElement;
import ipreomobile.core.XPathBuilder;
import org.openqa.selenium.By;

public class ProfileOverviewCardsList extends BaseList {
    private final String ITEMS_XPATH = new XPathBuilder()
            .byClassName("base-card")
            .withNoClassName("x-item-hidden")
            .build();
    private final String ITEM_NAME_XPATH = new XPathBuilder()
            .byClassName("card-title")
            .build();
    private final By CONTAINER_LOCATOR = By.xpath(new XPathBuilder()
            .byClassName("quickview-profile", "active-card")
            .withNoTranslate3dNegativeByY()
            .byClassName("quickview-inner")
            .build());
    private final By SCROLLER_LOCATOR = By.xpath(new XPathBuilder()
            .byClassName("quickview-profile", "active-card")
            .withNoTranslate3dNegativeByY()
            .build());
    private static final int LIST_ITEM_OFFSET = 1;

    public ProfileOverviewCardsList(){
        setItemsXpath(ITEMS_XPATH);
        setItemNameXpath(ITEM_NAME_XPATH);
        setAllElementsPresentInDom(true);
        setListScroller();
    }

    @Override
    protected void setListScroller(){
        SenchaWebElement container = Driver.findVisible(CONTAINER_LOCATOR);
        setListContainer(container);
        SenchaWebElement scroller = Driver.findVisible(SCROLLER_LOCATOR);
        setListScroller(scroller);
    }

    @Override
    public void bringToTop(SenchaWebElement item) {
        setListScroller();
        scrollToListBeginning();
        if (!isElementWithinContainerBounds(item)) {
            super.bringToTop(item);
        }
    }

    @Override
    protected boolean isElementWithinContainerBounds(SenchaWebElement item){
        boolean isInView;
        int containerYCoordinateUpper = listContainer.getLocation().getY();
        int containerYCoordinateLower = listContainer.getLocation().getY() + listContainer.getSize().getHeight();
        int itemYCoordinateUpper = item.getLocation().getY() + LIST_ITEM_OFFSET;
        int itemYCoordinateLower = item.getLocation().getY() + item.getSize().getHeight();
        isInView = (item.isDisplayed() &&
                itemYCoordinateLower <= containerYCoordinateLower &&
                itemYCoordinateUpper >= containerYCoordinateUpper);
        return isInView;
    }
}
