package corewar.ServerSide;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import corewar.ObjectModel.Player;
import corewar.ObjectModel.PlayersList;
import corewar.ObjectModel.PlayersRanking;
import corewar.ObjectModel.Rankings;
import corewar.ObjectModel.Warrior;
import corewar.ObjectModel.Warriors;
import corewar.ObjectModel.WarriorsRanking;
import corewar.ObjectModel.elementsCore.Core;
import corewar.Network.SocketCommunication;
import corewar.Utils;
import corewar.Network.EventsSubscriber;

public class Game{
    private Server server;
    private PlayersList playersList;
    private EventsSubscriber socketEventsSubscriber;
    private final int ID;
    private boolean hasStart = false;
    private HashMap<String,Integer> scoreJoueurs;
    private HashMap<String,Integer> scoreWarriors;
    private HashMap<String,Integer> settings;

    public Game(Server server) {
        this.server = server;
        this.playersList = new PlayersList();
        this.socketEventsSubscriber = new EventsSubscriber();
        this.ID = IDGenerator.get();
    }

    public void onPlayerJoin(ObjectOutputStream oosToExcept, Player player) {
        playersList.add(player);
        try {
            socketEventsSubscriber.sendAllExceptOne(
                    new SocketCommunication(SocketCommunication.PLAYER_JOINED_GAME, player), oosToExcept);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onPlayerLeave(ObjectOutputStream oosToExcept, Player player) {
        playersList.remove(player);
        socketEventsSubscriber.remove(oosToExcept);
        try {
            socketEventsSubscriber.sendAll(new SocketCommunication(SocketCommunication.PLAYER_LEFT_GAME, player));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(ObjectOutputStream oosToExcept) {
        hasStart = true;
        try {
            socketEventsSubscriber.sendAllExceptOne(new SocketCommunication(SocketCommunication.GAME_STARTING, null),
                    oosToExcept);
        } catch (IOException e) {
            e.printStackTrace();
        }
        main();
    }

    public void main() {
        try {
            System.out.println("LA LISTE DE JOUEURS ET LEURS WARRIORS :");
            for(Player p : playersList.getPlayersList()){
                System.out.println(p.getWarrior().toStringFull());
                System.out.println("\n-------------\n");
            }
            Core c = new Core(settings.get("CORE_SIZE"));
            Warriors w = new Warriors();
            for(int i = 0 ; i < playersList.getSize() ; i++){
                Warrior war = playersList.get(i).getWarrior();
                if(i == 0){
                    war.setId('1');
                    war.changeId('1');
                    war.setCouleur("green");
                }
                if(i == 1){
                    war.setId('2');
                    war.changeId('2');
                    war.setCouleur("blue");
                }
                if(i == 2){
                    war.setId('3');
                    war.changeId('3');
                    war.setCouleur("yellow");
                }
                if(i == 3){
                    war.setId('4');
                    war.changeId('4');
                    war.setCouleur("red");
                }
                w.add(war);
            }
            c.setWarriors(w);
            c.load(); 
            String debug = "";
            if(!(w.getWarriors().isEmpty()))
                debug += "Liste de joueur :\n"+w.toString();
            else
                debug+= "Liste de joueur vide";
        
                socketEventsSubscriber.sendAll(new SocketCommunication(SocketCommunication.GAME_UPDATE, debug)); 

            Utils.sleep(2000);

            Manche m = new Manche(w,c);
            m.traitementPartie(settings.get("TURNS"),settings.get("FRAME_RATE"),socketEventsSubscriber);

            scoreJoueurs = new HashMap<String,Integer>();
            scoreWarriors = new HashMap<String,Integer>();
            // On calcule les scores
            for(Player p : playersList.getPlayersList()){
                Warrior war = p.getWarrior();
                int as = m.aliveScore(war);
                int pss = m.possessionScore(war);
                int pts = m.pointeursScore(war);
                int score = as+pss+pts;
                String afficheScore = "Score du warrior "+war.getNom()+" :\n"+"En vie = "+as+" / 500  | Possession de la memoire = "+pss+"  / 400 | Nombre de pointeurs = "+pts+"  / 100\nTOTAL DES POINTS : "+score+"  / 1000";
                scoreJoueurs.put(p.getName(), score);
                scoreWarriors.put(war.getNom(), score);
                socketEventsSubscriber.sendAll(new SocketCommunication(SocketCommunication.GAME_UPDATE, afficheScore));
            }

            
        } catch (Exception e) {
            e.printStackTrace();
        }
        onEnd();
    }

    public void onEnd() {

        // PlayersRanking playersRanking = server.getRanking();
        // WarriorsRanking warriorsRanking = server.getWarriorsRanking();
        // for (int x = 0; x < playersList.getSize(); x++) {
        //     Player currentPlayer = playersRanking.get(playersList.get(x));
        //     Warrior currentWarrior = warriorsRanking.get(playersList.get(x).getWarrior());
            
        //     currentPlayer.setScore(scoreJoueurs.get(currentPlayer.getName())+currentPlayer.getScore());
        //     currentWarrior.setScore(scoreWarriors.get(currentWarrior.getNom())+currentPlayer.getScore());
        // }

        Rankings rankings = server.getRankings();
        for(String key : scoreJoueurs.keySet()){
            rankings.addPlayer(key.toUpperCase(), scoreJoueurs.get(key));
        }
        for(String key : scoreWarriors.keySet()){
            rankings.addWarrior(key.toUpperCase(), scoreWarriors.get(key));
        }
        

        try {
            socketEventsSubscriber.sendAll(new SocketCommunication(SocketCommunication.GAME_STOP, server.getRanking()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.getGameList().remove(this);
    }

    public void cancel(ObjectOutputStream oosToExcept) {
        try {
            socketEventsSubscriber.sendAllExceptOne(new SocketCommunication(SocketCommunication.CANCEL_GAME, null),oosToExcept);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void subscribeEvent(ObjectOutputStream oos){
        socketEventsSubscriber.add(oos);
    }

    public void unsubscribeEvent(ObjectOutputStream oos){
        socketEventsSubscriber.remove(oos);
    }

    public PlayersList getPlayersList(){
        return this.playersList;
    }

    public int getID(){
        return this.ID;
    }

    public boolean hasStart(){
        return this.hasStart;
    }

    public void setSettings(HashMap<String,Integer> settings){
        this.settings = settings;
    }

    public static class IDGenerator{

        public static int ID=0;

        public static synchronized int get(){
            ID+=1;
            return ID;
        }
    }
}