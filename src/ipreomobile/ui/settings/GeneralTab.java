package ipreomobile.ui.settings;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.ScreenCard;
import ipreomobile.ui.authentication.PinScreen;
import ipreomobile.ui.blocks.KeyValueTable;
import org.openqa.selenium.By;
import org.testng.Assert;

public class GeneralTab extends ScreenCard {
    private static final By FORM_FIELDSET_LOCATOR = By.className("x-form-fieldset-inner");
    private KeyValueTable productInfo = new KeyValueTable(
            new XPathBuilder().byClassName("input-box", "x-panel").build(),
            new XPathBuilder().byClassName("label").byClassName("x-innerhtml").build(),
            new XPathBuilder().byClassName("value").byClassName("x-innerhtml").build()
    );

    public GeneralTab() {
        addCheckpointElement(FORM_FIELDSET_LOCATOR);
        waitReady();
    }

    public GeneralTab verifyProductName(String productName) {
        productInfo.verifyKeyPresent("Product");
        productInfo.verifyValue("Product", productName);
        return this;
    }

    public GeneralTab verifyVersion() {
        String actualVersion, expectedVersion;
        productInfo.verifyKeyPresent("Version");
        actualVersion = productInfo.getValue("Version");
        expectedVersion = System.getProperty("test.version");
        Assert.assertEquals(actualVersion, expectedVersion, "Version displayed on General settings tab (actual) is different from the one on Pin screen (expected).");
        return this;
    }

    public GeneralTab verifyVersion(String expectedVersion) {
        productInfo.verifyKeyPresent("Version");
        productInfo.verifyValue("Version", expectedVersion);
        return this;
    }

    public GeneralTab verifyServer(String expectedServer) {
        productInfo.verifyKeyPresent("Server");
        productInfo.verifyValue("Server", expectedServer);
        return this;
    }

}
