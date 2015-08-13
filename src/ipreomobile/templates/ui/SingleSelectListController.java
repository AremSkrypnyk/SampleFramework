package ipreomobile.templates.ui;

public interface SingleSelectListController extends SelectableListController {

    public void select(String name);
    public boolean isSelected(String name);
    public String getSelectedItemName();

}
