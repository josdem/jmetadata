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
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;

import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthPainter;

public class TableHeaderPanelPainter extends SynthPainter {
	private static final float[] FRACTIONS = {0f, 1f};
	private static final Color[] COLORS = {new Color(0xd9,0xd9,0xd9), new Color(0x67,0x67,0x67)};
	private Color shadowColor = new Color(0xb4,0xb3,0xb4);
	private Color gridColor = new Color(0x77,0x77,0x77);

	@Override
	public void paintPanelBackground(SynthContext context, Graphics g, int x, int y, int w, int h) {
		LinearGradientPaint gradientPaint = new LinearGradientPaint(2f, 18.42f, 2f, -8.84f, FRACTIONS, COLORS);
		((Graphics2D) g).setPaint(gradientPaint);
		g.fillRect(x, y, w, h);
		g.setColor(shadowColor);
		g.fillRect(x, y, w, 3);
		g.setColor(gridColor);
		g.drawLine(0, h - 1, w, h - 1);
		g.drawLine(w-1, 0, w-1, h - 1);
	}
}
