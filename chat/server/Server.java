package server;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hazelcast.core.Hazelcast;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class Server {
	static Map<String, Saloon> saloons = Hazelcast.getMap("saloons");
	static Map<String, User> users = Hazelcast.getMap("users");
	static BlockingQueue<String> commandes = Hazelcast.getQueue("commandes");
	static Interpret interpret = new Interpret(commandes);
	
	
	public static void main(String[] args) throws InterruptedException {
		newUser(new User("user1", "password1"));
		newUser(new User("user2", "password2"));
		commandes.add("user1,password1,addContact,user2");

		while(true){
			interpret.execute(users, saloons);
		}
	}

	public static void newUser(User user){
		users.put(user.name, user);
		System.out.println("creation de l'utilisateur : "+user.name);
	}
	
	public static void delUser(String user){
		users.remove(user);					
	}
	
	public void broadcastMsg(String msg) throws InterruptedException{
		for(int i=0; i<saloons.size(); i++)
			saloons.get(i).receive(new Message(msg), users);
	}
	
	
}
