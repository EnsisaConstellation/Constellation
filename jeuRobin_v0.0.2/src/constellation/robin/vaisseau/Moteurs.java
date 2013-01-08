package constellation.robin.vaisseau;

public class Moteurs extends Modules {
	double poussée, vitMax, vitAct;
	boolean actif = false;
	
	Moteurs(){
		super();
		poussée = 1;
		vitMax = 3;
		vitAct = 0;
	}
	Moteurs(Coordonnées coord){
		super(coord);
		poussée = 1;
		vitMax = 3;
		vitAct = 0;
	}
	Moteurs(Coordonnées coord, int test, double direction){
		super(coord, test, direction);
		poussée = 5;
		vitMax = 50;
		vitAct = 0;
	}
	public double getPoussée() {
		return poussée;
	}
	public void setPoussée(double poussée) {
		this.poussée = poussée;
	}
	public double getVitMax() {
		return vitMax;
	}
	public void setVitMax(double vitMax) {
		this.vitMax = vitMax;
	}
	public boolean isActif() {
		return actif;
	}
	public void setActif(boolean actif) {
		this.actif = actif;
	}
	public double getVitAct() {
		return vitAct;
	}
	public void setVitAct(double vitAct) {
		this.vitAct = vitAct;
	}
	
	
	public void majVit(double delta){
		if(vitAct+poussée*delta>vitMax)
			vitAct = vitMax;
		else
			vitAct+=poussée*delta;
	}
	
	public String toString(){
		return super.toString()+"\n\tvitesse max : "+vitMax;
	}
}
