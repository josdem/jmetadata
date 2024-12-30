
package com.josdem.jmetadata.dnd;

import java.awt.Point;
import java.io.File;
import java.util.List;

public interface FileSelection {
	List<File> selectedObjects(Point point);
	boolean isFromExternalDevices(Point point);
}
