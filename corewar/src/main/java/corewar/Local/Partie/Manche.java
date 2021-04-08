package corewar.Local.Partie;
import java.util.ArrayList;

import corewar.Local.elementsCore.*;

public class Manche {

    private Warriors warriors;
    private Core core;

    public Warriors getWarriors() { return this.warriors; }
    public Core getCore() { return this.core; }

    public void setWarriors(Warriors warriors) { this.warriors = warriors; }
    public void setCore(Core core) { this.core = core; }

    public Manche(Warriors warriors, Core core){
        this.setWarriors(warriors);
        this.setCore(core);
    }

    public void traitementPartie(int tours){
        ArrayList<Warrior> winner = new ArrayList<Warrior>();
        int incr = 0;

        getCore().initOrdre();
        printBoard();
        LocalGame.wait(3000);

        while(winner.isEmpty() && incr <= tours ){
            
            try{
            String exec = "----------\nExecution de "+getWarriors().getWarriors().get(0)+"\n"+getCore().read(getCore().firstPointeur()).toString();
            System.out.println(exec);
            } catch(Exception e){System.out.println("Aucune instruction executee");}
            
            LocalGame.wait(10);
            Integer next = getCore().executer(getCore().firstPointeur());
            printBoard();
            getCore().cycle(next);
            Warrior w = getCore().isWinner();
            if(w != null)
                winner.add(getCore().isWinner());
            incr++;
        }

        if(winner.isEmpty()){
            String res = "";
            for(Warrior w : getCore().getOrdre()){
                winner.add(w);
                res+=w.getNom()+"\n";
            }
            System.out.println("IL Y A EGALITE ENTRE LES SURVIVANTS\n"+res);
            
        }
        else
            System.out.println("Le gagnant est : "+winner.get(0).getNom());
    }

    public void printBoard(){
        LocalGame.clearScreen();
        System.out.println(getCore().toString());
        System.out.println(getWarriors().toString());
    }

    
}
