package server;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import commands.*;


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
				Server.usersOnline.put(cmdAct.user, ((Connexion)cmdAct).client);						//l'utilisateur est enregistre
				users.get(cmdAct.user).generateToken();											//on lui genere un token
				((Connexion)cmdAct).client.receive(new Message(users.get(cmdAct.user).token, "Connexion"));	//on lui envoie le token
				System.out.println(cmdAct.user+ " s'est connecte au serveur");
				}
			
		else if(users.containsKey(cmdAct.user) && users.get(cmdAct.user).token.equals(cmdAct.token)){
			System.out.println("on execute une commande : "+cmdAct.name);
			//on l'execute
			if(cmdAct.name.equals("AddContact"))
				users.get(cmdAct.user).addContact(((AddContact)cmdAct).contact, users);
			
			else if(cmdAct.name.equals("DelContact"))
				users.get(cmdAct.user).delContact(((DelContact)cmdAct).contact);
			
			else if(cmdAct.name.equals("CreateRoom")){
				if(!rooms.containsKey(((CreateRoom)cmdAct).room))
					rooms.put(((CreateRoom)cmdAct).room, new Room(((CreateRoom)cmdAct).room, ((CreateRoom)cmdAct).password, cmdAct.user));
				else if(Server.rooms.get(((CreateRoom)cmdAct).room).getPassword().equals(((CreateRoom)cmdAct).password)){
					Server.rooms.get(((CreateRoom)cmdAct).room).participants.add(((CreateRoom)cmdAct).user);
					Server.users.get(((CreateRoom)cmdAct).user).roomsUsed.add(((CreateRoom)cmdAct).room);
				}
			}
			else if(cmdAct.name.equals("RenameRoom")){
				RenameRoom cmd = ((RenameRoom)cmdAct);
				if(rooms.containsKey(cmd.room)){
					if(rooms.get(cmd.room).getOwner().equals(cmd.user)){
						if(rooms.get(cmd.room).getPassword().equals(cmd.password)){
							rooms.get(cmd.room).setName(cmd.newName);
							System.out.println("le nom du salon "+cmd.room+" a ete change en "+cmd.newName);	
						}
						else
							System.out.println("le nom du salon "+cmd.room+" ne peut etre change : mot de passe faux");	
					}
					else
						System.out.println("le nom du salon "+cmd.room+" ne peut etre change : droits administrateurs requis");							
				}
				else
					System.out.println("le nom du salon "+cmd.room+" ne peut etre change : le salon n'existe pas");
			}
			else if(cmdAct.name.equals("ChangeRoomPass")){
				ChangeRoomPass cmd = ((ChangeRoomPass)cmdAct);
				if(rooms.containsKey(cmd.room)){
					if(rooms.get(cmd.room).getOwner().equals(cmd.user)){
						if(rooms.get(cmd.room).getPassword().equals(cmd.password)){
							rooms.get(cmd.room).setPassword(cmd.newPassword);
							System.out.println("le mot de passe du salon "+cmd.room+" a ete change");
						}
						else
							System.out.println("le mot de passe du salon "+cmd.room+" ne peut etre change : mot de passe faux");	
					}
					else
						System.out.println("le mot de passe du salon "+cmd.room+" ne peut etre change : droits administrateurs requis");							
				}
				else
					System.out.println("le mot de passe du salon "+cmd.room+" ne peut etre change : le salon n'existe pas");
			}
			else if(cmdAct.name.equals("QuitRoom")){
				QuitRoom cmd = ((QuitRoom)cmdAct);
				if(rooms.containsKey(cmd.room)){
					users.get(cmd.user).roomsUsed.remove(cmd.room);
					rooms.get(cmd.room).participants.remove(cmd.user);
					if(rooms.get(cmd.room).participants.isEmpty())
						rooms.remove(cmd.room);
					else if(rooms.get(cmd.room).getOwner().equals(cmd.user))
						rooms.get(cmd.room).setOwner(rooms.get(cmd.room).participants.get(0));
					}
				else
					System.out.println("le salon " + cmd.room + " ne peut etre quitte : il n'existe pas");
			}
			else if(cmdAct.name.equals("DelRoom")){
				DelRoom cmd = ((DelRoom)cmdAct);
				if(rooms.containsKey(cmd.room)){
					if(rooms.get(cmd.room).getOwner().equals(cmd.user)){
						if(rooms.get(cmd.room).getPassword().equals(cmd.password)){
							rooms.remove(cmd.room);
							System.out.println("le salon "+cmd.room+" a bien ete supprime");
						}
						else
							System.out.println("le salon "+cmd.room+" ne peut etre supprime : mot de passe faux");	
					}
					else
						System.out.println("le salon "+cmd.room+" ne peut etre supprime : droits administrateurs requis");	
					}
				else
					System.out.println("le salon " + cmd.room + " ne peut etre supprime : il n'existe pas");
			}
			else if(cmdAct.name.equals("Receive")){
				Receive cmd = ((Receive)cmdAct);
				if(rooms.containsKey(cmd.room) && rooms.get(cmd.room).participants.contains(cmd.user)){
					rooms.get(cmd.room).receive(new Message(cmd.message, cmd.room, cmd.user));
				}
				else
					System.out.println("le salon ne semble pas exister, ou vous n'en faites pas parti. "+cmd.room);
			}
			
			else if(cmdAct.name.equals("GetContact")){
				Server.usersOnline.get(cmdAct.user).receive(new Message(users.get(cmdAct.user).contacts.toString(), "Contacts"));
				Server.usersOnline.get(cmdAct.user).receive(new Message(users.get(cmdAct.user).roomsUsed.toString(), "Rooms"));
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
 