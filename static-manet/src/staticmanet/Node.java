package staticmanet;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Node {
    private int id;
    private Coordinate coordinate;
    private Application application;
    private NetworkInterface networkInterface;
    private ExecutorService es;
    private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

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

    /*
    * Starts moving the node randomly.
    * */
    public void startRandomMove() {
        scheduledExecutorService.scheduleAtFixedRate(() -> randomMove(), 1, 1, TimeUnit.SECONDS);
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
    * Randomly moves the node.
    * */
    private void randomMove() {
        double radius = 0 + Math.random() * ((ApplicationConstants.MAX_MOVE_DISTANCE - 0) + 1);
        int angle = (int)(Math.random() * (360 + 1));
        Coordinate newCoordinates = new Coordinate(Math.sin(angle)*radius + this.coordinate.getX(),
                Math.cos(angle)*radius + this.coordinate.getY());
        this.application.beforeNodeMoved(newCoordinates);
        this.coordinate = newCoordinates;
        this.application.afterNodeMoved(newCoordinates);
    }
}
