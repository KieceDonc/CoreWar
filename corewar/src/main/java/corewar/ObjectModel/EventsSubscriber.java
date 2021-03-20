package corewar.ObjectModel;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import corewar.Network.SocketCommunication;

public class EventsSubscriber{

    private ArrayList<Socket> socketList = new ArrayList<>();

    public void add(Socket socket) {
        socketList.add(socket);
    }

    public void sendAll(SocketCommunication socketCommunication) throws IOException {
        for(int x=0;x<this.getSize();x++){
            Socket currentSocket = socketList.get(x);
            new ObjectOutputStream(currentSocket.getOutputStream()).writeObject(socketCommunication);;
        }
    }

    public void sendAllExceptOne(SocketCommunication socketCommunication, Socket socketToExcept) throws IOException {
        for(int x=0;x<this.getSize();x++){
            Socket currentSocket = socketList.get(x);
            if(!currentSocket.equals(socketToExcept)){
                new ObjectOutputStream(currentSocket.getOutputStream()).writeObject(socketCommunication);
            }
        }
    }

    public void closeAll() throws IOException {
        sendAll(new SocketCommunication(SocketCommunication.END_COMM, null));
        for(int x=0;x<this.getSize();x++){
            Socket currentSocket = socketList.get(x);
            currentSocket.close();
        }
    }

    public int getSize(){
        return this.socketList.size();
    }
}