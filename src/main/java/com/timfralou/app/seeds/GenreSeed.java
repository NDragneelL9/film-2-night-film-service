package com.timfralou.app.seeds;

import java.io.File;
import java.io.IOException;
import com.timfralou.app.models.Genre;

public class GenreSeed extends BaseSeed {
    private Genre genre;

    public GenreSeed() {
        try {
            Genre genre = objectMapper.readValue(
                    new File(basePath + "Genre.json"), Genre.class);
            this.genre = genre;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Genre genre() {
        return genre;
    }
}
