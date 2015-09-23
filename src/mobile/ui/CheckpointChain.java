package ipreomobile.ui;

import ipreomobile.core.Driver;
import ipreomobile.core.ElementHelper;
import ipreomobile.core.Logger;
import ipreomobile.core.SenchaWebElement;
import org.openqa.selenium.By;

import java.util.LinkedList;

import static java.util.concurrent.TimeUnit.*;

public class CheckpointChain extends CheckpointElement {
    private long endWaitNanoSeconds;
    private Long startWaitNanoSeconds = NANOSECONDS.convert(1, SECONDS);
    private long timeout;

    private long startRetryIntervalMilliSeconds = 100;
    private long endRetryIntervalMilliSeconds = 500;
    private long retryInterval;

    private long startTime;

    private LinkedList<Checkpoint> startCheckpointList;
    private LinkedList<Checkpoint> endCheckpointList;

    public CheckpointChain(By locator, boolean isPresent) {
        super(locator, isPresent);
    }

    public CheckpointChain addClosingCheckpointStage(){
        startCheckpointList = new LinkedList<>(checkpointList);
        checkpointList = new LinkedList<>();
        return this;
    }

    public CheckpointChain addClosingCheckpoint(boolean isPresent){
        addClosingCheckpointStage();
        addPresenceCondition(isPresent);
        return this;
    }

    public void setEndWaitNanoSeconds(long endWaitNanoSeconds) {
        this.endWaitNanoSeconds = endWaitNanoSeconds;
    }

    public Long getStartWaitNanoSeconds() {
        return startWaitNanoSeconds;
    }

    public CheckpointChain setStartWaitNanoSeconds(long leadTimeoutNanoSeconds) {
        this.startWaitNanoSeconds = leadTimeoutNanoSeconds;
        return this;
    }

    public boolean check() {
        if (endCheckpointList == null) {
            endCheckpointList = new LinkedList<>(checkpointList);
            checkpointList = new LinkedList<>();
        }
        timeout = startWaitNanoSeconds;
        retryInterval = startRetryIntervalMilliSeconds;

        if (isCheckpointReached(startCheckpointList)) {
            Logger.logDebug("Checkpoint chain: start detected.");
            timeout = endWaitNanoSeconds;
            Logger.logDebug(SECONDS.convert(timeout, NANOSECONDS) + " seconds waiting for the 2nd stage to be reached.");

            retryInterval = endRetryIntervalMilliSeconds;
            return isCheckpointReached(endCheckpointList);
        }
        return true;
    }

    public String describe(){
        String message = " * Element ["+ locator.toString() + "] had the following conditions to satisfy: ";
        message += "\n Start condition: ";
        for (Checkpoint c : startCheckpointList) {
            message += "\n" + c.describe();
        }
        message += "\n End condition: ";
        for (Checkpoint c : endCheckpointList) {
            message += "\n" + c.describe();
        }
        if (parentItem != null) {
            message += "\n * Parent item description: ";
            message += ElementHelper.describe(parentItem);
        }
        SenchaWebElement chainElement = Driver.findIfExists(locator, parentItem);
        if (chainElement != null) {
            message += "\n * Element state: ";
            message += ElementHelper.describe(chainElement);
        }

        return message;
    }

    private boolean isCheckpointReached(LinkedList<Checkpoint> list){
        boolean passed;
        Logger.logDebug("Checkpoint chain: stage timeout = "+ SECONDS.convert(timeout, NANOSECONDS));
        checkpointList = list;
        startTime = System.nanoTime();

        do {
            passed = super.check();
            if (!passed) {
                Driver.pause(retryInterval);
            }
        } while (! (isTimeout() || passed));
        return passed;
    }

    private boolean isTimeout(){
        long currentTime =  System.nanoTime();
        long timePassed =   currentTime - startTime;
        boolean result =    (timePassed >= timeout);
        Logger.logDebug("Current time: " + currentTime +
                "; start time: " + startTime +
                "; passed time: " + timePassed +
                " timeout: "+ timeout +
                "; isTimeout = "+result);
        return result;
    }

}
