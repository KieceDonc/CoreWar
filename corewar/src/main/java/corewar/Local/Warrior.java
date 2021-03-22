package corewar.Local;

import java.util.LinkedList;

public class Warrior {

    private LinkedList<Instruction> instructions;
    private LinkedList<Adresse> pointeurs;
    private String id;
    
    public LinkedList<Instruction> getInstructions() { return this.instructions; }
    public LinkedList<Adresse> getPointeurs() { return this.pointeurs; }
    public String getId() { return this.id; }

    public void setInstructions(LinkedList<Instruction> instructions) { this.instructions = instructions; }
    public void setPointeurs(LinkedList<Adresse> pointeurs) { this.pointeurs = pointeurs; }
    public void setId(String id) { this.id = id; }

    public Warrior(LinkedList<Instruction> instructions, String id){
        this.setInstructions(instructions);
        this.setId(id);
        this.setPointeurs(null);
    }
}
