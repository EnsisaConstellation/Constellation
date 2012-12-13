package constellation.chat.robin;

import com.hazelcast.core.Hazelcast;
import java.util.Map;
import java.util.Queue;


public class Serveur {
	
	

    public static void main(String[] args) {
        Map<Integer, String> messages = Hazelcast.getMap("messages");
        Map<Integer, String> auteurs = Hazelcast.getMap("auteurs");
        
        auteurs.put(auteurs.size(), "un con");
        messages.put(messages.size(), "cet idiot est completement con");
        
        auteurs.put(auteurs.size(), "un idiot");
        messages.put(messages.size(), "ce con est un idiot");

        //System.out.println("Customer with key 1: "+ mapCustomers.get(1));
        //System.out.println("Map Size:" + mapCustomers.size());


    }
	
	
	
	
	
	

}
