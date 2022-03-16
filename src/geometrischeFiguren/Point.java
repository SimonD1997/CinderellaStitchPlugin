/* ------------------------------------------------------------------
# This library is free software; you can redistribute it and/or
# modify it under the terms of the GNU Lesser General Public License
# License as published by the Free Software Foundation; either
# version 3 of the License, or (at your option) any later version.s
# ------------------------------------------------------------------
*/
package geometrischeFiguren;

/**
 * Alle Punkte aus Cinderella sollen durch diese Klasse instaziiert werden. 
 * Definiert ist ein Punkt durch x und y Koordinate.
 * @author Simon Doubleday
 *
 */
public class Point  {
	
	double x;
	double y;
	
	String name; 
	
	/**
	 * Constructor
	 * @param name Name des Punkts
	 * @param x Koordinate als double
	 * @param y Koordinate als double
	 */
	public Point(String name, double x, double y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	//Getter und Setter
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
