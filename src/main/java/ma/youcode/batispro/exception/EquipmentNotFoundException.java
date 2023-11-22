package ma.youcode.batispro.exception;

public class EquipmentNotFoundException extends RuntimeException{
    public EquipmentNotFoundException(String message) {    // constructor
        super(message);
    }
}
