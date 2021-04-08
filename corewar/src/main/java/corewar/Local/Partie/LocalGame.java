package corewar.Local.Partie;
import corewar.Local.elementsCore.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import corewar.Local.Interpreteur.*;

public class LocalGame {

    Core core;

    public LocalGame() {
        this.core = null;
    }

    public void localTest() {
        /*
         * Core c = new Core(420); InstructionID i1 = new InstructionID(Mnemonique.ADD,
         * Mode.IMMEDIAT, 0, Mode.DIRECT, 3, 'A'); InstructionID i2 = new
         * InstructionID(Mnemonique.MOV, Mode.DIRECT, 2, Mode.INDIRECT, 2, 'A');
         * InstructionID i3 = new InstructionID(Mnemonique.JMP, Mode.DIRECT, -2, 'A');
         * InstructionID i4 = new InstructionID(Mnemonique.DAT, Mode.IMMEDIAT, 0, 'A');
         * c.write(600,i1); c.write(601,i2); c.write(602,i3); c.write(603,i4);
         * System.out.println(c.toString()+"\n"); System.out.println(i1.toString());
         * System.out.println(i2.toString()); System.out.println(i3.toString());
         * System.out.println(i4.toString());
         */

        Warrior J1 = new Warrior();
        pr("j1 test");
        J1.setNom("J1");
        J1.setId('1');
        J1.setInstructions(Interpreteur.interpreter("corewar/src/main/java/corewar/Warriors/imp.redcode"), J1.getId());
        J1.setCouleur("blue");
        pr("j1");

        Warrior J2 = new Warrior();
        J2.setNom("J2");
        J2.setId('2');
        J2.setInstructions(Interpreteur.interpreter("corewar/src/main/java/corewar/Warriors/imp.redcode"), J2.getId());
        J2.setCouleur("red");
        pr("j2");

        Warriors w = new Warriors();
        w.add(J1);
        w.add(J2);

        Core c = new Core(2000);
        c.setWarriors(w);
        c.load();

        Manche m = new Manche(w, c);

        m.traitementPartie(100);
    }

    public static void pr(Object o) {
        System.out.println(o);
    }

    public static void wait(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void clearScreen() {
        wait(2000);

        for(int x=0;x<50;x++){
            System.out.println("\n");
        }
     
     }
    
    
}
