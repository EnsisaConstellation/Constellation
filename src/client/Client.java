package client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import server.Message;
import server.ServerI;
import Commandes.Connexion;

public class Client extends UnicastRemoteObject implements ClientI{
	static Registry registry;
	String token;
	static String name = "user1";
	static String password = "password1";
	static Client client;
	
	protected Client() throws RemoteException {
		super();
	}

	public static void main(String[] args) throws RemoteException, InterruptedException {
		//mise en place de RMI
		registry = LocateRegistry.getRegistry("localhost", 1099);
		//System.setSecurityManager(new SecurityManagerPermissif());
		client = new Client();
		try {
		      ((ServerI)registry.lookup("server")).addCommand(new Connexion(name, password, client));
		    } catch(Exception e) {
		      e.printStackTrace();
		    }
		
		while(true){//dans cette version le client ne fais qu'afficher les messages recus
			Thread.sleep(10);
		}
	}

	@Override
	public void receive(Message msg) throws RemoteException { //cette fonction est appelée a distance par le serveur
		
		if(msg.getRoom().equals("Connexion")){		//le message contient le token
			token = msg.getContent();
			System.out.println(msg.getContent());}//juste pour tester le retour du token
		else if(msg.getRoom().equals("ErreurConnexion"))	//mauvais mot de passe
			System.out.println(msg.getContent());
		else {								//c'est un message normal
			System.out.println(msg.getContent());
		}
	}
	
	//TODO implémenter les fonctions correspondant aux diverses commandes possibles
	
	
}


