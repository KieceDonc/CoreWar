package corewar.ObjectModel;

import java.util.ArrayList;
import java.util.HashMap;

public class Warriors {

    protected ArrayList<Warrior> warriors;
    public ArrayList<Warrior> getWarriors() { return this.warriors; }
    public void setWarriors(ArrayList<Warrior> warriors) { this.warriors = warriors; }

    public Warriors(){
        this.setWarriors(new ArrayList<Warrior>());
    }

    /*
    * Retourne le programme de la liste avec l'index donné en paramètre
    * {int} index: Position du joueur dans la position 
    * return {Program} si le joueur n'est pas trouvé renvoie null
    */
    public synchronized Warrior get(int index){
        if(getSize()==0){
            return null;
        }
        if(index<0 && index>=getSize()){
            System.err.println("index "+index+" in program list out of bound");
            System.out.print(this.toString());
            return null;
        }
        return this.warriors.get(index);
    }

    public synchronized Warrior get(Warrior toCompare){
        if(getSize()==0){
            return null;
        }
        
        Warrior warrior = null;
        int index = 0;
        boolean founded = false;
    
        do{
            Warrior currentWarrior = this.get(index);
            if(currentWarrior.equals(toCompare)){
                warrior = currentWarrior;
                founded = true;
            }
            index++;
        }while(!founded && index<this.getSize());
        
        return warrior;
    }

    public synchronized boolean isInList(Warrior warrior){
        return this.get(warrior)!=null;
    }

    public void add(Warrior warrior){
        getWarriors().add(warrior);
    }

    public void remove(int i){
        getWarriors().remove(i);
    }

    public void remove(Warrior w){
        getWarriors().remove(w);
    }

    public void clear(){
        getWarriors().clear();
    }

    public int getSize(){
        return warriors.size();
    }

    public HashMap<Integer,Warrior> mapPointeurs(){
        HashMap<Integer,Warrior> map = new HashMap<Integer,Warrior>();
        for(Warrior w : getWarriors()){
            for(int p : w.getPointeurs()){
                map.put(p,w);
            }
        }
        return map;
    }

    public HashMap<Character,String> mapId(){
        HashMap<Character,String> map = new HashMap<Character,String>();
        for(Warrior w : getWarriors()){
            map.put(w.getId(),w.couleurAnsi());
        }
        return map;
    }
    
    public String toString(){
        String res = "";
        for(Warrior w : getWarriors())
            res += w.toString() + "\n";
        return res;        
    }
}
