package ipreomobile.ui.activities;

import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.blocks.overlay.ListOverlayFilter;
import ipreomobile.ui.blocks.overlay.ListOverlaySearchMultiSelect;
import org.openqa.selenium.By;

/**
 * Created by Artem_Skrypnyk on 7/31/2014.
 */
public class TaskDetails extends ActivityDetails{

    private static final By ASSIGNED_TO_FIELD_LOCATOR = By.xpath(new XPathBuilder().byTag("div").withClassName("activity-field").byTag("span").withClassName("label").withText("Assigned To").build());
    private static final By PRIORITY_FIELD_LOCATOR = By.xpath(new XPathBuilder().byTag("div").withClassName("activity-field").byTag("span").withClassName("label").withText("Priority").build());

    public TaskDetails(){
        super();
    }

    public ListOverlayFilter openPriorityOverlay(){
        clickOnField(PRIORITY_FIELD_LOCATOR);
        return new ListOverlayFilter(UITitles.OverlayType.PRIORITY);
    }

    public ListOverlaySearchMultiSelect openAssignedToOverlay(){
        clickOnField(ASSIGNED_TO_FIELD_LOCATOR);
        return new ListOverlaySearchMultiSelect(UITitles.OverlayType.ASSIGNED_TO);
    }


}
