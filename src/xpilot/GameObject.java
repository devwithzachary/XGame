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

import java.awt.Graphics2D;

import utilities.Vector2D;


/*
 * Main game object class that all game objects must extend from
 */
public abstract class GameObject {
	Vector2D s, v, d;
	XGame game;
	boolean dead;
	int RADIUS; // Used for collision area of the game object
	
	public GameObject(){
		
	}
	
	public GameObject(XGame game, Vector2D vs, Vector2D vx){
		game = game;
		s = vs;
		v = vx;
	}
	
	/*
	 * If the Game Object is hit we change it to dead so the main class can clear it away
	 */
	void hit(GameObject o){
		dead = true;
	}
	/*
	 * Update method to make changes to game object at each frame
	 */
	abstract void update();
	/*
	 * Draw method that is used to draw the Game Object within the game view
	 */
	abstract void draw(Graphics2D g);
	
	/*
	 * Returns the radius of the game object collision area 
	 */
	abstract int radius();

	/*
	 * reset the game object
	 */
	public void reset() {		
	}
	//difference vector between the positions of two game objects
	public Vector2D to(Vector2D v) {
        return new Vector2D(v.x-s.x, v.y-s.y);
    }
	//distance between two postion vectors
	public double dist(GameObject o){
		return this.s.dist(o.s);
	}
}
