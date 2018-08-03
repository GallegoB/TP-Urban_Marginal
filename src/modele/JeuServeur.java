package modele;

import java.util.ArrayList;
import java.util.Hashtable;

import controleur.Controle;
import controleur.Global;
import outils.connexion.Connection;

public class JeuServeur extends Jeu implements Global {

	//--- Propro�t�s priv�es ---
	private Hashtable<Connection, Joueur> lesjoueurs = new Hashtable<Connection, Joueur>() ;
	private ArrayList<Mur> lesmurs = new ArrayList<Mur>() ;
	private ArrayList<Joueur> lesJoueursDansLordre = new ArrayList<Joueur>() ;
	
	//--- Constructeur ---
	public JeuServeur (Controle controle) {
		super.controle = controle ;
		Label.nbLabel = 0 ;
	}
	
	//--- Construction des murs ---
	public void constructionMurs () {
		for (int k=0 ; k<NBMURS ; k++) {
			this.lesmurs.add(new Mur()) ;
			this.controle.evementModele(this, "ajout mur", this.lesmurs.get(k).getLabel().getJLabel()) ;
		}
	}
	//--- Nouveau label du jeu (donc ajout dans le panel du jeu) ---
	public void nouveauLabelJeu (Label label) {
		super.controle.evementModele(this, "ajout joueur", label.getJLabel()) ;
	}
	
	@Override
	public void setConnection(Connection connection) {
		this.lesjoueurs.put(connection, new Joueur(this)) ;
		//this.controle.evementModele(this, "envoi panel murs", connection);
		//this.envoi(connection, this) ;
	}

	@Override
	public void reception(Connection connection, Object info) {
		// traitement de l'information re�ue
		String[] infos = ((String)info).split(SEPARE) ;
		switch(Integer.parseInt(infos[0])) {
			case PSEUDO :
				// envoi des murs au nouveau
				this.controle.evementModele(this, "envoi panel murs", connection) ;
				// envoi des anciens joueurs au nouveau
				for (Joueur unjoueur : this.lesJoueursDansLordre) {
					super.envoi(connection, unjoueur.getLabel()) ;
					super.envoi(connection, unjoueur.getMessage()) ;
					super.envoi(connection, unjoueur.getBoule().getLabel()) ;
				}
				// initialisation des labels du personnage et de sa position al�atoire
				this.lesjoueurs.get(connection).initPerso(infos[1], Integer.parseInt(infos[2]), lesjoueurs, lesmurs) ;
				this.lesJoueursDansLordre.add(this.lesjoueurs.get(connection)) ;
				// envoi de la phrase de bienvenue (t'chat)
				String laphrase = "***"+this.lesjoueurs.get(connection).getPseudo()
				+" vient de se connecter ***" ;
				this.controle.evementModele(this, "ajout phrase", laphrase) ;
			break ;
			case CHAT :			// r�ception d'une phrase du t'chat
				laphrase = this.lesjoueurs.get(connection).getPseudo()+">"+infos[1] ;
				this.controle.evementModele(this, "ajout phrase", laphrase) ;
			break ;
			case ACTION :		// r�ception d'une action (d�placement ou tir)
				if (!this.lesjoueurs.get(connection).estMort()) {
					this.lesjoueurs.get(connection).action(Integer.parseInt(infos[1]), 
							lesjoueurs, lesmurs) ;
				}
			break;
		}
	}

	@Override
	public void deconnection(Connection connection) {
		// TODO Auto-generated method stub
		
	}
	//--- envoi � tous les clients ---
		public void envoi (Object info) {
			for (Connection unecle : this.lesjoueurs.keySet()) {
				super.envoi(unecle, info) ;
			}
		}

}
