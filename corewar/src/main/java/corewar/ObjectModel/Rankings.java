package corewar.ObjectModel;

import java.io.Serializable;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Rankings implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 4994639983832131427L;

    private HashMap<String, Integer> rankingWarriors;
    private HashMap<String, Integer> rankingPlayers;

    public HashMap<String, Integer> getRankingWarriors() {
        return this.rankingWarriors;
    }

    public void setRankingWarriors(HashMap<String, Integer> rankingWarriors) {
        this.rankingWarriors = rankingWarriors;
    }

    public HashMap<String, Integer> getRankingPlayers() {
        return this.rankingPlayers;
    }

    public void setRankingPlayers(HashMap<String, Integer> rankingPlayers) {
        this.rankingPlayers = rankingPlayers;
    }

    public Rankings(){
        this.setRankingPlayers(new HashMap<String,Integer>());
        this.setRankingWarriors(new HashMap<String,Integer>());
    }

    public void addWarrior(String name, Integer score){
        String key = name.toUpperCase();
        if(this.getRankingWarriors().containsKey(key))
            this.getRankingWarriors().replace(key,this.getRankingWarriors().get(key)+score);
        else
            this.getRankingWarriors().put(key,score);
    }

    public void addPlayer(String name, Integer score){
        String key = name.toUpperCase();
        if(this.getRankingPlayers().containsKey(key))
            this.getRankingPlayers().replace(key,this.getRankingPlayers().get(key)+score);
        else
            this.getRankingPlayers().put(key,score);
    }

    public String toStringWarriors(){
        int size = getRankingWarriors().size();
        Object arr[][] = new Object[size][2];

        // On remplit un tableau 2D avec les couples CLE-VALEUR
        int index = 0;
        for (String key : getRankingWarriors().keySet()) {
            arr[index][0] = key;
            arr[index][1] = getRankingWarriors().get(key);
            index++;
        }

        // On trie le tableau en fonction de la clé (donc, de la 2e colonne du tableau 2D)
        Arrays.sort(arr, new Comparator<Object[]>() {
            @Override              
            public int compare(final Object[] entry1, final Object[] entry2) {
              if ((Integer)entry1[1] < (Integer)entry2[1]) 
                return 1;
              else 
                return -1;
            }
        });

        String res = "";
        for(int i = 0 ; i < size ; i++){
            res+="#"+(i+1)+" - "+arr[i][0]+"   ("+arr[i][1]+")\n";
        }
        return res;
    }

    public String toStringPlayers(){
        int size = getRankingPlayers().size();
        Object arr[][] = new Object[size][2];

        // On remplit un tableau 2D avec les couples CLE-VALEUR
        int index = 0;
        for (String key : getRankingPlayers().keySet()) {
            arr[index][0] = key;
            arr[index][1] = getRankingPlayers().get(key);
            index++;
        }

        // On trie le tableau en fonction de la clé (donc, de la 2e colonne du tableau 2D)
        Arrays.sort(arr, new Comparator<Object[]>() {
            @Override              
            public int compare(final Object[] entry1, final Object[] entry2) {
              if ((Integer)entry1[1] < (Integer)entry2[1]) 
                return 1;
              else 
                return -1;
            }
        });

        String res = "";
        for(int i = 0 ; i < size ; i++){
            res+="#"+(i+1)+" - "+arr[i][0]+"   ("+arr[i][1]+")\n";
        }
        return res;
    }

    
}
