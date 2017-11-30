package trafficLightInterface;

import token.Token;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by user on 28/11/2017.
 */
public interface TrafficLightInterface extends Remote {
    //TODO : Not sure at all
    void request(int id, int seq, Tasks task) throws RemoteException;

    void waitToken(Tasks task) throws RemoteException;

    void takeToken(Token token, Tasks task) throws RemoteException;

    void kill(Tasks task) throws RemoteException;

    void print(Tasks task) throws  RemoteException;
}
