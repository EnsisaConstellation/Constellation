package swing;

import javax.imageio.ImageIO;
import javax.swing.*;

import client.Client;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
@SuppressWarnings("serial")
public class Login extends JFrame implements ActionListener{
	public static final String IP_ADDRESS_PATTERN = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}"; //pour la vérification de la forme de l'adresse IP -> regex
	public static final String PORT_IP_ADDRESS_PATTERN = "\\d{1,5}"; //pour vérification de la forme du port -> Regex
	private Bouton SUBMIT;// bouton 
	private JPanel panel;// creation de  panel de Jframe
	private JLabel label1;// creation du texte dans la fentree
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JLabel lab;
	final JTextField  text1;// text1 c'est le nom d'utilisateur
	final JPasswordField text2;  // mot de passe
	final JTextField text3; // adress ip 
	final JTextField text4;  // adress du port 
	private Image img;
	
	public Login()// constructeur 
	{
		//paramètres de la fenêtre
		this.setTitle("LOGIN FORM"); //titre de la fenêtre
		this.setSize(400, 200); //taille de la fenêtre
		this.setResizable(false);// empecher la fenetre de se redimensionner 
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    
		//mise en place de la structure
	    label1 = new JLabel();
		label1.setText("Username:");//inserer du texte dans la fenetre
		text1 = new JTextField(15);

		label2 = new JLabel();
		label2.setText("Password:");
	    text2 = new JPasswordField(15);
	    this.setLayout(new BorderLayout());
	    
	    label3=new JLabel ("IP Adress");
	    text3=new JTextField(15);
	    label4=new JLabel("Port");
	    text4=new JTextField(15);

 
		SUBMIT=new Bouton("SUBMIT");// bouton qui porte le nom de submit 
		
        panel=new JPanel(new GridLayout(7,1));// creation d'une grille 
		panel.add(label1);
		panel.add(text1);
		panel.add(label2);
		panel.add(text2);
		panel.add(label3);
		panel.add(text3);
		panel.add(label4);
		panel.add(text4);
		panel.add(SUBMIT);
		
	    add(panel,BorderLayout.CENTER); //on place la grille panel au centre
        SUBMIT.addActionListener(this);// la fenetre ecoute le bouton submit 
        this.setVisible(true);
	}
	
   public void actionPerformed(ActionEvent ae)// action que dois accomplir le bouton 
	{
	   //Vérification des champs IP et port
	   	String ip=text3.getText();
   		String port=text4.getText();
   		Matcher matcherIp = Pattern.compile(IP_ADDRESS_PATTERN).matcher(ip); //vérification de l'IP
   		Matcher matcherIpPort = Pattern.compile(PORT_IP_ADDRESS_PATTERN).matcher(port); //vérification du port
   		if(matcherIp.matches() && matcherIpPort.matches()){
   		//vérification du login et du mdp
	    	String login=text1.getText();
	    	String pass=new String(text2.getPassword());
	    	try {
	    		
				Client user = new Client(ip,Integer.valueOf(port),login,pass);
				try{
					for(int i=0;i<3;i++){
						user.connexion();
						if (user.getStatusConnexion()){
							NextPage page=new NextPage();
							page.setVisible(true);
							i=100; //pour ne plus refaire la boucle ;)
							/*
							System.out.println(client);//pour le test
							//juste des tests pour tester les fonctions implémentées
							System.out.println(client.getName());
							System.out.println(client.getRooms());
							System.out.println(client.getContacts());
							 */
							
						//TODO si on quitte/ferme la fenêtre -> appeler user.deconnexion(); pour remettre le token de connexion à false
						}
					}
				} catch(RemoteException e){
					//JOptionPane.showMessageDialog(this,"Please verify your login and password","Error",JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			} catch (RemoteException e2) {
				//System.out.println("Cannot create your client. Retry.");
				//JOptionPane.showMessageDialog(this,"Cannot create your client. Retry.","Error",JOptionPane.ERROR_MESSAGE);// faire apparaitre le message dans la page de dialogue 
				e2.printStackTrace();
			}
   		}
   		else if(!(matcherIp.matches()) && !(matcherIpPort.matches())){
	    	System.out.println("Verify IP Adress and Port");
	    	JOptionPane.showMessageDialog(this,"Verify IP Adress and Port","Error",JOptionPane.ERROR_MESSAGE);
    	}
    	else if(!(matcherIp.matches())){
    		System.out.println("Verify IP Adress");
    		JOptionPane.showMessageDialog(this,"Verify IP Adress","Error",JOptionPane.ERROR_MESSAGE);
    	}
    	else if(!(matcherIpPort.matches())){
    		System.out.println("Verify IP Port");
	    	JOptionPane.showMessageDialog(this,"Verify IP Port","Error",JOptionPane.ERROR_MESSAGE);
	    }
	}	
}