package tokenEngine;

import tokenInterface.TokenInterface;
import tokenInterface.TokenTasks;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by user on 30/11/2017.
 */
public class TokenEngine extends UnicastRemoteObject implements TokenInterface{
    public TokenEngine() throws RemoteException {
        super();
    }

    @Override
    public <T> T incrementLN(int i, TokenTasks<T> task) throws RemoteException {
        System.out.println("remote incrementLN");
        return task.incrementLN(i);
    }

    @Override
    public <T> T addQueue(int process, TokenTasks<T> task) throws RemoteException  {
        System.out.println("remote addQueue");
        return task.addQueue(process);
    }

    @Override
    public <T> T removeFirstQueue(TokenTasks<T> task) throws RemoteException {
        System.out.println("remote removeLastQueue");
        return task.removeFirstQueue();
    }

    @Override
    public <T> int getOneLN(int i, TokenTasks<T> task) throws RemoteException {
        System.out.println("remote getOneLN");
        return task.getOneLN(i);
    }

    @Override
    public <T> int getFirstQueue(TokenTasks<T> task) throws RemoteException {
        System.out.println("remote getFirstQueue");
        return task.getFirstQueue();
    }

    @Override
    public <T> void print(TokenTasks<T> task) throws  RemoteException {
        task.print();
    }

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


