package ipreomobile.ui.contacts;

import ipreomobile.core.Driver;
import ipreomobile.core.ElementHelper;
import ipreomobile.core.XPathBuilder;
import ipreomobile.data.ActivityData;
import ipreomobile.templates.ui.QuickProfileList;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;

import java.util.ArrayList;
import java.util.List;

public class ContactActivityList extends QuickProfileList{
    private static final String LIST_ITEM_NAME_XPATH = new XPathBuilder().byClassName("contact-name").build();
    private static final By ACTIVITY_CONTACT_LOCATOR = By.xpath(new XPathBuilder().byClassName("contact-name").build());
    private static final By ACTIVITY_TYPE_LOCATOR    = By.xpath(new XPathBuilder().byClassName("activity-type").build());

    private static final By ACTIVITY_COMPANY_LOCATOR = By.xpath(new XPathBuilder().byClassName("activity-company").build());
    private static final By ACTIVITY_DATE_LOCATOR    = By.xpath(new XPathBuilder().byClassName("activity-date-time").byClassName("activity-date").build());
    private static final By ACTIVITY_TIME_LOCATOR    = By.xpath(new XPathBuilder().byClassName("activity-date-time").byClassName("activity-time").build());
    private static final By LIST_CONTAINER_LOCATOR = By.className("quickview-container");

    private static final String ACTIVITY_SUBJECT_XPATH =  new XPathBuilder().byClassName("activity-subject").build();

    public ContactActivityList() {
        super();
        setItemNameXpath(LIST_ITEM_NAME_XPATH);
        setSubtextXpath(ACTIVITY_SUBJECT_XPATH);
        setListContainer(getListContainer());
        addCheckpointElement(By.xpath(getSelectedItemXpath()));
        waitReady();
    }

    @Override
    public void select(String name) {

    }

    @Override
    protected SenchaWebElement getItemByXpath(String itemXpath){
        List<SenchaWebElement> foundItems = findItems(itemXpath);

        if (foundItems.size() > 1 || foundItems.size() == 1) {
            bringToView(foundItems.get(0));
        }

        return (foundItems.isEmpty()) ? null : foundItems.get(0);
    }


    public ActivityData getSelectedActivityData() {
        String activityName = getSelectedItemName();
        return getActivityData(activityName);
    }

    public ActivityData getActivityData(String name) {
       SenchaWebElement item = getItem(name);
        return getActivityData(item);
    }

    private ActivityData getActivityData(SenchaWebElement item) {
        ActivityData data = new ActivityData();

        data.setSubject(getItemName(item));
        data.setContactName(getActivityParameter(item, ACTIVITY_CONTACT_LOCATOR));
        data.setCompany(getActivityParameter(item, ACTIVITY_COMPANY_LOCATOR));
        data.setStartDate(getActivityParameter(item, ACTIVITY_DATE_LOCATOR));
        data.setStartTime(getActivityParameter(item, ACTIVITY_TIME_LOCATOR));
        data.setType(getActivityParameter(item, ACTIVITY_TYPE_LOCATOR));

        return data;
    }

    private String getActivityParameter(SenchaWebElement item, By selector) {
        List<SenchaWebElement> dataElement = Driver.findAll(selector, item);
        if (dataElement.isEmpty())
            return "";
        else if (dataElement.size() > 1) {
            List<String> errorMessage = new ArrayList<>();
            errorMessage.add("More than 1 activity item property was found by selector'" + selector.toString() + "'.");
            for (SenchaWebElement el : dataElement) {
                errorMessage.addAll(ElementHelper.describe(el));
                throw new Error( String.join("\n", errorMessage) );
            }
        }
        return dataElement.get(0).getText();
    }

    private SenchaWebElement getListContainer(){
        return Driver.findVisible(LIST_CONTAINER_LOCATOR);
    }

}
