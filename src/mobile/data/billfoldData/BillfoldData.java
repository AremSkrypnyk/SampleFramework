package ipreomobile.data.billfoldData;

import ipreomobile.core.Driver;
import ipreomobile.core.Logger;
import ipreomobile.data.billfoldData.blocks.screens.BillfoldMainScreen;
import ipreomobile.data.billfoldData.blocks.screens.BillfoldLoginScreen;
import ipreomobile.data.billfoldData.blocks.BillfoldInstitutionProfile;
import ipreomobile.data.billfoldData.blocks.tables.InstitutionHoldingsTable;
import ipreomobile.data.billfoldData.blocks.tables.tableRows.*;
import ipreomobile.templates.test.EmptyTest;
import ipreomobile.ui.UITitles;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//@Listeners(TestListener.class)
public class BillfoldData extends EmptyTest {

    String productLink;

    public BillfoldData(){

    }

    BillfoldLoginScreen login;
    BillfoldMainScreen mainScreen;

    @BeforeMethod
    public void testCaseSetup(){
        super.testCaseSetup();
        productLink = System.getProperty("test.env").equals("qx") ? "https://irm.qx.ipreo.com/" : "https://irm.demo.ipreo.com/";
        Driver.goTo(productLink);
        Driver.setTimeout(15);

        login = new BillfoldLoginScreen();
    }

    @Test
    public void institutionData(){
        List<String> demoProfilesLinks = new ArrayList<>();
        //profilesLinks.add("https://irm.demo.ipreo.com/UI/Profile/Institution/Default.aspx?ProfileId=606617|&master_page=0");
        //profilesLinks.add("https://irm.demo.ipreo.com/UI/Profile/Institution/Default.aspx?ProfileId=605277|&master_page=1");
        demoProfilesLinks.add("https://irm.demo.ipreo.com/UI/Profile/Institution/Default.aspx?ProfileId=599730|&master_page=1");
        //profilesLinks.add("https://irm.demo.ipreo.com/UI/Profile/Institution/Default.aspx?ProfileId=599971|&master_page=1");
       //profilesLinks.add("https://irm.demo.ipreo.com/UI/Profile/Institution/Default.aspx?ProfileId=618089|&master_page=1");

        List<String> qxProfilesLinks = new ArrayList<>();
        qxProfilesLinks.add("https://irm.qx.ipreo.com/UI/Profile/Institution/Default.aspx?ProfileId=605277|&master_page=1");
//        qxProfilesLinks.add("https://irm.qx.ipreo.com/UI/Profile/Institution/Default.aspx?ProfileId=599730|&master_page=1");
//        qxProfilesLinks.add("https://irm.qx.ipreo.com/UI/Profile/Institution/Default.aspx?ProfileId=606617|&master_page=1");
//        qxProfilesLinks.add("https://irm.qx.ipreo.com/UI/Profile/Institution/Default.aspx?ProfileId=599971|&master_page=1");
//        qxProfilesLinks.add("https://irm.qx.ipreo.com/UI/Profile/Institution/Default.aspx?ProfileId=618089|&master_page=1");

        Map<String, InstitutionData> institutionsData = new HashMap<>();

        List<String> profilesLinks = System.getProperty("test.env").equals("qx") ? qxProfilesLinks : demoProfilesLinks;

        for (String profileLink : profilesLinks){
            Driver.goTo(profileLink);

            BillfoldInstitutionProfile profile = new BillfoldInstitutionProfile();

            InstitutionData data = new InstitutionData();

            Logger.logMessage("Name : " + profile.getName());
            data.detailsData.setName(profile.getName());
            Logger.logMessage("Side : " + profile.getSide());
            data.detailsData.setSide(profile.getSide());
            Logger.logMessage("Last Activity : " + profile.getLastActionDate());
            data.detailsData.setLastActivity(profile.getLastActionDate());
            Logger.logMessage("Primary Contacts : " + profile.getPrimaryContacts());
            data.detailsData.setPrimaryContacts(profile.getPrimaryContacts());
            Logger.logMessage("Location : " + profile.getBdDetailsWidget().getLocation());
            data.detailsData.setLocation(profile.getBdDetailsWidget().getLocation());
            Logger.logMessage("Address : " + profile.getBdDetailsWidget().getAddress());
            data.detailsData.setAddress(profile.getBdDetailsWidget().getAddress());
            Logger.logMessage("Website : " + profile.getBdDetailsWidget().getWebsite());
            data.detailsData.setWebsite(profile.getBdDetailsWidget().getWebsite());
            Logger.logMessage("Asset Class : " + profile.getBdDetailsWidget().getAssetClass());
            data.detailsData.setAssetClass(profile.getBdDetailsWidget().getAssetClass());
            Logger.logMessage("Phone : "+profile.getBdDetailsWidget().getPhone());
            data.detailsData.setPhone(profile.getBdDetailsWidget().getPhone());
            Logger.logMessage("Fax : " + profile.getBdDetailsWidget().getFax());
            data.detailsData.setFax(profile.getBdDetailsWidget().getFax());

            Logger.logMessage("Institution Type : "+profile.getBdDetailsWidget().getInstitutionType());
            data.detailsData.setInstitutionType(profile.getBdDetailsWidget().getInstitutionType());
            Logger.logMessage("Primary Equity Style : "+profile.getBdDetailsWidget().getPrimaryEquityStyle());
            data.detailsData.setPrimaryEquityStyle(profile.getBdDetailsWidget().getPrimaryEquityStyle());
            Logger.logMessage("Dominant Orientation : "+profile.getBdDetailsWidget().getDominantOrientation());
            data.detailsData.setDominantOrientation(profile.getBdDetailsWidget().getDominantOrientation());
            Logger.logMessage("Equity Portfolio Turnover : "+profile.getBdDetailsWidget().getEquityPortfolioTurnover());
            data.detailsData.setEquityPortfolioTurnover(profile.getBdDetailsWidget().getEquityPortfolioTurnover());
            Logger.logMessage("FI Portfolio Turnover : "+profile.getBdDetailsWidget().getFiPortfolioTurnover());
            data.detailsData.setFiPortfolioTurnover(profile.getBdDetailsWidget().getFiPortfolioTurnover());
            Logger.logMessage("Derivative Strategies : "+profile.getBdDetailsWidget().getDerivativeStrategies());
            data.detailsData.setDerivativeStrategies(profile.getBdDetailsWidget().getDerivativeStrategies());
            Logger.logMessage("Reported Total Assets ($m) : "+profile.getBdDetailsWidget().getReportedTotalAssets());
            data.detailsData.setReportedTotalAssets(profile.getBdDetailsWidget().getReportedTotalAssets());
            Logger.logMessage("Reported Equity Assets ($m) : "+profile.getBdDetailsWidget().getReportedEquityAssets());
            data.detailsData.setReportedEquityAssets(profile.getBdDetailsWidget().getReportedEquityAssets());
            Logger.logMessage("Reported Fixed Income Assets ($m) : "+profile.getBdDetailsWidget().getReportedFixedIncomeAssets());
            data.detailsData.setReportedFixedIncomeAssets(profile.getBdDetailsWidget().getReportedFixedIncomeAssets());
            Logger.logMessage("Reported Cash : "+profile.getBdDetailsWidget().getReportedCash());
            data.detailsData.setReportedCash(profile.getBdDetailsWidget().getReportedCash());
            Logger.logMessage("Reported Other : "+profile.getBdDetailsWidget().getReportedOther());
            data.detailsData.setReportedOther(profile.getBdDetailsWidget().getReportedOther());



            InstitutionHoldingsTable institutionHoldingsTable = new InstitutionHoldingsTable();
            profile.showPublicData();
            Logger.logMessage("Current Position in NKE - Value/Change : "+profile.getEquityOwnershipWidget().getCurrentPositionInDefaultTickerValueChange());
            institutionHoldingsTable.setCurrentPositionInDefaultTickerValueChange(profile.getEquityOwnershipWidget().getCurrentPositionInDefaultTickerValueChange());
            Logger.logMessage("Current Position in NKE - Shares/Change : "+profile.getEquityOwnershipWidget().getCurrentPositionInDefaultTickerSharesChange());
            institutionHoldingsTable.setCurrentPositionInDefaultTickerSharesChange(profile.getEquityOwnershipWidget().getCurrentPositionInDefaultTickerSharesChange());
            Logger.logMessage("% of Portfolio : "+profile.getEquityOwnershipWidget().getPercentOfPortfolio());
            institutionHoldingsTable.setPercentOfPortfolio(profile.getEquityOwnershipWidget().getPercentOfPortfolio());
            Logger.logMessage("% of Shares Outstanding : "+profile.getEquityOwnershipWidget().getPercentOfSharesOutstanding());
            institutionHoldingsTable.setPercentOfSharesOutstanding(profile.getEquityOwnershipWidget().getPercentOfSharesOutstanding());
            Logger.logMessage("NKE Rank in Portfolio : "+profile.getEquityOwnershipWidget().getDefaultTickerRankInPortfolio());
            institutionHoldingsTable.setDefaultTickerRankInPortfolio(profile.getEquityOwnershipWidget().getDefaultTickerRankInPortfolio());
            Logger.logMessage("Holder Rank : "+profile.getEquityOwnershipWidget().getHolderRank());
            institutionHoldingsTable.setHolderRank(profile.getEquityOwnershipWidget().getHolderRank());
            Logger.logMessage("Industry Value/Change : "+profile.getEquityOwnershipWidget().getIndustryValueChange());
            institutionHoldingsTable.setIndustryValueChange(profile.getEquityOwnershipWidget().getIndustryValueChange());
            Logger.logMessage("Peer Value/Change : "+profile.getEquityOwnershipWidget().getPeerValueChange());
            institutionHoldingsTable.setPeerValueChange(profile.getEquityOwnershipWidget().getPeerValueChange());
            data.equityOwnershipData.institutionHoldingsTable.put(UITitles.DataSources.PUBLIC_FILINGS, institutionHoldingsTable);

            Logger.logMessage("GET INTO SURVEILLANCE MODE");

            profile.showSurveillanceData();
            institutionHoldingsTable = new InstitutionHoldingsTable();
            Logger.logMessage("Current Position in NKE - Value/Change : "+profile.getEquityOwnershipWidget().getCurrentPositionInDefaultTickerValueChange());
            institutionHoldingsTable.setCurrentPositionInDefaultTickerValueChange(profile.getEquityOwnershipWidget().getCurrentPositionInDefaultTickerValueChange());
            Logger.logMessage("Current Position in NKE - Shares/Change : "+profile.getEquityOwnershipWidget().getCurrentPositionInDefaultTickerSharesChange());
            institutionHoldingsTable.setCurrentPositionInDefaultTickerSharesChange(profile.getEquityOwnershipWidget().getCurrentPositionInDefaultTickerSharesChange());
            Logger.logMessage("% of Portfolio : "+profile.getEquityOwnershipWidget().getPercentOfPortfolio());
            institutionHoldingsTable.setPercentOfPortfolio(profile.getEquityOwnershipWidget().getPercentOfPortfolio());
            Logger.logMessage("% of Shares Outstanding : "+profile.getEquityOwnershipWidget().getPercentOfSharesOutstanding());
            institutionHoldingsTable.setPercentOfSharesOutstanding(profile.getEquityOwnershipWidget().getPercentOfSharesOutstanding());
            Logger.logMessage("NKE Rank in Portfolio : "+profile.getEquityOwnershipWidget().getDefaultTickerRankInPortfolio());
            institutionHoldingsTable.setDefaultTickerRankInPortfolio(profile.getEquityOwnershipWidget().getDefaultTickerRankInPortfolio());
            Logger.logMessage("Holder Rank : "+profile.getEquityOwnershipWidget().getHolderRank());
            institutionHoldingsTable.setHolderRank(profile.getEquityOwnershipWidget().getHolderRank());
            Logger.logMessage("Industry Value/Change : "+profile.getEquityOwnershipWidget().getIndustryValueChange());
            institutionHoldingsTable.setIndustryValueChange(profile.getEquityOwnershipWidget().getIndustryValueChange());
            Logger.logMessage("Peer Value/Change : "+profile.getEquityOwnershipWidget().getPeerValueChange());
            institutionHoldingsTable.setPeerValueChange(profile.getEquityOwnershipWidget().getPeerValueChange());
            data.equityOwnershipData.institutionHoldingsTable.put(UITitles.DataSources.SURVEILLANCE, institutionHoldingsTable);

            Logger.logMessage("# of Holdings : "+profile.getEquityOwnershipWidget().getNumberOfHoldings());
            data.equityOwnershipData.ownershipSummaryTable.setNumberOfHoldings(profile.getEquityOwnershipWidget().getNumberOfHoldings());
            Logger.logMessage("Portfolio Value (USD, mm) : "+profile.getEquityOwnershipWidget().getPortfolioValue());
            data.equityOwnershipData.ownershipSummaryTable.setPortfolioValue(profile.getEquityOwnershipWidget().getPortfolioValue());
            Logger.logMessage("New Positions (USD, mm) : "+profile.getEquityOwnershipWidget().getNewPositions());
            data.equityOwnershipData.ownershipSummaryTable.setNewPositions(profile.getEquityOwnershipWidget().getNewPositions());
            Logger.logMessage("Increased Positions (USD, mm) : "+profile.getEquityOwnershipWidget().getIncreasedPositions());
            data.equityOwnershipData.ownershipSummaryTable.setIncreasedPositions(profile.getEquityOwnershipWidget().getIncreasedPositions());
            Logger.logMessage("Decreased Positions (USD, mm) : "+profile.getEquityOwnershipWidget().getDecreasedPositions());
            data.equityOwnershipData.ownershipSummaryTable.setDecreasedPositions(profile.getEquityOwnershipWidget().getDecreasedPositions());
            Logger.logMessage("Unchanged Positions (USD, mm) : "+profile.getEquityOwnershipWidget().getUnchangedPositions());
            data.equityOwnershipData.ownershipSummaryTable.setUnchangedPositions(profile.getEquityOwnershipWidget().getUnchangedPositions());

            for (int i = 1; i <= 6; i++) {
                Logger.logMessage(profile.getEquityOwnershipWidget().getInstitutionNameFromTopHoldingsTable(i)+" : % Port = "+profile.getEquityOwnershipWidget().getPercentPort(i)
                + ", % Cng = " + profile.getEquityOwnershipWidget().getPercentChg(i));

                String securityName = profile.getEquityOwnershipWidget().getInstitutionNameFromTopHoldingsTable(i);
                String percentPort = profile.getEquityOwnershipWidget().getPercentPort(i);
                String percentChg = profile.getEquityOwnershipWidget().getPercentChg(i);

                TopHoldingsTableRow row = new TopHoldingsTableRow(securityName, percentPort, percentChg);
                data.equityOwnershipData.topHoldingsTable.add(row);
            }

            for (int i = 2; i <= 7; i++) {
                Logger.logMessage(profile.getEquityOwnershipWidget().getInstitutionNameFromTopBuysAndSellsTable(i)+" : Value Change = "+profile.getEquityOwnershipWidget().getValueChange(i)
                        + ", Shares Change = " + profile.getEquityOwnershipWidget().getSharesChange(i));

                String securityName = profile.getEquityOwnershipWidget().getInstitutionNameFromTopBuysAndSellsTable(i);
                String valueChange = profile.getEquityOwnershipWidget().getValueChange(i);
                String sharesChange = profile.getEquityOwnershipWidget().getSharesChange(i);

                TopBuysSellsTableRow row = new TopBuysSellsTableRow(securityName, valueChange, sharesChange);

                data.equityOwnershipData.topBuysSellsTable.add(row);
            }

            for (int i = 0; i < 10; i++) {
                Logger.logMessage(profile.getFiOwnershipWidget().getSecurityNameFromTop10(i)+" : Par Held = "+profile.getFiOwnershipWidget().getParHeld(i)
                        + ", Par Chg = " + profile.getFiOwnershipWidget().getParChg(i) +  ", % FI Port = " + profile.getFiOwnershipWidget().getFiPort(i));

                String securityName = profile.getFiOwnershipWidget().getSecurityNameFromTop10(i);
                String parHeld = profile.getFiOwnershipWidget().getParHeld(i);
                String parChg = profile.getFiOwnershipWidget().getParChg(i);
                String fiPort = profile.getFiOwnershipWidget().getFiPort(i);

                Top10TableRow row = new Top10TableRow(securityName, parHeld, parChg, fiPort);

                data.fixedIncomeOwnershipData.top10Table.add(row);
            }

            for (int i = 0; i < 6; i++) {
                Logger.logMessage(profile.getFiOwnershipWidget().getSecurityNameFromTopBuysTable(i)+" : Par Change = "+profile.getFiOwnershipWidget().getParChangeTopBuys(i));

                String securityName = profile.getFiOwnershipWidget().getSecurityNameFromTopBuysTable(i);
                String parChange = profile.getFiOwnershipWidget().getParChangeTopBuys(i);

                TopBuysTableRow row = new TopBuysTableRow(securityName, parChange);

                data.fixedIncomeOwnershipData.topBuysTable.add(row);
            }

            for (int i = 0; i < 6; i++) {
                Logger.logMessage(profile.getFiOwnershipWidget().getSecurityNameFromTopBuysInsTable(i)+" : Par Change = "+profile.getFiOwnershipWidget().getParChangeTopBuyIns(i));

                String securityName = profile.getFiOwnershipWidget().getSecurityNameFromTopBuysInsTable(i);
                String parChange = profile.getFiOwnershipWidget().getParChangeTopBuyIns(i);

                TopBuyInsTableRow row = new TopBuyInsTableRow(securityName, parChange);

                data.fixedIncomeOwnershipData.topBuyInsTable.add(row);
            }

            for (int i = 0; i < 6; i++) {
                Logger.logMessage(profile.getFiOwnershipWidget().getSecurityNameFromTopSellsTable(i)+" : Par Change = "+profile.getFiOwnershipWidget().getParChangeTopSells(i));

                String securityName = profile.getFiOwnershipWidget().getSecurityNameFromTopSellsTable(i);
                String parChange = profile.getFiOwnershipWidget().getParChangeTopSells(i);

                TopSellsTableRow row = new TopSellsTableRow(securityName, parChange);

                data.fixedIncomeOwnershipData.topSellsTable.add(row);

            }

            institutionsData.put(profile.getName(), data);

        }
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        final String json = gson.toJson(institutionsData);
        Logger.logMessage(json);
        Logger.logMessage("done");
    }

    @AfterMethod
    public void cleanup() {

        Driver.goTo(productLink);
        mainScreen = new BillfoldMainScreen();
        mainScreen.logout();
        Driver.tearDown();
    }


}
