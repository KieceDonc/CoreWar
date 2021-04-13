package corewar;

import corewar.ClientSide.Interpreteur;
import corewar.ObjectModel.Rankings;
import corewar.ObjectModel.Warrior;
import corewar.ObjectModel.Warriors;
import corewar.ObjectModel.elementsCore.Core;
import corewar.ServerSide.Manche;

public class Debug{

    public static void mainDebug(){

        int choice = 0;
        final int maxChoice = 6;
        do{
            System.out.println("------------ MENU SECRET! ECRAN DE DEBUG\n---- Ce menu contient divers tests de fonctionnalités implémentées ou non.");
            System.out.println("");
            System.out.println("1 - Cls");
            System.out.println("2 - Sort Score");
            System.out.println("3 - Animation");
            System.out.println("4 - Test Partie DWARF vs IMP");
            System.out.println("5 - Afficher Règles");
            System.out.println("6 - Sauvegarde des warriors");
            System.out.println("");
            System.out.print("Votre choix : ");
            choice = Read.i();
            System.out.println("");
            System.out.println("------------------------------------------------------------------------------------------");    
        }while(choice>maxChoice || choice<0);

        switch(choice){
            case 0 : { System.exit(0);}
            case 1 : { testCls(); break; }
            case 2 : { testScoreRank(); break;}
            case 3 : { testAnim(); break;}
            case 4 : { testPartie(); break;}
            case 5 : { Regles.printRegles(); break;}
            case 6 : { testSauvegarde(); break;}

        }
        mainDebug();
        
        
    }

    private static void testSauvegarde() {

        Rankings r1 = new Rankings();
        r1.addWarrior("DWARF", 5);
        pr(r1.toStringWarriors());

        boolean ok = r1.saveWarriorRankings();
        pr(ok);

        r1.getRankingWarriors().clear();
        Utils.animation(3, "Supression de la liste");
        Rankings r2 = new Rankings();
        pr(r2.toStringWarriors());
        r2.loadWarriorRankings();
        r2.addWarrior("IMP", 7);
        pr(r2.toStringWarriors());
    }

    private static void testPartie() {
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

        Warrior J1 = new Warrior();
        J1.setNom("DWARF");
        J1.setId('1');
        J1.setInstructions(Interpreteur.interpreter("corewar/src/main/java/corewar/Warriors/dwarf"),J1.getId());
        J1.setCouleur("blue");

        Warrior J2 = new Warrior();
        J2.setNom("IMP");
        J2.setId('2');
        J2.setInstructions(Interpreteur.interpreter("corewar/src/main/java/corewar/Warriors/imp"),J2.getId());
        J2.setCouleur("red");


        Warriors w = new Warriors();
        w.add(J1);
        w.add(J2);

        Core c = new Core(coreSize);
        c.setWarriors(w);
        c.load();

        Manche m = new Manche(w,c);

        m.traitementPartie(tours,frameRate,null);

        System.out.println("1 : En vie : "+m.aliveScore(J1)+" | Possession : "+m.possessionScore(J1)+" | Pointeurs "+m.pointeursScore(J1));
        System.out.println("2 : En vie : "+m.aliveScore(J2)+" | Possession : "+m.possessionScore(J2)+" | Pointeurs "+m.pointeursScore(J2));
        
    }

    private static void testAnim() {
        Utils.animation(500, "TEST DE L'ANIMATION...");
    }

    public static void testScoreRank(){
        Rankings r = new Rankings();
        r.addPlayer("YUUMI", 5);
        r.addPlayer("SIVIR", 8);
        r.addPlayer("KHAZIX", 2);
        r.addPlayer("VIEGOSUPP", 3);
        r.addPlayer("YUUMI",15);

        System.out.println(r.toStringPlayers());
        //System.out.println("Imp : "+r.getRankingWarriors().get("IMP"));
    }

    public static void testCls(){
        Utils.clear();
        System.out.println("La console est clear? Sortie du programme");
        System.out.flush();  
    }

    public static void pr(Object o){
        System.out.println(o);
    }


}