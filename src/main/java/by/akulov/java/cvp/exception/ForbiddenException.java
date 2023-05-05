package by.akulov.java.cvp.exception;

public class ForbiddenException extends RuntimeException{

    public ForbiddenException() {
        super("Доступ запрещен!");
    }

}
