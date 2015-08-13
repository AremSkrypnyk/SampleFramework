package ipreomobile.templates.ui;

public interface ProfileOverviewController {
    public ProfileOverviewController waitProfileLoaded(String name);
    public ProfileOverviewController verifyProfileName(String name);
    public String getProfileName();
    public void waitReady();
}
