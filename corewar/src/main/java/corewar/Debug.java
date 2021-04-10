package corewar;

import java.io.File;

import corewar.ClientSide.Interpreteur;
import corewar.ObjectModel.Warrior;
import corewar.ObjectModel.Warriors;
import corewar.ObjectModel.elementsCore.Core;
import corewar.ServerSide.Manche;

public class Debug{

    public static void mainDebug(){

        int choice = 0;
        do{
            System.out.println("------------------------------------------------------------------------------------------");
            System.out.println("");
            System.out.println("1 - Cls");
            System.out.println("2 - makeWarrior()");
            System.out.println("3 - Animation");
            System.out.println("4 - Test Partie DWARF vs IMP");
            System.out.println("");
            System.out.print("Votre choix : ");
            choice = Read.i();
            System.out.println("");
            System.out.println("------------------------------------------------------------------------------------------");    
        }while(choice>4 || choice<1);

        switch(choice){
            case 1 : { testCls(); break; }
            case 2 : { testMakeWarrior(); break;}
            case 3 : { testAnim(); break;}
            case 4 : { testPartie(); break;}

        }
        mainDebug();
        
        
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
        J1.setInstructions(Interpreteur.interpreter("corewar/src/main/java/corewar/Warriors/dwarf.redcode"),J1.getId());
        J1.setCouleur("blue");

        Warrior J2 = new Warrior();
        J2.setNom("IMP");
        J2.setId('2');
        J2.setInstructions(Interpreteur.interpreter("corewar/src/main/java/corewar/Warriors/imp.redcode"),J2.getId());
        J2.setCouleur("red");


        Warriors w = new Warriors();
        w.add(J1);
        w.add(J2);

        Core c = new Core(coreSize);
        c.setWarriors(w);
        c.load();

        Manche m = new Manche(w,c);

        m.traitementPartie(tours,frameRate,null);
        m.updateScore();

        System.out.println("1 : En vie : "+m.aliveScore(J1)+" | Possession : "+m.possessionScore(J1)+" | Pointeurs "+m.pointeursScore(J1));
        System.out.println("2 : En vie : "+m.aliveScore(J2)+" | Possession : "+m.possessionScore(J2)+" | Pointeurs "+m.pointeursScore(J2));
        
    }

    private static void testAnim() {
        Utils.animation(500, "TEST DE L'ANIMATION...");
    }

    public static void testMakeWarrior(){
        Warrior w = Warrior.makeWarrior("corewar/src/main/java/corewar/Warriors/dwarf.redcode");
    }

    public static void testCls(){
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBB");
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBB");
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBB");
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBB");
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBB");
        System.out.println("APPUYEZ SUR ENTREE");
        String s = Read.S();
        Utils.clear();
        System.out.println("La console est clear? Sortie du programme");
        System.out.flush();  
    }

    public static void pr(Object o){
        System.out.println(o);
    }


}