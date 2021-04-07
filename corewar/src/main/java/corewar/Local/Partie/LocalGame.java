package corewar.Local.Partie;
import corewar.Local.elementsCore.*;

import java.util.concurrent.TimeUnit;

import corewar.Local.Interpreteur.*;

public class LocalGame {

    Core core;

    public LocalGame(){
        this.core = null;
    }



    public void localTest(){
        /*
        Core c = new Core(420);
        InstructionID i1 = new InstructionID(Mnemonique.ADD, Mode.IMMEDIAT, 0, Mode.DIRECT, 3, 'A');
        InstructionID i2 = new InstructionID(Mnemonique.MOV, Mode.DIRECT, 2, Mode.INDIRECT, 2, 'A');
        InstructionID i3 = new InstructionID(Mnemonique.JMP, Mode.DIRECT, -2, 'A');
        InstructionID i4 = new InstructionID(Mnemonique.DAT, Mode.IMMEDIAT, 0, 'A');
        c.write(600,i1);
        c.write(601,i2);
        c.write(602,i3);
        c.write(603,i4);
        System.out.println(c.toString()+"\n");
        System.out.println(i1.toString());
        System.out.println(i2.toString());
        System.out.println(i3.toString());
        System.out.println(i4.toString());
        */

        Warrior J1 = new Warrior();
        pr("j1 test");
        J1.setNom("J1");
        J1.setId('1');
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

        m.traitementPartie();
        /*
        
        while(true){
        
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        c.cycle();

        System.out.println(c.toString()+"\n"+c.ordreString());
        }*//*

        

        Core c = new Core (10*10);

        InstructionID ins1 = new InstructionID(Mnemonique.MOV, new Operande(Mode.DIRECT,3),new Operande(Mode.DIRECT,2),'a');
        InstructionID ins2 = new InstructionID(Mnemonique.DAT, Mode.IMMEDIAT, -2, 'a');
        InstructionID ins3 = new InstructionID(Mnemonique.MOV, new Operande(Mode.INDIRECT,-2),new Operande(Mode.DIRECT,-2),'a');
        InstructionID ins4 = new InstructionID(5,'a');
        InstructionID ins5 = new InstructionID(5,'a');

        c.write(5,ins1);
        c.write(11,ins2);
        c.write(13,ins3);
        c.write(9,ins4);

        //c.testInstruction(Mnemonique.DJZ);
        pr(ins4.equals(ins3));
        //pr(c.evalData(Core.OP.A, 13));

        /*

        //

        c.write(5,ins1);
        //c.write(3,ins2);
        c.executer(5);

        pr(c.testString());
        pr("1: "+c.read(1).toString()+"\n5: "+c.read(5));*/


    }

    
    public static void pr(Object o){
        System.out.println(o);
    }

    public static void wait(int s){
        try {
            TimeUnit.MILLISECONDS.sleep(30);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
}
