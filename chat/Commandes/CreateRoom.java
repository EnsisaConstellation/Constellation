package Commandes;

public class CreateRoom extends Command{
	public String name, password;
	
	public CreateRoom(String user, String token, String name, String password) {
		super(user, token, "CreateRoom");
		this.name = name;
		this.password = password;
	}
	
	
}
