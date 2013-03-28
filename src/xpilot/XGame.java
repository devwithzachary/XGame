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


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

import utilities.JEasyFrame;
import utilities.Vector2D;

//Main Game Class
public class XGame{

	  public static ArrayList<GameObject> objects;
	  public static ArrayList<GameObject> alive;
	  public static ArrayList<GameObject> pending;
	  public static LinkedList<GameObject> particles;
	  static Keys ctrl;
	  static AimNShoot AI;
	  static Ship ship;
	  static Turret ship2;
	  public static int score;
	  public static int level;
	  public static boolean gameover;
	  public static XGame game;
	  
	  //Constructor that starts at level one as well as adding the first levels asteroids and ship
	  public XGame() {
		  objects = new ArrayList<GameObject>();
		  level = 1;
		  gameover = false;
		  for (int i = 0; i < Constants.N_INITIAL_ASTEROIDS; i++) {
			  objects.add(makeAsteroid()); 
		  }
		  
		  ctrl = new Keys();
		  objects.add(ship = new Ship(this, ctrl)); 
		  particles = new LinkedList<GameObject>();

		}

	  public static void main(String[] args) /*throws Exception*/ {
		  pending = new ArrayList<GameObject>();
		  alive = new ArrayList<GameObject>();
		  game = new XGame(); //create new game
		  View view = new View(game);
		  new JEasyFrame(view, "XGame").addKeyListener(game.ctrl); //create frame for game and add key listener
		  score = 0;
		 
		  // run the game
		  while (true) {
		    game.update();
		    view.repaint();
		    game.runLevel();
		    try {
				Thread.sleep(Constants.DELAY); //delay between each loop to give a more constant frame rate
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		  }
	  }
	  /*
	   * Update method that updates the game objects
	   */
	  public void update() {
		  //moves through each game object and if its alive still add it to the alive array
		  for (GameObject object : objects) {
			    object.update();
			    checkCollision(object); //check if the object has colided with anything else
			    if (!object.dead) alive.add(object);
			  }
		  		//move all objects back into the objects array
			  synchronized (this) {
			    objects.clear();
			    objects.addAll(pending);
			    objects.addAll(alive);
			  }
			  //clear the pending and alive arrays for next update
			  pending.clear();
			  alive.clear();
			  updateParticles();
	  }
	  /*
	   * Create a new random asteroid to be added to the game
	   */
	  public Asteroid makeAsteroid(){
		  Asteroid a = new Asteroid((double) Constants.randomGenerator.nextInt(Constants.FRAME_WIDTH), (double) Constants.randomGenerator.nextInt(Constants.FRAME_HEIGHT) , (double) -200 + Constants.randomGenerator.nextInt(400), (double) -200 + Constants.randomGenerator.nextInt(400));
		  return a;
	  }
	  
	  /*
	   * Collision checker that sees if the passed object is currently colliding with another object
	   */
	  public void checkCollision(GameObject object) {
		    // check with other game objects
		    if ( ! object.dead) {
		      for (GameObject otherObject : objects) { 
		    	  //make sure that the two objects are not of the same type, and that they do infact overlap
				if (object.getClass()!= otherObject.getClass() && overlap(object, otherObject)) {
					  if (object instanceof Ship && otherObject instanceof Bullet){ // prevents bullets from the ship killing the ship
						  return;
					  }
					  //Check if the ship has hit the power up and if it does carry out the items hit
			          if (object instanceof UpPower || otherObject instanceof UpPower){
			        	  if (object instanceof Ship || otherObject instanceof Ship){
			        		  object.hit(otherObject);
					          otherObject.hit(object);
					          return;
			        	  }
			        	  else return;
			          }
			          // the object's hit, and the other is also
			          object.hit(otherObject);
			          otherObject.hit(object);
			          //if the hit has caused the object to die create an explosion effect
			          if (object.dead){
			        	  explosion(object.s,100,200); 
			        	  incScore(10);
			          }
			          return; 
			        }
			      }
		    }
		  }
	  
	  /*
	   * Checks if the two game objects are overlapping
	   */
	  public boolean overlap(GameObject x, GameObject y) {
		  // returns true if the distance between their centres is less than 
		  // the sum of the object radii 
		  return x.s.dist(y.s) <= x.radius() + y.radius(); 
		}
	  
	  /*
	   * Returns the current score of the game
	   */
	  public int getScore(){
		return score;
	  }
	  /*
	   * Increments the score by amount i
	   */
	  public void incScore(int i){
		  score = score + i;
	  }
	  
	  /*
	   * Method to create new levels when all Asteroids and turrets are destroyed 
	   */
	  public void runLevel(){
		  boolean asteroids = false;
		  boolean turrets = false;
		  int numasteroids = 0;
		  int numturrets = 0;
		  if (Ship.lives == 0) gameover = true; // if the ship has run out of lives we change the game to game over
		  for (GameObject object : objects) { // check if there are any asteroids or turrets left
			    if  (object instanceof Asteroid) asteroids = true; 
			    if  (object instanceof Turret) turrets = true;
		  } 
		  //if there is no more astroids or turrets in the game world we start a new level
		  if (!asteroids && !turrets){
			  for (GameObject object : objects) { 
				  if (object instanceof Ship) object.reset(); // reset the ship to its start position
			  }
			  numasteroids = Constants.N_INITIAL_ASTEROIDS + level;
			  level++; 
			  for (int i = 0; i < numasteroids; i++) { // add the new amount of astroids to the game world
				  objects.add(makeAsteroid()); 
			  }
			  //Randomly drop power ups at the start of the level
			  int q = Constants.randomGenerator.nextInt(1000);
			  if (q < 200) objects.add(new UpDoubleFire(Constants.randomGenerator.nextInt(Constants.FRAME_WIDTH), Constants.randomGenerator.nextInt(Constants.FRAME_HEIGHT)));
			  if (200 < q && q > 250) objects.add(new UpBeamGun(Constants.randomGenerator.nextInt(Constants.FRAME_WIDTH), Constants.randomGenerator.nextInt(Constants.FRAME_HEIGHT)));
			  if (250 < q && q > 500) objects.add(new UpLife(Constants.randomGenerator.nextInt(Constants.FRAME_WIDTH), Constants.randomGenerator.nextInt(Constants.FRAME_HEIGHT)));
			  if (500 < q && q > 700) objects.add(new UpLongGun(Constants.randomGenerator.nextInt(Constants.FRAME_WIDTH), Constants.randomGenerator.nextInt(Constants.FRAME_HEIGHT)));
			  if (700 < q && q < 1000) objects.add(new UpSheild(Constants.randomGenerator.nextInt(Constants.FRAME_WIDTH), Constants.randomGenerator.nextInt(Constants.FRAME_HEIGHT)));
			  //every other level add a new extra turret to the game world
			  if (level%2 == 0){
				  numturrets = level/2;
				  for (int i = 0; i < numturrets; i++){
					  Turret t = new Turret(game ,Constants.randomGenerator.nextInt(Constants.FRAME_WIDTH), Constants.randomGenerator.nextInt(Constants.FRAME_HEIGHT));
					  AimNShoot AI = new AimNShoot(t);
					  t.ctrl = AI;
					  objects.add(t);
				  }
			  }
		  }
	  }
	  
	  /*
	   * Explosion method that creates an explosion effect
	   */
	  public void explosion(Vector2D s, int n, int ttl) {
		    for (int i = 0; i < n; i++) 
		      particles.add(new Particle(this, s, (1 + Math.random() * ttl )));
		}
	  
	  /*
	   * Updates the particles from the explosion
	   */
	  public void updateParticles() {
		    // iterate over the set of particles, removing any dead ones
		    ListIterator<GameObject> it = particles.listIterator();
		    synchronized (this) {
		    while (it.hasNext()) {
		      Particle p = (Particle) it.next();
		      p.update();
		      if (p.dead) {
		        it.remove();
		      }
		     }
		    }
		  }
	 
	/*
	 * return the currently alive game objects
	 */
	public ArrayList<GameObject> getGameObjects() {
		return alive;
	}
	
	/*
	 * return the current particles
	 */
	public LinkedList<GameObject> getParticles() {
		return particles;
	}

	}
