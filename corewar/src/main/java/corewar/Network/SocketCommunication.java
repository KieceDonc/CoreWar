package corewar.Network;

import java.io.Serializable;

public class SocketCommunication implements Serializable{

  public static final String BAD_COMM = "BAD APICALL"; // unknow api call
  public static final int END_COMM = -1;
  public static final int GET_RANKING = 0;
  public static final int IS_PLAYER_NAME_TAKEN = 1;
  public static final int CREATE_PARTY = 2;

  private int APICallType;
  private Object object;  

  public SocketCommunication(int APICallType, Object object){
    this.APICallType = APICallType;
    this.object = object;
  }

  public int getAPICallType(){
    return this.APICallType;
  }

  public Object getObject(){
    return this.object;
  }

  @Override
  public String toString(){
    String toReturn ="API call type = "+APICallType+"\n";
    if(object!=null){
      toReturn+="Object send in SocketCommunication type = "+object.getClass()+"\n";
      toReturn+="Object send in SocketCommunication toString = "+object.toString();
    }else{
      toReturn+="Object send in SocketCommunication is null";
    }
    return toReturn;
  }
}