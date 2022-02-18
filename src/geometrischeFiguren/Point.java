package geometrischeFiguren;

public class Point  {
	
	double x;
	double y;
	
	String name; 
	
	public Point(String name, double x, double y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public String getName() {
		return this.name;
	}
	

}
