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

public class panel extends JFrame implements ActionListener{

	private JPanel container;
	private JTextField jtf;
	private JLabel label;
	private JButton bouton;
	boolean clic = false;
	private JLabel pseudo1;
	private JLabel pseudo2;
	private JLabel pseudo3;
	private JPanel pseudoCont;
	private JLabel message1;
	
	public panel(){
		container = new JPanel();
		jtf = new JTextField("");
		label = new JLabel("votre message");
		//pseudoCont = new JPanel();
		container.setBackground(Color.white);
		container.setLayout(new BorderLayout());
		
		this.setTitle("Chat Constellation");
		this.setSize(500, 300);
		bouton = new JButton("Envoyer");
		bouton.addActionListener(this);
		bouton.setPreferredSize(new Dimension(100, 30));
		
		JPanel west = new JPanel();
		setPseudo1(new JLabel(""));
		//pseudo2 = new JLabel("ttt");
		//pseudo3 = new JLabel("ttt");
		JPanel center = new JPanel();
		setMessage1(new JLabel(""));
		
		west.add(getPseudo1());
		center.add(getMessage1());
		//west.add(pseudo2);
		//west.add(pseudo3);
		container.add(west, BorderLayout.WEST);
		container.add(center, BorderLayout.CENTER);
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		JPanel bot = new JPanel();
		Font police = new Font("Arial", Font.BOLD, 14);
		jtf.setFont(police);
		jtf.setPreferredSize(new Dimension(250, 30));
		jtf.setForeground(Color.BLUE);
		bot.add(label);
		bot.add(jtf);
		bot.add(bouton);
		container.add(bot, BorderLayout.SOUTH);
		this.setContentPane(container);
		//this.setContentPane(pseudoCont);
		this.setVisible(true);
		}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		clic = true;
	}

	
	boolean getClic(){
		return clic;
	}
	JTextField getText(){
		return jtf;
	}
	void resetClic(){
		clic = false;
	}

	public JLabel getPseudo1() {
		return pseudo1;
	}

	public void setPseudo1(JLabel pseudo1) {
		this.pseudo1 = pseudo1;
	}

	public JLabel getMessage1() {
		return message1;
	}

	public void setMessage1(JLabel message1) {
		this.message1 = message1;
	}
}
