package ipreomobile.templates.ui;

import ipreomobile.core.SenchaWebElement;

class ListAsSingleSelect  extends SingleSelectListImpl {}
class ListAsMultiSelect   extends MultiSelectListImpl {}


public abstract class SingleMultiSelectListImpl extends BaseList implements SingleSelectListController, MultiSelectListController {

    //TODO: checkAll(), getChecked(), etc. methods can possible be usable for the lists which are fully displayed on the screen

    private ListAsSingleSelect  sList = new ListAsSingleSelect();
    private ListAsMultiSelect   mList = new ListAsMultiSelect();

    public void setItemsXpath(String xpath) {
        sList.setItemsXpath(xpath);
        mList.setItemsXpath(xpath);
        super.setItemsXpath(xpath);
    }

    public void setItemNameXpath(String xpath) {
        sList.setItemNameXpath(xpath);
        mList.setItemNameXpath(xpath);
        super.setItemNameXpath(xpath);
    }

    public void setDisabledItemClassName(String className) {
        sList.setDisabledItemClassName(className);
        mList.setDisabledItemClassName(className);
        super.setDisabledItemClassName(className);
    }

    public void setListContainer(SenchaWebElement el) {
        sList.setListContainer(el);
        mList.setListContainer(el);
        super.setListContainer(el);
    }

    public void setNonStrictNameEqualityFlag(boolean flag) {
        sList.setNonStrictNameEqualityFlag(flag);
        mList.setNonStrictNameEqualityFlag(flag);
        super.setNonStrictNameEqualityFlag(flag);
    }

    public void setSelectedItemClassName(String className) {
        sList.setSelectedItemClassName(className);
    }

    public void setStateSwitcherXpath(String stateSwitcherXpath) {
        mList.setStateSwitcherXpath(stateSwitcherXpath);
    }

    public void setStateTokenXpath(String stateTokenXpath) {
        mList.setStateTokenXpath(stateTokenXpath);
    }

    public void setStateTokenSelectedClassName(String stateTokenSelectedClassName) {
        mList.setStateTokenSelectedClassName(stateTokenSelectedClassName);
    }

    @Override
    public void check(String... names) {
        mList.check(names);
    }

    public void checkBySubtext(String name, String subtext) {
        mList.checkBySubtext(name, subtext);
    }

    @Override
    public void uncheck(String... names) {
       mList.uncheck(names);
    }

    public void uncheckBySubtext(String name, String subtext) {
        mList.uncheckBySubtext(name, subtext);
    }

    @Override
    public boolean areChecked(String... names) {
        return mList.areChecked(names);
    }

    public boolean areUnchecked(String... names) {
        return mList.areUnchecked(names);
    }

    public void verifyChecked(String... names){
        mList.verifyChecked(names);
    }

    public void verifyUnchecked(String... names){
        mList.verifyUnchecked(names);
    }

    @Override
    public boolean isChecked(String name) {
        return mList.isChecked(name);
    }
    public boolean isChecked(String name, String subtext) {
        return mList.isChecked(name, subtext);
    }

    @Override
    public void select(String name) {
        sList.select(name);
    }
    public void select(String name, String subtext) {
        sList.select(name, subtext);
    }

    public void selectNext() {sList.selectNext();}

    public void selectFirstWithName(String name) {
        sList.selectFirstWithName(name);
    }

    @Override
    public boolean isSelected(String name) {
        return sList.isSelected(name);
    }
    public boolean isSelected(String name, String subtext) {
        return sList.isSelected(name, subtext);
    }

    public boolean isSelectedItemUnavailableInOfflineMode(){
        return sList.isSelectedItemUnavailableInOfflineMode();
    }

    public void clickSelected(){
        sList.clickSelected();
    }

    public String getSelectedItemName(){
        return sList.getSelectedItemName();
    }

    protected String getSelectedItemXpath() {
        return sList.getSelectedItemXpath();
    }

//    public void verifyCheckedCount(int count) {
//        mList.verifyCheckedCount(count);
//    }
//    public void uncheckAll(){
//        mList.uncheckAll();
//    }
//    public List<String> checkAll(){
//        return mList.checkAll();
//    }

//    @Override
//    public List<SenchaWebElement> getChecked() {
//        return mList.getChecked();
//    }

//    @Override
//    public List<String> getCheckedNames() {
//        return mList.getCheckedNames();
//    }

//    public String listCheckedNames(){
//        return mList.listCheckedNames();
//    }

}
