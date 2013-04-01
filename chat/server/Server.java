package server;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Commandes.AddContact;
import Commandes.Command;
import Commandes.CreateRoom;
import Commandes.DelContact;
import Commandes.DelRoom;

import com.hazelcast.core.Hazelcast;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class Server {
	//map des users et des rooms, les noms etant uniques servent de cle
	static Map<String, Room> rooms = Hazelcast.getMap("rooms");
	static Map<String, User> users = Hazelcast.getMap("users");
	
	//liste des commandes que le serveur doit encore executer
	static BlockingQueue<Command> commands = Hazelcast.getQueue("commands");
	
	static Interpret interpret = new Interpret(commands);

	
	public static void main(String[] args) throws InterruptedException {
		
		//creation d'utilisateurs pour pouvoir tester le fonctionnement du serveur
		newUser(new User("user1", "password1"));
		users.get("user1").token = "token1";
		newUser(new User("user2", "password2"));
		commands.add(new AddContact("user1", "token1", "user2"));
		commands.add(new DelContact("user1", "token1", "user2"));
		commands.add(new CreateRoom("user1", "token1", "room1", "passwordRoom1"));
		commands.add(new DelRoom("user1", "token1", "room1"));

		//boucle de fonctionnement
		while(true){
			interpret.fetch(users, rooms);
			//System.out.println("commandes en attentes : " + commands.size());
		}
	}

	//TODO creer une commande
	public static void newUser(User user){
		users.put(user.name, user);
		System.out.println("creation de l'utilisateur : "+user.name);
	}
	
	
}
