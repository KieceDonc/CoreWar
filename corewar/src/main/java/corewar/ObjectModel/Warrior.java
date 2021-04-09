package corewar.ObjectModel;

import corewar.Read;
import corewar.ClientSide.Interpreteur;
import corewar.ObjectModel.elementsCore.*;
import corewar.*;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * Le warrior est le programme évoluant dans le core.
 * Tant qu'il lui reste un pointeur d'instruction (tête de lecture), il est en vie. 
 */

public class Warrior implements Serializable {

    private static final long serialVersionUID = 5672484260836002563L;

    private ArrayList<InstructionID> instructions;
    private ArrayDeque<Integer> pointeurs;
    private String nom;
    private char id;
    private String couleur;
    private boolean ready;
    private int score;
    
    public ArrayList<InstructionID> getInstructions() { return this.instructions; }
    public ArrayDeque<Integer> getPointeurs() { return this.pointeurs; }
    public String getNom() { return this.nom; }
    public char getId() { return this.id; }
    public String getCouleur() { return this.couleur; }
    public boolean isReady() { return this.ready; }
    public int getScore(){ return this.score; }

    public void setScore(int score){ this.score = score; }
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
        if(couleur.equals("red")) return ("\033[31m");
        if(couleur.equals("yellow")) return ("\033[32m");
        if(couleur.equals("green")) return ("\033[33m");
        if(couleur.equals("blue")) return ("\033[34m");
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

    //L'ID N'A PAS D'IMPORTANCE
    public boolean equals(Warrior w){
        return this.getNom().equals(w.getNom());
    }

    public static Warrior makeWarrior(String path){
        Warrior w = new Warrior();

        // On commence par interpreter le fichier et s'assurer qu'il n'y a pas d'erreur.
        ArrayList<Instruction> liste = Interpreteur.interpreter(path);
        if(liste == null){
            return null;
        }
        w.setInstructions(liste,'0');

        // On initialise aussi le nom du warrior.
        System.out.println("Veuillez rentrer le nom de votre Warrior (entre 3 et 16 caracteres) : ");
        String name = "";
        do
            name = Read.S();
        while(name.length()<3 && name.length()>16);
        w.setNom(name);

        Utils.clear();
        System.out.println("WARRIOR : "+w.getNom());
        for(InstructionID ins : w.getInstructions())
            System.out.println(ins.toString());
        System.out.println("CE CHOIX CONVIENT? O/N");
        String choix;
        do
            choix = Read.S();
        while(!(choix.equals("O") || choix.equals("N")));

        if(choix.equals("O")){
            w.setReady(true);
            return w;
        }
        else return null;
    }



}


