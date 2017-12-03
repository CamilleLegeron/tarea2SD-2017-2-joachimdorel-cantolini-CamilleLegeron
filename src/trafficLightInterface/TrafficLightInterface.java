package trafficLightInterface;

import token.Token;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by user on 28/11/2017.
 */
public interface TrafficLightInterface extends Remote {
    //TODO : Not sure at all
    void request(int id, int seq) throws RemoteException;

    void waitToken() throws RemoteException;

    void takeToken(Token token) throws RemoteException;

    void kill() throws RemoteException;

    void print() throws  RemoteException;

    void connectTrafficLightsClients() throws  RemoteException;

    }
