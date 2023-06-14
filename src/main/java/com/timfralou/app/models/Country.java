package com.timfralou.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Country {
    @JsonIgnore
    private long id;
    @JsonProperty("country")
    private String country;

    public Country() {
        // keep
    }

    @Override
    public String toString() {
        return "Country [country=" + country + "]";
    }
}
