package ipreomobile.data;

public class TaskData extends ActivityData {
    @TestDataField
    private String assignedTo;

    @TestDataField
    private String priority;

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
