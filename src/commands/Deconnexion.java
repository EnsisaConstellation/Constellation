package commands;
import client.ClientI;

public class Deconnexion extends Command {
	public ClientI client;
	
	public Deconnexion(String user, String token, ClientI client) {
		super(user, token, "Deconnexion");
		this.client=client;
	}

}
