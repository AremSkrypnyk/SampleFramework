package ipreomobile.templates.ui;

import ipreomobile.core.ElementHelper;
import ipreomobile.core.Logger;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

public class ProfileTabList extends SingleSelectListImpl {

    @Override
    public void select(String name) {
        if (BaseOverlay.isActiveMaskPresent()) {
            BaseOverlay.closeActiveOverlay();
        }
        SenchaWebElement item = getItem(name);
        Assert.assertNotNull(item, "Cannot select '" + name + "': no such item exists in the list.");
        click(item);
    }

    @Override
    public void select(String name, String subtext) {
        if (BaseOverlay.isActiveMaskPresent()) {
            BaseOverlay.closeActiveOverlay();
        }
        SenchaWebElement item = getItem(name, subtext);
        Assert.assertNotNull(item, "Cannot select '" + name + "' with subtext '"+subtext+"': no such item exists in the list.");
        click(item);
    }

    public void selectItemInOfflineMode(boolean availableOffline) {
        boolean isUnavailable = !availableOffline;
        SenchaWebElement firstVisibleItem = findFirstVisibleItem();
        if (isItemUnavailableInOfflineMode(firstVisibleItem) == isUnavailable) {
            firstVisibleItem.click();
            return;
        } else {
            SenchaWebElement nextItem;
            SenchaWebElement currentItem = firstVisibleItem;
            while(currentItem.hasNoClass(LAST_LIST_ITEM_CLASS_NAME)){
                nextItem = getNextItem(currentItem);
                if (isItemUnavailableInOfflineMode(nextItem) == isUnavailable) {
                    nextItem.click();
                    return;
                }
                currentItem = nextItem;
            }
        }
        Logger.logWarning(listNamePrefix + "No item available online was found.");
    }
}
