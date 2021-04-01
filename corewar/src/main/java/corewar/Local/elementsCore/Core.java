package corewar.Local.elementsCore;
import corewar.Local.Partie.*;
import java.util.ArrayList;
import java.util.Arrays;

/*
Le core est la mémoire dans laquelle les programmes s'affrontent. 
Il s'agit d'un tableau contenant des InstructionIDs.
*/

public class Core {

    private InstructionID[] memoire;
    private Warriors warriors;

    public InstructionID[] getMemoire() { return this.memoire; }
    public Warriors getWarriors() { return this.warriors; }

    public void setMemoire(InstructionID[] memoire) { this.memoire = memoire; }
    public void setWarriors(Warriors warriors) { this.warriors = warriors; }

    public Core(){
        this.setMemoire(new InstructionID[8000]);
    }

    public Core(int length){
        this.setMemoire(new InstructionID[length]);
    }


    public int length(){
        return this.getMemoire().length;
    }

    public void write(int adresse, InstructionID InstructionID){
        this.getMemoire()[Math.floorMod(adresse,this.length())] = InstructionID;
    }

    public void add(Warrior w, int i){
        w.setPointeurs(new ArrayList<Integer>());
        w.getPointeurs().add(i);
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
        
        for(int i = 0 ; i < length() ; i++){
            if(this.read(i) != null ){
                for(int j = 0 ; j <= 2*intervalle ; j++){
                    if(temp[Math.floorMod(i-intervalle+j,length())] == 0){
                        temp[Math.floorMod(i-intervalle+j,length())] = 1;
                        incr++;
                    }
                }
            }
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

    public InstructionID read(int adresse){
        return this.getMemoire()[adresse];
    }

    

    public String toString(int x){
        String res = "";
        int len = this.length();
        int i = 0;

        while(i < len){
            if(this.read(i) == null)
                res+="[-]";
            else
                res+="["+this.read(i).getId()+"]";
                //EN GRAS : res+="[\033[1m"+String.valueOf(this.read(i).getId())+"\033[0m]";
            i++;
            if(i%x == 0)
                res+="\n";
        }

        return res;
    }

    public String toString(){
        return (toString(bestLength(this.length())));
    }


    // 
    

    // Cette fonction sert à calculer la taille d'une ligne pour que toutes les lignes fassent la même taille et que le toString soit le plus carré possible.
    // Soit len la taille du core, la taille d'une ligne est l'entier le plus proche de sqrt(len) qui est diviseur de len
    public static int bestLength(int length){
        int square = (int) Math.ceil(Math.sqrt(length));
        int res = 0;

        for(int i = 1 ; i <= square ; i++){
            if(length%i == 0)
                res = i;
        }

        System.out.println(res);
        return (length/res);
    }
    
    




    
}
