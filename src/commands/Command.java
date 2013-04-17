package commands;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class Command implements Serializable{
	public final String name;
	public String user;
	public String token;
	
	
	public Command(String user, String token, String name){
		this.name = name;
		this.token = token;
		this.user = user;
	}


	public String getName() {
		return name;
	}
}
