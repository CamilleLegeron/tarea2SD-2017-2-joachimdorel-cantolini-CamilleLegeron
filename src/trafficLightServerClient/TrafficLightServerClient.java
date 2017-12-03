package trafficLightServerClient;

import token.Token;
import tokenInterface.TokenInterface;
import trafficLightInterface.TrafficLightInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class TrafficLightServerClient extends UnicastRemoteObject implements TrafficLightInterface{
    // G : Green --> idle
    // Y : Yellow --> waiting
    // R : Red --> critical section
    private static final String[] COLORS = {"G", "Y", "R"};

    private int id;
    private String name;
    private int n;
    private ArrayList<TrafficLightInterface> listNodeClient = new ArrayList<TrafficLightInterface>();
    private String state;
    private Boolean hasTheToken;
    private Token token;
    private int RN[];

    public TrafficLightServerClient(int id, String name, int n, boolean bearer) throws RemoteException {
        super();
        this.id = id;
        this.name = name;
        state = COLORS[0];
        hasTheToken = bearer;
        this.n = n;
        RN = new int[n];
        for(int i=0; i<n;i++){
            RN[i] = 0;
        }
    }

    /**
     * Method that register a remote process request
     * @param remoteID : id of the remote process
     * @param seq : number of the sequence
     */
    @Override
    public void request(int remoteID, int seq) throws RemoteException {
        if(RN[remoteID]<seq){
            System.out.println("The request of the process " + remoteID + "is accepted");
            RN[remoteID] = seq;
            if(hasTheToken && state.equals(COLORS[0])){
                //TODO add to queue of the token
                //TODO call function that gave the
            }
        } else {
            System.out.println("The request of the process " + remoteID + "is an outdated request");
        }
    }


    /**
     * Method that tells a remote process to wait for the token to perform the critical section
     */
    @Override
    public void waitToken() throws RemoteException {
        System.out.println("remote waitToken call");
        //TODO
    }


    /**
     * Method that takes possession of the token in the process.
     * @param token : the token to take
     */
    @Override
    public void takeToken(Token token) throws RemoteException {
        System.out.println("remote takeToken call");
        //TODO
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
        System.out.println();
    }

    /**
     * Method that connect this TrafficLight server to all other remote TrafficLights as a client
     */
    @Override
    public void connectTrafficLightsClients() throws RemoteException {
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

            TokenInterface interfaceToken;
            if(bearer){
                System.out.println("je veux me connecter au token parce que j'ai le bearer");
                interfaceToken = (TokenInterface) Naming.lookup("token");
                Token token = new Token(n);
            }

            //TODO to test !!!
            TimeUnit.MILLISECONDS.sleep(initialDelay);
            for(int i = 0; i<n; i++){
                //TODO : RN[id]++; appeler la fonction request(id, RN[id]) pour tous les noeuds de la liste listNodeClient
                //TODO : problem !!!!!!! d'ici on peut pas accéder à listNodeClient parce qu'on est dans le main
                //TODO : Et node peut accéder que aux fonctions qui sont accessibles dans l'interface ...
            }

            //TODO TO TEST!!
            TrafficLightServerClient nodeTest = (TrafficLightServerClient) node;
            nodeTest.test();


        } catch (RemoteException | MalformedURLException | NotBoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void test(){
        System.out.println("Test");
        System.out.println("Print the id "+ id);
        //TODO lancer l'algo de Suzuki Kasimi ici (attendre le initialDelay, lancer la requete etc.)
    }
}
