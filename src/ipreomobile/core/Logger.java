package ipreomobile.core;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestNG;

import static ipreomobile.core.MessageType.*;

enum MessageType {
    ERROR, SUCCESS, WARNING, MESSAGE, DEBUG, SCREENSHOT, START;

    String getLabel() {
        return "[" + StringUtils.center(this.toString(), 10, " ") + "]";
    }
}

public class Logger {

    private static final String REPORTS_DIR_DATE_FORMAT = "MM-dd-yy__HH-mm";
    private static final String LOG_TIMESTAMP_DATE_FORMAT = "MMM/dd HH:mm:ss.SSS";
    private static final String SCREENSHOT_TIMESTAMP_FORMAT = "MM-dd_HH-mm-ss_sss";
    private static final String DEBUG_INDICATOR = "********************************";
    private static final String HTML_TAB = "&nbsp;&nbsp;&nbsp;&nbsp;";
    private static final String TRACE_ELEMENT_PREFIX = "ipreomobile.";
    public static final String DEFAULT_REPORTS_PATH = "test-output";
    private static String reportsDir;
    private static boolean verboseFlag;
    private static MessageType lastLabel = MESSAGE;
    private static DebugOutputConfiguration debugConfiguration;
    private static String loggerPackage = Logger.class.getName();

    public static void init(String suiteName) {
        reportsDir = makeReportsDir(suiteName);
        verboseFlag = Boolean.parseBoolean(System.getProperty("test.debug"));
        Reporter.setEscapeHtml(false);
        debugConfiguration = new DebugOutputConfiguration();
    }

    public static String getTestName(){
        //String name = StringHelper.splitByCapitals(Reporter.getCurrentTestResult().getName());
        String name = "Configuration";
        if (Reporter.getCurrentTestResult() != null) {
            name = StringHelper.splitByCapitals(Reporter.getCurrentTestResult().getName());
        }
        return name;
    }

    public static void logTestStarted(String testName) {
        String logEntry = decorateLogEntry("Test [" + StringHelper.splitByCapitals(testName) + "] (method name: " + testName + ") started.", START);
        System.out.println(logEntry);
    }

    public static void setDebugMode(boolean flag) {
        if (flag)
            Logger.logDebugStarted();
        else
            Logger.logDebugEnded();
        verboseFlag = flag;
    }

    public static boolean isDebugMode() {
        return verboseFlag;
    }

    public static DebugOutputConfiguration configureDebugOutput() {
        return debugConfiguration;
    }


    public static void logSuccess(String... msg) {
        for (String s : msg) {
            log(decorateLogEntry(s, SUCCESS));
        }
    }

    public static void logSuccess(Iterable<? extends CharSequence> msg) {
        for (CharSequence s : msg) {
            log(decorateLogEntry((String) s, SUCCESS));
        }
    }

    public static void logWarning(String... msg) {
        for (String s : msg) {
            log(decorateLogEntry(s, WARNING));
        }
    }

    public static void logWarning(Iterable<? extends CharSequence> msg) {
        for (CharSequence s : msg) {
            log(decorateLogEntry((String) s, WARNING));
        }
    }

    public static void logMessage(String... msg) {
        for (String s : msg) {
            log(decorateLogEntry(s, MESSAGE));
        }
    }

    public static void logMessage(Iterable<? extends CharSequence> msg) {
        for (CharSequence s : msg) {
            log(decorateLogEntry((String) s, MESSAGE));
        }
    }

    public static void logDebugStarted() {
        Logger.log(DEBUG_INDICATOR + " Debug output start " + DEBUG_INDICATOR);
    }

    public static void logDebugEnded() {
        Logger.log(DEBUG_INDICATOR + " Debug output end " + DEBUG_INDICATOR);
    }

    public static void logDebug(String... msg) {
        logDebug( Arrays.asList(msg) );
    }

    public static void logDebug(List<String> msg) {
        if (verboseFlag) {
            String callingClass = getCallingClass();
            if (callingClass != null) {
                if (debugConfiguration.isMessageEnabled(callingClass)) {
                    msg.set(0, "[" + callingClass.replace(TRACE_ELEMENT_PREFIX, "") + "]: " + msg.get(0));
                    for (String s : msg) {
                        log(decorateLogEntry(s, DEBUG));
                    }
                }
                if (debugConfiguration.isStackTraceEnabled(callingClass)) {
                    logStackTrace(Thread.currentThread().getStackTrace());
                }
                if (debugConfiguration.isScreenshotEnabled(callingClass)) {
                    logScreenshot(msg.get(0));
                }
            }

        }
    }

    private static String getCallingClass(){
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTrace) {
            if (isStackTraceElementEffective(element)) {
                return element.getClassName();
            }
        }
        return null;
    }



    public static void logError(String msg, Throwable t) {
        logError(new Throwable(msg + ": " + t.getMessage(), t));
    }

    public static void logError(String... msg) {
        markTestAsFailed();
        logErrorHeader("An error occurred during '" + getTestName() + "' execution. Execution is NOT interrupted, proceeding.");

        String errorMessage = StringUtils.join(msg, "\n");
        logErrorMessage(errorMessage);

        logStackTrace(Thread.currentThread().getStackTrace());
        logErrorScreenshot(getTestName() + "-error");
    }

    public static void logError(Throwable t) {
        logErrorHeader("Test case '" + getTestName() + "' execution was interrupted with exception " + t.getClass());

        String errorMessage = t.getMessage();
        logErrorMessage(errorMessage);

        logStackTrace(t.getStackTrace());
        logErrorScreenshot(getTestName() + "-error");
    }

    public static void logError(ITestResult tr) {
        Reporter.setCurrentTestResult(tr);
        logError(tr.getThrowable());
    }

    private static void logErrorHeader(String str){
        log(decorateLogEntry(str, ERROR));
    }
    private static void logErrorMessage(String str){
        if (str != null) {
            String[] messageLines = str.split("\\r?\\n");
            log("\t* Error message: " + messageLines[0]);
            logErrorDetailsFromMsg(messageLines);
        } else {
            log("\t* No error message provided.");
        }
    }

    private static void logErrorDetailsFromMsg(String[] details){
        //First line is an error message line, it has been logged through logErrorMessage and is skipped here.
        if (details.length > 1) {
            log("\t* Details: ");
            for (int i = 1; i < details.length; i++) {
                log("\t\t" + details[i]);
            }
        }
    }
    public static void logStackTrace(StackTraceElement[] stackTrace){
        if (stackTrace.length > 0) {
            log("\t* Stack trace:");

            for (int i = 0; i < stackTrace.length; i++) {
                if (isStackTraceElementEffective(stackTrace[i])) {
                    log("\t\tat " + stackTrace[i]);
                }
            }
        }
    }

    private static boolean isStackTraceElementEffective(StackTraceElement element) {
        return element.toString().contains(TRACE_ELEMENT_PREFIX) && !element.toString().contains(loggerPackage);
    }

    public static void logDebugScreenshot(String description) {
        if (verboseFlag)
            logScreenshot(description);
    }

    public static void logScreenshot() {
        logScreenshot(Reporter.getCurrentTestResult().getName());
    }

    public static void logScreenshot(String screenshotName) {
        String msgPrefix = decorateLogEntry("Saved a screenshot (image is clickable) named ", SCREENSHOT);
        postScreenshot(screenshotName, msgPrefix);
    }

    private static void logErrorScreenshot(String screenshotName) {
        String msgPrefix = "\t* Screenshot on error: (image is clickable) ";
        postScreenshot(screenshotName, msgPrefix);
    }

    private static void postScreenshot(String screenshotName, String messagePrefix){
        if (Driver.isInitialized()) {
            File inputFile = Driver.getScreenshot();
            String outputDir = getReportsDir() + "/screenshots";
            new File(outputDir).mkdirs();

            String outputFileName = getScreenshotFileName(screenshotName);
            String outputPath = outputDir + "/" + outputFileName;

            File outputFile = new File(outputPath);
            try {
                FileUtils.copyFile(inputFile, outputFile);

                Reporter.log(messagePrefix + generateHTMLImgWrapper(outputFile.getCanonicalPath(), outputFileName), false);
                System.out.println(messagePrefix + outputFile.getCanonicalPath());
            } catch (IOException e) {
                logError("Failed to save a screenshot named " + outputFileName, e);
            }
        } else {
            Reporter.log(messagePrefix + "Failed to save a screenshot named "+screenshotName+": driver is not initialized.");
        }
    }

    private static String getScreenshotFileName(String screenshotName) {
        String uniqueSuffix = "-" + System.nanoTime() + ".png";
        int filenameLength = 255 - uniqueSuffix.length();
        DateFormat dateFormat = new SimpleDateFormat(SCREENSHOT_TIMESTAMP_FORMAT);

        String formattedName = screenshotName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_").replaceAll("_+", "_");
        formattedName = dateFormat.format(new Date()) + "-" + formattedName;
        if (formattedName.length() > filenameLength) {
            formattedName = formattedName.substring(0, filenameLength - 1);
        }
        formattedName = formattedName + uniqueSuffix;
        return formattedName;
    }

    private static String generateHTMLImgWrapper(String filePath, String imgName) {
        return "<a href = \"" + filePath + "\">" + imgName + "<br/>" +
                "<img width=\"50%\" src=\"file:///" + filePath + "\"/></br></a>";
    }

    public static String getReportsDir() {
        return reportsDir;
    }

    private static void log(String msg) {
        System.out.println(msg);
        String msgWithHtmlLineBreaks = msg.replaceAll("\n", "</br>").replaceAll("\t", HTML_TAB);

        Reporter.log(msgWithHtmlLineBreaks + "</br>", false);
    }

    private static String decorateLogEntry(String msg, MessageType type) {
        if (lastLabel.equals(ERROR) != type.equals(ERROR)) {
            log(DEBUG_INDICATOR);
        }
        lastLabel = type;
        return type.getLabel() + getTimeStamp() + ": " + msg;
    }

    private static String getTimeStamp() {
        DateFormat dateFormat = new SimpleDateFormat(LOG_TIMESTAMP_DATE_FORMAT);
        return dateFormat.format(new Date());
    }

    private static void markTestAsFailed(){
        AssertionError error = new AssertionError("Test '"+getTestName()+"' failed due to the errors found during the execution.");
        StackTraceElement[] stackTrace = {};
        error.setStackTrace( stackTrace );

        ITestResult tr = Reporter.getCurrentTestResult();
        tr.setStatus(ITestResult.FAILURE);
        tr.setThrowable(error);
    }

    @SuppressWarnings("deprecation")
    private static String makeReportsDir(String suiteName) {
        String pathForReport = "";
        if (TestNG.getDefault().getOutputDirectory() == DEFAULT_REPORTS_PATH) {
            DateFormat dateFormat = new SimpleDateFormat(REPORTS_DIR_DATE_FORMAT);
            pathForReport = "reports/" + suiteName + "_" + dateFormat.format(new Date()).toString();

        } else {
            pathForReport = TestNG.getDefault().getOutputDirectory();
        }
        new File(pathForReport).mkdirs();
        TestNG.getDefault().setOutputDirectory(pathForReport);
        return pathForReport;
    }
}
