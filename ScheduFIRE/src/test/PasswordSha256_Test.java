package test;

import static org.junit.jupiter.api.Assertions.*;
import java.security.NoSuchAlgorithmException;
import org.junit.jupiter.api.Test;
import util.*;

class PasswordSha256_Test {

	@Test
	void getEncodedPassword_Test() throws NoSuchAlgorithmException {
		String passEncriptExpected = "BCEE59C152CF518D3C6FBBCE6EB4E7CA3757323D5939A59B816197DFAA372EE4";
	
		String passEncript = PasswordSha256.getEncodedpassword("capoturno");
		
		assertEquals(passEncriptExpected, passEncript);
	}
	
	@Test
	void testPassword_Test() throws NoSuchAlgorithmException {
		boolean expect = true;
		
		boolean testPass = PasswordSha256.testPassword("capoturno", PasswordSha256.getEncodedpassword("capoturno"));
		assertEquals(expect, testPass);
	}
	
	@Test
	void coverageClass() throws NoSuchAlgorithmException {
		PasswordSha256 pass = new PasswordSha256();
		HexString hex = new HexString();
	}

}
