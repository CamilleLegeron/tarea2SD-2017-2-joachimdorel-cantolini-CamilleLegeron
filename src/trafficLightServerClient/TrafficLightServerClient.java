package trafficLightServerClient;

import token.Token;
import tokenInterface.TokenInterface;
import trafficLightInterface.TrafficLightInterface;
import trafficLightInterface.TrafficLightTasks;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class TrafficLightServerClient extends UnicastRemoteObject implements TrafficLightInterface{

    private String name;
    ArrayList<TrafficLightInterface> listNodeClient = new ArrayList<TrafficLightInterface>();

    public TrafficLightServerClient(String name) throws RemoteException {
        super();
        this.name=name;
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

    /*@Override
    public void takeToken(Token token, TrafficLightTasks task) throws RemoteException {
        System.out.println("remote takeToken call");
        task.takeToken(token);
    }*/

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

    public void connectTrafficLightsClients(int id, int n){
        for(int i=0;i<n;i++){
            if(i!=id){
                try {
                    listNodeClient.add((TrafficLightInterface) Naming.lookup("node"+i));
                    System.out.println("Node "+id+" connected to the client node"+i);
                } catch (NotBoundException | MalformedURLException | RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String args[]){
        int id = Integer.parseInt(args[0]);
        String name = "node" + id;
        int n=Integer.parseInt(args[1]);
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            TrafficLightInterface node = new trafficLightServerClient.TrafficLightServerClient(name);
            Naming.rebind(name, node);
            System.out.println(name +" bound.");
            TokenInterface interfaceToken;
            if(Integer.parseInt(args[2])==1){
                System.out.println("je veux me connecter au token parce que j'ai le bearer");
                interfaceToken = (TokenInterface) Naming.lookup("token");
                Token tok = new Token(n);
            }

        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }
}
