package tokenEngine;

import tokenInterface.TokenInterface;
import tokenInterface.TokenTasks;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Implementation of the token engine
 */
public class TokenEngine extends UnicastRemoteObject implements TokenInterface{
    public TokenEngine() throws RemoteException {
        super();
    }

    @Override
    /**
     * Method to increment the value of element at the position i in LN, the list of the
     * token.
     * @param i is the position of the element to increment
     * @param task corresponding to a token generalized
     * @return the token updated
     * @throws RemoteException in order to avoid remote call fail
     */
    public <T> T incrementLN(int i, TokenTasks<T> task) throws RemoteException {
        System.out.println("remote incrementLN");
        return task.incrementLN(i);
    }

    @Override
    /**
     * Method to add a new process at the queue of the token
     * @param process corresponding to the process to add
     * @param task corresponding to a token generalized
     * @return the token updated
     * @throws RemoteException in order to avoid remote call fail
     */
    public <T> T addQueue(int process, TokenTasks<T> task) throws RemoteException  {
        System.out.println("remote addQueue");
        return task.addQueue(process);
    }

    @Override
    /**
     * Method to remove the first item in the queue of the token
     * @param task corresponding to a token generalized
     * @return the token updated
     * @throws RemoteException in order to avoid remote call fail
     */
    public <T> T removeFirstQueue(TokenTasks<T> task) throws RemoteException {
        System.out.println("remote removeLastQueue");
        return task.removeFirstQueue();
    }

    @Override
    /**
     * Method to read an element at the indice i of the LN of the token
     * @param i the indice of the element we are trying to retrieve
     * @param task corresponding to a token generalized
     * @return the int contained at the indice given
     * @throws RemoteException in order to avoid remote call fail
     */
    public <T> int getOneLN(int i, TokenTasks<T> task) throws RemoteException {
        System.out.println("remote getOneLN");
        return task.getOneLN(i);
    }

    @Override
    /**
     * Method to read the first element of the queue
     * @param task corresponding to a token generalized
     * @return an int corresponding at the value of the element
     * @throws RemoteException in order to avoid remote call fail
     */
    public <T> int getFirstQueue(TokenTasks<T> task) throws RemoteException {
        System.out.println("remote getFirstQueue");
        return task.getFirstQueue();
    }

    @Override
    /**
     * Method to print the current status of the LN in the token
     * @param task corresponding to a token generalized
     * @throws RemoteException in order to avoid remote call fail
     */
    public <T> void print(TokenTasks<T> task) throws  RemoteException {
        task.print();
    }

    /**
     * Main method of the Token Engine
     * @param args
     */
    public static void main(String args[]){
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            TokenInterface engine = new TokenEngine();
            Naming.rebind("token", engine);
            System.out.println("Engine bound.");
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}


