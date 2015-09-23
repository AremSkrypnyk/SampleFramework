package ipreomobile.templates.ui;

import ipreomobile.core.SenchaWebElement;
import ipreomobile.core.XPathBuilder;
import org.openqa.selenium.By;
import org.testng.Assert;

import static org.testng.Assert.assertTrue;

public abstract class SingleSelectListImpl extends BaseList implements SingleSelectListController {

    private String selectedItemClassName = "active-item";

    public void setSelectedItemClassName(String className) {
        this.selectedItemClassName = className;
    }

    public String getSelectedItemClassName() {
        return selectedItemClassName;
    }

    public void select(String name){
        SenchaWebElement item = getItem(name);
        Assert.assertNotNull(item, "Item '"+name+"' was not found in the list.");
        if (!isSelected(item)) {
            click(item);
            waitItemSelected(item);
        }
    }

    public void selectFirstWithName(String name) {
        SenchaWebElement item = getFirstItemWith(name);
        Assert.assertNotNull(item, "Item '"+name+"' was not found in the list.");
        if (!isSelected(item)) {
            click(item);
            waitItemSelected(item);
        }
    }

    public void select(String name, String subtext)  {
        setNonStrictNameEqualityFlag(true);
        SenchaWebElement item = getItem(name, subtext);
        Assert.assertNotNull(item, "Item '"+name+"' with subtext '" + subtext + "' was not found in the list.");
        if (!isSelected(item)) {
            click(item);
            waitItemSelected(item);
        }
        setNonStrictNameEqualityFlag(false);
    }

    public void selectFirstWithNameAndSubtext(String name, String subtext) {
        SenchaWebElement item = getFirstItemWith(name, subtext);
        Assert.assertNotNull(item, "Item '"+name+"' was not found in the list.");
        if (!isSelected(item)) {
            click(item);
            waitItemSelected(item);
        }
    }

    public void selectNext()  {
        SenchaWebElement activeItem = getSelected();
        SenchaWebElement nextItem = getNextItem(activeItem);
        click(nextItem);
        waitItemSelected(nextItem);
    }

//    private int getTranslate3dYValue(SenchaWebElement item) {
//        int yValue = 0;
//        String style = item.getAttribute("style");
//        String regexp = "^.*translate3d\\(0px,\\s*(\\d+)px.*$";
//        Pattern yPattern = Pattern.compile(regexp);
//        Matcher m = yPattern.matcher(style);
//        if (m.matches()) {
//            yValue = Integer.parseInt(m.group(1));
//        }
//        return yValue;
//
//    }

//    private SenchaWebElement getItemNextToSelected(int offset){
//        SenchaWebElement selectedItem = getSelected();
//        int itemIdIndex = ElementHelper.getIdIndex(selectedItem);
//        String itemIdPrefix = ElementHelper.getIdExtPrefix(selectedItem);
//
//        int nextItemIndex = itemIdIndex + offset;
//        String nextItemId = itemIdPrefix + nextItemIndex;
//        String nextItemXpath = new XPathBuilder().byId(nextItemId).build();
//        return getItemByXpath(nextItemXpath);
//    }


    protected void waitItemSelected(SenchaWebElement item){
        addOneTimeCheckpoint(By.xpath(".")).addClassCondition(selectedItemClassName).setParentItem(item);
        waitReady();
    }

    public boolean isSelected(String name)  {
        SenchaWebElement item = getItem(name);
        return isSelected(item);
    }

    public boolean isSelected(String name, String subtext)  {
        SenchaWebElement item = getItem(name, subtext);
        return isSelected(item);
    }

    private boolean isSelected(SenchaWebElement item) {
        return checkListItemProperty(item, selectedItemClassName);
    }

    public boolean isSelectedItemUnavailableInOfflineMode()  {
        SenchaWebElement selectedItem = getSelected();
        return isItemUnavailableInOfflineMode(selectedItem);
    }

    protected SenchaWebElement getSelected()  {
        Assert.assertNotNull(selectedItemClassName, "Active item class name cannot be null.");
        String activeItemXpath = getSelectedItemXpath();
        return getItemByXpath(activeItemXpath);
    }

    public String getSelectedItemName()  {
        return getItemName( getSelected() );
    }

    public void clickSelected()  {
        click(getSelected());
    }

    public SingleSelectListImpl verifySelectedItemName(String expectedName)  {
        assertTrue(isSelected(expectedName), "Wrong item was selected. Expected '"
                + expectedName + "', but found '" + getSelectedItemName() + "'.");
        return this;
    }

    public void verifySelectedItemSubtext(String expectedSubtext)  {
        assertTrue(isSelected(expectedSubtext), "Wrong item was selected. Expected item '" + getSelectedItemName() +
                "with subtext '" + expectedSubtext + "', but found '" + getSelectedItemName() + "with subtext" + getItemSubtext(getSelectedItemName()) + "'.");
    }

    protected String getSelectedItemXpath(){
        Assert.assertNotNull(getItemsXpath(), "No item XPath is specified for the list.");
        Assert.assertNotNull(selectedItemClassName, "No active item class name is specified for the list.");
        return new XPathBuilder().byXpathPart(getItemsXpath()).withNoTranslate3dNegativeByY().withClassName(selectedItemClassName).build();
    }

}
