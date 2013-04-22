package client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import commands.*;

import server.Message;
import server.ServerI;
import swing.Login;

public class Client extends UnicastRemoteObject implements ClientI{
	static Registry registry;
	String token;
	static String name;
	static String password;
	static String ip;
	static int port;
	static Client client;
	boolean isConnected=false;
	
	protected Client() throws RemoteException {
		super();
	}
	public Client(String ip, int port, String login, String pass) throws RemoteException{
		super();
		this.ip=ip;
		this.port=port;
		this.name=login;
		this.password=pass;
		//connexion();
	}
	
	//TODO implementer les fonctions correspondant aux diverses commandes possibles
	
	public void connexion() throws RemoteException, InterruptedException{
		registry = LocateRegistry.getRegistry(ip,port);
		try {
		      ((ServerI)registry.lookup("server")).addCommand(new Connexion(name, password, client));
		      isConnected=true;
		    } catch(Exception e) {
		      e.printStackTrace();
		    }
		
		/*while(true){//dans cette version le client ne fait qu'afficher les messages recus
			Thread.sleep(10);
		}*/
	}
	
	
	public void deconnexion(){
		isConnected=false;
		//on vide les variables
		this.registry=null;
		this.token=null;
		this.name=null;
		this.password=null;
		this.ip=null;
		this.port=(Integer) null;
		this.client=null;
		//appel du Garbage Collector
		System.gc(); 
	}
	
	public boolean getStatusConnexion(){
		return isConnected;
	}
	
	@Override
	public void receive(Message msg) throws RemoteException { //cette fonction est appelee a distance par le serveur
		
		if(msg.getRoom().equals("Connexion")){		//le message contient le token
			token = msg.getContent();
			System.out.println(msg.getContent());}//juste pour tester le retour du token
		else if(msg.getRoom().equals("ErreurConnexion"))	//mauvais mot de passe
			System.out.println(msg.getContent());
		else {								//c'est un message normal
			System.out.println(msg.getContent());
		}
	}
	
	//ajout de contact
	public void addContact(String contact){
		try {
		      ((ServerI)registry.lookup("server")).addCommand(new AddContact(name,token,contact));
		      System.out.println("Contact added (from Client)");//pour le test
		    } catch(Exception e) {
		      e.printStackTrace();
		    }
	}
	
	//supprimer un contact
	public void delContact(String contact){
		try {
		      ((ServerI)registry.lookup("server")).addCommand(new DelContact(name,token,contact));
		      System.out.println("Contact deleted (from Client)");//pour le test
		    } catch(Exception e) {
		      e.printStackTrace();
		    }
	}
	
	//creation de salon
	public void createRoom(String roomName, String pass){
		try {
		      ((ServerI)registry.lookup("server")).addCommand(new CreateRoom(name,token,roomName,pass));
		      System.out.println("Room created (from Client)");//pour le test
		    } catch(Exception e) {
		      e.printStackTrace();
		    }
	}
	
	//renommer un salon
	public void renameRoom(String roomName, String newRoomName, String pass){
		try {
		      ((ServerI)registry.lookup("server")).addCommand(new RenameRoom(name,token,roomName,newRoomName,pass));
		      System.out.println("Room renamed (from Client)");//pour le test
		    } catch(Exception e) {
		      e.printStackTrace();
		    }
	}
	
	//modifier le mot de passe d'un salon
	public void changeRoomPass(String roomName, String pass, String newPass){
		try {
		      ((ServerI)registry.lookup("server")).addCommand(new ChangeRoomPass(name,token,roomName,pass,newPass));
		      System.out.println("Pass changed (from Client)");//pour le test
		    } catch(Exception e) {
		      e.printStackTrace();
		    }
	}


	//quitter un salon
	public void quitRoom(String roomName){
		try {
		      ((ServerI)registry.lookup("server")).addCommand(new QuitRoom(name,token,roomName));
		      System.out.println("Room quitted (from Client)");//pour le test
		    } catch(Exception e) {
		      e.printStackTrace();
		    }
	}
	
	//supprimer un salon
	public void delRoom(String roomName, String password){
		try {
		      ((ServerI)registry.lookup("server")).addCommand(new DelRoom(name,token,roomName,password));
		      System.out.println("Room deleted (from Client)");//pour le test
		    } catch(Exception e) {
		      e.printStackTrace();
		    }
	}
	
	//Getters
	public String getName(){
		return name;
	}
	
	public String getPass(){
		return password;
	}
	
	public String getToken(){
		return token;
	}
	/*
	public String getRooms(){
		
	}
	
	public String getContacts(){
	
	}
	
	*/
	//Setters
	
	//Affichage correct pour le test : Attention mot de passe en clair !!!!!
	@Override
	public String toString(){
		StringBuilder tmp=new StringBuilder();
		tmp.append("Client : "+name+"\nPassword : "+password+"\nConnecte au serveur : "+ip+":"+port);
		return tmp.toString();
	}
	
	/*
		//main (non utilise)
	public static void main(String[] args) throws RemoteException, InterruptedException {
		//mise en place de RMI
		registry = LocateRegistry.getRegistry(ip, (int)port);
		//System.setSecurityManager(new SecurityManagerPermissif());
		client = new Client();
		try {
		      ((ServerI)registry.lookup("server")).addCommand(new Connexion(name, password, client));
		      System.out.println("Connected");//pour le test
		    } catch(Exception e) {
		      e.printStackTrace();
		    }
		
		while(true){//dans cette version le client ne fait qu'afficher les messages recus
			Thread.sleep(10);
		}
	}
	*/
}


