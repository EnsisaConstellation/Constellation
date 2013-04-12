package Commandes;

import client.ClientI;

public class Connexion extends Command{
	public ClientI client;
	
	public Connexion(String user, String password, ClientI client){
		super(user, password, "Connexion");
		this.client = client;
	}
}
