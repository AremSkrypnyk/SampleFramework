package ipreomobile.templates.test;

import ipreomobile.core.*;
import ipreomobile.ui.GlobalNavigation;
import io.appium.java_client.AppiumDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import ipreomobile.actions.Login;

import java.util.Set;

@Listeners(TestListener.class)
public class BaseTest extends StartupTest {

    protected GlobalNavigation navigation;

    @BeforeMethod
    public void testCaseSetup(){
        long startNanos = System.nanoTime();
        super.testCaseSetup();
        Driver.pause(3000);
        switchToWebView();
        Login login = new Login();

        navigation = new GlobalNavigation();
        long endNanos = System.nanoTime();
        Logger.logSuccess(
                "Logged in as [" + login.getUsername() + "]. "
                        + "\n\tStartup took [" + StringHelper.durationToTimeStr((endNanos - startNanos) / 1000000) + "]. "
                        + "\n\tProduct:     [" + System.getProperty("test.product") + "]."
                        + "\n\tEnvironment: ["+ System.getProperty("test.env")+"]."
                        + "\n\tVersion:     [" + System.getProperty("test.version") + "]."
        );
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


}
