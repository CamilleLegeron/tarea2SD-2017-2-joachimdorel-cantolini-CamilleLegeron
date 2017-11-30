package token;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by user on 27/11/2017.
 */
public class Token {
    int[] LN;
    Queue<Integer> queue;
    public Token(int size){
        LN = new int[size];
        for(int i=0; i<size;i++){
            LN[i] = 0;
        }
        queue = new LinkedList<Integer>();
    }

    public void setLN(int[] LN) {
        this.LN = LN;
    }

    public void setQueue(Queue<Integer> queue) {
        this.queue = queue;
    }

    public int[] getLN() {
        return LN;
    }

    public Queue<Integer> getQueue(){
        return  queue;
    }
}
