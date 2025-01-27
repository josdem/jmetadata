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

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileTransferable implements Transferable {

  private final List<File> files;
  private final boolean isFromExternalPanel;
  public static final DataFlavor EXTERNAL_DEVICES_FLAVOR =
      new DataFlavor(Boolean.class, "external-devices-source");

  public FileTransferable(boolean isFromExternalPanel, List<File> files) {
    this.isFromExternalPanel = isFromExternalPanel;
    this.files = files;
  }

  @Override
  public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
    if (flavor.equals(DataFlavor.javaFileListFlavor)) {
      return files;
    } else if (flavor.equals(EXTERNAL_DEVICES_FLAVOR)) {
      return isFromExternalPanel;
    } else {
      throw new UnsupportedFlavorException(flavor);
    }
  }

  @Override
  public DataFlavor[] getTransferDataFlavors() {
    return new DataFlavor[] {DataFlavor.javaFileListFlavor, EXTERNAL_DEVICES_FLAVOR};
  }

  @Override
  public boolean isDataFlavorSupported(DataFlavor flavor) {
    return flavor.equals(DataFlavor.javaFileListFlavor) || flavor.equals(EXTERNAL_DEVICES_FLAVOR);
  }
}
