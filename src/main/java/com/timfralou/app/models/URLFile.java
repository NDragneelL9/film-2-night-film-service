package com.timfralou.app.models;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class URLFile {
    private URL url;
    private String fileName;

    public URLFile(URL url, String fileName) {
        this.url = url;
        this.fileName = fileName;
    }

    public URLFile(String fileName) {
        this.fileName = fileName;
    }

    public void save() {
        try {
            String filePath = "media/" + fileName;
            InputStream is = url.openStream();
            File poster = new File(filePath);
            poster.getParentFile().mkdirs();
            poster.createNewFile();
            OutputStream fos = new FileOutputStream(poster, false);
            int ch;
            while ((ch = is.read()) != -1) {
                fos.write(ch);
            }
            is.close();
            fos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void delete() {
        String filePath = "media/" + fileName;
        File poster = new File(filePath);
        poster.delete();
    }
}
