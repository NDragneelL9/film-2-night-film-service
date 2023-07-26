package com.timfralou.app.seeds;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FilmsPageSeed {
    private String filmsPage;

    public FilmsPageSeed() {
        String basePath = "src/main/java/com/timfralou/app/seeds/jsons/";
        try {
            this.filmsPage = new String(Files.readAllBytes(Paths.get(basePath + "FilmsPage.json")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String filmPage() {
        return filmsPage;
    }
}
