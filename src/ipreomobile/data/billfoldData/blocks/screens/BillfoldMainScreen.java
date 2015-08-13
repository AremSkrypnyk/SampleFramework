package ipreomobile.data.billfoldData.blocks.screens;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.ScreenCard;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;

public class BillfoldMainScreen extends ScreenCard {

    private static final String LOG_OUT_BUTTON_XPATH = new XPathBuilder().byIdContains("toolsBottomBar").byIdContains("loginStatus").build();

    public BillfoldMainScreen(){
        addCheckpointElement(By.xpath(LOG_OUT_BUTTON_XPATH));
        waitReady();
    }

    public void logout(){
       SenchaWebElement logoutButton = Driver.findVisible(By.xpath(LOG_OUT_BUTTON_XPATH));
        logoutButton.click();
    }
}
