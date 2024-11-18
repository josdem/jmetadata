/*
   Copyright 2014 Jose Morales contact@josdem.io

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

import org.apache.commons.lang3.StringUtils;
import org.jas.ApplicationState;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtils {
    private List<File> fileList;

    public List<File> getFileList(File root) {
        fileList = new ArrayList<>();
        scan(root);
        return fileList;
    }

    private void scan(File root) {
        var listFiles = root.list();
        assert listFiles != null;
        Arrays.stream(listFiles).forEach(file -> {
            var inode = new File(root.getAbsolutePath() + File.separator + file);
            if (inode.isDirectory()) {
                scan(inode);
            } else {
                fileList.add(inode);
            }
        });
    }

    public boolean isMp3File(File file) {
        return StringUtils.endsWithIgnoreCase(file.getPath(), "mp3");
    }

    public boolean isM4aFile(File file) {
        return StringUtils.endsWithIgnoreCase(file.getPath(), "m4a");
    }

    public File createTempFile() throws IOException {
        return File.createTempFile(ApplicationState.PREFIX, ApplicationState.FILE_EXT);
    }

    public File createFile(File root, String prefix, String ext) {
        long timestamp = LocalDateTime.now().getNano();
        StringBuilder sb = new StringBuilder();
        sb.append(timestamp);
        sb.append(".");
        sb.append(ext.toLowerCase());
        return (prefix.equals(StringUtils.EMPTY)) ? new File(root, ApplicationState.PREFIX + sb) : new File(root, prefix + sb);
    }
}
