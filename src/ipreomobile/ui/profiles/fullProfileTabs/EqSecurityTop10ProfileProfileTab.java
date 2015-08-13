package ipreomobile.ui.profiles.fullProfileTabs;

import ipreomobile.core.Driver;
import ipreomobile.core.ElementHelper;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.ProfileTab;
import ipreomobile.ui.blocks.Carousel;
import ipreomobile.ui.blocks.EyeButton;
import ipreomobile.ui.blocks.KeyValueTable;
import ipreomobile.ui.funds.FundFullProfile;
import ipreomobile.ui.institutions.InstitutionFullProfile;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;
import org.testng.Assert;

public class EqSecurityTop10ProfileProfileTab extends ProfileTab {

    //TODO: class is not ready

    private EyeButton eye;

    private static final String TOP_10_TABLE_CLASS = "table-name-value";
    private static final String TOP_10_TABLE_HEADER_XPATH = new XPathBuilder().byClassName(TOP_10_TABLE_CLASS)
            .byClassName("header", "even")
            .byClassName("column")
            .build();
    private static final String TOP_10_TABLE_SECTION_CLASS = "eqsecurity-topholders";
    private static final String TOP_10_TABLE_CAROUSEL_XPATH =
            new XPathBuilder().byClassName(TOP_10_TABLE_SECTION_CLASS).build();
    private static final String TOP_10_TABLE_CAROUSEL_BUTTON_XPATH = new XPathBuilder().byTag("span").build();
    private static final String TOP_10_TABLE_CAROUSEL_ACTIVE_BUTTON_CLASS = "x-carousel-indicator-active";

    private static final String TOP_10_HOLDERS_HEADER = "TOP HOLDERS";
    private static final String TOP_10_BUYERS_HEADER = "TOP BUYERS";
    private static final String TOP_10_SELLERS_HEADER = "TOP SELLERS";

    private static final String INSTITUTIONS_SUB_TAB = "Institutions";
    private static final String FUNDS_SUB_TAB = "Funds";

    private static final By VALUE_SUBHEADER_TAB_LOCATOR = By.xpath(new XPathBuilder().byClassName("subheader").byClassName("column").withTextContainsIgnoreCase("value").build());

    private Carousel top10TableCarousel = new Carousel();

    private KeyValueTable top10Table = new KeyValueTable(
            new XPathBuilder().byTag("div").withNoClassName("header").byXpathPart(KeyValueTable.ODD_OR_EVEN_ROW_CLASS_CONDITION).build(),
            new XPathBuilder().byClassName("column").byIndex(1).build(),
            new XPathBuilder().byClassName("column").byIndex(2).build()
    );

    public EqSecurityTop10ProfileProfileTab(){
        top10TableCarousel.setContainerXpath(TOP_10_TABLE_CAROUSEL_XPATH);
        top10TableCarousel.setAnimationTimeout(2);
        if (isContentLoaded())
            top10Table.setContainer(top10TableCarousel.getActiveCarouselItem());
    }

    public EqSecurityTop10ProfileProfileTab selectInstitutionsTab(){
        selectTab(INSTITUTIONS_SUB_TAB);
        top10Table.setContainer(top10TableCarousel.getActiveCarouselItem());
        return this;
    }

    public EqSecurityTop10ProfileProfileTab selectFundsTab(){
        selectTab(FUNDS_SUB_TAB);
        top10Table.setContainer(top10TableCarousel.getActiveCarouselItem());
        return this;
    }

    private void selectTop10Table(String expectedHeader) {
        String currentHeader = getActiveTop10TableHeader();
        if (!currentHeader.startsWith(expectedHeader)) {
            top10TableCarousel.goToPage(0);
            while (!getActiveTop10TableHeader().startsWith(expectedHeader)) {
                if (top10TableCarousel.hasNext()) {
                    top10TableCarousel.goForward();
                } else {
                    throw new Error("Could not reach '" + expectedHeader + "' table using Carousel: no such table found.");
                }
            }
        }
        top10Table.setContainer(top10TableCarousel.getActiveCarouselItem());
    }

    private String getActiveTop10TableHeader(){
        Driver.findFirst(By.className(TOP_10_TABLE_SECTION_CLASS)).bringToView();
        SenchaWebElement container = top10TableCarousel.getActiveCarouselItem();
        SenchaWebElement header = Driver.findVisible(By.xpath(TOP_10_TABLE_HEADER_XPATH), container);
        return (header == null)
                ? ""
                : header.getText().toUpperCase();
    }

    public EqSecurityTop10ProfileProfileTab verifyActiveTop10Table(String expectedTop10Table){
        Assert.assertTrue(getActiveTop10TableHeader().equalsIgnoreCase(expectedTop10Table), "Expect that " + expectedTop10Table + " table is active, but actually " + getActiveTop10TableHeader() + " table is active!");
        return this;
    }

    public InstitutionFullProfile selectInstitutionFromTop10Table(String institutionName){
        selectInstitutionsTab();
        top10Table.clickKeyCell(institutionName);
        return new InstitutionFullProfile();
    }

    public InstitutionFullProfile selectFirstInstitutionFromTop10Table(){
        selectInstitutionsTab();
        top10Table.clickKeyCell(1);
        return new InstitutionFullProfile();
    }

    public String getFirstInstitutionNameFromTop10Table(){
        selectInstitutionsTab();
        return top10Table.getKey(1);
    }

    public FundFullProfile selectFundFromTop10Table(String fundName){
        selectFundsTab();
        top10Table.clickKeyCell(fundName);
        return new FundFullProfile();
    }

    public FundFullProfile selectFirstFundFromTop10Table(){
        selectFundsTab();
        top10Table.clickKeyCell(1);
        return new FundFullProfile();
    }

    public String getFirstFundNameFromTop10Table(){
        selectFundsTab();
        return top10Table.getKey(1);
    }

    public EqSecurityTop10ProfileProfileTab goToTop10HoldersTable(){
        selectTop10Table(TOP_10_HOLDERS_HEADER);
        return this;
    }

    public EqSecurityTop10ProfileProfileTab goToTop10BuyersTable(){
        selectTop10Table(TOP_10_BUYERS_HEADER);
        return this;
    }

    public EqSecurityTop10ProfileProfileTab goToTop10SellersTable(){
        selectTop10Table(TOP_10_SELLERS_HEADER);
        return this;
    }

    public void verifyInstitutionNameInTopHolders(String institutionName) {
        selectInstitutionsTab();
        goToTop10HoldersTable();
        top10Table.verifyKeyPresent(institutionName);
    }

    public void verifyInstitutionNameInTopBuyers(String institutionName) {
        selectInstitutionsTab();
        goToTop10BuyersTable();
        top10Table.verifyKeyPresent(institutionName);
    }

    public void verifyInstitutionNameInTopSellers(String institutionName) {
        selectInstitutionsTab();
        goToTop10SellersTable();
        top10Table.verifyKeyPresent(institutionName);
    }

    public void verifyFundNameInTopHolders(String fundName) {
        selectFundsTab();
        goToTop10HoldersTable();
        top10Table.verifyKeyPresent(fundName);
    }

    public void verifyFundNameInTopBuyers(String fundName) {
        selectFundsTab();
        goToTop10BuyersTable();
        top10Table.verifyKeyPresent(fundName);
    }

    public void verifyFundNameInTopSellers(String fundName) {
        selectFundsTab();
        goToTop10SellersTable();
        top10Table.verifyKeyPresent(fundName);
    }

    public void verifyInstitutionForFundNameInTop10Table(String fundName, String institutionName) {
        top10Table.verifyValue(fundName, institutionName);
    }

    public EqSecurityTop10ProfileProfileTab showSurveillanceData(){
        eye = new EyeButton();
        eye.showSurveillanceData();
        waitReady();
        return this;
    }

    public EqSecurityTop10ProfileProfileTab showPublicData(){
        eye = new EyeButton();
        eye.showPublicData();
        waitReady();
        return this;
    }

    public EqSecurityTop10ProfileProfileTab verifySurveillanceDataIsDisplayed(){
        eye = new EyeButton();
        eye.verifySurveillanceDataShown();
        return this;
    }

    public EqSecurityTop10ProfileProfileTab verifyPublicDataIsDisplayed(){
        eye = new EyeButton();
        eye.verifyPublicDataShown();
        return this;
    }

    private void verifyCurrency() {
        String[] tableHeaders = {
                "TOP HOLDERS",
                "TOP BUYERS",
                "TOP SELLERS",
        };
        String expectedText = "Value (" + System.getProperty("test.currency") +"M)";

        selectInstitutionsTab();
        for (int i = 0; i < tableHeaders.length; i++) {
            String actualText   = Driver.findVisible(VALUE_SUBHEADER_TAB_LOCATOR).getText();
            Assert.assertTrue(expectedText.equalsIgnoreCase(actualText), "Currency value is not as expected. Expected: ["
                    + expectedText + "] Actual: [" + actualText + "]");
        }
    }

    public EqSecurityTop10ProfileProfileTab verifyCurrencyInInInstitutionsTab() {
        selectInstitutionsTab();
        verifyCurrency();
        return this;
    }

    public EqSecurityTop10ProfileProfileTab verifyCurrencyInFundsTab() {
        selectFundsTab();
        verifyCurrency();
        return this;
    }
}
