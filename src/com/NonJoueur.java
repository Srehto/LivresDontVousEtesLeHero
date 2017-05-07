package com;

public class NonJoueur extends Personnage {

	private int nb_assault; /* Un evenement peut intervenir apres un certain nombre d'assault*/
	private int lance_des;
	private int res_de_necessaire;

	public NonJoueur(String Nom, int habilite, int endurance, int nb_assault) {
		super(Nom,habilite,endurance);
		this.nb_assault=nb_assault;
	}


	public int getNb_assault() {
		return nb_assault;
	}
	public void setNb_assault(int nb_assault) {
		this.nb_assault = nb_assault;
	}
	public int getLance_des() {
		return lance_des;
	}
	public void setLance_des(int lance_des) {
		this.lance_des = lance_des;
	}
	public int getRes_de_necessaire() {
		return res_de_necessaire;
	}
	public void setRes_de_necessaire(int res_de_necessaire) {
		this.res_de_necessaire = res_de_necessaire;
	}

	@Override
	public String toString()
	{
		return "Nom du PNJ : " + getNom() + "\nHabilite : "	+ getHabilete_max() + "\nEndurance : " + getEndurance_max() + "\n";
	}




}
