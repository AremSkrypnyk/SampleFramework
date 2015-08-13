package ipreomobile.ui.securities;

import ipreomobile.core.Driver;
import ipreomobile.templates.ui.FullProfile;
import ipreomobile.templates.ui.SearchResultsTab;
import org.openqa.selenium.By;
import org.testng.Assert;

import java.util.regex.Pattern;

public class SecuritySearchTab extends SearchResultsTab {



    @Override
    protected void setupProfileList() {
        qpl = new SecurityProfileList();
        super.setupProfileList();
    }

    @Override
    protected void setupProfileOverview() {
        profile = new SecurityProfileOverview();
    }

    public FullProfile openFullProfile(String name){
        super.openFullProfile(name);
        return new FullProfile();
    }

    public FullProfile openFullProfile(String name, String subtext){
        super.openFullProfile(name, subtext);
        return new FullProfile();
    }

    public FiSecurityFullProfile openFixedIncomeFullProfile(String name){
        if (isSecurityFixedIncome(name)) {
            super.openFullProfile(name);
            return new FiSecurityFullProfile();
        }
        else{
            throw new Error("You try to open Equity Security Profile through the Fixed Income Security profile. Use openEqProfileSummary() method to open current profile.");
        }
    }

    public SecurityProfileOverview getActiveProfileOverview(){
        return (SecurityProfileOverview)profile;
    }

    public FiSecurityFullProfile openFixedIncomeFullProfile(String name, String subtext){
        if (isSecurityFixedIncome(name, subtext)) {
            super.openFullProfile(name, subtext);
            return new FiSecurityFullProfile();
        }
        else{
            throw new Error("You try to open Equity Security Profile through the Fixed Income Security profile. Use openEqProfileSummary() method to open current profile.");
        }
    }

    public EqSecurityFullProfile openEquityFullProfile(String name){
        if (isSecurityEquity(name)) {
            super.openFullProfile(name);
            return new EqSecurityFullProfile();
        }
        else{
            throw new Error("You try to open Fixed Income Security Profile through the Equity Security profile. Use openFiProfileSummary() method to open current profile.");
        }
    }

    public EqSecurityFullProfile openEquityFullProfile(String name, String subtext){
        if (isSecurityEquity(name, subtext)) {
            super.openFullProfile(name, subtext);
            return new EqSecurityFullProfile();
        }
        else{
            throw new Error("You try to open Fixed Income Security Profile through the Equity Security profile. Use openFiProfileSummary() method to open current profile.");
        }
    }

    public SecuritySearchTab verifyProfileNameSelectedInList(String name) {
        String itemName = Pattern.compile("\\s\\(\\d*\\)").matcher(super.getProfileNameSelectedInList()).replaceAll("");
        Assert.assertEquals(itemName, name, "Active profile name highlighted in Quick Profile List is different from expected one:");
        return this;
    }

    public boolean isSecurityEquity(){
        return ((SecurityProfileOverview)profile).isEquity();
    }

    public boolean isSecurityEquity(String securityName) {
        openProfileOverview(securityName);
        return isSecurityEquity();
    }

    public boolean isSecurityEquity(String securityName, String subtext) {
        openProfileOverview(securityName, subtext);
        return isSecurityEquity();
    }

    public boolean isSecurityFixedIncome() {
        return ((SecurityProfileOverview)profile).isFixedIncome();
    }

    public boolean isSecurityFixedIncome(String securityName) {
        openProfileOverview(securityName);
        return isSecurityFixedIncome();
    }

    public boolean isSecurityFixedIncome(String securityName, String subtext) {
        openProfileOverview(securityName, subtext);
        return isSecurityFixedIncome();
    }


//    public TwoPane verifyProfileNameDisplayedInOverview(String expectedName) {
//        profile.verifyProfileNameContains(expectedName);
//        return this;
//    }
}
