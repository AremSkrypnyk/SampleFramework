package ipreomobile.core;

import org.testng.Assert;

import java.util.Collection;

public class Verify {

    public static void verifyTrue(boolean b, String message) {
        try {
            Assert.assertTrue(b);
        } catch (AssertionError e) {
            Logger.logError(message);
        }
    }

    public static void verifyFalse(boolean b, String message) {
        try {
            Assert.assertFalse(b);
        } catch (AssertionError e) {
            Logger.logError(message);
        }
    }

    public static void verifyEquals(boolean actual, boolean expected, String message) {
       try {
           Assert.assertEquals(actual, expected);
       } catch (AssertionError e) {
            Logger.logError(message);
       }
    }

    public static void verifyEquals(String actual, String expected, String message) {
        try {
            Assert.assertEquals(actual, expected);
        } catch (AssertionError e) {
            Logger.logError(message + "\nExpected value: ["+expected+"], actual value: ["+actual+"].");
        }
    }
    public static void verifyEqualsIgnoreCase(String actual, String expected, String message) {
        if (!actual.equalsIgnoreCase(expected)) {
            Logger.logError(message + " (Comparison ignores case.)\nExpected value: ["+expected+"], actual value: ["+actual+"].");
        }
    }

    public static void verifyEquals(Collection<? extends String> actual, Collection<? extends String> expected, String message) {
        try {
            Assert.assertEquals(actual, expected);
        } catch (AssertionError e) {
            Logger.logError( message
                            + "\nExpected data set:\n" + StringHelper.nameArrayToString(expected)
                            + "\nActual data set:\n" + StringHelper.nameArrayToString(actual)
            );
        }
    }

    public static void verifyEquals(double actual, double expected, double delta, String message) {
        try {
            Assert.assertEquals(actual, expected, delta);
        } catch (AssertionError e) {
            Logger.logError(message + "\nExpected value: ["+expected+"], actual value: ["+actual+"], allowed delta: ["+delta+"].");
        }
    }

    public static void verifyMoreOrEquals(int actual, int expected, String message) {
        try {
            Assert.assertTrue(actual >= expected);
        } catch (AssertionError e) {
            Logger.logError(message + "\nExpected value to be >= ["+expected+"], actual value: ["+actual+"].");
        }
    }

    public static void verifyMoreOrEquals(double actual, double expected, String message) {
        try {
            Assert.assertTrue(actual >= expected);
        } catch (AssertionError e) {
            Logger.logError(message + "\nExpected value to be >= ["+expected+"], actual value: ["+actual+"].");
        }
    }

    public static void verifyMoreThan(int actual, int expected, String message) {
        try {
            Assert.assertTrue(actual > expected);
        } catch (AssertionError e) {
            Logger.logError(message + "\nExpected value to be > ["+expected+"], actual value: ["+actual+"].");
        }
    }

    public static void verifyLessOrEquals(int actual, int expected, String message) {
        try {
            Assert.assertTrue(actual <= expected);
        } catch (AssertionError e) {
            Logger.logError(message + "\nExpected value to be <= ["+expected+"], actual value: ["+actual+"].");
        }
    }

    public static void verifyLessThan(int actual, int expected, String message) {
        try {
            Assert.assertTrue(actual < expected);
        } catch (AssertionError e) {
            Logger.logError(message + "\nExpected value to be < ["+expected+"], actual value: ["+actual+"].");
        }
    }

    public static void verifyContainsText(String string, String substring, String message) {
        try {
            Assert.assertTrue(string.contains(substring));
        } catch (AssertionError e) {
            Logger.logError(message + "\nSubstring ["+substring+"] was not found in ["+string+"].");
        }
    }

    public static void verifyStartsWith(String string, String substring, String message) {
        try {
            Assert.assertTrue(string.startsWith(substring));
        } catch (AssertionError e) {
            Logger.logError(message + "\nString ["+string+"] expected to start with ["+substring+"].");
        }
    }

    public static void verifyEmpty(String string, String message) {
        try {
            Assert.assertTrue(string.isEmpty());
        } catch (AssertionError e) {
            Logger.logError(message + "\nExpected ["+string+"] to be empty.");
        }
    }

    public static void verifyEmpty(Collection col, String message) {
        try {
            Assert.assertTrue(col.size() == 0);
        } catch (AssertionError e) {
            Logger.logError(message + "\nExpected collection to be empty, but found "+StringHelper.nameArrayToString(col)+".");
        }
    }

    public static void verifyNotEmpty(String string, String message) {
        try {
            Assert.assertFalse(string.isEmpty());
        } catch (AssertionError e) {
            Logger.logError(message + "\nString is empty, but should not be.");
        }
    }

    public static void verifyNotEmpty(Collection col, String message) {
        try {
            Assert.assertFalse(col.isEmpty());
        } catch (AssertionError e) {
            Logger.logError(message + "\nCollection is empty, but should not be.");
        }
    }

    public static void verifyNull(Object o, String message) {
        try {
            Assert.assertNotNull(o);
        } catch (AssertionError e) {
            Logger.logError(message + "\n"+o.toString()+" found, but [NULL] expected.");
        }
    }

    public static void verifyNotNull(Object o, String message) {
        try {
            Assert.assertNotNull(o);
        } catch (AssertionError e) {
            Logger.logError(message + "\n[NULL] found.");
        }
    }
}
