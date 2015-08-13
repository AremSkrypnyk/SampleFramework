package ipreomobile.ui;

import ipreomobile.core.*;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.*;


public class ScreenCard {
    private String screenName = StringHelper.splitByCapitals(this.getClass().getSimpleName());
    private static final int DEFAULT_RETRY_INTERVAL = 500;

    private List<CheckpointElement> keyElements = new ArrayList<>();
    private List<CheckpointElement> oneTimeKeyElements = new ArrayList<>();

    private boolean hasChainKeyElements;
    private CheckpointChain checkpointChain;

    private boolean hasLoadingIndicator;
    private SenchaWebElement loadingIndicatorContainer;

    private Long maxWaitTimeout;
    private Long animationTimeoutMillis;
    private long animationTimeoutMultiplier = Long.parseLong(System.getProperty("test.animationLength"));
    private long retryIntervalMillis = DEFAULT_RETRY_INTERVAL;

    private long loadingIndicatorShowSeconds = 1;

    public CheckpointElement addCheckpointElement(By locator) {
        return this.addCheckpointElement(locator, true);
    }

    public CheckpointElement addCheckpointElement(By locator, boolean isPresent) {
        Assert.assertNotNull(locator, "Cannot verify checkpoint element state for ["+getScreenName()+"]: the selector is NULL.");
        CheckpointElement ce = new CheckpointElement(locator, isPresent);
        this.keyElements.add(ce);
        return ce;
    }

    public CheckpointElement addForceStopCheckpointElement(By locator) {
        return addForceStopCheckpointElement(locator, true);
    }

    public CheckpointElement addForceStopCheckpointElement(By locator, boolean isPresent) {
        Assert.assertNotNull(locator, "Cannot verify element state for ["+getScreenName()+"]: the selector is NULL.");
        CheckpointElement ce = new CheckpointElement(locator, isPresent);
        ce.makeForceStopElement();
        this.keyElements.add(ce);
        return ce;
    }

    public CheckpointChain addCheckpointChain(By locator) {
        return addCheckpointChain(locator, true);
    }

    public CheckpointChain addCheckpointChain(By locator, boolean isPresent) {
        hasChainKeyElements = true;
        checkpointChain = new CheckpointChain(locator, isPresent);
        return checkpointChain;
    }

    public CheckpointElement addOneTimeCheckpoint(By locator) {
        return this.addOneTimeCheckpoint(locator, true);
    }

    public CheckpointElement addOneTimeCheckpoint(By locator, boolean isPresent) {
        CheckpointElement ce = new CheckpointElement(locator, isPresent);
        this.oneTimeKeyElements.add(ce);
        return ce;
    }

    public ScreenCard addLoadingIndicatorCheckpoint() {
        hasLoadingIndicator = true;
        return this;
    }

    public ScreenCard addLoadingIndicatorCheckpoint(SenchaWebElement parentItem) {
        hasLoadingIndicator = true;
        loadingIndicatorContainer = parentItem;
        return this;
    }

    public ScreenCard setCheckpointChainParentItem(SenchaWebElement parentItem) {
        checkpointChain.setParentItem(parentItem);
        return this;
    }

    public void resetCheckpointElements(){
        keyElements.clear();
    }

    public void setMaxWaitTimeout(long timeoutInSeconds) {
        maxWaitTimeout = NANOSECONDS.convert(timeoutInSeconds, SECONDS);
    }

    public void setAnimationTimeout(double animationTimeout) {
        this.animationTimeoutMillis = (long) animationTimeout * animationTimeoutMultiplier;
    }

    private void setRetryIntervalMillis(int millis) {
        retryIntervalMillis = millis;
    }

    private void resetRetryIntervalMillis() {
        setRetryIntervalMillis(DEFAULT_RETRY_INTERVAL);
    }

    public void setLoadingIndicatorShowSeconds(long loadingIndicatorShowSeconds) {
        this.loadingIndicatorShowSeconds = loadingIndicatorShowSeconds;
    }

    public void waitReady() {
        Logger.logDebug("Screen " + getScreenName() + ": wait ready.");
        if (maxWaitTimeout == null) {
            setMaxWaitTimeout(Long.parseLong(System.getProperty("test.timeout")));
        }
        Logger.logDebug("Max wait timeout: " + maxWaitTimeout);
        if (animationTimeoutMillis != null) {
            Driver.pause(animationTimeoutMillis);
        }

        verifyLoadingIndicator();
        verifyCheckpointChain();
        verifyCheckpoints();

        oneTimeKeyElements.clear();
    }

    private void verifyCheckpointChain(){
        if (hasChainKeyElements) {
            Logger.logDebug("[" + getScreenName() + "]: requested checkpoint chain check.");
            if (checkpointChain.getStartWaitNanoSeconds() == null) {
                checkpointChain.setStartWaitNanoSeconds(
                        NANOSECONDS.convert(loadingIndicatorShowSeconds, SECONDS)
                );
            }
            checkpointChain.setEndWaitNanoSeconds(maxWaitTimeout);
            if (!checkpointChain.check()) {
                String message = "["+getScreenName()+"] failed to satisfy chain checkpoint condition within " + SECONDS.convert(maxWaitTimeout, NANOSECONDS) + " seconds.\n";
                message += checkpointChain.describe();
                throw new PageNotReadyException(message);
            }
            Logger.logDebug("[" + getScreenName() + "]: checkpoint chain check passed.");
        }
    }

    private void verifyCheckpoints(){
        boolean isReady = false;
        //Driver.nullifyTimeout();
        long startTime = System.nanoTime();
        Logger.logDebug(getScreenName() + ": checkpoints check started.");
        while (System.nanoTime() - startTime <= maxWaitTimeout && !isReady) {
            isReady = verifyCheckpointElements(keyElements);
            if (isReady) {
                isReady = verifyCheckpointElements(oneTimeKeyElements);
            }
        }
        if (!isReady) {
            String message = "["+getScreenName()+"] failed to satisfy checkpoint conditions for some elements within " + SECONDS.convert(maxWaitTimeout, NANOSECONDS) + " seconds.";
            message += "\nPermanent key elements state:" + ((keyElements.isEmpty())?" <none> ":"");
            for (CheckpointElement ce : keyElements) {
                message += ("\n" + ce.describe());
            }
            message +="\nSingle use key elements state:" + ((oneTimeKeyElements.isEmpty())?" <none> ":"");
            for (CheckpointElement ce : oneTimeKeyElements) {
                message += ("\n" + ce.describe());
            }
            throw new PageNotReadyException(message);
        }
        Logger.logDebug(getScreenName() + ": checkpoints check passed.");
        //Driver.resetTimeout();
    }

    public boolean isReady() {
        boolean isReady;

        setRetryIntervalMillis(0);
        if (animationTimeoutMillis != null) {
            Driver.pause(animationTimeoutMillis);
        }
        //Driver.nullifyTimeout();
        isReady = verifyCheckpointElements(keyElements);
        if (isReady) {
            isReady = verifyCheckpointElements(oneTimeKeyElements);
        }

        oneTimeKeyElements.clear();
        resetRetryIntervalMillis();
        //Driver.resetTimeout();

        return isReady;
    }

    private void verifyLoadingIndicator(){
        if (hasLoadingIndicator) {
            Logger.logDebug(getScreenName() + ": requested loading indicator check.");
            Driver.setTimeout((int)loadingIndicatorShowSeconds);
            SenchaWebElement loadingIndicator = Driver.findVisible(By.className("x-loading-mask"), loadingIndicatorContainer);
            Driver.resetTimeout();
            if (loadingIndicator != null) {
                try {
                    Logger.logDebugScreenshot(getScreenName() + ": found loading indicator. It should be gone within " + SECONDS.convert(maxWaitTimeout, NANOSECONDS) + " seconds.");
                    By currentIndicatorLocator = By.id(loadingIndicator.getAttribute("id"));
                    CheckpointElement loadingIndicatorInvisible = new CheckpointElement(currentIndicatorLocator, true)
                            .addVisibilityCondition(false)
                            .setParentItem(loadingIndicatorContainer);
                    CheckpointElement loadingIndicatorDisappeared = new CheckpointElement(currentIndicatorLocator, false)
                            .setParentItem(loadingIndicatorContainer);
                    long startTime = System.nanoTime();
                    while (System.nanoTime() - startTime <= maxWaitTimeout &&
                            !(loadingIndicatorInvisible.check() || loadingIndicatorDisappeared.check())) {
                        Driver.pause(retryIntervalMillis);
                    }
                    if (!(loadingIndicatorInvisible.check() || loadingIndicatorDisappeared.check())) {
                        String errorMessage = getScreenName() + ": Page is not ready. Loading indicator check failed: it is still visible on the screen '"
                                + getScreenName() + "' after " + SECONDS.convert(maxWaitTimeout, NANOSECONDS) + " seconds. \n";
                        if (Logger.isDebugMode()) {
                            errorMessage += "Loading indicator expected state: \n If becomes invisible: \n";
                            errorMessage += loadingIndicatorInvisible.describe();
                            errorMessage += "\n If disappears: \n";
                            errorMessage += loadingIndicatorDisappeared.describe();
                        }
                        throw new PageNotReadyException(errorMessage);
                    }
                    Logger.logDebug(getScreenName() + ": loading indicator is shown and gone. PASS.");
                } catch (StaleElementReferenceException e) {
                    Logger.logDebugScreenshot("Loading indicator was hidden before it could be verified! Proceeding...");
                }
            } else {
                Logger.logDebug(getScreenName() + ": no loading indicator was found. PASS.");
            }
        }
    }

    private boolean verifyCheckpointElements(List<CheckpointElement> checkpoints) {
        boolean isReady = true;
        for (CheckpointElement ce : checkpoints) {
            boolean checkpointPassed = ce.check();
            if (ce.isForceStopElement()) {
                if (checkpointPassed) {
                    throw new PageNotReadyException("Test will be terminated. Page condition indicating an error was found: " + ce.describe());
                }
            } else {
                isReady = isReady && checkpointPassed;
            }

            if (!isReady) {
                Driver.stopIfServicesAreUnavailable();
                Driver.pause(retryIntervalMillis);
                break;
            }
        }
        return isReady;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }
}

