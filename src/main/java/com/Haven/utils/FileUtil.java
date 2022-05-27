package com.Haven.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 文件操作工具工具类 FileUtil
 *
 * @author HavenJust
 * @date 14:06 周二 10 五月 2022年
 */

public class FileUtil {


    public static void copy(String from, String to) {
        try {
            File filePath = new File(to.substring(0, to.lastIndexOf('/')));
            Files.deleteIfExists(Path.of(to));
            if (!filePath.exists()) filePath.mkdirs();
            Files.copy(Path.of(from), Path.of(to));
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

}