package trafficLight;

import token.Token;
import trafficLightInterface.Tasks;

import java.io.Serializable;

/**
 * Created by user on 27/11/2017.
 */

// TODO : CAUTION : all functions on this class have to be declared in the TrafficLightInterface

public class TrafficLight implements Tasks, Serializable{
    String id;
    String[] colors;
    String state;
    Boolean hasTheToken;
    int RN[];

    /**
     *
     * @param id of the present trafficLight.TrafficLight
     * @param n the total number of trafficLight.TrafficLight
     */
    public TrafficLight(String id, int n){
        this.id = id;
        colors = new String[3];
        colors[0] = "GREEN";
        colors[1] = "YELLOW";
        colors[2] = "RED";
        state = colors[0];
        hasTheToken = false;
        RN = new int[n];
        for(int i=0; i<n;i++){
            RN[i] = 0;
        }
    }



    public String getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public Boolean getHasTheToken() {
        return hasTheToken;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setHasTheToken(){
        hasTheToken = !hasTheToken;
    }

    public void print() {
        System.out.println("----Traffic Light " + id + "-----");
        System.out.println("-  -");
        for (int i = 0; i<3; i++) {
            if(state.equals(colors[i])){
                System.out.println("| " + colors[i] + " |");
            } else {
                System.out.println("|   |");
            }
        }
        System.out.println("-  -");
        System.out.println();
    }

    //Método que registra un request de un proceso remoto. Recibe el id del proceso que hace la petición y el número de petición del proceso.
    public void request(int id, int seq){
        //TODO
    }

    //Método que le indica a un proceso remoto que debe esperar por el token para realizar la sección crítica.
    public void waitToken(){
        //TODO
    }

    //Método que toma posesión del token en el proceso.
    public void takeToken(Token token){
        //TODO
    }

    //Método que mata el proceso remoto. Debe usar este método para detener el algoritmo de S-K una vez que el token haya pasado por todos los nodos del sistema.
    public void kill(){
        //TODO
    }
}
