package ipreomobile.data;

//import com.sun.xml.internal.ws.util.StringUtils;
import ipreomobile.core.Logger;
import org.apache.commons.lang3.text.WordUtils;
import org.testng.Reporter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TestDataObject {
    private String dataSetName;
    private String dataObjectName;
    private String index;
    private List<Field> dataFields;

    public TestDataObject() {
        this.dataSetName = Reporter.getCurrentTestResult().getName();
        this.dataObjectName = getDataObjectName();
        extractDataFields();
        loadCaseDataSet();
    }

    public TestDataObject loadCommonDataSet() {
        String caseDataSetId = dataSetName;
        dataSetName = "common";
        loadCaseDataSet();
        this.dataSetName = caseDataSetId;
        return this;
    }

    public TestDataObject loadCommonDataSetByIndex(int index) {
        this.dataSetName = "common";
        loadDataSetByIndex(index);
        this.dataSetName = Reporter.getCurrentTestResult().getName();
        return this;
    }

    public TestDataObject loadCaseDataSet() {
        return loadDataSetByTag("");
    }

    public TestDataObject loadDataSetByIndex(int index) {
        return loadDataSetByTag(index + "");
    }

    public TestDataObject loadDataSetByTag(String index) {
        this.index = index;
        setProperties();
        return this;
    }

    public void setTestCaseName(String testCaseName) {
        this.clear();
        this.dataSetName = testCaseName;
        loadCaseDataSet();
    }

    public void clear() {
        for (Field f : dataFields) {
            try {
                f.setAccessible(true);
                f.set(this, "");
                f.setAccessible(false);
            } catch (IllegalAccessException e) {
                Logger.logError(e);
            }
        }
    }

    public String describe() {
        StringBuilder description = new StringBuilder(WordUtils.capitalize(dataObjectName) + " parameters:\n");
        for (Field f : dataFields) {
            try {
                f.setAccessible(true);
                description.append(decorateFieldEntry(f));
                f.setAccessible(false);
            } catch (IllegalAccessException e) {
                Logger.logError(e);
            }
        }
        return description.toString();
    }

    private String decorateFieldEntry(Field f) throws IllegalAccessException {
        return "- " + f.getName() + " = '" + f.get(this) + "'\n";
    }

    private String getDataObjectName() {
        String[] names = this.getClass().getName().split("\\.");
        return WordUtils.uncapitalize(names[names.length - 1].replace("Data", ""));
    }

    private void extractDataFields(){
        dataFields = new ArrayList<>();
        Class daClass = this.getClass();
        List<Field> fields = new ArrayList<>();

        do {
            fields.addAll(Arrays.asList(daClass.getDeclaredFields()));
            daClass = daClass.getSuperclass();
        } while (daClass.getName() != TestDataObject.class.getName());

        for (Field f : fields) {
            for (Annotation a : f.getAnnotations()) {
                if (a.annotationType() == TestDataField.class) {
                    dataFields.add(f);
                }
            }
        }
    }

    private void setProperties() {
        for (Field f : dataFields) {
            try {
                f.setAccessible(true);
                f.set(this, getFieldValue(f.getName()));
                f.setAccessible(false);
            } catch (IllegalAccessException e) {
                Logger.logError(e);
            }
        }
    }

    private String getFieldValue(String fieldName){
        String value = "";
        LinkedList<String> keys = getFieldKeys(fieldName);
        for (int i=0; i<keys.size(); i++) {
            value = TestDataStorage.get(keys.get(i));
            if (!value.isEmpty())
                break;
        }
        return value;
    }

    private LinkedList<String> getFieldKeys(String fieldName) {
        LinkedList<String> keys = new LinkedList<>();

        String testCaseKey = dataSetName + "." + dataObjectName + "." + fieldName;
        String commonKey = "common." + dataObjectName + "." + fieldName;
        if (!index.isEmpty()) {
            testCaseKey += ("." + index);
            commonKey += ("." + index);
        }

        String envSuffix = System.getProperty("test.env");
        keys.add(testCaseKey + "." + envSuffix);
        keys.add(testCaseKey);
        keys.add(commonKey + "." + envSuffix);
        keys.add(commonKey);

        return keys;
    }
}
