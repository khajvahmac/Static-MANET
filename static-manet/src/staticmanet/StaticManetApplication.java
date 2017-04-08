package staticmanet;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class StaticManetApplication implements Application{

    private final HexagonAreaManager areaManager;
    private int currHexagon;
    private NetworkInterface networkInterface;
    private static Logger logger = Logger.getLogger("StaticManet");
    private Coordinate coordinate;
    private Map<Integer, Boolean> neighbours;

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
        }
    }

    public void nodeMoving(Coordinate newCoordinates) {
        if (this.currHexagon != this.areaManager.getHexagonId(newCoordinates)) {
            //Do something TODO:
        }
    }

    private void dataToTransmit(Packet packet) {
        List<Integer> nonEmptyNeighbours = this.neighbours.keySet().stream()
                .filter(x -> this.neighbours.get(x) && x != packet.getInterSource())
                .collect(Collectors.toList());

        int nextDestination = this.areaManager.getClosestTo(packet.getDestination(), nonEmptyNeighbours);

        packet.setInterSource(this.currHexagon);
        packet.setNextDestination(nextDestination);

        this.networkInterface.longTransmitPacket(packet);
    }

    private void nodeEntered(Packet packet) {
        Packet p = new Packet(this.currHexagon, this.currHexagon, this.currHexagon, null, MessageType.HEX_NOT_EMPTY);

        this.networkInterface.shortTransmitPacket(p);
    }

    private void nodeLeaving(Packet packet) {
        packet.setMessageType(MessageType.HEX_NOT_EMPTY);

        this.networkInterface.shortTransmitPacket(packet);
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

}
