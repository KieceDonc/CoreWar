package corewar.Local;

public class LocalGame {

    Core core;

    public LocalGame(){
        this.core = null;
    }



    public void localTest(){

        Core c = new Core(800);
        Instruction i = new Instruction(Mnemonique.MOV, Mode.IMMEDIAT, 0, Mode.DIRECT, 3);
        InstructionID in = new InstructionID(i,'a');
        c.write(1605,in);
        System.out.println(c.toString());
    }
    
}
