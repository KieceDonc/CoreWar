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
        

        Warrior J1 = new Warrior();
        J1.setNom("Gemini");
        J1.setId('G');
        J1.setInstructions(Interpreteur.interpreter("corewar/src/main/java/corewar/Warriors/gemini.redcode"),J1.getId());
        J1.setCouleur("blue");

        Warrior J2 = new Warrior();
        J2.setNom("Dwarf");
        J2.setId('D');
        J2.setInstructions(Interpreteur.interpreter("corewar/src/main/java/corewar/Warriors/dwarf.redcode"),J2.getId());
        J2.setCouleur("red");

        Warrior J3 = new Warrior();
        J3.setNom("BigDwarf");
        J3.setId('B');
        J3.setInstructions(Interpreteur.interpreter("corewar/src/main/java/corewar/Warriors/mice.redcode"),J3.getId());
        J3.setCouleur("green");

        


        Warriors w = new Warriors();
        w.add(J1);
        w.add(J2);
        w.add(J3);

        Core c = new Core(15*15);
        c.setWarriors(w);
        c.load();

        J1.addPointeur(8);
        J1.addPointeur(15);
        J1.addPointeur(59);
        J1.addPointeur(13);

        c.initOrdre();
        
        while(true){
        
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        c.cycle();

        System.out.println(c.toString()+"\n"+c.ordreString());
        }*/

        

        Core c = new Core (10*10);

        InstructionID ins1 = new InstructionID(Mnemonique.MOV, new Operande(Mode.DIRECT,3),new Operande(Mode.DIRECT,2),'a');
        InstructionID ins2 = new InstructionID(Mnemonique.DAT, Mode.IMMEDIAT, 42, 'a');
        InstructionID ins3 = new InstructionID(Mnemonique.MOV, new Operande(Mode.INDIRECT,-2),new Operande(Mode.DIRECT,-2),'a');

        c.write(5,ins1);
        c.write(93,ins2);
        c.write(13,ins3);

        c.testInstruction(Mnemonique.ADD);


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
    
    
}
