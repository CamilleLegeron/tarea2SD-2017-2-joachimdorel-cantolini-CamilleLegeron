package tokenInterface;

/**
 * Interface of the TokenTasks
 */
public interface TokenTasks<T> {

    /**
     * Method to increment the value of element at the position i in LN, the list of the
     * token.
     * @param i is the position of the element to increment
     * @return the generic updated
     */
    T incrementLN(int i);

    /**
     * Method to add a new process at the queue of the token
     * @param process corresponding to the process to add
     * @return the generic updated
     */
    T addQueue(int process);

    /**
     * Method to remove the first item in the queue of the token
     * @return the generic updated
     */
    T removeFirstQueue();

    /**
     * Method to read an element at the indice i of the LN of the token
     * @param i the indice of the element we are trying to retrieve
     * @return the int contained at the indice given
     */
    int getOneLN(int i);

    /**
     * Method to read the first element of the queue
     * @return an int corresponding at the value of the element
     */
    int getFirstQueue();

    /**
     * Method to print the current status of the LN in the token
     */
    void print();
}
