package com.timfralou.app.seeds;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FiltersPage extends BaseSeed {
    private String filtersPage;

    public FiltersPage() {
        try {
            this.filtersPage = new String(Files.readAllBytes(Paths.get(basePath + "FiltersPage.json")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String filtersPage() {
        return filtersPage;
    }
}
