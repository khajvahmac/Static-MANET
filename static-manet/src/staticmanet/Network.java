package staticmanet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Main network class.
* */
public class Network {

    private List<Node> nodes = new ArrayList<>();
    private static ExecutorService executorService = new ThreadPoolExecutor(10, 100, 100, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());

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
        ).forEach(x -> {
            executorService.execute(() -> x.receivePacket(packet));
        });
    }
}
