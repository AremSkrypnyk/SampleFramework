package ipreomobile.ui.profiles.fullProfileTabs;

import ipreomobile.templates.ui.ProfileTab;
import ipreomobile.ui.blocks.EyeButton;

public class EqSecurityAnalyticsProfileTab extends ProfileTab {

    private EyeButton eye;

    private static final String CONCENTRATION_SUB_TAB = "Concentration";
    private static final String STYLE_INCOME_SUB_TAB = "Style";
    private static final String TURNOVER_INCOME_SUB_TAB = "Turnover";

    public EqSecurityAnalyticsProfileTab selectConcentrationTab(){
        selectTab(CONCENTRATION_SUB_TAB);
        return this;
    }

    public EqSecurityAnalyticsProfileTab selectStyleTab(){
        selectTab(STYLE_INCOME_SUB_TAB);
        return this;
    }

    public EqSecurityAnalyticsProfileTab selectTurnoverTab(){
        selectTab(TURNOVER_INCOME_SUB_TAB);
        return this;
    }

    public EqSecurityAnalyticsProfileTab showSurveillanceData(){
        eye = new EyeButton();
        eye.showSurveillanceData();
        waitReady();
        return this;
    }

    public EqSecurityAnalyticsProfileTab showPublicData(){
        eye = new EyeButton();
        eye.showPublicData();
        waitReady();
        return this;
    }

    public EqSecurityAnalyticsProfileTab verifySurveillanceDataIsDisplayed(){
        eye = new EyeButton();
        eye.verifySurveillanceDataShown();
        return this;
    }

    public EqSecurityAnalyticsProfileTab verifyPublicDataIsDisplayed(){
        eye = new EyeButton();
        eye.verifyPublicDataShown();
        return this;
    }
}
