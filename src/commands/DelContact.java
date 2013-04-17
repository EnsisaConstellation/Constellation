package commands;

@SuppressWarnings("serial")
public class DelContact extends Command{
	
	public String contact;
	
	public DelContact(String user, String token, String contact) {
		super(user, token, "DelContact");
		this.contact = contact;
	}

}
