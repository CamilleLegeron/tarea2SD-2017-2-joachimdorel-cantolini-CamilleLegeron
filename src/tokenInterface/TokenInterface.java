package tokenInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The interface of the engine for the token
 */
public interface TokenInterface extends Remote {

    /**
     * Method to increment the value of element at the position i in LN, the list of the
     * token.
     * @param i is the position of the element to increment
     * @param task corresponding to a token generalized
     * @return the token updated
     * @throws RemoteException in order to avoid remote call fail
     */
    <T> T incrementLN(int i, TokenTasks<T> task) throws RemoteException;

    /**
     * Method to add a new process at the queue of the token
     * @param process corresponding to the process to add
     * @param task corresponding to a token generalized
     * @return the token updated
     * @throws RemoteException in order to avoid remote call fail
     */
    <T> T addQueue(int process, TokenTasks<T> task) throws RemoteException;

    /**
     * Method to remove the first item in the queue of the token
     * @param task corresponding to a token generalized
     * @return the token updated
     * @throws RemoteException in order to avoid remote call fail
     */
    <T> T removeFirstQueue(TokenTasks<T> task) throws RemoteException;

    /**
     * Method to read an element at the indice i of the LN of the token
     * @param i the indice of the element we are trying to retrieve
     * @param task corresponding to a token generalized
     * @return the int contained at the indice given
     * @throws RemoteException in order to avoid remote call fail
     */
    <T> int getOneLN(int i, TokenTasks<T> task) throws RemoteException;

    /**
     * Method to read the first element of the queue
     * @param task corresponding to a token generalized
     * @return an int corresponding at the value of the element
     * @throws RemoteException in order to avoid remote call fail
     */
    <T> int getFirstQueue(TokenTasks<T> task) throws  RemoteException;

    /**
     * Method to print the current status of the LN in the token
     * @param task corresponding to a token generalized
     * @throws RemoteException in order to avoid remote call fail
     */
    <T> void print(TokenTasks<T> task) throws  RemoteException;
}
