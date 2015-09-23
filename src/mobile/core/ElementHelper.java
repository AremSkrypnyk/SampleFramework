package ipreomobile.core;

import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ElementHelper {

    public static List<String> describe(SenchaWebElement element) {
        return element.inspect();
    }

    public static List<String> describe(List<SenchaWebElement> elements) {
        List<String> details = new ArrayList<>();
        List<String> elementDetails;
        for (int i = 0; i<elements.size(); i++) {
            SenchaWebElement element = elements.get(i);
            details.add("\t#"+i);
            elementDetails = describe(element);
            for (String str : elementDetails) {
                details.add(str);
            }
            details.add("\n");
        }
        return details;
    }

    public static List<String> getTextValues(List<SenchaWebElement> elements) {
        List<String> textContents = new ArrayList<>();
        for (SenchaWebElement el: elements){
            textContents.add(el.getText());
        }
        return textContents;
    }

    public static List<SenchaWebElement> sortElementsByZIndex(List<SenchaWebElement> elements) {
        if (!elements.isEmpty()) {
            Comparator<SenchaWebElement> comparatorByZIndex = (element1, element2) -> element2.getZIndex() - element1.getZIndex();
            Collections.sort(elements, comparatorByZIndex);
        }
        return elements;
    }

    public static SenchaWebElement getTopElement(List<SenchaWebElement> elements) {
        return (elements.isEmpty()) ? null : sortElementsByZIndex(elements).get(0);
    }

    public static SenchaWebElement getTopElement(By locator) {
        List<SenchaWebElement> elements = Driver.findAll(locator);
        return getTopElement(elements);
    }

    public static SenchaWebElement getTopElement(By locator, SenchaWebElement parentItem) {
        List<SenchaWebElement> elements = Driver.findAll(locator, parentItem);
        return getTopElement(elements);
    }

}
