package com.timfralou.app.seeds;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timfralou.app.models.Country;

public class CountrySeed {
    private Country country;

    public CountrySeed() {
        ObjectMapper objectMapper = new ObjectMapper();
        Country country = new Country();
        try {
            country = objectMapper.readValue(
                    new File("src/test/java/com/timfralou/app/seeds/jsons/Country.json"), Country.class);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.country = country;
    }

    public Country country() {
        return country;
    }
}
