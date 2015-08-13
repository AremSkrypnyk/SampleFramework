package ipreomobile.templates.ui;

public interface MultiSelectListController extends SelectableListController {

    public void check(String... names);
    //public void check(List<String> names);

    public void uncheck(String... names);

    public boolean areChecked(String... names);

    public boolean isChecked(String name);

    //public ListSenchaWebElement> getChecked();
    //public List<String> getCheckedNames();
}
