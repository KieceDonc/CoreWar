package corewar.Local;

public class Instruction {
    private Mnemonique mnq;
    private Operande op1;
    private Operande op2;

    public Mnemonique getMnq() { return this.mnq; }
    public Operande getOp1() { return this.op1; }
    public Operande getOp2() { return this.op2; }

    public void setMnq(Mnemonique mnq) { this.mnq = mnq; }
    public void setOp1(Operande op1) { this.op1 = op1; }
    public void setOp2(Operande op2) { this.op2 = op2;}

    public Instruction(Mnemonique mnq, Operande op1, Operande op2){
        this.mnq = mnq;
        this.op1 = op1;
        this.op2 = op2;
    }

    public Instruction(Mnemonique mnq, Mode m1, int a1, Mode m2, int a2){
        this.mnq = mnq;
        this.op1 = new Operande(m1,a1);
        this.op2 = new Operande(m2,a2);
    }

    public Instruction(Instruction i){
        this.setMnq(i.getMnq());
        this.setOp1(i.getOp1());
        this.setOp2(i.getOp2());
    }

    public String toString(){
        return mnq.toString() + " " + op1.toString() + " " + op2.toString();
    }
    

}
