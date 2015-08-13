package ipreomobile.ui;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UITitles {

    public enum ListType {
        MY_LISTS, OTHER_LISTS
    }

    public enum PanelTabs {
        INVESTORS, ACTIVITY, CALENDAR, RECENTLY_VIEWED, LISTS, SEARCH_RESULTS
    }

    public enum SearchParams {
        ALL, BD_ONLY, COMPANY_CRM, BUY_SIDE, SELL_SIDE
    }

    public enum Panels {
        INSTITUTIONS, CONTACTS, CALENDAR, SECURITIES, FUNDS
    }

    public enum SearchType{
        SEARCH_IN, SIDE
    }

    public enum Side{
        SELL_SIDE, BUY_SIDE
    }

    public enum SearchLocation{
        CONTACTS, FUNDS, SECURITIES, ACTIVITIES, INSTITUTIONS
    }

    public enum OverlayType{
        ACTIVITY_TOPIC,
        ACTIVITY_TYPE,
        CONTACT_ADDRESS,
        CONTACT_LISTS,
        CONTACT_LOCATION,
        END_DATE,
        FIX_DATA,
        INSTITUTION_ADDRESS,
        INSTITUTION_LISTS,
        INSTITUTION_NAME,
        INSTITUTION_TYPE,
        INVESTMENT_CENTER_LOCATION,
        PRIMARY_CONTACTS,
        PRIMARY_JOB_FUNCTION,
        PROFILE_INFO,
        START_DATE,
        SYMBOL,
        FUND_TYPE,
        FUND_ADDRESS,
        SEARCH_INSTITUTION,
        SEARCH_CONTACT,
        MANAGED_BY,
        MACRO,
        MID,
        MICRO,
        LOCATION,
        DATE_TIME,
        ASSIGNED_TO,
        PRIORITY,
        TIME_ZONE
    }

    public enum ProfileTab{
        //Institutions, Contacts, Funds
        DETAILS,
        OWNERSHIP,
        TARGETING,
        FOCUS,
        ACTIONS,
        FUNDS,
        CURRENT_HOLDINGS,
        CONTACTS,
        ADDITIONAL_INFO,

        //Securities
        TOP_10,
        CURRENT_HOLDERS,
        ANALYTICS,
        PEERS,
        DEBT_SECURITIES_OF_ISSUER
    }

    public enum ProfileCardTitle {
        INSTITUTIONAL_HOLDINGS,
        NON_OWNER_INSTITUTIONAL_HOLDINGS,
        NEXT_ACTION,
        LAST_ACTIONS,
        POSITION_HISTORY,
        CONTACT_HOLDINGS,
        NON_OWNER_CONTACT_HOLDINGS,
        BIOGRAPHY,
        COVERAGE,
        FUND_HOLDINGS,
        NON_OWNER_FUND_HOLDINGS,
        SUMMARY,
        TOP_HOLDERS,
        SHARES,
        INDUSTRY,
        PRICE_HISTORY
    }

    public enum ActivityCardTitle {
        ACTIVITY_DETAILS,
        LOCATION_MAP,
        NOTES,
        EXTERNAL_PARTICIPANTS,
        INTERNAL_PARTICIPANTS,
        ALERT_RECIPIENTS
    }

    public enum AssetClass{
        EQUITY, FIXED_INCOME
    }

    public enum PickerType{
        SELECT_DATE
    }

    public enum SettingsTab {
        GENERAL,
        OFFLINE_MODE,
        CHANGE_PASSWORD,
        APPLICATION_FEEDBACK,
        GLOBALIZATION,
        TERMS_AND_CONDITIONS,
        PRIVACY_POLICY,
        CONTACT_US,
    }

    public enum DataSources{
        PUBLIC_FILINGS,
        SURVEILLANCE
    }

    private static Map<Enum<?>, String> titlesMapping = new HashMap<>();

    public static void init() {
        titlesMapping.put(ListType.MY_LISTS, "My Lists");
        titlesMapping.put(ListType.OTHER_LISTS, "Other Lists");

        titlesMapping.put(Side.BUY_SIDE, "Buy Side");
        titlesMapping.put(Side.SELL_SIDE, "Sell Side");

        titlesMapping.put(PanelTabs.INVESTORS, "Investors");
        titlesMapping.put(PanelTabs.ACTIVITY, "Activity");
        titlesMapping.put(PanelTabs.CALENDAR, "Calendar");
        titlesMapping.put(PanelTabs.RECENTLY_VIEWED, "Recently Viewed");
        titlesMapping.put(PanelTabs.LISTS, "Lists");
        titlesMapping.put(PanelTabs.SEARCH_RESULTS, "Search Results");

        titlesMapping.put(SearchParams.ALL, "All");
        titlesMapping.put(SearchParams.BD_ONLY, "BD Only");
        titlesMapping.put(SearchParams.COMPANY_CRM, "Company CRM");
        titlesMapping.put(SearchParams.BUY_SIDE, "Buy Side");
        titlesMapping.put(SearchParams.SELL_SIDE, "Sell Side");

        titlesMapping.put(Panels.INSTITUTIONS, "Institutions");
        titlesMapping.put(Panels.CONTACTS, "Contacts");
        titlesMapping.put(Panels.CALENDAR, "Calendar");
        titlesMapping.put(Panels.SECURITIES, "Securities");
        titlesMapping.put(Panels.FUNDS, "Funds");

        titlesMapping.put(SearchType.SEARCH_IN, "Search In");
        titlesMapping.put(SearchType.SIDE, "Side");

        titlesMapping.put(OverlayType.INSTITUTION_TYPE, "Institution Type");
        titlesMapping.put(OverlayType.INVESTMENT_CENTER_LOCATION, "Investment Center / Locations");
        titlesMapping.put(OverlayType.INSTITUTION_LISTS, "Institution Lists");
        titlesMapping.put(OverlayType.INSTITUTION_NAME, "Institution Name");
        titlesMapping.put(OverlayType.CONTACT_LOCATION, "Contact Location");
        titlesMapping.put(OverlayType.PRIMARY_JOB_FUNCTION, "Primary Job Function");
        titlesMapping.put(OverlayType.CONTACT_LISTS, "Contact Lists");
        titlesMapping.put(OverlayType.ACTIVITY_TYPE, "Type");
        titlesMapping.put(OverlayType.ACTIVITY_TOPIC, "Topic");
        titlesMapping.put(OverlayType.START_DATE, "Start Date");
        titlesMapping.put(OverlayType.END_DATE, "End Date");
        titlesMapping.put(OverlayType.FUND_TYPE, "Fund Type");
        titlesMapping.put(OverlayType.SYMBOL, "Symbol");
        titlesMapping.put(OverlayType.FUND_ADDRESS, "Fund Address");
        titlesMapping.put(OverlayType.MANAGED_BY, "Managed By");
        titlesMapping.put(OverlayType.TIME_ZONE, "Time Zone");


        titlesMapping.put(SearchLocation.ACTIVITIES, "Activities");
        titlesMapping.put(SearchLocation.FUNDS, "Funds");
        titlesMapping.put(SearchLocation.CONTACTS, "Contacts");
        titlesMapping.put(SearchLocation.SECURITIES, "Securities");
        titlesMapping.put(SearchLocation.INSTITUTIONS, "Institutions");

        titlesMapping.put(AssetClass.EQUITY, "Equity");
        titlesMapping.put(AssetClass.FIXED_INCOME, "Fixed Income");

        titlesMapping.put(PickerType.SELECT_DATE, "Select Date");

        titlesMapping.put(OverlayType.INSTITUTION_ADDRESS, "Institution Address");
        titlesMapping.put(OverlayType.CONTACT_ADDRESS, "Contact Address");

        titlesMapping.put(OverlayType.SEARCH_INSTITUTION, "Search Institution");
        titlesMapping.put(OverlayType.SEARCH_CONTACT, "Search Contact");

        titlesMapping.put(OverlayType.MACRO, "Macro");
        titlesMapping.put(OverlayType.MID, "Mid");
        titlesMapping.put(OverlayType.MICRO, "Micro");
        titlesMapping.put(OverlayType.LOCATION, "Location");
        titlesMapping.put(OverlayType.DATE_TIME, "Date / Time");
        titlesMapping.put(OverlayType.ASSIGNED_TO, "Assigned To");
        titlesMapping.put(OverlayType.PRIORITY, "Priority");

        titlesMapping.put(OverlayType.FIX_DATA, "Fix Data");
        titlesMapping.put(OverlayType.PRIMARY_CONTACTS, "Primary Contacts");

        titlesMapping.put(ProfileTab.ACTIONS, "Actions");
        titlesMapping.put(ProfileTab.ADDITIONAL_INFO, "Additional Info");
        titlesMapping.put(ProfileTab.ANALYTICS, "Analytics");
        titlesMapping.put(ProfileTab.CONTACTS, "Contacts");
        titlesMapping.put(ProfileTab.CURRENT_HOLDERS, "Current Holders");
        titlesMapping.put(ProfileTab.CURRENT_HOLDINGS, "Current Holdings");
        titlesMapping.put(ProfileTab.DETAILS, "Details");
        titlesMapping.put(ProfileTab.FOCUS, "Focus");
        titlesMapping.put(ProfileTab.FUNDS, "Funds");
        titlesMapping.put(ProfileTab.OWNERSHIP, "Ownership");
        titlesMapping.put(ProfileTab.PEERS, "Peers");
        titlesMapping.put(ProfileTab.TARGETING, "Targeting");
        titlesMapping.put(ProfileTab.TOP_10, "Top 10");
        titlesMapping.put(ProfileTab.DEBT_SECURITIES_OF_ISSUER, "Debt Securities Of Issuer");


        titlesMapping.put(SettingsTab.GENERAL, "General");
        titlesMapping.put(SettingsTab.OFFLINE_MODE, "Offline Mode");
        titlesMapping.put(SettingsTab.CHANGE_PASSWORD, "Change Password");
        titlesMapping.put(SettingsTab.APPLICATION_FEEDBACK, "Application Feedback");
        titlesMapping.put(SettingsTab.GLOBALIZATION, "Globalization");
        titlesMapping.put(SettingsTab.TERMS_AND_CONDITIONS, "Terms & Conditions");
        titlesMapping.put(SettingsTab.PRIVACY_POLICY, "Privacy Policy");
        titlesMapping.put(SettingsTab.CONTACT_US, "Contact Us");

        titlesMapping.put(ProfileCardTitle.BIOGRAPHY, "Biography");
        titlesMapping.put(ProfileCardTitle.COVERAGE, "Coverage");
        titlesMapping.put(ProfileCardTitle.INSTITUTIONAL_HOLDINGS, "Institutional Holdings");
        titlesMapping.put(ProfileCardTitle.CONTACT_HOLDINGS, "Contact Holdings");
        titlesMapping.put(ProfileCardTitle.FUND_HOLDINGS, "Fund Holdings");
        titlesMapping.put(ProfileCardTitle.NON_OWNER_INSTITUTIONAL_HOLDINGS, "Non-Owner Institutional Holdings");
        titlesMapping.put(ProfileCardTitle.NON_OWNER_CONTACT_HOLDINGS, "Non-Owner Contact Holdings");
        titlesMapping.put(ProfileCardTitle.NON_OWNER_FUND_HOLDINGS, "Non-Owner Fund Holdings");
        titlesMapping.put(ProfileCardTitle.NEXT_ACTION, "Next Action");
        titlesMapping.put(ProfileCardTitle.LAST_ACTIONS, "Last Actions");
        titlesMapping.put(ProfileCardTitle.POSITION_HISTORY, "Position History");
        titlesMapping.put(ProfileCardTitle.SUMMARY, "Summary");
        titlesMapping.put(ProfileCardTitle.TOP_HOLDERS, "Top Holders");
        titlesMapping.put(ProfileCardTitle.SHARES, "Shares");
        titlesMapping.put(ProfileCardTitle.INDUSTRY, "Industry");
        titlesMapping.put(ProfileCardTitle.PRICE_HISTORY, "Price History");

        titlesMapping.put(ActivityCardTitle.ACTIVITY_DETAILS, "Activity Details");
        titlesMapping.put(ActivityCardTitle.LOCATION_MAP, "Location Map");
        titlesMapping.put(ActivityCardTitle.NOTES, "Notes");
        titlesMapping.put(ActivityCardTitle.EXTERNAL_PARTICIPANTS, "External Participants");
        titlesMapping.put(ActivityCardTitle.INTERNAL_PARTICIPANTS, "Internal Participants");
        titlesMapping.put(ActivityCardTitle.ALERT_RECIPIENTS, "Alert Recipients");

    }

    public static String get(Enum<?> id) {
        return titlesMapping.get(id);
    }

    public static List<Panels> getPanels() {
        return Arrays.asList(Panels.INSTITUTIONS, Panels.CONTACTS, Panels.CALENDAR, Panels.SECURITIES, Panels.FUNDS);
    }

}


