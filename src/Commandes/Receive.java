package Commandes;

public class Receive extends Command {
	public String room, message;
	
	public Receive(String user, String token, String room, String message) {
		super(user, token, "Receive");
		this.room = room;
		this.message = message;
	}
	
	
}
