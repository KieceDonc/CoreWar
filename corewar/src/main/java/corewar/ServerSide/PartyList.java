package corewar.ServerSide;

import java.io.Serializable;
import java.util.ArrayList;

public class PartyList implements Serializable {

  private ArrayList<Party> partyList = new ArrayList<>();

  public void add(Party party){
    this.partyList.add(party);
  }
    
  /*
    Retourne la party de la liste avec l'id donné en paramètre
    * {int} ID 
    * return {Party} si la partie n'est pas trouvé renvoit null
  */
  public Party getByID(int ID){
    if(getSize()==0){
      return null;
    }
    Party party = null;
    int index = 0;
    boolean founded = false;

    do{
      Party currentParty = partyList.get(index);
      if(currentParty.getID()==ID){
        party = currentParty;
        founded = true;
      }
      index++;
    }while(!founded);
    
    if(!founded){
      System.err.println("Party "+ID+" not founded");
      System.out.print(this.toString());
    }
    return party;
  }

  public void remove(Party party){
      partyList.remove(party);
  }

  public int getSize(){
      return this.partyList.size();
  }

  public void printClient(){
    System.out.println("");
    for(int x=0;x<partyList.size();x++){
      Party currentParty = partyList.get(x);
      System.out.println("Partie id : "+currentParty.getID()+", Nombre de joueurs : "+currentParty.getPlayersList().getSize());
    }
  }
}