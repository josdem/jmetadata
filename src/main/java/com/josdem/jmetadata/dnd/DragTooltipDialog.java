/*
   Copyright 2026 Jose Morales contact@josdem.io

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

package com.josdem.jmetadata.dnd;

import com.josdem.jmetadata.util.FileSystemValidatorLight;
import com.josdem.jmetadata.util.Picture;
import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DragTooltipDialog extends JDialog {
  private static final String DRAG_DIALOG_NAME = "dragDialog";

  private static final Rectangle DRAG_ICON_BOUNDS = new Rectangle(1, 1, 18, 18);
  private static final int DEFAULT_MIN_FONT_WIDTH = 4;
  private static final int SPACER_WIDTH = 4;
  private static final int ROW_HEIGHT = 20;

  public enum IconType {
    Track("tracks", Object.class, 14, 14);

    @Getter private final String text;
    private final Class<?> clazz;
    private final int width;
    private final int height;

    IconType(String text, Class<?> clazz, int width, int height) {
      this.text = text;
      this.clazz = clazz;
      this.width = width;
      this.height = height;
    }

    public static IconType getIconType(Object object) {
      if (IconType.Track.clazz.isAssignableFrom(object.getClass())) {
        return IconType.Track;
      }
      return null;
    }
  }

  private static final long serialVersionUID = 1L;
  private JPanel dragAcceptedPanel;
  private static final Dimension compactSize = new Dimension(44, 44);
  private Dimension fullSize = new Dimension(100, 100);
  private JPanel jContentPane = null;
  private JLabel dragIcon = null;
  private JPanel descriptionPanel = null;
  private boolean allowed;

  public DragTooltipDialog(Window owner) {
    super((owner instanceof Frame) ? (JFrame) owner : null, null, false);
    initialize();
  }

  private void initialize() {
    this.setSize(this.getPreferredSize());
    this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    this.setContentPane(getJContentPane());
    this.setUndecorated(true);
    this.setAlwaysOnTop(true);
  }

  private JPanel getJContentPane() {
    if (jContentPane == null) {
      jContentPane = new JPanel(null);
      jContentPane.setName(DRAG_DIALOG_NAME);
    }
    return jContentPane;
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
  }

  public void setContent(String... message) {
    jContentPane.setName(DRAG_DIALOG_NAME);
    Dimension contentSize = fillContent(Arrays.asList(message));
  }

  public void setContent(final Picture picture) {
    jContentPane.setName(DRAG_DIALOG_NAME);
  }

  private Dimension fillContent(List<?>... stuff) {
    var height = 0;
    var width = 0;
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
    return new Dimension(width, height);
  }

  private DynamicPanel getDynamicPanel(List<?> list) {
    if (list == null || list.isEmpty()) {
      return null;
    }

    var typeText = (IconType.getIconType(list.getFirst()) == null)
            ? "ERROR"
            : IconType.Track.getText();
    String text =
        list.isEmpty()
            ? null
            : (list.size()) + " " + typeText;
    if (list.size() == 1) {
      text =
          list.getFirst() instanceof File
              ? ((File) list.getFirst()).getName()
              : list.getFirst().toString();
    }

    var dynamicText = new JLabel(text);
    dynamicText.setForeground(Color.WHITE);
    FontMetrics fontMetrics = dynamicText.getFontMetrics(dynamicText.getFont());
    var width = fontMetrics.stringWidth(dynamicText.getText()) + DEFAULT_MIN_FONT_WIDTH;
    var realHeight = ROW_HEIGHT;
    var longestText = "";
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
    var dynamicPanel = new JPanel();
    return new DynamicPanel(dynamicPanel, width, realHeight);
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
