package server;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.BlockingQueue;

import client.ClientI;

import Commandes.AddContact;
import Commandes.Connexion;
import Commandes.DelContact;
import Commandes.CreateRoom;
import Commandes.Command;
import Commandes.DelRoom;
import Commandes.Receive;

public class Interpret {
	//la liste des commandes a executer
	private BlockingQueue<Command> commands;
	private Command cmdAct;

	
	
	public Interpret(BlockingQueue<Command> commands){
		this.commands = commands;
	} 
	
	public void fetch(Map<String, User> users, Map<String, Room> rooms) throws InterruptedException, RemoteException{
		//on recupere une commande de la queue
		if(!commands.isEmpty()){
			cmdAct = commands.poll();
			execute(users, rooms);
		}
		else
			Thread.sleep(1);
	}
	
	public void execute(Map<String, User> users, Map<String, Room> rooms) throws RemoteException, InterruptedException{
		
		
		//on test l'identite de la personne ayant lance la commande
		if(cmdAct.name.equals("Connexion"))
			if(!users.containsKey(cmdAct.user))//si c'est un nouvel utilisateur, on lui cree un compte
				Server.newUser(new User(cmdAct.user, cmdAct.token));
			if(users.containsKey(cmdAct.user) && users.get(cmdAct.user).password.equals(cmdAct.token)){
				Server.usersOnline.put(cmdAct.user, ((Connexion)cmdAct).client);						//l'utilisateur est enregistré
				users.get(cmdAct.user).generateToken();											//on lui genere un token
				((Connexion)cmdAct).client.receive(new Message(users.get(cmdAct.user).token, "Connexion"));	//on lui envoie le token
				System.out.println(cmdAct.user+ " s'est connecte au serveur");
				}
			
		else if(users.containsKey(cmdAct.user) && users.get(cmdAct.user).token.equals(cmdAct.token)){
			System.out.println("on execute une commande");
			//on l'execute
			if(cmdAct.name.equals("AddContact"))
				users.get(cmdAct.user).addContact(((AddContact)cmdAct).contact, users);
			
			else if(cmdAct.name.equals("DelContact"))
				users.get(cmdAct.user).delContact(((DelContact)cmdAct).contact);
			
			else if(cmdAct.name.equals("CreateRoom"))
				rooms.put(((CreateRoom)cmdAct).name, new Room(((CreateRoom)cmdAct).name, ((CreateRoom)cmdAct).password, ((CreateRoom)cmdAct).user));
			
			else if(cmdAct.name.equals("DelRoom")){
				DelRoom cmd = ((DelRoom)cmdAct);
				if(rooms.containsKey(cmd.room)){
					if(rooms.get(cmd.room).getOwner().equals(cmd.user)){
						rooms.get(cmd.room).remove();
						rooms.remove(cmd.room);
						System.out.println("le salon " + cmd.room + " a ete supprime");}
						else
							System.out.println("le salon " + cmd.room + " ne peut etre supprime : vous n'en etes pas le createur");
					}
				else
					System.out.println("le salon " + cmd.room + " ne peut etre supprime : il n'existe pas");
			}
			
			else if(cmdAct.name.equals("Receive")){
				Receive cmd = ((Receive)cmdAct);
				if(rooms.containsKey(cmd.room) && rooms.get(cmd.room).getParticipants().contains(cmd.user)){
					rooms.get(cmd.room).receive(new Message(cmd.message, cmd.room, cmd.user));
				}
				else
					System.out.println("le saloon ne semble pas exister, ou vous n'en faites pas parti.");
			}
			
			else if(cmdAct.name.equals("GetContact")){
				Server.usersOnline.get(cmdAct.user).receive(new Message(users.get(cmdAct.user).contacts.toString(), "Contacts"));
			}
			//TODO completer les fonctions manquantes
		}
		else
			System.out.println("probleme d'authentification : " + cmdAct.user + " n'existe pas ou n'est plus authentifie " + cmdAct.name);
	}
	
	
	
	/*ancien code de la fonction execute
	public void execute(Map<String, User> users, Map<String, Room> saloons) throws InterruptedException{
		
				switch(tabCmd[2]){
				
			
				case "joinSaloon": // nom du saloon, pwd du saloon
					if(tabCmd.length == 5)
						if(saloons.containsKey(tabCmd[3]))
							if(saloons.get(tabCmd[3]).password.equals(tabCmd[4])){
								users.get(tabCmd[0]).saloonsUsed.add(tabCmd[3]);
								saloons.get(tabCmd[3]).participants.add(tabCmd[0]);
							}
							else
								System.out.println(new Exception("commande joinSaloon incorrecte"));
							break;
						
				//TODO changement de mdp user
		}
	
			
			
				
				
		}
	}*/
}
 