package staticmanet;

import java.util.Map;
import java.util.logging.Logger;

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
    }

    public void receivePacket(Packet packet) {
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
            //Do something
        }
    }

    private void dataToTransmit(Packet packet) {

    }

    private void nodeEntered(Packet packet) {

    }

    private void nodeLeaving(Packet packet) {

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
