package cristinapalmisani.BEU2W3L1.exceptions;

public class NotFoundException extends RuntimeException {
	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(int id) {
		super(id + " non trovato!");
	}
}