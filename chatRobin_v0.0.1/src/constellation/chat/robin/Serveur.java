package constellation.chat.robin;

import com.hazelcast.core.Hazelcast;
import java.util.Map;
import java.util.Queue;


public class Serveur {
	
	

    public static void main(String[] args) {
        Map<Integer, String> messages = Hazelcast.getMap("messages");
        Map<Integer, String> auteurs = Hazelcast.getMap("auteurs");
    }
	
	
	
	
	
	

}
