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
            System.out.println("4 - Test Partie");
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
        Warrior J1 = new Warrior();
        pr("j1 test");
        J1.setNom("J1");
        J1.setId('C');
        J1.setInstructions(Interpreteur.interpreter("corewar/src/main/java/corewar/Warriors/dwarf.redcode"),J1.getId());
        J1.setCouleur("blue");
        pr("j1");

        Warrior J2 = new Warrior();
        J2.setNom("J2");
        J2.setId('2');
        J2.setInstructions(Interpreteur.interpreter("corewar/src/main/java/corewar/Warriors/imp.redcode"),J2.getId());
        J2.setCouleur("red");
        pr("j2");


        Warriors w = new Warriors();
        w.add(J1);
        w.add(J2);

        Core c = new Core(15*15);
        c.setWarriors(w);
        c.load();

        Manche m = new Manche(w,c);

        m.traitementPartie(6,null);
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