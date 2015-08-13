package ipreomobile.ui.blocks.overlay;

import ipreomobile.templates.ui.BaseOverlay;
import ipreomobile.templates.ui.OverlayController;
import ipreomobile.templates.ui.SingleSelectListImpl;
import ipreomobile.ui.ScreenCard;
import ipreomobile.ui.UITitles;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;


public abstract class SingleSelectOverlay extends SingleSelectListImpl implements OverlayController {

    private static final String CHECKMARK_CLASS_NAME    = "x-item-selected";
    private SenchaWebElement container;
    private SearchOverlay overlay;
    private By maskLocator;

     public SingleSelectOverlay(UITitles.OverlayType type) {
        overlay = new SearchOverlay(type);

        //setItemsXpath(SearchOverlay.getItemsXpath());
        setItemNameXpath(SearchOverlay.getItemNameXpath());

        container = BaseOverlay.getActiveOverlay();
        setListContainer(container);

        setSelectedItemClassName(CHECKMARK_CLASS_NAME);
        setListContainer(BaseOverlay.getActiveOverlay());

        maskLocator = BaseOverlay.getActiveMaskLocator();
    }

//    @Override
//    public void select(String name) {
//
//        super.select(name);
//    }

    @Override
    protected void waitItemSelected(SenchaWebElement item) {
        BaseOverlay.waitMaskHidden(maskLocator);
    }

    @Override
    public void waitReady(){
        overlay.waitReady();
        super.waitReady();
    }

    public SingleSelectOverlay setSearchFilter(String filter){
        overlay.setSearchFilter(filter);
        return this;
    }

    public void findAndSelect(String value) {
        overlay.setSearchFilter(value);
        overlay.waitReady();

        ScreenCard elementsCard = new ScreenCard();
        elementsCard.addCheckpointElement(By.xpath(getItemsXpath()))
                .addVisibilityCondition(true);
        elementsCard.waitReady();

        select(value);
    }

    public boolean isResultListEmpty() {
        return overlay.isResultListEmpty();
    }

    public SingleSelectOverlay verifyResultListEmpty() {
        Assert.assertTrue(isResultListEmpty(), "Results list should be empty, but some entries found.");
        return this;
    }



    public SingleSelectOverlay verifyAlphabeticalSortingOrder(int profilesNumberForVerification){
        int i = 0;
       SenchaWebElement nextItem;
       SenchaWebElement currentItem = getSelected();

        if (getItemName(currentItem).equals("All")){
            currentItem = getNextItem(currentItem);
        }

        while (i < profilesNumberForVerification) {
            nextItem = getNextItem(currentItem);
            verifyAlphabeticalSortingOrder(currentItem, nextItem);
            currentItem = nextItem;
            i++;
        }
        return this;
    }

    protected void verifyAlphabeticalSortingOrder(SenchaWebElement firstWebElement,SenchaWebElement secondWebElement){
        String firstItemName = getItemName(firstWebElement);
        String secondItemName = getItemName(secondWebElement);

        if (secondItemName.equals(firstItemName)){
            String firstItemSubtext = getItemSubtext(firstWebElement);
            String secondItemSubtext = getItemSubtext(secondWebElement);
            Assert.assertTrue(secondItemSubtext.compareTo(firstItemSubtext) >= 0, "Incorrectly sorting order detected. " +
                    "Expected: item with '" + secondItemName + "' name and with '" + secondItemSubtext +
                    "' subtext should come after item with '" + firstItemName + "' name and '" + firstItemSubtext + "' subtext.");
        }else {
            Assert.assertTrue(secondItemName.compareTo(firstItemName) > 0, "Incorrectly sorting order detected. Expected: item with '"
                    + secondItemName + "' name should come after item with '" + firstItemName + "' name");
        }
    }

    @Override
    public void close() {
        overlay.close();
    }

    public UITitles.OverlayType getOverlayType(){
        return overlay.getType();
    }

    public boolean checkType(UITitles.OverlayType type) {
        return overlay.checkType(type);
    }

    public SingleSelectOverlay verifySelectedItemName(String expectedName){
        super.verifySelectedItemName(expectedName);
        return this;
    }
}
