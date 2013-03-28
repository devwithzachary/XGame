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
import java.awt.geom.AffineTransform;

import utilities.Vector2D;

/*
 * Bullet class for the Ships bullets 
 */
public class Bullet extends GameObject{
	int lifetime;

	//Create a bullet at the position of the ship that fired it
	//and keep it alive for the amount of time given
	public Bullet(GameObject ship, int life) {
		dead = false;
	    s = new Vector2D(ship.s.x, ship.s.y);
	    v = new Vector2D();
	    v.add(ship.d, 200);
	    lifetime = life;
	}
	/*
	 * Move the bullet at the constant velocity and decrease its life
	 * (non-Javadoc)
	 * @see xpilot.GameObject#update()
	 */
	@Override
	void update() {
		s.add(v, Constants.DT);
		 if(lifetime > 0)lifetime--;
		 else dead = true;
	}
	
	/*
	 * Draw class for the bullet that just draws a green line
	 * (non-Javadoc)
	 * @see xpilot.GameObject#draw(java.awt.Graphics2D)
	 */
	@Override
	void draw(Graphics2D g) {
		AffineTransform at = g.getTransform();
	    g.translate(s.x, s.y);
        double rot = v.theta() + Math.PI / 2;
        g.rotate(rot);
        g.scale(1, 1);
	    g.setColor(Color.green);
	    g.drawLine(0,0,0,2);
	    g.setTransform(at);
	}
	/*
	 * (non-Javadoc)
	 * @see xpilot.GameObject#radius()
	 */
	@Override
	int radius() {
		return 2;
	}


}
