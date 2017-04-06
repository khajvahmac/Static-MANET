package staticmanet;

public class Coordinate {
    private double x;
    private double y;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @param coordinate Coordinate to calculate distance to.
     * */
    public double distanceTo(Coordinate coordinate) {
        double diffX = coordinate.getX() - this.getY();
        double diffY = coordinate.getY() - this.getY();

        return Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));
    }

    public double getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
