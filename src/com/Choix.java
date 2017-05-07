package com;

public class Choix {
	
	private int dest;
	private String Phrase;
	private Equipement equip_need;
	
	public Choix(){
		Phrase="";
		equip_need=null;
	}
	
	public int getDest() {
		return dest;
	}
	public void setDest(int dest) {
		this.dest = dest;
	}
	public String getPhrase() {
		return Phrase;
	}
	public void setPhrase(String phrase) {
		Phrase = phrase;
	}
	@Override
	public String toString()
	{
		if(equip_need==null)
			return Phrase + " => " + dest;
		else
			return Phrase + " => " + dest + "\n" + " mais il faut " + equip_need.toString();
	}
	public Equipement getEquip_need() {
		return equip_need;
	}
	public void setEquip_need(Equipement equip_need) {
		this.equip_need = equip_need;
	}
	
}

