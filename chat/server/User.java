package server;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import com.hazelcast.core.Hazelcast;


public class User implements Serializable {

	String name, password;
	DateFormat lastConnection = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
	Boolean auth;
	List<String> contacts = new ArrayList<String>();
	List<String> saloonsUsed = new ArrayList<String>();
	BlockingQueue<Message> aEnvoyer = Hazelcast.getQueue("aEnvoyer:"+name);
	
	
	public User(String name, String password){
		this.name = name;
		this.password = password;
	}
	
	public void addContact(String contact, Map<String, User> users){
		if(!contacts.contains(contact)){
			contacts.add(contact);
		//TODO envoyer une invitation
		System.out.println(name+" ajoute "+ contact +" en contact");}
	}
	
	public void delContact(String contact){
		if(!contacts.contains(contact)){
			contacts.remove(contact);
		}
	}
}
