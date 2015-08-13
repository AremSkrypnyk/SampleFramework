package ipreomobile.ui.blocks.overlay;

import ipreomobile.core.SenchaWebElement;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.BaseOverlay;
import ipreomobile.templates.ui.MultiSelectListImpl;
import ipreomobile.templates.ui.OverlayController;
import ipreomobile.ui.UITitles;

public abstract class MultiSelectOverlay extends MultiSelectListImpl implements OverlayController {
    private static final String CHECKMARK_CLASS_NAME    = "x-item-selected";
    private static final String CHECKMARK_XPATH         = ".";
    private String maskXpath = new XPathBuilder().byTag("div").withClassName("second-level-mask").build();
    private SearchOverlay overlay;

    public MultiSelectOverlay(UITitles.OverlayType type) {
        overlay = new SearchOverlay(type);

        //setItemsXpath(SearchOverlay.getItemsXpath());
        setItemNameXpath(SearchOverlay.getItemNameXpath());
        SenchaWebElement container = BaseOverlay.getActiveOverlay();
        setListContainer(container);

        setStateTokenSelectedClassName(CHECKMARK_CLASS_NAME);
        setStateTokenXpath(CHECKMARK_XPATH);
        setStateSwitcherXpath(CHECKMARK_XPATH);
    }

    @Override
    public void waitReady(){
        overlay.waitReady();
        super.waitReady();
    }

    public MultiSelectOverlay setSearchFilter(String filter) {
        overlay.setSearchFilter(filter);
        return this;
    }

    public void findAndSelect(String valueToSearch, String... valuesToSelect) {
        overlay.setSearchFilter(valueToSearch);
        check(valuesToSelect);
    }

    public boolean isResultListEmpty() {
        return overlay.isResultListEmpty();
    }

    @Override
    public void close(){
        overlay.close();
    }

//    @Override
//    public void waitClosed() {
//        overlay.waitClosed();
//    }

    public UITitles.OverlayType getOverlayType(){
        return overlay.getType();
    }

    public boolean checkType(UITitles.OverlayType type) {
        return overlay.checkType(type);
    }

}

