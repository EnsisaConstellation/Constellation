package commands;

public class ChangeRoomPass extends Command{
	public String room, password, newPassword;
	public ChangeRoomPass(String user, String token, String room, String password, String newPassword) {
		super(user, token, "ChangeRoomPass");
		this.room=room;
		this.password=password;
		this.newPassword=newPassword;
	}

}
