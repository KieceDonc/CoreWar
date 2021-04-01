package corewar.Local.Partie;
import corewar.Local.elementsCore.*;
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
        J1.setNom("Gemini");
        J1.setId('G');
        J1.setInstructions(Interpreteur.interpreter("corewar/src/main/java/corewar/Warriors/gemini.redcode"),J1.getId());

        Warrior J2 = new Warrior();
        J2.setNom("Dwarf");
        J2.setId('D');
        J2.setInstructions(Interpreteur.interpreter("corewar/src/main/java/corewar/Warriors/dwarf.redcode"),J2.getId());

        Warrior J3 = new Warrior();
        J3.setNom("BigDwarf");
        J3.setId('B');
        J3.setInstructions(Interpreteur.interpreter("corewar/src/main/java/corewar/Warriors/mice.redcode"),J3.getId());


        Warriors w = new Warriors();
        w.add(J1);
        w.add(J2);
        w.add(J3);

        Core c = new Core(200);
        c.addRandom(J1);
        c.addRandom(J2);
        c.addRandom(J3);


        System.out.println(c.toString());



        


    }

    
    
    
    
}
