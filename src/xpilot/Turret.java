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
 * Turret class for the enemy turrets
 */
public class Turret extends GameObject {
    static final double STEER_RATE = 2* Math.PI;  // in radians per second
    static final Color COLOR = Color.red;
    static final double SCALE = 1.5;
    //Cor-Ords to draw the turret
    int[] XP = { -6,  -6,  -2,  -2,  2,   2,   6,  6,  12, 12, 4, -4, -12, -12, -6};
    int[] YP = {-10, -28, -28, -12, -12, -28, -28, -10, -4, 4, 12,  12,  4, -4, -10};
    long time;
	public Controller ctrl;
    int shootdelay, bulletlife, xstart, ystart;

    /*
     * Turret constructor that will create a new turret to be spawned at a given position
     */
    public Turret(XGame game, int x, int y) {
    	RADIUS = 9;
    	shootdelay = 500; // amount of time in milliseconds between bullets
    	bulletlife = 25;
    	dead = false;
      s = new Vector2D();
      v = new Vector2D();
      d = new Vector2D(2,2); 
      xstart = x;
      ystart = y;
      this.game = game;
      reset();
    }

    /*
     * Move the turret to the given position
     * (non-Javadoc)
     * @see xpilot.GameObject#reset()
     */
    public void reset() {
    	s.set(xstart, ystart);
    	shootdelay=500;
    	bulletlife = 20;
    }
    /*
     * create a new bullet with the current bullet life at the position of the turret
     */
    public void shoot(){
    	game.pending.add(new TBullet(this, bulletlife));	
    }
    /*
     * (non-Javadoc)
     * @see xpilot.GameObject#update()
     */
    public void update() {
    	Action action = ctrl.action(game);
    	d.rotate(action.turn*STEER_RATE*Constants.DT);
    	if (action.shoot == true) {
    			if (System.currentTimeMillis()-time > shootdelay){
        			time = System.currentTimeMillis();
        			shoot();
        		}				
    	}
    }
    /*
     * Draw the turret
     * (non-Javadoc)
     * @see xpilot.GameObject#draw(java.awt.Graphics2D)
     */
    public void draw(Graphics2D g) {
    	AffineTransform at = g.getTransform();
        g.translate(s.x, s.y);
        double rot = d.theta() + Math.PI / 2;
        
        g.rotate(rot);
        g.scale(SCALE, SCALE);
    	
        g.setColor(COLOR);
        g.fillPolygon(XP, YP, XP.length);
        
        g.setTransform(at);
        
       
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
	 * If a Asteroid hit the turret we just return, otherwise the turret is killed
	 * (non-Javadoc)
	 * @see xpilot.GameObject#hit(xpilot.GameObject)
	 */
	void hit(GameObject o){
		if (o instanceof Asteroid) return;
		else  dead = true;
	}

}
