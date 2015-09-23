package ipreomobile.core;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CollectionHelper {
    public static void removeNull(List<?> list) {
        list.removeAll(Collections.singleton(null));
    }

    public static List<String> toList(String[] strings) {
        return Arrays.asList(strings);
    }

    public static String[] toArray(List<String> list) {
        String[] result = new String[list.size()];
        result = list.toArray(result);
        return result;
    }

}
