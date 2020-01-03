package util;

import java.security.NoSuchAlgorithmException;

public class ProvaPass256 {

	public static void main(String[] args) throws NoSuchAlgorithmException{
		System.out.println(PasswordSha256.getEncodedpassword("capoturno"));

	}

}
