package corewar.Local;

import java.util.LinkedList;

/**
 * Le warrior est le programme évoluant dans le core.
 * Tant qu'il lui reste un pointeur d'instruction (tête de lecture), il est en vie. 
 */

public class Warrior {

    private LinkedList<Instruction> instructions;
    private LinkedList<Integer> pointeurs;
    private String id;
    
    public LinkedList<Instruction> getInstructions() { return this.instructions; }
    public LinkedList<Integer> getPointeurs() { return this.pointeurs; }
    public String getId() { return this.id; }

    public void setInstructions(LinkedList<Instruction> instructions) { this.instructions = instructions; }
    public void setPointeurs(LinkedList<Integer> pointeurs) { this.pointeurs = pointeurs; }
    public void setId(String id) { this.id = id; }

    public Warrior(LinkedList<Instruction> instructions, String id){
        this.setInstructions(instructions);
        this.setId(id);
        this.setPointeurs(null);
    }

    public boolean isDead(){
        return this.pointeurs.isEmpty();
    }
}
