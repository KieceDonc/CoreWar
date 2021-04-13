package corewar.ObjectModel;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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

    public synchronized void addWarrior(String name, Integer score){
        String key = name.toUpperCase();
        if(this.getRankingWarriors().containsKey(key))
            this.getRankingWarriors().replace(key,this.getRankingWarriors().get(key)+score);
        else
            this.getRankingWarriors().put(key,score);
    }

    public synchronized void addPlayer(String name, Integer score){
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

    public boolean saveWarriorRankings(){
        try {
            new File("Save").mkdir();
            FileOutputStream fileOut = new FileOutputStream("Save/WarriorSave");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this.getRankingWarriors());
            out.close();
            fileOut.close();
    
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
   }

    public boolean loadWarriorRankings(){
        try {
            if(!new File("Save/WarriorSave").isFile())
                return false;
            FileInputStream fileIn = new FileInputStream("Save/WarriorSave");
            ObjectInputStream ois = new ObjectInputStream(fileIn);

            @SuppressWarnings("unchecked")
            HashMap<String,Integer> load = (HashMap<String,Integer>) ois.readObject();

            this.setRankingWarriors(load);
            ois.close();
            fileIn.close();
          } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
          } catch (IOException e) {
            e.printStackTrace();
            return false;
          } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
          }

        return true;

   }

    
}
