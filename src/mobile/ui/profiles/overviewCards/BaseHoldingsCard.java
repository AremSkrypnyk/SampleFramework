package ipreomobile.ui.profiles.overviewCards;

import ipreomobile.core.Driver;
import ipreomobile.core.Logger;
import ipreomobile.core.XPathBuilder;
import ipreomobile.templates.ui.ProfileOverviewCard;
import ipreomobile.templates.ui.ProfileOverviewCardsList;
import ipreomobile.ui.blocks.KeyValueTable;
import org.openqa.selenium.By;
import ipreomobile.core.SenchaWebElement;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Pattern;

public class BaseHoldingsCard extends ProfileOverviewCard {

    protected KeyValueTable informationTable = new KeyValueTable(
            new XPathBuilder().byClassName("information", "row").byClassName("column").byXpathPart("[div[@class='label']]").build(),
            new XPathBuilder().byClassName("label").build(),
            new XPathBuilder().byClassName("value").build()
    );
    private String[] fieldsWithCurrencyIdentifier = {"Equity Assets (%s)", "Purchasing Power (%s)"};
    private String currency = System.getProperty("test.currency");


    protected void setupTable(){
        informationTable.setContainer(container);
    }

    public String getValue(String label){
        return informationTable.getValue(label);
    }

    public BaseHoldingsCard verifyLabel(String label, String expectedValue){
        informationTable.verifyValue(label, expectedValue);
        return this;
    }

    public BaseHoldingsCard verifyLabelPresent(String label){
        informationTable.verifyKeyPresent(label);
        return this;
    }

    public BaseHoldingsCard verifyLabelsPresent(Iterable<String> labels){
        informationTable.verifyKeysPresent(labels);
        return this;
    }

    public BaseHoldingsCard verifyCurrency(){
        String expectedCurrency = System.getProperty("test.currency");
        for (int i = 0; i< fieldsWithCurrencyIdentifier.length; i++) {
            String expectedLabel = String.format(fieldsWithCurrencyIdentifier[i], expectedCurrency);
            informationTable.verifyKeyPresent(expectedLabel);
        }
        return this;
    }

    public double getEquityAssetValue(){
        String textValue = informationTable.getValue("Equity Assets ("+currency+")");
        int multiplier;
        long result = 0;
        if (textValue.endsWith("M")) {
            multiplier = 1000000;
            textValue = textValue.replaceAll("M", "");
        } else {
            multiplier = 1;
        }
        NumberFormat format = NumberFormat.getInstance(Locale.US);
        try {
            result = format.parse(textValue).longValue() * multiplier;
        } catch (ParseException e) {
            throw new Error("Failed to parse Equity Asset value "+textValue, e);
        }
        return result;

//        int iteration = 0;
//
//        textValue = Pattern.compile(",").matcher(textValue).replaceAll("");
//
//        do{
//            textValue = Pattern.compile("M").matcher(textValue).replaceFirst("");
//            iteration++;
//        }while(textValue.contains("M"));
//
//        double value = Double.parseDouble(textValue);
//
//        if (iteration!=0) {
//            for (int i = 0; i < iteration; i++) {
//                value = value*1000000;
//            }
//        }
//        return value;
    }
}
