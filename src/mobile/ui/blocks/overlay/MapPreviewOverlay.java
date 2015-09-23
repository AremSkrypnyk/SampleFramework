package ipreomobile.ui.blocks.overlay;

import ipreomobile.core.*;
import ipreomobile.templates.ui.BaseOverlay;
import ipreomobile.ui.GlobalNavigation;
import ipreomobile.ui.MapView;
import ipreomobile.ui.UITitles;
import org.openqa.selenium.By;
import org.testng.Assert;

public class MapPreviewOverlay extends BaseOverlay {

    private static final By MAP_LOCATOR         = By.xpath(new XPathBuilder().byCurrentItem().byClassNameEquals("map").build());
    private static final By ADDRESS_LOCATOR     = By.xpath(new XPathBuilder().byCurrentItem().byClassName("address-text").build());
    private static final By TITLE_LOCATOR       = By.xpath(new XPathBuilder().byCurrentItem().byClassName("t1-table-header").build());
    private static final By CONTAINER_LOCATOR   = By.className("profile-map");
    private static final By MAP_MESSAGE_LOCATOR = By.className("map-message");
    private SenchaWebElement mapContainer;

    public MapPreviewOverlay(UITitles.OverlayType addressOverlayType){
        super(addressOverlayType);
        mapContainer = Driver.findVisible(CONTAINER_LOCATOR);
        Logger.logDebugScreenshot("Initialized base overlay details.");
        addCheckpointElement(ADDRESS_LOCATOR)
                .addVisibilityCondition(true)
                .setParentItem(mapContainer);
        Logger.logDebugScreenshot("Waiting for the map and address controls.");
        waitReady();
    }

    public String getAddress(){
        SenchaWebElement addressLine = Driver.findOne(ADDRESS_LOCATOR, mapContainer);
        return (addressLine == null) ? "" : addressLine.getText().trim();
    }

    public String getTitle() {
        return Driver.findVisible(TITLE_LOCATOR, mapContainer).getText().trim();
    }

    public MapPreviewOverlay verifyTitle(String expectedTitle) {
        Assert.assertEquals(getTitle(), expectedTitle, "Map overlay title mismatch: ");
        return this;
    }

    public MapPreviewOverlay verifyAddressPart(String addressPart) {
        String actualAddress = getAddress();
        Verify.verifyContainsText(actualAddress, addressPart, "Address part was not found in the whole address line: ");
        return this;
    }

    public MapPreviewOverlay verifyAddressPresent() {
        String address = getAddress();
        Verify.verifyNotEmpty(address, "Address was not found, or found empty on Map Preview Overlay.");
        return this;
    }

    public MapView openMap(){
        Driver.findOne(ADDRESS_LOCATOR, mapContainer).click();
        new GlobalNavigation().waitApplicationReady();
        return new MapView();
    }

    public String getMapMessage(){
        SenchaWebElement mapMessage = Driver.findVisible(MAP_MESSAGE_LOCATOR, mapContainer);
        return (mapMessage == null) ? "" : mapMessage.getText();
    }

    public MapPreviewOverlay verifyMapMessage(String expectedMessage) {
        Verify.verifyEquals(getMapMessage(), expectedMessage, "Message mismatch on map preview overlay:");
        return this;
    }
}
