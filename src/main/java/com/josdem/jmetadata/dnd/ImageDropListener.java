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

package com.josdem.jmetadata.dnd;

import com.josdem.jmetadata.gui.ImagePanel;
import com.josdem.jmetadata.observer.ObservValue;
import com.josdem.jmetadata.observer.Observable;
import com.josdem.jmetadata.observer.ObserverCollection;
import com.josdem.jmetadata.util.FileSystemValidatorLight;
import com.josdem.jmetadata.util.Picture;
import java.awt.Image;
import java.awt.Point;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImageDropListener implements DropListener {
  private final ImagePanel imagePanel;
  private static final Class<?>[] classes = new Class<?>[] {Picture.class};
  private final Observable<ObservValue<ImagePanel>> nombre =
      new Observable<ObservValue<ImagePanel>>();

  @Override
  public Class<?>[] handledTypes() {
    return classes;
  }

  public ImageDropListener(ImagePanel imagePanel) {
    this.imagePanel = imagePanel;
  }

  @Override
  public void doDrop(DraggedObject draggedObject, Point location) {
    Picture picture = draggedObject.get(Picture.class);
    if (picture != null) {
      doDrop(picture, location);
    }
  }

  public void doDrop(Picture pic, Point location) {
    if (!pic.isValidImageSize()) {
      log.info("editContact.portrait.dnd.error.size");
      return;
    }

    if (!pic.isProportionedImage()) {
      log.info("editContact.portrait.dnd.error.proportion");
      return;
    }

    Image image = pic.getImage();
    if (image != null) {
      imagePanel.setImage(image, .17, .17);
      this.nombre.fire(new ObservValue<ImagePanel>(imagePanel));
    }
  }

  public void doDrop(FileSystemValidatorLight validator, Point location) {
    log.info("editContact.portrait.dnd.error.file");
  }

  @Override
  public boolean validateDrop(DraggedObject draggedObject, Point location) {
    return draggedObject.is(Picture.class, FileSystemValidatorLight.class);
  }

  public ObserverCollection<ObservValue<ImagePanel>> onDropped() {
    return nombre;
  }
}
