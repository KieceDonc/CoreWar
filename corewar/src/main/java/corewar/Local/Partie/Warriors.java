package corewar.Local.Partie;
import java.util.ArrayList;

public class Warriors {

    private ArrayList<Warrior> warriors;

    public ArrayList<Warrior> getWarriors() { return this.warriors; }

    public void setWarriors(ArrayList<Warrior> warriors) { this.warriors = warriors; }

    public Warriors(){
        this.setWarriors(new ArrayList<Warrior>());
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
    
    public String toString(){
        String res = "";
        for(Warrior w : getWarriors())
            res += w.toString() + "\n";
        return res;        
    }
}
