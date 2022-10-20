package exceptions;

import java.io.FileNotFoundException;

public class UnreadableFileException extends FileNotFoundException {
    public UnreadableFileException(String message) {
        super(message);
    }
}
