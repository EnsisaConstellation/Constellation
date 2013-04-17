package server;
import java.rmi.RemoteException;
import java.util.Map;
//import java.util.TreeMap;
import java.util.concurrent.BlockingQueue;

import commands.AddContact;
import commands.Command;
import commands.Connexion;
import commands.CreateRoom;
import commands.DelContact;
import commands.DelRoom;

//import client.ClientI;



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
	
	public void execute(Map<String, User> users, Map<String, Room> rooms) throws RemoteException{
		
		
		//on test l'identite de la personne ayant lance la commande
		if(cmdAct.name.equals("Connexion"))
			if(!users.containsKey(cmdAct.user))//si c'est un nouvel utilisateur, on lui cree un compte
				Server.newUser(new User(cmdAct.user, cmdAct.token));
			if(users.containsKey(cmdAct.user) && users.get(cmdAct.user).password.equals(cmdAct.token)){
				Server.usersOnline.put(cmdAct.user, ((Connexion)cmdAct).client);						//l'utilisateur est enregistr�
				users.get(cmdAct.user).generateToken();											//on lui genere un token
				((Connexion)cmdAct).client.receive(new Message(users.get(cmdAct.user).token, "Connexion"));	//on lui envoie le token
				System.out.println(cmdAct.user+ " s'est connecte au serveur");
				}
			
		else if(users.containsKey(cmdAct.user) && users.get(cmdAct.user).token.equals(cmdAct.token)){
			
			//on l'execute
			if(cmdAct.name.equals("AddContact"))
				users.get(cmdAct.user).addContact(((AddContact)cmdAct).contact, users);
			
			else if(cmdAct.name.equals("DelContact"))
				users.get(cmdAct.user).delContact(((DelContact)cmdAct).contact);
			
			else if(cmdAct.name.equals("CreateRoom"))
				rooms.put(((CreateRoom)cmdAct).name, new Room(((CreateRoom)cmdAct).name, ((CreateRoom)cmdAct).password, ((CreateRoom)cmdAct).user));
			
			else if(cmdAct.name.equals("DelRoom")){
				if(rooms.containsKey(((DelRoom)cmdAct).room)){
					if(rooms.get(((DelRoom)cmdAct).room).getOwner().equals(((DelRoom)cmdAct).user)){
						rooms.get(((DelRoom)cmdAct).room).remove(users);
						rooms.remove(((DelRoom)cmdAct).room);
						System.out.println("le salon " + ((DelRoom)cmdAct).room + " a ete supprime");}
						else
							System.out.println("le salon " + ((DelRoom)cmdAct).room + " ne peut etre supprime : vous n'en etes pas le createur");
					}
				else
					System.out.println("le salon " + ((DelRoom)cmdAct).room + " ne peut etre supprime : il n'existe pas");
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
							
				case "setName": //nom du saloon, pwd du saloon, nouveau nom du saloon
					if(tabCmd.length==6)									//la longueur de la cmd est bonne ?
						if(saloons.containsKey(tabCmd[3]) 					//le saloons existe bien ?
						&& !saloons.containsKey(tabCmd[5]) 					//le nom n'est pas pris ?
						&& saloons.get(tabCmd[2]).owner.equals(tabCmd[0]))	//si l'utilisateur poss�de bien le saloon
						{	
							saloons.get(tabCmd[2]).setName(tabCmd[5]);		//Changement du nom du sallon
							saloons.put(tabCmd[5], saloons.get(tabCmd[2])); //Cr�ation d'une copie
							saloons.remove(tabCmd[2]);						//Supression de l'ancien saloon
							for (int i = 0 ; i< saloons.get(tabCmd[5]).participants.size() ; i++)
							{
								users.get(saloons.get(tabCmd[5]).participants.get(i)).saloonsUsed.remove(tabCmd[3]);	//supprime l'ancienne cl�
								users.get(saloons.get(tabCmd[5]).participants.get(i)).saloonsUsed.add(tabCmd[5]);	//ajout du nouveau nom du saloon
							}
						}
						else
							System.out.println(new Exception("commande setName pour le saloon est incorecte"));
						break;
						
				case "setPassword":	//nom du saloon, pwd du saloon, nouveau pwd
					if(tabCmd.length==6)
						if(saloons.containsKey(tabCmd[3]) 	//saloon existe ?
								&& saloons.get(tabCmd[2]).owner.equals(tabCmd[0])) //owner ?
							//TODO v�rification du mdp avant la modification
						{
							saloons.get(tabCmd[2]).setPassword(tabCmd[5]);	//changelent du mdp
						}
						else
							System.out.println(new Exception("Commande setPassword incorecte"));
							
					break;
						
				case "receive":	// nom du saloon, msg
					if(tabCmd.length >=5 ) {
						if(saloons.containsKey(tabCmd[3])			 //saloon existe ?
								&& saloons.get(tabCmd[3]).participants.contains(tabCmd[0]))	//user appartient aux participants
						{
							msgResult = new StringBuffer() ;								//init du msg
							for(int i = 4 ; i < tabCmd.length ; i++)
							{
								msgResult.append(tabCmd[i]);
							}
							saloons.get(tabCmd[3]).receive(new Message(msgResult.toString(), tabCmd[3]), users);
						}
						else
							System.out.println("le saloon ne semble pas exister, ou vous n'en faites pas parti."+tabCmd[3]+saloons.containsKey(tabCmd[3]));}
						else
							System.out.println(new Exception("commande receive incorecte"));
					break ;
				//TODO changement de mdp user
		}
	
			
			
				
				
		}
	}*/
}
 