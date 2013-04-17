package commands;

import client.ClientI;

@SuppressWarnings("serial")
public class Connexion extends Command{
	public ClientI client;
	
	public Connexion(String user, String password, ClientI client){
		super(user, password, "Connexion");
		this.client = client;
	}
}
