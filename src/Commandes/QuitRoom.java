package Commandes;


public class QuitRoom extends Command{
	public String room;
	
	public QuitRoom(String user, String token, String room) {
		super(user, token, "QuitRoom");
		this.room = room;
	}

}
