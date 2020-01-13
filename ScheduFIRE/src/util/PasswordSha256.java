package util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* Classe che si occupa della criptazione password
 * @author Ciro Cipolletta
 */

public class PasswordSha256 {
	/**
	 * Cripta in SHA256 la stringa passata come parametro
	 * @param clearTextPassword la stringa in chiaro da criptare
	 * @return la stringa criptata
	 * @throws NoSuchAlgorithmException in caso di errore
	 */
	public static String getEncodedpassword(String clearTextPassword) throws NoSuchAlgorithmException  {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(clearTextPassword.getBytes());
		
		return HexString.bufferToHex(md.digest()); //effettua il padding finale
		
	}

	/**
	 * Testa se la password in chiaro e la password criptata sono uguali
	 * @param clearTextTestPassword la password in chiaro
	 * @param encodedActualPassword la password criptata
	 * @return TRUE se la password in chiaro è uguale alla criptata, FALSE altrimenti
	 * @throws NoSuchAlgorithmException in caso di errore
	 */
	public static boolean testPassword(String clearTextTestPassword, String encodedActualPassword) throws NoSuchAlgorithmException{
		String encodedTestPassword = getEncodedpassword(clearTextTestPassword);
		 return (encodedTestPassword.equals(encodedActualPassword));
		
	}
	
}
