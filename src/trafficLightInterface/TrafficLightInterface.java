package trafficLightInterface;

import token.Token;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface for the TrafficLight
 */
public interface TrafficLightInterface extends Remote {

    /**
     * Method to send a request to all the others semaphores to let them know about the need for the token
     * @param id of the sending semaphore
     * @param seq is, in the Suzuki-Kasami algorithm, the number of the sequence of the current TrafficLight
     * @throws RemoteException in order to avoid remote call fail
     * @throws MalformedURLException indicates that a malformed URL has occurred
     * @throws NotBoundException throwns if an attempt is made to lookup or unbind in the registry a name that has no associated binding
     */
    void request(int id, int seq) throws RemoteException, MalformedURLException, NotBoundException;

    /**
     * Method that tells a remote process to wait for the token to perform the critical section
     * @throws RemoteException in order to avoid remote call fail
     */
    void waitToken() throws RemoteException;

    /**
     * Method that takes possession of the token in the process.
     * @param token took
     * @throws RemoteException in order to avoid remote call fail
     * @throws MalformedURLException indicates that a malformed URL has occurred
     * @throws NotBoundException throwns if an attempt is made to lookup or unbind in the registry a name that has no associated binding
     */
    void takeToken(Token token) throws RemoteException, MalformedURLException, NotBoundException;

    /**
     * Method that kills the remote process.
     * Stop the S-K algorithm once the token has passed through all the nodes in the system.
     * @throws RemoteException in order to avoid remote call fail
     * @throws MalformedURLException indicates that a malformed URL has occurred
     * @throws NotBoundException throwns if an attempt is made to lookup or unbind in the registry a name that has no associated binding
     */
    void kill() throws RemoteException, MalformedURLException, NotBoundException;

    /**
     * Print the current state of the semaphore
     * @throws RemoteException in order to avoid remote call fail
     */
    void print() throws  RemoteException;

    /**
     * Method that connect this TrafficLight server to all other remote TrafficLights as a client
     * @throws RemoteException in order to avoid remote call fail
     */
    void connectTrafficLightsClients() throws  RemoteException;
}
