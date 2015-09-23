package ipreomobile.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class DebugOutputConfiguration {

    private static final String DEBUG_PROPERTIES_FILE = "debug.properties";
    private Properties debugProperties;

    private boolean MESSAGE_BY_DEFAULT      = true;
    private boolean STACKTRACE_BY_DEFAULT   = false;
    private boolean SCREENSHOT_BY_DEFAULT   = false;

    public DebugOutputConfiguration(){
        debugProperties = new Properties();
        FileInputStream propFile;
        try {
            propFile = new FileInputStream(DEBUG_PROPERTIES_FILE);
            debugProperties.load(propFile);
        } catch (IOException e) {
            throw new Error("Failed to read properties file '" + DEBUG_PROPERTIES_FILE+"': "+e.getMessage(), e);
        }
        Enumeration<String> e = (Enumeration<String>) debugProperties.propertyNames();
    }

    public boolean isMessageEnabled(String projectClassName){
        return getPropertyState(projectClassName, OutputType.message);
    }

    public boolean isStackTraceEnabled(String projectClassName){
        return getPropertyState(projectClassName, OutputType.stacktrace);
    }

    public boolean isScreenshotEnabled(String projectClassName){
        return getPropertyState(projectClassName, OutputType.screenshot);
    }

    public DebugOutputConfiguration enableAllMessages() {
        return setOutputTypeForAllClasses(OutputType.message, true);
    }

    public DebugOutputConfiguration enableAllStackTraces() {
        return setOutputTypeForAllClasses(OutputType.stacktrace, true);
    }

    public DebugOutputConfiguration enableAllScreenshots() {
        return setOutputTypeForAllClasses(OutputType.screenshot, true);
    }

    public DebugOutputConfiguration disableAllMessages() {
        return setOutputTypeForAllClasses(OutputType.message, false);
    }

    public DebugOutputConfiguration disableAllStackTraces() {
        return setOutputTypeForAllClasses(OutputType.stacktrace, false);
    }

    public DebugOutputConfiguration disableAllScreenshots() {
        return setOutputTypeForAllClasses(OutputType.screenshot, false);
    }

    private DebugOutputConfiguration setOutputTypeForAllClasses(OutputType outputType, boolean state) {
        for (String key : debugProperties.stringPropertyNames()) {
            if (key.endsWith(outputType.toString())) {
                setPropertyState(key, state);
            }
        }
        return this;
    }

    public DebugOutputConfiguration enableMessage(Class projectClass) {
        String key = buildKey(projectClass.getName(), OutputType.message);
        setPropertyState(key, true);
        return this;
    }

    public DebugOutputConfiguration enableStackTrace(Class projectClass) {
        String key = buildKey(projectClass.getName(), OutputType.stacktrace);
        setPropertyState(key, true);
        return this;
    }

    public DebugOutputConfiguration enableScreenshot(Class projectClass) {
        String key = buildKey(projectClass.getName(), OutputType.screenshot);
        setPropertyState(key, true);
        return this;
    }

    public DebugOutputConfiguration disableMessage(Class projectClass) {
        String key = buildKey(projectClass.getName(), OutputType.message);
        setPropertyState(key, false);
        return this;
    }

    public DebugOutputConfiguration disableStackTrace(Class projectClass) {
        String key = buildKey(projectClass.getName(), OutputType.stacktrace);
        setPropertyState(key, false);
        return this;
    }

    public DebugOutputConfiguration disableScreenshot(Class projectClass) {
        String key = buildKey(projectClass.getName(), OutputType.screenshot);
        setPropertyState(key, false);
        return this;
    }

    private String buildKey(String projectClassName, OutputType outputType) {
        return projectClassName + "." + outputType.toString();
    }

    private void setPropertyState(String key, boolean state) {
        debugProperties.setProperty(key, state + "");
    }

    private boolean getPropertyState(String projectClassName, OutputType outputType){
        String key = buildKey(projectClassName, outputType);
        if (!debugProperties.containsKey(key)) {
            switch(outputType) {
                case message:
                    setPropertyState(key, MESSAGE_BY_DEFAULT);
                    break;
                case stacktrace:
                    setPropertyState(key, STACKTRACE_BY_DEFAULT);
                    break;
                case screenshot:
                    setPropertyState(key, SCREENSHOT_BY_DEFAULT);
                    break;
                default:
                    throw new Error("Unexpected output type: "+outputType);
            }
        }
        return Boolean.parseBoolean(debugProperties.getProperty(key));
    }

    enum OutputType { message, screenshot, stacktrace }


}
