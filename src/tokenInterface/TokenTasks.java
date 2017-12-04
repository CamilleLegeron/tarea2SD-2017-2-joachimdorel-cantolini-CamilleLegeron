package tokenInterface;

/**
 * Created by user on 30/11/2017.
 */
public interface TokenTasks<T> {
    T incrementLN(int i);
    T addQueue(int process);
    T removeFirstQueue();
    int getOneLN(int i);
    int getFirstQueue();
    void print();
}
