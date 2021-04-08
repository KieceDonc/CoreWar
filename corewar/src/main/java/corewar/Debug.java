package corewar;

import java.io.File;

import corewar.ObjectModel.Warrior;

public class Debug{

    public static void mainDebug(){

        int choice = 0;
        do{
            System.out.println("------------------------------------------------------------------------------------------");
            System.out.println("");
            System.out.println("1 - Cls");
            System.out.println("2 - makeWarrior()");
            System.out.println("3 - Animation");
            System.out.println("");
            System.out.print("Votre choix : ");
            choice = Read.i();
            System.out.println("");
            System.out.println("------------------------------------------------------------------------------------------");    
        }while(choice>3 || choice<1);

        switch(choice){
            case 1 : { testCls(); break; }
            case 2 : { testMakeWarrior(); break;}
            case 3 : { testAnim(); break;}

        }
        mainDebug();
        
        
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


}