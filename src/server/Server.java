package server;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import Commandes.AddContact;
import Commandes.Command;
import Commandes.CreateRoom;
import Commandes.DelContact;
import Commandes.DelRoom;

import client.ClientI;

import com.hazelcast.core.Hazelcast;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class Server extends UnicastRemoteObject implements ServerI {
	

	//map des users et des rooms, les noms etant uniques servent de cle
	static Map<String, Room> rooms = Hazelcast.getMap("rooms");
	static Map<String, User> users = Hazelcast.getMap("users");
	
	//liste des commandes que le serveur doit encore executer
	static BlockingQueue<Command> commands = Hazelcast.getQueue("commands");

	//map des utilisateurs actuellement en ligne
	static Map<String, ClientI> usersOnline = new TreeMap<String, ClientI>();
	
	static Interpret interpret = new Interpret(commands);

	//constructeur de base
	protected Server() throws RemoteException {
		super();
	}
	
	
	public static void main(String[] args) throws InterruptedException, RemoteException {
		//mise en place de RMI
		Registry RMI_REGISTRY = LocateRegistry.createRegistry(1099);
		Server server = new Server();
		//System.setSecurityManager(new SecurityManagerPermissif());
		/*try {
		    if (System.getSecurityManager() == null) {
		      System.setSecurityManager(new RMISecurityManager());
		    }
		  } catch (Exception e) {
		     e.printStackTrace();
		  }*/
		
		RMI_REGISTRY.rebind("server", server);
		
		//on cree l'utilisateur principal, on le connecte et cree les rooms utilent au serveur
		newUser(new User("root", "root"));
		users.get("root").generateToken();
		commands.add(new CreateRoom("root", users.get("root").token, "Connexion", "root"));//Réserve le salon Connexion
		commands.add(new CreateRoom("root", users.get("root").token, "Contacts", "root"));
		
		//creation d'utilisateurs pour pouvoir tester le fonctionnement du serveur
		newUser(new User("user1", "password1"));
		users.get("user1").token = "token1";
		newUser(new User("user2", "password2"));
		commands.add(new AddContact("user1", "token1", "user2"));
		newUser(new User("user3", "password3"));
		commands.add(new AddContact("user1", "token1", "user3"));
		commands.add(new CreateRoom("user1", "token1", "room1", "passwordRoom1"));
		users.get("user1").getaEnvoyer().add("test");
				
		//boucle de fonctionnement
		while(true){
			interpret.fetch(users, rooms);

			//test de la queue aEnvoyer
			if(!users.get("user1").getaEnvoyer().isEmpty())
				System.out.println(users.get("user1").getaEnvoyer().poll());
		}
	}

	//TODO creer une commande
	public static void newUser(User user){
		users.put(user.name, user);
		System.out.println("creation de l'utilisateur : "+user.name);
	}

	//reception des commandes des clients
	@Override
	public void addCommand(Command command) throws RemoteException {
		commands.add(command);		
	}
	
	
}
