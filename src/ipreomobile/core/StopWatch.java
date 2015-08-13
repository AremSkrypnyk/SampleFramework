package ipreomobile.core;

import java.util.concurrent.TimeUnit;

public class StopWatch {
    private long timeout;
    private long startTime;

    public StopWatch(){
        setTimeoutInSeconds(Long.parseLong(System.getProperty("test.timeout")));
    }

    public StopWatch(long timeInNanoseconds){
        timeout = timeInNanoseconds;
    }

    public void setTimeoutInSeconds(long timeoutInSeconds){
        timeout = TimeUnit.NANOSECONDS.convert(timeoutInSeconds, TimeUnit.SECONDS);
    }

    public void start(){
        startTime = System.nanoTime();
    }

    public boolean isTimeout(){
        long currentTime = System.nanoTime();
        return (currentTime - startTime >= timeout);
    }
}
