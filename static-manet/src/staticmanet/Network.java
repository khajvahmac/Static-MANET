package staticmanet;

import java.util.ArrayList;
import java.util.List;

/**
 * Main network class.
* */
public class Network {

    private List<Node> nodes = new ArrayList<>();

    /**
     * Add a new node to the network.
     * */
    public void addNode(Node node) {
        this.nodes.add(node);
    }

    /**
     * Transmit data to the nodes in range.
     * */
    public void transmit(Node source, Packet packet, double range) {
        nodes.stream().filter(x ->
            source.getCoordinate().distanceTo(x.getCoordinate()) <= range
        ).forEach(x -> x.receivePacket(packet));
    }
}
