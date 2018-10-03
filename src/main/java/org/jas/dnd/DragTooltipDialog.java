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

package org.jas.dnd;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Window;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jas.util.Picture;
import org.jas.util.FileSystemValidatorLight;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DragTooltipDialog extends JDialog {
	private static final String DRAG_DIALOG_NAME = "dragDialog";

  private Logger log = LoggerFactory.getLogger(this.getClass());

	private static final Rectangle DRAG_ICON_BOUNDS = new Rectangle(1, 1, 18, 18);
	private static final int DEFAULT_MIN_FONT_WIDTH = 4;
	private static final int SPACER_WIDTH = 4;
	private static final int ROW_HEIGHT = 20;

	public enum IconType {
		Folder("folders", "dragFolder", Object.class, 14, 14), Playlist("playlists", "dragPlaylist", Object.class, 14, 14), Track(
				"tracks", "dragTrack", Object.class, 14, 14), Download("downloads", "dragTrack", Object.class, 14, 14), Contact(
				"contacts", "dragContacts", Object.class, 7, 14) {
			@Override
			public boolean matches(Object arg0) {
				return super.matches(arg0);
			}
		},
		PendingEmail("pending emails", "dragPendingEmail", Object.class, 16, 14) {
			@Override
			public boolean matches(Object arg0) {
				return super.matches(arg0);
			}
		};

		private final String text;
		private final String synthName;
		private final Class<?> clazz;
		private final int height;
		private final int width;

		private IconType(String text, String synthName, Class<?> clazz, int width, int height) {
			this.text = text;
			this.synthName = synthName;
			this.clazz = clazz;
			this.width = width;
			this.height = height;
		}

		public String getText() {
			return text;
		}

		public static IconType getIconType(Object object) {
			for (IconType iconType : values()) {
				if (iconType.matches(object)) {
					return iconType;
				}
			}
			return null;
		}

		public boolean matches(Object o) {
			return clazz.isAssignableFrom(o.getClass());
		}
	}

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel dragIcon = null;
	private JPanel descriptionPanel = null;
	private Dimension fullSize = new Dimension(100, 100);
	private static final Dimension compactSize = new Dimension(44, 44);
	private JPanel dragAcceptedPanel;
	private boolean allowed;

	public DragTooltipDialog(Window owner) {
		super((owner instanceof Frame) ? (JFrame) owner : null, null, false, TransparencyManagerFactory.getManager()
				.getTranslucencyCapableGC());
		initialize();
		TransparencyManagerFactory.getManager().setWindowOpaque(this, false);
	}

	private void initialize() {
		this.setSize(this.getPreferredSize());
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		this.setContentPane(getJContentPane());
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
	}

	public void setAllowed(boolean allowed) {
		this.allowed = allowed;
		if (!isVisible()) {
			return;
		}
		if (dragIcon != null) {
			try {
				if (allowed) {
					this.setContentPane(getDragAcceptedPanel());
					this.setSize(compactSize);
					dragIcon.setName("dragPermitted");
				} else {
					this.setContentPane(getJContentPane());
					this.setSize(fullSize);
					dragIcon.setName("dragNotPermitted");
				}
			} catch (IllegalArgumentException ex) {
				log.debug("Drag tootltip display error.");
			}
		}
		TransparencyManagerFactory.getManager().setWindowOpaque(this, false);
	}

	private void setFullSize(Dimension contentSize) {
		setFullSize(24 + contentSize.width, Math.max(20, contentSize.height));
	}

	private void setFullSize(int width, int height) {
		this.fullSize = new Dimension(width, height);
		setAllowed(allowed);
	}

	private JPanel getDragAcceptedPanel() {
		if (dragAcceptedPanel == null) {
			dragAcceptedPanel = new JPanel(new BorderLayout());
			dragAcceptedPanel.setName("transparent");
			JLabel dragIconAccepted = new JLabel();
			dragIconAccepted.setName("dragPermittedPanel");
			dragAcceptedPanel.add(dragIconAccepted, BorderLayout.CENTER);
			dragAcceptedPanel.setSize(compactSize);
		}
		return dragAcceptedPanel;
	}

	public void setContent(FileSystemValidatorLight validator) {
		jContentPane.setName(DRAG_DIALOG_NAME);
		int height = 0;
		int width = 0;
		Dimension contentSize = new Dimension(width, height);
		this.setFullSize(contentSize);
		TransparencyManagerFactory.getManager().setWindowOpaque(this, false);
	}

	public void setContent(String... message) {
		jContentPane.setName(DRAG_DIALOG_NAME);
		Dimension contentSize = fillContent(Arrays.asList(message));
		this.setFullSize(contentSize);
	}

	public void setContent(final Picture picture) {
		jContentPane.setName(DRAG_DIALOG_NAME);
		final Dimension d = calculateImagePanelSize(picture);
		final int width = d.width;
		final int height = d.height;
		JPanel imagePanel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				g.drawImage(picture.getImage(), 0, 0, width, height, null);
			}
		};
		descriptionPanel.setBounds(7, 20, width, height);
		descriptionPanel.add(imagePanel);
		this.setFullSize(width + 14, height + dragIcon.getHeight() + 7);
		TransparencyManagerFactory.getManager().setWindowOpaque(this, false);
	}

	private Dimension fillContent(List<?>... stuff) {
		int height = 0;
		int width = 0;
		for (List<?> list : stuff) {
			DynamicPanel dynamicPanel = getDynamicPanel(list);
			if (dynamicPanel == null) {
				continue;
			}
			width = Math.max(width, dynamicPanel.getPanelWidth());
			height += dynamicPanel.getPanelHeight();
			descriptionPanel.setBounds(21, 3, width, height);
			descriptionPanel.add(dynamicPanel.getPanel());
		}
		TransparencyManagerFactory.getManager().setWindowOpaque(this, false);
		return new Dimension(width, height);
	}

	private Dimension calculateImagePanelSize(Picture picture) {
		int width = 100;
		int height = 100;
		int imgW = picture.getImage().getWidth(null);
		int imgH = picture.getImage().getHeight(null);
		if (imgW > imgH) {
			height = (imgH * 100) / imgW;
		} else {
			width = (imgW * 100) / imgH;
		}
		return new Dimension(width, height);
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel(null);
			jContentPane.setName(DRAG_DIALOG_NAME);
			jContentPane.add(getDragIcon());
			jContentPane.add(getDescriptionPanel());
		}
		return jContentPane;
	}

	private JPanel getDescriptionPanel() {
		if (descriptionPanel == null) {
			descriptionPanel = new JPanel();
			descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.Y_AXIS));
		}
		return descriptionPanel;
	}

	private DynamicPanel getDynamicPanel(List<?> list) {
		if (list == null || list.isEmpty()) {
			return null;
		}
		IconType type = IconType.getIconType(list.get(0));
		return getDynamicPanel(type, list);
	}

	private DynamicPanel getDynamicPanel(IconType type, List<?> list) {
		if (list == null || list.isEmpty()) {
			return null;
		}

		JPanel dynamicPanel = null;
		JLabel dynamicText = null;

		String text = list.isEmpty() ? null : (list.size()) + " " + (type == null ? "ERROR" : type.getText());
		if (list.size() == 1) {
			text = list.get(0) instanceof File ? ((File) list.get(0)).getName() : list.get(0).toString();
		}

		dynamicText = new JLabel(text);
		dynamicText.setForeground(Color.WHITE);
		FontMetrics fontMetrics = dynamicText.getFontMetrics(dynamicText.getFont());
		int width = fontMetrics.stringWidth(dynamicText.getText()) + DEFAULT_MIN_FONT_WIDTH;
		int realHeight = ROW_HEIGHT;
		String longestText = "";
		while (text.contains("<br>")) {
			text = text.substring(text.indexOf("<br>") + DEFAULT_MIN_FONT_WIDTH);
			if (text.length() > longestText.length()) {
				longestText = text;
			}
			realHeight += ROW_HEIGHT;
		}
		if (!longestText.isEmpty()) {
			width = fontMetrics.stringWidth(longestText) + DEFAULT_MIN_FONT_WIDTH;
		}
		dynamicPanel = new JPanel();
		dynamicPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		dynamicPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, realHeight));
		dynamicPanel.setMinimumSize(new Dimension(0, realHeight));
		dynamicPanel.setPreferredSize(new Dimension(100, realHeight));
		dynamicPanel.setSize(new Dimension(100, realHeight));
		if (type != null) {
			dynamicPanel.add(getDynamicIcon(type), null);
			JPanel spacer = new JPanel();
			Dimension d = new Dimension(SPACER_WIDTH, SPACER_WIDTH);
			width += SPACER_WIDTH;

			spacer.setSize(d);
			spacer.setMinimumSize(d);
			spacer.setMaximumSize(d);
			spacer.setPreferredSize(d);
			dynamicPanel.add(spacer);
			width += type.width;
		}
		dynamicPanel.add(dynamicText);
		return new DynamicPanel(dynamicPanel, width, realHeight);
	}

	private JPanel getDynamicIcon(IconType type) {
		JPanel dynamicIcon = null;
		dynamicIcon = new JPanel();
		dynamicIcon.setMaximumSize(new Dimension(type.width, type.height));
		dynamicIcon.setPreferredSize(new Dimension(type.width, type.height));
		dynamicIcon.setName(type.synthName);
		dynamicIcon.setMinimumSize(new Dimension(type.width, type.height));
		return dynamicIcon;
	}

	private JLabel getDragIcon() {
		if (dragIcon == null) {
			dragIcon = new JLabel();
			dragIcon.setText("");
			dragIcon.setBounds(DRAG_ICON_BOUNDS);
		}
		return dragIcon;
	}

}

class DynamicPanel {
	private JPanel panel;
	private int panelWidth;
	private int panelHeight;

	public DynamicPanel(JPanel panel, int panelWidth, int panelHeight) {
		super();
		this.panel = panel;
		this.panelWidth = panelWidth;
		this.panelHeight = panelHeight;
	}

	public JPanel getPanel() {
		return panel;
	}

	public int getPanelWidth() {
		return panelWidth;
	}

  public int getPanelHeight() {
		return panelHeight;
	}
}
