package staticmanet;

public class Node {
    private int id;
    private Coordinate coordinate;
    private Application application;
    private NetworkInterface networkInterface;

    /**
    * @param id The id of the node;
    * @param coordinate Initial coordinates;
    * @param application The application instance that is ran on the node;
    * */
    public Node(int id, Coordinate coordinate, Application application) {
        this.id = id;
        this.coordinate = coordinate;
        this.application = application;
        this.networkInterface = new NetworkInterface(this);
        this.application.setNetworkInterface(this.networkInterface);
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
    public void runRandomMove() {

    }

    /*
    * Randomly moves the node.
    * */
    private void randomMove() {

    }
}
