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
import java.util.HashMap;

public class TrafficLightServerClient extends UnicastRemoteObject implements TrafficLightInterface{
    /**
     * G corresponds to GREEN
     * Y corresponds to YELLOW
     * R corresponds to RED
     */
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

    /**
     * Constructor of the TrafficLightServerClient
     * @param id of the semaphore
     * @param name of the semaphore
     * @param n of the semaphore to generate the binding in the RMI
     * @param bearer if the semaphore is holding the token or no
     * @throws RemoteException in order to avoid remote call fail
     */
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
     * @param remoteID is id of the remote process
     * @param seq corresponds to the number of the sequence
     * @throws RemoteException in order to avoid remote call fail
     * @throws MalformedURLException indicates that a malformed URL has occurred
     * @throws NotBoundException throwns if an attempt is made to lookup or unbind in the registry a name that has no associated binding
     */
    @Override
    public void request(int remoteID, int seq) throws RemoteException, MalformedURLException, NotBoundException {
        if(remoteID == id && bearer && state.equals(COLORS[0])){
            if(tokenInterface.getOneLN(id, token)<RN[id]){
                inOutCriticalSection();
            }
            return;
        }
        System.out.println("");
        System.out.println("Received request : (" + remoteID +","+ seq +")");
        if(RN[remoteID]<seq){
            System.out.println("The request of the remote process is accepted");
            RN[remoteID] = seq;
            if(bearer){
                System.out.println("I have the token");
            }
            if(bearer && state.equals(COLORS[0])){
                tokenInterface.print(token);
                token = tokenInterface.addQueue(remoteID, token);
                tokenInterface.print(token);
                giveToken();
            } else if (bearer && state.equals(COLORS[2])) {
                System.out.println("I am in critical section, the remote process has to wait");
                mapNodeClient.get(remoteID).waitToken();
            }
        } else {
            System.out.println("The request of the process is an outdated request");
        }
    }


    /**
     * Method that tells a remote process to wait for the token to perform the critical section
     * @throws RemoteException in order to avoid remote call fail
     */
    @Override
    public void waitToken() throws RemoteException {
        System.out.println("remote waitToken call");
        state = COLORS[1];
        print();
    }


    /**
     * Method that takes possession of the token in the process.
     * @param token took
     * @throws RemoteException in order to avoid remote call fail
     * @throws MalformedURLException indicates that a malformed URL has occurred
     * @throws NotBoundException throwns if an attempt is made to lookup or unbind in the registry a name that has no associated binding
     */
    @Override
    public void takeToken(Token token) throws RemoteException, MalformedURLException, NotBoundException {
        System.out.println("remote takeToken call");
        this.token = token;
        bearer = true;
        System.out.println("I took the token");
        inOutCriticalSection();
    }

    /**
     * Method that kills the remote process.
     * Stop the S-K algorithm once the token has passed through all the nodes in the system.
     * @throws RemoteException in order to avoid remote call fail
     * @throws MalformedURLException indicates that a malformed URL has occurred
     * @throws NotBoundException throwns if an attempt is made to lookup or unbind in the registry a name that has no associated binding
     */
    @Override
    public void kill() throws RemoteException, MalformedURLException, NotBoundException {
        System.out.println("remote kill call");
        Naming.unbind(name);
    }

    /**
     * Print the current state of the semaphore
     * @throws RemoteException in order to avoid remote call fail
     */
    @Override
    public void print() throws RemoteException {
        System.out.println("----Traffic Light " + id + "-----");
        System.out.println("-- --");
        for (int i = 0; i<3; i++) {
            if(state.equals(COLORS[i])){
                System.out.println("| " + COLORS[i] + " |");
            } else {
                System.out.println("|   |");
            }
        }
        System.out.println("-- --");
        System.out.print("RN = [ ");
        for (int i=0;i<n;i++){
            System.out.print(" "+ RN[i]+ " ");
        }
        System.out.println(" ]");
        if(bearer){
            System.out.println("bearer = true");
        } else {
            System.out.println("bearer = false");
        }
        System.out.println("");
    }

    /**
     * Method that connect this TrafficLight server to all other remote TrafficLights as a client
     * @throws RemoteException in order to avoid remote call fail
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
     * @throws RemoteException in order to avoid remote call fail
     * @throws MalformedURLException indicates that a malformed URL has occurred
     * @throws NotBoundException throwns if an attempt is made to lookup or unbind in the registry a name that has no associated binding
     */
    private void inOutCriticalSection() throws RemoteException, MalformedURLException, NotBoundException {
        System.out.println("");
        System.out.println("------I have the token, I enter in my critical section------");
        state = COLORS[2];
        print();
        System.out.println("--------------I go out of my critical section---------------");
        state = COLORS[0];
        print();
        tokenInterface.print(token);
        System.out.println("I increment the token's LN["+id+"]");
        token = tokenInterface.incrementLN(id, token);
        System.out.println("I check all RN[i] == LN[i]+1, to add processes which want to enter in critical section in the queue of the token ");
        for(int i=0; i<n; i++){
            if(RN[i] == tokenInterface.getOneLN(i,token)+1){
                tokenInterface.addQueue(i, token);
            }
        }
        tokenInterface.print(token);
        System.out.println("I asked the token to print itself");
        if(checkEnd()){
            //kill
            for(int i =0;i<n;i++){
                if(i!=id)
                    mapNodeClient.get(i).kill();
            }
            kill();
        }else {
            giveToken();
        }
    }

    /**
     * Method informing that the algo is over
     * @return a boolean giving the information if the Suzuki-Kasami algorithm is over
     * @throws RemoteException in order to avoid remote call fail
     */
    private boolean checkEnd() throws RemoteException {
        for(int i=0;i<n;i++){
            if(tokenInterface.getOneLN(i,token)==0){
                return false;
            }
        }
        System.out.println("All processes have entered once in critical section. The algorithm has finished");
        return true;
    }

    /**
     * Function that gives the token to the first node in the queue and set its own token to null
     * In this way, we take take of the mutual exclusion
     * @throws RemoteException in order to avoid remote call fail
     * @throws MalformedURLException indicates that a malformed URL has occurred
     * @throws NotBoundException throwns if an attempt is made to lookup or unbind in the registry a name that has no associated binding
     */
    private void giveToken() throws RemoteException, MalformedURLException, NotBoundException {
        Integer nextNode = tokenInterface.getFirstQueue(this.token);
        tokenInterface.print(token);
        if(nextNode != -1) {
            System.out.println("");
            System.out.println("Call the takeToken function of the remote process " + nextNode);
            this.token = tokenInterface.removeFirstQueue(this.token);
            mapNodeClient.get(nextNode).takeToken(this.token);
            this.token = null;
            bearer = false;
            print();
        } else {
            System.out.println("There isn't process in the queue of the token");
        }
    }


    /**
     * Main method of the TrafficLightServerClient
     * @param args
     */
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
            TokenInterface interfaceToken = (TokenInterface) Naming.lookup("token");
            Token token = null;
            if(bearer){
                System.out.println("I have the bearer, only me instantiates the token");
                token = new Token(n);
            }

            TrafficLightServerClient nodeServer = (TrafficLightServerClient) node;
            nodeServer.suzukiKasami(initialDelay, interfaceToken, token);
        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Suzuki-Kasami algorithm
     * @param initialDelay until the semaphore will enter into a critic section
     * @param tokenInterface to access to the token methods
     * @param token to access the token
     * @throws RemoteException in order to avoid remote call fail
     */
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
                        } catch (RemoteException | NotBoundException | MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }else{
                        try {
                            request(id,RN[id]);
                        } catch (RemoteException | NotBoundException | MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        timer.setRepeats(false); // Only execute once
        timer.start();
        System.out.println("Waiting the initial delay ("+initialDelay+"ms) before asking for the token...");
    }
}
