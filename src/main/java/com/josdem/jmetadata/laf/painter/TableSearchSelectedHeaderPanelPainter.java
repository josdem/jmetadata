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

public class TableSearchSelectedHeaderPanelPainter extends SynthPainter {
	private static final float[] FRACTIONS = { 0.294f, 0.4186f, 0.4858f, 0.5408f, 0.5892f, 0.6332f, 0.6739f, 0.7122f, 0.7485f, 0.7831f, 0.8162f, 0.8482f, 0.8790f, 0.9090f, 0.9380f, 0.9663f, 0.994f };

	private static final Color[] COLORS = { new Color(0x97, 0x5d, 0x8d), new Color(0x9a, 0x62, 0x8f), new Color(0x9e, 0x67, 0x92), new Color(0xa2, 0x6c, 0x94), new Color(0xa6, 0x71, 0x97),
			new Color(0xaa, 0x76, 0x99), new Color(0xad, 0x7b, 0x9c), new Color(0xb1, 0x80, 0x9e), new Color(0xb5, 0x85, 0xa1), new Color(0xb9, 0x8a, 0xa4), new Color(0xbd, 0x8f, 0xa6),
			new Color(0xc0, 0x94, 0xa9), new Color(0xc4, 0x99, 0xab), new Color(0xc8, 0x9e, 0xae), new Color(0xcc, 0xa3, 0xb0), new Color(0xd0, 0xa8, 0xb3), new Color(0xd4, 0xae, 0xb6) };
	private Color shadowColor = new Color(0x97, 0x5d, 0x8d);
	private Color gridColor = new Color(0x77, 0x77, 0x77);

	@Override
	public void paintPanelBackground(SynthContext context, Graphics g, int x, int y, int w, int h) {
		LinearGradientPaint gradientPaint = new LinearGradientPaint(67.00f, 18.24f, 67.00f, -7.18f, FRACTIONS, COLORS);
		((Graphics2D) g).setPaint(gradientPaint);
		g.fillRect(x, y, w, h);
		g.setColor(shadowColor);
		g.fillRect(x, y, w, 3);
		g.setColor(gridColor);
		g.drawLine(0, h - 1, w, h - 1);
		g.drawLine(w - 1, 0, w - 1, h - 1);
	}

}
