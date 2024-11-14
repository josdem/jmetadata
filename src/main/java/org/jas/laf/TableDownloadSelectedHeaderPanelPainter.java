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

public class TableDownloadSelectedHeaderPanelPainter extends SynthPainter {
	private static final float[] FRACTIONS = { 0.360f, 0.4506f, 0.5073f, 0.5558f, 0.5996f, 0.6402f, 0.6785f, 0.7149f, 0.7497f, 0.7833f, 0.8158f, 0.8474f, 0.8780f, 0.9080f, 0.9372f, 0.9658f, 0.994f };

	private static final Color[] COLORS = { new Color(0x5a, 0x5a, 0x5a), new Color(0x60, 0x60, 0x60), new Color(0x67, 0x67, 0x67), new Color(0x6e, 0x6e, 0x6e), new Color(0x75, 0x75, 0x75),
			new Color(0x7c, 0x7c, 0x7c), new Color(0x83, 0x83, 0x83), new Color(0x8a, 0x8a, 0x8a), new Color(0x91, 0x91, 0x91), new Color(0x97, 0x97, 0x97), new Color(0x9e, 0x9e, 0x9e),
			new Color(0xa5, 0xa5, 0xa5), new Color(0xac, 0xac, 0xac), new Color(0xb3, 0xb3, 0xb3), new Color(0xba, 0xba, 0xba), new Color(0xc1, 0xc1, 0xc1), new Color(0xc8, 0xc8, 0xc8) };
	private Color shadowColor = new Color(0x64, 0x64, 0x64);
	private Color gridColor = new Color(0x77, 0x77, 0x77);

	@Override
	public void paintPanelBackground(SynthContext context, Graphics g, int x, int y, int w, int h) {
		LinearGradientPaint gradientPaint = new LinearGradientPaint(67.00f, 25.08f, 67.00f, -11.50f, FRACTIONS, COLORS);
		((Graphics2D) g).setPaint(gradientPaint);
		g.fillRect(x, y, w, h);
		g.setColor(shadowColor);
		g.fillRect(x, y, w, 3);
		g.setColor(gridColor);
		g.drawLine(0, h - 1, w, h - 1);
		g.drawLine(w - 1, 0, w - 1, h - 1);
	}
}
