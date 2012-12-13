package constellation.robin.vaisseau;

public class module {

//******************************************	initialisation	****************************************************
	protected float �tat;
	protected int direction;
	protected static int poids;
	protected int posX;
	protected int posY;
	static public int test = 2;
	protected float positionX;
	protected float positionY;
	
	//constructeur par defaut
	public module(){
		this.�tat = 100;
		this.direction = 1; 		//1 devant 2 gauche 3 arri�re 4 droite
		this.poids = 2;
		this.posX = 0;
		this.posY = 1;
	}
	
	//constructeur complet
	public module(int positionX, int positionY){
		this.�tat = 100;
		this.direction = 1; 		//1 devant 2 gauche 3 arri�re 4 droite
		this.poids = 2;
		this.posX = positionX;
		this.posY = positionY;
	}
	
//******************************************	m�thodes	****************************************************
	public void position(float angle, vaisseau vRobin){
		angle = -angle;
		positionX = (float)Math.cos(Math.toRadians(angle))*50*posX+(float)Math.sin(Math.toRadians(angle))*50*posY+vRobin.posX;
		positionY = (float)Math.cos(Math.toRadians(angle))*50*posY-(float)Math.sin(Math.toRadians(angle))*50*posX+vRobin.posY;
	}
	
	
	
	
	
//******************************************	get & set	****************************************************
	public float get�tat() {
		return �tat;
	}

	public void set�tat(float �tat) {
		this.�tat = �tat;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public static int getPoids() {
		return poids;
	}

	public void setPoids(int poids) {
		this.poids = poids;
	}

	public int getPositionX() {
		return posX;
	}

	public void setPositionX(int positionX) {
		this.posX = positionX;
	}

	public int getPositionY() {
		return posY;
	}

	public void setPositionY(int positionY) {
		this.posY = positionY;
	}
}
