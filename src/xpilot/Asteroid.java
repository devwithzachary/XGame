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

package xpilot;

import static xpilot.Constants.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import utilities.Vector2D;

/*
 * Asteroid class
 */
public class Asteroid extends GameObject {
	Image im = Constants.ASTEROID1; 
	AffineTransform bgTransf; 
	int[] px;
	int[] py;
	int nPoints;
	double direction, rotation;
	double scale;
	
	/*
	 * Construct a new asteroid using the passed values
	 */
  public Asteroid(double sx, double sy, double vx, double vy) {
	RADIUS = 24;
	scale = 2;
    s = new Vector2D();
    v = new Vector2D(); 
    s.set(sx, sy);
    v.set(vx, vy);
    setPolygon();
    direction += rotation; // start the asteroid spinning
  }

  /*
   * Keep the asteroid moving at its defined speed and keep rotating it
   * (non-Javadoc)
   * @see xpilot.GameObject#update()
   */
  public void update() {
    s.add(v, Constants.DT);
    s.wrap(FRAME_WIDTH,FRAME_HEIGHT);
    direction += rotation;
  }

  /*
   * Draw the polygon that has been randomly generated
   * (non-Javadoc)
   * @see xpilot.GameObject#draw(java.awt.Graphics2D)
   */
  public void draw(Graphics2D g) {
		  AffineTransform at = g.getTransform();
		  g.setColor(Color.white);
		  g.translate(s.x, s.y);
		  g.rotate(direction);
		  g.scale(scale, scale);
		  g.drawPolygon(px, py, nPoints);
		  g.setTransform(at);
  }

  	/*
  	 * (non-Javadoc)
  	 * @see xpilot.GameObject#radius()
  	 */
	int radius() {
		return RADIUS;
	}
	
	/*	
	 * If hit a turret it does not effect the asteroid otherwise we decrease
	 * and finaly kill it after 2 size decreases
	 * (non-Javadoc)
	 * @see xpilot.GameObject#hit(xpilot.GameObject)
	 */
	void hit(GameObject o){
		if (o instanceof Turret) return;
		if (scale == 0.5){
			dead = true;
		}
		else{
			RADIUS = RADIUS/2;
			scale = scale/2;
			setPolygon();
		}
		
	}
	
	/*
	 * Generate a new polygon for the asteroid shape
	 */
	public void setPolygon() {
		  nPoints = MIN_POINTS + (int)(Math.random() * (MAX_POINTS - MIN_POINTS));
		  px = new int[nPoints];
		  py = new int[nPoints];
		  rotation = (Math.random()-0.5) * 2 * MAX_ROTATION;   
		  for (int i = 0; i < nPoints; i++) {
		    // generate vertices within certain ranges in polar coordinates
		    // then transform to cartesian
		    double theta = (Math.PI * 2 / nPoints) * (i + Math.random());
		    double radius = MIN_RADIUS + Math.random() * (MAX_RADIUS - MIN_RADIUS); 
		    px[i] = (int) (radius * Math.cos(theta));
		    py[i] = (int) (radius * Math.sin(theta));
		  }
		}
}