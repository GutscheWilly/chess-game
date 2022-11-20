package chess.exceptions;

public class ChessException extends RuntimeException {
    
    public static final long serialVersionUID = 1L;

    public ChessException(String message) {
        super(message);
    }
}
