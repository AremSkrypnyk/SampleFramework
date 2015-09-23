package ipreomobile.core;

public class DuplicateListEntryError extends Error {
    public DuplicateListEntryError(String message){
        super(message);
    }

    DuplicateListEntryError(String message, Throwable cause){
        super(message, cause);
    }
}
