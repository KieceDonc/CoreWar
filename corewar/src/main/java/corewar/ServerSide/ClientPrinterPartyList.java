package corewar.ServerSide;

import java.io.Serializable;

public class ClientPrinterPartyList implements Serializable {

    private static final long serialVersionUID = -4582253561152039497L;
    
    private String toPrint;
    private int partyListSize;
    private int[] allIDS;

    public ClientPrinterPartyList(String toPrint, int partyListSize, int[] allIDS){
      this.toPrint = toPrint;
      this.partyListSize = partyListSize;
      this.allIDS = allIDS;
    }

    public void print(){
      System.out.print(toPrint);
    }

    public int getSize(){
      return this.partyListSize;
    }

    public int getByID(int ID){
      if(getSize()==0){
        return -1;
      }

      int partyID = -1;
      int index = 0;
      boolean founded = false;
  
      do{
        int currentPartyID = allIDS[index];
        if(currentPartyID==ID){
          partyID = currentPartyID;
          founded = true;
        }
        index++;
      }while(!founded && index<this.getSize());
      
      if(!founded){
        System.err.println("Party "+ID+" not founded");
        System.out.print(this.toString());
      }
      return partyID;
    }
  }