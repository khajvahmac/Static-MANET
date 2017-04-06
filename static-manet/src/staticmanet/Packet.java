package staticmanet;

public class Packet {
    private int source;
    private int destination;
    private int interSource;
    private MessageType messageType;

    public Packet(int source, int destination, int interSource, Object data, MessageType type) {
        this.source = source;
        this.destination = destination;
        this.interSource = interSource;
        this.data = data;
        this.messageType = messageType;
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

    private Object data;
}
