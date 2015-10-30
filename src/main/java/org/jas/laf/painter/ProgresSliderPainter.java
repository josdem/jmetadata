package org.jas.laf.painter;

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
