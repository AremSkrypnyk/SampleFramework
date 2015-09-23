package ipreomobile.data;

public class ActivityData extends TestDataObject {

    private static final String ACTIVITY_TYPE_XPATH = ".//span[@class='activity-type']";
    private static final String ACTIVITY_COMPANY_XPATH = ".//span[@class='activity-company']";
    private static final String ACTIVITY_SUBJECT_XPATH = ".//span[@class='activity-subject']";
    private static final String ACTIVITY_DATE_XPATH = ".//span[@class='activity-date-time']";
    private static final String ACTIVITY_TIME_XPATH = ".//span[@class='activity-date-time']";

    @TestDataField
    private String contactName;

    @TestDataField
    private String type;

    @TestDataField
    private String company;

    @TestDataField
    private String subject;

    @TestDataField
    private String startDate;

    @TestDataField
    private String endDate;

    @TestDataField
    private String startTime;

    @TestDataField
    private String endTime;

    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String date) {
        this.startDate = date;
    }

    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getContactName() {
        return contactName;
    }
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }

    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStartDateTime(){
        return startDate + " " + startTime;
    }

    public String getEndDateTime(){
        return endDate + " " + endTime;
    }

    public String getTimeSlot(){
        return startTime + " - " + endTime;
    }

}
