package constellation.robin.vaisseau;

import java.util.LinkedList;
import java.util.List;

public class environnement {
	static List modules = new LinkedList();
	
	public environnement(){
		
	}

	
	public static List getModules() {
		return modules;
	}
	
	public static void addModule(module module){
		modules.add(module);
	}
	
	public static module getModule(int cle){
		return (module)modules.get(cle);
	}
	
	
	
	
	
	
}
