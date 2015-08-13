package ipreomobile.ui.blocks.overlay;

import ipreomobile.ui.UITitles;
import ipreomobile.core.SenchaWebElement;

public class ListOverlaySearchMultiSelect extends MultiSelectOverlay {

    public ListOverlaySearchMultiSelect(UITitles.OverlayType type) {
        super(type);
    }

    public ListOverlaySearchMultiSelect(UITitles.OverlayType type,SenchaWebElement listContainer) {
        super(type);
        addLoadingIndicatorCheckpoint(listContainer);
        waitReady();
    }

}
