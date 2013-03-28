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
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.util.Random;

import utilities.ImageManager;

public class Constants {
	  // background colour 
	  public static final Color BG_COLOR = Color.black;
	  public static final Color TEXT_COLOR = Color.green;
	  public static final Color GAME_COLOR = Color.red;
	  // frame dimensions 
	  public static final int FRAME_HEIGHT = 600;
	  public static final int FRAME_WIDTH = 800;
	  public static final Dimension FRAME_SIZE = 
	    new Dimension(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
	  // constants relating to frame rate 
	  public static final int DELAY = 20;
	  public static final double DT = DELAY / 1000.0;
	  // number of asteroids on 1st level 
	  public static final int N_INITIAL_ASTEROIDS = 1;
	  // constant related to max velocity of asteroid
	  public static final double VMAX = 100;
	  public static Image ASTEROID1, MILKYWAY1;
	  
	  //Number of points an asteroid can be made from
	  public static int MIN_POINTS = 8;
	  public static int MAX_POINTS = 15;
	  //Max rotation of the asteroid
	  public static double MAX_ROTATION = 0.15;
	  public static double MIN_RADIUS = 10;
	  public static double MAX_RADIUS = 20;
	  
	  static Random randomGenerator = new Random();
	  
	  static {
	    try {
	      MILKYWAY1 = ImageManager.loadImage("milkyway1");
	    } catch (IOException e) { System.exit(1); }
	  }
}