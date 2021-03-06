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

package org.jas.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jas.ApplicationState;
import org.jas.exception.InvalidId3VersionException;
import org.jas.helper.DateHelper;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

public class FileUtils {
	private List<File> fileList;
	private DateHelper fileHelper = new DateHelper();

	public List<File> getFileList(File root) throws InterruptedException, IOException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException, InvalidId3VersionException {
		fileList = new ArrayList<File>();
		scan(root);
		return fileList;
	}

	private void scan(File root) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, InvalidId3VersionException {
		String[] listFiles = root.list();
		for(int i=0; i<listFiles.length; i++){
			File file = new File(root.getAbsolutePath() + File.separator + listFiles[i]);
			if(file.isDirectory()){
				scan(file);
			} else{
				fileList.add(file);
			}
		}
	}

	public boolean isMp3File(File file) {
		return file.getPath().toLowerCase().endsWith("mp3");
	}

	public boolean isM4aFile(File file) {
		return file.getPath().toLowerCase().endsWith("m4a");
	}

	public File createTempFile() throws IOException {
		return File.createTempFile(ApplicationState.PREFIX, ApplicationState.FILE_EXT);
	}

	public File createFile(File root, String prefix, String ext) {
		long timestamp = fileHelper.getTimestamp();
		StringBuilder sb = new StringBuilder();
		sb.append(timestamp);
		sb.append(".");
		sb.append(ext.toLowerCase());
		return (prefix == StringUtils.EMPTY) ? new File(root, ApplicationState.PREFIX + sb.toString()) : new File(root, prefix + sb.toString());
	}
}
