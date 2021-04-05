package corewar.Local.elementsCore;

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

    public Instruction(){
        this.mnq = null;
        this.op1 = null;
        this.op2 = null;
    }

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

    public Instruction(Mnemonique mnq, Operande op1){
        this.mnq = mnq;
        this.op1 = op1;
        this.op2 = new Operande(Mode.IMMEDIAT,0);
    }

    public Instruction(Mnemonique mnq, Mode m1, int a1){
        this.mnq = mnq;
        this.op1 = new Operande(m1,a1);
        this.op2 = new Operande(Mode.IMMEDIAT,0);
    }

    public Instruction(int i){
        this(Mnemonique.DAT,new Operande(Mode.IMMEDIAT,i));
    }



    public Instruction(Instruction i){
        this.setMnq(i.getMnq());
        this.setOp1(i.getOp1());
        this.setOp2(i.getOp2());
    }

    public String toString(){
        String res = "";
        if(op2 == null)
            res = mnq.toString() + "\t" + op1.toString();
        else
            res =mnq.toString() + "\t" + op1.toString() + "\t" + op2.toString();
        return res;
    }

    public boolean equals(Instruction comp){
        if(this.getMnq() != comp.getMnq()) return false;
        if(this.getOp1().getAdresse() != comp.getOp1().getAdresse()) return false;
        if(this.getOp1().getMode() != comp.getOp1().getMode()) return false;
        if(this.getOp2().getMode() != comp.getOp2().getMode()) return false;
        if(this.getOp2().getAdresse() != comp.getOp2().getAdresse()) return false;
        
        return true;
    }
    

}

