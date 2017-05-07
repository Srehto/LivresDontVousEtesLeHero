package com;

import java.util.ArrayList;
import java.util.Scanner;

public class Jeu {

	private Joueur PJ;
	private Paragraphe[] histoire;
	
	public Jeu(Joueur PJ, Paragraphe[] histoire) {
		this.PJ=PJ;
		this.histoire=histoire;
	}
	
	public void Jouer() throws InterruptedException
	{
		Scanner choix= new Scanner(System.in);
		String str;
		int dep;
		int i = 1;
		int k = 0;
		boolean continuer= true;
		boolean trouve = false;
		/* Intoduction */
		for (int j = 0 ; j < histoire[0].getTexte().length() ;j++)
		{
			 System.out.print(histoire[0].getTexte().charAt(j));
			 Thread.sleep(30);
		}
		System.out.print("\n");
		   
		System.out.println("\n\nTaper sur Entré pour continuer\n");
		str=choix.nextLine();
		
		while(continuer)
		{
			trouve=false;
			/*Affichage du texte*/
			for (int j = 0 ; j < histoire[i].getTexte().length() ;j++)
			{
				 System.out.print(histoire[i].getTexte().charAt(j));
				 Thread.sleep(25);
			}
			/* Perte du sac ? */
			if(histoire[i].isPerte_sac())
			{
				for(int e=0;e<PJ.getSac().size();e++)
					PJ.getSac().remove(e);
				PJ.setHabilete_cur(PJ.getHabilete_max());
				PJ.setEndurance_cur(PJ.getEndurance_max());
				PJ.setChance_cur(PJ.getChance_max());
			}
			/*Caractéristiques affécté*/
			PJ.setHabilete_cur(PJ.getHabilete_cur()+histoire[i].getHabi_Affect());
			if(PJ.getHabilete_cur()>PJ.getHabilete_max())
				PJ.setHabilete_cur(PJ.getHabilete_max());
			PJ.setEndurance_cur(PJ.getEndurance_cur()+histoire[i].getEndu_Affect());
			if(PJ.getEndurance_cur()>PJ.getEndurance_max())
				PJ.setEndurance_cur(PJ.getEndurance_max());
			PJ.setChance_cur(PJ.getHabilete_cur()+histoire[i].getChance_Affect());
			if(PJ.getChance_cur()>PJ.getChance_max())
				PJ.setChance_cur(PJ.getChance_max());
			/* Mort ? */
			if(histoire[i].isMort() || PJ.getEndurance_cur()<=0)
			{
				trouve=true;
				continuer=false;
			}
			/*Equipement gagné */
			if(histoire[i].isChg_objet())
			{
				for(int e=0;e<histoire[i].getEquipement_gagne().size();e++)
				{
					PJ.ajouter_objet_sac(histoire[i].getEquipement_gagne().get(e));
				}
				/*Equipement perdu*/
				for(int e=0;e<histoire[i].getEquipement_perdu().size();e++)
				{
					PJ.retirer_objet_sac(histoire[i].getEquipement_perdu().get(e));
				}
			}
			/*Changement du nombre de pièces d'or */
			PJ.setNb_pieces_or(PJ.getNb_pieces_or()+histoire[i].getChg_or());
			/* On ajoute a l'inventaire le nombre de pièces d'or*/
			PJ.getSac().get(0).setQuantite(PJ.getNb_pieces_or());
			/*Combat ?*/
			if(histoire[i].getPNJ().size()!=0)
			{
				switch(combat(histoire[i].getPNJ(), PJ, histoire[i])) 
				{
				case 1:/*Victoire*/
						k=histoire[i].getChoix().get(0).getDest()-1;
						trouve=true;
						break;
				
				case -1:/*Defaite*/
						if(histoire[i].getChoix().get(1).getPhrase().equals("Defaite")) /* On ne meurt pas mais on se dirrige vers un endroit*/
						{
							k=histoire[i].getChoix().get(1).getDest();
						}
						else
							continuer=false; /* On meurt */
						break;
				case 0:
					break;
				default:
					k=histoire[i].getChoix().get(1).getDest();
					break;
				
				}
				trouve=true;
				i=k+1;
				
			}
			/* Mort ?*/
			if(histoire[i].isMort())
			{
				System.out.println("Vous êtes mort ! Vous avez échoué dans votre quête ! Vous aurez peut être plus de chance la prochaine foi...");
				k=0;
				trouve=true;
			}
			
			/* Que veut faire l'utilisateur ?*/
			while(!trouve)
			{
				System.out.println("\nQue voulez vous faire ?\n1: Utiliser un objet\n2: Choisir une destination\n3: Afficher votre fiche de personnage\n4: Afficher votre inventaire\n5: Quitter\n");
				str=choix.nextLine();
				if(str.equals("1"))
				{
					/*Utiliser un objet*/
					System.out.println("Quel objet voulez vous utiliser ?");
					System.out.println("0: Ne rien prendre");
					for(int j=0;j<PJ.getSac().size();j++)
					{
						if(PJ.getSac().get(j).isConsommable())
						{
							System.out.println(j + ":" + " " + PJ.getSac().get(j).toString());
						}
							
					}
					str=choix.nextLine();
					while(Integer.valueOf(str)>PJ.getSac().size() || Integer.valueOf(str)<0)
					{
						System.out.println("Mauvaise saisie, numéro invalide !");
						str=choix.nextLine();
					}
					if(Integer.valueOf(str)==0)
					{
						System.out.println("Vous ne consommer rien !\n");
					}
					else if(PJ.consommer_objet(PJ.getSac().get(Integer.valueOf(str))))
					{
						System.out.println("Vous avez consommer l'objet !\n");
					}
					else
						System.out.println("Erreur, vous n'avez pas choisie d'objet consommable parmis la liste\n");
					
				}
				else if(str.equals("2"))
				{
					/* Choisir une destination  */
					/* Jet de dés ? */
					if(histoire[i].isJete_de())
					{
						int res=0;
						System.out.println("Vous jetez " + histoire[i].getEvenement().getNb_de() + " dé(s)," +
								" si le résulat est inférieur a " + histoire[i].getEvenement().getCarac() + " vous irez" +
								" à la case " + (histoire[i].getChoix().get(0).getDest()-1) + ". Sinon, " + 
								"vous irez à la case " + (histoire[i].getChoix().get(1).getDest()-1) + 
								"\nAppuyer sur entrer pour continuer...");
						str=choix.nextLine();
						for(int l=0;l<histoire[i].getEvenement().getNb_de();l++)
						{
							res=res+Joueur.jet_de();
						}
						switch(histoire[i].getEvenement().getCarac())
						{
							case "HABILETE" :
								if(res<=PJ.getHabilete_cur())
								{
									k=histoire[i].getChoix().get(0).getDest()-1;
									System.out.println("Succès !");
								}
								else
								{
									k=histoire[i].getChoix().get(1).getDest()-1;
									System.out.println("Echec !");
								}
								break;
							case "ENDURANCE" :
								if(res<=PJ.getEndurance_cur())
								{
									k=histoire[i].getChoix().get(0).getDest()-1;
									System.out.println("Succès !");
								}
								else
								{
									k=histoire[i].getChoix().get(1).getDest()-1;
									System.out.println("Echec !");
								}
								break;
							case "CHANCE" :
								if(res<=PJ.getChance_cur())
								{
									k=histoire[i].getChoix().get(0).getDest()-1;
									System.out.println("Succès !");
								}
								else
								{
									k=histoire[i].getChoix().get(1).getDest()-1;
									System.out.println("Echec !");
								}
								break;
							default :
								if(res<=Integer.valueOf(histoire[i].getEvenement().getCarac()))
								{
									k=histoire[i].getChoix().get(0).getDest()-1;
									System.out.println("Succès !");
								}
								else
								{
									k=histoire[i].getChoix().get(1).getDest()-1;
									System.out.println("Echec !");
								}
								break;
						}
						trouve=true;
					}
					else
					{
						for(int j=0;j<histoire[i].getChoix().size();j++)
						{
							System.out.println(histoire[i].getChoix().get(j).getPhrase() + " => Allez au numéro " + histoire[i].getChoix().get(j).getDest());
						}
					}
					while(!trouve && histoire[i].getChoix().size()>0)
					{
						dep=0;
						/* Tentez votre chance ? */
						if(histoire[i].isTenter_chance())
						{
							dep=2;
							System.out.println("Tenter votre chance ? 1 = Oui, Autre = Non");
							if(choix.nextInt()==1)
							{
								if(PJ.tentez_votre_chance()) /* Chanceux*/
									k=histoire[i].getChoix().get(0).getDest()-1;
								else /* Malchanceux */
									k=histoire[i].getChoix().get(1).getDest()-1;
								trouve=true;
								break;
							}
						}
						k=ChoixDest()-1;
						for(int j=dep;j<histoire[i].getChoix().size();j++)
						{
							if(k+1==histoire[i].getChoix().get(j).getDest())
							{
								if(histoire[i].getChoix().get(j).getEquip_need() == null) /* Aucun objet requis */
								{
									trouve = true;
								}
								else /* Un objet est requis */
								{
									if(PJ.est_dans_sac(histoire[i].getChoix().get(j).getEquip_need()))
									{
										trouve=true;
									}
								}
							}
						}
						if(!trouve)
						{
							System.out.println("Le choix rentré ne correspond pas au choix possibles ! Choisissez à nouveau !\nPeut être n'avez vous pas l'objet requis pour ce choix\n");
						}
					}
					if(k==0) /* On a finit la partie */
					{
						System.out.println("Le jeu se finit ici pour vous !");
						continuer=false;
					}
					trouve = true;
					i=k+1;
				}
				else if(str.equals("3"))
				{
					System.out.println("Etat du personnage :\n" + PJ.toString());
				}
				else if(str.equals("4"))
				{
					System.out.println("Votre sac :" + PJ.getSac().toString());
				}
				else if(str.equals("5"))
				{
					/*Quitter*/
					continuer=false;
					trouve=true;
				}
				else
				{
					System.out.println("Mauvaise saisie, recommencer");
				}
					
			}
		}
	}
	

	public static int combat(ArrayList<NonJoueur> PNJ, Joueur PJ, Paragraphe histoire)
	{
		Scanner scan = new Scanner(System.in);
		int i;
		int p;
		int nb_assault=1;
		System.out.println("\nIl y a " + histoire.getNb_pnj() + " adversaires\n");
		Scanner wait = new Scanner(System.in);
		System.out.println("Appuyer sur Entré pour commencer le combat");
		String in=wait.nextLine();
		/*Nvl Assault*/
		while(PJ.getEndurance_cur()>0 && histoire.getNb_pnj()>0)
		{
			/*Nomrbre d'assaut suffisant ? */
			if(PNJ.get(0).getNb_assault()==nb_assault)
			{
				System.out.println("Quel que chose arrive..");
				break;
				
			}
			/* Etat des différents personnages */
			System.out.println("\nVotre Endurance : " + PJ.getEndurance_cur() + "\nVotre Chance : " + PJ.getChance_cur() + "\n");
			for(int j=0;j<histoire.getNb_pnj();j++)
			{
				System.out.println("Endurance de  " + PNJ.get(j).getNom() + " : " + PNJ.get(j).getEndurance_cur());
			}
			System.out.println("\nAppuyer sur Entré pour lancer un nouvel assault");
			in=wait.nextLine();
			/* Choix de l'adversaire a attaquer*/
			if(histoire.getNb_pnj()>1) /*Il y a plusieurs adversaires */
			{	
				System.out.println("Quel adversaire attaquer ? ");
				for(int j=0;j<histoire.getNb_pnj();j++)
				{
					System.out.println(j + " : " + PNJ.get(j).getNom() + " Endu " + PNJ.get(j).getEndurance_cur() + " Habi " + PNJ.get(j).getHabilete_cur());
				}
			    p=scan.nextInt();
			    while(p>histoire.getNb_pnj() ||  p<0)
			    {
			    	System.out.println("Erreur, nombre non valide, réessayez");
			    	p=scan.nextInt();
			    }
			}
			else /* Il n'y a qu'un adversaire */
				p=0;
			
			/*On attribut la force du joueur */
			PJ.setForce(Personnage.jet_de() + Personnage.jet_de() + PJ.getHabilete_cur());
			/*On attribut les forces des PNJ */
			for(int j=0;j<histoire.getNb_pnj();j++)
			{
				PNJ.get(j).setForce(Personnage.jet_de() + Personnage.jet_de() + PNJ.get(j).getHabilete_max());
				System.out.println("Force de " + PNJ.get(j).getNom() + " : " + PNJ.get(j).getForce());
				System.out.println("Votre force : " + PJ.getForce() + "\n");
				/*Combat ! */
				if(PJ.getForce()>PNJ.get(j).getForce()) /*Le PJ gagne*/
				{
					if(j!=p)
					{
						System.out.println("Vous avez esquivé le coup de " + PNJ.get(j).getNom() + " !");
					}
					else
					{
						PNJ.get(j).setEndurance_cur(PNJ.get(j).getEndurance_cur()-2);
						if(PNJ.get(j).getEndurance_cur()>0)
						{
							System.out.println("Vous avez touché " + PNJ.get(j).getNom() + " tentez votre chance ? 1=Oui, Autre=Non");
							i=scan.nextInt();
							if(i==1)
							{
								if(PJ.tentez_votre_chance())
								{
									System.out.println("vous êtes Chanceux, vous avez infligé une blessure grave, " + PNJ.get(j).getNom() + " perd 1 points d'endurance !");
									PNJ.get(j).setEndurance_cur(PNJ.get(j).getEndurance_cur()-2);
								}
								else
								{
									System.out.println("vous êtes Malchanceux, la blessure n'était qu'une simple écorchure, " + PNJ.get(j).getNom() + " gagne 1 points d'endurance !");
									PNJ.get(j).setEndurance_cur(PNJ.get(j).getEndurance_cur()+ 1);
								}
							}
						}
						if(PNJ.get(j).getEndurance_cur()<=0)
						{
							System.out.println("Vous avez tué " + PNJ.get(j).getNom() + "\n");
							PNJ.get(j).setAlive(false);
							histoire.setNb_pnj(histoire.getNb_pnj()-1);
						}
					}
					
				}
				else if(PJ.getForce()<PNJ.get(j).getForce())/* Le PNJ gagne*/
				{
					PJ.setEndurance_cur(PJ.getEndurance_cur()-2);
					if(PJ.getEndurance_cur()>0)
					{
						System.out.println("Vous prenez un coup, tentez votre chance ? 1=Oui, Autre=Non");
						i=scan.nextInt();
						if(i==1)
						{
							if(PJ.tentez_votre_chance())
							{
								System.out.println("vous êtes Chanceux, vous avez réussi à atténuer le coup, " + PNJ.get(j).getNom() + " vous gagnez 1 point d'endurance +1 \n");
								PJ.setEndurance_cur(PJ.getEndurance_cur()+1);
							}
							else
							{
								System.out.println("vous êtes Malchanceux, le coup que vous avez pris était plus grave, " + PNJ.get(j).getNom() + " vous perdez 1 point d'endurance \n");
								PJ.setEndurance_cur(PJ.getEndurance_cur()-1);
							}
						}
					}

				}
				else /* Egalité*/
					System.out.println("Vous avez évité le coups de " + PNJ.get(j).getNom() + "!\n");
				if(nb_assault==PNJ.get(j).getNb_assault())
					break;
			
			}
			nb_assault++;
		}
		
		/* Fin du combat, qui a gagné ? */
		if(PNJ.get(0).getNb_assault()==nb_assault) /* Quelque chose c'est produit */
		{
			if(histoire.getChoix().get(1).getPhrase().equals("Quelque chose arrive..")) /* Une fois le nombre de coup atteints quelque chose se produit */
				return histoire.getChoix().get(1).getDest();
			else																		/* Ou alors c'est simplement une victoire */
				return 1;
		}
		else if(PJ.getEndurance_cur()<=0) /* On a perdu */
		{
			System.out.println("Vous avez perdu le combat, et vous êtes donc mort !");
			PJ.setAlive(false);
			return 1;
		}
		else if(histoire.getNb_pnj()==0) /* On a gagné */
		{
			System.out.println("Vous avez gagné le combat !");
			return -1;
		}
		else
			return 0;
	}


	public Paragraphe[] getHistoire() {
		return histoire;
	}
	public void setHistoire(Paragraphe[] histoire) {
		this.histoire = histoire;
	}
	public Joueur getPJ() {
		return PJ;
	}
	public void setPJ(Joueur pJ) {
		PJ = pJ;
	}
	public void AfficherParagraphe(Paragraphe paragraphe)
	{
		System.out.println(paragraphe.getTexte());
		System.out.println(paragraphe.getChoix().toString());
	}
	public int ChoixDest()
	{
		System.out.println("Quel destination choisissez vous ? : ");
		Scanner choix= new Scanner(System.in);
	    return choix.nextInt();
	}

}
