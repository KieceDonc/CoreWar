package corewar.Local;

public class LocalGame {

    Core core;

    public LocalGame(){
        this.core = null;
    }



    public void localTest(){
        
        Instruction i = new Instruction(Mnemonique.MOV, new Operande(Mode.IMMEDIAT, new Adresse(0)), new Operande(new Adresse(3)));
        System.out.println(i.toString());
    }
    
}
