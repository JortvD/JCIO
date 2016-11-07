package nl.jortenmilo.command;

public class CommandDecoder {
	
	public static String[] getParameters(String s) {
		String[] params = null;
		int amount = 1;
		byte[] bytes = s.getBytes();
		
		/*for(int i = 0; i < bytes.length; i++) {
			if(bytes[i] == 8) {
				CharSequence str = s;
				
				if(i > 0) {
					System.out.println(str.subSequence(i-1, i+1));
					s = s.replace(str.subSequence(i-1, i+1), "");
					
				} else {
					System.out.println(str.subSequence(i, i+1));
					s = s.replace(str.subSequence(i, i+1), "");
				}
			}
		}*/
		
		for(int i = 0; i < bytes.length; i++) {
			if(bytes[i] == 32) {
				amount++;
			}
		}
		
		params = new String[amount];
		String param = "";
		int n = 0;
		
		for(int i = 0; i < bytes.length; i++) {
			if(bytes[i] == 32) {
				params[n] = param;
				param = "";
				n++;
			} else {
				param += s.substring(i, i+1);
			}
		}
		
		params[n] = param;
		return params;
	}
	
}
