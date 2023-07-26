package com.timfralou.app.seeds;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FilmPageSeed {
    private String filmPage;

    public FilmPageSeed() {
        String basePath = "src/main/java/com/timfralou/app/seeds/jsons/";
        try {
            this.filmPage = new String(Files.readAllBytes(Paths.get(basePath + "FilmPage.json")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String filmPage() {
        return filmPage;
    }
}
