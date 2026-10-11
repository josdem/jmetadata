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

import com.josdem.jmetadata.enums.IconType;
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

  private static final long serialVersionUID = 1L;
  private JPanel dragAcceptedPanel;
  private static final Dimension compactSize = new Dimension(44, 44);
  private Dimension fullSize = new Dimension(100, 100);
  private JPanel jContentPane = null;
  private JLabel dragIcon = null;
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

  public void setContent() {
    jContentPane.setName(DRAG_DIALOG_NAME);
  }
}

