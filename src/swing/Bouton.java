package swing;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;


public class Bouton extends JButton implements MouseListener {

	 private String name;
	 private Image img;
	 
	 public Bouton(String str){
	    super(str);
	    this.name = str;
	    // inser une image de fond pour le bouton 
	    /*try {
	      img = ImageIO.read(new File("orange.png"));
	        } 
	    catch (IOException e) 
	    {
	      e.printStackTrace();
	    }*/
	    //Grâce à cette instruction, notre objet va s'écouter
	    //Dès qu'un événement de la souris sera intercepté, il en sera averti
	    this.addMouseListener(this);
	  }
	 // methode pour inserer les forme graphique 
	  public void paintComponent(Graphics g){
	    Graphics2D g2d = (Graphics2D)g;
	   
	    g2d.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	    g2d.setColor(Color.black);
	    g2d.drawString(this.name, this.getWidth() /2 - (this.getWidth() /  2 /4), (this.getHeight() / 2) );
	  }
	
	
	
	
	
	
	  //Méthode appelée lors du clic de souris
	  public void mouseClicked(MouseEvent event)
	  {
		 /* try 
		  {
		      img = ImageIO.read(new File("gris.png"));
		    } catch (IOException e) {
		      e.printStackTrace();
		    }*/
	  }
	 
	  //Méthode appelée lors du survol de la souris
	  public void mouseEntered(MouseEvent event)
	  {
		 /* try {
		      img = ImageIO.read(new File("jaune.png"));
		    } catch (IOException e) {
		      e.printStackTrace();
		    }*/
		  
	  }
	 
	  //Méthode appelée lorsque la souris sort de la zone du bouton
	  public void mouseExited(MouseEvent event) 
	  {
		 /* try {
		      img = ImageIO.read(new File("orange.png"));
		        } 
		    catch (IOException e) 
		    {
		      e.printStackTrace();
		    }*/
		  
	  }
	 
	  //Méthode appelée lorsque l'on presse le bouton gauche de la souris
	  public void mousePressed(MouseEvent event) 
	  {/*try
	  {
	      img = ImageIO.read(new File("gris.png"));
      } 
      catch (IOException e) 
      {
          e.printStackTrace();
       }*/
       }
	 
	  //Méthode appelée lorsque l'on relâche le clic de souris
	  public void mouseReleased(MouseEvent event)
	  { 
		 /* try {
		      img = ImageIO.read(new File("orange.png"));
		        } 
		    catch (IOException e) 
		    {
		      e.printStackTrace();
		    }*/
		  
	  }       
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

