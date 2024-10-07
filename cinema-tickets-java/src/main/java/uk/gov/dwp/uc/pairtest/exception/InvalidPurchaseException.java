package uk.gov.dwp.uc.pairtest.exception;

public class InvalidPurchaseException extends RuntimeException {
	
	private static final long serialVersionUID = 244466666L;
	
	public InvalidPurchaseException(String message) {
		super(message);
	}
}
