package com.timfralou.app.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import com.timfralou.app.postgresql.PostgreDB;
import com.timfralou.app.seeds.CountrySeed;
import com.timfralou.app.postgresql.dbType;

public class CountryTest {
    private JSONObject countryJson;
    private Country country;
    private static final PostgreDB db = new PostgreDB(dbType.TEST);

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
        this.country = new CountrySeed().country();
    }

    @Test
    public void hasCountry() {
        String actualCountry = countryJson.optString("country", "");
        assertEquals(country.country(), actualCountry);
    }

    @Test
    public void savesCountryToDB() throws SQLException {
        int insertedRows = country.saveToDB(db.conn());
        assertEquals(1, insertedRows);
    }

    @AfterAll
    public static void cleanUp() throws SQLException {
        db.updateQuery("DELETE FROM countries");
    }
}
