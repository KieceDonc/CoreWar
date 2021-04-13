package corewar.ClientSide;

import java.io.File;

import corewar.Read;
import corewar.Utils;
import corewar.ObjectModel.Warrior;
import corewar.ObjectModel.Warriors;

public class Local {

    public static void PartieLocale(){
        System.out.println("cool");
        int choix;
        String source;
        File f;
        Character id = null;
        String color = null;

        // Choix du nombre de joueurs
        do{
            Utils.clear();
            System.out.println("LANCEMENT D'UNE PARTIE SOLO!\n\nEntrez le nombre de Warriors participant au match (entre 2 et 4) :");
            choix = Read.i();
        }while(choix < 2 && choix > 4);
        
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
        for(int i = 1 ; i <= choix ; i++){
            choix = -1;
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
                    color = "red";
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
                    color = "green";
                    break;
                }
            }
            war.setId(id);
            war.setCouleur(color);
            
            
        }


        


    }
    
}
