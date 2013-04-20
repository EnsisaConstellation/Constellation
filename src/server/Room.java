package server;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class Room implements Serializable {

	private String name, password;
	
	//date de creation 
	private DateFormat bornDate;
	
	//liste les participants et les messages de la discussion
	public List<String> participants = new ArrayList<String>();
	public List<Message> messages = new ArrayList<Message>();
	
	//nom du createur, c'est lui qui l'administre
	private String owner;
	
	
	public Room(String name, String user){
		this.name = name;
		this.owner = user;
		this.password = "";
		this.bornDate = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
		participants.add(user);
		Server.users.get(user).roomsUsed.add(name);
		System.out.println("création du salon " + name + " par l'utilisateur " + user);
	}
	public Room(String name, String password, String user){
		this(name, user);
		this.password = password;
	}
	
	
	//Methodes
	public void receive(Message message) throws InterruptedException{//le saloon recoit une message d'un utilisateur
		//il le met dans la liste des messages et l'envoie à chaque utilisateur inscrit (via send)
		messages.add(message);
		System.out.println(message.getContent());
		for(int i=0; i<participants.size(); i++){
			sendMsg(participants.get(i), messages.size()-1);
			System.out.println("message envoyé a : "+participants.get(i));
		}
	}
	
	public void send(String user, int nb) throws InterruptedException{
		for(int i=messages.size()-nb; i<messages.size();i++)
			this.sendMsg(user, i);
	}

	public void sendMsg(String user, int index) throws InterruptedException{//ajoute un message à un utilisateur
		if(Server.usersOnline.containsKey(user))
			try {
				Server.usersOnline.get(user).receive(this.messages.get(index));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	//ce n'est plus un salon pour ses participants
	public void remove(){
		for(Iterator it = participants.iterator(); it.hasNext();)
			Server.users.get(it.next()).roomsUsed.remove(name);
	}

	
	//getters and setters
	public void setName(String name){
		this.name = name;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public DateFormat getBornDate() {
		return bornDate;
	}
	public void setBornDate(DateFormat bornDate) {
		this.bornDate = bornDate;
	}
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getName() {
		return name;
	}
	public String getPassword() {
		return password;
	}
	
	
	
}
