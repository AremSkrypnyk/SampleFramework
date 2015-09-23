package ipreomobile.data;

import java.util.Arrays;
import java.util.List;

public class FundData extends TestDataObject {
    @TestDataField
    private String name;

    @TestDataField
    private String type;

    @TestDataField
    private String location;

    @TestDataField
    private String cityName;

    @TestDataField
    private String managedBy;

    @TestDataField
    private String labels;

    @TestDataField
    private String institutionName;

    @TestDataField
    private String phone;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getManagedBy() {
        return managedBy;
    }

    public void setManagedBy(String managedBy) {
        this.managedBy = managedBy;
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

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
