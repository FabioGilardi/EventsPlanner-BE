package FabioGilardi.U5W3D5.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(long id) {
        super("Record with id: " + id + " has not been found!");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
