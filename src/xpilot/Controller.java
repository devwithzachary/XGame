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

//Simple controller interface to allow multiple controllers
public interface Controller {
	  public Action action(XGame game);
	}