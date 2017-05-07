package com;

import java.util.ArrayList;

public class Paragraphe {

	//private int Num; /* Numéro du paragraphe : Le but du jeu est d'aller au paragraphe 400 */
	private String Texte; /* Texte de l'histoire correspondant au paragraphe */
	private Evenement evenement; /* Un evenement qui pourrais avoir lieux dans ce paragraphe (CF classe evenement) */
	private int Endu_Affect; /* De combien votre ENDURANCE changera dans ce paragraphe */
	private int Habi_Affect; /* De combien votre HABILITE changera dans ce paragraphe */
	private int Chance_Affect; /* De combien votre CHANCE changera dans ce paragraphe */
	private int chg_or; /* Nombre de pieces d'or gagnée ou perdu dans ce paragraphe */
	private boolean mort; /* Mourrons-nous a cet endroit ? */
	private boolean tenter_chance; /* Allons nous devoir tenter notre chance ? */
	private boolean jete_de; /* Allons nous devoir faire un jeté de dé ici ? */
	private boolean chg_objet; /* Allons nous subire un changement dans notre inventaire ? */
	private boolean perte_sac; /* Allons perdre notre equipement ici ? */
	private int nb_pnj; /* Nombre de PNJ a cet endroit */
	private ArrayList<Choix> choix; /* Liste des choix qui s'offrent a nous */
	private ArrayList<Equipement> equipement_gagne; /* Liste des equipements que l'on gagne qui sont a cet endroit */
	private ArrayList<Equipement> equipement_perdu; /* Liste des equipements que l'on perd qui sont a cet endroit */
	private ArrayList<NonJoueur> PNJ;

	
	
	public Paragraphe() {
		ArrayList<Choix> choix = new ArrayList<Choix>();
		ArrayList<Equipement> equipement_gagne = new ArrayList<Equipement>();
		ArrayList<Equipement> equipement_perdu = new ArrayList<Equipement>();
		ArrayList<NonJoueur> PNJ = new ArrayList<NonJoueur>();
		this.choix=choix;
		this.setEquipement_gagne(equipement_gagne);
		this.setEquipement_perdu(equipement_perdu);
		this.setPNJ(PNJ);
		
	}
	
	public int getChg_or(){
		return chg_or;
	}
	public void setChg_or(int chg_or){
		this.chg_or=chg_or;
	}
	public ArrayList<Equipement> getEquipement_gagne() {
		return equipement_gagne;
	}
	public void setEquipement_gagne(ArrayList<Equipement> equipement_gagne) {
		this.equipement_gagne = equipement_gagne;
	}
	public void add_equipement_gagne(Equipement objet)
	{
		equipement_gagne.add(objet);
	}
	public ArrayList<Equipement> getEquipement_perdu() {
		return equipement_perdu;
	}
	public void setEquipement_perdu(ArrayList<Equipement> equipement_perdu) {
		this.equipement_perdu = equipement_perdu;
	}
	public void add_equipement_perdu(Equipement objet)
	{
		equipement_perdu.add(objet);
	}
	public boolean isChg_objet() {
		return chg_objet;
	}
	public void setChg_objet(boolean chg_objet) {
		this.chg_objet = chg_objet;
	}
	public void ajouter_choix(Choix choix)
	{
		this.choix.add(choix);
	}
	public ArrayList<Choix> getChoix() {
		return choix;
	}
	public void setChoix(ArrayList<Choix> Choix) {
		this.choix = Choix;
	}
	public ArrayList<NonJoueur> getPNJ() {
		return PNJ;
	}
	public void setPNJ(ArrayList<NonJoueur> PNJ) {
		this.PNJ = PNJ;
	}
	public int getNb_pnj(){
		return nb_pnj;
	}
	public void setNb_pnj(int nb_pnj){
		this.nb_pnj=nb_pnj;
	}
	public boolean isTenter_chance() {
		return tenter_chance;
	}
	public void setTenter_chance(boolean tenter_chance) {
		this.tenter_chance = tenter_chance;
	}
	public String getTexte() {
		return Texte;
	}
	public void setTexte(String texte) {
		Texte = texte;
	}
	public int getChance_Affect() {
		return Chance_Affect;
	}
	public void setChance_Affect(int chance_Affect) {
		Chance_Affect = chance_Affect;
	}
	public int getHabi_Affect() {
		return Habi_Affect;
	}
	public void setHabi_Affect(int habi_Affect) {
		Habi_Affect = habi_Affect;
	}
	public int getEndu_Affect() {
		return Endu_Affect;
	}
	public void setEndu_Affect(int endu_Affect) {
		Endu_Affect = endu_Affect;
	}

	public boolean isJete_de() {
		return jete_de;
	}
	public void setJete_de(boolean jete_de) {
		this.jete_de = jete_de;
	}
	public Evenement getEvenement() {
		return evenement;
	}
	public void setEvenement(Evenement evenement) {
		this.evenement = evenement;
	}
	public boolean isMort() {
		return mort;
	}
	public void setMort(boolean mort) {
		this.mort = mort;
	}
	public boolean isPerte_sac() {
		return perte_sac;
	}
	public void setPerte_sac(boolean perte_sac) {
		this.perte_sac = perte_sac;
	}



	@Override
	public String toString()
	{
		String chaine= "Texte : " + Texte + "\nIl y a " + nb_pnj + " PNJ" + "\nChoix : " + choix.toString() + "\n";
		chaine = chaine + "PNJ présents :" + PNJ.toString() + "\n";
		if(isTenter_chance())
				chaine = chaine + "Vous aurez à tenter votre chance\n";
		else
				chaine = chaine + "Vous n'aurez pas à tenter votre chance\n";
		chaine = chaine + "Caracteristique affectés : " + getChance_Affect() + " Points de Chance "
						+ getEndu_Affect() + " Point d'endurance  "
						+ getHabi_Affect() + " Point d'Habileté\n";
		if(isPerte_sac())
			chaine = chaine + "Vous avez perdu votre sac ! \n";
		if(chg_or>=0)
			chaine = chaine + "Changement dans votre bourse : + " + chg_or + " pièces d'or\n";
		else
			chaine = chaine + "Changement dans votre bourse : " + chg_or + " pièces d'or\n";
		if(isJete_de())
			chaine = chaine + "Vous aurez un jet de dés durant ce paragraphe !\n" + evenement.toString();
		else
			chaine = chaine + "Vous n'aurez pas de jet de dés durant ce paragraphe !\n";
		if(isChg_objet())
			chaine = chaine + "Vous avez trouvez les objet suivant : " + equipement_gagne.toString() + " et voud avez perdu " + equipement_perdu.toString() + "\n";
		if(isMort())
			chaine = chaine + "Vous mourrez a ce paragraphe ! \n";
		return chaine;

	}
}
