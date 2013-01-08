package constellation.robin.vaisseau;

public class Modules {
	double vie, direction, poids;
	Coordonnées absC, relC;
	boolean relative = false;
	
	Modules(){
		vie = 100;
		direction = 90;
		poids = 10;
		absC = new Coordonnées();
		relC = null;
	}
	Modules(Coordonnées coord){
		vie = 100;
		direction = 90;
		poids = 10;
		absC = coord;
		relC = null;
	}
	Modules(Coordonnées coord, int choix, double direction){
		relative = true;
		this.direction = direction;
		if(choix == 1){
			relC = coord;
			relC(new Coordonnées(0,0), 0, 0);}
		else
			absC = coord;
		vie = 100;
		poids = 100;
		
	}
	public double getVie() {
		return vie;
	}
	public void setVie(double vie) {
		this.vie = vie;
	}
	public double getDirection() {
		return direction;
	}
	public void setDirection(double direction) {
		this.direction = direction;
	}
	public double getPoids() {
		return poids;
	}
	public void setPoids(double poids) {
		this.poids = poids;
	}
	public Coordonnées getAbsC() {
		return absC;
	}
	public void setAbsC(Coordonnées absC) {
		this.absC = absC;
	}
	public Coordonnées getRelC() {
		return relC;
	}
	public void setRelC(Coordonnées relC) {
		this.relC = relC;
	}
	public boolean isRelative() {
		return relative;
	}
	public void setRelative(boolean relative) {
		this.relative = relative;
	}
	

	public void relC(Coordonnées posV, double rot, double moduleTaille){
		absC = new Coordonnées(posV.getX()+relC.rotCoord(rot).getX()*moduleTaille, posV.getY()+relC.rotCoord(rot).getY()*moduleTaille);
	}
	
	public String toString(){
		return "module : \n\tposition : "+absC.getX()+":"+absC.getY();
	}
	
	public void majAbs(){
		if(relative)
			relC(new Coordonnées(0,0), 0, 0);
	}
	
	public void damage(double damage){
		vie-=damage;
	}
}
