package ipreomobile.ui.blocks.overlay;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.BaseOverlay;
import ipreomobile.templates.ui.OverlayController;
import ipreomobile.ui.CheckpointChain;
import ipreomobile.ui.UITitles;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;

import java.util.concurrent.TimeUnit;

public class SearchOverlay extends BaseOverlay implements OverlayController {

    private static final String SEARCH_FILTER_XPATH     = new XPathBuilder().byTag("input").withClassName("x-input-el").build();
    //private static final String ITEMS_XPATH             = new XPathBuilder().byClassName("x-list-item").build();
    private static final String ITEM_NAME_XPATH         = new XPathBuilder().byClassName("list-simple-item").byClassName("info").byClassName("text").build();
    //private static final String LOADING_INDICATOR_XPATH = new XPathBuilder().byXpathPart(OVERLAY_CONTAINER_XPATH).byClassName("x-loading-indicator").build();
    private static final String LOADING_INDICATOR_XPATH = new XPathBuilder().byClassName("x-loading-indicator").build();
    private String searchCompletedCheckpointXpath       = new XPathBuilder().byClassName("x-list").byClassName("x-innerhtml").build();
    private String searchCompletedWithEmptyResultCheckpointXpath = new XPathBuilder().byClassName("message-panel").byClassName("x-innerhtml").build();

    public SearchOverlay(UITitles.OverlayType type) {
        super(type);
        addLoadingIndicatorCheckpoint(getActiveOverlay());
        waitReady();
    }

    public static String getItemNameXpath() {
        return ITEM_NAME_XPATH;
    }

    public void setSearchFilter(String filter){
       SenchaWebElement searchFilter = Driver.findVisible(By.xpath(SEARCH_FILTER_XPATH), getActiveOverlay());
        searchFilter.click();
        searchFilter.clear();
        searchFilter.sendKeys(filter);
        ((CheckpointChain) addCheckpointChain(By.xpath(LOADING_INDICATOR_XPATH), true)
                .addVisibilityCondition(true))
                .setStartWaitNanoSeconds(TimeUnit.NANOSECONDS.convert(2, TimeUnit.SECONDS) )
                .addClosingCheckpoint(true)
                .addVisibilityCondition(false);
        setCheckpointChainParentItem(getActiveOverlay());
        waitReady();
    }

    public boolean isResultListEmpty() {
        String resultText = Driver.findVisible(By.xpath(searchCompletedWithEmptyResultCheckpointXpath), getActiveOverlay()).getText(); //getActiveOverlay().findElement(By.xpath(searchCompletedWithEmptyResultCheckpointXpath)).getText();
        return resultText.equals("No results found.") || resultText.contains("Tap the search field above to find an");
    }

    public String getSearchCompletedCheckpointXpath(){
        return searchCompletedCheckpointXpath;
    }



}
