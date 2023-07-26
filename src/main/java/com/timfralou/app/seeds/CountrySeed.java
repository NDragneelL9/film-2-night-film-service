package com.timfralou.app.seeds;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timfralou.app.models.Country;

public class CountrySeed {
    private Country country;

    public CountrySeed() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Country country = objectMapper.readValue(
                    new File("src/main/java/com/timfralou/app/seeds/jsons/Country.json"), Country.class);
            this.country = country;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Country country() {
        return country;
    }
}
