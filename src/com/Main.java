package com;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {

		public static void main(String[] args) throws FileNotFoundException, InterruptedException
		{
			/*Affichage du titre*/
			afficher_titre();
			/* Création de l'espace de stockage pour l'histoire */
			Paragraphe[] histoire;
			histoire=new Paragraphe[401];
			for(int i=0;i<401;i++)
			{
				histoire[i] = new Paragraphe();
			}
			histoire=remplir_histoire(histoire);
			
			String p;
			Scanner choix= new Scanner(System.in);
			boolean jouer=true;
			/*Menu*/
			while(jouer)
			{
				System.out.println("Que voulez vous faire ?\n1: Jouer\n2: Lire les règles\n3: Quitter");
				p=choix.next();
			    while(Integer.valueOf(p)>3 ||  Integer.valueOf(p)<1)
			    {
			    	System.out.println("Erreur, nombre non valide, réessayez");
			    	p=choix.next();
			    }
			    switch(Integer.valueOf(p))
			    {
			    case 1:	/* On veut jouer */
			    	/* Création et initialisation du personnage joueur*/
					Joueur PJ = new Joueur(histoire[0],Personnage.jet_de()+8,Personnage.jet_de()+Personnage.jet_de()+12);
					System.out.println("\nTenez vous prêt, la partie va commencer !");
					Thread.sleep(2000);
					/* Jeu */
					Jeu jeu=new Jeu(PJ, histoire);
					jeu.Jouer();
					break;
			    case 2: /* On veut afficher les règles */
			    	choisir_regle();
			    	break;
			    case 3: /* On veut quitter */
			    	jouer= false;
			    	break;
			    }
				
			}
			/*Fin du jeu*/
			choix.close();
			System.out.println("Merci d'avoir joué au Talisman de la Mort, à bientot !");
			
		}
		
		public static void afficher_titre() throws FileNotFoundException
		{
			Scanner titre = new Scanner(new FileReader("titre.txt"));
			String p;
			p=titre.nextLine();
			while(titre.hasNext())
			{
				System.out.println(p);
				p=titre.nextLine();
			}
			titre.close();
			System.out.println("\n\nBienvenue dans cette grande aventure. Tenez vous pret a affronter les pires créatures du monde d'Orbus, et à sauver ce monde de la"
					+ " désolation. Tenez vous prêt.\nPour une meilleurs expérience de jeu, mettez votre console en plein écran.\n"
					+ "Si c'est la première foi que vous jouez, lire les règles vous permettra de mieux débuter\n");
		}

		public static void choisir_regle() throws NumberFormatException, FileNotFoundException 
		{
			boolean continuer=true;
			Scanner ind = new Scanner(System.in);
			while(continuer)
			{
				System.out.println("\nSur quel sujet voulez vous vous renseigner ?\n1: Vos Caractéristiques\n2: Les Batailles\n3: Les Combats avec plus d'une créature\n"
						+ "4: La chance\n5: Comment rétablir votre Habileté, votre Endurance et votre Chance\n6: Équipement et Potions\n7: Retour au menu\n");
			    String p=ind.next();
			    while(Integer.valueOf(p)>7 ||  Integer.valueOf(p)<1)
			    {
			    	System.out.println("Erreur, nombre non valide, réessayez");
			    	p=ind.next();
			    }
			    if(Integer.valueOf(p)!=7)	
			    {
			    	afficher_regle(p);
			    }

			    else
			    	continuer=false;
				
			}
			
		}
		
		public static void afficher_regle(String p) throws FileNotFoundException
		{
			Scanner fichier = new Scanner(new FileReader("regles.txt"));
			Scanner in = new Scanner(System.in);
			String str;
			str=fichier.nextLine();
			while(!(str.equals(p)))
			{
				str=fichier.nextLine();
			}
			str=fichier.nextLine();
			while(!str.equals("@"))
			{
				System.out.println(str);
				str=fichier.nextLine();
			}
			fichier.close();
			
			System.out.println("\nAppuyer sur" + " Entré pour continuer !");
			String wait=in.nextLine();

		}


		public static Paragraphe[] remplir_histoire(Paragraphe[] histoire) throws FileNotFoundException

		{
			/* générer l'histoire*/

			Choix[] choix;
			choix =new Choix[700];
			int nb_monstre=0;
			int nb_assault;
			int nb_choix = 0;
			String nom_pnj;
			int habi_pnj, endu_pnj;

			for(int i=0;i<700;i++)
			{
				choix[i] = new Choix();
			}

			Scanner scanner = new Scanner(new FileReader("texte.txt"));
			String str;
			String phrase = "";
			int i=0;
			/* Tant qu'on est pas a la fin du fichier txt */
			while(scanner.hasNextLine())
			{
				 str = scanner.nextLine();
				 str = scanner.nextLine();
				 /*Texte*/
				 while(!str.equals("@"))
				 {
					phrase=phrase+" \n"+str;
					str=scanner.nextLine();
				 }
				 histoire[i].setTexte(phrase);
				 str=scanner.nextLine();
				 /*Monstres*/
				 nb_monstre=0;
				 while(str.equals("%"))
				 {
					 str=scanner.nextLine(); /* Nom du PNJ */
					 nom_pnj=str;
					 str=scanner.nextLine(); /* Habilite du PNJ */
					 habi_pnj=Integer.valueOf(str);
					 str=scanner.nextLine();
					 endu_pnj=Integer.valueOf(str); /* Endurance du PNJ */
					 str=scanner.nextLine();
					 nb_assault=Integer.valueOf(str); /* NB d'assault avant un evenement */
					 str=scanner.nextLine();
					 /*Direction si victoire */
					 choix[nb_choix].setDest(Integer.valueOf(str));
					 choix[nb_choix].setPhrase("Victoire");
					 histoire[i].getChoix().add(choix[nb_choix]);
					 nb_choix++;
					 str=scanner.nextLine();
					 if(str.equals("=>")) /*Direction si défaite */
					 {
						 str=scanner.nextLine();
						 choix[nb_choix].setDest(Integer.valueOf(str));
						 choix[nb_choix].setPhrase("Defaite");
						 histoire[i].getChoix().add(choix[nb_choix]);
						 nb_choix++;
						 str=scanner.nextLine();
					 }
					 if(str.equals("[]")) /* Si l'evenement différent d'une victoire */
					 {
						 str=scanner.nextLine();
						 choix[nb_choix].setDest(Integer.valueOf(str));
						 choix[nb_choix].setPhrase("Quelque chose arrive..");
						 histoire[i].getChoix().add(choix[nb_choix]);
						 nb_choix++;
						 str=scanner.nextLine();
						 
					 }
					 histoire[i].getPNJ().add(new NonJoueur(nom_pnj,habi_pnj,endu_pnj,nb_assault));
					 /*Lancer de dés pendant combat */
					 if(str.equals("#"))
					 {
						 str=scanner.nextLine();
						 histoire[i].getPNJ().get(nb_monstre).setLance_des(Integer.valueOf(str));/* Type de lancé */
						 //monstre[nb_monstre].setLance_des(Integer.valueOf(str)); 
						 str=scanner.nextLine();
						 histoire[i].getPNJ().get(nb_monstre).setRes_de_necessaire(Integer.valueOf(str));/* Numero Gagnant */
						 // monstre[nb_monstre].setRes_de_necessaire(Integer.valueOf(str)); 
						 str=scanner.nextLine();
						 choix[nb_choix].setDest(Integer.valueOf(str)); /* Destination si victoire */
						 choix[nb_choix].setPhrase("Vous avez gagné votre lancé de dé");
						 histoire[i].getChoix().add(choix[nb_choix]);
						 nb_choix++;
						 str=scanner.nextLine();

					 }
					 histoire[i].setNb_pnj(histoire[i].getNb_pnj()+1);
					 nb_monstre++;

				 }
				 /* Mort ?*/
				 if(str.equals("§"))
				 {
					 histoire[i].setMort(true);
					 str=scanner.nextLine();
				 }
				 else
					 histoire[i].setMort(false);

				 /*Evenement jet de dés*/
				 if(str.equals("{}"))
				 {
					 histoire[i].setJete_de(true);
					 Evenement evenement = new Evenement();
					 str=scanner.nextLine(); 	/*Nb dés jeté*/
					 evenement.setNb_de(Integer.valueOf(str));
					 str=scanner.nextLine();  	/* Carac testée*/
					 evenement.setCarac(str);
					 str=scanner.nextLine();	/*Dest si Oui*/
					 evenement.setSuccess(Integer.valueOf(str));
					 choix[nb_choix].setDest(Integer.valueOf(str));
					 choix[nb_choix].setPhrase("Direction si succes au lancé de dé");
					 histoire[i].getChoix().add(choix[nb_choix]);
					 nb_choix++;
					 str=scanner.nextLine(); 	/*Dest si Non*/
					 evenement.setFail(Integer.valueOf(str));
					 choix[nb_choix].setDest(Integer.valueOf(str));
					 choix[nb_choix].setPhrase("Direction si echec au lancé de dé");
					 histoire[i].getChoix().add(choix[nb_choix]);
					 nb_choix++;
					 str=scanner.nextLine();	/*Continuer*/
					 histoire[i].setEvenement(evenement);
				 }
				 else
					 histoire[i].setJete_de(false);
				 /*Tentez votre chance ? */
				 if(str.equals("*"))
				 {
					 histoire[i].setTenter_chance(true);
					 str=scanner.nextLine();
					 choix[nb_choix].setDest(Integer.valueOf(str)); /* Destination si tent� gagn� */
					 choix[nb_choix].setPhrase("Vous avez gagné votre tenté de chance");
					 histoire[i].getChoix().add(choix[nb_choix]);
					 nb_choix++;
					 str=scanner.nextLine();
					 choix[nb_choix].setDest(Integer.valueOf(str)); /* Dest si tent� perdu */
					 choix[nb_choix].setPhrase("Vous avez perdu votre tent� de chance");
					 histoire[i].getChoix().add(choix[nb_choix]);
					 nb_choix++;
					 str=scanner.nextLine();
				 }
				 else
					 histoire[i].setTenter_chance(false);

				 /*Affectation Caracteristiques */
				 if(str.equals("$"))
				 {
					str=scanner.nextLine();
					histoire[i].setHabi_Affect(Integer.valueOf(str));
					str=scanner.nextLine();
					histoire[i].setEndu_Affect(Integer.valueOf(str));
					str=scanner.nextLine();
					histoire[i].setChance_Affect(Integer.valueOf(str));
					str=scanner.nextLine();

				 }
				 /*Gain / perte equipement */
				 histoire[i].setChg_objet(false);
				 histoire[i].setPerte_sac(false);
				 while (str.equals("^^"))
				 {
					 str=scanner.nextLine(); /* Nom de l'objet */
					 Equipement objet = new Equipement(str,0,0,0,0,false);
					 objet.setNom(str);
					 str=scanner.nextLine(); /* Quantite */
					 objet.setQuantite(Integer.valueOf(str));
					 str=scanner.nextLine();
					 if(str.equals("+"))
					 {
						 histoire[i].getEquipement_gagne().add(objet);
						 str=scanner.nextLine();
						 if(str.equals("&&"))
						 {
							 str=scanner.nextLine();
							 objet.setHabilete(Integer.valueOf(str));
							 str=scanner.nextLine();
							 objet.setEndurance(Integer.valueOf(str));
							 str=scanner.nextLine();
							 objet.setChance(Integer.valueOf(str));
							 str=scanner.nextLine();
							 
						 }
						 else if(str.equals("µ"))
						 {
							 objet.setConsommable(true);
							 str=scanner.nextLine();
						 }
						 
					 }
					 else
					 {
						 histoire[i].getEquipement_perdu().add(objet);
						 str=scanner.nextLine();
					 }
					 histoire[i].setChg_objet(true);
				 }
				 /*Perte de sac ou pas ?*/
				 if(str.equals("!!"))
				 {
					 histoire[i].setPerte_sac(true);
					 str=scanner.nextLine();
				 }
				 /* Perte ou gain d'or*/
				 if(str.equals("€"))
				 {
					 str=scanner.nextLine();
					 histoire[i].setChg_or(Integer.valueOf(str));
					 str=scanner.nextLine();
				 }
				 /*Choix*/
				 while(!str.equals("@") && scanner.hasNextLine())
				 {
					 
					 if(str.equals("<="))
					 {
						 choix[nb_choix].setDest(-1);
						 choix[nb_choix].setPhrase("Faire demi tour");
						 histoire[i].ajouter_choix(choix[nb_choix]); 
						 nb_choix++;
						 str=scanner.nextLine();
						 break;
					 }
					 choix[nb_choix].setDest(Integer.valueOf(str)); /* Destination */
					 str=scanner.nextLine(); /* Phrase correspondante */
					 choix[nb_choix].setPhrase(str);
					 str=scanner.nextLine();
					 if(str.equals("£")) /* Un objet est requis */
					 {
						 str=scanner.nextLine(); /* Nom de l'objet */
						 Equipement equip = new Equipement(str,0,0,0,0,false);
						 choix[nb_choix].setEquip_need(equip);
						 str=scanner.nextLine(); /* Quantit� necessaire */
						 choix[nb_choix].getEquip_need().setQuantite(Integer.valueOf(str));
						 str=scanner.nextLine();
					 }
					 histoire[i].ajouter_choix(choix[nb_choix]); 
					 nb_choix++;
				 }
				 phrase="";
				 //System.out.println(histoire[i].toString());
				 i++;
			}
			scanner.close();
			return histoire;
	}

}
