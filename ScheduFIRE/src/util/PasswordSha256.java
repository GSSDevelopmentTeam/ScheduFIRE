package util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* Classe che si occupa della criptazione password
 * @author Ciro Cipolletta
 */

public class PasswordSha256 {

	public static String getEncodedpassword(String clearTextPassword) throws NoSuchAlgorithmException  {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(clearTextPassword.getBytes());
		
		return HexString.bufferToHex(md.digest()); //effettua il padding finale
		
	}

	public static boolean testPassword(String clearTextTestPassword, String encodedActualPassword) throws NoSuchAlgorithmException{
		String encodedTestPassword = getEncodedpassword(clearTextTestPassword);
		 return (encodedTestPassword.equals(encodedActualPassword));
		
	}
	
}
