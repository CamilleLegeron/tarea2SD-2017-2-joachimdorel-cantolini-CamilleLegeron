package trafficLightInterface;

import token.Token;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by user on 28/11/2017.
 */
public interface TrafficLightInterface extends Remote {
    //TODO : Not sure at all
    void request(int id, int seq) throws RemoteException, MalformedURLException, NotBoundException;

    void waitToken() throws RemoteException;

    void takeToken(Token token) throws RemoteException, MalformedURLException, NotBoundException;

    void kill() throws RemoteException, MalformedURLException, NotBoundException;

    void print() throws  RemoteException;

    void connectTrafficLightsClients() throws  RemoteException;

    }
