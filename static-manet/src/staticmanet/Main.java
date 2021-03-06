package staticmanet;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Created by kv on 4/23/17.
 */
public class Main {

    private static Logger logger = Logger.getLogger("main");
    private static ExecutorService es = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        Network network = new Network();

        try {
            Scanner lineReader = new Scanner(new File("/Users/kv/uni/static-manet/static-manet/production/staticmanet/graph.dat"));
            String firstLine = lineReader.nextLine();
            String[] firstWords = firstLine.split(" ");
            HexagonAreaManager areaManager = new HexagonAreaManager();

            for (int i = 0; i < Integer.parseInt(firstWords[1]); i++ ) {
                String[] coordinateString = lineReader.nextLine().split(" ");
                logger.info(coordinateString[0] + " " + coordinateString[1]);

                Coordinate coordinate = new Coordinate(Double.parseDouble(coordinateString[0]), Double.parseDouble(coordinateString[1]));
                Application application = new StaticManetApplication(coordinate, areaManager);
                Node node = new Node(i, coordinate, application, network, es);
                network.addNode(node);
            }

            for (Node node : network.getNodes()) {
                node.getApplication().initialize();
            }


            Node sourceNode = network.getNodes().get(1);
            Node targetNode = network.getNodes().get(network.getNodes().size() - 1);
            Map<String, String> data = new HashMap<String, String>() {{
                this.put("Stuff", "SomeStuff");
            }};
            Packet packet = new Packet(((StaticManetApplication)sourceNode.getApplication()).getCurrentHexagon(),
                    ((StaticManetApplication)targetNode.getApplication()).getCurrentHexagon(),
                    ((StaticManetApplication)targetNode.getApplication()).getCurrentHexagon(),
                    data, MessageType.DATA_TO_TRANSMIT);

            ((StaticManetApplication)sourceNode.getApplication()).dataToTransmit(packet);

        } catch (IOException e) {
            logger.severe("Cannot read graph text. " + e);
        } catch (NoSuchElementException e) {
            logger.severe("File is empty. " + e);
        }
    }
}
