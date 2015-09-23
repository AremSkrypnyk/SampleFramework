package ipreomobile.test.settings;

import ipreomobile.core.NumberFormatHelper;
import ipreomobile.data.*;
import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.profiles.overviewCards.*;
import ipreomobile.ui.securities.SecuritySearchTab;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Locale;

public class NumberFormatChangeInSettings extends BaseTest {
    // TODO: tests for full profiles
    private static final String GERMAN_NUMBER_FORMAT_EXAMPLE_STRING = "100.000,23";
    private InstitutionData institutionData;
    private ContactData contactData;
    private FundData fundData;
    private EquityData equityData;
    private FixedIncomeData fixedIncomeData;
    private String institutionName;
    private String contactName;
    private String fundName;
    private String equitySecurityName;
    private String equitySecurityMarketName;
    private String fixedIncomeCouponName;
    private String currency;
    private Locale locale;
    private String keyToVerify;

    @BeforeMethod
    public void setUpDataAndChangeNumberFormat() {
        currency = System.getProperty("test.currency");
        institutionData = new InstitutionData();
        institutionName = institutionData.getName();
        contactData = new ContactData();
        contactName = contactData.getName();
        fundData = new FundData();
        fundName = fundData.getName();
        equityData = new EquityData();
        equitySecurityName = equityData.getSecurityName();
        equitySecurityMarketName = equityData.getSecurityMarketName();
        fixedIncomeData = new FixedIncomeData();
        fixedIncomeCouponName = fixedIncomeData.getCouponName();

        navigation
                .openHamburger()
                .openSettings()
                .openGlobalizationTab()
                .setNumberFormat(GERMAN_NUMBER_FORMAT_EXAMPLE_STRING);
        locale = Locale.GERMANY;
    }

    @Test
    public void verifyNumberFormatOnInstitutionProfileOverview() {
        NonOwnerInstitutionalHoldingsCard nonOwnerInstitutionalHoldingsCard;
        keyToVerify = "Equity Assets (" + currency + ")";

        navigation
                .searchInstitutions(institutionName)
                .openProfileOverview(institutionName);
        nonOwnerInstitutionalHoldingsCard = new NonOwnerInstitutionalHoldingsCard();

        String nonOwnershipAssetValue = nonOwnerInstitutionalHoldingsCard.getValue(keyToVerify);

        verifyValueDisplayedInProperNumberFormat(nonOwnershipAssetValue);
    }

    @Test
    public void verifyNumberFormatOnContactProfileOverview() {
        ContactHoldingsCard contactHoldingsCard;
        NonOwnerInstitutionalHoldingsCard nonOwnerInstitutionalHoldingsCard;
        keyToVerify = "Equity Assets (" + currency + ")";

        navigation
                .searchContacts(contactName)
                .openProfileOverview(contactName);
        contactHoldingsCard = new ContactHoldingsCard();
        nonOwnerInstitutionalHoldingsCard = new NonOwnerInstitutionalHoldingsCard();

        String contactHoldingAssetValue = contactHoldingsCard.getValue(keyToVerify);
        String nonOwnershipAssetValue = nonOwnerInstitutionalHoldingsCard.getValue(keyToVerify);

        verifyValueDisplayedInProperNumberFormat(contactHoldingAssetValue);
        verifyValueDisplayedInProperNumberFormat(nonOwnershipAssetValue);
    }

    @Test
    public void numberFormatCheckOnFundProfileOverview() {
        NonOwnerFundHoldingsCard nonOwnerFundHoldingsCard;
        keyToVerify = "Equity Assets (" + currency + ")";

        navigation
                .searchFunds(fundName)
                .openProfileOverview(fundName);
        nonOwnerFundHoldingsCard = new NonOwnerFundHoldingsCard();

        String fundHoldingAssetValue = nonOwnerFundHoldingsCard.getValue(keyToVerify);

        verifyValueDisplayedInProperNumberFormat(fundHoldingAssetValue);
    }

    @Test
    public void verifyNumberFormatOnEquityProfileOverview() {
        SharesCard sharesCard;

        navigation
                .searchSecurities(equitySecurityName)
                .openProfileOverview(equitySecurityName, equitySecurityMarketName);
        sharesCard = new SharesCard();
        String institutionalShares = sharesCard.getInstitutionalShares();

        verifyValueDisplayedInProperNumberFormat(institutionalShares);
    }

    @Test
    public void verifyNumberFormatOnFixedIncomeProfileOverview() {
        // TODO: top holders card on fixed income profile overview
        SummaryCard summaryCard;

        navigation
                .searchSecurities()
                .searchFixedIncomes()
                .setTickerOrSecurityName(fixedIncomeCouponName)
                .search();
        SecuritySearchTab searchTab = new SecuritySearchTab();
        searchTab.openProfileOverview(fixedIncomeCouponName);
        summaryCard = new SummaryCard();

        String totalParHeld = summaryCard.getTotalParHeld();

        verifyValueDisplayedInProperNumberFormat(totalParHeld);
    }

    private void verifyValueDisplayedInProperNumberFormat(String valueText) {
        NumberFormatHelper.verifyNumberFormat(valueText.replaceAll("M", ""), locale);
    }
}
