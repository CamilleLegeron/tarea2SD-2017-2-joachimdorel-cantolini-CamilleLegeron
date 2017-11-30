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
public class ComputeTrafficLight {
    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            TrafficLightInterface i = (TrafficLightInterface) Naming.lookup("TrafficLightInterface");
            TrafficLight tl = new TrafficLight("0",1);
            i.print(tl);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
    }
}
