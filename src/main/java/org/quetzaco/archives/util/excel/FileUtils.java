package org.quetzaco.archives.util.excel;

import org.quetzaco.converter.utils.FileUtil;

import java.io.File;
import java.io.FilenameFilter;

public class FileUtils {
    public static String getExtension(String filename) {
        String[] extension = new File(filename).getName().split("\\.");
        String type;
        if (extension.length >= 2) {
            type = extension[extension.length - 1].trim();
        } else {
            type = "";
        }
        return type;
    }

    public static int getFileCount(String path, FilenameFilter filter) {
        File folder = new File(path);
        if (!folder.exists() || !folder.isDirectory()) return -1;

        File[] list;
        if (filter == null) {
            list = folder.listFiles();
        } else {
            list = folder.listFiles(filter);
        }
        if (list == null) {
            return -1;
        } else {
            return list.length;
        }
    }

    public static int getNumberOfPagesOfSWF(String swfFile) {
        return FileUtil.getFileCount(swfFile.substring(0, swfFile.indexOf(FileUtil.getExtension(swfFile)) - 1), new SWFFrameFilter());
    }
}

class SWFFrameFilter implements FilenameFilter {
    public boolean accept(File dir, String name) {
        return (name.endsWith(".frm"));
    }
}