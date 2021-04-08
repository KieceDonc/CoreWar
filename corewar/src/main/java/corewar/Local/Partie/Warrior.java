package corewar.Local.Partie;
import corewar.Local.elementsCore.*;

import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * Le warrior est le programme évoluant dans le core.
 * Tant qu'il lui reste un pointeur d'instruction (tête de lecture), il est en vie. 
 */

public class Warrior {

    private ArrayList<InstructionID> instructions;
    private ArrayDeque<Integer> pointeurs;
    private String nom;
    private char id;
    private String couleur;
    private boolean ready;
    
    public ArrayList<InstructionID> getInstructions() { return this.instructions; }
    public ArrayDeque<Integer> getPointeurs() { return this.pointeurs; }
    public String getNom() { return this.nom; }
    public char getId() { return this.id; }
    public String getCouleur() { return this.couleur; }
    public boolean isRead() { return this.ready; }

    public void setInstructions(ArrayList<InstructionID> instructions) { this.instructions = instructions; }
    public void setInstructions(ArrayList<Instruction> instructions, char id) { this.setInstructions(toInstructionID(instructions,id)); }
    public void setPointeurs(ArrayDeque<Integer> pointeurs) { this.pointeurs = pointeurs; }
    public void setNom(String nom) { this.nom = nom; }
    public void setId(char id) { this.id = id; }
    public void setCouleur(String couleur) { this.couleur = couleur; }
    public void setReady(boolean ready) { this.ready = ready; }

    public Warrior(ArrayList<InstructionID> ins, ArrayDeque<Integer> p, String nom, char id, String col, Boolean rdy){
        this.setInstructions(ins);
        this.setPointeurs(p);
        this.setNom(nom);
        this.setId(id);
        this.setCouleur(col);
        this.setReady(rdy);
    }

    public Warrior(){
        this(null, null, null, '0' , null, false);
    }

    public Warrior(String nom, char id){
        this(null,null,nom,id,null,false);
    }

    public void setInstructionsNoID(ArrayList<Instruction> listeIns){
        this.setInstructions(toInstructionID(listeIns,this.getId()));
    }

    public boolean isDead(){
        return this.pointeurs.isEmpty();
    }

    public String toString(){
        return getNom()+" | ID : "+getId()+" | Pointeurs : "+PointeursString();
    }

    public static ArrayList<InstructionID> toInstructionID(ArrayList<Instruction> listeIns, char id){
        ArrayList<InstructionID> res = new ArrayList<InstructionID>();
        for(Instruction ins : listeIns)
            res.add(new InstructionID(ins,id));
        return res;
    }

    public String couleurAnsi(){
        if(couleur.equals("red")) return ("\u001B[31m");
        if(couleur.equals("yellow")) return ("\u001B[32m");
        if(couleur.equals("green")) return ("\u001B[33m");
        if(couleur.equals("blue")) return ("\u001B[34m");
        return "";
    }

    public void initPointeur(int i){
        setPointeurs(new ArrayDeque<Integer>());
        addPointeur(i);
    }

    public void addPointeur(int i){
        getPointeurs().offerLast(i);
    }

    public int firstPointeur(){
        return getPointeurs().peekFirst();
    }

    // return true si le warrior est en vie, sinon return false
    public boolean cycle(Integer adresse){
        if(adresse == null){
            getPointeurs().removeFirst();
            if(this.isDead())
                return false;
        }
        else{
            getPointeurs().removeFirst();
            addPointeur(adresse);
        }
        return true;
    }

    public String PointeursString(){
        
        String res = "[ >";
        for(int pointeur : getPointeurs()){
            res+=pointeur+" ";
        }
        return res+"]";
    }
}
