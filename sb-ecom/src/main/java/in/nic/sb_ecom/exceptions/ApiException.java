package in.nic.sb_ecom.exceptions;

public class ApiException extends RuntimeException{
    private static final Long serialVersionUID = 1L;

    public ApiException() {
    }

    public ApiException(String message) {
        super(message);
    }
}
