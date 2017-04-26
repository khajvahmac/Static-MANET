package staticmanet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by khajvah on 4/8/17.
 */
public class HexagonAreaManager {

    public HexagonAreaManager()
    {
        this.tRadius = 10;
        this.R = tRadius / Math.abs(13);
        this.X = 100;
        this.Y = 100;

        for(double i = -X; i <= 2 * X; i += 3 * R) {
			for(double j = -Y; j <= 2 * Y; j += R * Math.sqrt(3)) {
				arr.add(new Coordinate(i, j));
			}
		}
		for(double i = -X + R * 3 / 2; i <= 2 * X; i += 3 * R) {
			for(double j = -Y + R * Math.sqrt(3) / 2; j <= 2 * Y; j += R * Math.sqrt(3)) {
				arr.add(new Coordinate(i, j));
			}
		}
		
		Collections.sort(arr, new Comparator<Coordinate>() {
			public int compare(Coordinate o1, Coordinate o2) {
				if(o1.getY() < o2.getY() || (o1.getY() == o2.getY() && o1.getX() < o2.getX()))
                return -1;
		        return 1;
		    }
		});
    }

    /**
     * Get the hexagon id from coordinates.
     * */
    public int getHexagonId(Coordinate coordinate)
    {
        double dist = 100000000;
        int bestHexagonID = -1;

        for(int i = 0; i < arr.size(); i++) {
            if (coordinate.distanceTo(arr.get(i)) <= dist) {
                dist = coordinate.distanceTo(arr.get(i));
                bestHexagonID = i;
            }
        }
        return bestHexagonID;
    }

    /**
     * Get the neighbouring hexagon ids from coordinate
     * */
    public List<Integer> getNeighbourIds(Coordinate coordinate)
    {
        int currentHexagonId = getHexagonId(coordinate);

        List<Integer> neighbours = new ArrayList<>();
              
        neighbours.add(currentHexagonId - 36);
        neighbours.add(currentHexagonId + 36);
        neighbours.add(currentHexagonId - 37);
        neighbours.add(currentHexagonId + 37);
        neighbours.add(currentHexagonId - 73);
        neighbours.add(currentHexagonId + 73);

        return neighbours; 
   }

    /**
     * Returns the closest hexagon to destination from the list of source hexagons.
     * */
    public int getClosestTo(int destination, List<Integer> sources) {
        return 0;
    }

    private int X;
    private int Y;
    private double R;
    private double tRadius;

    private List<Coordinate> arr = new ArrayList<>();

}
