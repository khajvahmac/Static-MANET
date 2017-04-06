package staticmanet;

/**
 * The main abstraction that Applications use to transmit data. The interface is exposed from a node to the application.
 * The NetworkInterface, in turn, works with a Network instance to transmit data to other nodes.
* */
public class NetworkInterface {
    private Node source;
    private Network network;
    private double shortRange;
    private double longRange;

    public NetworkInterface(Node source, Network network) {
        this.source = source;
        this.network = network;
        this.shortRange = ApplicationConstants.SHORT_TRANSMITTION_RANGE;
        this.longRange = ApplicationConstants.LONG_TRANSMITTION_RANGE;
    }

    public void shortTransmitPacket(Packet packet) {
        this.network.transmit(this.source, packet, this.shortRange);
    }

    public void longTransmitPacket(Packet packet) {
        this.network.transmit(this.source, packet, this.longRange);
    }
}
