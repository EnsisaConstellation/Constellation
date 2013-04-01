package server;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import com.hazelcast.core.Hazelcast;


public class User implements Serializable {

	String name, password, token;
	
	//date de la dernière connexion, permet de charger les nouveaux messages
	DateFormat lastConnection = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
	
	//liste des contacts de l'utilisateur et des rooms qu'il utilise
	List<String> contacts = new ArrayList<String>();
	List<String> roomsUsed = new ArrayList<String>();
	
	
	public User(String name, String password){
		this.name = name;
		this.password = password;
	}
	
	public void addContact(String contact, Map<String, User> users){
		if(!contacts.contains(contact)){
			contacts.add(contact);
			//TODO envoyer une invitation
			System.out.println(name+" ajoute "+ contact +" en contact");
			}
		else
			System.out.println("ce contact existe deja : "+ contact + " pour l'utilisateur " + name);
	}
	
	public void delContact(String contact){
		if(contacts.contains(contact)){
			contacts.remove(contact);
			System.out.println(name+" supprime "+ contact +" de ses contact");
		}
		else
			System.out.println("ce contact n'existe pas : "+ contact + " pour l'utilisateur " + name);
	}
}
