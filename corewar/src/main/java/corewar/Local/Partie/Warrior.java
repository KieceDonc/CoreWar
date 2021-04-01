package corewar.Local.Partie;
import corewar.Local.elementsCore.*;
import corewar.Local.Interpreteur.*;

import java.util.ArrayList;

/**
 * Le warrior est le programme évoluant dans le core.
 * Tant qu'il lui reste un pointeur d'instruction (tête de lecture), il est en vie. 
 */

public class Warrior {

    private ArrayList<InstructionID> instructions;
    private ArrayList<Integer> pointeurs;
    private String nom;
    private char id;
    
    public ArrayList<InstructionID> getInstructions() { return this.instructions; }
    public ArrayList<Integer> getPointeurs() { return this.pointeurs; }
    public String getNom() { return this.nom; }
    public char getId() { return this.id; }

    public void setInstructions(ArrayList<InstructionID> instructions) { this.instructions = instructions; }
    public void setInstructions(ArrayList<Instruction> instructions, char id) { this.setInstructions(toInstructionID(instructions,id)); }
    public void setPointeurs(ArrayList<Integer> pointeurs) { this.pointeurs = pointeurs; }
    public void setNom(String nom) { this.nom = nom; }
    public void setId(char id) { this.id = id; }

    public Warrior(){
        this.setInstructions(null);
        this.setNom(null);
        this.setPointeurs(null);
    }

    public Warrior(String nom, char id){
        this.setInstructions(null);
        this.setPointeurs(null);
        this.setNom(nom);
        this.setId(id);
    }

    public void setInstructionsNoID(ArrayList<Instruction> listeIns){
        this.setInstructions(toInstructionID(listeIns,this.getId()));
    }

    public boolean isDead(){
        return this.pointeurs.isEmpty();
    }

    public String toString(){
        return getNom()+"(ID : "+getId()+")";
    }

    public static ArrayList<InstructionID> toInstructionID(ArrayList<Instruction> listeIns, char id){
        ArrayList<InstructionID> res = new ArrayList<InstructionID>();
        for(Instruction ins : listeIns)
            res.add(new InstructionID(ins,id));
        return res;
    }
}
