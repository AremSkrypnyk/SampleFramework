package ipreomobile.templates.ui;

import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.ScreenCard;
import org.openqa.selenium.By;

public abstract class NativeViewScreen extends ScreenCard {

    private String testBrowser = System.getProperty("test.browser");

    public By getElementLocatorByText(String text) {
        By elementLocator = null;
        switch(System.getProperty("test.browser")){
            case "ipad_simulator":
            case "ipad":
                elementLocator = By.name(text + "");
                break;
            case "android_tablet":
            case "android_phone":
            case "chrome":
                elementLocator = By.xpath(new XPathBuilder().byText(text).build());
                break;
        }
        return elementLocator;
    }

    @Override
    public void waitReady() {
        if (this.testBrowser.equalsIgnoreCase("chrome")) {
            super.waitReady();
        }
    }

}
