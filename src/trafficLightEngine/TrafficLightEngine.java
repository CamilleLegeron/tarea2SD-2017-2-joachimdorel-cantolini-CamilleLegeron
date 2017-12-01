package trafficLightEngine;

import token.Token;
import trafficLightInterface.TrafficLightTasks;
import trafficLightInterface.TrafficLightInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by user on 27/11/2017.
 */
public class TrafficLightEngine extends UnicastRemoteObject implements TrafficLightInterface {

    public TrafficLightEngine() throws RemoteException {
        super();
    }


    @Override
    public void request(int id, int seq, TrafficLightTasks task) throws RemoteException {
        System.out.println("remote request call");
        task.request(id, seq);
    }

    @Override
    public void waitToken(TrafficLightTasks task) throws RemoteException {
        System.out.println("remote waitToken call");
        task.waitToken();
    }

    @Override
    public void takeToken(Token token, TrafficLightTasks task) throws RemoteException {
        System.out.println("remote takeToken call");
        task.takeToken(token);
    }

    @Override
    public void kill(TrafficLightTasks task) throws RemoteException {
        System.out.println("remote kill call");
        task.kill();
    }

    @Override
    public void print(TrafficLightTasks task) throws RemoteException {
        System.out.println("remote print call");
        task.print();
    }

    public static void main(String args[]){
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            TrafficLightInterface engine = new TrafficLightEngine();
            Naming.rebind("TrafficLightInterface", engine);
            System.out.println("Engine bound.");
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
