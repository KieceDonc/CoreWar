package corewar.Local.Partie;
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

    public void traitementPartie(){
        Warrior winner = null;

        getCore().initOrdre();
        printBoard();
        LocalGame.wait(1);

        while(winner == null){
            System.out.println(getCore().read(getCore().firstPointeur()));
            Integer next = getCore().executer(getCore().firstPointeur());
            String exec = "Execution de "+getWarriors().getWarriors().get(0)+"\n"+getCore().read(getCore().firstPointeur()).toString();
            System.out.println(exec);
            printBoard();
            getCore().cycle(next);
            winner = getCore().isWinner();
            LocalGame.wait(1);
        }

        System.out.println("Le gagnant est : "+winner.getNom());
    }

    public void printBoard(){
        System.out.println(getCore().toString());
        System.out.println(getWarriors().toString());
    }

    
}
