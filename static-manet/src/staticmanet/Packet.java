package staticmanet;

public class Packet {
    private int source;
    private int destination;
    private int interSource;
    private MessageType messageType;
    private int nextDestination;
    private Object data;

    public Packet(int source, int destination, int interSource, Object data, MessageType type) {
        this.source = source;
        this.destination = destination;
        this.interSource = interSource;
        this.data = data;
        this.messageType = messageType;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public int getNextDestination() {
        return nextDestination;
    }

    public void setNextDestination(int nextDestination) {
        this.nextDestination = nextDestination;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getInterSource() {
        return interSource;
    }

    public void setInterSource(int interSource) {
        this.interSource = interSource;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Packet{" +
                "source=" + source +
                ", destination=" + destination +
                ", interSource=" + interSource +
                ", messageType=" + messageType +
                ", data=" + data +
                '}';
    }
}
