package ipreomobile.ui.profiles.fullProfileTabs;

import ipreomobile.core.Driver;
import ipreomobile.core.Logger;
import ipreomobile.core.XPathBuilder;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;

public class EqSecurityDetailsProfileTab extends BaseDetailsProfileTab {

    public void verifyPriceChartPresent() {
        String chartBlockXpath = new XPathBuilder().byClassName("chart").build();
       SenchaWebElement chartBlock = Driver.findVisible(By.xpath(chartBlockXpath));
        if (chartBlock == null) {
            Logger.logError("No price chart was found on Details tab.");
        } else {
            String lastUpdatedInfoXpath = new XPathBuilder().byCurrentItem().byClassName("last-updated").build();
           SenchaWebElement lastUpdatedInfo = Driver.findVisible(By.xpath(lastUpdatedInfoXpath));
            if (lastUpdatedInfo == null) {
                Logger.logError("No last updated title was found for price chart.");
            }
        }
    }

    public void verifyLastUpdatedTimeForPriceChart(String lastUpdatedDateTime){
        String lastUpdatedInfoXpath = new XPathBuilder().byCurrentItem().byClassName("last-updated").build();
       SenchaWebElement lastUpdatedInfo = Driver.findVisible(By.xpath(lastUpdatedInfoXpath));
        if (lastUpdatedInfo == null) {
            Logger.logError("No last updated title was found for price chart.");
        } else {
            if (lastUpdatedInfo.getText().equalsIgnoreCase("Last updated on: " + lastUpdatedDateTime))
                Logger.logError("Last updated date mismatch.");
            else Logger.logMessage("Last updated date matches with expected.");
        }
    }

    
}
