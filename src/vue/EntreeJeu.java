package vue;

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Rectangle;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import controleur.Controle;

public class EntreeJeu extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton cmdServeur = null;
	private JButton cmdClient = null;
	private JButton cmdQuitter = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JTextArea txtIP = null;
	/**
	 * This is the default constructor
	 */
	
	//--- Autres propriétés ---
		private Controle controle ;
		
	//--- Constructeur ---	
	public EntreeJeu(Controle controle) {
		super();
		setDefaultCloseOperation(EXIT_ON_CLOSE) ;
		this.controle = controle ;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 155);
		this.setContentPane(getJContentPane());
		this.setTitle("Urban Marginal");
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(4, 67, 77, 22));
			jLabel2.setText("IP Serveur:");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(4, 44, 281, 19));
			jLabel1.setText("Je veux me connecter à un serveur existant:");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(4, 17, 152, 22));
			jLabel.setText("Je Veux être un serveur:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getCmdServeur(), null);
			jContentPane.add(getCmdClient(), null);
			jContentPane.add(getCmdQuitter(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getTxtIP(), null);
		}
		return jContentPane;
	}
//--- clic sur le bouton Demarrer ---
	private void cmdServeur_clic(){
		this.controle.evenementVue(this, "serveur");
	}
	
	/**
	 * This method initializes cmdServeur	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCmdServeur() {
		if (cmdServeur == null) {
			cmdServeur = new JButton();
			cmdServeur.setBounds(new Rectangle(166, 17, 122, 23));
			cmdServeur.setText("Démarrer");
			cmdServeur.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					cmdServeur_clic();
				}
			});
		}
		return cmdServeur;
	}
//--- clic sur  le bouton Connecter ---
	private void cmdClient_clic(){
		this.controle.evenementVue(this, this.txtIP.getText()) ;
		// dans le cas où la connexion se passerait mal
		this.txtIP.setText("") ;
		this.txtIP.requestFocus() ;
	}
	/**
	 * This method initializes cmdClient	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCmdClient() {
		if (cmdClient == null) {
			cmdClient = new JButton();
			cmdClient.setBounds(new Rectangle(165, 67, 123, 22));
			cmdClient.setText("Connecter");
			cmdClient.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					cmdClient_clic(); // TODO Auto-generated Event stub mouseClicked()
				}
			});
		}
		return cmdClient;
	}
//--- clic sur le bouton Quitter ---
	private void cmdQuitter_clic(){
		System.exit(0);
	}
	/**
	 * This method initializes cmdQuitter	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCmdQuitter() {
		if (cmdQuitter == null) {
			cmdQuitter = new JButton();
			cmdQuitter.setBounds(new Rectangle(165, 93, 123, 22));
			cmdQuitter.setText("Quitter");
			cmdQuitter.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					cmdQuitter_clic(); // TODO Auto-generated Event stub mouseClicked()
				}
			});
		}
		return cmdQuitter;
	}

	/**
	 * This method initializes txtIP	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getTxtIP() {
		if (txtIP == null) {
			txtIP = new JTextArea();
			txtIP.setBounds(new Rectangle(85, 70, 77, 18));
			txtIP.setText("127.0.0.1");
		}
		return txtIP;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
