package com.timfralou.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Genre {
    @JsonIgnore
    private long id;
    @JsonProperty("genre")
    private String genre;

    public Genre() {
        // keep
    }

    public Genre(String s) {
        this.genre = s;
    }

    public long id() {
        return id;
    }

    public String genre() {
        return genre;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((genre == null) ? 0 : genre.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Genre other = (Genre) obj;
        if (genre == null) {
            if (other.genre != null)
                return false;
        } else if (!genre.equals(other.genre))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Genre [genre=" + genre + "]";
    }

}
