package corewar.Local;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

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

    

        System.out.println("Sandbox:");
        Interprete.sandbox1();

        


    }

    
    
    
    
}
