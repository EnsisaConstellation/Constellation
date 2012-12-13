package constellation.chat.robin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class choixPseudo extends JFrame implements ActionListener{

	private JPanel container;
	private JTextField jtf;
	private JLabel label;
	private JButton bouton;
	boolean clic = false;
	
	public choixPseudo(){
		container = new JPanel();
		jtf = new JTextField("Anon");
		label = new JLabel("pseudo : ");
		bouton = new JButton("Ok");
		
		this.setTitle("choix pseudo");
		this.setSize(300, 110);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		container.setBackground(Color.white);
		container.setLayout(new BorderLayout());
		
		JPanel top = new JPanel();
		Font police = new Font("Arial", Font.BOLD, 14);
		jtf.setFont(police);
		jtf.setPreferredSize(new Dimension(200, 30));
		jtf.setForeground(Color.BLUE);
		top.add(label);
		top.add(jtf);
		container.add(top, BorderLayout.NORTH);
		
		JPanel bot = new JPanel();
		bouton.setPreferredSize(new Dimension(60, 30));
		bot.add(bouton);
		bouton.addActionListener(this);
		container.add(bot, BorderLayout.SOUTH);
		
		this.setContentPane(container);
		this.setVisible(true);
		}

	
	public void actionPerformed(ActionEvent arg0) {
		this.clic = true; 
	}
	
	boolean getClic(){
		return clic;
	}
	JTextField getText(){
		return jtf;
	}
	
	
}
