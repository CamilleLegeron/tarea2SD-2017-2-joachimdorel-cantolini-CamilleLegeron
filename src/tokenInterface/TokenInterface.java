package tokenInterface;

import token.Token;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by user on 30/11/2017.
 */
public interface TokenInterface extends Remote {
    void incrementLN(int i, TokenTasks task) throws RemoteException;

    void addQueue(int process, TokenTasks task) throws RemoteException;

    void removeFirstQueue(TokenTasks task) throws RemoteException;

    int getOneLN(int i, TokenTasks task) throws RemoteException;

    Integer getFirstQueue(TokenTasks task) throws  RemoteException;
}
