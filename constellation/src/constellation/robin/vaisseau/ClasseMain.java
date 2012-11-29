package constellation.robin.vaisseau;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


public class ClasseMain extends BasicGame{
	static vaisseau vRobin;
	Image bCentre = null;
	Image bGris = null;
	boolean nonfait = true;
	boolean btnA = true;

	
    public ClasseMain()
    {
        super("Constellation - le jeu");
    }
	
	@Override
    public void init(GameContainer gc) 
            throws SlickException {
		vRobin = new vaisseau();
		bCentre = new Image("data/bloc_central.png");
		bGris = new Image("data/bloc_gris.png");
    }
  
    @Override
    public void update(GameContainer gc, int delta) 
            throws SlickException     
    {
    	if(nonfait){
    	vRobin.addModule(new module(-1,-1));
    	vRobin.addModule(new module(0,-1));
    	vRobin.addModule(new module(0,1));
    	vRobin.addModule(new module(-1,1));
    	vRobin.addModule(new module(-1,0));
    	//environnement.addModule(new module(500, 500));
    	nonfait = false;}
    
    	Input input = gc.getInput();
    	Input userInput = gc.getInput();
    	if(distance(userInput.getMouseX(), userInput.getMouseY(), vRobin.posX+25, vRobin.posY+25)>25){
        if(input.isKeyDown(Input.KEY_Z)&&btnA)
        {
            vRobin.avance(vRobin.rotation(gc), delta);
        }
        if(input.isKeyDown(Input.KEY_S)&&btnA)
        {
        	vRobin.recule(vRobin.rotation(gc), delta);
        }
        if(input.isKeyDown(Input.KEY_Q)&&btnA)
        {
        	vRobin.gauche(vRobin.rotation(gc), delta);
        }
        if(input.isKeyDown(Input.KEY_D)&&btnA)
        {
        	vRobin.droite(vRobin.rotation(gc), delta);
        }
        if(input.isKeyDown(Input.KEY_A))
        {
        	if(btnA) btnA = false;
        	else btnA = true;
        }
        if(btnA)
        bCentre.setRotation(vRobin.rotation(gc));
        for(int i = 0; i<vRobin.getModules().size(); i++){
        if(btnA){
        vRobin.getModule(i).position(vRobin.rotation(gc), vRobin);
		bGris.setRotation(vRobin.rotation(gc));}}
    	}
    }
  
    public void render(GameContainer gc, Graphics g) 
            throws SlickException 
    {
    	Input userInput = gc.getInput();
    	
    	bCentre.draw(vRobin.posX, vRobin.posY);
    	
    	for(int i = 0; i<vRobin.getModules().size(); i++)
    			bGris.draw(vRobin.getModule(i).positionX, vRobin.getModule(i).positionY);
    	
    	for(int i = 0; i<environnement.getModules().size(); i++){
    		bGris.setRotation(0);
    		bGris.draw(environnement.getModule(i).posX, environnement.getModule(i).posY);}
    }

//******************************************	Main	****************************************************
    public static void main(String[] args) 
            throws SlickException
    {
         AppGameContainer app = 
            new AppGameContainer(new ClasseMain());
  
         app.setDisplayMode(800, 600, false);
         app.start();
         
    }
    
    
  //******************************************	Fonctions	****************************************************
    public float distance(float x1, float y1, float x2, float y2){
    	return (float)Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2));
    }
    
    public void transCoordX(float X, float angle){
    	
    }
    
    
    
}



