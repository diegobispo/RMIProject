package src;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
	
	private static int port = 1099; // número-padrão da porta para o rmiregistry.
	
	public static void main(String[] args) throws RemoteException {
		try {
			// registra a porta
			LocateRegistry.createRegistry(port);
		} catch (Exception e1) {
		}
		// Criação do gerenciador de segurança (Security Manager)
		if (System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());

		try {
			String name =  MessageI18NUtil.getMessage("server");
			TextAnalyzerImpl textAnalyzer = new TextAnalyzerImpl();

			Registry registry = LocateRegistry.getRegistry(port);

			// registro do objeto 'textAnalyzer'  no servidor
			registry.rebind(name, textAnalyzer);

			System.out.println(" Servidor Rodando... ");

		} catch (Exception e) {

			System.err.println("exceção RMI:");
			e.printStackTrace();

		}
	}
}