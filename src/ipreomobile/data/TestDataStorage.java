package ipreomobile.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Daryna_Chernysheva on 5/26/2014.
 */
public class TestDataStorage {

    private static Properties fullTestData;
    private static final String FILENAME_FORMAT =  getPath() + "/storage/%s.properties";
    private static final String product = System.getProperty("test.product");


    public static void init() {
        try {
            String defaultDataFileName = String.format(FILENAME_FORMAT, "base");
            fullTestData = readDataFile(defaultDataFileName);
            if (product != "base") {
                String productDataFileName = String.format(FILENAME_FORMAT, product);
                Properties productTestData = readDataFile(productDataFileName);
                fullTestData.putAll(productTestData);
            }
        } catch (IOException | NullPointerException e) {
            throw new Error("Failed to read test data objects information: " + e.getMessage(), e);
        }
    }

    private static String getPath(){
        String pack = TestDataStorage.class.getPackage().getName();
        return pack.replaceAll("\\.", "/");
    }

    public static String get(String key) {
        return fullTestData.getProperty(key, "");
    }

    protected static Properties readDataFile(String filename) throws IOException{
        Properties data = new Properties();
        InputStream in = new TestDataStorage().getClass().getClassLoader().getResourceAsStream(filename);
        data.load(in);
        return data;
    }
}
