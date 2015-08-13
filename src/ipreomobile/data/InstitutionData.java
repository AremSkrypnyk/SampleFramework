package ipreomobile.data;

import org.testng.Assert;

import java.util.Arrays;
import java.util.List;

public class InstitutionData extends TestDataObject {

    @TestDataField
    private String name;

    @TestDataField
    private String primaryContact;

    @TestDataField
    private String side;

    @TestDataField
    private String phone;

    @TestDataField
    private String website;

    @TestDataField
    private String location;

    @TestDataField
    private String listName;

    @TestDataField
    private String labels;

    @TestDataField
    private String type;


    public InstitutionData() { super();}

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    public String getPrimaryContact() {
        return primaryContact;
    }
    public void setPrimaryContact(String primaryContact) {
        this.primaryContact = primaryContact;
    }

    public String getSide() {
        return side;
    }
    public void setSide(String side) {
        this.side = side;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }
    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String city) {
        this.location = city;
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
        Assert.assertNotNull(labelsList, "No label set was provided for the given test case.");
        return labelsList;
    }
    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
