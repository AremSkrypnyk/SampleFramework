package ipreomobile.ui.profiles.fullProfileTabs;

import ipreomobile.core.Driver;
import ipreomobile.core.Verify;
import ipreomobile.core.XPathBuilder;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

public class BankFocusProfileTab extends BaseFocusProfileTab {
    private static final By OVERVIEW_INFO_LOCATOR = By.xpath(new XPathBuilder().byClassName("investmentApproach").byClassName("info").build());

    public BankFocusProfileTab verifyOverviewSectionPresent(){
        Driver.verifyExactTextPresentAndVisible("Overview");
       SenchaWebElement infoField = Driver.findIfExists(OVERVIEW_INFO_LOCATOR);
        Assert.assertNotNull(infoField, "Overview information is not found on Bank profile, Focus tab");
        Verify.verifyNotEmpty(infoField.getText(), "Overview information is empty on Bank profile, Focus tab");
        return this;
    }
}
