package corewar.ServerSide;
import java.io.IOException;
import java.util.ArrayList;

import corewar.ObjectModel.elementsCore.*;
import corewar.Utils;
import corewar.Network.EventsSubscriber;
import corewar.Network.SocketCommunication;
import corewar.ObjectModel.*;



public class Manche {

    private Warriors warriors;
    private Core core;
    private ArrayList<Warrior> winners;
    static final int RATE = 10;

    public Warriors getWarriors() { return this.warriors; }
    public Core getCore() { return this.core; }
    public ArrayList<Warrior> getWinners(){ return this.winners; }

    public void setWarriors(Warriors warriors) { this.warriors = warriors; }
    public void setCore(Core core) { this.core = core; }
    public void setWinners(ArrayList<Warrior> winners) { this.winners = winners; }

    public Manche(Warriors warriors, Core core){
        this.setWarriors(warriors);
        this.setCore(core);
    }

    public void traitementPartie(int tours, EventsSubscriber evt){
        this.setWinners(new ArrayList<Warrior>());
        int incr = 0;
        getCore().initOrdre();
        if(evt == null)
            System.out.println(stringBoard(incr));
        else{
            try {
                System.out.println(stringBoard(incr));
                evt.sendAll(new SocketCommunication(SocketCommunication.GAME_UPDATE, stringBoard(incr)));
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        System.out.println("LA PARTIE VA COMMENCER!");
        Utils.sleep(5000);

        while(getWinners().isEmpty() && incr <= tours ){
            
            try{
            String exec = "----------\nExecution de "+getWarriors().getWarriors().get(0)+"\n"+getCore().read(getCore().firstPointeur()).toString();
            System.out.println(exec);
            } catch(Exception e){System.out.println("Aucune instruction executee");}
            
            Integer next = getCore().executer(getCore().firstPointeur());
            if(evt == null)
                System.out.println(stringBoard(incr));
            else{
                if(incr%RATE == 0){
                    try {
                        Utils.sleep(333);
                        System.out.println(stringBoard(incr));
                        evt.sendAll(new SocketCommunication(SocketCommunication.GAME_UPDATE, stringBoard(incr)));
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            getCore().cycle(next);
            Warrior w = getCore().isWinner();
            if(w != null)
                getWinners().add(getCore().isWinner());
            incr++;
        }

        if(getWinners().isEmpty()){
            String res = "";
            for(Warrior w : getCore().getOrdre()){
                getWinners().add(w);
                res+=w.getNom()+"\n";
            }
            System.out.println("IL Y A EGALITE ENTRE LES SURVIVANTS\n"+res);
            
        }
        else
            System.out.println("Le gagnant est : "+getWinners().get(0).getNom());
    }

    public String stringBoard(int tour){
        return "TOUR#"+tour+"\n"+this.getCore().toString()+"\n"+this.getWarriors().toString();
    }

    public void updateScore(){
        for(Warrior w : getWarriors().getWarriors()){
            int score = 0; //score
            if(getWinners().contains(w)){
                score += 1200/getWinners().size();
            }
            w.setScore(w.getScore()+score);
        }
    }

    //500 points repartis entre les survivants
    public int aliveScore(Warrior w){
        double winners = getWinners().size();
        if(getWinners().contains(w))
            return (int)Math.floor(500/winners);
        else
            return 0;
    }

    //400 points répartis pour la possession de la mémoire
    public int possessionScore(Warrior w){
        int score = 0;
        double coreSize = getCore().length();
        for(int i = 0 ; i < coreSize ; i++)
            if(getCore().read(i) != null && getCore().read(i).getId() == w.getId())
                score++;
        return (int)Math.floor((400*score)/coreSize);
    }

    //+10 points pour le nombre de pointeurs en vie max 100
    public int pointeursScore(Warrior w){
        int score = 100;
        if(w.getPointeurs().size() <= 10)
            score = w.getPointeurs().size()*10;
        return score;
    }

    
}
