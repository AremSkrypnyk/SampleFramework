package ipreomobile.ui.profiles.fullProfileTabs;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.data.ActivityData;
import ipreomobile.data.TaskData;
import ipreomobile.templates.ui.BaseOverlay;
import ipreomobile.templates.ui.ProfileTab;
import ipreomobile.templates.ui.ProfileTabList;
import ipreomobile.ui.activities.ActivityDetailsOverview;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseActionsProfileTab extends ProfileTab {

    private static final String ACTION_ITEM_XPATH = new XPathBuilder().byClassName("recent-items")
            .byClassName("action-item").withChildTag("h1").withText("%s").build();

    private static final By ACTION_ITEM_SUBJECT_LOCATOR = By.xpath(new XPathBuilder().byCurrentItem().byClassName("header").build());
    private static final By ACTION_ITEM_SUBTEXT_LOCATOR = By.xpath(new XPathBuilder().byCurrentItem().byClassName("subheader").build());
    private static final By CHART_LOCATOR               = By.className("chart-container");
    private static final By RECENT_ITEMS_LOCATOR        = By.className("recent-items");

    protected static final Pattern SUBTEXT_PATTERN = Pattern.compile("([\\w\\s]*)\\s+(\\d\\d/\\d\\d/\\d\\d\\d\\d)\\s+(.*)");
    protected static final int ACTION_ITEM_TYPE_INDEX = 1;
    protected static final int ACTION_ITEM_DATE_INDEX = 2;
    protected static final int ACTION_ITEM_TIME_INDEX = 3;

    private RecentActionsList activityList;

    public BaseActionsProfileTab selectRecentTab(){
        selectTab("Recent");
        waitReady();
        return this;
    }
    public BaseActionsProfileTab selectActivitiesTab(){
        selectActionsTab("Activities");
        waitReady();
        return this;
    }

    public BaseActionsProfileTab selectTasksTab(){
        selectActionsTab("Tasks");
        waitReady();
        return this;
    }

    private void selectActionsTab(String tabName) {
        selectTab(tabName);
        activityList = new RecentActionsList();
    }

    public BaseActionsProfileTab closeActionOverlay(){
        closeActiveOverlay();
        return this;
    }
	public BaseActionsProfileTab verifyActionUnavailableInOffline(String actionName){
        Assert.assertTrue(isActionUnavailableInOffline(actionName), "Action '" + actionName +"' is available while expected to be unavailable in offline mode.");
        return this;
    }

    public boolean isActionUnavailableInOffline(String actionName){
        return activityList.isItemUnavailableInOfflineMode(actionName);
    }
    public BaseActionsProfileTab verifyActionsSummaryChartPresent(){
        selectRecentTab();
        Assert.assertTrue(Driver.isElementVisible(CHART_LOCATOR), "Recent Actions tab failure. Expected to find Actions Summary chart:");
        return this;
    }

    public BaseActionsProfileTab verifyRecentActionsBlockPresent(){
        selectRecentTab();
        Assert.assertTrue(Driver.isElementVisible(RECENT_ITEMS_LOCATOR), "Recent Actions tab failure. Expected to find Recent Actions block:");
        return this;
    }

    public ActivityDetailsOverview selectActivity(String activitySubject) {
        return selectAction("Activities", activitySubject);
    }

    public ActivityDetailsOverview selectTask(String taskSubject) {
        return selectAction("Tasks", taskSubject);
    }

    public BaseActionsProfileTab clickActivity(String activitySubject) {
        return clickAction("Activities", activitySubject);
    }

    public BaseActionsProfileTab clickTask(String taskSubject) {
        return clickAction("Tasks", taskSubject);
    }

    private ActivityDetailsOverview selectAction(String type, String actionSubject){
        clickAction(type, actionSubject);
        return new ActivityDetailsOverview();
    }

    private BaseActionsProfileTab clickAction(String type, String actionSubject) {
        selectActionsTab(type);
        activityList.select(actionSubject);
        return this;
    }

    public ActivityData getActivityData(String activitySubject) {
        return getActionData("Activities", activitySubject);
    }

    public TaskData getTaskData(String taskSubject) {
        return (TaskData)getActionData("Tasks", taskSubject);
    }

    private ActivityData getActionData(String type, String actionSubject) {
        selectActionsTab(type);
        return activityList.getActivityData(actionSubject);
    }

    public ActivityData getRecentActivityData(){
        selectTab("Recent");
        ActivityData data = new ActivityData();
        getActionItemDetails("Activity", data);
        return data;
    }

    public TaskData getRecentTaskData(){
        selectTab("Recent");
        TaskData data = new TaskData();
        getActionItemDetails("Task", data);
        return data;
    }

    public BaseActionsProfileTab clickRecentActivity() { return clickRecentAction("Activity"); }

    public BaseActionsProfileTab clickRecentTask() { return clickRecentAction("Task"); }

    public ActivityDetailsOverview openRecentActivity(){
        return openRecentAction("Activity");
    }

    public ActivityDetailsOverview openRecentTask(){
        return openRecentAction("Task");
    }

    protected BaseActionsProfileTab clickRecentAction(String actionType){
        selectTab("Recent");
       SenchaWebElement actionItem = getRecentActionItem(actionType);
        actionItem.click();
        return this;
    }

    public ActivityDetailsOverview clickActivityAvailableOffline(){
        clickActionInOfflineMode("Activities", true);
        return new ActivityDetailsOverview();
    }
    public ActivityDetailsOverview clickTaskAvailableOffline(){
        clickActionInOfflineMode("Tasks", true);
        return new ActivityDetailsOverview();
    }
    public BaseActionsProfileTab clickActivityUnavailableOffline(){
        clickActionInOfflineMode("Activities", false);
        return this;
    }
    public BaseActionsProfileTab clickTaskUnavailableOffline(){
        clickActionInOfflineMode("Tasks", false);
        return this;
    }

    protected BaseActionsProfileTab clickActionInOfflineMode(String actionType, boolean availableOffline){
        selectActionsTab(actionType);
        activityList.selectItemInOfflineMode(availableOffline);
        return this;
    }

    protected ActivityDetailsOverview openRecentAction(String actionType){
        clickRecentAction(actionType);
        return new ActivityDetailsOverview();
    }

    private SenchaWebElement getRecentActionItem(String actionItemType){
        String actionItemXpath = String.format(ACTION_ITEM_XPATH, actionItemType);
        return Driver.findVisible(By.xpath(actionItemXpath));
    }

    private ActivityData getActionItemDetails(String actionItemType, ActivityData data){
        data.clear();

       SenchaWebElement actionItem = getRecentActionItem(actionItemType);

        String subject = Driver.findVisible(ACTION_ITEM_SUBJECT_LOCATOR, actionItem).getText();
        String subtext = Driver.findVisible(ACTION_ITEM_SUBTEXT_LOCATOR, actionItem).getText();
        Matcher m = SUBTEXT_PATTERN.matcher(subtext);
        if (m.matches()) {
            data.setType(m.group(ACTION_ITEM_TYPE_INDEX));
            data.setStartDate(m.group(ACTION_ITEM_DATE_INDEX));
            data.setStartTime(m.group(ACTION_ITEM_TIME_INDEX));
        }
        data.setSubject(subject);
        return data;
    }

    class RecentActionsList extends ProfileTabList {
        private final String ACTIVE_LIST_XPATH = new XPathBuilder().byClassName("action-list").withNoClassName("x-item-hidden").build();
        private final String ITEM_NAME_XPATH = new XPathBuilder().byClassName("header").build();

        private final String ACTION_TYPE_XPATH = new XPathBuilder().byCurrentItem().byClassName("subheader").build();
        private final String ACTION_DETAILS_XPATH = new XPathBuilder().byCurrentItem().byClassName("values").build();

        RecentActionsList(){
           SenchaWebElement activeListContainer = Driver.findVisible(By.xpath(ACTIVE_LIST_XPATH));
            setItemNameXpath(ITEM_NAME_XPATH);
            setSelectedItemClassName("");
            setListContainer(activeListContainer);
        }

        public ActivityData getActivityData(String activitySubject){
            ActivityData data = new ActivityData();
            data.clear();
            data.setSubject(activitySubject);

           SenchaWebElement actionItem = getItem(activitySubject);
            String actionType = Driver.findVisible(By.xpath(ACTION_TYPE_XPATH), actionItem).getText();
            data.setType(actionType);

            String actionDetails = Driver.findVisible(By.xpath(ACTION_DETAILS_XPATH), actionItem).getText();
            Matcher m = SUBTEXT_PATTERN.matcher(actionDetails);
            if (m.matches()) {
                data.setStartDate(m.group(ACTION_ITEM_DATE_INDEX));
                data.setStartTime(m.group(ACTION_ITEM_TIME_INDEX));
            }
            return data;
        }

        @Override
        public void select(String name) {
            super.select(name);
            BaseOverlay.getActiveMask();
        }

    }
}
