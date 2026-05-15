package bankAccount.exception;

public class IllegalTransferDataException extends RuntimeException {
    public IllegalTransferDataException(String message) {
        super(message);
    }
}
