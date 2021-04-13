package corewar.ClientSide;

import java.io.File;

import corewar.Read;
import corewar.Utils;
import corewar.ObjectModel.Warrior;
import corewar.ObjectModel.Warriors;
import corewar.ObjectModel.elementsCore.Core;
import corewar.ServerSide.Manche;

public class Local {

    public static void PartieLocale(){
        System.out.println("cool");
        int nombreJoueurs;
        int choix;
        String source;
        File f;
        Character id = null;
        String color = null;

        // Choix du nombre de joueurs
        do{
            Utils.clear();
            System.out.println("LANCEMENT D'UNE PARTIE SOLO!\n\nEntrez le nombre de Warriors participant au match (entre 2 et 4) :");
            nombreJoueurs = Read.i();
        }while(nombreJoueurs < 2 && nombreJoueurs > 4);
        
        // Répertoire des warriors
        do{
            System.out.println("Rentrez le chemin absolu vers le REPERTOIRE contenant vos warriors, sans / à la fin. EXEMPLE : C:/Users/Yuumi/Desktop/Warriors\nRentrez 0 si ils sont rangés dans un dossier nommé Warriors dans le même répertoire que ce .jar");
            source = Read.S();
            if(source.equals("-1")) source = "corewar/src/main/java/corewar/Warriors/";
            if(source.equals("0")) source = "Warriors";
            f = new File(source);
            if(f.isDirectory())
                System.out.println("Le répertoire a été reconnu!");
            else{
                Utils.clear();
                System.out.println("Le répertoire n'a pas été trouvé. Veuillez reessayer.");
            }
        }while(!f.isDirectory());
        String[] pathnames = f.list();

        // On demande a chaque joueur de paramétrer son Warrior
        Warriors warriors = new Warriors();
        for(int i = 1 ; i <= nombreJoueurs ; i++){
            Utils.clear();
            System.out.println("----- CREATION DU WARRIOR #"+i+"\n");
            do{
                int index = 1;
                for (String pathname : pathnames) {
                    System.out.println(index+" || "+pathname);
                    index++;
                }
                System.out.println("Rentrez le numéro correspondant au fichier de votre choix");
                choix = Read.i();
            }while(choix <0 || choix > f.length());

            Warrior war = Warrior.makeWarrior(source+pathnames[--choix]);

            if(war == null){
                System.out.println("Erreur dans l'interprétation du Warrior. Retour au menu.");
                return;
            }

            switch(i){
                case 1 :{
                    id = '1';
                    color = "green";
                    break;
                }
                case 2 :{
                    id = '2';
                    color = "blue";
                    break;
                }
                case 3 :{
                    id = '3';
                    color = "yellow";
                    break;
                }
                case 4 :{
                    id = '4';
                    color = "red";
                    break;
                }
            }
            war.setId(id);
            war.changeId(id);
            war.setCouleur(color);
            war.setReady(true);
            warriors.add(war);
        }

        Integer coreSize = null, frameRate = null, tours = null;
        
        do {
            System.out.println("Veuillez rentrer la taille du core (min 500, maximum 10000, multiple de 100):");
            coreSize = Read.i();
        } while(coreSize == null || coreSize < 500 || coreSize > 10000 || coreSize%100 != 0);
        do {
            System.out.println("Veuillez rentrer la vitesse de la partie entre 1 et 500, c'est à dire le nombre de tour à passer toutes les secondes (Conseillé 20 -> Une partie de 2000 tours durera alors 100 secondes) :");
            frameRate = Read.i();
        } while(frameRate == null || frameRate < 1 || frameRate > 500);
        do {
            System.out.println("Veuillez rentrer le nombre de tours max (conseillé ~2000), entre 5 et 50000:");
            tours = Read.i();
        } while(tours == null || tours < 5 || tours > 50000);
        frameRate = frameRate/2;

        Core c = new Core(coreSize);
        c.setWarriors(warriors);
        c.load();

        Manche m = new Manche(warriors,c);

        m.traitementPartie(tours,frameRate,null);

        for(Warrior current : warriors.getWarriors()){
            int as = m.aliveScore(current);
            int pss = m.possessionScore(current);
            int pts = m.pointeursScore(current);
            int score = as+pss+pts;
            System.out.println("Score du warrior "+current.getNom()+" :\n"+"En vie = "+as+" / 500  | Possession de la memoire = "+pss+"  / 400 | Nombre de pointeurs = "+pts+"  / 100\nTOTAL DES POINTS : "+score+"  / 1000\n");
        }

            


    }
    
}
