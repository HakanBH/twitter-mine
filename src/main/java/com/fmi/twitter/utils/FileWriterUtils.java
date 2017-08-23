package com.fmi.twitter.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileWriterUtils {
    public static void appendFile(String string, String filename) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            File file = new File(filename);
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            // true = append file
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.write(string);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void appendFile(List<?> list, String filename) {
        for (Object o : list) {
            appendFile(String.valueOf(o), filename);
        }
    }
}
