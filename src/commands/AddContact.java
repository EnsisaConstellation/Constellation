package commands;

@SuppressWarnings("serial")
public class AddContact extends Command{
	public String contact;
	
	public AddContact(String user, String token, String contact) {
		super(user, token, "AddContact");
		this.contact = contact;
	}

}
