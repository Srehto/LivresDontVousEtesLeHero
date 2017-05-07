package com;

public class Equipement {

	private String Nom;
	private int endurance;
	private int habilete;
	private int chance;
	private int quantite;
	private boolean consommable;


	public Equipement(String Nom, int habilete, int endurance, int chance, int quantite, boolean consommable) {
		this.Nom=Nom;
		this.endurance=endurance;
		this.habilete=habilete;
		this.chance=chance;
		this.quantite=quantite;
	}
	public String getNom() {
		return Nom;
	}
	public void setNom(String nom) {
		Nom = nom;
	}
	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	@Override
	public String toString()
	{
		return quantite + " " + Nom;
	}
	public int getChance() {
		return chance;
	}
	public void setChance(int chance) {
		this.chance = chance;
	}
	public int getHabilete() {
		return habilete;
	}
	public void setHabilete(int habilete) {
		this.habilete = habilete;
	}
	public int getEndurance() {
		return endurance;
	}
	public void setEndurance(int endurance) {
		this.endurance = endurance;
	}
	public boolean isConsommable() {
		return consommable;
	}
	public void setConsommable(boolean consommable) {
		this.consommable = consommable;
	}

}
