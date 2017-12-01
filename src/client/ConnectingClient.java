package client;

import trafficLight.TrafficLight;
import trafficLightInterface.TrafficLightInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by user on 30/11/2017.
 */
public class ConnectingClient {
    public static void main(String[] args) {
        int n=Integer.parseInt(args[0]);
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            TrafficLightInterface nodeInt;
            for(int i=0;i<n;i++){
                System.out.println("je veux connecter node"+i);
                nodeInt = (TrafficLightInterface) Naming.lookup("node"+i);
                TrafficLight tl = new TrafficLight("0",1);
                nodeInt.connectTrafficLightsClients(i,n);
            }


        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
    }
}
