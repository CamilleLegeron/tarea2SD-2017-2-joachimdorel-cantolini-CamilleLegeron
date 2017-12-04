package token;

import tokenInterface.TokenTasks;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;


public class Token implements TokenTasks, Serializable{
    private int[] LN;
    private Queue<Integer> queue;
//    private final static Token token = new Token(5);

    public Token(int n){
        LN = new int[n];
        for(int i=0; i<n;i++){
            LN[i] = 0;
        }
        queue = new LinkedList<Integer>();
    }

//    public static Token getToken(){return token;}

    public void incrementLN(int i) {
        LN[i]++;
    }

    public void addQueue(int process) {
        queue.add(process);
    }

    public void removeFirstQueue() {
        queue.poll();
    }

    public int getOneLN(int i) { return LN[i]; }

    public Integer getFirstQueue() {return queue.peek(); }
}
