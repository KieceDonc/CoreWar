package corewar.ServerSide;

import java.io.Serializable;

public class ClientPrinterGameList implements Serializable {

    private static final long serialVersionUID = -4582253561152039497L;
    
    private String toPrint;
    private int[] allIDS;

    public ClientPrinterGameList(String toPrint, int[] allIDS){
      this.toPrint = toPrint;
      this.allIDS = allIDS;
    }

    public void print(){
      System.out.print(toPrint);
    }

    public int getSize(){
      return this.allIDS.length;
    }

    public int getByID(int ID){
      if(getSize()==0){
        return -1;
      }

      int gameID = -1;
      int index = 0;
      boolean founded = false;
  
      do{
        int currentGameID = allIDS[index];
        if(currentGameID==ID){
          gameID = currentGameID;
          founded = true;
        }
        index++;
      }while(!founded && index<this.getSize());
      
      if(!founded){
        System.err.println("La partie avec l'ID "+ID+" n'existe pas");
      }
      return gameID;
    }
  }