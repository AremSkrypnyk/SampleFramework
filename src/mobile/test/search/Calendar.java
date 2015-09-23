package ipreomobile.test.search;

import ipreomobile.templates.test.BaseTest;
import ipreomobile.ui.GlobalNavigation;
import ipreomobile.ui.blocks.overlay.ListOverlaySearchMultiSelect;
import ipreomobile.ui.activities.ActivitySearchTab;
import ipreomobile.ui.search.CalendarSearchPanel;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Calendar extends BaseTest {

    CalendarSearchPanel panel;
    ActivitySearchTab activitySearchTab;
    ListOverlaySearchMultiSelect addSymbol;

    @Test
    public void verifyAllParametersInActivitySearch(){
        navigation = new GlobalNavigation();
        navigation.openCalendar();

//        openActivitySearchPanel();
//        panel.setSearchField("Act");
//        panel.search();
//        activitySearchTab = new ActivitySearchTab();
//        Assert.assertTrue(!activitySearchTab.isResultSetEmpty(), "Expected to find activities in search results: ");
//
//        openActivitySearchPanel();
//        panel.setSearchField("Test Act");
//        panel.search();
//        activitySearchTab = new ActivitySearchTab();
//        Assert.assertTrue(!activitySearchTab.isResultSetEmpty(), "Expected to find activities in search results: ");

        openActivitySearchPanel();
        panel.selectActivityType("Analyst Day");
        panel.selectActivityTopic("Cash Flow");
        panel.addSymbol("GO", "Google, Inc. CL A");
        addSymbol = panel.addSymbol();
        addSymbol.setSearchFilter("GO");
        Assert.assertTrue(addSymbol.isChecked("Google, Inc. CL A"), "Google, Inc. CL A expected to be checked :");
        addSymbol.uncheck("Google, Inc. CL A");
        addSymbol.setSearchFilter("AAPL");
        addSymbol.checkBySubtext("Apple, Inc.", "AAPL, NASDAQ Global Market");
        addSymbol.close();

        panel.addSymbol("MSFT", "Microsoft Corporation");
        addSymbol = panel.addSymbol();
        addSymbol.setSearchFilter("MSFT");
        Assert.assertTrue(addSymbol.isChecked("Microsoft Corporation"), "Microsoft Corporation expected to be checked :");
        addSymbol.setSearchFilter("AAPL");
        Assert.assertTrue(addSymbol.isChecked("Apple, Inc.", "AAPL, NASDAQ Global Market"), "Apple, Inc. expected to be checked :");
        addSymbol.uncheckBySubtext("Apple, Inc.", "AAPL, NASDAQ Global Market");
        addSymbol.close();
        panel.search();
        activitySearchTab = new ActivitySearchTab();
        activitySearchTab.openProfileOverview("Final test group activity");

        openActivitySearchPanel();
        panel.reset();
        panel.setSearchField("Partic");
        panel.selectActivityType("Group Meeting");
        panel.selectActivityTopic("M&A");
        panel.selectStartDate("05/21/2012");
        panel.selectEndDate("06/24/2014");
        panel.search();
        activitySearchTab = new ActivitySearchTab();
        Assert.assertTrue(!activitySearchTab.isResultSetEmpty(), "Expected to find activities in search results: ");
    }

    @Test
    public void activitySearch(){
        openActivitySearchPanel();
        panel.selectActivityType("Analyst Day");
        panel.search();
        ActivitySearchTab activitySearchTab = new ActivitySearchTab();
        activitySearchTab.openProfileOverview("'autotest subject'");


    }
    private void openActivitySearchPanel(){
        this.panel = navigation.searchActivities();
    }
}
