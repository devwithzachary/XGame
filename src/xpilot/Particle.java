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

import java.awt.Color;
import java.awt.Graphics2D;

import utilities.Vector2D;

/*
 * Particle class for use with explosions
 */
public class Particle extends GameObject {
	  public static final int RADIUS = 1;
	  public static final Color COLOR = Color.white; 
	  double ttl;
	  
	  /*
	   * Create a new particle 
	   */
	  public Particle(XGame game, Vector2D s, double ttl) {
	    super(game, new Vector2D(s), new Vector2D(Constants.randomGenerator.nextGaussian(), Constants.randomGenerator.nextGaussian()));
	    this.ttl = ttl;
	  }

	  /*
	   * 
	   * (non-Javadoc)
	   * @see xpilot.GameObject#update()
	   */
	  @Override
	  public void update() {
	    s.add(v);
	    v.mult(0.99);
	    ttl--;
	    if (ttl < 0) dead = true; 
	  }

	  /*
	   * (non-Javadoc)
	   * @see xpilot.GameObject#draw(java.awt.Graphics2D)
	   */
	  @Override
	  void draw(Graphics2D g) {
	    g.setColor(COLOR);
	    g.fillOval((int) s.x - RADIUS, (int) s.y - RADIUS, 
	                2 * RADIUS, 2 * RADIUS);
	  }

	  /*
	   * (non-Javadoc)
	   * @see xpilot.GameObject#radius()
	   */
	  @Override
	  public int radius() {
	    return RADIUS;
	  }

	}