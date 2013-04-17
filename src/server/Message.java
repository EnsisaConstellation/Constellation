package server;
import java.io.Serializable;
import java.text.DateFormat;


@SuppressWarnings("serial")
public class Message implements Serializable{

	private String content;
	private String saloon;
	DateFormat date;
	
	
	public Message(String msg){
		content = msg;
		date = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
		saloon = "";
	}
	public Message(String msg, String saloon){
		this(msg);
		this.saloon = saloon;
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
	public String getSaloon() {
		return saloon;
	}
	public void setSaloon(String saloon) {
		this.saloon = saloon;
	}
	public DateFormat getDate() {
		return date;
	}
	public void setDate(DateFormat date) {
		this.date = date;
	}
}
