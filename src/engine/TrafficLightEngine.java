package engine;

import token.Token;
import trafficLightInterface.Tasks;
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
    public void request(int id, int seq, Tasks task) throws RemoteException {
        System.out.println("remote request call");
        task.request(id, seq);
    }

    @Override
    public void waitToken(Tasks task) throws RemoteException {
        System.out.println("remote waitToken call");
        task.waitToken();
    }

    @Override
    public void takeToken(Token token, Tasks task) throws RemoteException {
        System.out.println("remote takeToken call");
        task.takeToken(token);
    }

    @Override
    public void kill(Tasks task) throws RemoteException {
        System.out.println("remote kill call");
        task.kill();
    }

    @Override
    public void print(Tasks task) throws RemoteException {
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
