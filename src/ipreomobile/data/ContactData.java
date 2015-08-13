package ipreomobile.data;

import java.util.Arrays;
import java.util.List;

public class ContactData extends TestDataObject {
    @TestDataField
    private String name;

    @TestDataField
    private String fullName;

    @TestDataField
    private String companyName;

    @TestDataField
    private String jobFunction;

    @TestDataField
    private String phone;

    @TestDataField
    private String email;

    @TestDataField
    private String location;

    @TestDataField
    private String cityName;

    @TestDataField
    private String side;

    @TestDataField
    private String labels;

    @TestDataField
    private String listName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobFunction() {
        return jobFunction;
    }

    public void setJobFunction(String jobFunction) {
        this.jobFunction = jobFunction;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public List<String> getLabels() {
        List<String> labelsList = Arrays.asList(labels.split(";"));
        for (int entryNumber = 0; entryNumber < labelsList.size(); entryNumber++)
            labelsList.set(entryNumber, String.format(labelsList.get(entryNumber), "(" + System.getProperty("test.currency") + "M)"));
        return labelsList;
    }
    public void setLabels(String labels) {
        this.labels = labels;
    }
}
