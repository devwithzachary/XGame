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

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

/*
 * Class to update the game view
 */
public class View extends JComponent {
	XGame game;
	Image im = Constants.MILKYWAY1; 
	AffineTransform bgTransf; 

	/*
	 * The view class constructor that create the background image
	 */
	public View(XGame game) {
	    this.game = game;
	    double imWidth = im.getWidth(null); 
	    double imHeight = im.getHeight(null); 
	    double stretchx = (imWidth > Constants.FRAME_WIDTH? 1 : 
	                                Constants.FRAME_WIDTH/imWidth); 
	    double stretchy = (imHeight > Constants.FRAME_HEIGHT? 1 : 
	                                Constants.FRAME_HEIGHT/imHeight);
	    bgTransf = new AffineTransform(); 
	    bgTransf.scale(stretchx, stretchy);
	}

	@Override
	/*
	 * painComponet method that draws the current game view, as long as the game is not over we call all the draw methods of the objects
	 * Once we hit game over we display the game over text
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g0) {
	    Graphics2D g = (Graphics2D) g0; 
	    g.drawImage(im, bgTransf,null); 

	    if (!game.gameover){
	    	for (GameObject object : game.objects) object.draw(g);
	    	for (GameObject object : game.particles) object.draw(g);
		    g.setColor(Constants.TEXT_COLOR);
		    String s = "Score: " + game.getScore() +"\nLives: " + game.ship.lives + "\nLevel: " + game.level + "\nShield Power: " + game.ship.spower ;
		   drawString(g, s, 25, 25);  	
	    }
	    else {
	    	g.setColor(Constants.GAME_COLOR);
		    String s = "GAME OVER";
		    String s1 = "Your final score was: " + game.getScore() + " You reached level: " + game.level;
		    Font font = new Font("Arial", Font.PLAIN, 50);
		    g.setFont(font);
		    g.drawString(s, 250, Constants.FRAME_HEIGHT/2);
		    Font font1 = new Font("Arial", Font.PLAIN, 30);
		    g.setFont(font1);
		    g.drawString(s1, 100, Constants.FRAME_HEIGHT/2+40);
	    }
	}
	
	/*
	 * Draw string method to split a string into lines where ever a new line chracter is entered
	 */
	private void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }
	@Override
	public Dimension getPreferredSize() {
	    return Constants.FRAME_SIZE; 
	}
}