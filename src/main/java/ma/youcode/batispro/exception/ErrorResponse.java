package ma.youcode.batispro.exception;

public record ErrorResponse(
        String timeStamp,
        Integer status,
        String error,
        String message,
        String path
) {
}
