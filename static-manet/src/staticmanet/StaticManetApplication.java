package staticmanet;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class StaticManetApplication implements Application{

    private final HexagonAreaManager areaManager;
    private int currHexagon;
    private NetworkInterface networkInterface;
    private static Logger logger = Logger.getLogger("StaticManet");
    private Coordinate coordinate;
    private Map<Integer, Boolean> neighbours;
    private Set<Integer> processedPackets = new HashSet<>();
    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    private Boolean isEmpty;

    public StaticManetApplication(Coordinate coordinate, HexagonAreaManager areaManager) {
        this.coordinate = coordinate;
        this.areaManager = areaManager;
        this.currHexagon = areaManager.getHexagonId(coordinate);
        //TODO: assign neighbours
    }

    public void receivePacket(Packet packet) {
        //Discard if the packet isn't for the current hexagon.
        if (packet.getNextDestination() != this.currHexagon) {
            return;
        }
        switch (packet.getMessageType()) {
            case DATA_TO_TRANSMIT:
                if (packet.getInterSource() == this.currHexagon) {
                    if (packet.getSource() != this.currHexagon) {
                        this.processedPackets.add(packet.getId());
                    }
                    return;
                }
                logger.info("Data to transmit" + packet);
                dataToTransmit(packet);
                break;
            case NODE_ENTERING:
                logger.info("Node Entering" + packet);
                nodeEntered(packet);
                break;
            case NODE_LEAVING:
                logger.info("Node Leaving" + packet);
                nodeLeaving(packet);
                break;
            case HEX_NOT_EMPTY:
                logger.info("Hex not empty" + packet);
                this.isEmpty = false;
                break;
            case HEX_EMPTY:
                logger.info("Neighbour empty" + packet);
                this.neighbours.put(packet.getInterSource(), false);
                break;
            case HEX_BECAME_ACTIVE:
                logger.info("Hex became active" + packet);
                this.neighbours.put(packet.getInterSource(), true);
        }
    }

    public void beforeNodeMoved(Coordinate newCoordinates) {
        if (this.currHexagon != this.areaManager.getHexagonId(newCoordinates)) {
            Packet leavingPacket = new Packet(this.currHexagon, this.currHexagon, this.currHexagon,
                    null, MessageType.NODE_LEAVING);
            this.networkInterface.shortTransmitPacket(leavingPacket);
            this.isEmpty = true;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, "Critical error: " + e + ", Coordinates: " + this.coordinate);
                return;
            }

            if (this.isEmpty) {
                Packet emptyPacket = new Packet(this.currHexagon, this.currHexagon, this.currHexagon,
                        null, MessageType.HEX_EMPTY);

                this.networkInterface.longTransmitPacket(emptyPacket);
            }
        }
    }

    public void afterNodeMoved(Coordinate newCoordinates) {
        if (this.currHexagon != this.areaManager.getHexagonId(newCoordinates)) {
            Packet enteringPacket = new Packet(this.currHexagon, this.currHexagon, this.currHexagon,
                    null, MessageType.NODE_ENTERING);

            this.networkInterface.shortTransmitPacket(enteringPacket);
            this.isEmpty = true;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, "Critical error: " + e + ", Coordinates: " + this.coordinate);
                return;
            }

            if (this.isEmpty) {
                Packet hexActive = new Packet(this.currHexagon, this.currHexagon, this.currHexagon,
                        null, MessageType.HEX_BECAME_ACTIVE);
                this.networkInterface.longTransmitPacket(hexActive);
            }
        }
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void setNetworkInterface(NetworkInterface networkInterface) {
        this.networkInterface = networkInterface;
    }

    private void dataToTransmit(Packet packet) {
        List<Integer> nonEmptyNeighbours = this.neighbours.keySet().stream()
                .filter(x -> this.neighbours.get(x) && x != packet.getInterSource())
                .collect(Collectors.toList());

        int nextDestination = this.areaManager.getClosestTo(packet.getDestination(), nonEmptyNeighbours);

        packet.setInterSource(this.currHexagon);
        packet.setNextDestination(nextDestination);

        this.transmitAfterTimer(packet);
    }

    private void nodeEntered(Packet packet) {
        Packet p = new Packet(this.currHexagon, this.currHexagon, this.currHexagon, null, MessageType.HEX_NOT_EMPTY);

        this.networkInterface.shortTransmitPacket(p);
    }

    private void nodeLeaving(Packet packet) {
        packet.setMessageType(MessageType.HEX_NOT_EMPTY);

        this.networkInterface.shortTransmitPacket(packet);
    }

    private void transmitAfterTimer(Packet packet) {
        this.processedPackets.add(packet.getId());
        this.scheduledExecutorService.schedule(() -> {
            if (!this.processedPackets.contains(packet.getId())) {
                this.networkInterface.longTransmitPacket(packet);
            } else {
                this.processedPackets.remove(packet.getId());
            }
        }, 100, TimeUnit.MILLISECONDS);
    }
}
