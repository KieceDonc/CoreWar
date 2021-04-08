package corewar.Local.Partie;
import java.util.ArrayList;
import java.util.HashMap;

public class Warriors {

    private ArrayList<Warrior> warriors;
    public ArrayList<Warrior> getWarriors() { return this.warriors; }
    public void setWarriors(ArrayList<Warrior> warriors) { this.warriors = warriors; }

    public Warriors(){
        this.setWarriors(new ArrayList<Warrior>());
    }

    public Warrior get(int i){
        return this.getWarriors().get(i);
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
