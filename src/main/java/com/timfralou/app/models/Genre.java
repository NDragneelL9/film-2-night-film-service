package com.timfralou.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Genre {
    @JsonIgnore
    private long id;
    @JsonProperty("genre")
    private String genre;

    public Genre() {
        // keep
    }

    public long id() {
        return id;
    }

    public String genre() {
        return genre;
    }

    @Override
    public String toString() {
        return "Genre [genre=" + genre + "]";
    }

}
