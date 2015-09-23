package ipreomobile.ui.search;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.BaseSearchPanel;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.blocks.overlay.ListOverlayFilter;
import ipreomobile.ui.blocks.overlay.ListOverlayFilterWithTabs;
import ipreomobile.ui.blocks.overlay.ListOverlaySearch;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;

public class ContactSearchPanel extends BaseSearchPanel{
    private static final By CITY_NAME_LOCATOR            = By.xpath(new XPathBuilder().byTag("div").withClassName("input-box").withChildText("City Name").byTag("input").withClassName("x-input-search").build());
    private static final By INSTITUTION_NAME_LOCATOR     = By.xpath(new XPathBuilder().byTag("div").withClassName("x-innerhtml").withChildText("Institution Name").build());
    private static final By CONTACT_LOCATION_LOCATOR     = By.xpath(new XPathBuilder().byTag("div").withClassName("x-innerhtml").withChildText("Contact Location").build());
    private static final By PRIMARY_JOB_FUNCTION_LOCATOR = By.xpath(new XPathBuilder().byTag("div").withClassName("x-innerhtml").withChildText("Primary Job Function").build());
    private static final By CONTACT_LIST_LOCATOR         = By.xpath(new XPathBuilder().byTag("div").withClassName("x-innerhtml").withChildText("Contact Lists").build());
    private static final By INST_LIST_LOCATOR            = By.xpath(new XPathBuilder().byTag("div").withClassName("x-innerhtml").withChildText("Institution Lists").build());

    public ContactSearchPanel(){}

    //SearchIn
    public ContactSearchPanel selectSearchIn(UITitles.SearchParams tabName){
        SearchTabControl tabControl = new SearchTabControl(UITitles.SearchType.SEARCH_IN);
        tabControl.selectTab(tabName);
        return this;
    }

    //Side
    public ContactSearchPanel selectSide(UITitles.SearchParams tabName){
        SearchTabControl tabControl = new SearchTabControl(UITitles.SearchType.SIDE);
        tabControl.selectTab(tabName);
        return this;
    }

    public ContactSearchPanel setContactName(String name) {
        setSearchField(name);
        return this;
    }

    public ContactSearchPanel setCityName(String string){
        SenchaWebElement cityName = Driver.findVisible(CITY_NAME_LOCATOR);
        cityName.click();
        cityName.clear();
        cityName.sendKeys(string);
        return this;
    }

    public ListOverlaySearch setInstitutionName(){
        Driver.findVisible(INSTITUTION_NAME_LOCATOR).click();
        return new ListOverlaySearch(UITitles.OverlayType.INSTITUTION_NAME);
    }

    public ContactSearchPanel setInstitutionName(String value){
        Driver.findVisible(INSTITUTION_NAME_LOCATOR).click();
        new ListOverlaySearch(UITitles.OverlayType.INSTITUTION_NAME).findAndSelect(value);
        return this;
    }

    public ListOverlayFilter setContactLocation(){
        Driver.findVisible(CONTACT_LOCATION_LOCATOR).click();
        return new ListOverlayFilter(UITitles.OverlayType.CONTACT_LOCATION);
    }

    public ContactSearchPanel setContactLocation(String value){
        Driver.findVisible(CONTACT_LOCATION_LOCATOR).click();
        new ListOverlayFilter(UITitles.OverlayType.CONTACT_LOCATION).findAndSelect(value);
        return this;
    }

    public ListOverlayFilter setPrimaryJobFunction(){
        Driver.findVisible(PRIMARY_JOB_FUNCTION_LOCATOR).click();
        return new ListOverlayFilter(UITitles.OverlayType.PRIMARY_JOB_FUNCTION);
    }

    public ContactSearchPanel setPrimaryJobFunction(String value){
        Driver.findVisible(PRIMARY_JOB_FUNCTION_LOCATOR).click();
        new ListOverlayFilter(UITitles.OverlayType.PRIMARY_JOB_FUNCTION).findAndSelect(value);
        return this;
    }

    public ListOverlayFilterWithTabs selectContactLists(){
        Driver.findVisible(CONTACT_LIST_LOCATOR).click();
        return new ListOverlayFilterWithTabs(UITitles.OverlayType.CONTACT_LISTS);
    }

    public ContactSearchPanel selectContactLists(UITitles.ListType listType, String listName){
        Driver.findVisible(CONTACT_LIST_LOCATOR).click();
        ListOverlayFilterWithTabs contactListOverlay = new ListOverlayFilterWithTabs(UITitles.OverlayType.CONTACT_LISTS);
        contactListOverlay.selectListType(listType);
        contactListOverlay.findAndSelect(listName);
        return this;
    }

    public ListOverlayFilterWithTabs selectInstitutionLists(){
        Driver.findVisible(INST_LIST_LOCATOR).click();
        return new ListOverlayFilterWithTabs(UITitles.OverlayType.INSTITUTION_LISTS);
    }

    public ContactSearchPanel selectInstitutionLists(UITitles.ListType listType, String listName){
        Driver.findVisible(INST_LIST_LOCATOR).click();
        ListOverlayFilterWithTabs institutionListOverlay = new ListOverlayFilterWithTabs(UITitles.OverlayType.INSTITUTION_LISTS);
        institutionListOverlay.selectListType(listType);
        institutionListOverlay.findAndSelect(listName);
        return this;
    }

    public ContactSearchPanel clearSearchFilter(){
        super.clearSearchFilter();
        return this;
    }

    public ContactSearchPanel reset(){
        super.reset();
        return this;
    }

}
