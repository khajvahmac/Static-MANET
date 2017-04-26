package staticmanet;

/**
 * Created by kv on 4/5/17.
 */
public interface Application {
    void receivePacket(Packet packet);
    void beforeNodeMoved(Coordinate newCoordinates);
    void afterNodeMoved(Coordinate newCoordinates);
    void setNetworkInterface(NetworkInterface networkInterface);
    void initialize();
}
