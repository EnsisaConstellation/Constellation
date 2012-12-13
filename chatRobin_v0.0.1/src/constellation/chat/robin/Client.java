package constellation.chat.robin;

import javax.swing.JFrame;

import com.hazelcast.client.ClientConfig;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class Client{
	static HazelcastInstance client;
	static IMap auteurs;
	static IMap messages;
	static String pseudo;
	static String message;

	
	public static void main(String[] args) throws InterruptedException {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.addAddress("127.0.0.1:5701");
        client = HazelcastClient.newHazelcastClient(clientConfig);
        
        
        getMsg();
        printMsg();
        
        choixPseudo choixPseudo = new choixPseudo();
        
        while(!choixPseudo.getClic()){
        	Thread.currentThread().sleep(100);
        }
        pseudo = choixPseudo.getText().getText(); 
        choixPseudo.dispose();
        
    	panel fenetre = new panel();
    	
    	while(true){
    	while(!fenetre.getClic()){
        	Thread.currentThread().sleep(100);
        	getMsg();
        	fenetre.getPseudo1().setText((String) auteurs.get(auteurs.size()-1));
        	fenetre.getMessage1().setText((String) messages.get(messages.size()-1));
        }
        message = fenetre.getText().getText();
        fenetre.getText().setText("");
        fenetre.resetClic();
    	putMsg();
    	
    	}
    	
    	
    }
	
	
	static void printMsg(){
		for(int i=0; i<auteurs.size(); i++)
        	System.out.println("message de : " + auteurs.get(i)+" : " + messages.get(i));
	}
	
	
	static void getMsg(){
		auteurs = client.getMap("auteurs");
        messages = client.getMap("messages");
	}
	
	static void putMsg(){
        auteurs.put(auteurs.size(), pseudo);
        messages.put(messages.size(), message);
	}

}
