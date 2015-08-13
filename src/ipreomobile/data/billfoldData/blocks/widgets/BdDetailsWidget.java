package ipreomobile.data.billfoldData.blocks.widgets;

import ipreomobile.core.Driver;
import ipreomobile.core.XPathBuilder;
import ipreomobile.ui.ScreenCard;
import org.openqa.selenium.By;

public class BdDetailsWidget extends ScreenCard {

    private static final String BD_DETAILS_WIDGET_TITLE = new XPathBuilder().byIdContains("ucPrimaryInfo_tio").build();

    private static final String LOCATION_XPATH = new XPathBuilder().byIdContains("_trDetails").byClassName("profileCityCountry").build();
    private static final String ADDRESS_XPATH = new XPathBuilder().byIdContains("_trDetails").byTag("tr").byTag("tr").withChildTag("td")
            .withClassName("fieldLabel").withTextContains("Address").byTag("td").withClassName("fieldValue").byIndex(1).build();

    private static final String PHONE_XPATH = new XPathBuilder().byIdContains("_trDetails").byTag("tr").byTag("tr").withChildTag("td")
            .withClassName("fieldLabel").withTextContains("Address").byTag("td").withClassName("fieldValue").byIndex(2).build();

    private static final String WEBSITE_XPATH = new XPathBuilder().byIdContains("_hlWebsite").build();
    private static final String FAX_XPATH = new XPathBuilder().byIdContains("_trDetails").byTag("tr").byTag("tr").withChildTag("td")
            .withClassName("fieldLabel").withTextContains("Fax").byTag("td").withClassName("fieldValue").byIndex(2).build();

    private static final String ASSET_CLASS_XPATH = new XPathBuilder().byIdContains("_trDetails").byTag("tr").byTag("tr").withChildTag("td")
            .withClassName("fieldLabel").withTextContains("Asset Class").byTag("td").withClassName("fieldValue").byIndex(1).build();

    private static final String LEFT_OWNERSHIP_TABLE_XPATH = new XPathBuilder().byClassName("ownership-summary").byTag("td").byIndex(1).byTag("tr")
            .withChildTag("td").withClassName("fieldLabel").withTextContains("%s").byTag("td").withClassName("fieldValue").build();

    private static final String RIGHT_OWNERSHIP_TABLE_XPATH = new XPathBuilder().byClassName("ownership-summary").byTag("td").byIndex(2).byTag("tr")
            .withChildTag("td").withClassName("fieldLabel").withTextContains("%s").byTag("td").withClassName("fieldValue").build();

    private static final String INSTITUTION_TYPE = "Institution Type";
    private static final String PRIMARY_EQUITY_STYLE = "Primary Equity Style";
    private static final String DOMINANT_ORIENTATION = "Dominant Orientation";
    private static final String EQUITY_PORTFOLIO_TURNOVER = "Equity Portfolio Turnover";
    private static final String FI_PORTFOLIO_TURNOVER = "FI Portfolio Turnover";
    private static final String DERIVATIVE_STRATEGIES = "Derivative Strategies";

    private static final String REPORTED_TOTAL_ASSETS = "Reported Total Assets";
    private static final String REPORTED_EQUITY_ASSETS = "Reported Equity Assets";
    private static final String REPORTED_FIXED_INCOME_ASSETS = "Reported Fixed Income Assets";
    private static final String REPORTED_CASH = "Reported Cash";
    private static final String REPORTED_OTHER = "Reported Other";

    public BdDetailsWidget(){
        addCheckpointElement(By.xpath(BD_DETAILS_WIDGET_TITLE));
        waitReady();
    }


    public String getLocation(){
        return Driver.findVisible(By.xpath(LOCATION_XPATH)).getText().trim();
    }

    public String getAddress(){
        return Driver.findVisible(By.xpath(ADDRESS_XPATH)).getText().trim();
    }

    public String getWebsite(){
        return Driver.findVisible(By.xpath(WEBSITE_XPATH)).getText().trim();
    }

    public String getAssetClass(){
        return Driver.findVisible(By.xpath(ASSET_CLASS_XPATH)).getText().trim();
    }

    public String getPhone(){
        return Driver.findVisible(By.xpath(PHONE_XPATH)).getText().trim();
    }

    public String getFax(){
        return Driver.findVisible(By.xpath(FAX_XPATH)).getText().trim();
    }

    public String getInstitutionType(){
        return Driver.findVisible(By.xpath(String.format(LEFT_OWNERSHIP_TABLE_XPATH, INSTITUTION_TYPE))).getText().trim();
    }

    public String getPrimaryEquityStyle(){
        return Driver.findVisible(By.xpath(String.format(LEFT_OWNERSHIP_TABLE_XPATH, PRIMARY_EQUITY_STYLE))).getText().trim();
    }

    public String getDominantOrientation(){
        return Driver.findVisible(By.xpath(String.format(LEFT_OWNERSHIP_TABLE_XPATH, DOMINANT_ORIENTATION))).getText().trim();
    }

    public String getEquityPortfolioTurnover(){
        return Driver.findVisible(By.xpath(String.format(LEFT_OWNERSHIP_TABLE_XPATH, EQUITY_PORTFOLIO_TURNOVER))).getText().trim();
    }

    public String getFiPortfolioTurnover(){
        return Driver.findVisible(By.xpath(String.format(LEFT_OWNERSHIP_TABLE_XPATH, FI_PORTFOLIO_TURNOVER))).getText().trim();
    }

    public String getDerivativeStrategies(){
        return Driver.findVisible(By.xpath(String.format(LEFT_OWNERSHIP_TABLE_XPATH, DERIVATIVE_STRATEGIES))).getText().trim();
    }

    public String getReportedTotalAssets(){
        return Driver.findVisible(By.xpath(String.format(RIGHT_OWNERSHIP_TABLE_XPATH, REPORTED_TOTAL_ASSETS))).getText().trim();
    }

    public String getReportedEquityAssets(){
        return Driver.findVisible(By.xpath(String.format(RIGHT_OWNERSHIP_TABLE_XPATH, REPORTED_EQUITY_ASSETS))).getText().trim();
    }

    public String getReportedFixedIncomeAssets(){
        return Driver.findVisible(By.xpath(String.format(RIGHT_OWNERSHIP_TABLE_XPATH, REPORTED_FIXED_INCOME_ASSETS))).getText().trim();
    }

    public String getReportedCash(){
        return Driver.findVisible(By.xpath(String.format(RIGHT_OWNERSHIP_TABLE_XPATH, REPORTED_CASH))).getText().trim();
    }

    public String getReportedOther(){
        return Driver.findVisible(By.xpath(String.format(RIGHT_OWNERSHIP_TABLE_XPATH, REPORTED_OTHER))).getText().trim();
    }
}
