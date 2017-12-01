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
    public void incrementLN(int i, TokenTasks task) {
        System.out.println("remote kill incrementLN");
        task.incrementLN(i);
    }

    @Override
    public void addQueue(int process, TokenTasks task) {
        System.out.println("remote kill addQueue");
        task.addQueue(process);
    }

    @Override
    public void removeFirstQueue(TokenTasks task) {
        System.out.println("remote kill removeLastQueue");
        task.getFirstQueue();
    }

    @Override
    public int getOneLN(int i, TokenTasks task) {
        System.out.println("remote kill getOneLN");
        return task.getOneLN(i);
    }

    @Override
    public int getFirstQueue(TokenTasks task) {
        System.out.println("remote kill getFirstQueue");
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


