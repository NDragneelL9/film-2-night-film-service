package com.timfralou.app.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.timfralou.app.seeds.CountrySeed;
import com.timfralou.app.seeds.FilmSeed;

public class CountryTest {
    private JSONObject countryJson;

    public CountryTest() {
        String jsonTxt = new String();
        try {
            jsonTxt = new String(
                    Files.readAllBytes(Paths.get("src/test/java/com/timfralou/app/seeds/jsons/Country.json")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        JSONObject jsonObj = new JSONObject(jsonTxt);
        this.countryJson = jsonObj;
    }

    @Test
    public void hasCountry() {
        String actualCountry = countryJson.optString("country", "");
        Country country = new CountrySeed().country();
        assertEquals(country.country(), actualCountry);
    }
}
