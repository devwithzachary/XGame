/* Code based on code from CE218 - University of Essex Copyrighted 
 * Including code from Dr.-Ing. Norbert Völker http://dces.essex.ac.uk/staff/norbert/
 * And Professor Simon M. Lucas (SMIEEE) http://dces.essex.ac.uk/staff/sml/lucas.htm
 * 
 * 
 * Copyright 2013 Zachary Powell 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.*/

package utilities;
//mutable 2D vectors
public final class Vector2D {

	//fields
	public double x, y; 
	
	//construct a null vector
	public Vector2D(){
	   this.x = 0;
	   this.y = 0; 
	} 
	
	//construct a vector with given coordinates
	public Vector2D(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	//construct a vector that is a copy of the argument
	public Vector2D(Vector2D v){
		this.x = v.x;
		this.y = v.y;
	}
	
	//set coordinates 
	public void set (double x, double y) {
		this.x = x;
		this.y = y;
	} 
	
	//set coordinates to argument vector coordinates
	public void set (Vector2D v) {
		this.x = v.x;
		this.y = v.y;
	}
	
	//compare for equality (needs to allow for Object type argument...) 
	public boolean equals(Object o) {
		if (o instanceof Vector2D){
			Vector2D v = (Vector2D) o;
			return this.x == v.x && this.y == v.y;
		}
		return false;
	}
	
	//magnitude (= "length") of this vector 
	public double mag() {
		return Math.hypot(this.x, this.y);
	}
	
	//angle between vector and horizontal axis in radians
	public double theta() {
		return Math.atan2(this.y, this.x);
	}
	
	//String for displaying vector as text 
	public String toString() {
		return "This vector has the X cord: " + this.x + "And Y cord: " + this.y;
	}
	
	//add argument vector 
	public void add(Vector2D v) {
		this.x += v.x;
		this.y += v.y;
	}
	
	//add coordinate values 
	public void add(double x, double y) {
		this.x += x;
		this.y += y;		
	}
	
	//weighted add - frequently useful 
	public void add(Vector2D v, double fac) {
		this.x += (v.x*fac);
		this.y += (v.y*fac);
	}
	
	//substract argument vector 
	public void subtract(Vector2D v) {
		this.x -= v.x;
		this.y -= v.y;
	}
	
	//subtract coordinate values
	public void subtract(double x, double y) {
		this.x -= x;
		this.y -= y;
	}
	
	//multiply with factor 
	public void mult(double fac) {
		this.x = this.x*fac;
		this.y = this.y*fac;
	}
	
	//"wrap" vector with respect to given positive values w and h
	//method assumes that x >= -w and y >= -h
	public void wrap(double w, double h) {
		this.x = (x+w)%w;
		this.y = (y+h)%h;
	}
	
	//rotate by angle given in radians 
	public void rotate(double theta) {
		double a = this.x;
		double b = this.y;
		this.x = a*Math.cos(theta) - b*Math.sin(theta);
		this.y = a*Math.sin(theta) + b*Math.cos(theta);
	}
	
	//scalar product with argument vector 
	public double scalarProduct(Vector2D v) {
		return this.x * v.x + this.y * v.y;
	}
	
	//distance to argument vector 
	public double dist(Vector2D v) {
		return  new Vector2D(v.x - this.x, v.y - this.y).mag();
	}
	
	//normalise vector so that mag becomes 1
	//direction is unchanged  
	public void normalise() {
		this.mult(1/this.mag());
	}
}