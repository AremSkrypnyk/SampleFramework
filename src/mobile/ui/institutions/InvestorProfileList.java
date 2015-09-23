package ipreomobile.ui.institutions;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.QuickProfileList;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;

public class InvestorProfileList extends QuickProfileList {
    private final String LIST_ITEM_NAME_XPATH = new XPathBuilder().byClassName("investor-name").build();
    private final String LIST_ITEM_XPATH    = new XPathBuilder().byClassName(getActiveListClassName()).byClassName("x-list-item").withChildTag("p").withClassName("investor-name").build();
    private final String LIST_TITLE_XPATH   = new XPathBuilder().byClassName("investors-toolbar-title").build();
    private static final By BOTTOM_MASK_BY  = By.xpath(new XPathBuilder().byClassName("investors-carousel").byClassName("x-carousel-indicator").build());

    private static final By CHECKPOINT_SELECTOR = By.className("investor-card");

    private static final String EMPTY_PAGE_MESSAGE = "No Data Available";

    public InvestorProfileList() {
        super();
        setItemsXpath(LIST_ITEM_XPATH);
        setItemNameXpath(LIST_ITEM_NAME_XPATH);
        setListTitleXpath(LIST_TITLE_XPATH);
        setBottomMaskBy(BOTTOM_MASK_BY);
        addCheckpointElement(CHECKPOINT_SELECTOR).mustBeVisible();
        waitReady();
    }

    public boolean isListEmpty(){
        return Driver.isExactTextPresentAndVisible(EMPTY_PAGE_MESSAGE);
    }

    public SenchaWebElement getItem(String name) {
        if (isListEmpty()) {
            throw new Error("[Investor Profile List]: Cannot get investor '"+name+"', as no Investor data is available.");
        }
        return super.getItem(name);
    }

    public SenchaWebElement getItem(String name, String subtext) {
        if (isListEmpty()) {
            throw new Error("[Investor Profile List]: Cannot get investor '"+name+"' with subtitle '"+subtext+"', as no Investor data is available.");
        }
        return super.getItem(name, subtext);
    }

    public String getSelectedItemName() {
        if (isListEmpty()) {
            throw new Error("[Investor Profile List]: Cannot get selected list item name, as no Investor data is available.");
        }
        return super.getSelectedItemName();
    }
}
