package token;

import tokenInterface.TokenTasks;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Class for a token
 */
public class Token implements TokenTasks<Token>, Serializable{
    private int[] LN;
    private Queue<Integer> queue;

    public Token(int n){
        LN = new int[n];
        for(int i=0; i<n;i++){
            LN[i] = 0;
        }
        queue = new LinkedList<Integer>();
    }

    /**
     * Method to increment the value of element at the position i in LN, the list of the
     * token.
     * @param i is the position of the element to increment
     * @return the token updated
     */
    public Token incrementLN(int i) {
        LN[i]++;
        return this;
    }

    /**
     * Method to add a new process at the queue of the token
     * @param process corresponding to the process to add
     * @return the token updated
     */
    public Token addQueue(int process) {
        queue.add(process);
        return this;
    }

    /**
     * Method to remove the first item in the queue of the token
     * @return the token updated
     */
    public Token removeFirstQueue() {
        queue.poll();
        return this;
    }

    /**
     * Method to read an element at the indice i of the LN of the token
     * @param i the indice of the element we are trying to retrieve
     * @return the int contained at the indice given
     */
    public int getOneLN(int i) { return LN[i]; }

    /**
     * Method to read the first element of the queue
     * @return an int corresponding at the value of the element
     */
    public int getFirstQueue() {
        if(queue.isEmpty()){
            return-1;
        }
        return queue.peek();
    }

    /**
     * Method to print the current status of the LN in the token
     */
    public void print() {
        System.out.println("");
        System.out.print("LN = [ ");
        for (int aLN : LN) {
            System.out.print(" " + aLN + " ");
        }
        System.out.println(" ]");
        System.out.println("Queue = " + queue.toString());

    }
}
