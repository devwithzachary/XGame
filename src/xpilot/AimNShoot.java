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

import utilities.Vector2D;

/*
 * AimNShoot controller AI used by the turret
 */
public class AimNShoot implements Controller {

	  public static final double SHOOTING_DISTANCE = 150;
	  public static final double SHOOTING_ANGLE = Math.PI / 12;
	  GameObject target;
	  Action action = new Action();
	  Turret ship;
	  
	  //Link the turret to the controller
	  public AimNShoot(Turret t){
		  ship = t;
	  }
	  
	  @Override
	  /*
	   * Action method to work out what the AI needs to do
	   * (non-Javadoc)
	   * @see xpilot.Controller#action(xpilot.XGame)
	   */
	  public Action action(XGame game) {
	    GameObject nextTarget = findTarget(game.getGameObjects()); 
	    if (nextTarget == null)  //if there is no target we just return a blank action
	      return new Action(); 
	    switchTarget(nextTarget); //change to the found target
	    aim(); //move the turret to face target
	    action.shoot = ((Math.abs(angleToTarget()) < SHOOTING_ANGLE) 
	                      && inShootingDistance()); //shoot at the target once its in range
	    return action;
	  }
	  
	  /*
	   * Method to find next target for AI
	   */
	  public GameObject findTarget(Iterable<GameObject> gameObjects){
		  double minDistance = 2 * SHOOTING_DISTANCE;
		  GameObject closestTarget = null;
		  for (GameObject obj : gameObjects) {
		    if (!(obj instanceof Ship)) // If the closest target is not a ship we move to the next game object
		      continue;
		    double dist = ship.dist(obj); // find the distance to the object
		    if (dist < minDistance) { // so long as this is within out shooting distance we set this as the target
		      closestTarget = obj;
		      minDistance = dist;
		    }
		  }
		  return closestTarget; 
		}
	  
	  /*
	   * Find the angle between the target and turret
	   */
	  public double angleToTarget() {
		  
		  Vector2D v = ship.to(target.s);
	        v.rotate(-ship.d.theta());
	        return v.theta();
		}
	  
	  /*
	   * Work out the distance between the two game object
	   */
	  public boolean inShootingDistance() {
		  //return  ship.dist(target) < SHOOTING_DISTANCE + target.radius(); 
		  return  ship.s.dist(target.s) < SHOOTING_DISTANCE + target.radius();
	  }
	  	
	  /*
	   * Move the turret to face the target
	   */
		public void aim() {
		  double angle = angleToTarget();
		  action.turn = (int) Math.signum(Math.sin(angle));
		}
		   
		/*
		 * Move to the new target
		 */
		public void switchTarget(GameObject newTarget) {
		  target = newTarget;
		}
}