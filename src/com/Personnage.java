package com;

import java.util.Random;
import java.util.Scanner;

public class Personnage {

	private String Nom;
	private boolean isAlive;
	private int habilete_max;
	private int habilete_cur;
	private int endurance_max;
	private int endurance_cur;
	private int force;


	public Personnage(String Nom, int habilete, int endurance) {
		this.Nom=Nom;
		habilete_max=habilete;
		endurance_max=endurance;
		endurance_cur=endurance;
		habilete_cur=habilete;
		isAlive=true;
	}


	public boolean isAlive() {
		return isAlive;
	}
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	public int getEndurance_max() {
		return endurance_max;
	}
	public void setEndurance_max(int endurance) {
		this.endurance_max = endurance;
	}
	public int getHabilete_max() {
		return habilete_max;
	}
	public void setHabilete_max(int habilete) {
		this.habilete_max = habilete;
	}
	public String getNom() {
		return Nom;
	}
	public int getHabilete_cur() {
		return habilete_cur;
	}
	public void setHabilete_cur(int habilete_cur) {
		this.habilete_cur = habilete_cur;
	}
	public int getEndurance_cur() {
		return endurance_cur;
	}
	public void setEndurance_cur(int endurance_cur) {
		this.endurance_cur = endurance_cur;
	}
	public int getForce() {
		return force;
	}
	public void setForce(int force) {
		this.force = force;
	}


	public static String demanderNom(){
		System.out.print("\nQuel nom voulez vous donner à votre personnage : ");
		Scanner scanner = new Scanner(System.in);
		String Nom=scanner.next();
		return Nom;
	}

	public static int jet_de(){
		
		Random randGen = new Random();
	    int max = 6;
	    int randNum = randGen.nextInt(max);
	    randNum += 1;
	    return randNum;
	}




}
