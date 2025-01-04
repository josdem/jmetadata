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

import com.josdem.jmetadata.util.FileSystemValidatorLight;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class DraggedObjectFileSystemGenerator extends SimpleDraggedObjectGenerator {

  @Override
  public DraggedObject get(Transferable transferable) {
    DataFlavor[] flavors = transferable.getTransferDataFlavors();
    if (flavors == null) {
      return null;
    }
    Object draggedObject;
    for (DataFlavor flavor : flavors) {
      List<File> files = null;
      files = tryGetFile(transferable, flavor);
      if (files != null && !files.isEmpty()) {
        boolean isFromExternalDevices = false;
        try {
          isFromExternalDevices =
              (Boolean) transferable.getTransferData(FileTransferable.EXTERNAL_DEVICES_FLAVOR);
        } catch (Exception e) {
        }
        draggedObject = new FileSystemValidatorLight(isFromExternalDevices, files);
        return new SimpleDraggedObject(draggedObject);
      }
    }
    return new SimpleDraggedObject(null);
  }

  @SuppressWarnings("unchecked")
  protected static List<File> tryGetFile(Transferable transferable, DataFlavor flavor) {
    try {
      Object container = transferable.getTransferData(flavor);
      List<?> someThings = (List<?>) container;
      if (someThings != null && !someThings.isEmpty() && someThings.get(0) instanceof File) {
        return (List<File>) someThings;
      }
    } catch (Exception e) {
      return null;
    }
    // we can have a string or multiple inside
    try {
      String data = (String) transferable.getTransferData(flavor);
      return textURIListToFileList(data);
    } catch (Exception e) {
      return null;
    }
  }

  private static List<File> textURIListToFileList(String data) throws Exception {
    List<File> list = new ArrayList<File>(1);
    for (StringTokenizer st = new StringTokenizer(data, "\r\n"); st.hasMoreTokens(); ) {
      String s = st.nextToken();
      if (s.startsWith("#")) {
        // the line is a comment (as per the RFC 2483)
        continue;
      }
      URI uri = new URI(s);
      File file = new File(uri);
      list.add(file);
    }
    return list;
  }
}
