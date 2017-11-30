package trafficLightInterface;

import token.Token;

/**
 * Created by user on 30/11/2017.
 */
public interface Tasks {
    //TODO : Not sure at all
    void takeToken(Token token);
    void request(int id, int seq);
    void waitToken();
    void kill();
    void print();
}
