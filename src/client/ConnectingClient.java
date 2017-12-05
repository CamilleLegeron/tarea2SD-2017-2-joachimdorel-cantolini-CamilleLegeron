package client;

import trafficLightInterface.TrafficLightInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Client which allows all TrafficLight Server connect itself with all others TrafficLight Server as a Client
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
                nodeInt = (TrafficLightInterface) Naming.lookup("node"+i);
                nodeInt.connectTrafficLightsClients();
            }

        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
    }
}
