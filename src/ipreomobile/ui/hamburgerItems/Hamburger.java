package ipreomobile.ui.hamburgerItems;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.ScreenCard;
import ipreomobile.ui.activities.GroupActivityOverlay;
import ipreomobile.ui.authentication.LoginScreen;
import ipreomobile.ui.blocks.ConfirmationDialog;
import ipreomobile.ui.hamburgerItems.HelpPage;
import ipreomobile.ui.hamburgerItems.SettingsView;
import org.openqa.selenium.By;

public class Hamburger extends ScreenCard {

    private static final By HAMBURGER_CONTAINER_LOCATOR = By.xpath(new XPathBuilder().byClassName("menu-overlay").build());
    private static final By SETTINGS_BUTTON_LOCATOR = By.xpath(new XPathBuilder().byClassName("menu-overlay").byClassName("x-button-normal").withChildText("SETTINGS").build());
    private static final By ADD_ACTIVITY_BUTTON_LOCATOR = By.xpath(new XPathBuilder().byClassName("menu-overlay").byClassName("x-button-normal").withChildText("ADD ACTIVITY").build());
    private static final By HELP_BUTTON_LOCATOR = By.xpath(new XPathBuilder().byClassName("menu-overlay").byClassName("x-button-normal").withChildText("HELP").build());
    private static final By LOGOUT_BUTTON_LOCATOR = By.xpath(new XPathBuilder().byClassName("menu-overlay").byClassName("x-button-normal").withChildText("Logout").build());
    private static final By WIFI_BUTTON_LOCATOR = By.xpath(new XPathBuilder().byClassName("menu-overlay").byTag("div").withClassName("x-button").withChildTag("span").withClassName("offline").build());
    private static final By DEBUG_BUTTON_LOCATOR = By.xpath(new XPathBuilder().byClassName("menu-overlay").byClassName("bug").build());

    private static final String WIFI_PRESSED_CLASSNAME      = "pressed";

    public Hamburger(){
        addOneTimeCheckpoint(HAMBURGER_CONTAINER_LOCATOR);
        waitReady();
    }

    public SettingsView openSettings(){
        Driver.findOne(SETTINGS_BUTTON_LOCATOR).click();
        addOneTimeCheckpoint(SETTINGS_BUTTON_LOCATOR).addVisibilityCondition(false);
        waitReady();
        return new SettingsView();
    }

    public GroupActivityOverlay addActivity(){
        Driver.findOne(ADD_ACTIVITY_BUTTON_LOCATOR).click();
        addOneTimeCheckpoint(ADD_ACTIVITY_BUTTON_LOCATOR).addVisibilityCondition(false);
        waitReady();
        return new GroupActivityOverlay();
    }

    public HelpPage openHelp(){
        Driver.findOne(HELP_BUTTON_LOCATOR).click();
        addOneTimeCheckpoint(HELP_BUTTON_LOCATOR).addVisibilityCondition(false);
        waitReady();
        return new HelpPage();
    }

    public LoginScreen logout(){
        Driver.findOne(LOGOUT_BUTTON_LOCATOR).click();
        ConfirmationDialog dialog = new ConfirmationDialog();
        dialog.clickYes();
        addOneTimeCheckpoint(LOGOUT_BUTTON_LOCATOR).addVisibilityCondition(false);
        waitReady();
        return new LoginScreen();
    }

    public void turnWiFiOn(){
        if(Driver.findOne(WIFI_BUTTON_LOCATOR).getAttribute("class").contains(WIFI_PRESSED_CLASSNAME)){
            Driver.findOne(WIFI_BUTTON_LOCATOR).click();
        }
        addOneTimeCheckpoint(WIFI_BUTTON_LOCATOR).addVisibilityCondition(false);
        waitReady();
    }

    public void turnWiFiOff(){
        if(!Driver.findOne(WIFI_BUTTON_LOCATOR).getAttribute("class").contains(WIFI_PRESSED_CLASSNAME)){
            Driver.findOne(WIFI_BUTTON_LOCATOR).click();
        }
        addOneTimeCheckpoint(WIFI_BUTTON_LOCATOR).addVisibilityCondition(false);
        waitReady();
    }

    public void openDebug(){
        Driver.findOne(DEBUG_BUTTON_LOCATOR).click();
        addOneTimeCheckpoint(DEBUG_BUTTON_LOCATOR).addVisibilityCondition(false);
        waitReady();
    }

}
