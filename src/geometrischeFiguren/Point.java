/* ------------------------------------------------------------------
# This library is free software; you can redistribute it and/or
# modify it under the terms of the GNU Lesser General Public License
# License as published by the Free Software Foundation; either
# version 3 of the License, or (at your option) any later version.s
# ------------------------------------------------------------------
*/
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
