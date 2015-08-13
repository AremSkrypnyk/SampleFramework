package ipreomobile.templates.ui;

import ipreomobile.core.Driver;
import ipreomobile.core.ElementHelper;
import ipreomobile.core.SenchaWebElement;
import ipreomobile.core.StringHelper;
import ipreomobile.ui.ScreenCard;
import org.openqa.selenium.By;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public abstract class MultiSelectListImpl extends BaseList implements MultiSelectListController {

    private String stateSwitcherXpath;
    private String stateTokenXpath;
    private String stateTokenSelectedClassName;

    protected String verifyCheckedAssertionError = "Entries %s must be %s.";

    protected String noStateTokenError = "List with multiple selection must have XPath specified for the item part which displays element state (obtains extra class when item is selected).";
    protected String noStateSwitcherError = "List with multiple selection must have XPath specified for the item part which should be clicked to change element state.";
    protected String noStateTokenClassError = "List with multiple selection must have class name specified for the selected item.";


    public void setStateSwitcherXpath(String stateSwitcherXpath) {
        this.stateSwitcherXpath = stateSwitcherXpath;
    }

    public void setStateTokenXpath(String stateTokenXpath) {
        this.stateTokenXpath = stateTokenXpath;
    }

    public void setStateTokenSelectedClassName(String stateTokenSelectedClassName) {
        this.stateTokenSelectedClassName = stateTokenSelectedClassName;
    }

    @Override
    public void check(String... names) {
        SenchaWebElement item;
        for(String name : names) {
            item = getItem(name);
            assertNotNull(item, "[" + name + "] was not found in " + getScreenName() + " and cannot be checked.");
            if (!isItemUnavailableInOfflineMode(item)) {
                check(item);
            }
        }
    }

    public void checkBySubtext(String name, String subtext) {
        SenchaWebElement item = getItem(name, subtext);
        assertNotNull(item, "[" + name + "] with subtext [" + subtext + "] was not found in " + getScreenName() + " and cannot be checked.");
        if (!isItemUnavailableInOfflineMode(item)) {
            check(item);
        }
    }

    private void check(SenchaWebElement item) {
        switchItemState(item, true);
    }

    @Override
    public void uncheck(String... names) {
        SenchaWebElement item;
        for(String name : names) {
            item = getItem(name);
            assertNotNull(item, "[" + name + "] was not found in " + getScreenName() + " and cannot be unchecked.");
            if (!isItemUnavailableInOfflineMode(item)) {
                uncheck(item);
            }
        }
    }

    public void uncheckBySubtext(String name, String subtext) {
        SenchaWebElement item = getItem(name, subtext);
        assertNotNull(item, "[" + name + "] with subtext [" + subtext + "] was not found in " + getScreenName() + " and cannot be unchecked.");
        if (!isItemUnavailableInOfflineMode(item)) {
            uncheck(item);
        }
    }

    private void uncheck(SenchaWebElement item) {
        switchItemState(item, false);
    }

    protected void switchItemState(SenchaWebElement item, boolean desiredState) {
        if (isChecked(item) != desiredState) {
            Driver.findOne(By.xpath(stateSwitcherXpath), item).click();
            waitItemStateChanged(item, desiredState);
        }
    }

    protected void waitItemStateChanged(SenchaWebElement item, boolean desiredState){
        ScreenCard itemCard = new ScreenCard();
        itemCard.setMaxWaitTimeout(5);
        itemCard.addOneTimeCheckpoint(By.xpath(stateTokenXpath))
                .addClassCondition(stateTokenSelectedClassName, desiredState)
                .setParentItem(item);
        itemCard.waitReady();
    }

    @Override
    public boolean areChecked(String... names) {
        for(String name : names) {
            SenchaWebElement item = getItem(name);
            assertNotNull(item, "[" + name + "] was not found in " + getScreenName() + " and its state cannot be found.");
            if (!isChecked(item)) {
                return false;
            }
        }
        return true;
    }

    public boolean areUnchecked(String... names) {
        for(String name : names) {
            SenchaWebElement item = getItem(name);
            assertNotNull(item, "[" + name + "] was not found in " + getScreenName() + " and its state cannot be found.");
            if (isChecked(item)) {
                return false;
            }
        }
        return true;
    }

    public void verifyChecked(String... names){
        assertTrue(areChecked(names), String.format(verifyCheckedAssertionError, StringHelper.nameArrayToString(names), "checked"));
    }

    public void verifyUnchecked(String... names){
        assertTrue(areUnchecked(names), String.format(verifyCheckedAssertionError, StringHelper.nameArrayToString(names), "unchecked"));
    }

    @Override
    public boolean isChecked(String name) {
        SenchaWebElement listItem = getItem(name);
        assertNotNull(listItem, "[" + name + "] was not found in " + getScreenName() + " and its state cannot be found.");
        return isChecked(listItem);
    }

    public boolean isChecked(String name, String subtext) {
        SenchaWebElement listItem = getItem(name, subtext);
        assertNotNull(listItem, "[" + name + "] was not found in " + getScreenName() + " and its state cannot be found.");
        return isChecked(listItem);
    }

    private boolean isChecked(SenchaWebElement item) {
        assertNotNull(stateTokenXpath, noStateTokenError);
        assertNotNull(stateTokenSelectedClassName, noStateTokenClassError);
        assertNotNull(stateSwitcherXpath, noStateSwitcherError);
        SenchaWebElement favouriteIcon = Driver.findOne(By.xpath(stateTokenXpath), item);
        return favouriteIcon.hasClass(stateTokenSelectedClassName);
    }

//    @Override
//    public List<SenchaWebElement> getChecked() {
//        List<SenchaWebElement> selectedItems = new ArrayList<>();
//        List<SenchaWebElement> items = Driver.get().findElements(By.xpath(getItemsXpath()));
//        for (SenchaWebElement item : items) {
//            if (isChecked(item)) {
//                selectedItems.add(item);
//            }
//        }
//        return selectedItems;
//    }

//    @Override
//    public List<String> getCheckedNames() {
//        List<String> selectedNames = new ArrayList<>();
//        List<SenchaWebElement> items = Driver.get().findElements(By.xpath(getItemsXpath()));
//        for (SenchaWebElement item : items) {
//            if (isChecked(item)) {
//                selectedNames.add(getItemName(item));
//            }
//        }
//        return selectedNames;
//    }
//
//    public String listCheckedNames(){
//        return StringHelper.nameArrayToString(getCheckedNames());
//    }
//    public void verifyCheckedCount(int count) {
//        assertEquals(getChecked().size(), count, verifyCheckedCountAssertionError);
//    }
//    public void uncheckAll(){
//        for (SenchaWebElement item : Driver.get().findElements(By.xpath(getItemsXpath()))) {
//            uncheck(item);
//        }
//    }
//    public void checkAll(){
//        List<SenchaWebElement> listItems = Driver.get().findElements(By.xpath(getItemsXpath()));
//        for (SenchaWebElement item : listItems) {
//            check(item);
//        }
//    }
}
