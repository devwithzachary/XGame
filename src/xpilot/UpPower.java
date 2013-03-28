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
import java.awt.Image;
import java.awt.geom.AffineTransform;

/*
 * Class that all power ups must extend which in tern extends GameObject itself
 */
public abstract class UpPower extends GameObject{
	int lifetime;
	Color c;
	int RADIUS = 10; // All power ups have the same radius
	
	/*
	 * (non-Javadoc)
	 * @see xpilot.GameObject#hit(xpilot.GameObject)
	 */
	abstract void hit(GameObject o);
	
	/*
	 * All power ups have the same draw class just different colours
	 * (non-Javadoc)
	 * @see xpilot.GameObject#draw(java.awt.Graphics2D)
	 */
	public void draw(Graphics2D g) {
		int x = (int) s.x;
		int y = (int) s.y;
		g.setColor(c);
		g.fillOval(x - RADIUS, y - RADIUS, 2 * RADIUS, 2 * RADIUS);
		AffineTransform bgTransf = new AffineTransform(); 
	}

}
