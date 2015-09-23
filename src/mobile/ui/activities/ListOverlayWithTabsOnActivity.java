package ipreomobile.ui.activities;

import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.BaseOverlay;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.blocks.BaseTabControl;
import ipreomobile.ui.blocks.overlay.ListOverlayFilterWithTabs;
import ipreomobile.ui.blocks.overlay.ListOverlaySearchMultiSelect;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

/**
 * Created by Artem_Skrypnyk on 7/14/2014.
 */
public class ListOverlayWithTabsOnActivity extends ListOverlayFilterWithTabs {

    private UITitles.OverlayType overlayType;
    private static final String TAB_CONTROL_XPATH = new XPathBuilder().byCurrentItem().byClassName("search-field-container").byClassName("x-button-normal").build();
    private BaseTabControl tabControl = new BaseTabControl();
    private static final String OVERLAY_CONTAINER_XPATH = new XPathBuilder().byTag("div").withClassName("x-panel-b1").withIdContains("ExternalParticipantsUserListContent").build();

    public ListOverlayWithTabsOnActivity(UITitles.OverlayType overlayType) {
        super(overlayType);
        tabControl.setTabControlXpath(TAB_CONTROL_XPATH);
        tabControl.setTabControlContainer(BaseOverlay.getActiveOverlay());
        this.overlayType = overlayType;
    }

    @Override
    public void select(String name) {
       SenchaWebElement item = getItem(name);
        Assert.assertNotNull(item, "Item '" + name + "' was not found in the list.");
        click(name);
    }

    public ListOverlaySearchMultiSelect selectList(String name) {
        select(name);
        return new ListOverlaySearchMultiSelect(overlayType);
    }

    public ListOverlayWithTabsOnActivity selectListType(UITitles.ListType listType){
        tabControl.selectTab(listType);
        waitReady();
        return this;
    }
}
