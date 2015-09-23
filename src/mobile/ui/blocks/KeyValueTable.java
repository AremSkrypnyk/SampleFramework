package ipreomobile.ui.blocks;

import java.util.List;

import ipreomobile.core.*;
import ipreomobile.ui.ScreenCard;
import ipreomobile.ui.VerticalScroller;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriverException;
import org.testng.Assert;

public class KeyValueTable {

    private String headerXpath = new XPathBuilder().byClassName("first", "header").build();
    private String keyColXpath = new XPathBuilder().byClassName("column").byIndex(1).build();
    private String valueColXpath = new XPathBuilder().byClassName("column").byIndex(2).build();
    private String rowXpath;
    private SenchaWebElement container;

    public static String ODD_OR_EVEN_ROW_CLASS_CONDITION = "[contains(@class, 'odd') or contains(@class, 'even')]";

    public KeyValueTable(){}

    public KeyValueTable(String rowXpath) {
        this.rowXpath = rowXpath;
    }

    public KeyValueTable(String rowXpath, String keyColClass) {
        this.rowXpath = rowXpath;
        this.keyColXpath = keyColClass;
    }

    public KeyValueTable(String rowXpath, String keyColClass, String valueColClass) {
        this.rowXpath = rowXpath;
        this.keyColXpath = keyColClass;
        this.valueColXpath = valueColClass;
    }

    public void setHeaderXpath(String headerXpath) {
        this.headerXpath = headerXpath;
    }

    public String getHeader(){
        SenchaWebElement header;
        String headerText = "";
        if (this.container != null) {
            header = Driver.findOne(By.xpath(headerXpath), container);
            header.bringToView();
            headerText = header.getText();
        } else {
            Logger.logError("Cannot fetch table header: no container specified.");
        }
        return headerText;
    }

    public void verifyHeader(String expectedHeader) {
        Verify.verifyEqualsIgnoreCase(getHeader(), expectedHeader, "Table header mismatch: ");
    }

    public void setValueColXpath(String valueColXpath) {
        this.valueColXpath = valueColXpath;
    }

    public void setKeyColXpath(String keyColXpath) {
        this.keyColXpath = keyColXpath;
    }

    public void setRowXpath(String rowXpath) {
        this.rowXpath = rowXpath;
    }

    public void setContainer(SenchaWebElement container) {
        this.container = container;
    }

    public String getKey(int index){
        String value = "";
        SenchaWebElement valueCell = getKeyCell(index);
        if (valueCell == null) {
            Logger.logError("No key was found in row ["+index+"]");
        } else {
            value = valueCell.getText();
        }
        return value;
    }


    public String getValue(String key){
        String value = "";
        SenchaWebElement valueCell = getValueCell(key);
        if (valueCell == null) {
            Logger.logError("No value was found by key '"+key+"'.");
        } else {
            value = valueCell.getText();
        }
        return value;
    }

    public String getValue(int index){
        String value = "";
        SenchaWebElement valueCell = getValueCell(index);
        if (valueCell == null) {
            Logger.logError("No value was found in row ["+index+"].");
        } else {
            value = valueCell.getText();
        }
        return value;
    }

    public boolean verifyKeyPresent(String key) {
        if (getKeyCell(key) == null) {
            Logger.logError("No row was found with key '"+key+"'.");
            return false;
        }
        return true;
    }

    public boolean verifyKeysPresent(Iterable<String> keys){
        boolean result = true;
        for (String key : keys) {
            result = result && verifyKeyPresent(key);
        }
        return result;
    }

    public boolean verifyValuePresent(String key) {
        if (getValueCell(key) == null){
            Logger.logError("No value was found for '"+key+"' row.");
            return false;
        }
        return true;
    }

    public boolean verifyValuesPresent(Iterable<String> keys) {
        boolean result = true;
        for (String key : keys) {
            result = result && verifyValuePresent(key);
        }
        return true;
    }

    public boolean verifyValue(String key, String expectedValue) {
        String actualValue = getValue(key);
        if (!actualValue.equals(expectedValue)) {
            Logger.logError("[" + key + "] value mismatch: expected [" + expectedValue + "], but found [" + actualValue + "].");
            return false;
        }
        return true;
    }

    public void clickKeyCell(String key){
        SenchaWebElement keyCell = getKeyCell(key);
        Assert.assertNotNull(keyCell, "Cannot click '"+key+"': cell not found.");
        click(keyCell);
    }

    public void clickKeyCell(int rowIndex){
        SenchaWebElement keyCell = getKeyCell(rowIndex);
        Assert.assertNotNull(keyCell, "Cannot click key cell in row ["+rowIndex+"]: cell not found.");
        click(keyCell);
    }

    public void clickValueCell(String key){
        SenchaWebElement valueCell = getValueCell(key);
        Assert.assertNotNull(valueCell, "Cannot click value '"+key+"': cell not found.");
        click(valueCell);
    }

    public void clickValueCell(int rowIndex){
        SenchaWebElement valueCell = getValueCell(rowIndex);
        Assert.assertNotNull(valueCell, "Cannot click value cell in row ["+rowIndex+"]: cell not found.");
        click(valueCell);
    }

    private void click(SenchaWebElement cell){
        String statusIndicatorClass = "status-indicator";
        try {
            cell.click();
        } catch (WebDriverException e) {
            if (e.getMessage().contains("Element is not clickable at point")
                    && e.getMessage().contains(statusIndicatorClass)) {
                String containerId = cell.getScrollableContainerId();
                if (containerId != null) {
                    VerticalScroller scroller = new VerticalScroller(containerId);
                    SenchaWebElement updateIndicator = Driver.findVisible(By.className(statusIndicatorClass));
                    scroller.scrollBy(updateIndicator.getSize().getHeight());
                } else {
                    Logger.logWarning("Update indicator is overlapping a control on the page. Page is not scrollable. Waiting until update indicator is gone...");
                    ScreenCard updateIndicatorCard = new ScreenCard();
                    updateIndicatorCard.addCheckpointElement(By.className(statusIndicatorClass))
                            .addVisibilityCondition(false);
                    updateIndicatorCard.setMaxWaitTimeout(120);
                    updateIndicatorCard.waitReady();
                }
                cell.click();
            } else {
                throw e;
            }
        }
    }

    private SenchaWebElement getKeyRow(String key){

        String expectedKeyXpath = new XPathBuilder().byCurrentItem().byXpathPart(keyColXpath).withText(key).build();
        String expectedRowXpath = new XPathBuilder().byXpathPart(rowXpath)
                .byXpathPart("[" + expectedKeyXpath + "]").build();
        return getRowByXpath(expectedRowXpath);
    }

    private SenchaWebElement getKeyRow(int index){
        int rowCount = Driver.findAll(By.xpath(rowXpath), container).size();
        if (rowCount < index) {
            throw new Error("Unable to select row #"+index+": only "+rowCount+" rows available");
        }
        String expectedRowXpath = new XPathBuilder().byXpathPart(rowXpath).byIndex(index).build();
        return getRowByXpath(expectedRowXpath);
    }

    private SenchaWebElement getRowByXpath(String xpath){
        List<SenchaWebElement> matchingRows = Driver.findAllNow(By.xpath(xpath), container);
        SenchaWebElement row;
        if (matchingRows.isEmpty()){
            row = null;
        } else if (matchingRows.size() > 1) {
            String errorMessage = "Key is not unique: more than 1 row was found by XPath ["+xpath+"].\nRow contents:";
            for (int i=0; i<matchingRows.size(); i++) {
                matchingRows.get(i).bringToView();
                errorMessage += "\n#"+i+": "+matchingRows.get(i).getText();
            }
            throw new Error(errorMessage);
        } else {
            row = matchingRows.get(0);
            row.bringToView();
        }
        return row;
    }

    private SenchaWebElement getKeyCell(String key){
        SenchaWebElement row = getKeyRow(key);
        return (row == null) ? null : Driver.findVisible(By.xpath(keyColXpath), row);
    }

    private SenchaWebElement getKeyCell(int index){
        SenchaWebElement row = getKeyRow(index);
        return (row == null) ? null : Driver.findVisible(By.xpath(keyColXpath), row);
    }

    private SenchaWebElement getValueCell(String key){
        SenchaWebElement row = getKeyRow(key);
        return (row == null) ? null : Driver.findVisible(By.xpath(valueColXpath), row);
    }

    private SenchaWebElement getValueCell(int index){
        SenchaWebElement row = getKeyRow(index);
        return (row == null) ? null : Driver.findVisible(By.xpath(valueColXpath), row);
    }

}
