package staticmanet;

/**
 * Created by kv on 4/5/17.
 */
public interface Application {
    void receivePacket(Packet packet);
    void setNetworkInterface(NetworkInterface networkInterface);
}
