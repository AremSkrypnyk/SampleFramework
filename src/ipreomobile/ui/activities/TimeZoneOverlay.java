package ipreomobile.ui.activities;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.UITitles;
import ipreomobile.ui.blocks.overlay.SingleSelectOverlay;
import org.openqa.selenium.By;

public class TimeZoneOverlay extends SingleSelectOverlay {

    private static final By BACK_BUTTON_SELECTOR = By.xpath(new XPathBuilder().byClassName("x-button").withChildText("Back").build());

    public TimeZoneOverlay(){
        super(UITitles.OverlayType.TIME_ZONE);
        waitReady();
    }

    public TimeZoneOverlay(String name) {
        super(UITitles.OverlayType.TIME_ZONE);
        waitReady();
        select(name);
        back();
    }

    public void back(){
        Driver.findVisible(BACK_BUTTON_SELECTOR).click();
    }

}
