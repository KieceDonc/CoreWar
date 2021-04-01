package corewar.Network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class EventsSubscriber{

    private ArrayList<ObjectOutputStream> oosList = new ArrayList<>();

    public void add(ObjectOutputStream oos) {
        oosList.add(oos);
    }

    public void remove(ObjectOutputStream oos){
        for(int x=0;x<this.getSize();x++){
            ObjectOutputStream currentOos = oosList.get(x);
            if(currentOos.equals(oos)){
                oosList.remove(x);
            }
        }
    }

    public void sendAll(SocketCommunication socketCommunication) throws IOException {
        for(int x=0;x<this.getSize();x++){
            ObjectOutputStream currentOos = oosList.get(x);
            currentOos.writeObject(socketCommunication);;
        }
    }

    public void sendAllExceptOne(SocketCommunication socketCommunication, ObjectOutputStream oosExcept) throws IOException {
        for(int x=0;x<this.getSize();x++){
            ObjectOutputStream currentOos = oosList.get(x);
            if(!currentOos.equals(oosExcept)){
                currentOos.writeObject(socketCommunication);
            }
        }
    }

    public int getSize(){
        return this.oosList.size();
    }
}