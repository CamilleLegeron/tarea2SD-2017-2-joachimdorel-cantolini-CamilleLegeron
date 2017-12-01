package token;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by user on 27/11/2017.
 */
public class Token {
    static int[] LN;
    static Queue<Integer> queue;

    public Token(int n){
        LN = new int[n];
        for(int i=0; i<n;i++){
            LN[i] = 0;
        }
        queue = new LinkedList<Integer>();
    }

    public static void incrementLN(int i) {
        LN[i]++;
    }

    public static void addQueue(int process) {
        queue.add(process);
    }

    public static void removeFirstQueue() {
        queue.poll();
    }

    public static int getOneLN(int i) { return LN[i]; }

    public static int getFirstQueue() {return queue.element(); }
}
