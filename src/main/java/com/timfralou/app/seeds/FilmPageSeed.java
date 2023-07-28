package com.timfralou.app.seeds;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FilmPageSeed extends BaseSeed {
    private String filmPage;

    public FilmPageSeed() {
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
