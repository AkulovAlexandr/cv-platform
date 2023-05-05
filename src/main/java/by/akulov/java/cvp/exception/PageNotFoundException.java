package by.akulov.java.cvp.exception;

public class PageNotFoundException extends RuntimeException{

    public PageNotFoundException() {
        super("Страница не найдена");
    }
}
