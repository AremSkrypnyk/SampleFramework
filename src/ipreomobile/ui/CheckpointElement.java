package ipreomobile.ui;

import ipreomobile.core.Driver;
import ipreomobile.core.ElementHelper;
import ipreomobile.core.Logger;
import ipreomobile.core.SenchaWebElement;
import org.openqa.selenium.By;

import java.util.LinkedList;

public class CheckpointElement {
    protected By locator;
    protected SenchaWebElement parentItem;
    protected LinkedList<Checkpoint> checkpointList = new LinkedList<>();
    private boolean isForceStopElement = false;

    public CheckpointElement(By locator, boolean isPresent) {
        this.locator = locator;
        addPresenceCondition(isPresent);
    }

    public CheckpointElement setParentItem(SenchaWebElement parentItem) {
        this.parentItem = parentItem;
        return this;
    }

    public CheckpointElement makeForceStopElement(){
        isForceStopElement = true;
        return this;
    }

    protected CheckpointElement addPresenceCondition(boolean isPresent){
        Checkpoint c = new CheckpointByPresence(isPresent);
        checkpointList.add(c);
        return this;
    }

    public CheckpointElement addCssCondition(String name, String value) {
        Checkpoint c = new CheckpointByCssValue(name, value);
        checkpointList.add(c);
        return this;
    }

    public CheckpointElement addVisibilityCondition(Boolean isVisible) {
        Checkpoint c = new CheckpointByVisibility(isVisible);
        checkpointList.add(c);
        return this;
    }

    public CheckpointElement mustBeVisible() {
        return addVisibilityCondition(true);
    }

    public CheckpointElement addClassCondition(String className) {
        Checkpoint c = new CheckpointByClass(className, true);
        checkpointList.add(c);
        return this;
    }

    public CheckpointElement addClassCondition(String className, boolean hasClass) {
        Checkpoint c = new CheckpointByClass(className, hasClass);
        checkpointList.add(c);
        return this;
    }

    public CheckpointElement addCountCondition(int count) {
        Checkpoint c = new CheckpointByCount(count);
        checkpointList.add(c);
        return this;
    }

    public CheckpointElement addTextCondition(String expectedText) {
        Checkpoint c = new CheckpointByText(expectedText);
        checkpointList.add(c);
        return this;
    }

    public CheckpointElement addInvisibleOrAbsentCondition(){
        for (Checkpoint previouslyAdded : checkpointList) {
            if (previouslyAdded instanceof CheckpointByPresence) {
                checkpointList.remove(previouslyAdded);
                break;
            }
        }
        Checkpoint c = new CheckpointByInvisibilityOrAbsence();
        checkpointList.add(c);
        return this;
    }

    public boolean check() {
        for (Checkpoint c: checkpointList) {
            c.setLocator(locator);
        }
        Logger.logDebug("\tVerifying CheckpointElement by "+locator.toString());
        if (parentItem != null) {
            Logger.logDebug("\tParent item: "+parentItem.getTagName() + "[" +parentItem.getAttribute("class") + "]" );
        }
        for (Checkpoint c: checkpointList) {
            c.setLocator(locator);
            if (parentItem != null) {
                c.setContainer(parentItem);
            }

            Driver.stopIfServicesAreUnavailable();
            boolean checkpointReached = c.check();
            Logger.logDebug("? " + c.getConditionDescription() + checkpointReached);
            if (!checkpointReached) {
                return false;
            }
        }
        return true;
    }

    public String describe() {
        String message = " * Element ["+ locator.toString() + "] had the following conditions to satisfy: ";
        for (Checkpoint c : checkpointList) {
            message += "\n" + c.describe();
        }
        if (parentItem != null) {
            message += "\n * Parent item description: ";
            message += ElementHelper.describe(parentItem);
        }
        return message;
    }

    public By getLocator(){ return this.locator; }

    public boolean isForceStopElement(){
        return isForceStopElement;
    }
}

abstract class Checkpoint {
    protected By locator;
    protected SenchaWebElement parentItem;
    protected String conditionDescription;

    Checkpoint setLocator(By locator) {
        this.locator = locator;
        return this;
    }

    Checkpoint setContainer(SenchaWebElement parentItem) {
        this.parentItem = parentItem;
        return this;
    }

    abstract boolean check();
    String describe() {
        return "*" + conditionDescription + (check() ? "PASS" : "FAIL");
    };
    String getConditionDescription() {
        return this.conditionDescription;
    };
}

class CheckpointByPresence extends Checkpoint {
    private boolean isPresent;

    public CheckpointByPresence(boolean isPresent) {
        this.isPresent = isPresent;
        conditionDescription = " [isPresent = " + isPresent + "]: ";
    }

    @Override
    public boolean check() {
        SenchaWebElement element = Driver.findIfExistsNow(locator, parentItem);
        return ((element != null) == isPresent);
    }
}

class CheckpointByVisibility extends Checkpoint {
    private boolean isVisible;

    public CheckpointByVisibility(Boolean isVisible) {
        this.isVisible = isVisible;
        conditionDescription = " [isVisible = " + isVisible + "]: ";
    }

    @Override
    public boolean check() {
        return ( Driver.isElementVisible(locator, parentItem) ) == isVisible;
    }

}

class CheckpointByClass extends Checkpoint {
    private String className;
    private boolean hasClass;

    public CheckpointByClass(String className, boolean hasClass) {
        this.className = className;
        this.hasClass = hasClass;
        conditionDescription = " [has"+ (hasClass ? " " : " no ") + "CSS class name '" + className + "']: ";
    }

    @Override
    public boolean check() {
        return Driver.findFirstNow(locator, parentItem).getAttribute("class").contains(className) == hasClass;
    }

}

class CheckpointByCssValue extends Checkpoint {
    private String cssPropertyName;
    private String cssPropertyValue;

    public CheckpointByCssValue(String name, String value) {
        cssPropertyName = name;
        cssPropertyValue = value;
        conditionDescription = " [style: '" + cssPropertyName + "'='" + cssPropertyValue + "']: ";
    }

    @Override
    public boolean check() {
        return Driver.findFirstNow(locator, parentItem).getCssValue(cssPropertyName).equals(cssPropertyValue);
    }

}

class CheckpointByCount extends Checkpoint {
    private int expectedCount;

    public CheckpointByCount(int expectedCount) {
        this.expectedCount = expectedCount;
        conditionDescription = " [number of elements = " + expectedCount + "]: ";
    }

    @Override
    public boolean check() {
        return Driver.findAllNow(locator, parentItem).size() == expectedCount;
    }

}

class CheckpointByInvisibilityOrAbsence extends Checkpoint {
    public CheckpointByInvisibilityOrAbsence() {
        conditionDescription = " [must be invisible or absent on the page]: ";
    }

    @Override
    public boolean check() {
        return !Driver.isElementVisible(locator, parentItem);
    }

}

class CheckpointByText extends Checkpoint {
    private String text;
    public CheckpointByText(String expectedText) {
        this.text = expectedText;
        conditionDescription = " [expected text = '" + expectedText + "']: ";
    }

    @Override
    public boolean check() {
        return Driver.findVisibleNow(locator, parentItem).getText().trim().equals(text);
    }

    @Override
    public String describe() {
        return "*" + conditionDescription + ((check()) ? "PASS" : "FAIL: found '"+Driver.findVisible(locator, parentItem).getText().trim().equals(text)+"' instead.");
    }
}