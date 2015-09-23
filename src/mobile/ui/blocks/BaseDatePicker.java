package ipreomobile.ui.blocks;

import ipreomobile.core.Driver;
import ipreomobile.core.SenchaWebElement;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.SingleSelectListImpl;
import org.openqa.selenium.By;
import org.testng.Assert;

public abstract class BaseDatePicker extends SingleSelectListImpl {

    private static final By DATA_PICKER_CONTAINERS_LOCATOR  = By.xpath(new XPathBuilder().byCurrentItem().byTag("div").withIdContains("ext-pickerslot").build());
    private static final String ITEMS_XPATH                 = new XPathBuilder().byClassName("x-dataview-item").build();
    private static final String ITEM_NAME_XPATH             = new XPathBuilder().byClassName("x-picker-item").build();

    private static final By PICKER_CONTAINER_LOCATOR        = By.xpath(new XPathBuilder().byTag("div").withClassName("date-time-picker").build());

    public BaseDatePicker(){
        setListContainer(getActivePicker());
        setItemsXpath(ITEMS_XPATH);
        setItemNameXpath(ITEM_NAME_XPATH);
        setSelectedItemClassName("x-item-selected");
    }

    protected BaseDatePicker selectData(int controlIndex, String data){
        SenchaWebElement dataPartContainer = Driver.findAll(DATA_PICKER_CONTAINERS_LOCATOR, getActivePicker()).get(controlIndex);
        setListContainer(dataPartContainer);
        select(data);
        return this;
    }

    @Override
    protected void setListScroller(){
        setScrollerById(listContainer.getAttribute("id"));
    }

    @Override
    protected void bringToView(SenchaWebElement item) {
        SenchaWebElement scrollTo = getSelected();
        getScroller().scrollBetweenElements(item, scrollTo);
    }

    @Override
    protected SenchaWebElement getSelected(){
        Assert.assertNotNull(getSelectedItemClassName(), "Active item class name cannot be null.");
        String activeItemXpath = new XPathBuilder().byCurrentItem().byXpathPart(ITEMS_XPATH).withClassName(getSelectedItemClassName()).build();
        return Driver.findOne(By.xpath(activeItemXpath), listContainer);
    }

    protected static SenchaWebElement getActivePicker() {
        return Driver.findVisible(PICKER_CONTAINER_LOCATOR);
    }
}
