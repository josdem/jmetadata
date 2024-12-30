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

package com.josdem.jmetadata.laf.painter;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JSlider;
import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthPainter;

public class ProgresSliderPainter extends SynthPainter {
	Color color = new Color(0xaa, 0xaa, 0xaa);

	@Override
	public void paintSliderTrackBackground(SynthContext context, Graphics g, int x, int y, int w, int h, int orientation) {
		JSlider progressSlider = (JSlider) context.getComponent();
		double currentValue = progressSlider.getValue();
		double maximun = progressSlider.getMaximum();
		double minimum = progressSlider.getMinimum();
		int fillValue = (int) (w * (currentValue - minimum) / (maximun - minimum));
		//let painting the component fully
		h -= 1;
		g.setColor(color);
		g.drawRoundRect(x, y, w, h, 4, 4);
		g.fillRoundRect(x, y, fillValue, h, 4, 4);
	}

	@Override
	public void paintSliderThumbBackground(SynthContext context, Graphics g, int x, int y, int w, int h, int orientation) {
		//do nothing, we don't want a thumb
	}
}
