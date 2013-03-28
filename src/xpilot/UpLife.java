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

import utilities.Vector2D;

/*
 * Power up class that gives the ship an extra life
 */
public class UpLife extends UpPower {
	
	/*
	 * Constructor that sets the colour of the power up
	 */
	public UpLife(double sx, double sy){
		lifetime = 1000;
		c = Color.white;
		 s = new Vector2D();
		 s.add(sx, sy);
	}
	
	/*
	 * Update method just checks to see if the power up needs to die, if not decrease its life
	 * (non-Javadoc)
	 * @see xpilot.GameObject#update()
	 */
	public void update() {
		if (lifetime < 1){
			dead = true;
		}
		else lifetime--;
	}

	/*
	 * (non-Javadoc)
	 * @see xpilot.GameObject#radius()
	 */
	@Override
	int radius() {
		return RADIUS;
	}
	
	/*
	 * If the power up was hit by a ship we add an extra life to the ship
	 * (non-Javadoc)
	 * @see xpilot.UpPower#hit(xpilot.GameObject)
	 */
	@Override
	void hit(GameObject o) {
		if (o instanceof Ship){
			((Ship) o).lives += 1;
			dead = true;
		}
		
	}
}
