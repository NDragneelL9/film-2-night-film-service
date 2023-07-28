package com.timfralou.app.seeds;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FilmsPageSeed extends BaseSeed {
    private String filmsPage;

    public FilmsPageSeed() {
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
