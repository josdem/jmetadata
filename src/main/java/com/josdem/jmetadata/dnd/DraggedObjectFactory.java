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

import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.List;

public class DraggedObjectFactory {
  private final List<DraggedObjectGenerator> innerGenerators =
      new ArrayList<DraggedObjectGenerator>();

  public DraggedObjectFactory() {
    innerGenerators.add(new DraggedObjectPictureGenerator());
    innerGenerators.add(new DraggedObjectFileSystemGenerator());
  }

  public DraggedObject getPreview(Transferable transferable) {
    for (DraggedObjectGenerator generator : innerGenerators) {
      DraggedObject draggedObject = generator.getPreview(transferable);
      if (draggedObject != null && draggedObject.get() != null) {
        return draggedObject;
      }
    }
    return DraggedObject.NULL;
  }

  public DraggedObject getContent(Transferable transferable) {
    for (DraggedObjectGenerator generator : innerGenerators) {
      DraggedObject draggedObject = generator.getContent(transferable);
      if (draggedObject != null && draggedObject.get() != null) {
        return draggedObject;
      }
    }
    return DraggedObject.NULL;
  }
}
