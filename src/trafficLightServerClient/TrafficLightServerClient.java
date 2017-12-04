package trafficLightServerClient;

import token.Token;
import tokenInterface.TokenInterface;
import trafficLightInterface.TrafficLightInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class TrafficLightServerClient extends UnicastRemoteObject implements TrafficLightInterface{
    // G : Green --> idle
    // Y : Yellow --> waiting
    // R : Red --> critical section
    private static final String[] COLORS = {"G", "Y", "R"};

    private int id;
    private String name;
    private int n;
    private HashMap<Integer, TrafficLightInterface> mapNodeClient = new HashMap<Integer, TrafficLightInterface>();
    private String state;
    private Boolean bearer;
    private Token token;
    private TokenInterface tokenInterface;
    private int RN[];

    public TrafficLightServerClient(int id, String name, int n, boolean bearer) throws RemoteException {
        super();
        this.id = id;
        this.name = name;
        state = COLORS[0];
        this.bearer = bearer;
        this.n = n;
        RN = new int[n];
        for(int i=0; i<n;i++){
            RN[i] = 0;
        }
    }

    /**
     * Method that registers a remote process request
     * @param remoteID : id of the remote process
     * @param seq : number of the sequence
     */
    @Override
    public void request(int remoteID, int seq) throws RemoteException {
        System.out.println("Received request : (" + remoteID +","+ seq +")");
        if(RN[remoteID]<seq){
            System.out.println("The request of the remote process is accepted");
            RN[remoteID] = seq;
            if(bearer){
                System.out.println("I have the bearer");
            }
            if(bearer && state.equals(COLORS[0])){
                tokenInterface.addQueue(remoteID, token);
                giveToken();
            } else if (bearer && state.equals(COLORS[2])) {
                System.out.println("I am in critical section");
            }
        } else {
            System.out.println("The request of the process is an outdated request");
        }
    }


    /**
     * Method that tells a remote process to wait for the token to perform the critical section
     */
    @Override
    public void waitToken() throws RemoteException {
        System.out.println("remote waitToken call");
        state = COLORS[1];
        print();
    }


    /**
     * Method that takes possession of the token in the process.
     * @param token : the token to take
     */
    @Override
    public void takeToken(Token token) throws RemoteException {
        System.out.println("remote takeToken call");
        this.token = token;
        bearer = true;
        inOutCriticalSection();
    }

    /**
     * Method that kills the remote process.
     * Stop the S-K algorithm once the token has passed through all the nodes in the system.
     */
    @Override
    public void kill() throws RemoteException {
        System.out.println("remote kill call");
        //TODO
    }

    @Override
    public void print() throws RemoteException {
        System.out.println("----Traffic Light " + id + "-----");
        System.out.println("-   -");
        for (int i = 0; i<3; i++) {
            if(state.equals(COLORS[i])){
                System.out.println("| " + COLORS[i] + " |");
            } else {
                System.out.println("|   |");
            }
        }
        System.out.println("-   -");
        System.out.print("RN = [ ");
        for (int i=0;i<n;i++){
            System.out.print(" "+ RN[i]+ " ");
        }
        System.out.println(" ]");
        if(bearer){
            System.out.println("I have the token");
        }
        System.out.println("");
    }

    /**
     * Method that connect this TrafficLight server to all other remote TrafficLights as a client
     */
    @Override
    public void connectTrafficLightsClients() throws RemoteException {
        for(int i=0;i<n;i++){
            if(i!=id){
                try {
                    mapNodeClient.put(i, (TrafficLightInterface) Naming.lookup("node"+i));
                    System.out.println("Node "+id+" connected to the client node"+i);
                } catch (NotBoundException | MalformedURLException | RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Function that goes in and out the Critical Section
     */
    private void inOutCriticalSection() throws RemoteException {
        System.out.println("------I have the token, I enter in my critical section------");
        state = COLORS[2];
        print();
        System.out.println("--------------I go out of my critical section---------------");
        state = COLORS[0];
        print();
        tokenInterface.incrementLN(id, token);
        giveToken();
    }

    /**
     * Function that gives the token to the first node in the queue and set its own token to null
     * In this way, we take take of the mutual exclusion
     */
    private void giveToken() throws  RemoteException {
        Integer nextNode = tokenInterface.getFirstQueue(this.token);
        if(nextNode != null) {
            tokenInterface.removeFirstQueue(this.token);
            mapNodeClient.get(nextNode).takeToken(this.token);
            this.token = null;
            bearer = false;
        }
    }


    public static void main(String args[]){
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            int id = Integer.parseInt(args[0]);
            String name = "node" + id;
            int n=Integer.parseInt(args[1]);
            int initialDelay = Integer.parseInt(args[2]);
            boolean bearer;
            bearer = args[3].equals("1");

            TrafficLightInterface node = new trafficLightServerClient.TrafficLightServerClient(id, name, n, bearer);
            Naming.rebind(name, node);
            System.out.println(name +" bound.");

            TokenInterface interfaceToken = null;
            Token token = null;
            if(bearer){
                System.out.println("je veux me connecter au token parce que j'ai le bearer");
                interfaceToken = (TokenInterface) Naming.lookup("token");
                token = new Token(n);
            }



            TrafficLightServerClient nodeServer = (TrafficLightServerClient) node;
            nodeServer.suzukiKasami(initialDelay, interfaceToken, token);


        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    private void suzukiKasami(int initialDelay, TokenInterface tokenInterface, Token token) throws  RemoteException{
        System.out.println();
        System.out.println("Beginning of the Suzuki Kasami function");

        this.tokenInterface = tokenInterface;
        this.token = token;

        Timer timer = new Timer(initialDelay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                RN[id]++;
                System.out.println("Send request to all other traffic lights : (" + id + "," + RN[id] + ")");
                for(int i = 0; i<n; i++){
                    if(i != id){
                        try {
                            mapNodeClient.get(i).request(id, RN[id]);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if(bearer){
                    try {
                        inOutCriticalSection();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        timer.setRepeats(false); // Only execute once
        timer.start(); // Go go go!
        System.out.println("Waiting the initial delay ("+initialDelay+"ms) before asking for the token...");
    }
}
