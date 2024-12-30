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

import java.io.File;
import java.util.List;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import com.josdem.jmetadata.util.Picture;

import lombok.extern.slf4j.Slf4j;

@Slf4j

public class DraggedObjectPictureGenerator extends DraggedObjectFileSystemGenerator {


	@Override
	public DraggedObject get(Transferable transferable) {
		DataFlavor[] flavors = transferable.getTransferDataFlavors();
		if (flavors == null) {
			return null;
		}
		for (DataFlavor flavor : flavors) {
			List<File> files = null;
			files = tryGetFile(transferable, flavor);
			if (files != null && files.size() == 1 && !files.get(0).isDirectory()) {
				try {
					Object draggedObject = new Picture(files.get(0));
					return new SimpleDraggedObject(draggedObject);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return new SimpleDraggedObject(null);
	}

}
