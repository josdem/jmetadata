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

public class ProgressBarPainter extends SynthPainter {

	@Override
	public void paintSliderTrackBackground(SynthContext context, Graphics g, int x, int y, int w, int h, int orientation) {
		Graphics2D g2 = (Graphics2D) g;
		JSlider slider = (JSlider) context.getComponent();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setPaint(slider.isEnabled() ? getBorderGradientPaint() : getDisabledBorderGradientPaint());
		g2.fill(getBorderPath(slider));
		g2.setColor(slider.isEnabled() ? new Color(0xb9, 0xb9, 0xb9) : new Color(0xf3, 0xf3, 0xf3));
		g2.fill(getBackgroundPath(slider));
	}

	@Override
	public void paintSliderThumbBackground(SynthContext context, Graphics g, int x, int y, int w, int h, int orientation) {
		Graphics2D g2 = (Graphics2D) g;
		JSlider slider = (JSlider)context.getComponent();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setPaint(slider.isEnabled() ? getPurpleProgressGradientPaint() : getGrayProgressGradientPaint());
		g2.fill(getProgressPath(slider));
		g2.setPaint(slider.isEnabled() ? getBrightInPurpleProgressGradientPaint() : getBrightInGrayProgressGradientPaint());
		g2.fill(getBrightInProgressPath(slider));
	}

	private int getProgressFromSlider(JSlider progressSlider) {
		double currentValue = progressSlider.getValue();
		double sliderWidth = progressSlider.getSize().getWidth();
		int fillValue = (int) (currentValue*sliderWidth/100);
		//TODO FIXME Ugly patch for avoiding alias in progress when the bar is at is minimum size;
		int curveGap = 3;
		return fillValue > curveGap ? fillValue : curveGap;
	}

	protected GeneralPath getBorderPath(JSlider slider) {
		GeneralPath gp = new GeneralPath();
		double sliderWidth = slider.getWidth();
		double sliderHeight = slider.getHeight();
		double gap = 2.5;
		double zero = 0.00;

		gp.moveTo(sliderWidth, sliderHeight/2);
		gp.curveTo(sliderWidth, sliderHeight-gap, sliderWidth-gap, sliderHeight, sliderWidth-gap*2, sliderHeight);
		gp.lineTo(gap*2, sliderHeight);
		gp.curveTo(gap, sliderHeight, zero, sliderHeight-gap, zero, sliderHeight/2);
		gp.lineTo(zero, sliderHeight/2);
		gp.curveTo(zero, gap, gap, zero, gap*2, zero);
		gp.lineTo(sliderWidth-gap*2, zero);
		gp.curveTo(sliderWidth-gap, zero, sliderWidth, gap, sliderWidth, sliderHeight/2);
		gp.closePath();
		return gp;
	}

	protected GeneralPath getBackgroundPath(JSlider slider) {
		GeneralPath gp2 = new GeneralPath();
		double sliderWidth = slider.getWidth();
		double sliderHeight = slider.getHeight();
		double gap = 2.5;
		double border = 1.00;

		gp2.moveTo(sliderWidth-border, sliderHeight/2);
		gp2.curveTo(sliderWidth-border, sliderHeight-gap, sliderWidth-gap, sliderHeight-border, sliderWidth-2*gap, sliderHeight-border);
		gp2.lineTo(2*gap, sliderHeight-border);
		gp2.curveTo(gap, sliderHeight-border, border, sliderHeight-gap, border, sliderHeight/2);
		gp2.lineTo(border, sliderHeight/2);
		gp2.curveTo(border, gap, gap, border, gap*2, border);
		gp2.lineTo(sliderWidth-gap*2, border);
		gp2.curveTo(sliderWidth-gap, border, sliderWidth-border, gap, sliderWidth-border, sliderHeight/2);
		gp2.closePath();
		return gp2;
	}

	protected LinearGradientPaint getBorderGradientPaint() {
		float fractions[] = { 0.000f, 0.0553f, 0.1111f, 0.1673f, 0.2240f, 0.2814f, 0.3393f, 0.3979f, 0.4573f, 0.5176f, 0.5789f,
		        0.6414f, 0.7055f, 0.7715f, 0.8402f, 0.9133f, 1.000f };

		Color colors[] = { Color.WHITE, new Color(0xf1, 0xf1, 0xf1), new Color(0xe3, 0xe3, 0xe3), new Color(0xd5, 0xd5, 0xd5),
		        new Color(0xc8, 0xc7, 0xc7), new Color(0xba, 0xb9, 0xb9), new Color(0xac, 0xab, 0xab),
		        new Color(0x9e, 0x9d, 0x9d), new Color(0x91, 0x8f, 0x8f), new Color(0x83, 0x81, 0x81),
		        new Color(0x75, 0x73, 0x73), new Color(0x67, 0x65, 0x65), new Color(0x5a, 0x57, 0x57),
		        new Color(0x4c, 0x49, 0x49), new Color(0x3e, 0x3b, 0x3b), new Color(0x30, 0x2d, 0x2d),
		        new Color(0x23, 0x1f, 0x20) };
		LinearGradientPaint gradientPaint = new LinearGradientPaint(70.00f, 11.95f, 70.00f, -8.46f, fractions, colors);
		return gradientPaint;
	}

	protected LinearGradientPaint getPurpleProgressGradientPaint() {
		float fractions[] = { 0.000f, 0.0418f, 0.0845f, 0.1283f, 0.1732f, 0.2194f, 0.2671f, 0.3164f, 0.3676f, 0.4210f, 0.4771f,
		        0.5365f, 0.6001f, 0.6694f, 0.7471f, 0.8401f, 1.000f };
		Color colors[] = { new Color(0xb8, 0x0, 0xb8), new Color(0xaf, 0x1, 0xb0), new Color(0xa6, 0x2, 0xa8),
		        new Color(0x9d, 0x3, 0xa1), new Color(0x94, 0x4, 0x99), new Color(0x8b, 0x5, 0x91), new Color(0x82, 0x6, 0x8a),
		        new Color(0x79, 0x7, 0x82), new Color(0x71, 0x8, 0x7b), new Color(0x68, 0x9, 0x73), new Color(0x5f, 0xa, 0x6b),
		        new Color(0x56, 0xb, 0x64), new Color(0x4d, 0xc, 0x5c), new Color(0x44, 0xd, 0x54), new Color(0x3b, 0xe, 0x4d),
		        new Color(0x32, 0xf, 0x45), new Color(0x2a, 0x11, 0x3e) };
		LinearGradientPaint gradientPaint = new LinearGradientPaint(44.00f, 10.31f, 44.00f, 1.43f, fractions, colors);
		return gradientPaint;
	}

	protected GeneralPath getProgressPath(JSlider slider) {
		GeneralPath gp = new GeneralPath();
		double sliderWidth = slider.getWidth();
		double sliderHeight = slider.getHeight();
		double progressFromSlider = getProgressFromSlider(slider);
		double border = 1.0;
		double gap  = 2.5;
		gp.moveTo(progressFromSlider, border);
		gp.lineTo(2*gap, border);
		gp.curveTo(gap, border, border, gap, border, sliderHeight/2.0);
		gp.curveTo(gap, sliderHeight - gap, gap, sliderHeight-border, 2*gap, sliderHeight-border);
		gp.lineTo(progressFromSlider, sliderHeight-border);
		double fillValue = getProgressFromSlider(slider);
		int curveGap = 3;
		if (fillValue >= slider.getWidth()-curveGap) {
			gp.lineTo(sliderWidth-2*gap, sliderHeight-border);
			gp.curveTo(sliderWidth-gap, sliderHeight-border, sliderWidth-border, sliderHeight-gap, sliderWidth-border, sliderHeight/2.0);
			gp.curveTo(sliderWidth-border, gap, sliderWidth-gap, border, sliderWidth-2*gap, border);
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
		fillValue = fillValue < sliderWidth-curveGap ? fillValue : sliderWidth-curveGap;
		gp.moveTo(fillValue, gap);
		gp.lineTo(2*gap, gap);
		gp.curveTo(2*gap, gap, gap, 2*gap, gap, sliderHeight/2.0);
		gp.lineTo(fillValue, sliderHeight/2.0);
		gp.closePath();
		return gp;
	}

	protected LinearGradientPaint getBrightInPurpleProgressGradientPaint() {
		float fractions[] = { 0.160f, 0.479f, 0.877f };
		Color colors[] = { new Color(0x4d, 0x15, 0x5c), new Color(0x85, 0x65, 0x90), new Color(0xd1, 0xc9, 0xd8) };
		LinearGradientPaint gradientPaint = new LinearGradientPaint(44.25f, 6.33f, 44.25f, 0.50f, fractions, colors);
		return gradientPaint;
	}

	private LinearGradientPaint getDisabledBorderGradientPaint() {
		float fractions[] = {0.000f, 0.0553f, 0.1111f, 0.1673f, 0.2240f, 0.2814f, 0.3393f, 0.3979f, 0.4573f, 0.5176f, 0.5789f,
				 0.6414f, 0.7055f, 0.7715f, 0.8402f, 0.9133f, 1.000f };

		Color colors[] = { Color.WHITE, new Color(0xfb, 0xfb, 0xfb), new Color(0xf8, 0xf8, 0xf8), new Color(0xf4, 0xf4, 0xf4),
		        new Color(0xf1, 0xf1, 0xf1), new Color(0xed, 0xed, 0xed), new Color(0xea, 0xea, 0xea),
		        new Color(0xe6, 0xe6, 0xe6), new Color(0xe3, 0xe3, 0xe3), new Color(0xe0, 0xe0, 0xe0),
		        new Color(0xdc, 0xdc, 0xdc), new Color(0xd9, 0xd9, 0xd9), new Color(0xd5, 0xd5, 0xd5),
		        new Color(0xd2, 0xd2, 0xd2), new Color(0xce, 0xce, 0xce), new Color(0xcb, 0xcb, 0xcb),
		        new Color(0xc8, 0xc8, 0xc8) };
		LinearGradientPaint gradientPaint = new LinearGradientPaint(70.00f, 11.95f, 70.00f, -8.46f, fractions, colors);
		return gradientPaint;
	}

	private LinearGradientPaint getGrayProgressGradientPaint() {
	  float fractions[] = {0.000f, 0.0418f, 0.0845f, 0.1283f, 0.1732f, 0.2194f, 0.2671f, 0.3164f, 0.3676f, 0.4210f, 0.4771f, 0.5365f,
	    0.6001f, 0.6694f, 0.7471f, 0.8401f, 1.000f };

	  Color colors[] = { new Color(0xf0, 0xf0, 0xf0), new Color(0xea, 0xea, 0xea), new Color(0xe4, 0xe4, 0xe4), new Color(0xdf, 0xdf, 0xdf),
	          new Color(0xd9, 0xd9, 0xd9), new Color(0xd3, 0xd3, 0xd3), new Color(0xce, 0xce, 0xce),
	          new Color(0xc8, 0xc8, 0xc8), new Color(0xc3, 0xc3, 0xc3), new Color(0xbd, 0xbd, 0xbd),
	          new Color(0xb7, 0xb7, 0xb7), new Color(0xb2, 0xb2, 0xb2), new Color(0xac, 0xac, 0xac),
	          new Color(0xa6, 0xa6, 0xa6), new Color(0xa1, 0xa1, 0xa1), new Color(0x9b, 0x9b, 0x9b),
	          new Color(0x96, 0x96, 0x96) };
	  LinearGradientPaint gradientPaint = new LinearGradientPaint(70.00f, 10.31f, 70.00f, 1.43f, fractions, colors);
	  return gradientPaint;
	 }


	private LinearGradientPaint getBrightInGrayProgressGradientPaint() {
	  float fractions[] = {0.160f, 0.479f, 0.877f };

	  Color colors[] = { new Color(0x78, 0x78, 0x78), new Color(0xb4, 0xb4, 0xb4), new Color(0xe6, 0xe6, 0xe6)};
	  LinearGradientPaint gradientPaint = new LinearGradientPaint(70.00f, 6.33f, 70.00f, 0.50f, fractions, colors);
	  return gradientPaint;
	 }

}
