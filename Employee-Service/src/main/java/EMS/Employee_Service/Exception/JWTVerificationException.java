package EMS.Employee_Service.Exception;

public class JWTVerificationException extends Exception{
	public JWTVerificationException(String message) {
		super(message);
	}
}
