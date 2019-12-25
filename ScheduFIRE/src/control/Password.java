package control;

import java.util.Base64;
import java.io.UnsupportedEncodingException;
import java.security.*;

public class Password {
	public static String codificaPwd(String pwd){
		
			String pwdCodificata = Base64.getEncoder().encodeToString(pwd.getBytes());
			return pwdCodificata;
		} 
	
	public static String decodificaPwd(String pwd_Cod){
		
		byte[] ByteDecodificati = Base64.getDecoder().decode(pwd_Cod);
		String pwdDecodificata = new String(ByteDecodificati);
		return pwdDecodificata;
	} 
}
