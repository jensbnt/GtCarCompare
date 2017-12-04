package jensbnt.database;

/* This exception should be thrown when there is an error and the program can't continue.
 * Other exceptions should be handled internally. */

@SuppressWarnings("serial")
public class CarLoadException extends Exception {
	 public CarLoadException() { super(); }
	 public CarLoadException(String message) { super(message); }
	 public CarLoadException(String message, Throwable cause) { super(message, cause); }
	 public CarLoadException(Throwable cause) { super(cause); }
}
