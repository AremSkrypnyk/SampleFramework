package ipreomobile.core;

public class PageNotReadyException extends RuntimeException {

    public PageNotReadyException(String message){
        super(message);
    }

    public PageNotReadyException(String message, Throwable cause){
        super(message, cause);
    }
}
