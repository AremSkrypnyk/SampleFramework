package ipreomobile.ui.blocks;

import ipreomobile.core.Driver;
import ipreomobile.core.SenchaWebElement;
import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.ScreenCard;
import org.openqa.selenium.By;

public class Carousel extends ScreenCard {
    private String containerXpath;
    private String carouselId;

    private static final String CAROUSEL_CONTAINER_CLASS = "x-carousel";
    private static final String CAROUSEL_CONTENT_CLASS = "x-carousel-inner";
    private static final String CAROUSEL_CARD_CLASS = "x-carousel-item";

    private static final String DEFAULT_CAROUSEL_XPATH = new XPathBuilder().byClassName(CAROUSEL_CONTAINER_CLASS, "x-container").build();

    public static final String GET_PAGE_COMMAND =
            "var carousel = Ext.ComponentQuery.query('#%s')[0];" +
            " carousel.setActiveItem(%d);" +
            " carousel.fireEvent('userSwipe', carousel, carousel.getActiveItem());";

    public static final String GET_ACTIVE_PAGE_INDEX_COMMAND =
            "var carousel = Ext.ComponentQuery.query('#%s')[0]; " +
            "return carousel.getActiveIndex();";

    public static final String
            GET_CAROUSEL_SIZE_COMMAND =
            "var carousel = Ext.ComponentQuery.query('#%s')[0]; " +
            "return carousel._indicator.indicators.length;";

    private String activeCardXpath;

    private SenchaWebElement container, carousel;
    private int currentIndex;
    private int expectedIndex;
    private int carouselSize;

    public Carousel(){
        setContainerXpath(DEFAULT_CAROUSEL_XPATH);
    }

    public void setContainerXpath(String containerXpath) {
        this.containerXpath = containerXpath;
        this.activeCardXpath = new XPathBuilder()
                .byClassName(CAROUSEL_CONTENT_CLASS)
                .byClassName(CAROUSEL_CARD_CLASS)
                .byXpathPart("[contains(@style, '-webkit-transform: translate3d(0px, 0px, 0px)')]")
                .build();
    }

    public void goForward(){
        if (hasNext()){
            expectedIndex = currentIndex + 1;
            Driver.executeJS(String.format(GET_PAGE_COMMAND, carouselId, expectedIndex));
        }
    }

    public void goBackward(){
        if (hasPrevious()) {
            expectedIndex = currentIndex - 1;
            Driver.executeJS(String.format(GET_PAGE_COMMAND, carouselId, expectedIndex));
        }
    }

    public SenchaWebElement getActiveCarouselItem(){
        getActivePageIndex();
        return Driver.findVisible(By.xpath(activeCardXpath), container);
    }

    public void goToPage(int pageIndex){
        currentIndex = getActivePageIndex();
        while (currentIndex < pageIndex) {
            goForward();
            currentIndex = getActivePageIndex();
        }
        while (currentIndex > pageIndex) {
            goBackward();
            currentIndex = getActivePageIndex();
        }
    }

    public boolean hasNext() {
        currentIndex = getActivePageIndex();
        carouselSize = getCarouselSize();
        return (currentIndex < carouselSize);
    }

    public boolean hasPrevious() {
        currentIndex = getActivePageIndex();
        return (currentIndex > 0);
    }

    public int getActivePageIndex(){
        container = Driver.findFirst(By.xpath(containerXpath));
        container.bringToView();
        carousel = Driver.findVisible(By.xpath(DEFAULT_CAROUSEL_XPATH), container);
        carouselId = carousel.getId();

        return (int) ((long) Driver.executeJS(String.format(GET_ACTIVE_PAGE_INDEX_COMMAND, carouselId)));
    }

    public int getCarouselSize(){
        return (int) ((long) Driver.executeJS(String.format(GET_CAROUSEL_SIZE_COMMAND, carouselId)));
    }
}
