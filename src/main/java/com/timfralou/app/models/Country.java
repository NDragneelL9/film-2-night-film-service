package com.timfralou.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {
    @JsonIgnore
    private long id;
    @JsonProperty("country")
    private String country;

    public Country() {
        // keep
    }

    public long id() {
        return id;
    }

    public String country() {
        return country;
    }

    @Override
    public String toString() {
        return "Country [country=" + country + "]";
    }
}
