package corewar.ServerSide;

import java.util.ArrayList;

public class PartyList {

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
}