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

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/*
 * Controller class for getting player input via the keyboard
 */
public class Keys extends KeyAdapter implements Controller  {
	  Action action;
	  public Keys() {
	    action = new Action();
	  }

	  public Action action() {
	    // this is defined to comply with the standard interface
	    return action;
	  }
	  
	/*
	 * Check if any of the bound keys have been pressed
	 * (non-Javadoc)
	 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
	    int key = e.getKeyCode();
	    switch (key) {
	    case KeyEvent.VK_UP: //if the up arrow key that was pressed we thrust
	      action.thrust = 1;
	      break;
	    case KeyEvent.VK_LEFT: //if left arrow key is pressed we turn anti-clockwise
	      action.turn = -1;
	      break;
	    case KeyEvent.VK_RIGHT: //if right arrow key is pressed we turn clockwise
	      action.turn = +1;
	      break;
	    case KeyEvent.VK_SPACE: //if space bar is pressed we are firing
	      action.shoot = true;
	      break;
	    case KeyEvent.VK_S:
	      action.shield = true; //if the S key is pressed we turn on the shield
	      break;
	    }
	  }

	  public void keyReleased(KeyEvent e) {
		  int key = e.getKeyCode();
		    switch (key) {
		    case KeyEvent.VK_UP:
		      action.thrust = 0;
		      break;
		    case KeyEvent.VK_LEFT:
		      action.turn = 0;
		      break;
		    case KeyEvent.VK_RIGHT:
		      action.turn = 0;
		      break;
		    case KeyEvent.VK_SPACE:
		      action.shoot = false;
		      break;
		    case KeyEvent.VK_S:
			      action.shield = false;
			      break;
		    }
	  }
	  
	  public Action action(XGame game) { return action; }
}