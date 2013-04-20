package Commandes;

public class CreateRoom extends Command{
	public String room, password;
	
	public CreateRoom(String user, String token, String room, String password) {
		super(user, token, "CreateRoom");
		this.room = room;
		this.password = password;
	}
	
	
}
