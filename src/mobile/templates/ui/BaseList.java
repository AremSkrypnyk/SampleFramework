package ipreomobile.templates.ui;

import ipreomobile.core.*;
import ipreomobile.ui.CheckpointChain;
import ipreomobile.ui.ScreenCard;
import ipreomobile.ui.VerticalScroller;
import ipreomobile.ui.blocks.StatusIndicator;
import org.openqa.selenium.*;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class BaseList extends ScreenCard {

    private String itemsXpath = new XPathBuilder().byClassName("x-list-item").build();
    private String itemNameXpath;
    private String disabledItemClassName = "disabled";
    private String scrollableListId;
    private By bottomMaskBy;
    private static final String UNAVAILABLE_FLAG_CLASS = "unavailable";
    private static final String CACHED_FLAG_CLASS = "force-offline-available";

    private boolean allElementsPresentInDom = false;

    protected SenchaWebElement listContainer;
    private String itemXpathByName;
    protected String listNamePrefix = "[List '"+ getScreenName() + "']: ";

    private final String LOADIND_MORE_TEXT_XPATH        = new XPathBuilder().byTag("div").withIdContains("%s").byTag("div").withText("Loading more...").build();
    private final String LOADING_MORE_INDICATOR_XPATH   = new XPathBuilder().byTag("div").withIdContains("%s").byTag("div").withClassName("x-loading-spinner").build();
    protected final String LAST_LIST_ITEM_CLASS_NAME      = "x-list-item-last";
    protected final String FIRST_LIST_ITEM_CLASS_NAME      = "x-list-item-first";

    private long SPINNER_TIMEOUT = TimeUnit.NANOSECONDS.convert(3, TimeUnit.NANOSECONDS.SECONDS);

    private static final int LIST_ITEM_OFFSET = 1;
    private boolean nonStrictNameEqualityFlag;

    protected void setScrollerById(String scrollableListId) {
        this.scrollableListId = scrollableListId;
        this.scroller = new VerticalScroller(scrollableListId);
    }

    protected void setNonStrictNameEqualityFlag(boolean nonStrictNameEqualityFlag) {
        this.nonStrictNameEqualityFlag = nonStrictNameEqualityFlag;
    }

    public boolean isAllElementsPresentInDom() {
        return allElementsPresentInDom;
    }

    public void setAllElementsPresentInDom(boolean allElementsPresentInDom) {
        this.allElementsPresentInDom = allElementsPresentInDom;
    }

    public void setBottomMaskBy(By bottomMaskBy) {
        this.bottomMaskBy = bottomMaskBy;
    }

    protected VerticalScroller getScroller() {
        return scroller;
    }

    private VerticalScroller scroller;

    public void setItemsXpath(String xpath) {
        itemsXpath = xpath;
    }

    public String getItemsXpath() {
        return itemsXpath;
    }

    protected void setListScroller(){
        SenchaWebElement firstVisibleListItem = Driver.findVisible(By.xpath(itemsXpath), listContainer);
        if (firstVisibleListItem != null) {
            int itemsCount = getItemsByXpath(itemsXpath).size();
            if (itemsCount == 1) {
                Logger.logDebug("List "+getScreenName()+" has only 1 item. No scroller available.");
            } else {
                scrollableListId = firstVisibleListItem.getScrollableContainerId();
                if (scrollableListId != null) {
                    scroller = new VerticalScroller(scrollableListId);
                }
            }
        }
    }

    protected void setListScroller(SenchaWebElement scrollableContainer){
        scrollableListId = scrollableContainer.getAttribute("id");
        scroller = new VerticalScroller(scrollableListId);
    }

    protected void scrollToListBeginning(){
        scroller.scrollToTop();
    }

    public void setItemNameXpath(String xpath) {
        itemNameXpath = xpath;
    }

    public String getItemNameXpath() {
        return itemNameXpath;
    }

    public void setDisabledItemClassName(String className) { this.disabledItemClassName = className; }

    public void setListContainer(SenchaWebElement el) { this.listContainer = el; }

    public String click(String name) {
        SenchaWebElement foundItem = getItem(name);
        Assert.assertNotNull(foundItem, listNamePrefix + "Cannot click item '"+name+"': no such item exists in the list.");
        click(foundItem);
        return name;
    }

    protected String click(SenchaWebElement foundItem) {
        foundItem.highlight();
        String name = getItemName(foundItem);
        foundItem.click();
        return name;
    }

    public SenchaWebElement getFirstItemWith(String name) {
        String itemXpath = getItemXpathByName(name);
        return getFirstItemByXpath(itemXpath);
    }

    public SenchaWebElement getItem(String name)  {
        String itemXpath = getItemXpathByName(name);
        return getItemByXpath(itemXpath);
    }

    public SenchaWebElement getFirstItemWith(String name, String subtext) {
        String itemXpath = getItemXpathByNameAndSubtext(name, subtext);
        return getFirstItemByXpath(itemXpath);
    }

    public SenchaWebElement getItem(String name, String subtext) {
        String itemXpath = getItemXpathByNameAndSubtext(name, subtext);
        return getItemByXpath(itemXpath);

    }

    public String getNextItemName(String name) {
        SenchaWebElement currentItem = getItem(name);
        SenchaWebElement nextItem = getNextItem(currentItem);
        return getItemName(nextItem);
    }

    public String getPreviousItemName(String name) {
        SenchaWebElement currentItem = getItem(name);
        SenchaWebElement previousItem = getPreviousItem(currentItem);
        return getItemName(previousItem);
    }

    public void verifyItemPresence(String name){
        Assert.assertNotNull(getItem(name), listNamePrefix + "'" + name + "' was not found in the list.");
    }

    public void verifyItemPresence(String name, String subtext) {
        Assert.assertNotNull(getItem(name, subtext), listNamePrefix + "'" + name + "' with subtext '"+subtext+"' was not found in the list.");
    }

    public void verifyItemAbsence(String name)  {
        Assert.assertNull(getItem(name), listNamePrefix + "'" + name + "' was found in "+getScreenName()+" list, but not expected.");
    }

    public void verifyItemAbsence(String name, String subtext) {
        Assert.assertNull(getItem(name, subtext), listNamePrefix + "'" + name + "' with subtext '"+subtext+"'was found in "+getScreenName()+" list, but not expected.");
    }

    public void verifyItemFirstInList(String name) {
        SenchaWebElement item = getItem(name);
        Assert.assertNotNull(item, listNamePrefix + "'" + name + "' was not found in "+getScreenName()+" list.");
        Assert.assertTrue(item.hasClass(FIRST_LIST_ITEM_CLASS_NAME), "'" + name + "' isn't first item in "+getScreenName()+" list.");
    }


    public SenchaWebElement getNextItem(SenchaWebElement currentItem) {
        bringToTop(currentItem);

        String visibleItemsXpath = new XPathBuilder().byXpathPart(itemsXpath).withNoTranslate3dNegativeByY().build();
        List<SenchaWebElement> items = getItemsByXpath(visibleItemsXpath);
        SenchaWebElement targetItem = null;

        items = sortListByYCoordinatesDescendingOrder(items);
        for (int i=0;i<items.size()-1; i++) {
            if (items.get(i).getLocation().getY() == currentItem.getLocation().getY()) {
                if (items.get(i).getLocation().getX() == currentItem.getLocation().getX()) {
                    targetItem = items.get(i + 1);
                    break;
                }
            }
        }

        return targetItem;
    }

    public SenchaWebElement getPreviousItem(SenchaWebElement currentItem) {
        bringToTop(currentItem);

        String visibleItemsXpath = new XPathBuilder().byXpathPart(itemsXpath).withNoTranslate3dNegativeByY().build();
        List<SenchaWebElement> items = getItemsByXpath(visibleItemsXpath);
        SenchaWebElement targetItem = null;

        items = sortListByYCoordinatesDescendingOrder(items);
        for (int i=1;i<items.size(); i++) {
            if (items.get(i).getLocation().getY() == currentItem.getLocation().getY()) {
                if (items.get(i).getLocation().getX() == currentItem.getLocation().getX()) {
                    targetItem = items.get(i - 1);
                    break;
                }
            }
        }

        return targetItem;
    }

    private boolean isLastListItemVisible(){
        return findVisibleItemFromTheEnd(0).hasClass(LAST_LIST_ITEM_CLASS_NAME);
    }

    protected boolean isEndOfListReached(){
        return ( (!hasMoreElements() && isLastListItemVisible()));
    }

    protected List<SenchaWebElement> findItems(String itemXpath) {
        verifyListContainsItems();
        setListScroller();
        List<SenchaWebElement> foundItems = getItemsByXpath(itemXpath);

        if (foundItems.isEmpty() && !allElementsPresentInDom) {
            if (scroller != null) {
                scroller.scrollToTop();
                while (foundItems.isEmpty() && !isEndOfListReached()) {
                    scrollFromFirstToLastVisible();
                    foundItems = getItemsByXpath(itemXpath);
                }
            }
        }
        return foundItems;
    }

    protected SenchaWebElement getFirstItemByXpath(String itemXpath){
        List<SenchaWebElement> foundItems = findItems(itemXpath);
        if (!foundItems.isEmpty()) {
            bringToView(foundItems.get(0));
        }
        return (foundItems.isEmpty()) ? null : foundItems.get(0);
    }

    protected SenchaWebElement getItemByXpath(String itemXpath)  {
        List<SenchaWebElement> foundItems = findItems(itemXpath);

        if (foundItems.size() > 1) {
            List<String> errorMessage = new ArrayList<>();
            errorMessage.add(listNamePrefix + "More than 1 item was found by XPath '" + itemXpath + "'.");
            if (listContainer == null) {
                errorMessage.add("No list container specified.");
            } else {
                errorMessage.add("List container: \n"+listContainer.getClassNames()
                        + "; size: " + listContainer.getSize()
                        + "; position: " + listContainer.getLocation());
            }
            errorMessage.add("Items description: ");
            for (int i = 0; i < foundItems.size(); i++) {
                errorMessage.add("\tItem #"+i);
                errorMessage.addAll(ElementHelper.describe(foundItems.get(i)));
            }
            throw new DuplicateListEntryError(String.join("\n", errorMessage));
        }
        if (foundItems.size() == 1){
            bringToView(foundItems.get(0));
        }

        //Driver.resetTimeout();
        return (foundItems.isEmpty()) ? null : foundItems.get(0);
    }

    protected String getItemName(SenchaWebElement item) {
        Assert.assertNotNull(itemNameXpath, listNamePrefix + "XPath for list item name should be provided in a list implementation.\n" +
                "Item name is the part of item inner text which can identify the list item.");
        Assert.assertNotNull(item, listNamePrefix + "Item is null, cannot fetch its name.");
        return Driver.findVisible(By.xpath(itemNameXpath), item).getText();
    }

    private String getItemXpathByName(String name) {
        Assert.assertNotNull(itemsXpath, listNamePrefix + "No XPath was specified for the list items.");
        Assert.assertNotNull(itemNameXpath, "No XPath was specified for list item names.");
        String namePart = (nonStrictNameEqualityFlag)
                ? "[" + new XPathBuilder().byCurrentItem().byXpathPart(itemNameXpath).withTextContains(name).build() + "]"
                : "[" + new XPathBuilder().byCurrentItem().byXpathPart(itemNameXpath).withText(name).build() + "]";
        itemXpathByName = new XPathBuilder()
                .byXpathPart(itemsXpath)
                .withNoTranslate3dNegativeByY()
                .byXpathPart(namePart)
                .build();
        return itemXpathByName;

    }

    private String getItemXpathByNameAndSubtext(String name, String subtext){
        Assert.assertNotNull(itemsXpath, listNamePrefix + "No XPath was specified for the list items.");
        Assert.assertNotNull(subtextXpath, listNamePrefix + "No subtext XPath was specified for the list items.");

        itemXpathByName = (nonStrictNameEqualityFlag)
                ? new XPathBuilder().byXpathPart(itemsXpath)
                    .withNoTranslate3dNegativeByY()
                    .withChildTextContains(name).build()
                :new XPathBuilder().byXpathPart(itemsXpath)
                    .withNoTranslate3dNegativeByY()
                    .withChildText(name).build();

        return new XPathBuilder().byXpathPart(itemXpathByName).withChildText(subtext).build();
    }

    private String subtextXpath = new XPathBuilder().byClassName("info").byClassName("subtext").build();

    public void setSubtextXpath(String subtextXpath) {
        this.subtextXpath = subtextXpath;
    }

    public String getSubtextXpath() {
        return subtextXpath;
    }

    public String getItemSubtext(String name)  {
        return getItemSubtext(getItem(name));
    }

    protected String getItemSubtext(SenchaWebElement item) {
        String subtext = "";
        Assert.assertNotNull(subtextXpath, listNamePrefix + "XPath for list item subtext should be provided in a list implementation.");
        List<SenchaWebElement> subtextItems = Driver.findAll(By.xpath(subtextXpath), item);
        if (subtextItems.size() > 1) {
            Logger.logError(listNamePrefix + "Subtext selector invalid: more than 1 element was found by subtext XPath ["
                    + subtextXpath + "].\n[List item] "
                    + ElementHelper.describe(item));
        } else if (subtextItems.isEmpty()) {
            Logger.logError(listNamePrefix + "Subtext selector invalid: no subtext was found by XPath ["
                    + subtextXpath + "].\n[List item] "
                    + ElementHelper.describe(item));
        } else {
            subtext = subtextItems.get(0).getText();
        }
        return subtext;
    }

    protected void bringToView(SenchaWebElement item) {
        if (!isElementWithinContainerBounds(item)) {
            bringToTop(item);
        }
    }

    protected void bringToTop( SenchaWebElement item ){
        setListScroller();
        SenchaWebElement scrollTo = findFirstVisibleItem();
        Assert.assertNotNull(scrollTo, listNamePrefix + "No visible item was found in the list: check if waitReady() method was called before interacting with the list;");
        scroller.scrollBetweenElements(item, scrollTo);
    }

    protected boolean isElementWithinContainerBounds(SenchaWebElement item){
        if (scroller == null) {
            if (item.getText().isEmpty()) {
                throw new Error(listNamePrefix + "Scrollable container was not found for the list. " +
                        "Found item is not displayed and cannot be brought into view: \n" +
                        ElementHelper.describe(item));
            } else {
                return true;
            }
        }
        int scrollableContainerYCoordinateUpper = (Driver.findOne(By.id(scrollableListId)).getLocation().getY());
        int scrollableContainerYCoordinateLower = getScrollableContainerYCoordinateLower();
        int itemYCoordinateUpper = item.getLocation().getY() + LIST_ITEM_OFFSET;
        int itemYCoordinateLower = item.getLocation().getY() + item.getSize().getHeight();

        return (item.isDisplayed() &&
                itemYCoordinateLower <= scrollableContainerYCoordinateLower &&
                itemYCoordinateUpper >= scrollableContainerYCoordinateUpper);
    }

    private int getScrollableContainerYCoordinateLower(){
        int scrollableContainerYCoordinateLower = (Driver.findOne(By.id(scrollableListId)).getLocation().getY() + Driver.findOne(By.id(scrollableListId)).getSize().getHeight());
        if (bottomMaskBy != null) {
            //Driver.nullifyTimeout();
            SenchaWebElement bottomMask = Driver.findVisibleNow(bottomMaskBy);
            if (bottomMask != null) {
                scrollableContainerYCoordinateLower -= bottomMask.getSize().getHeight();
            }
        }
        return scrollableContainerYCoordinateLower;
    }

    protected SenchaWebElement findFirstVisibleItem(){
        String visibleItemsXpath = new XPathBuilder().byXpathPart(itemsXpath).withNoTranslate3dNegativeByY().build();
        List<SenchaWebElement> items = findItems(visibleItemsXpath);
        if (items.size() == 1) {
            return items.get(0);
        }

        SenchaWebElement firstVisibleItem = null;
        int scrollableContainerYCoordinate = Driver.findIfExists(By.id(scrollableListId)).getLocation().getY();

        try {
            items = sortListByYCoordinatesDescendingOrder(items);
            for (SenchaWebElement listItem : items) {
                if (listItem.isDisplayed() &&
                        listItem.getLocation().getY()  >= scrollableContainerYCoordinate) {
                    firstVisibleItem = listItem;
                    break;
                }
            }
        } catch (StaleElementReferenceException e) {
            Logger.logWarning(listNamePrefix + "Stale element reference detected. Calling findFirstVisibleItem function once more.");
            return findFirstVisibleItem();
        }
        return firstVisibleItem;

    }

    private SenchaWebElement findVisibleItemFromTheEnd(int offset){
        String visibleItemsXpath = new XPathBuilder().byXpathPart(itemsXpath).withNoTranslate3dNegativeByY().build();
        List<SenchaWebElement> items = getItemsByXpath(visibleItemsXpath);
        if (items.size() == 1) {
            return items.get(0);
        }
        SenchaWebElement targetItem = null;
        int scrollableContainerYCoordinateLower = getScrollableContainerYCoordinateLower();

        items = sortListByYCoordinatesDescendingOrder(items);

        SenchaWebElement previousItem;

        for (int i = items.size() - 1; i>=0; i--){
            if (items.get(i).isDisplayed() && items.get(i).getLocation().getY() <= scrollableContainerYCoordinateLower){
                previousItem = items.get(i-offset);
                if (isElementWithinContainerBounds(previousItem)) {
                    targetItem = previousItem;
                    break;
                }
            }
        }

        return targetItem;
    }

    private List<SenchaWebElement> sortListByYCoordinatesDescendingOrder(List<SenchaWebElement> items){
        CollectionHelper.removeNull(items);
        Comparator<SenchaWebElement> comparatorByY = (element1, element2) -> element1.getLocation().getY() - element2.getLocation().getY();
        Collections.sort(items, comparatorByY);
        return items;
    }

    private void scrollFromFirstToLastVisible() {
        Logger.setDebugMode(true);
        ScreenCard listCard = new ScreenCard();
        String spinnerXpath = String.format(LOADING_MORE_INDICATOR_XPATH, scrollableListId);
        ((CheckpointChain)listCard
                .addCheckpointChain(By.xpath(spinnerXpath))
                .addVisibilityCondition(true))

                .setStartWaitNanoSeconds(SPINNER_TIMEOUT)

                .addClosingCheckpointStage()
                .addInvisibleOrAbsentCondition();

        SenchaWebElement scrollTo = findFirstVisibleItem();
        SenchaWebElement scrollFrom = findVisibleItemFromTheEnd(1);

        scroller.scrollBetweenElements(scrollFrom, scrollTo);
        listCard.waitReady();
    }

    private boolean hasMoreElements(){
        boolean result;
        String loadingMoreTokenXpath = String.format(LOADIND_MORE_TEXT_XPATH, scrollableListId);
        result = Driver.findIfExistsNow(By.xpath(loadingMoreTokenXpath), listContainer) != null;
        return result;
    }

    private List<SenchaWebElement> getItemsByXpath(String xpath) {
        return Driver.findAllNow(By.xpath(xpath), listContainer);
    }

    private void verifyListContainsItems() {
        Assert.assertFalse(isListEmpty(), listNamePrefix + "Cannot proceed: list contains no items.");
    }

    public boolean isListEmpty(){
        return getItemsByXpath(itemsXpath).isEmpty();
    }

    protected boolean checkListItemProperty(SenchaWebElement item, String className) {
        return item.getAttribute("class").contains(className);
    }

    public boolean isItemUnavailableInOfflineMode(String itemName) {
        SenchaWebElement item = getItem(itemName);
        Assert.assertNotNull(item, getScreenName() + ": Cannot check if ["+itemName+"] is unavailable in offline: no such element was found.");
        return isItemUnavailableInOfflineMode(item);
    }

    public boolean isItemUnavailableInOfflineMode(String itemName, String subtext) {
        SenchaWebElement item = getItem(itemName, subtext);
        Assert.assertNotNull(item, getScreenName() + ": Cannot check if ["+itemName+"] with subtext ["+subtext+"] is unavailable in offline: no such element was found.");
        return isItemUnavailableInOfflineMode(item);
    }

    public boolean isItemUnavailableInOfflineMode(SenchaWebElement listItem) {
        SenchaWebElement unavailableChildElement = Driver.findIfExistsNow(By.xpath(new XPathBuilder().byClassName(UNAVAILABLE_FLAG_CLASS).build()), listItem);
        return ((unavailableChildElement != null) || (listItem.hasClass(UNAVAILABLE_FLAG_CLASS))) && StatusIndicator.isOfflineModeEnabled()
                && (listItem.hasNoClass(CACHED_FLAG_CLASS));
    }

    public boolean isItemPresentBySubtext(String subtext) {
        String itemXpath = new XPathBuilder().byXpathPart(subtextXpath).withText(subtext).build();
        return getFirstItemByXpath(itemXpath) != null;
    }

    public void verifyItemPresentBySubtext(String subtext) {
        Assert.assertTrue(isItemPresentBySubtext(subtext));
    }
}

