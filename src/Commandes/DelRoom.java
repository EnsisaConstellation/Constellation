package Commandes;


public class DelRoom extends Command{
	public String room;
	
	public DelRoom(String user, String token, String room) {
		super(user, token, "DelRoom");
		this.room = room;
	}

}
