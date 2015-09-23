package ipreomobile.core;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class NumberFormatHelper {
    final static double NUMBER_EXAMPLE = 111222.333;
    public static void verifyNumberFormat(String textValue, Locale locale) {
        NumberFormat nf = NumberFormat.getInstance(locale);
        nf.setMinimumFractionDigits(2);
        try {
            Double doubleValue = nf.parse(textValue).doubleValue();
            String formattedValue = nf.format(doubleValue);
            if (!textValue.equals(formattedValue)) {

                throw new AssertionError("Numerical value ["+textValue+"] should have matched the selected locale ["
                        + locale.getCountry() + "] [Example: " + nf.format(NUMBER_EXAMPLE) + "]."
                );
            }
        } catch (ParseException e) {
            throw new Error("Text value [" + textValue + "] cannot be parsed due to invalid symbols.", e);
        }

    }
}
