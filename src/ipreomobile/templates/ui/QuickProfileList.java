package ipreomobile.templates.ui;


import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import org.openqa.selenium.By;

public abstract class QuickProfileList extends SingleMultiSelectListImpl {

    private String activeListClassName = "active-qpl";

    private String listTitleXpath = new XPathBuilder().byClassName(activeListClassName).byClassName("x-title").build();

    public void setListTitleXpath(String listTitleXpath) {
        this.listTitleXpath = listTitleXpath;
    }

    public String getActiveListClassName() {
        return activeListClassName;
    }

    public void setActiveListClassName(String activeListClassName) {
        this.activeListClassName = activeListClassName;
    }

    public String getListTitle(){
        return Driver.findVisible(By.xpath(listTitleXpath)).getText();
    }

    protected void addContainerCheckpoint(){
        addCheckpointElement(By.className(activeListClassName));
    }




}
