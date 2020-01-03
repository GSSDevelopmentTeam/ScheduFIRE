package util;

public class HexString {

	static char [] hexChar = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	
	 static String bufferToHex(byte [] b) {
		StringBuffer sb = new StringBuffer(b.length * 2);
		
		for(int i = 0; i<b.length; i++) {
			sb.append(hexChar [(b[i] & 0xf0 ) >>> 4 ]);
			sb.append(hexChar [b[i] & 0x0f]);
		}
		return sb.toString();
	}
}
