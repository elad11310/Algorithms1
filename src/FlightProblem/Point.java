package FlightProblem;

public class Point {
    private double x;
    private double y;

    public Point(double x, double y){
        setX(x);
        setY(y);
    }

    public double getX(){
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
    public String toString(){
        return "X: " + this.x + " Y: " + this.y;

    }
}
