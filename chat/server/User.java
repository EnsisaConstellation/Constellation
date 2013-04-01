package server;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import Commandes.Command;

import com.hazelcast.core.Hazelcast;


public class User implements Serializable {

	//le token vas remplacer le mot de passe une fois la connexion etablie
	String name, password, token;
	
	//date de la derni�re connexion, permet de charger les nouveaux messages
	DateFormat lastConnection = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
	
	//liste des contacts de l'utilisateur et des rooms qu'il utilise
	List<String> contacts = new ArrayList<String>();
	List<String> roomsUsed = new ArrayList<String>();
	
	//la queue aEnvoyer contient tous les messages que l'on veut transmettre � l'utilisateur
	BlockingQueue<String> aEnvoyer;
	
	public User(String name, String password){
		this.name = name;
		this.password = password;
		generateToken();
		aEnvoyer = Hazelcast.getQueue("aEnvoyer:"+name);
		
		//test montrant l'eereur obtenue avec la queue aEnvoyer
		aEnvoyer.add("             test");
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

	public void generateToken() {
		token = ""+10000*Math.random();
		//TODO am�liorer ce systeme
		
	}
}
