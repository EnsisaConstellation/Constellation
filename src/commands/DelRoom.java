package commands;


public class DelRoom extends Command{
	public String room, password;
	
	public DelRoom(String user, String token, String room, String password) {
		super(user, token, "DelRoom");
		this.room = room;
		this.password=password;
	}

}
