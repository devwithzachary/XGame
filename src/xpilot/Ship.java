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
 * Ship class for the players ship
 */
public class Ship extends GameObject {
    static final double STEER_RATE = 2* Math.PI;  // in radians per second
    static final double LOSS = 0.99;
    static final double SHIP_VMAX = 200;
    static final Color COLOR = Color.cyan;
    static final double SCALE = 1.5;
    boolean thrusting = false;
    //Co-ords to draw ship
    int[] XP = {0, 8, 0, -8};
    int[] YP = {0, 8, -8, 8};
    //Co-Ords for the ships thruster
    int[] XPTHRUST = {0, 8, 5, 6, 4, 5, 3, 4, 2, 3, 1, 2, 0, -2, -1, -3, -2, -4, -3, -5, -5, -6, -5, -8, 0};  
    int[] YPTHRUST = {0, 10, 7, 10, 7, 11, 7, 12, 7, 12, 7, 12, 7, 12, 7, 12, 7, 12, 7 , 11, 7 ,10, 7, 10, 0};
    int[] XPTHRUSTIN = {0, 4, 2, 3, 1, 2, 0, -2, -1, -3, -2, -4, 0};  
    int[] YPTHRUSTIN = {0, 5, 3, 5, 3, 5, 3, 6, 0, 6, 3, 5, 3, 5, 3, 5, 0};
    long time;
	private Controller ctrl;
    static int lives, spower;
    boolean shield;
    int shootdelay, bulletlife;

    /*
     * Ship constructor to create a new ship for the passed game with the passed controller
     */
    public Ship(XGame game, Controller ctrl) {
    	RADIUS = 8;
    	lives = 5;
    	spower = 100;
    	shootdelay = 500; // amount of time in milliseconds between bullets
    	bulletlife = 25;  
      s = new Vector2D();
      v = new Vector2D();
      d = new Vector2D(2,2); 
      this.game = game;
      this.ctrl = ctrl;
      reset(); // move the ship to its start position
    }

    /*
     * Change the delay and bullet life back to its start values
     * (non-Javadoc)
     * @see xpilot.GameObject#reset()
     */
    public void reset() {
    	s.set(Constants.FRAME_WIDTH/2, Constants.FRAME_HEIGHT/2);
    	shootdelay=500;
    	bulletlife = 20;
    }
    
    /*
     * create a new bullet with the current bullet life at the position of the ship
     */
    public void shoot(){
    	game.pending.add(new Bullet(this, bulletlife));	
    }
    
    /*
     * (non-Javadoc)
     * @see xpilot.GameObject#update()
     */
    public void update() {
    	Action action = ctrl.action(game); // get the current action
    	d.rotate(action.turn*STEER_RATE*Constants.DT); // rotate the ship if the action says to do so
    	v.add(d, action.thrust*SHIP_VMAX*Constants.DT); // move the ship forward
    	v.mult(LOSS); //simulate drag on the ship
    	s.add(v, Constants.DT);
    	s.wrap(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT); // Used to wrap the ship back round the map if it goes off the edge
    	
    	//If the shield is turned on and it still has power we set the shield to on and decrease the power
    	if (action.shield && spower > 0){
    		shield = true;
    		if (System.currentTimeMillis()-time > 50){
    			time = System.currentTimeMillis();
    			spower--;
    		}	
    	}
    	else{
    		shield = false;
    	}
    	
    	//if the player is thrusting we set it to thrusting
    	if (action.thrust != 0) thrusting = true;
    	else thrusting = false;
    	
    	//if the player is shooting we fire a new bullet after waiting the delay
    	if (action.shoot == true) {
    		if (!shield){
    			if (System.currentTimeMillis()-time > shootdelay){
        			time = System.currentTimeMillis();
        			shoot();
        		}
    		}				
    	}
    }
    
    /*
     * Draw the ship
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
        //If the shield is active we draw it to
        if (shield) {
        	g.setColor(Color.red);
        	g.drawOval(-13, -10, 25, 25);
        }
        //If the ship is trusting we draw the thruster
        if (thrusting) {
          g.setColor(Color.red);
          g.fillPolygon(XPTHRUST, YPTHRUST, XPTHRUST.length);
          g.setColor(Color.yellow);
          g.fillPolygon(XPTHRUSTIN, YPTHRUSTIN, XPTHRUSTIN.length);
        }
        g.setTransform(at);
        
       
      }

	@Override
	/*
	 * (non-Javadoc)
	 * @see xpilot.GameObject#radius()
	 */
	int radius() {
		return RADIUS;
	}
	/*
	 * Check if what we have hit should take a life or do something else
	 * (non-Javadoc)
	 * @see xpilot.GameObject#hit(xpilot.GameObject)
	 */
	void hit(GameObject o){
		if (!shield){ // if sheild is on all hits do nothing
			if (!(o instanceof UpPower)){ // if didnt hit a power up the ship loses a life
				if (lives > 1) {
					lives--;
					reset();
				}
				else { // once there is no life left the ship dies
					dead = true;
					lives--;
				}	
			}
			
		}
	}

}
