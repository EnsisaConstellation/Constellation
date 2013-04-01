package server;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Saloon implements Serializable {

	String name, password;
	DateFormat bornDate;
	List<String> participants = new ArrayList<String>();
	List<Message> messages = new ArrayList<Message>();
	String owner;
	
	public Saloon(String name, User user){
		this.name = name;
		owner = user.name;
		password = "";
		bornDate = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
		participants.add(user.name);
	}
	public Saloon(String name, String password, User user){
		this.name = name;
		owner = user.name;
		this.password = password;
		bornDate = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
		participants.add(user.name);
	}
	
	public void receive(Message message, Map<String, User> users) throws InterruptedException{//le saloon recoit une message d'un utilisateur
		//il le met dans la liste des messages et l'envoie à chaque utilisateur inscrit (via send)
		messages.add(message);
		System.out.println(message.content);
		for(int i=0; i<participants.size(); i++)
			sendMsg(participants.get(i), messages.size()-1, users);
	}
	
	public void send(String user, int nb, Map<String, User> users) throws InterruptedException{
		for(int i=messages.size()-nb; i<messages.size();i++)
			this.sendMsg(user, i, users);
	}

	public void sendMsg(String user, int index, Map<String, User> users) throws InterruptedException{//ajoute un message à un utilisateur
		if(!messages.isEmpty())
			users.get(user).aEnvoyer.offer(messages.get(0));
	}

	public void setName(String name){
		this.name = name;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
}
