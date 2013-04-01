package server;
import java.text.DateFormat;


public class Message {

	public String content;
	public String saloon;
	DateFormat date;
	
	public Message(){
		content = "";
		date = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
	}
	public Message(String msg){
		content = msg;
		date = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
		saloon = "";
	}
	public Message(String msg, String saloon){
		super(msg);
		this.saloon = saloon;
	}
	
	public void modify(String msg){
		content = msg;
		date = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
	}
	
	public void deleteContent(){
		content = "";
	}
	
	public String getContent(){
		return content; 
	}
}
