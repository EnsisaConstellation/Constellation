package constellation.robin.vaisseau;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import java.util.LinkedList;
import java.util.List;


public class Main extends BasicGame {
	static int fenH = 600, fenL = 800;
	Image bCentre = null, bGris = null, croix = null, etoile = null, moteur1 = null, moteur2, moteur3, moteur4, moteur5;
	static List<Vaisseaux> listeVaisseaux = new LinkedList<Vaisseaux>();
	double angle, petitDelta, max, act;

	
    public Main()
    {
        super("Constellation - le jeu");
    }
    public static void main(String[] args) throws SlickException {
      AppGameContainer app = 
         new AppGameContainer(new Main());

      app.setDisplayMode(fenL, fenH, false);
      app.start();
    }

	
	@Override
	public void init(GameContainer gc) throws SlickException {
		// TODO Auto-generated method stub
		bCentre = new Image("data/bloc_central.png");
		bGris = new Image("data/bloc_gris2.png");
		croix = new Image("data/croix.png");
		etoile = new Image("data/etoile.png");
		moteur1 = new Image("data/moteur_1.png");
		moteur2 = new Image("data/moteur_2.png");
		moteur3 = new Image("data/moteur_3.png");
		moteur4 = new Image("data/moteur_4.png");
		moteur5 = new Image("data/moteur_5.png");
	      listeVaisseaux.add(new Vaisseaux(100, 100));
	      listeVaisseaux.get(0).addModules(new Moteurs(new Coordonnées(1,0),1,0));
	      listeVaisseaux.get(0).addModules(new Moteurs(new Coordonnées(2,0),1,90));
	      listeVaisseaux.get(0).addModules(new Moteurs(new Coordonnées(2,1),1,180));
	      listeVaisseaux.get(0).addModules(new Moteurs(new Coordonnées(-1,0),1,270));
	      listeVaisseaux.add(new Vaisseaux(230, 230));
	      listeVaisseaux.get(1).addModules(new Modules(new Coordonnées(1,0),1, 0));
	      listeVaisseaux.get(1).addModules(new Moteurs(new Coordonnées(2,0),1,0));
	      listeVaisseaux.get(1).addModules(new Modules(new Coordonnées(2,1),1,0));
	      listeVaisseaux.get(1).addModules(new Modules(new Coordonnées(3,0),1,-90));
	}
	
	
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		petitDelta = (double)delta/100;
		// TODO Auto-generated method stub
		setInactif();
		calcG();
		actionJoueur(gc);
		deplacement(petitDelta);
		posModVaisseaux();
		angle+=delta;
		
		for(int j=0;j<listeVaisseaux.get(0).getModules().size();j++){
			//System.out.println(((Moteurs) listeVaisseaux.get(0).getModules().get(j)).getVitAct()/((Moteurs) listeVaisseaux.get(0).getModules().get(j)).getVitMax());
		}
		//test rotation
		//listeVaisseaux.get(0).setOrientation((long)angle/10);
		//listeVaisseaux.get(1).setOrientation((long)-angle/10);
	}
    
    
  	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		// TODO Auto-generated method stub
  		for(int i=40;i<800;i+=80)
  			for(int j=40;j<600;j+=80)
  				etoile.draw(i,j);
  		
  		renderVaisseaux();
  		
  		
	}
	  
    public void renderVaisseaux(){
    	for(int i=0;i<listeVaisseaux.size();i++){
  			bCentre.setRotation((float)listeVaisseaux.get(i).getOrientation());
  			bCentre.draw((float)listeVaisseaux.get(i).getPosition().getX(),(float)listeVaisseaux.get(i).getPosition().getY());
  			
  			for(int j=0;j<listeVaisseaux.get(i).getModules().size();j++){
  				if(listeVaisseaux.get(i).getModules().get(i).getClass().toString().equals("class constellation.robin.vaisseau.Moteurs")){
  					moteur1.setRotation((float)(listeVaisseaux.get(i).getModules().get(j).getDirection()+listeVaisseaux.get(i).getOrientation()));
  					moteur2.setRotation((float)(listeVaisseaux.get(i).getModules().get(j).getDirection()+listeVaisseaux.get(i).getOrientation()));
  					moteur3.setRotation((float)(listeVaisseaux.get(i).getModules().get(j).getDirection()+listeVaisseaux.get(i).getOrientation()));
  					moteur4.setRotation((float)(listeVaisseaux.get(i).getModules().get(j).getDirection()+listeVaisseaux.get(i).getOrientation()));
  					moteur5.setRotation((float)(listeVaisseaux.get(i).getModules().get(j).getDirection()+listeVaisseaux.get(i).getOrientation()));
  					System.out.println(act*100);
  					act = ((Moteurs) listeVaisseaux.get(i).getModules().get(i)).getVitAct();
  					max = ((Moteurs) listeVaisseaux.get(i).getModules().get(i)).getVitMax();
  					if(act/max==0)
  						moteur1.draw((float)listeVaisseaux.get(i).getModules().get(j).getAbsC().getX(),(float)listeVaisseaux.get(i).getModules().get(j).getAbsC().getY());
  					else if(act/max*100<10&&act/max*100>0)
  						moteur2.draw((float)listeVaisseaux.get(i).getModules().get(j).getAbsC().getX(),(float)listeVaisseaux.get(i).getModules().get(j).getAbsC().getY());
  					else if(act/max*100<50&&act/max*100>10)
  	  					moteur3.draw((float)listeVaisseaux.get(i).getModules().get(j).getAbsC().getX(),(float)listeVaisseaux.get(i).getModules().get(j).getAbsC().getY());
  					else if(act/max*100<80&&act/max*100>50)
  	  					moteur4.draw((float)listeVaisseaux.get(i).getModules().get(j).getAbsC().getX(),(float)listeVaisseaux.get(i).getModules().get(j).getAbsC().getY());
  					else if(act/max*100>80)
  	  					moteur5.draw((float)listeVaisseaux.get(i).getModules().get(j).getAbsC().getX(),(float)listeVaisseaux.get(i).getModules().get(j).getAbsC().getY());
  					
  				}
  				else{
  				bGris.setRotation((float)(listeVaisseaux.get(i).getModules().get(j).getDirection()+listeVaisseaux.get(i).getOrientation()));
  				bGris.draw((float)listeVaisseaux.get(i).getModules().get(j).getAbsC().getX(),(float)listeVaisseaux.get(i).getModules().get(j).getAbsC().getY());
  			}}
  		croix.draw((float)(listeVaisseaux.get(i).getCentreG().getX()+listeVaisseaux.get(i).getPosition().getX()+20),(float)(listeVaisseaux.get(i).getCentreG().getY()+listeVaisseaux.get(i).getPosition().getY()+20));}
    }
    
    public void posModVaisseaux(){
    	for(int i=0;i<listeVaisseaux.size();i++){
    		listeVaisseaux.get(i).calcPosModules();
    	}
    }
    
    public void calcG(){
		for(int i=0;i<listeVaisseaux.size();i++)
			listeVaisseaux.get(i).calcG();
    }
    
    public void setInactif(){
		for(int i=0;i<listeVaisseaux.size();i++)
			listeVaisseaux.get(i).setInactive();
    }
    
    public void actionJoueur(GameContainer gc){
    	Input input = gc.getInput();
        if(input.isKeyDown(Input.KEY_Z))
        {
            listeVaisseaux.get(0).avance();
        }
        if(input.isKeyDown(Input.KEY_S))
        {
            listeVaisseaux.get(0).recule();
        }
        if(input.isKeyDown(Input.KEY_Q))
        {
            listeVaisseaux.get(0).gauche();
        }
        if(input.isKeyDown(Input.KEY_D))
        {
            listeVaisseaux.get(0).droite();
        }
    }
    
    public void deplacement(double delta){
    	for(int i=0;i<listeVaisseaux.size();i++){
    		listeVaisseaux.get(i).deplacement(delta);
    	}
    }
}
