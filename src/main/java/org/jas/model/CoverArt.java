/*
   Copyright 2013 Jose Luis De la Cruz Morales joseluis.delacruz@gmail.com

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

package org.jas.model;

import java.awt.Image;

public class CoverArt {
	private final Image image;
	private final CoverArtType type;

	public CoverArt(Image image, CoverArtType type){
		this.image = image;
		this.type = type;
	}

	public Image getImageIcon() {
		return image;
	}

	public CoverArtType getType() {
		return type;
	}

}
