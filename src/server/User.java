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
	
	//date de la dernière connexion, permet de charger les nouveaux messages
	DateFormat lastConnection = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
	
	//liste des contacts de l'utilisateur et des rooms qu'il utilise
	List<String> contacts = new ArrayList<String>();
	List<String> roomsUsed = new ArrayList<String>();
	
	//la queue aEnvoyer contient tous les messages que l'on veut transmettre à l'utilisateur
	transient BlockingQueue<String> aEnvoyer;
	
	public User(String name, String password){
		this.name = name;
		this.password = password;
		generateToken();

		if (aEnvoyer == null) {
            synchronized(this) { // Exclusion mutuelle, seulement dans le cas où on se doute que aEnvoyer ne l'est pas
                if (aEnvoyer == null) { // Revérification quand on est sûr que un seul processus est actif à cet endroit là (un autre a peutêtre déjà fait le boulot)
                    aEnvoyer = Hazelcast.getQueue("aEnvoyer:"+name); // La véritable initialisation
                }
            }
        }
		
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
		//TODO améliorer ce systeme
		
	}
}
