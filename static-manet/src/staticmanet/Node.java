package staticmanet;

import java.util.concurrent.ExecutorService;

public class Node {
    private int id;
    private Coordinate coordinate;
    private Application application;
    private NetworkInterface networkInterface;
    private ExecutorService es;

    /**
     * @param id The id of the node
     * @param coordinate Initial coordinates
     * @param application The application instance that is ran on the node
     * @param network The network instance the node belongs to
    * */
    public Node(int id, Coordinate coordinate, Application application, Network network, ExecutorService es) {
        this.id = id;
        this.es = es;
        this.coordinate = coordinate;
        this.application = application;
        this.networkInterface = new NetworkInterface(this, network);
        this.application.setNetworkInterface(this.networkInterface);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public NetworkInterface getNetworkInterface() {
        return networkInterface;
    }

    public void move(Coordinate newCoordinate) {
        this.coordinate = newCoordinate;
    }

    public void receivePacket(Packet packet) {
        this.application.receivePacket(packet);
    }

    /*
    * Starts moving the node randomly.
    * */
    public void startRandomMove() {
        this.es.submit(this::randomMove);
    }

    /*
    * Randomly moves the node.
    * */
    private void randomMove() {

    }
}
