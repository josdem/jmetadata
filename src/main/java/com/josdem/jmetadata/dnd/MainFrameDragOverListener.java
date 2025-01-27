/*
   Copyright 2025 Jose Morales contact@josdem.io

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
import java.awt.Frame;
import java.awt.Point;
import java.awt.Window;
import javax.swing.SwingUtilities;

public class MainFrameDragOverListener implements DragOverListener {
  private final Window frame;
  private DragTooltipDialog dialog;

  public MainFrameDragOverListener(Window frame) {
    this.frame = frame;
  }

  @Override
  public void dragEnter(DraggedObject draggedObject) {
    if (frame instanceof Frame) {
      dialog = new DragTooltipDialog((Frame) frame);
    } else {
      dialog = new DragTooltipDialog(null);
    }
    if (draggedObject.is(FileSystemValidatorLight.class)) {
      FileSystemValidatorLight validator = draggedObject.get(FileSystemValidatorLight.class);
      if (!validator.hasError()) {
        dialog.setContent(validator);
      } else {
        dialog.setContent("mainFrameDrop.error.1level");
      }
    } else if (draggedObject.is(Picture.class)) {
      Picture picture = draggedObject.get(Picture.class);
      dialog.setContent(picture);
      dialog.setVisible(true);
    } else if (draggedObject.get() == null) {
      dialog.setContent("");
      dialog.setVisible(true);
    } else {
      dialog.setContent("It is impossible to insert what you dragged. Cause its evil");
    }
    dialog.setVisible(true);
  }

  @Override
  public void dragExit(boolean droppe) {
    if (dialog != null) {
      dialog.setVisible(false);
      dialog.dispose();
      dialog = null;
    }
  }

  @Override
  public void dropOcurred(boolean success) {
    if (dialog != null) {
      dialog.setVisible(false);
      dialog.dispose();
      dialog = null;
    }
  }

  @Override
  public void updateLocation(Point location) {
    if (dialog != null) {
      Point p = (Point) location.clone();
      p.x = p.x + 5;
      SwingUtilities.convertPointToScreen(p, frame);
      dialog.setLocation(p);
    }
  }

  @Override
  public void dragAllowedChanged(boolean allowed) {
    if (dialog != null) {
      dialog.setAllowed(allowed);
    }
  }

  @Override
  public Class<?>[] handledTypes() {
    return null;
  }
}
