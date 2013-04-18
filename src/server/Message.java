package server;
import java.io.Serializable;
import java.text.DateFormat;


public class Message implements Serializable{

	public String content;
	public String room;
	public String user;
	DateFormat date;
	
	
	public Message(String msg){
		content = msg;
		date = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
		room = "";
		user = "";
	}
	public Message(String msg, String room){
		this(msg);
		this.room = room;
		user = "";
	}
	
	public Message(String msg, String room, String user){
		this(msg);
		this.room = room;
		this.user = user;
	}
	
	public void modify(String msg){
		content = msg;
		date = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
	}
	
	public void deleteContent(){
		content = "";
	}
	
	//getters and setters
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public DateFormat getDate() {
		return date;
	}
	public void setDate(DateFormat date) {
		this.date = date;
	}
}
