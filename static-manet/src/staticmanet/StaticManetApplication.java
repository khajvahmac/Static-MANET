package staticmanet;

/**
 * Created by kv on 4/5/17.
 */
public class StaticManetApplication implements Application{

    private NetworkInterface networkInterface;

    public void receivePacket(Packet packet) {

    }

    public void setNetworkInterface(NetworkInterface networkInterface) {
        this.networkInterface = networkInterface;
    }
}
