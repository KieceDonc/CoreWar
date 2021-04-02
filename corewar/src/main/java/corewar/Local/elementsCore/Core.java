package corewar.Local.elementsCore;
import corewar.Local.Partie.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Deque;

/*
Le core est la mémoire dans laquelle les programmes s'affrontent. 
Il s'agit d'un tableau contenant des InstructionIDs.
*/

public class Core {

    private InstructionID[] memoire;
    private Warriors warriors;
    private ArrayDeque<Warrior> ordre;

    public InstructionID[] getMemoire() { return this.memoire; }
    public Warriors getWarriors() { return this.warriors; }
    public ArrayDeque<Warrior> getOrdre() { return this.ordre; }

    public void setMemoire(InstructionID[] memoire) { this.memoire = memoire; }
    public void setWarriors(Warriors warriors) { this.warriors = warriors; }
    public void setOrdre(ArrayDeque<Warrior> ordre) {this.ordre = ordre; }

    public Core(InstructionID[] memoire, Warriors warriors, ArrayDeque<Warrior> ordre){
        this.setMemoire(memoire);
        this.setWarriors(warriors);
        this.setOrdre(ordre);
    }

    public Core(int length){
        this(new InstructionID[length],null,null);
    }

    public Core(){
        this(8000);
    }

    public int length(){
        return this.getMemoire().length;
    }

    public void write(int adresse, InstructionID InstructionID){
        this.getMemoire()[Math.floorMod(adresse,this.length())] = InstructionID;
    }

    public void add(Warrior w, int i){
        w.initPointeur(i);
        for(InstructionID ins : w.getInstructions()){
           write(i++,ins);
        }
    }

    public void addRandom(Warrior warrior){
        int DIST_MIN = 5;
        int length = warrior.getInstructions().size();
        int intervalle = DIST_MIN + length;
        int[] temp = new int[length()];

        int incr = 0;
        
        for(int i = 0 ; i < length() ; i++)
            if(this.read(i) != null )
                for(int j = 0 ; j <= 2*intervalle ; j++)
                    if(temp[Math.floorMod(i-intervalle+j,length())] == 0){
                        temp[Math.floorMod(i-intervalle+j,length())] = 1;
                        incr++;
                    }

        int rand = (int) Math.floor(Math.random()*(length()-incr));
        int i = 0;
        int j = 0;
        while(j < rand){
            if(temp[i] == 0)
                j++;
            i++;
        }

        add(warrior,i);
    }

    public void load(){
        Arrays.fill(getMemoire(),null);
        for(int i = 0 ; i < getWarriors().getWarriors().size() ; i++){
            if(i == 0) add(getWarriors().get(i),0); 
            else addRandom(getWarriors().get(i));
        }

    }

    public InstructionID read(int adresse){
        return this.getMemoire()[adresse];
    }

    // toString renvoyant x cases par lignes
    public String toString(int x){
        String res = "";
        int len = this.length();
        int i = 0;
        String ANSI_RESET = "\u001B[0m";
        HashMap<Integer,Warrior> pointeurs = getWarriors().mapPointeurs();

        while(i < len){
            if(this.read(i) == null){
                if(pointeurs.containsKey(i)) res+=pointeurs.get(i).couleurAnsi()+"[-]"+ANSI_RESET;
                else res+= "[-]";
                    
            }
            else{
                if(pointeurs.containsKey(i)) res+=pointeurs.get(i).couleurAnsi()+"["+String.valueOf(this.read(i).getId())+"]"+ANSI_RESET;
                else res+="["+this.read(i).getId()+"]";
                //EN GRAS : res+="[\033[1m"+String.valueOf(this.read(i).getId())+"\033[0m]";
            }
            i++;
            if(i%x == 0)
                res+="\n";
        }

        return res;
    }

    public String toString(){
        return (toString(bestLength(this.length())));
    }

    // Cette fonction sert à calculer la taille d'une ligne pour que toutes les lignes fassent la même taille et que le toString soit le plus carré possible.
    // Soit len la taille du core, la taille d'une ligne est l'entier le plus proche de sqrt(len) qui est diviseur de len
    public static int bestLength(int length){
        
        // On calcule la racine carrée de la longueue (length^(x) avec x = 0.5) mais si on le souhaite, on peut changer x pour afficher plus comme un rectancle.
        double ratio = 0.5; 
        int square = (int) Math.ceil(Math.pow(length,ratio));

        int res = 0;
        int MAX = 60;
        
        for(int i = 1 ; (i <= square) ; i++){
            if(length%i == 0)
                res = i;
        }

        System.out.println(res+" res || length/res "+length/res);
        if(length/res >MAX) return MAX;
        return (length/res);
    }
    
    // GESTION TOUR DE JEU
    public void initOrdre(){
        setOrdre(new ArrayDeque<Warrior>());
        for(Warrior w : getWarriors().getWarriors())
            ordre.offerLast(w);
    }

    public Warrior firstWarrior(){
        return getOrdre().peekFirst();
    }

    public void cycle(){
        getOrdre().peekFirst().cycle();
        getOrdre().offerLast(getOrdre().pollFirst());
    }

    public String ordreString(){
        String res = "> ";
        if(!getOrdre().isEmpty()){
    
        for(Warrior w : getOrdre()){
            res+=w.toString()+"\n";
        }}
        return res;

    }

}
