package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility;

import java.io.File;

public class FileUtils {
    public static long getLength(String str) {
        return getLength(getFileByPath(str));
    }

    public static long getDirLength(File file) {
        if (!isDir(file)) {
            return -1;
        }
        long j = 0;
        File[] listFiles = file.listFiles();
        if (!(listFiles == null || listFiles.length == 0)) {
            for (File file2 : listFiles) {
                long dirLength;
                if (file2.isDirectory()) {
                    dirLength = getDirLength(file2);
                } else {
                    dirLength = file2.length();
                }
                j = dirLength + j;
            }
        }
        return j;
    }
    public static long getLength(File file) {
        if (file == null) {
            return 0;
        }
        if (file.isDirectory()) {
            return getDirLength(file);
        }
        return getFileLength(file);
    }

    public static long getFileLength(File file) {
        if (isFile(file)) {
            return file.length();
        }
        return -1;
    }
    public static boolean isFile(File file) {
        return file != null && file.exists() && file.isFile();
    }

    public static boolean isDir(File file) {
        return file != null && file.exists() && file.isDirectory();
    }

    public static File getFileByPath(String str) {
        return StringUtils.isSpace(str) ? null : new File(str);
    }

}
