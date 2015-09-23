package ipreomobile.core;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import ipreomobile.ui.VerticalScroller;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SenchaWebElement implements WebElement, Locatable {

    private RemoteWebElement webElement;

    public SenchaWebElement(RemoteWebElement el){
        webElement = el;
    }

    private static final String OPACITY = "opacity";
    private static final String OPACITY_VALUE_FOR_DISABLED_ITEMS = "0.5";
    private static final String STATUS_INDICATOR_XPATH = new XPathBuilder()
            .byIdContains("ext-statusIndicator")
            .withChildTag("div")
            .withClassName("status-indicator-text")
            .build();

    public void click(){
        String browser = System.getProperty("test.browser");
        if (!browser.equals("chrome")) {
            //clickByActions();
            int x = webElement.getCoordinates().onPage().getX();
            int x_offset = webElement.getSize().getWidth() / 2;
            int y = webElement.getCoordinates().onPage().getY();
            int y_offset = webElement.getSize().getHeight() / 2;
            switchToNativeView();
            TouchAction touchAction = new TouchAction((AppiumDriver) Driver.get());
            touchAction.tap(x + x_offset, y + y_offset).perform();
            switchToWebView();
        } else {
            try {
                webElement.click();
            } catch (Exception e) {
                clickByActions();
            }
        }
    }

    private void switchToWebView(){
        switch (System.getProperty("test.browser")) {
            case "ipad_simulator":
            case "iphone_simulator":
            case "ipad":
            case "iphone":
                Set<String> contexts = ((AppiumDriver)Driver.get()).getContextHandles();
                for (String context: contexts)
                    if (context.contains("WEBVIEW")) ((AppiumDriver)Driver.get()).context(context);
                break;
            case "android_tablet":
            case "android_phone":
                Set<String> windows = Driver.get().getWindowHandles();
                for (String window: windows)
                    if (window.contains("WEBVIEW"))  Driver.get().switchTo().window("WEBVIEW");
                break;
            case "chrome":
                break;
            default:
                throw new AssertionError("Unsupported browser: " + System.getProperty("test.browser"));
        }
    }

    private void switchToNativeView(){
        switch (System.getProperty("test.browser")) {
            case "ipad_simulator":
            case "iphone_simulator":
            case "ipad":
            case "iphone":
                Set<String> contexts = ((AppiumDriver)Driver.get()).getContextHandles();
                for (String context: contexts)
                    if (context.contains("NATIVE")) ((AppiumDriver)Driver.get()).context(context);
                break;
            case "android_tablet":
            case "android_phone":
                Set<String> windows = Driver.get().getWindowHandles();
                for (String window: windows)
                    if (window.contains("NATIVE"))  Driver.get().switchTo().window(window);
                break;
            case "chrome":
                break;
            default:
                throw new AssertionError("Unsupported browser: " + System.getProperty("test.browser"));
        }
    }

    @Override
    public void submit() {
        webElement.submit();
    }

    @Override
    public void sendKeys(CharSequence... charSequences) {
        webElement.sendKeys(charSequences);
    }

    @Override
    public void clear() {
        webElement.clear();
    }

    @Override
    public String getTagName() {
        return webElement.getTagName();
    }

    @Override
    public String getAttribute(String s) {
        return webElement.getAttribute(s);
    }

    @Override
    public boolean isSelected() {
        return webElement.isSelected();
    }

    @Override
    public boolean isEnabled() {
        return webElement.isEnabled();
    }

    @Override
    public String getText() {
        return webElement.getText();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return webElement.findElements(by);
    }

    @Override
    public SenchaWebElement findElement(By by) {
        return Driver.findIfExists(by, this);
    }

    @Override
    public boolean isDisplayed() {
        return webElement.isDisplayed();
    }

    @Override
    public Point getLocation() {
        return webElement.getLocation();
    }

    @Override
    public Dimension getSize() {
        return webElement.getSize();
    }

    @Override
    public String getCssValue(String s) {
        return webElement.getCssValue(s);
    }

    private void clickByActions(){
        Actions builder = new Actions(Driver.get());
        builder.click(webElement).perform();
    }

    public void highlight() {
        Driver.executeJS("element = arguments[0];" +
                "original_style = element.getAttribute('style');" +
                "element.setAttribute('style', original_style + \";" +
                "background: yellow; border: 2px solid red;\");" +
                "setTimeout(function(){element.setAttribute('style', original_style);}, 1000);", webElement);
    }

    public void setText(String text) {
        webElement.clear();
        webElement.sendKeys(text);
    }

    public void sendKeys(CharSequence text) {
        String errorMessageForSendKeys = "cannot focus element";
        try{
            webElement.sendKeys(text);
        }
        catch(WebDriverException e){
            if (e.getMessage().contains(errorMessageForSendKeys)){
                Actions action = new Actions(Driver.get());
                action.sendKeys(webElement, text).perform();
            }
        }
    }

    public boolean hasClass(String className) {
        return webElement.getAttribute("class").contains(className);
    }

    public boolean hasNoClass(String className) {
        return !webElement.getAttribute("class").contains(className);
    }

    public boolean isDisabled(){
        return webElement.getCssValue(OPACITY).equals(OPACITY_VALUE_FOR_DISABLED_ITEMS);
    }

    public List<String> inspect() {
        List<String> details = new ArrayList<>();
        details.add("Element description:");
        details.add("\n\tText:_______" + webElement.getText());
        details.add("\n\tClass:______"+webElement.getAttribute("class"));
        details.add("\n\tStyle:______"+webElement.getAttribute("style"));
        details.add("\n\tId:_________"+webElement.getAttribute("id"));
        details.add("\n\tSize:_______"+webElement.getSize());
        details.add("\n\tLocation:___"+webElement.getLocation());
        details.add("\n\tIs visible:_"+webElement.isDisplayed());
        details.add("\n\tz-index:____"+this.getZIndex());
        return details;
    }

    public List<String> getClassNames() {
        String[] classNames = webElement.getAttribute("class").split("\\s+");
        Arrays.sort(classNames);
        return new ArrayList<>(Arrays.asList(classNames));
    }

    public String getScrollableContainerId() {
        String containerId = "";
        SenchaWebElement container = Driver.findFirst(By.xpath("."), this);
        List<String> elementDescription = this.inspect();
        boolean scrollableFound = false;
        while (!scrollableFound) {
            try {
                container = Driver.findFirst(By.xpath(".."), container);
            } catch (NoSuchElementException e) {
                Logger.logDebug("No scrollable container found, element is not scrollable: \n"+elementDescription);
                return null;
            }
            containerId = container.getAttribute("id");
            try {
                Driver.executeJS(String.format(VerticalScroller.GET_SCROLLABLE_COMMAND, containerId));
                scrollableFound = true;
            } catch (WebDriverException e) {}
        }
        return containerId;
    }

    public void bringToView() {
        String containerId = this.getScrollableContainerId();
        if (containerId != null) {
            VerticalScroller scroller = new VerticalScroller(containerId);
            scroller.bringElementIntoView(this);

            SenchaWebElement statusIndicator = Driver.findIfExistsNow(By.xpath(STATUS_INDICATOR_XPATH));
            if (isOverlappedByAnotherElementByY(statusIndicator)) {
                int delta = statusIndicator.getSize().getHeight();
                scroller.scrollBy(delta);
            }
        }
    }

    public void bringToView(SenchaWebElement mask) {
        String containerId = this.getScrollableContainerId();
        if (containerId != null) {
            VerticalScroller scroller = new VerticalScroller(containerId);
            scroller.bringElementIntoView(this);
            if (isOverlappedByAnotherElementByY(mask)) {
                int delta = mask.getSize().getHeight();
                scroller.scrollBy(delta);
                if (isOverlappedByAnotherElementByY(mask) || !scroller.isElementWithinScrollableContainerBounds(this)) {
                    delta = delta*(-2);
                    scroller.scrollBy(delta);
                    if (isOverlappedByAnotherElementByY(mask) || !scroller.isElementWithinScrollableContainerBounds(this)) {
                        String errorMessage = "Scrolling failed: Element is likely overlapped by Mask";
                        errorMessage += "\nElement: " + inspect();
                        errorMessage += "\nMask: " + mask.inspect();
                        Logger.logError(errorMessage);
                    }
                }
            }
        }
    }

    public boolean isOverlappedByAnotherElementByY(SenchaWebElement overlappingElement) {
        boolean isOverlapped = true;
        if (overlappingElement == null || !overlappingElement.isDisplayed() || overlappingElement.getSize().getHeight() == 0) {
            isOverlapped = false;
        } else {
            int backElementTop = webElement.getLocation().getY();
            int backElementBottom = backElementTop + webElement.getSize().getHeight();
            int frontElementTop = overlappingElement.getLocation().getY();
            int frontElementBottom = frontElementTop + overlappingElement.getSize().getHeight();
            if (frontElementBottom <= backElementTop || frontElementTop >= backElementBottom) {
                isOverlapped = false;
            }
        }
        return isOverlapped;
    }

    public int getZIndex() {
        int zIndex = -1;
        String style = webElement.getAttribute("style");
        String regexp = "^.*z-index:\\s*(\\d+).*$";
        Pattern zIndexPattern = Pattern.compile(regexp);
        Matcher m = zIndexPattern.matcher(style);
        if (m.matches()) {
            zIndex = Integer.parseInt(m.group(1));
        }
        return zIndex;
    }

    public int getIdIndex(){
        int index = -1;
        Matcher m = splitElementId();
        if (m.matches()) {
            index = Integer.parseInt(m.group(2));
        } else {
            Logger.logError("No index was found in element id '"+getId()+"'.");
        }
        return index;
    }

    public String getIdExtPrefix(){
        String extPrefix = "";
        Matcher m = splitElementId();
        if (m.matches()) {
            extPrefix = m.group(1);
        } else {
            Logger.logError("No prefix was found in element id '"+getId()+"'.");
        }
        return extPrefix;
    }

    public String getId(){
        return webElement.getAttribute("id");
    }

    public boolean isDetachedFromDom() {
        boolean isDetached = false;
        try {
            webElement.getLocation();
        } catch (StaleElementReferenceException e) {
            isDetached = true;
        }
        return isDetached;
    }

    private Matcher splitElementId(){
        String id = webElement.getAttribute("id");
        String regexp = "^(.*)(\\d)+$";
        Pattern indexPattern = Pattern.compile(regexp);
        return indexPattern.matcher(id);
    }

    @Override
    public Coordinates getCoordinates() {
        return webElement.getCoordinates();
    }
}
