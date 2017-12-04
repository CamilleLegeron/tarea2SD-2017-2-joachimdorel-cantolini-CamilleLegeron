package token;

import tokenInterface.TokenTasks;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;


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


    public Token incrementLN(int i) {
        LN[i]++;
        return this;
    }

    public Token addQueue(int process) {
        queue.add(process);
        return this;
    }

    public Token removeFirstQueue() {
        queue.poll();
        return this;
    }

    public int getOneLN(int i) { return LN[i]; }

    public int getFirstQueue() {return queue.peek(); }

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
