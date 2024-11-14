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
import java.awt.RenderingHints;

import javax.swing.JSlider;
import javax.swing.plaf.synth.SynthContext;

public class DeviceProgressBarPainter extends ProgressBarPainter {

	@Override
	public void paintSliderTrackBackground(SynthContext context, Graphics g, int x, int y, int w, int h, int orientation) {
		Graphics2D g2 = (Graphics2D) g;
		JSlider slider = (JSlider) context.getComponent();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setPaint(getBorderGradientPaint());
		g2.fill(getBorderPath(slider));
		g2.setColor(new Color(0xb9, 0xb9, 0xb9));
		g2.fill(getBackgroundPath(slider));
	}

	@Override
	public void paintSliderThumbBackground(SynthContext context, Graphics g, int x, int y, int w, int h, int orientation) {
		Graphics2D g2 = (Graphics2D) g;
		JSlider slider = (JSlider)context.getComponent();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setPaint(getPurpleProgressGradientPaint());
		g2.fill(getProgressPath(slider));
		g2.setPaint(getBrightInPurpleProgressGradientPaint());
		g2.fill(getBrightInProgressPath(slider));
	}

}
