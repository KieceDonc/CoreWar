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

    public void traitementPartie(int tours, int frameRate, EventsSubscriber evt){
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
                e1.printStackTrace();
            }
        }
        System.out.println("LA PARTIE VA COMMENCER!");
        Utils.sleep(5000);

        while(getWinners().isEmpty() && incr <= tours ){
            Integer next = getCore().executer(getCore().firstPointeur());
            if(incr%frameRate == 0){
                if(evt == null){
                    Utils.sleep(500);
                    System.out.println(stringBoard(incr));
                }else{
                    try {
                        Utils.sleep(500);
                        evt.sendAll(new SocketCommunication(SocketCommunication.GAME_UPDATE, stringBoard(incr)));
                    } catch (IOException e) {
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
            System.out.println("PLUSIEURS WARRIORS SONT ENCORE EN VIE\n"+res);
            
        }
        else
            System.out.println("LA PARTIE SE FINIT SUR UN SEUL SURVIVANT : "+getWinners().get(0).getNom());
    }

    public String stringBoard(int tour){
        return "TOUR#"+tour+"\n"+this.getCore().toString()+"\n"+this.getWarriors().toString();
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
