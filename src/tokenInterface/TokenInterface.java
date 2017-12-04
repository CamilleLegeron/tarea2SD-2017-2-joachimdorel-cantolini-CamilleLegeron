package tokenInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by user on 30/11/2017.
 */
public interface TokenInterface extends Remote {
    <T> T incrementLN(int i, TokenTasks<T> task) throws RemoteException;

    <T> T addQueue(int process, TokenTasks<T> task) throws RemoteException;

    <T> T removeFirstQueue(TokenTasks<T> task) throws RemoteException;

    <T> int getOneLN(int i, TokenTasks<T> task) throws RemoteException;

    <T> int getFirstQueue(TokenTasks<T> task) throws  RemoteException;

    <T> void print(TokenTasks<T> task) throws  RemoteException;
}
