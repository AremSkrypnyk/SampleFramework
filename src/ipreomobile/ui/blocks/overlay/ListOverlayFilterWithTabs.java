package ipreomobile.ui.blocks.overlay;

import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.BaseOverlay;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.blocks.BaseTabControl;

public class ListOverlayFilterWithTabs extends ListOverlayFilter {

    private static final String TAB_CONTROL_XPATH = new XPathBuilder().byCurrentItem()
            .byClassName("search-field-container")
            .byClassName("x-button-normal").build();
    private BaseTabControl tabControl = new BaseTabControl();

    public ListOverlayFilterWithTabs(UITitles.OverlayType overlayType){
        super(overlayType);
        tabControl.setTabControlXpath(TAB_CONTROL_XPATH);
        tabControl.setTabControlContainer(BaseOverlay.getActiveOverlay());
    }

    public ListOverlayFilterWithTabs selectListType(UITitles.ListType listType){
        tabControl.selectTab(listType);
        waitReady();
        return this;
    }
}
