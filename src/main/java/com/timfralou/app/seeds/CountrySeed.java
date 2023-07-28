package com.timfralou.app.seeds;

import java.io.File;
import java.io.IOException;
import com.timfralou.app.models.Country;

public class CountrySeed extends BaseSeed {
    private Country country;

    public CountrySeed() {
        try {
            Country country = objectMapper.readValue(
                    new File(basePath + "Country.json"), Country.class);
            this.country = country;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Country country() {
        return country;
    }
}
