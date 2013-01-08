package constellation.robin.vaisseau;

import java.util.LinkedList;
import java.util.List;

public class Vaisseaux {
	List<Modules> modules = new LinkedList<Modules>();
	Coordonnées position, centreG, dep, vit;
	double orientation, vitesse, vie, freinage, angle;
	 double poids, moduleTaille, poidsT;

	
	Vaisseaux(double x, double y){
		position = new Coordonnées(x, y);
		vit = new Coordonnées();
		orientation = 90;
		vitesse = 0;
		vie = 100;
		poids = 20;
		moduleTaille = 50;
		calcG();
		freinage = 0.003; 
	}
	public List<Modules> getModules() {
		return modules;
	}
	public void addModules(Modules module) {
		modules.add(module);
		calcG();
	}
	public Coordonnées getPosition() {
		return position;
	}
	public void setPosition(Coordonnées position) {
		this.position = position;
	}
	public Coordonnées getCentreG() {
		return centreG;
	}
	public void setCentreG(Coordonnées centreG) {
		this.centreG = centreG;
	}
	public double getOrientation() {
		return orientation;
	}
	public void setOrientation(long orientation) {
		this.orientation = orientation;
	}
	public double getVitesse() {
		return vitesse;
	}
	public void setVitesse(long vitesse) {
		this.vitesse = vitesse;
	}
	public double getVie() {
		return vie;
	}
	public void setVie(long vie) {
		this.vie = vie;
	}
	public double getPoids() {
		return poids;
	}
	public void setPoids(long poids) {
		this.poids = poids;
	}
	
	
	public void damage(double damage){
		vie-=damage;
	}
	
	public void calcG(){
		calcPoidsT();
		double poidsX=0, poidsY=0;
		for(int i=0;i<modules.size();i++){
			poidsX += modules.get(i).getPoids() * modules.get(i).getRelC().getX();
			poidsY += modules.get(i).getPoids() * modules.get(i).getRelC().getY();
		}
		centreG = new Coordonnées(poidsX*moduleTaille/poidsT, poidsY*moduleTaille/poidsT).rotCoord(orientation);
	}
	
	public void calcPoidsT(){
		poidsT = poids;
		for(int i=0;i<modules.size();i++)
			poidsT += modules.get(i).getPoids();
	}
	
	public void calcPosModules(){
		for(int i=0;i<modules.size();i++){
			modules.get(i).relC(position, orientation, moduleTaille);
		}
	}
	
	public void setInactive(){
		for(int i=0;i<modules.size();i++)
			if(modules.get(i).getClass().toString().equals("class constellation.robin.vaisseau.Moteurs")){
				((Moteurs) modules.get(i)).setActif(false);
			}
	}
	
	public void avance(){
		for(int i=0;i<modules.size();i++)
			if(modules.get(i).getClass().toString().equals("class constellation.robin.vaisseau.Moteurs"))
				if(!((Moteurs) modules.get(i)).isActif())
					if(modules.get(i).getDirection()==270)
						((Moteurs) modules.get(i)).setActif(true);
	}
	public void recule(){
		for(int i=0;i<modules.size();i++)
			if(modules.get(i).getClass().toString().equals("class constellation.robin.vaisseau.Moteurs"))
				if(!((Moteurs) modules.get(i)).isActif())
					if(modules.get(i).getDirection()==90)
						((Moteurs) modules.get(i)).setActif(true);
	}
	public void gauche(){
		for(int i=0;i<modules.size();i++)
			if(modules.get(i).getClass().toString().equals("class constellation.robin.vaisseau.Moteurs"))
				if(!((Moteurs) modules.get(i)).isActif())
					if(modules.get(i).getDirection()==0)
						((Moteurs) modules.get(i)).setActif(true);
	}
	public void droite(){
		for(int i=0;i<modules.size();i++)
			if(modules.get(i).getClass().toString().equals("class constellation.robin.vaisseau.Moteurs"))
				if(!((Moteurs) modules.get(i)).isActif())
					if(modules.get(i).getDirection()==180)
						((Moteurs) modules.get(i)).setActif(true);
	}
	
	public void deplacement(double delta){
		dep = new Coordonnées();
		for(int i=0;i<modules.size();i++)
			if(modules.get(i).getClass().toString().equals("class constellation.robin.vaisseau.Moteurs"))
				if(((Moteurs) modules.get(i)).isActif()){
					if(modules.get(i).getDirection()==0){
						((Moteurs) modules.get(i)).majVit(delta);
						dep.setX(dep.getX()-((Moteurs) modules.get(i)).getVitAct()*delta);
					}
					if(modules.get(i).getDirection()==90){
						((Moteurs) modules.get(i)).majVit(delta);
						dep.setY(dep.getY()+((Moteurs) modules.get(i)).getVitAct()*delta);
						
					}
					if(modules.get(i).getDirection()==180){
						((Moteurs) modules.get(i)).majVit(delta);
						dep.setX(dep.getX()+((Moteurs) modules.get(i)).getVitAct()*delta);
						
					}
					if(modules.get(i).getDirection()==270){
						((Moteurs) modules.get(i)).majVit(delta);
						dep.setY(dep.getY()-((Moteurs) modules.get(i)).getVitAct()*delta);
						
					}
				}
				else
					((Moteurs) modules.get(i)).setVitAct(0);
		acceleration(dep, delta);
									
	}
	
	public void acceleration(Coordonnées dep, double delta){
		vit.plus(dep);
		angle = Math.toDegrees(Math.atan2(vit.getY(),vit.getX()));
		vit = vit.rotCoord(-angle);
		if(vit.getX()-delta*freinage*Math.pow(vit.getX(),2)*poidsT-0.1>0)
			vit.setX(vit.getX()-delta*freinage*Math.pow(vit.getX(),2)*poidsT-0.01*delta*freinage);
		else
			vit.setX(0);
		vit = vit.rotCoord(angle);

		
		position.plus(new Coordonnées(vit.getX()*delta, vit.getY()*delta));
	}
}
