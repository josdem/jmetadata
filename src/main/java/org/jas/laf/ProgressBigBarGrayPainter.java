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
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;

import javax.swing.JSlider;
import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthPainter;

public class ProgressBigBarGrayPainter extends SynthPainter {

	private final LinearGradientPaint thumbBackgroundGradientOne = new LinearGradientPaint(69.00f, 9.33f, 69.00f, -1.67f,
			new float[] { 0.000f, 1.000f }, new Color[] { Color.WHITE, new Color(0x22, 0x1f, 0x1f) });

	private final LinearGradientPaint thumbBackgroundGradientTwo = new LinearGradientPaint(69.00f, 3.50f, 69.00f, -10.14f, new float[] { 0.000f,
			1.000f }, new Color[] { Color.WHITE, new Color(0x32, 0x32, 0x32) });

	@Override
	public void paintSliderTrackBackground(SynthContext context, Graphics g, int x, int y, int w, int h, int orientation) {
		Graphics2D g2 = (Graphics2D) g;
		JSlider slider = (JSlider) context.getComponent();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setPaint(getBorderGradientPaint());
		g2.fill(getBorderPath(slider));
		g2.setColor(new Color(0xb9, 0xb9, 0xb9));
	}

	@Override
	public void paintSliderThumbBackground(SynthContext context, Graphics g, int x, int y, int w, int h, int orientation) {
		Graphics2D g2 = (Graphics2D) g;
		JSlider slider = (JSlider) context.getComponent();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setPaint(thumbBackgroundGradientOne);
		g2.fill(getProgressPath(slider));
		g2.setPaint(thumbBackgroundGradientTwo);
		g2.fill(getBrightInProgressPath(slider));
	}

	private int getProgressFromSlider(JSlider progressSlider) {
		double currentValue = progressSlider.getValue();
		double sliderWidth = progressSlider.getSize().getWidth();
		int fillValue = (int) (currentValue * sliderWidth / 100);
		// TODO FIXME Ugly patch for avoiding alias in progress when the bar is at is minimum size;
		int curveGap = 3;
		return fillValue > curveGap ? fillValue : curveGap;
	}

	protected GeneralPath getBorderPath(JSlider slider) {
		GeneralPath gp = new GeneralPath();
		double sliderWidth = slider.getWidth();
		double sliderHeight = slider.getHeight();
		double gap = 2.5;
		double zero = 0.00;

		gp.moveTo(sliderWidth, sliderHeight / 2);
		gp.curveTo(sliderWidth, sliderHeight - gap, sliderWidth - gap, sliderHeight, sliderWidth - gap * 2, sliderHeight);
		gp.lineTo(gap * 2, sliderHeight);
		gp.curveTo(gap, sliderHeight, zero, sliderHeight - gap, zero, sliderHeight / 2);
		gp.lineTo(zero, sliderHeight / 2);
		gp.curveTo(zero, gap, gap, zero, gap * 2, zero);
		gp.lineTo(sliderWidth - gap * 2, zero);
		gp.curveTo(sliderWidth - gap, zero, sliderWidth, gap, sliderWidth, sliderHeight / 2);
		gp.closePath();
		return gp;
	}

	protected GeneralPath getBackgroundPath(JSlider slider) {
		GeneralPath gp2 = new GeneralPath();
		double sliderWidth = slider.getWidth();
		double sliderHeight = slider.getHeight();
		double gap = 2.5;
		double border = 1.00;

		gp2.moveTo(sliderWidth - border, sliderHeight / 2);
		gp2.curveTo(sliderWidth - border, sliderHeight - gap, sliderWidth - gap, sliderHeight - border, sliderWidth - 2
				* gap, sliderHeight - border);
		gp2.lineTo(2 * gap, sliderHeight - border);
		gp2.curveTo(gap, sliderHeight - border, border, sliderHeight - gap, border, sliderHeight / 2);
		gp2.lineTo(border, sliderHeight / 2);
		gp2.curveTo(border, gap, gap, border, gap * 2, border);
		gp2.lineTo(sliderWidth - gap * 2, border);
		gp2.curveTo(sliderWidth - gap, border, sliderWidth - border, gap, sliderWidth - border, sliderHeight / 2);
		gp2.closePath();
		return gp2;
	}

	protected LinearGradientPaint getBorderGradientPaint() {
		float fractions[] = { 0.000f, 0.0553f, 0.1111f, 0.1673f, 0.2240f, 0.2814f, 0.3393f, 0.3979f, 0.4573f, 0.5176f,
				0.5789f, 0.6414f, 0.7055f, 0.7715f, 0.8402f, 0.9133f, 1.000f };

		Color colors[] = { Color.WHITE, new Color(0xf1, 0xf1, 0xf1), new Color(0xe3, 0xe3, 0xe3),
				new Color(0xd5, 0xd5, 0xd5), new Color(0xc8, 0xc7, 0xc7), new Color(0xba, 0xb9, 0xb9),
				new Color(0xac, 0xab, 0xab), new Color(0x9e, 0x9d, 0x9d), new Color(0x91, 0x8f, 0x8f),
				new Color(0x83, 0x81, 0x81), new Color(0x75, 0x73, 0x73), new Color(0x67, 0x65, 0x65),
				new Color(0x5a, 0x57, 0x57), new Color(0x4c, 0x49, 0x49), new Color(0x3e, 0x3b, 0x3b),
				new Color(0x30, 0x2d, 0x2d), new Color(0x23, 0x1f, 0x20) };
		LinearGradientPaint gradientPaint = new LinearGradientPaint(70.00f, 13.17f, 70.00f, -11.32f, fractions, colors);
		return gradientPaint;
	}

	protected GeneralPath getProgressPath(JSlider slider) {
		GeneralPath gp = new GeneralPath();
		double sliderWidth = slider.getWidth();
		double sliderHeight = slider.getHeight();
		double progressFromSlider = getProgressFromSlider(slider);
		double border = 1.0;
		double gap = 2.5;
		gp.moveTo(progressFromSlider, border);
		gp.lineTo(2 * gap, border);
		gp.curveTo(gap, border, border, gap, border, sliderHeight / 2.0);
		gp.curveTo(gap, sliderHeight - gap, gap, sliderHeight - border, 2 * gap, sliderHeight - border);
		gp.lineTo(progressFromSlider, sliderHeight - border);
		double fillValue = getProgressFromSlider(slider);
		int curveGap = 3;
		if (fillValue >= slider.getWidth() - curveGap) {
			gp.lineTo(sliderWidth - 2 * gap, sliderHeight - border);
			gp.curveTo(sliderWidth - gap, sliderHeight - border, sliderWidth - border, sliderHeight - gap, sliderWidth
					- border, sliderHeight / 2.0);
			gp.curveTo(sliderWidth - border, gap, sliderWidth - gap, border, sliderWidth - 2 * gap, border);
		}
		gp.closePath();
		return gp;
	}

	protected GeneralPath getBrightInProgressPath(JSlider slider) {
		GeneralPath gp = new GeneralPath();
		double sliderHeight = slider.getHeight();
		double sliderWidth = slider.getWidth();
		double gap = 2.5;
		int curveGap = 3;
		double fillValue = getProgressFromSlider(slider);
		fillValue = fillValue < sliderWidth - curveGap ? fillValue : sliderWidth - curveGap;
		gp.moveTo(fillValue, gap);
		gp.lineTo(2 * gap, gap);
		gp.curveTo(2 * gap, gap, gap, 2 * gap, gap, sliderHeight / 2.0);
		gp.lineTo(fillValue, sliderHeight / 2.0);
		gp.closePath();
		return gp;
	}

}
