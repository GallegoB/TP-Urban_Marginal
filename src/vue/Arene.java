package vue;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import outils.son.Son;

import controleur.Controle;
import controleur.Global;


public class Arene extends JFrame implements Global {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel lblFond = null;
	private JPanel jpnJeu = null;
	private JPanel jpnMurs = null;
	private JScrollPane jspChat = null;
	private JTextArea txtChat = null;
	private JTextField txtSaisie = null;
	
	//--- autres propri�t�s priv�es ---
	private boolean client ;
	private Controle controle ;		// pour acc�der au controleur
	private Son[] lessons = new Son[SON.length] ;	// les sons
	
	public Arene(Controle controle, String typejeu) {
		super();
		// pour savoir si c'est l'ar�ne du client ou du serveur
		this.client = (typejeu=="client") ;
		this.controle = controle ;
		setDefaultCloseOperation(EXIT_ON_CLOSE) ;
		initialize();
		// chargement des sons et d�marrage de la musique d'ambiance
		if (this.client) {
			for (int k=0 ; k<SON.length ; k++) {
				this.lessons[k] = new Son(CHEMINSONS+SON[k]) ;
			}
			(new Son(SONAMBIANCE)).playContinue() ;
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(L_ARENE, H_ARENE + H_CHAT);
		this.setContentPane(getJContentPane());
		this.setTitle("Ar�ne");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			
			lblFond = new JLabel();
			lblFond.setBounds(new Rectangle(0, 0, L_ARENE, H_ARENE));
			lblFond.setIcon(new ImageIcon(FONDARENE)) ;
			lblFond.setText("");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJpnMurs(), null);
			jContentPane.add(getJpnJeu(), null);
		
			jContentPane.add(lblFond, null);
			if (this.client) {
				jContentPane.add(getTxtSaisie(), null);
				jContentPane.addKeyListener(new KeyAdapter() {
					public void keyPressed(KeyEvent e) {
						jContentPane_keyPressed(e) ;
					}
				});
			}
			jContentPane.add(getJspChat(), null);
			
		}
		return jContentPane;
	}

	/**
	 * This method initializes jpnJeu	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpnJeu() {
		if (jpnJeu == null) {
			jpnJeu = new JPanel();
			jpnJeu.setOpaque(false) ;
			jpnJeu.setLayout(null);
			jpnJeu.setBounds(new Rectangle(0, 0, L_ARENE, H_ARENE));
		}
		return jpnJeu;
	}

	/**
	 * This method initializes jpnMurs	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public JPanel getJpnMurs() {
		if (jpnMurs == null) {
			jpnMurs = new JPanel();
			jpnMurs.setOpaque(false) ;
			jpnMurs.setLayout(null);
			jpnMurs.setBounds(new Rectangle(0, 0, L_ARENE, H_ARENE));
		}
		return jpnMurs;
	}

	/**
	 * This method initializes jspChat	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJspChat() {
		if (jspChat == null) {
			jspChat = new JScrollPane();
			jspChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS) ;
			jspChat.setViewportView(getTxtChat());
			jspChat.setBounds(new Rectangle(MARGE, H_ARENE + H_SAISIE + 2*MARGE, 
					L_ARENE - 3*MARGE, H_CHAT - H_SAISIE - 8*MARGE));
		}
		return jspChat;
	}

	/**
	 * This method initializes txtChat	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	public JTextArea getTxtChat() {
		if (txtChat == null) {
			txtChat = new JTextArea();
		}
		return txtChat;
	}

	/**
	 * This method initializes txtSaisie	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtSaisie() {
		if (txtSaisie == null) {
			txtSaisie = new JTextField();
			txtSaisie.setBounds(new Rectangle(2, H_ARENE + MARGE, 
					L_ARENE - 2*MARGE, H_SAISIE));
			txtSaisie.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					txtSaisie_keyPressed(e);
				}
			});
		}
		return txtSaisie;
	}
	
	
	//--- Ajout d'un mur ---
		public void ajoutMur (JLabel unmur) {
			this.jpnMurs.add(unmur) ;
			this.jpnMurs.repaint() ;
		}
		
	//--- Ajout de tout le panel des murs ---
	public void ajoutPanelMurs (JPanel unpanel) {
		this.jpnMurs.add(unpanel) ;
		this.jpnMurs.repaint() ;
		this.jContentPane.requestFocus() ;
	}
	
	//--- Ajout d'un joueur (personnage ou message) ---
	public void ajoutJoueur (JLabel unlabel) {
		this.jpnJeu.add(unlabel) ;
	}
	
	//--- Ajout ou modification d'un joueur (personnage ou message) ---
	public void ajoutmodifJoueur (int num, JLabel unlabel) {
		try {
			this.jpnJeu.remove(num) ;
		}catch (ArrayIndexOutOfBoundsException ex) {}
		this.jpnJeu.add(unlabel, num) ;
		this.jpnJeu.repaint() ;
	}
	
	//--- Touche press�e dans la zone de saisie ---
		private void txtSaisie_keyPressed (KeyEvent e) {
			if (e.getKeyCode()==KeyEvent.VK_ENTER) {
				this.controle.evenementVue(this, CHAT+SEPARE+this.txtSaisie.getText()) ;
				this.txtSaisie.setText("") ;
				this.jContentPane.requestFocus() ;
			}
		}
		
		//--- Ajout d'une phrase dans la conversation ---
		public void ajoutChat (String laphrase) {
			this.txtChat.setText(laphrase+"\r\n"+this.txtChat.getText()) ;
		}
		
		//--- Remplace le contenu de l'affichage de la conversation ---
		public void remplaceChat (String letexte) {
			this.txtChat.setText(letexte) ;
		}
		//--- Touche press�e dans la zone du panel principal ---
		private void jContentPane_keyPressed (KeyEvent e) {
			int valeur = -1 ;
			switch (e.getKeyCode()) {
				case KeyEvent.VK_SPACE : valeur = TIRE ;   break ;
				case KeyEvent.VK_LEFT  : valeur = GAUCHE ; break ;
				case KeyEvent.VK_RIGHT : valeur = DROITE ; break ;
				case KeyEvent.VK_DOWN  : valeur = BAS ;    break ;
				case KeyEvent.VK_UP    : valeur = HAUT ;   break ;
			}
			if (valeur != -1) {
				this.controle.evenementVue(this, ACTION+SEPARE+valeur) ;
			}
		}
		

		//--- joue un son demand� par le serveur ---
		public void joueSon (int numson) {
			this.lessons[numson].play() ;
		}
}  
