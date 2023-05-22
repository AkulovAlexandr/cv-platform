package by.akulov.java.cvp.exception;

public class ServerErrorException extends RuntimeException {
    public ServerErrorException() {
        super("Ошибка сервера");
    }

}
