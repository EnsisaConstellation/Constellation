package constellation.robin.vaisseau;
import java.util.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class vaisseau {
	
//******************************************	initialisation	****************************************************
	protected float vitesse;
	protected float direction;
	protected static int poids;
	protected static int poidsT;
	public static float posX;
	protected static float posY;
	static List modules = new LinkedList();
	
	
	//constructeur par defaut
	public vaisseau(){
		this.vitesse = 0.0f;		//vitesse actuelle du vaisseau
		this.direction = 90.0f;  	//direction de la vitesse du vaisseau en ° (90° == avant)
		this.poids = 10; 			//poids du vaisseau sans modules
		this.posX = 200;
		posY = 200;
	}
	
	//constructeur complet
	public vaisseau(float vitesse, float direction, int poids){
		this.vitesse = vitesse;		//vitesse actuelle du vaisseau
		this.direction = direction;  	//direction de la vitesse du vaisseau en ° (90° == avant)
		this.poids = poids; 			//poids du vaisseau sans modules
	}
	
	
	
//******************************************	méthodes	****************************************************
	public void setVitesseTotale(float direction){  //+G +modules
		//calcul de la vitesse du vaisseau
	}
	
	public void centreG(){  //+modules
		//calcul du centre de gravité du vaisseau complet
	}
	
	public static void masseT(){   //+modules
		int somme = poids;
		for(int i = 0; i < modules.size(); i++){
			somme = somme + ((module)modules.get(i)).getPoids();
			}
		poidsT = somme;
	}
	
	public static List getModules() {
		return modules;
	}

	public String toString(){
		return "   Vaisseau :\n\tvitesse : " + this.vitesse + "\n\tdirection : " + this.direction + "°\n\tpoids : " + this.poids;
	}
	
	public static void addModule(module module){
		modules.add(module);
		masseT();
	}
	
	public module getModule(int cle){
		return (module)this.modules.get(cle);
	}
	
	public static int getPoidsT(){
		return poidsT;
	}
	
	public static float rotation(GameContainer gc){
    	Input userInput = gc.getInput();
    	float mouseX = userInput.getMouseX();
    	float mouseY = userInput.getMouseY();
    	float distX = mouseX - posX-25;
    	float distY = mouseY - posY-25;
    	return (float)Math.toDegrees(Math.atan2(distY, distX));
	}
	
	public static void avance(float angle, int delta){
		float delt = 0.5f*delta;
		posX = (float)Math.cos(Math.toRadians(angle))*delt+posX;
		posY = (float)Math.sin(Math.toRadians(angle))*delt+posY;
	}
	
	public static void recule(float angle, int delta){
		float delt = 0.5f*delta;
		posX = (float)-Math.cos(Math.toRadians(angle))*delt+posX;
		posY = (float)-Math.sin(Math.toRadians(angle))*delt+posY;
	}
	
	public static void gauche(float angle, int delta){
		float delt = 0.5f*delta;
		posX = (float)Math.sin(Math.toRadians(angle))*delt+posX;
		posY = (float)-Math.cos(Math.toRadians(angle))*delt+posY;
	}
	
	public static void droite(float angle, int delta){
		float delt = 0.5f*delta;
		posX = (float)-Math.sin(Math.toRadians(angle))*delt+posX;
		posY = (float)Math.cos(Math.toRadians(angle))*delt+posY;
	}
}
