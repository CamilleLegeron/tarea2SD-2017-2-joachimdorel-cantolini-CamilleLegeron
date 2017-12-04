package tokenInterface;

/**
 * Created by user on 30/11/2017.
 */
public interface TokenTasks {
    void incrementLN(int i);
    void addQueue(int process);
    void removeFirstQueue();
    int getOneLN(int i);
    Integer getFirstQueue();
}
