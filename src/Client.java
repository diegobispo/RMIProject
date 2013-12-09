package src;

import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;

/**
 * 
 * 
 * @author Diego Bispo
 */
public class Client {
	private static TextAnalyzer textAnalyser = null;
	// Server IP
	private static String ipServer = "127.0.0.1";
	private static int port = 1099;
	private static boolean isConected = true;

	/**
   * 
   */
	private static void conect() {

		String ipConfig = "rmi://" + ipServer + ":" + port + "/";
		try {
			// Instance of instanciamos o objeto remoto
			textAnalyser = (TextAnalyzer) Naming.lookup(ipConfig + MessageI18NUtil.getMessage("server"));
		} catch (ConnectException e) {
			isConected = false;
			System.out.println(MessageI18NUtil.getMessage("error_server_offline"));
			 e.printStackTrace();
		} catch (Exception e) {
			isConected = false;
			System.out.println(MessageI18NUtil.getMessage("internal_error"));
			 e.printStackTrace();
		}
	}

	public static void main(final String args[]) throws Exception {
		conect();
		if(isConected){
			MainFrame app = new MainFrame(textAnalyser);
			app.sowFrame();
		}
		
	}
}