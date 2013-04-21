package commands;
/* Command that permit to the owner of a room to rename it.
 *  Params are :
 *  Name of the room owner, its token, room name, new room name, room pass  
 *  */
public class RenameRoom extends Command{
	public String room, newName;
	
	// Room Owner
	public RenameRoom(String user, String token, String room, String newName, String password){
		super(user, token, "RenameRoom");
		this.room = newName;
	}

}
