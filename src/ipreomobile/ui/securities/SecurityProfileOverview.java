package ipreomobile.ui.securities;

import ipreomobile.core.Driver;
import ipreomobile.templates.ui.ProfileOverview;
import org.openqa.selenium.By;

import java.util.regex.Pattern;

public class SecurityProfileOverview extends ProfileOverview {

    private static final By EQUITY_SECURITY_SUMMARY_LOCATOR       = By.className("eqsecurity-summary-card");
    private static final By FIXED_INCOME_SECURITY_SUMMARY_LOCATOR = By.className("fisecurity-summary-card");

    public SecurityProfileOverview(){
        super();
        addCheckpointElement(By.className("active-card")).mustBeVisible();
        addCheckpointElement(By.className("profile-header-labels")).mustBeVisible();
        waitReady();
    }

    @Override
    public SecurityProfileOverview waitProfileLoaded(String name){
        name = Pattern.compile("\\s\\(\\d*\\)").matcher(name.trim()).replaceAll("");
        super.waitProfileLoaded(name);
        return this;
    }

    public boolean isFixedIncome(){
        return Driver.findIfExists(FIXED_INCOME_SECURITY_SUMMARY_LOCATOR) != null;
    }

    public boolean isEquity(){
        return (Driver.findIfExists(EQUITY_SECURITY_SUMMARY_LOCATOR) != null);
    }
}
