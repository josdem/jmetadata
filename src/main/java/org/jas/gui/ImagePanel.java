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

package org.jas.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;

  private Image portrait;
	private double arcHeight;
	private double arcWidth;

  private Logger log = LoggerFactory.getLogger(this.getClass());

	public ImagePanel() {
	}

	public ImagePanel(Image portrait, double arcWidth, double arcHeight) {
		setImage(portrait, arcWidth, arcHeight);
	}

	public void setImage(Image portrait, double arcWidth, double arcHeight) {
		this.portrait = portrait;
		this.arcWidth = arcWidth;
		this.arcHeight = arcHeight;
		this.repaint();
	}

	public Image getImage() {
		return portrait;
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (portrait == null) {
			super.paintComponent(g);
			return;
		}
		try {
			// Create a translucent intermediate image in which we can perform the soft clipping
			GraphicsConfiguration gc = ((Graphics2D) g).getDeviceConfiguration();
			BufferedImage intermediateBufferedImage = gc.createCompatibleImage(getWidth(), getHeight(),
					Transparency.TRANSLUCENT);
			Graphics2D bufferGraphics = intermediateBufferedImage.createGraphics();

			// Clear the image so all pixels have zero alpha
			bufferGraphics.setComposite(AlphaComposite.Clear);
			bufferGraphics.fillRect(0, 0, getWidth(), getHeight());

			// Render our clip shape into the image. Shape on where to paint
			bufferGraphics.setComposite(AlphaComposite.Src);
			bufferGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			bufferGraphics.setColor(Color.WHITE);
			bufferGraphics.fillRoundRect(0, 0, getWidth(), getHeight(), (int) (getWidth() * arcWidth),
					(int) (getHeight() * arcHeight));

			// SrcAtop uses the alpha value as a coverage value for each pixel stored in the
			// destination shape. For the areas outside our clip shape, the destination
			// alpha will be zero, so nothing is rendered in those areas. For
			// the areas inside our clip shape, the destination alpha will be fully
			// opaque.
			bufferGraphics.setComposite(AlphaComposite.SrcAtop);
			bufferGraphics.drawImage(portrait, 0, 0, getWidth(), getHeight(), null);
			bufferGraphics.dispose();

			// Copy our intermediate image to the screen
			g.drawImage(intermediateBufferedImage, 0, 0, null);

		} catch (Exception e) {
			log.warn("Error: Creating Renderings", e);
		}
	}

}
