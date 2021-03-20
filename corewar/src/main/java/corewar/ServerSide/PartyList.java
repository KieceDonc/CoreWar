package corewar.ServerSide;

import java.util.ArrayList;

public class PartyList{

  private ArrayList<Party> partyList = new ArrayList<>();

  public void add(Party party){
    this.partyList.add(party);
  }

  public Party getByIndex(int index){
    if(index<0 || index>this.getSize()){
      return null;
    }else{
      return this.partyList.get(index);
    }
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
    }while(!founded && index<this.getSize());
    
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

  public int[] getAllIDS(){
    int allIDS[] = new int[this.getSize()];
    for(int x=0;x<this.getSize();x++){
      allIDS[x] = this.partyList.get(0).getID();
    }
    return allIDS;
  }

  public ClientPrinterPartyList getClientPrinterObject(){
    String toPrint = "\n";
    for(int x=0;x<partyList.size();x++){
      Party currentParty = partyList.get(x);
      toPrint+="Partie id : "+currentParty.getID()+", Nombre de joueurs : "+currentParty.getPlayersList().getSize()+"\n";
    }
    return new ClientPrinterPartyList(toPrint,partyList.size(),this.getAllIDS());
  }
}