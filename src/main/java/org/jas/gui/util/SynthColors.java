/*
   Copyright 2024 Jose Morales contact@josdem.io

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package org.jas.gui.util;

import java.awt.Color;

import javax.swing.UIManager;

public interface SynthColors {
		Color WHITE255_255_255 = UIManager.getDefaults().getColor("Color.white255_255_255");
		Color CLEAR_GRAY245_245_245 = UIManager.getDefaults().getColor("Color.clearGray245_245_245");
		Color BLUE175_205_225 = UIManager.getDefaults().getColor("Color.blue175_205_225");
		Color GRAY150_150_150 = UIManager.getDefaults().getColor("Color.grid150_150_150");
}
