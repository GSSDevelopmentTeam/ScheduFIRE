package control;

public class NotEnoughMembersException extends Exception {
	
	public NotEnoughMembersException() {
		super();
	}
	
	public NotEnoughMembersException(String msg) {
		super(msg);
	}
}
