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

package org.jas.laf;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;

import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthPainter;

public class TableSelectedHeaderPanelPainter extends SynthPainter {
	private static final float[] FRACTIONS = { 0.261f, 0.3920f, 0.4625f, 0.5202f, 0.5709f, 0.6169f, 0.6596f, 0.6996f, 0.7376f, 0.7737f, 0.8084f, 0.8417f, 0.8740f, 0.9052f, 0.9355f, 0.9651f, 0.994f, };

	private static final Color[] COLORS = { new Color(0x50, 0x8c, 0xaa), new Color(0x55, 0x8f, 0xad), new Color(0x5a, 0x93, 0xb0), new Color(0x5f, 0x97, 0xb3), new Color(0x65, 0x9b, 0xb6),
			new Color(0x6a, 0x9e, 0xb9), new Color(0x6f, 0xa2, 0xbc), new Color(0x75, 0xa6, 0xbf), new Color(0x7a, 0xaa, 0xc3), new Color(0x7f, 0xad, 0xc6), new Color(0x85, 0xb1, 0xc9),
			new Color(0x8a, 0xb5, 0xcc), new Color(0x8f, 0xb9, 0xcf), new Color(0x95, 0xbc, 0xd2), new Color(0x9a, 0xc0, 0xd5), new Color(0x9f, 0xc4, 0xd8), new Color(0xa5, 0xc8, 0xdc) };
	private Color shadowColor = new Color(0x50, 0x8c, 0xaa);
	private Color gridColor = new Color(0x77, 0x77, 0x77);

	@Override
	public void paintPanelBackground(SynthContext context, Graphics g, int x, int y, int w, int h) {
		LinearGradientPaint gradientPaint = new LinearGradientPaint(67.00f, 18.20f, 67.00f, -8.00f, FRACTIONS, COLORS);
		((Graphics2D) g).setPaint(gradientPaint);
		g.fillRect(x, y, w, h);
		g.setColor(shadowColor);
		g.fillRect(x, y, w, 3);
		g.setColor(gridColor);
		g.drawLine(0, h - 1, w, h - 1);
		g.drawLine(w - 1, 0, w - 1, h - 1);
	}

}
