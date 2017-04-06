package staticmanet;

public class StaticManetApplication implements Application{

    private NetworkInterface networkInterface;

    public void receivePacket(Packet packet) {

    }

    public void nodeMoving(Coordinate newCoordinates) {
    }

    public void setNetworkInterface(NetworkInterface networkInterface) {
        this.networkInterface = networkInterface;
    }
}
