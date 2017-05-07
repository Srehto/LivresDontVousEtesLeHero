package com;

import java.util.ArrayList;
import java.util.Scanner;


public class Joueur extends Personnage {

	private int chance_max;
	private int chance_cur;
	private int provision;
	private int nb_pieces_or;
	private Paragraphe position;
	private int pos_precedent;
	private ArrayList<Equipement> sac;

	
	public Joueur(Paragraphe position, int habi, int endu) throws InterruptedException
	{
		super(demanderNom(),habi,endu);
		chance_max=jet_de()+6;
		chance_cur=chance_max;
		provision=10;
		this.position=position;
		System.out.println("Bienvenue " + this.getNom() +" !\n\nLes dés vont être jeté !\n");
		System.out.print(".");
		Thread.sleep(1000);
		System.out.print(".");
		Thread.sleep(1000);
		System.out.print(".");
		Thread.sleep(1000);
		System.out.println("\n");
		System.out.println("Vous avez :\n => " +getHabilete_cur() + " points d'HABILETE\n => " + getEndurance_cur() + " points d'ENDURANCE\n => "
				+ this.chance_cur + " points de CHANCE\n");
		int p=choix_potion();
		ArrayList<Equipement> sac= new ArrayList<Equipement>();
		this.sac=sac;
		Equipement Or = new Equipement("PIECE D'OR",0,0,0,0,true);
		sac.add(Or);
		switch(p)
		{
			case 1: Equipement PotionAdresse= new Equipement("POTION D'ADRESSE",20,0,0,1,true);
					PotionAdresse.setConsommable(true);
					PotionAdresse.setEndurance(endu);
					sac.add(PotionAdresse);
					break;
			case 2:	Equipement PotionVigueur = new Equipement("POTION DE VIGUEUR",0,20,0,1,true);
					PotionVigueur.setConsommable(true);
					PotionVigueur.setHabilete(habi);
					sac.add(PotionVigueur);
					break;
			case 3:	Equipement PotionBonneFortune = new Equipement("POTION DE BONNE FORTUNE",0,0,20,1,true);
					PotionBonneFortune.setConsommable(true);
					PotionBonneFortune.setChance(chance_max);
					sac.add(PotionBonneFortune);
					break;
				
		}
		

		Equipement Epee = new Equipement("EPEE",1,0,0,1,false);
		sac.add(Epee);
		Equipement Armure = new Equipement("ARMURE",0,1,0,1,false);
		sac.add(Armure);
		Equipement Provision = new Equipement("REPAS",0,4,0,10,true);
		Provision.setEndurance(4);
		Provision.setConsommable(true);
		sac.add(Provision);
		Equipement Torche = new Equipement("TORCHE",0,0,0,5,true);
		sac.add(Torche);
		Equipement Silex = new Equipement("SILEX",0,0,0,1,false);
		sac.add(Silex);
		Equipement Mechedamadou = new Equipement("MECHE D'AMADOU",0,0,0,1,false);
		sac.add(Mechedamadou);

	}

	
	
	public int getPos_precedent() {
		return pos_precedent;
	}
	public void setPos_precedent(int pos_precedent) {
		this.pos_precedent = pos_precedent;
	}
	public int getNb_pieces_or() {
		return nb_pieces_or;
	}
	public void setNb_pieces_or(int nb_pieces_or) {
		this.nb_pieces_or = nb_pieces_or;
	}
	public ArrayList<Equipement> getSac() {
		return sac;
	}
	public void setSac(ArrayList<Equipement> sac) {
		this.sac = sac;
	}
	public Paragraphe getPosition() {
		return position;
	}
	public void setPosition(Paragraphe position) {
		this.position = position;
	}
	public int getChance_max() {
		return chance_max;
	}
	public void setChance_max(int chance) {
		this.chance_max = chance;
	}
	public int getProvision() {
		return provision;
	}
	public void setProvision(int provision) {
		this.provision = provision;
	}
	public int getChance_cur() {
		return chance_cur;
	}
	public void setChance_cur(int chance_cur) {
		this.chance_cur = chance_cur;
	}
	
	public Boolean tentez_votre_chance()
	{
		setChance_cur(getChance_cur()-1);
		if(jet_de()+jet_de()>getChance_cur()) /*Malchance*/
		{
			System.out.println("Vous avez été Malchanceux");
			return false;
		}
		else /*Chance*/
		{
			System.out.println("Vous avez été Chanceux");
			return true;
		}
	}
	
	public boolean consommer_objet(Equipement objet)
	{
		if(objet.isConsommable())
		{
			setEndurance_cur(getEndurance_cur()+objet.getEndurance());
			setHabilete_cur(getHabilete_cur()+objet.getHabilete());
			setChance_cur(getChance_cur()+objet.getChance());
			if(getEndurance_cur()>getEndurance_max()) /* Potion de vigueur*/
				setEndurance_cur(getEndurance_max());
			if(getHabilete_cur()>getHabilete_max()) /* Potion d'adresse,*/
				setHabilete_cur(getHabilete_max());
			if(getChance_cur()>getChance_max()) /* Potion de chance (ajoute 1 a la chance max) */
			{
				setChance_max(getChance_max()+1);
				setChance_cur(getChance_max());
				
			}
			retirer_objet_sac(objet);
			return true;
		}
		else
			return false;
	}
	
	public void retirer_objet_sac(Equipement objet)
	{
		if(!est_dans_sac(objet))
			System.out.println("Impossible, l'objet n'est pas dans le sac");
		else
		{
			for(int i=0;i<sac.size();i++)
			{
				if(sac.get(i).getNom().equals(objet.getNom()))
				{
					if(sac.get(i).getQuantite()==1)
						sac.remove(i);
					else
						sac.get(i).setQuantite(sac.get(i).getQuantite()-1);
				}
			}
		}
	}
	public void ajouter_objet_sac(Equipement objet)
	{
		if(est_dans_sac(objet))
		{
			for(int i=0;i<sac.size();i++)
			{
				if(sac.get(i).getNom()==objet.getNom())
				{
					sac.get(i).setQuantite(sac.get(i).getQuantite()+1);
					break;
				}
				
			}
		}
		else
		{
			sac.add(objet);
		}
		
	}
	
	
	public boolean est_dans_sac(Equipement objet)
	{
		System.out.println(objet.getNom());
		for(int i=0;i<sac.size();i++)
		{
			if(sac.get(i).getNom().equals(objet.getNom()) && sac.get(i).getQuantite()>=objet.getQuantite())
			{
				System.out.println(sac.get(i).getNom());
				return true;
			}

		}
		return false;
	}

	public int choix_potion()
	{
		System.out.println("De quel Potion souhaitez vous vous équiper pour commencer votre aventure ?\n" +
				"Une potion d'Adresse, qui rétabliera vos points d'HABILETE ? Choisissez 1\n" +
				"Une potion de Vigueur, qui rétabliera vos points d'ENDURANCE ? Choisissez 2\n" +
				"Une potion de Bonne Fortune, qui rétabliera vos points de CHANCE ? Choisissez 3\n");
		System.out.print("Votre choix : ");
		Scanner choix= new Scanner(System.in);
	    String p=choix.next();
	    while(Integer.valueOf(p)>3 ||  Integer.valueOf(p)<0)
	    {
	    	System.out.println("Erreur, nombre non valide, réessayez");
	    	p=choix.next();
	    }
	    return Integer.valueOf(p);
	}

	@Override
	public String toString()
	{
		return "Nom : " + getNom() + "\nChance : " + chance_cur + "\nHabileté : " + getHabilete_cur()
				+ "\nEndurance : " + getEndurance_cur() + "\n";
	}



}
