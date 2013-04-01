package server;
import java.util.Map;
import java.util.concurrent.BlockingQueue;


public class Interpret {
	BlockingQueue<String> commandes;
	String cmdAct;
	String[] tabCmd;
	StringBuffer msgResult = new StringBuffer() ; // message recomposé
	
	public Interpret(BlockingQueue<String> commandes){
		this.commandes = commandes;
		tabCmd = new String[200];
	} 
	
	public void execute(Map<String, User> users, Map<String, Saloon> saloons) throws InterruptedException{
		if(!commandes.isEmpty()){
			cmdAct = commandes.poll();
			tabCmd = cmdAct.split(",");
		// cmd = user,password,CaseFoncion,params ;
			
			if(tabCmd.length<3)
				System.out.println(new Exception("commande recue trop courte"));
			else if(!users.containsKey(tabCmd[0]) || !users.get(tabCmd[0]).password.equals(tabCmd[1]))
				System.out.println(new Exception("utilisateur ou mot de passe incorrecte"+users.get(tabCmd[0]).password+tabCmd[1]));
			else
				switch(tabCmd[2]){
				
				case "addContact": // nom du contact
					if(tabCmd.length == 4)
						users.get(tabCmd[0]).addContact(tabCmd[3], users);
					else
						System.out.println(new Exception("commande addUser incorrecte"));
					break;	//on sort du case
					
				case "delContact": // nom du contact
					if(tabCmd.length == 4)
							users.get(tabCmd[0]).delContact(tabCmd[3]);
					else
						System.out.println(new Exception("commande addUser incorrecte"));
					break;
					
				case "newSaloon": // nom du saloon, pwd du saloon
					if(tabCmd.length == 5){
						System.out.println("tentative de création du saloon "+ tabCmd[3]);
						if(!saloons.containsKey(tabCmd[3])){
							saloons.put(tabCmd[3], new Saloon(tabCmd[3],tabCmd[4], users.get(tabCmd[0])));	
							System.out.println("création du saloon "+ tabCmd[3]);}
					}
					else
						System.out.println(new Exception("commande newSaloon incorrecte"));
					break;
			
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
						&& saloons.get(tabCmd[2]).owner.equals(tabCmd[0]))	//si l'utilisateur possède bien le saloon
						{	
							saloons.get(tabCmd[2]).setName(tabCmd[5]);		//Changement du nom du sallon
							saloons.put(tabCmd[5], saloons.get(tabCmd[2])); //Création d'une copie
							saloons.remove(tabCmd[2]);						//Supression de l'ancien saloon
							for (int i = 0 ; i< saloons.get(tabCmd[5]).participants.size() ; i++)
							{
								users.get(saloons.get(tabCmd[5]).participants.get(i)).saloonsUsed.remove(tabCmd[3]);	//supprime l'ancienne clé
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
							//TODO vérification du mdp avant la modification
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
	}
}
 