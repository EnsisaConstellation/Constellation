package swing;

import javax.swing.*;
import java.awt.*;
 
class NextPage extends JFrame
{ private JLabel label;
	NextPage()
 	{
	   //setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	   setTitle("la ou la page de dialogue est censé apparaitre ");
       setSize(200,100);
       label = new JLabel("Welcome");
       this.getContentPane().add(label);
       
	    
	}
 }