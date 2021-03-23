package corewar.Local;

public class InstructionID extends Instruction {

    private char id;

    public char getId() { return this.id; }
    public void setId(char id) { this.id = id; }

    public InstructionID(Mnemonique mnq, Operande op1, Operande op2, char id){
        super(mnq,op1,op2);
        this.setId(id);
    }

    public InstructionID(Mnemonique mnq, Mode m1, int a1, Mode m2, int a2, char id){
        super(mnq,m1,a1,m2,a2);
        this.setId(id);
    }

    public InstructionID(Instruction i, char id){
        super(i);
        this.setId(id);
    }


}
