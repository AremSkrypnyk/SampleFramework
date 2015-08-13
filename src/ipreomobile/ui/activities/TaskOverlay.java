package ipreomobile.ui.activities;

import ipreomobile.core.Driver;
import ipreomobile.ui.blocks.overlay.ListOverlaySearchMultiSelect;

/**
 * Created by Artem_Skrypnyk on 7/31/2014.
 */
public class TaskOverlay extends ActivityOverlay {

    public TaskOverlay(){
        super();
        setupActivityDetails();
        setupExternalParticipantsList();
    }

    @Override
    protected void setupActivityDetails(){
        details = new TaskDetails();
    }

    @Override
    protected void setupExternalParticipantsList() {
        externalParticipants = new ExternalParticipantsList();
        externalParticipants.setListContainer(Driver.findVisible(EXTERNAL_PARTICIPANTS_LIST_CONTAINER_LOCATOR));
    }

    public TaskOverlay selectPriority(String priority){
        ((TaskDetails)details).openPriorityOverlay().select(priority);
        return this;
    }

    public TaskOverlay selectAssignedTo(String searchFilter, String... names){
        ListOverlaySearchMultiSelect assignedToOverlay = ((TaskDetails)details).openAssignedToOverlay();
        assignedToOverlay.setSearchFilter(searchFilter);
        assignedToOverlay.check(names);
        assignedToOverlay.close();
        return this;
    }
}
