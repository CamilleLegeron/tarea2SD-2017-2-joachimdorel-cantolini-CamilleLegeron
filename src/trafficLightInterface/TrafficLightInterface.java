package trafficLightInterface;

import token.Token;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by user on 28/11/2017.
 */
public interface TrafficLightInterface extends Remote {
    //TODO : Not sure at all
    void request(int id, int seq, TrafficLightTasks task) throws RemoteException;

    void waitToken(TrafficLightTasks task) throws RemoteException;

    void takeToken(Token token, TrafficLightTasks task) throws RemoteException;

    void kill(TrafficLightTasks task) throws RemoteException;

    void print(TrafficLightTasks task) throws  RemoteException;
}
