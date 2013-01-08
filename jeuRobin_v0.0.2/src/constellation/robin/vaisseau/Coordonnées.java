package constellation.robin.vaisseau;

import java.lang.Math;

public class Coordonnées {
	double x, y;
	
	Coordonnées(){
		x=0;
		y=0;
	}
	Coordonnées(double x,double y){
		this.x =x;
		this.y =y;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	
	public Coordonnées rotCoord(double angle){
		double angle2 = Math.atan2(this.y,this.x);
		double x = Math.sqrt(Math.pow(this.x, 2)+Math.pow(this.y, 2))*Math.cos(Math.toRadians(angle)+angle2);
		double y = Math.sqrt(Math.pow(this.x, 2)+Math.pow(this.y, 2))*Math.sin(Math.toRadians(angle)+angle2);
		return new Coordonnées(x, y);
	}
	
	public void moins(Coordonnées coord){
		x-=coord.getX();
		y-=coord.getY();
	}
	public void plus(Coordonnées coord){
		x+=coord.getX();
		y+=coord.getY();
	}
	
}
