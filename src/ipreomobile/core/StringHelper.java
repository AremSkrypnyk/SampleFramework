package ipreomobile.core;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class StringHelper {

    public static String nameArrayToString(CharSequence ... names) {
        return "\n\t* '" + String.join("',\n\t* '", names) + "'";
    }

    public static String nameArrayToString(Collection<? extends CharSequence> names) {
        return nameArrayToString(listToArray(names));
    }

    public static boolean containsAllWords(String fullString, String... words){
        boolean result = true;
        for (int i=0; i<words.length; i++) {
            if (fullString.contains(words[i])) {
                fullString = fullString.replace(words[i], "");
            }
            else {
                result = false;
                break;
            }
        }
        return result;
    }

    public static boolean containsAllWords(String fullString, String incompleteString){
        boolean result = true;
        String[] words = incompleteString.split("\\s+");
        for (int i=0; i<words.length; i++) {
            if (fullString.contains(words[i])) {
                if (words[i].contains("(")){
                    words[i] = words[i].replaceAll("\\(", "\\\\(");
                }
                if (words[i].contains(")")){
                    words[i] = words[i].replaceAll("\\)", "\\\\)");
                }
                fullString = fullString.replaceFirst(words[i], "");
            }
            else {
                result = false;
                break;
            }
        }
        return result;
    }

    public static String durationToTimeStr(long millis) {
        return String.format("%d min %d sec",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
    }

    public static String splitByCapitals(String input){
        return StringUtils.capitalize(String.join(" ", input.split("(?=\\p{Upper})")));
    }


    public static String[] listToArray(Collection<? extends CharSequence> col) {
        return col.toArray(new String[col.size()]);
    }

}
