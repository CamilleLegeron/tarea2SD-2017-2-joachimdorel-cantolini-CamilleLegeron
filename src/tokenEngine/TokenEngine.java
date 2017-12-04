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
    public void incrementLN(int i, TokenTasks task) throws RemoteException {
        System.out.println("remote incrementLN");
        task.incrementLN(i);
    }

    @Override
    public void addQueue(int process, TokenTasks task) throws RemoteException  {
        System.out.println("remote addQueue");
        task.addQueue(process);
    }

    @Override
    public void removeFirstQueue(TokenTasks task) throws RemoteException {
        System.out.println("remote removeLastQueue");
        task.removeFirstQueue();
    }

    @Override
    public int getOneLN(int i, TokenTasks task) throws RemoteException {
        System.out.println("remote getOneLN");
        return task.getOneLN(i);
    }

    @Override
    public Integer getFirstQueue(TokenTasks task) throws RemoteException {
        System.out.println("remote getFirstQueue");
        return task.getFirstQueue();
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


