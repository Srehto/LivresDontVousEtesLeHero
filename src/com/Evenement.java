package com;

public class Evenement extends Paragraphe {

	private int nb_de; /* Nombre de dé */
	private int success; /* Dest si succes*/
	private int fail; /* Dest si echec */
	private String Carac; /* Caract ou type testé */

	public Evenement() {
	}


	public int getFail() {
		return fail;
	}
	public void setFail(int fail) {
		this.fail = fail;
	}
	public int getSuccess() {
		return success;
	}
	public void setSuccess(int success) {
		this.success = success;
	}
	public int getNb_de() {
		return nb_de;
	}
	public void setNb_de(int nb_de) {
		this.nb_de = nb_de;
	}
	public String getCarac() {
		return Carac;
	}
	public void setCarac(String carac) {
		Carac = carac;
	}

	@Override
	public String toString()
	{
		return "Nombre de d�s jet�s : " + nb_de + "\nCaracteristique test� : " + Carac
				+ "\nDirection si succes : " + success
				+ "\nDirection si echec : " + fail +"\n\n";
	}
}
